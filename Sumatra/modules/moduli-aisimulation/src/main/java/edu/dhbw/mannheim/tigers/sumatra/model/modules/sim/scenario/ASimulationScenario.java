/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.SimulationParameters;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.SimulationParameters.SimulationObject;
import edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.stopcrit.ASimStopCriterion;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.sumatra.Referee.SSL_Referee;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.ai.data.PlayStrategy;
import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.autoreferee.AutoReferee;
import edu.tigers.sumatra.autoreferee.RefereeCaseMsg;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.referee.RefereeHandler;
import edu.tigers.sumatra.referee.RefereeMsg;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;
import edu.x.framework.skillsystem.skills.ISkill;



public abstract class ASimulationScenario
{
	private static final Logger				log							= Logger.getLogger(ASimulationScenario.class
																								.getName());
	private final List<ASimStopCriterion>	stopCriteria				= new ArrayList<>();
	private final SimulationParameters		params						= new SimulationParameters();
	private long									startTimeReal, startTimeSim;
	private boolean								stopSimulation				= false;
																						
	private boolean								enableBlue					= false,
																enableYellow = false;
	private boolean								enableAutoReferee			= true;
																						
	private AutoReferee							autoReferee					= null;
																						
	private boolean								paused						= false;
																						
	private boolean								stopAfterCompletition	= false;
																						
	private SSL_Referee							newRefereeMsg, latestRefereeMsg;
														
														
	
	public final SimulationParameters setupSimulation()
	{
		setupBots(params.getInitBots());
		params.setInitBall(setupBall());
		setupStopCriteria(stopCriteria);
		return params;
	}
	
	
	
	public final void start(final long timestamp)
	{
		try
		{
			autoReferee = (AutoReferee) SumatraModel.getInstance().getModule(AutoReferee.MODULE_ID);
			autoReferee.setProcessRefereeCases(true);
		} catch (ModuleNotFoundException err)
		{
			log.error("Could not find autoReferee module.", err);
		}
		
		startTimeSim = timestamp;
		startTimeReal = System.nanoTime();
	}
	
	
	
	public final AIInfoFrame createInitialAiFrame(final WorldFrameWrapper wfw, final ETeamColor teamColor)
	{
		BaseAiFrame bFrame = new BaseAiFrame(wfw, false, null, teamColor);
		TacticalField tacticalField = new TacticalField();
		setupInitialTacticalField(tacticalField);
		
		newRefereeMsg(getInitialRefereeCmd(), 0, 0, 100, bFrame.getWorldFrame().getTimestamp());
		
		MetisAiFrame mFrame = new MetisAiFrame(bFrame, tacticalField);
		AthenaAiFrame aFrame = new AthenaAiFrame(mFrame, new PlayStrategy(new PlayStrategy.Builder()));
		AIInfoFrame aiFrame = new AIInfoFrame(aFrame);
		return aiFrame;
	}
	
	
	
	public final void stopSimulation()
	{
		stopSimulation = true;
	}
	
	
	
	public final boolean checkStopSimulation(final AIInfoFrame aiFrame)
	{
		if (stopSimulation)
		{
			return true;
		}
		for (ASimStopCriterion crit : stopCriteria)
		{
			if (crit.checkStopSimulation(aiFrame))
			{
				return true;
			}
		}
		return false;
	}
	
	
	protected void setupStopCriteria(final List<ASimStopCriterion> criteria)
	{
	
	}
	
	
	protected void setupBots(final Map<BotID, SimulationObject> bots)
	{
	
	}
	
	
	protected final void setupBotsFormation1(final Map<BotID, SimulationObject> bots)
	{
		int side = 1;
		ETeamColor[] tcs = new ETeamColor[] { ETeamColor.YELLOW, ETeamColor.BLUE };
		for (ETeamColor tc : tcs)
		{
			bots.put(BotID.createBotId(0, tc), new SimulationObject(new Vector3(side * 3900, 0, 0)));
			bots.put(BotID.createBotId(1, tc), new SimulationObject(new Vector3(side * 2800, 300, 0)));
			bots.put(BotID.createBotId(2, tc), new SimulationObject(new Vector3(side * 2800, -300, 0)));
			bots.put(BotID.createBotId(3, tc), new SimulationObject(new Vector3(side * 1500, 1000, 0)));
			bots.put(BotID.createBotId(4, tc), new SimulationObject(new Vector3(side * 1500, -1000, 0)));
			bots.put(BotID.createBotId(5, tc), new SimulationObject(new Vector3(side * 1000, 0, 0)));
			
			side *= -1;
		}
	}
	
	
	protected void setupInitialTacticalField(final TacticalField tacticalField)
	{
	
	}
	
	
	protected Command getInitialRefereeCmd()
	{
		return Command.FORCE_START;
	}
	
	
	protected SimulationObject setupBall()
	{
		SimulationObject ball = new SimulationObject();
		return ball;
	}
	
	
	
	public void onUpdate(final AIInfoFrame aiFrame, final List<RefereeCaseMsg> caseMsgs)
	{
	}
	
	
	
	public final double getSimTimeSinceStart(final long timestamp)
	{
		return (timestamp - startTimeSim) * 1e-9f;
	}
	
	
	
	public final double getRealTimeSinceStart()
	{
		return (System.nanoTime() - startTimeReal) * 1e-9f;
	}
	
	
	
	public final SimulationParameters getParams()
	{
		return params;
	}
	
	
	protected final void newRefereeMsg(final Command cmd, final int goalsBlue, final int goalsYellow,
			final int timeLeft, final long timestamp)
	{
		int refId = latestRefereeMsg == null ? 0 : (int) latestRefereeMsg.getCommandCounter();
		newRefereeMsg = RefereeHandler.createSSLRefereeMsg(cmd, goalsBlue, goalsYellow, timeLeft, refId, timestamp, null);
	}
	
	
	
	protected String getBotAiInfo(final AIInfoFrame aiFrame, final BotID botId)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(botId.getTeamColor().name());
		sb.append('_');
		sb.append(botId.getNumber());
		ITrackedBot bot = aiFrame.getWorldFrame().getBot(botId);
		if (bot == null)
		{
			return sb.toString();
		}
		sb.append(' ');
		sb.append(bot.getPos());
		
		ARole role = aiFrame.getPlayStrategy().getActiveRoles().getWithNull(botId);
		if (role == null)
		{
			return sb.toString();
		}
		sb.append(' ');
		sb.append(role.getType().name());
		sb.append(' ');
		sb.append(role.getCurrentState().name());
		
		ISkill skill = role.getCurrentSkill();
		if (skill == null)
		{
			return sb.toString();
		}
		sb.append(' ');
		sb.append(skill.getType().name());
		return sb.toString();
	}
	
	
	
	public List<RefereeCaseMsg> processAutoReferee(final WorldFrameWrapper wfw, final RefereeMsg refMsg)
	{
		if (!enableAutoReferee)
		{
			return new ArrayList<>();
		}
		
		return autoReferee.process(wfw, refMsg);
	}
	
	
	
	public List<String> getRuleViolationsForAI(final AIInfoFrame aiFrame, final List<RefereeCaseMsg> msgs)
	{
		List<String> errors = new ArrayList<>();
		if (!msgs.isEmpty())
		{
			for (RefereeCaseMsg msg : msgs)
			{
				if (msg.getTeamAtFault() != aiFrame.getTeamColor())
				{
					continue;
				}
				StringBuilder stringMsg = new StringBuilder();
				stringMsg.append("Rule violation: ");
				stringMsg.append(msg.getMsgType().name());
				stringMsg.append(" by ");
				stringMsg.append(msg.getTeamAtFault().toString());
				if (msg.getBotAtFault().isBot())
				{
					stringMsg.append(" (");
					stringMsg.append(msg.getBotAtFault());
					stringMsg.append(")");
				}
				if (!msg.getAdditionalInfo().isEmpty())
				{
					stringMsg.append(" ");
					stringMsg.append(msg.getAdditionalInfo());
				}
				if (aiFrame.getTeamColor() == msg.getBotAtFault().getTeamColor())
				{
					stringMsg.append(" AI bot: ");
					stringMsg.append(getBotAiInfo(aiFrame, msg.getBotAtFault()));
				}
				errors.add(stringMsg.toString());
			}
		}
		return errors;
	}
	
	
	
	public void togglePause()
	{
		paused = !paused;
	}
	
	
	
	public void pause()
	{
		paused = true;
	}
	
	
	
	public void resume()
	{
		paused = false;
	}
	
	
	
	public void afterSimulation(final AIInfoFrame aiFrame)
	{
	
	}
	
	
	
	public final boolean isEnableBlue()
	{
		return enableBlue;
	}
	
	
	
	protected final void setEnableBlue(final boolean enableBlue)
	{
		this.enableBlue = enableBlue;
	}
	
	
	
	public final boolean isEnableYellow()
	{
		return enableYellow;
	}
	
	
	
	protected final void setEnableYellow(final boolean enableYellow)
	{
		this.enableYellow = enableYellow;
	}
	
	
	
	public final boolean isEnableAutoReferee()
	{
		return enableAutoReferee;
	}
	
	
	
	public final void setEnableAutoReferee(final boolean enableAutoReferee)
	{
		this.enableAutoReferee = enableAutoReferee;
	}
	
	
	
	public final boolean isPaused()
	{
		return paused;
	}
	
	
	
	public final void setStopOnError(final boolean stopAfterCompletition)
	{
		this.stopAfterCompletition = stopAfterCompletition;
	}
	
	
	
	public final boolean isStopAfterCompletition()
	{
		return stopAfterCompletition;
	}
	
	
	
	public final List<ASimStopCriterion> getStopCriteria()
	{
		return stopCriteria;
	}
	
	
	
	public final SSL_Referee getNewRefereeMsg()
	{
		return newRefereeMsg;
	}
	
	
	
	public final SSL_Referee getLatestRefereeMsg()
	{
		return latestRefereeMsg;
	}
}
