/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.bots;

import java.util.Map;
import java.util.Optional;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.IConfigClient;
import com.github.g3force.configurable.IConfigObserver;
import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.bot.EFeature;
import edu.tigers.sumatra.bot.EFeatureState;
import edu.tigers.sumatra.bot.IBot;
import edu.tigers.sumatra.bot.MoveConstraints;
import edu.tigers.sumatra.botmanager.basestation.IBaseStation;
import edu.tigers.sumatra.botmanager.bots.communication.Statistics;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.CommandFactory;
import edu.tigers.sumatra.botmanager.commands.MatchCommand;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.trajectory.TrajectoryWithTime;



@Persistent(version = 2)
public abstract class ABot implements IBot, IConfigObserver
{
	
	public static final double											BAT_MIN			= 10.5;
	
	public static final double											BAT_MAX			= 12.6;
	
	private final BotID													botId;
	private final EBotType												type;
	private transient final IBaseStation							baseStation;
	
	private double															relBattery		= 1;
	private double															kickerLevel		= 0;
	
	private final Map<EFeature, EFeatureState>					botFeatures;
	
	private transient final Statistics								txStats			= new Statistics();
	private transient final Statistics								rxStats			= new Statistics();
	private transient final MatchCommand							matchCtrl		= new MatchCommand();
	private transient final MoveConstraints						moveConstraints;
	private transient Optional<TrajectoryWithTime<IVector3>>	curTrajectory	= Optional.empty();
	
	private transient double											kickerLevelMax	= 180;
	private transient String											controlledBy	= "";
	private transient boolean											hideFromAi		= false;
	private transient boolean											hideFromRcm		= false;
	private transient long												lastKickTime	= 0;
	
	private transient double											updateRate		= 100;
	private transient double											minUpdateRate	= 10;
	
	
	private static final String[]										BOT_NAMES		= { "Gandalf", "Alice", "Tigger",
			"Poller",
			"Q", "Eichbaum",
			"This Bot",
			"Black Betty",
			"Trinity", "Neo",
			"Bob",
			"Yoda" };
	
	
	
	public ABot(final EBotType type, final BotID id, final IBaseStation baseStation)
	{
		botId = id;
		this.type = type;
		botFeatures = getDefaultFeatureStates();
		this.baseStation = baseStation;
		moveConstraints = new MoveConstraints();
		ConfigRegistration.applySpezis(moveConstraints, "botmgr", "");
		ConfigRegistration.applySpezis(moveConstraints, "botmgr", type.name());
	}
	
	
	protected ABot(final ABot aBot, final EBotType type)
	{
		botId = aBot.botId;
		this.type = type;
		botFeatures = aBot.botFeatures;
		baseStation = aBot.baseStation;
		relBattery = aBot.relBattery;
		kickerLevel = aBot.kickerLevel;
		kickerLevelMax = aBot.kickerLevelMax;
		moveConstraints = aBot.moveConstraints;
	}
	
	
	protected ABot()
	{
		botId = null;
		type = null;
		botFeatures = null;
		baseStation = null;
		moveConstraints = null;
	}
	
	
	@Override
	public void afterApply(final IConfigClient configClient)
	{
		ConfigRegistration.applySpezis(moveConstraints, "botmgr", type.name());
	}
	
	
	
	@Override
	public double getBatteryRelative()
	{
		return relBattery;
	}
	
	
	protected Map<EFeature, EFeatureState> getDefaultFeatureStates()
	{
		Map<EFeature, EFeatureState> result = EFeature.createFeatureList();
		result.put(EFeature.DRIBBLER, EFeatureState.WORKING);
		result.put(EFeature.CHIP_KICKER, EFeatureState.WORKING);
		result.put(EFeature.STRAIGHT_KICKER, EFeatureState.WORKING);
		result.put(EFeature.MOVE, EFeatureState.WORKING);
		result.put(EFeature.BARRIER, EFeatureState.WORKING);
		return result;
	}
	
	
	
	public void execute(final ACommand cmd)
	{
		txStats.packets++;
		txStats.payload += CommandFactory.getInstance().getLength(cmd, false);
	}
	
	
	
	public void sendMatchCommand()
	{
	}
	
	
	
	public abstract void start();
	
	
	
	public abstract void stop();
	
	
	
	@Override
	public double getKickerLevel()
	{
		return kickerLevel;
	}
	
	
	
	@Override
	public double getKickerLevelMax()
	{
		return kickerLevelMax;
	}
	
	
	
	@Override
	public abstract double getDribblerSpeed();
	
	
	
	public void onIncommingBotCommand(final BotID id, final ACommand cmd)
	{
		rxStats.packets++;
		rxStats.payload += CommandFactory.getInstance().getLength(cmd, false);
	}
	
	
	
	@Override
	public abstract int getHardwareId();
	
	
	
	@Override
	public boolean isAvailableToAi()
	{
		return !isBlocked() && !isHideFromAi();
	}
	
	
	
	@Override
	public final long getLastKickTime()
	{
		return lastKickTime;
	}
	
	
	
	protected final void setLastKickTime(final long lastKickTime)
	{
		this.lastKickTime = lastKickTime;
	}
	
	
	
	public abstract boolean isBarrierInterrupted();
	
	
	
	public Statistics getRxStats()
	{
		return new Statistics(rxStats);
	}
	
	
	
	public Statistics getTxStats()
	{
		return new Statistics(txStats);
	}
	
	
	
	@Override
	public final EBotType getType()
	{
		return type;
	}
	
	
	@Override
	public String toString()
	{
		return "[Bot: " + type + "|" + getBotId() + "]";
	}
	
	
	
	@Override
	public final Map<EFeature, EFeatureState> getBotFeatures()
	{
		return botFeatures;
	}
	
	
	
	@Override
	public final String getControlledBy()
	{
		return controlledBy;
	}
	
	
	
	public final void setControlledBy(final String controlledBy)
	{
		this.controlledBy = controlledBy;
	}
	
	
	
	@Override
	public final ETeamColor getColor()
	{
		return getBotId().getTeamColor();
	}
	
	
	
	@Override
	public final boolean isBlocked()
	{
		return !controlledBy.isEmpty();
	}
	
	
	
	@Override
	public final boolean isHideFromAi()
	{
		return hideFromAi;
	}
	
	
	
	public final void setHideFromAi(final boolean excludeFromAi)
	{
		hideFromAi = excludeFromAi;
	}
	
	
	
	@Override
	public final boolean isHideFromRcm()
	{
		return hideFromRcm;
	}
	
	
	
	public final void setHideFromRcm(final boolean hideFromRcm)
	{
		this.hideFromRcm = hideFromRcm;
	}
	
	
	
	@Override
	public final BotID getBotId()
	{
		return botId;
	}
	
	
	
	public final MatchCommand getMatchCtrl()
	{
		return matchCtrl;
	}
	
	
	
	public final double getUpdateRate()
	{
		return updateRate;
	}
	
	
	
	public final void setUpdateRate(final double updateRate)
	{
		this.updateRate = updateRate;
	}
	
	
	
	@Override
	public String getName()
	{
		return BOT_NAMES[getBotId().getNumber()];
	}
	
	
	
	public final IBaseStation getBaseStation()
	{
		return baseStation;
	}
	
	
	
	public void setCheering(final boolean cheering)
	{
		getMatchCtrl().setCheering(cheering);
	}
	
	
	
	protected final void setKickerLevel(final double kickerLevel)
	{
		this.kickerLevel = kickerLevel;
	}
	
	
	
	@Override
	public Optional<IVector3> getSensoryPos()
	{
		return Optional.empty();
	}
	
	
	
	@Override
	public Optional<IVector3> getSensoryVel()
	{
		return Optional.empty();
	}
	
	
	
	@Override
	public Optional<TrajectoryWithTime<IVector3>> getCurrentTrajectory()
	{
		return curTrajectory;
	}
	
	
	
	public synchronized void setCurrentTrajectory(final Optional<TrajectoryWithTime<IVector3>> curTrajectory)
	{
		this.curTrajectory = curTrajectory;
	}
	
	
	
	public double getMinUpdateRate()
	{
		return minUpdateRate;
	}
	
	
	
	public IVector3 getGlobalTargetVelocity(final long timestamp)
	{
		return AVector3.ZERO_VECTOR;
	}
	
	
	
	@Override
	public double getKickSpeed()
	{
		return getMatchCtrl().getKickSpeed();
	}
	
	
	
	@Override
	public String getDevice()
	{
		return getMatchCtrl().getDevice().name();
	}
	
	
	
	public void setRelBattery(final double relBattery)
	{
		this.relBattery = relBattery;
	}
	
	
	
	@Override
	public double getDefaultVelocity()
	{
		return getMoveConstraints().getVelMax();
	}
	
	
	
	@Override
	public double getDefaultAcceleration()
	{
		return getMoveConstraints().getAccMax();
	}
	
	
	
	@Override
	public MoveConstraints getMoveConstraints()
	{
		return moveConstraints;
	}
}
