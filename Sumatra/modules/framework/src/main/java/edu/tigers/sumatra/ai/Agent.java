/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.log4j.Logger;

import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.ai.ares.Ares;
import edu.tigers.sumatra.ai.athena.Athena;
import edu.tigers.sumatra.ai.data.MultiTeamMessage;
import edu.tigers.sumatra.ai.data.PlayStrategy;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.ai.metis.Metis;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.multiteammessage.AMultiTeamMessage;
import edu.tigers.sumatra.multiteammessage.IMultiTeamMessageConsumer;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.timer.ATimer;
import edu.tigers.sumatra.timer.DummyTimer;
import edu.tigers.sumatra.timer.ETimable;
import edu.tigers.sumatra.timer.ITimer;
import edu.tigers.sumatra.timer.SumatraTimer;
import edu.tigers.sumatra.wp.AWorldPredictor;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;
import edu.x.framework.XFramework;
import edu.x.framework.skillsystem.ASkillSystem;
import edu.x.framework.skillsystem.GenericSkillSystem;



public class Agent extends AAgent implements Runnable, IMultiTeamMessageConsumer
{
	private static final Logger							log					= Logger.getLogger(Agent.class.getName());
	
	private static final double							MIN_DT				= 0.015;
	
	
	private final BlockingDeque<WorldFrameWrapper>	freshWorldFrames	= new LinkedBlockingDeque<>(1);
	private AIInfoFrame										previousAIFrame	= null;
	private MultiTeamMessage								multiTeamMsg		= null;
	private long												lastRefMsgCounter	= -1;
	
	
	private final Metis										metis					= new Metis();
	private final Athena										athena				= new Athena();
	
	
	private ExecutorService									executor;
	private Ares												ares;
	private ASkillSystem										skillSystem;
	private ITimer												timer					= new DummyTimer();
	
	
	
	private boolean											activated			= false;
	private String												activatedKey		= "";
	
	private boolean											active				= false;
	private boolean											reset					= false;
	
	private ETimable											timableAgent;
	
	private XFramework										xf;
	
	
	
	public Agent(final SubnodeConfiguration subnodeConfiguration)
	{
	}
	
	
	
	public Agent(final ETeamColor teamColor)
	{
		setTeamColor(teamColor);
		skillSystem = new GenericSkillSystem();
		ares = new Ares(skillSystem);
		
		
	}
	
	
	@Override
	public void initModule() throws InitModuleException
	{
		if (MODULE_ID_YELLOW.equals(getId()))
		{
			timableAgent = ETimable.AGENT_Y;
			setTeamColor(ETeamColor.YELLOW);
		} else if (MODULE_ID_BLUE.equals(getId()))
		{
			timableAgent = ETimable.AGENT_B;
			setTeamColor(ETeamColor.BLUE);
		}
		
		activatedKey = Agent.class.getName() + "-" + getId() + ".activated";
		boolean defValue = getTeamColor() == ETeamColor.YELLOW;
		setActive(Boolean.valueOf(SumatraModel.getInstance().getUserProperty(activatedKey, Boolean.toString(defValue))));
		
		
	}
	
	
	@Override
	public void startModule() throws StartModuleException
	{
		if (!activated)
		{
			return;
		}
		try
		{
			timer = ((SumatraTimer) SumatraModel.getInstance().getModule(ATimer.MODULE_ID));
		} catch (final ModuleNotFoundException err)
		{
			log.warn("No timer module found.");
		}
		
		try
		{
			AWorldPredictor predictor = (AWorldPredictor) SumatraModel.getInstance().getModule(AWorldPredictor.MODULE_ID);
			predictor.addWorldFrameConsumer(this);
		} catch (final ModuleNotFoundException err)
		{
			log.warn("No WP module found.");
		}
		
		try
		{
			skillSystem = (ASkillSystem) SumatraModel.getInstance().getModule(ASkillSystem.MODULE_ID);
			ares = new Ares(skillSystem);
		} catch (final ModuleNotFoundException err)
		{
			log.warn("No skill system module found.");
		}
		
		try
		{
			AMultiTeamMessage mtm = (AMultiTeamMessage) SumatraModel.getInstance().getModule(AMultiTeamMessage.MODULE_ID);
			mtm.addMultiTeamMessageConsumer(this);
		} catch (final ModuleNotFoundException err)
		{
		}
		
		lastRefMsgCounter = -1;
		
		assert (executor == null) || executor.isShutdown();
		executor = Executors.newSingleThreadExecutor();
		executor.execute(this);
	}
	
	
	
	private boolean isNewMessage(final RefereeMsg msg)
	{
		if (msg.getCommandCounter() != lastRefMsgCounter)
		{
			lastRefMsgCounter = msg.getCommandCounter();
			return true;
		}
		
		return false;
	}
	
	
	
	public AIInfoFrame processWorldFrame(final WorldFrameWrapper wfw)
	{
		RefereeMsg refereeMsg = wfw.getRefereeMsg();
		boolean newRefereeMsg = false;
		if ((refereeMsg != null) && isNewMessage(refereeMsg))
		{
			log.trace("Referee cmd: " + refereeMsg.getCommand());
			newRefereeMsg = true;
		}
		
		BaseAiFrame baseAiFrame = new BaseAiFrame(wfw, newRefereeMsg, previousAIFrame, getTeamColor());
		
		if (previousAIFrame == null)
		{
			// Skip first frame
			previousAIFrame = generateAIInfoFrame(baseAiFrame);
			return null;
		}
		
		previousAIFrame.cleanUp();
		
		
		// ### Process!
		MetisAiFrame metisAiFrame = null;
		AthenaAiFrame athenaAiFrame = null;
		try
		{
			// Set multi-team message
			baseAiFrame.setMultiTeamMessage(multiTeamMsg);
			
			
			// Analyze
			metisAiFrame = metis.process(baseAiFrame);
			
			// Choose and calculate behavior
			athenaAiFrame = athena.process(metisAiFrame);
			
			// Execute!
			// ares.process(athenaAiFrame);
			
			xf = new XFramework(skillSystem);
			xf.run(baseAiFrame);
			
			// ### Populate used AIInfoFrame (for visualization etc)
			previousAIFrame = new AIInfoFrame(athenaAiFrame);
			return previousAIFrame;
		} catch (final Throwable ex)
		{
			log.error("Exception in AI: " + ex.getMessage(), ex);
			// # Notify observers (gui) about errors...
			notifyNewAIExceptionVisualize(ex, generateVisualizationFrame(baseAiFrame), new VisualizationFrame(
					previousAIFrame));
			
			// # Undo everything we've done this cycle to restore previous state
			// - RefereeMsg
			if (refereeMsg != null)
			{
				lastRefMsgCounter--;
			}
			
			if (athenaAiFrame != null)
			{
				athena.onException(athenaAiFrame);
			}
			
			skillSystem.reset(getTeamColor());
			
			if (previousAIFrame != null)
			{
				previousAIFrame.cleanUp();
			}
			previousAIFrame = generateAIInfoFrame(baseAiFrame);
		}
		
		return null;
	}
	
	
	
	@Override
	public void run()
	{
		Thread.currentThread().setName("AI_Nathan_" + getId());
		while (!Thread.currentThread().isInterrupted())
		{
			if (!active)
			{
				if (previousAIFrame != null)
				{
					notifyAIStopped(getTeamColor());
				}
				previousAIFrame = null;
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException err)
				{
					break;
				}
				continue;
			}
			// ### Get latest worldframe
			WorldFrameWrapper wfw;
			try
			{
				wfw = freshWorldFrames.takeLast();
			} catch (final InterruptedException err)
			{
				// No error here...
				break;
			}
			if (wfw == null)
			{
				notifyAIStopped(getTeamColor());
				continue;
			}
			
			if (reset)
			{
				previousAIFrame = null;
				lastRefMsgCounter = -1;
				metis.reset();
				reset = false;
			}
			
			long tLast = 0;
			if (previousAIFrame != null)
			{
				tLast = previousAIFrame.getSimpleWorldFrame().getTimestamp();
			}
			long tNow = wfw.getSimpleWorldFrame().getTimestamp();
			double dt = (tNow - tLast) / 1e9;
			if ((dt < MIN_DT))
			{
				continue;
			}
			
			long id = wfw.getSimpleWorldFrame().getId();
			
			timer.start(timableAgent, id);
			
			AIInfoFrame frame = processWorldFrame(wfw);
			if (frame != null)
			{
				notifyNewAIInfoFrame(frame);
				VisualizationFrame visFrame = new VisualizationFrame(frame);
				notifyNewAIInfoFrameVisualize(visFrame);
			}
			// TODO
			// if (baseAiFrame.getPrevFrame().getPlayStrategy().getAIControlState() == EAIControlState.MIXED_TEAM_MODE)
			// {
			// multiTeamMessageSender.send(athenaAiFrame);
			// }
			
			timer.stop(timableAgent, id);
		}
		notifyAIStopped(getTeamColor());
	}
	
	
	private AIInfoFrame generateAIInfoFrame(final BaseAiFrame baseAiFrame)
	{
		return new AIInfoFrame(new AthenaAiFrame(new MetisAiFrame(baseAiFrame, new TacticalField()), new PlayStrategy(
				new PlayStrategy.Builder())));
	}
	
	
	private VisualizationFrame generateVisualizationFrame(final BaseAiFrame baseAiFrame)
	{
		return new VisualizationFrame(generateAIInfoFrame(baseAiFrame));
	}
	
	
	@Override
	public void stopModule()
	{
		if (!activated)
		{
			return;
		}
		executor.shutdownNow();
		
		try
		{
			AWorldPredictor predictor = (AWorldPredictor) SumatraModel.getInstance().getModule(AWorldPredictor.MODULE_ID);
			predictor.removeWorldFrameConsumer(this);
		} catch (final ModuleNotFoundException err)
		{
			log.warn("No WP module found.");
		}
		
		try
		{
			AMultiTeamMessage mtm = (AMultiTeamMessage) SumatraModel.getInstance().getModule(AMultiTeamMessage.MODULE_ID);
			mtm.removeMultiTeamMessageConsumer(this);
		} catch (final ModuleNotFoundException err)
		{
		}
		
		try
		{
			executor.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e)
		{
			log.error("Timed out waiting for agent to stop", e);
		}
	}
	
	
	@Override
	public void deinitModule()
	{
		metis.stop();
		active = false;
		
		if (ares != null)
		{
			ares = null;
		}
		
		previousAIFrame = null;
	}
	
	
	@Override
	public void onNewWorldFrame(final WorldFrameWrapper wfWrapper)
	{
		freshWorldFrames.pollLast();
		freshWorldFrames.addFirst(wfWrapper);
	}
	
	
	@Override
	public void onClearWorldFrame()
	{
		freshWorldFrames.clear();
		reset();
	}
	
	
	@Override
	public void onNewMultiTeamMessage(final MultiTeamMessage message)
	{
		multiTeamMsg = message;
	}
	
	
	
	private void setActivated()
	{
		if (!activated && (SumatraModel.getInstance().getModulesState().get() == ModulesState.ACTIVE))
		{
			try
			{
				activated = true;
				startModule();
			} catch (StartModuleException err)
			{
				log.error("Could not start Agent " + getId());
			}
		}
		activated = true;
	}
	
	
	
	@Override
	public void reset()
	{
		reset = true;
	}
	
	
	
	public final boolean isActive()
	{
		return active;
	}
	
	
	
	public final void setActive(final boolean active)
	{
		setActivated();
		this.active = active;
		SumatraModel.getInstance().setUserProperty(activatedKey, String.valueOf(active));
	}
	
	
	
	public final Metis getMetis()
	{
		return metis;
	}
	
	
	
	public final Ares getAres()
	{
		return ares;
	}
	
	
	
	public final Athena getAthena()
	{
		return athena;
	}
	
	
	
	public final AIInfoFrame getLatestAiFrame()
	{
		return previousAIFrame;
	}
	
	
	
	public final void setPreviousAIFrame(final AIInfoFrame previousAIFrame)
	{
		this.previousAIFrame = previousAIFrame;
	}
	
	
	
	public final ASkillSystem getSkillSystem()
	{
		return skillSystem;
	}
}
