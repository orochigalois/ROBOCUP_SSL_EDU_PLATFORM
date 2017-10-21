/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.tigers.sumatra.ai.data.ballpossession.BallPossession;
import edu.tigers.sumatra.ai.data.event.GameEvents;
import edu.tigers.sumatra.ai.lachesis.RoleFinderInfo;
import edu.tigers.sumatra.ai.metis.ECalculator;
import edu.tigers.sumatra.ai.metis.defense.KeeperStateCalc;
import edu.tigers.sumatra.ai.metis.defense.data.AngleDefenseData;
import edu.tigers.sumatra.ai.metis.defense.data.DefensePoint;
import edu.tigers.sumatra.ai.metis.defense.data.FoeBotData;
import edu.tigers.sumatra.ai.metis.offense.data.OffensiveAction;
import edu.tigers.sumatra.ai.metis.support.data.AdvancedPassTarget;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.drawable.ValuedField;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.ValuePoint;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.ShapeMap;



public class TacticalField implements ITacticalField
{
	private final List<FoeBotData>					dangerousFoeBots							= new ArrayList<FoeBotData>();
	
	private AngleDefenseData							angleDefenseData							= new AngleDefenseData();
	
	private DefensePoint									directShotSingleDefenderDefPoint		= null;
	private DefensePoint									directShotDoubleDefenderDefPointA	= null;
	private DefensePoint									directShotDoubleDefenderDefPointB	= null;
	
	private Map<ITrackedBot, DefensePoint>			directShotDefenderDistr					= new HashMap<ITrackedBot, DefensePoint>();
	
	private List<BotID>									crucialDefenders							= new ArrayList<BotID>();
	
	private final List<ValuePoint>					goalValuePoints							= new ArrayList<>();
	private ValuePoint									bestDirectShotTarget						= new ValuePoint(
			
			Geometry.getGoalTheir()
					.getGoalCenter());
	
	private Map<BotID, ValuePoint>					bestDirectShotTargetsForTigerBots	= new HashMap<BotID, ValuePoint>();
	
	private final Map<BotID, BotDistance>			tigersToBallDist							= new LinkedHashMap<>();
	
	private final Map<BotID, BotDistance>			enemiesToBallDist							= new LinkedHashMap<>();
	
	
	private BallPossession								ballPossession;
	
	
	private EPossibleGoal								possibleGoal;
	
	
	private BotID											botLastTouchedBall;
	
	private BotID											botTouchedBall;
	
	private final Map<BotID, BotAiInformation>	botAiInformation							= new HashMap<>();
	
	private EGameStateTeam								gameState									= EGameStateTeam.UNKNOWN;
	
	private boolean										goalScored									= false;
	
	private IVector2										ballLeftFieldPos							= null;
	
	private final Map<BotID, OffensiveAction>		offensiveActions							= new HashMap<>();
	
	
	private final MatchStatistics						statistics;
	
	private final GameEvents							gameEvents;
	
	private OffensiveStrategy							offensiveStrategy;
	
	private final Map<EPlay, RoleFinderInfo>		roleFinderInfos							= new LinkedHashMap<>();
	
	private EGameBehavior								gameBehavior								= EGameBehavior.OFFENSIVE;
	
	private final ShapeMap								drawableShapes								= new ShapeMap();
	private final List<AdvancedPassTarget>			advancedPassTargetsRanked				= new ArrayList<>();
	
	private final Map<ECalculator, Integer>		metisCalcTimes								= new EnumMap<>(
			ECalculator.class);
	
	private final List<IVector2>						topGpuGridPositions						= new ArrayList<>();
	private ValuedField									supporterValuedField;
	
	private IBotIDMap<ITrackedBot>					supporterBots								= new BotIDMap<>();
	private List<ValuePoint>							scoreChancePoints							= new LinkedList<>();
	private List<ValuePoint>							ballDistancePoints						= new LinkedList<>();
	
	
	private List<ValuePoint>							distanceToFOEGrid							= new LinkedList<>();
	
	private boolean										mixedTeamBothTouchedBall				= false;
	
	private AutomatedThrowInInfo						throwInInfo									= new AutomatedThrowInInfo();
	
	private KeeperStateCalc.EStateId					keeperState									= KeeperStateCalc.EStateId.MOVE_TO_PENALTYAREA;
	
	private Map<BotID, Double>							kickSkillTimes								= new HashMap<>();
	
	private Map<BotID, LedControl>					ledData										= new HashMap<>();
	
	
	
	public TacticalField()
	{
		ballPossession = new BallPossession();
		possibleGoal = EPossibleGoal.NO_ONE;
		botLastTouchedBall = BotID.get();
		statistics = new MatchStatistics();
		gameEvents = new GameEvents();
	}
	
	
	@Override
	public final void cleanup()
	{
		supporterValuedField = null;
	}
	
	
	
	@Override
	public void setDirectShotSingleDefenderDefPoint(final DefensePoint directShotSingleDefenderDefPoint)
	{
		
		this.directShotSingleDefenderDefPoint = directShotSingleDefenderDefPoint;
	}
	
	
	
	@Override
	public AngleDefenseData getAngleDefenseData()
	{
		return angleDefenseData;
	}
	
	
	
	@Override
	public void setAngleDefenseData(final AngleDefenseData angleDefenseData)
	{
		this.angleDefenseData = angleDefenseData;
	}
	
	
	
	@Override
	public DefensePoint getDirectShotSingleDefenderDefPoint()
	{
		
		return directShotSingleDefenderDefPoint;
	}
	
	
	
	@Override
	public void setDirectShotDoubleDefenderDefPointA(final DefensePoint directShotDoubleDefenderDefPointA)
	{
		
		this.directShotDoubleDefenderDefPointA = directShotDoubleDefenderDefPointA;
	}
	
	
	
	@Override
	public DefensePoint getDirectShotDoubleDefenderDefPointA()
	{
		
		return directShotDoubleDefenderDefPointA;
	}
	
	
	
	@Override
	public void setDirectShotDoubleDefenderDefPointB(final DefensePoint directShotDoubleDefenderDefPointB)
	{
		
		this.directShotDoubleDefenderDefPointB = directShotDoubleDefenderDefPointB;
	}
	
	
	
	@Override
	public DefensePoint getDirectShotDoubleDefenderDefPointB()
	{
		
		return directShotDoubleDefenderDefPointB;
	}
	
	
	@Override
	public void setDirectShotDefenderDistr(final Map<ITrackedBot, DefensePoint> directShotDefenderDistr)
	{
		
		this.directShotDefenderDistr = directShotDefenderDistr;
	}
	
	
	
	@Override
	public Map<ITrackedBot, DefensePoint> getDirectShotDefenderDistr()
	{
		return directShotDefenderDistr;
	}
	
	
	
	@Override
	public BallPossession getBallPossession()
	{
		return ballPossession;
	}
	
	
	
	public void setBallPossession(final BallPossession newBallPossession)
	{
		if (newBallPossession == null)
		{
			ballPossession = new BallPossession();
		} else
		{
			ballPossession = newBallPossession;
		}
	}
	
	
	
	public void setTigersToBallDist(final List<BotDistance> newTigersToBallDist)
	{
		tigersToBallDist.clear();
		for (final BotDistance bDist : newTigersToBallDist)
		{
			tigersToBallDist.put(bDist.getBot().getBotId(), bDist);
		}
	}
	
	
	
	@Override
	public Map<BotID, BotDistance> getTigersToBallDist()
	{
		return Collections.unmodifiableMap(tigersToBallDist);
	}
	
	
	
	@Override
	public BotDistance getTigerClosestToBall()
	{
		if (!getTigersToBallDist().values().isEmpty())
		{
			return getTigersToBallDist().values().iterator().next();
		}
		return BotDistance.NULL_BOT_DISTANCE;
	}
	
	
	
	public void setEnemiesToBallDist(final List<BotDistance> newEnemiesToBallDist)
	{
		enemiesToBallDist.clear();
		for (final BotDistance bDist : newEnemiesToBallDist)
		{
			enemiesToBallDist.put(bDist.getBot().getBotId(), bDist);
		}
	}
	
	
	
	@Override
	public Map<BotID, BotDistance> getEnemiesToBallDist()
	{
		return Collections.unmodifiableMap(enemiesToBallDist);
	}
	
	
	
	@Override
	public BotDistance getEnemyClosestToBall()
	{
		if (enemiesToBallDist.values().size() > 0)
		{
			return enemiesToBallDist.values().iterator().next();
		}
		return BotDistance.NULL_BOT_DISTANCE;
	}
	
	
	
	@Override
	public EPossibleGoal getPossibleGoal()
	{
		return possibleGoal;
	}
	
	
	
	public void setPossibleGoal(final EPossibleGoal possibleGoal)
	{
		this.possibleGoal = possibleGoal;
	}
	
	
	
	@Override
	public BotID getBotLastTouchedBall()
	{
		return botLastTouchedBall;
	}
	
	
	
	public void setBotLastTouchedBall(final BotID botLastTouchedBall)
	{
		this.botLastTouchedBall = botLastTouchedBall;
	}
	
	
	
	@Override
	public BotID getBotTouchedBall()
	{
		return botTouchedBall;
	}
	
	
	
	public void setBotTouchedBall(final BotID botTouchedBall)
	{
		this.botTouchedBall = botTouchedBall;
	}
	
	
	
	@Override
	public final List<ValuePoint> getGoalValuePoints()
	{
		return goalValuePoints;
	}
	
	
	
	@Override
	public final ValuePoint getBestDirectShootTarget()
	{
		return bestDirectShotTarget;
	}
	
	
	
	public final void setBestDirectShotTarget(final ValuePoint bestDirectShootTarget)
	{
		bestDirectShotTarget = bestDirectShootTarget;
	}
	
	
	
	@Override
	public Map<BotID, ValuePoint> getBestDirectShotTargetsForTigerBots()
	{
		return bestDirectShotTargetsForTigerBots;
	}
	
	
	
	public void setBestDirectShotTargetsForTigerBots(final Map<BotID, ValuePoint> bestDirectShotTargetsForTigerBots)
	{
		this.bestDirectShotTargetsForTigerBots = bestDirectShotTargetsForTigerBots;
	}
	
	
	
	@Override
	public final Map<BotID, OffensiveAction> getOffensiveActions()
	{
		return offensiveActions;
	}
	
	
	
	@Override
	public final Map<BotID, BotAiInformation> getBotAiInformation()
	{
		return botAiInformation;
	}
	
	
	
	@Override
	public final EGameStateTeam getGameState()
	{
		return gameState;
	}
	
	
	
	public final void setGameState(final EGameStateTeam gameState)
	{
		this.gameState = gameState;
	}
	
	
	
	@Override
	public boolean isGoalScored()
	{
		return goalScored;
	}
	
	
	
	public void setGoalScored(final boolean goalScored)
	{
		this.goalScored = goalScored;
	}
	
	
	
	@Override
	public final IVector2 getBallLeftFieldPos()
	{
		return ballLeftFieldPos;
	}
	
	
	
	public final void setBallLeftFieldPos(final IVector2 ballLeftFieldPos)
	{
		this.ballLeftFieldPos = ballLeftFieldPos;
	}
	
	
	
	@Override
	public final ValuedField getSupporterValuedField()
	{
		return supporterValuedField;
	}
	
	
	
	public final void setSupporterValuedField(final ValuedField supporterValuedField)
	{
		this.supporterValuedField = supporterValuedField;
	}
	
	
	
	@Override
	public MatchStatistics getStatistics()
	{
		return statistics;
	}
	
	
	
	@Override
	public GameEvents getGameEvents()
	{
		return gameEvents;
	}
	
	
	
	@Override
	public OffensiveStrategy getOffensiveStrategy()
	{
		return offensiveStrategy;
	}
	
	
	
	public void setOffensiveStrategy(final OffensiveStrategy strategy)
	{
		offensiveStrategy = strategy;
	}
	
	
	@Override
	public Map<EPlay, RoleFinderInfo> getRoleFinderInfos()
	{
		return roleFinderInfos;
	}
	
	
	
	@Override
	public EGameBehavior getGameBehavior()
	{
		return gameBehavior;
	}
	
	
	
	public void setGameBehavior(final EGameBehavior gameBehavior)
	{
		this.gameBehavior = gameBehavior;
	}
	
	
	
	@Override
	public void addCrucialDefender(final BotID crucialDefenderID)
	{
		for (BotID curBotId : crucialDefenders)
		{
			if (curBotId.getNumber() == crucialDefenderID.getNumber())
			{
				
				return;
			}
		}
		
		crucialDefenders.add(crucialDefenderID);
	}
	
	
	
	@Override
	public List<BotID> getCrucialDefenders()
	{
		
		return crucialDefenders;
	}
	
	
	
	@Override
	public ShapeMap getDrawableShapes()
	{
		return drawableShapes;
	}
	
	
	
	@Override
	public List<AdvancedPassTarget> getAdvancedPassTargetsRanked()
	{
		return advancedPassTargetsRanked;
	}
	
	
	
	@Override
	public Map<ECalculator, Integer> getMetisCalcTimes()
	{
		return metisCalcTimes;
	}
	
	
	
	@Override
	public final List<IVector2> getTopGpuGridPositions()
	{
		return topGpuGridPositions;
	}
	
	
	
	@Override
	public boolean isMixedTeamBothTouchedBall()
	{
		return mixedTeamBothTouchedBall;
	}
	
	
	
	public void setMixedTeamBothTouchedBall(final boolean mixedTeamBothTouchedBall)
	{
		this.mixedTeamBothTouchedBall = mixedTeamBothTouchedBall;
	}
	
	
	
	@Override
	public AutomatedThrowInInfo getThrowInInfo()
	{
		return throwInInfo;
	}
	
	
	
	public void setThrowInInfo(final AutomatedThrowInInfo throwInInfo)
	{
		this.throwInInfo = throwInInfo;
	}
	
	
	
	@Override
	public KeeperStateCalc.EStateId getKeeperState()
	{
		return keeperState;
	}
	
	
	
	public void setKeeperState(final KeeperStateCalc.EStateId keeperState)
	{
		this.keeperState = keeperState;
	}
	
	
	@Override
	public List<ValuePoint> getScoreChancePoints()
	{
		return scoreChancePoints;
	}
	
	
	
	public Map<BotID, Double> getKickSkillTimes()
	{
		return kickSkillTimes;
		
	}
	
	
	
	public List<ValuePoint> getDistanceToFOEGrid()
	{
		return distanceToFOEGrid;
	}
	
	
	
	public void setDistanceToFOEGrid(final List<ValuePoint> distancePoints)
	{
		distanceToFOEGrid = distancePoints;
	}
	
	
	
	public IBotIDMap<ITrackedBot> getSupporterBots()
	{
		return supporterBots;
	}
	
	
	
	public void setSupporterBots(final IBotIDMap<ITrackedBot> supporterBots)
	{
		this.supporterBots = supporterBots;
	}
	
	
	
	public void setKickSkillTimes(final Map<BotID, Double> kickSkillTimes)
	{
		this.kickSkillTimes = kickSkillTimes;
	}
	
	
	
	@Override
	public List<FoeBotData> getDangerousFoeBots()
	{
		return dangerousFoeBots;
	}
	
	
	
	@Override
	public Map<BotID, LedControl> getLedData()
	{
		return ledData;
	}
	
	
	
	public void setLedData(final Map<BotID, LedControl> ledData)
	{
		this.ledData = ledData;
	}
	
	
	
	@Override
	public List<ValuePoint> getBallDistancePoints()
	{
		return ballDistancePoints;
	}
}
