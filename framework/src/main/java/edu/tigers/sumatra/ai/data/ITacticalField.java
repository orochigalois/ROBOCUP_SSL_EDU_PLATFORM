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

import java.util.LinkedHashMap;
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
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.ValuePoint;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.ShapeMap;



public interface ITacticalField
{
	
	void cleanup();
	
	
	
	public AngleDefenseData getAngleDefenseData();
	
	
	
	public void setAngleDefenseData(final AngleDefenseData angleDefenseData);
	
	
	
	BallPossession getBallPossession();
	
	
	
	public void setDirectShotSingleDefenderDefPoint(final DefensePoint directShotSingleDefenderDefPoint);
	
	
	
	public DefensePoint getDirectShotSingleDefenderDefPoint();
	
	
	
	public List<ValuePoint> getScoreChancePoints();
	
	
	
	public void setDirectShotDoubleDefenderDefPointA(final DefensePoint directShotDoubleDefenderDefPointA);
	
	
	
	public DefensePoint getDirectShotDoubleDefenderDefPointA();
	
	
	
	public void setDirectShotDoubleDefenderDefPointB(final DefensePoint directShotDoubleDefenderDefPointB);
	
	
	
	public DefensePoint getDirectShotDoubleDefenderDefPointB();
	
	
	
	void setDirectShotDefenderDistr(final Map<ITrackedBot, DefensePoint> directShotDefenderDistr);
	
	
	
	public Map<ITrackedBot, DefensePoint> getDirectShotDefenderDistr();
	
	
	
	Map<BotID, BotDistance> getTigersToBallDist();
	
	
	
	BotDistance getTigerClosestToBall();
	
	
	
	Map<BotID, BotDistance> getEnemiesToBallDist();
	
	
	
	BotDistance getEnemyClosestToBall();
	
	
	
	EPossibleGoal getPossibleGoal();
	
	
	
	BotID getBotLastTouchedBall();
	
	
	
	BotID getBotTouchedBall();
	
	
	
	ValuePoint getBestDirectShootTarget();
	
	
	
	Map<BotID, ValuePoint> getBestDirectShotTargetsForTigerBots();
	
	
	
	List<ValuePoint> getGoalValuePoints();
	
	
	
	ValuedField getSupporterValuedField();
	
	
	
	OffensiveStrategy getOffensiveStrategy();
	
	
	
	Map<BotID, BotAiInformation> getBotAiInformation();
	
	
	
	EGameStateTeam getGameState();
	
	
	
	boolean isGoalScored();
	
	
	
	IVector2 getBallLeftFieldPos();
	
	
	
	MatchStatistics getStatistics();
	
	
	
	Map<EPlay, RoleFinderInfo> getRoleFinderInfos();
	
	
	
	EGameBehavior getGameBehavior();
	
	
	
	ShapeMap getDrawableShapes();
	
	
	
	List<AdvancedPassTarget> getAdvancedPassTargetsRanked();
	
	
	
	public void addCrucialDefender(final BotID crucialDefenderID);
	
	
	
	public List<BotID> getCrucialDefenders();
	
	
	
	Map<ECalculator, Integer> getMetisCalcTimes();
	
	
	
	List<IVector2> getTopGpuGridPositions();
	
	
	
	Map<BotID, OffensiveAction> getOffensiveActions();
	
	
	
	public boolean isMixedTeamBothTouchedBall();
	
	
	
	AutomatedThrowInInfo getThrowInInfo();
	
	
	
	public KeeperStateCalc.EStateId getKeeperState();
	
	
	
	GameEvents getGameEvents();
	
	
	
	List<FoeBotData> getDangerousFoeBots();
	
	
	
	public Map<BotID, LedControl> getLedData();
	
	
	
	List<ValuePoint> getBallDistancePoints();
}
