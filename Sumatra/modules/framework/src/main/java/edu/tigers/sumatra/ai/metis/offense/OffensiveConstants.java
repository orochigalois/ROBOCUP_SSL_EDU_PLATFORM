/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.offense;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.math.AngleMath;



public class OffensiveConstants
{
	
	
	
	@Configurable(comment = "time in seconds")
	private static double	cheeringStopTimer										= 3.0;
	
	
	@Configurable(comment = "time in nanoseconds")
	private static double	delayWaitTime											= 3.0e9;
	
	
	@Configurable(comment = "show additional informations in debug log, lots of spamming")
	private static boolean	showDebugInformations								= false;
	
	
	@Configurable(comment = "no directShots when indirectFreeKick is called")
	private static boolean	forcePassWhenIndirectIsCalled						= true;
	
	
	@Configurable(comment = "minimal distance to ball for move Positions")
	private static double	minDistToBall											= 40;
	
	
	@Configurable(comment = "distance to our Penalty Area")
	private static double	distanceToPenaltyArea								= 300;
	
	
	
	@Configurable(comment = "finalKickStateDistance")
	private static double	finalKickStateDistance								= 650;
	
	
	@Configurable(comment = "finalKickStateUpdate")
	private static double	finalKickStateUpdate									= 300;
	
	
	@Configurable(comment = "chance to do hard coded plays for indirects in enemy half")
	private static double	chanceToDoSpecialMove								= 0.7;
	
	
	@Configurable(comment = "enable supportive Attacker")
	private static boolean	enableSupportiveAttacker							= true;
	
	@Configurable(comment = "max dist where ball can be pushed. if dist > this, then shoot")
	private static double	automatedThrowInPushDinstance						= 750;
	
	@Configurable(comment = "tolerance if ball is at proper position")
	private static double	automatedThrowInFinalTolerance					= 100;
	
	@Configurable(comment = "time in ns to wait for free path to target")
	private static double	automatedThrowInWaitForFreeSightTime			= 3e9;
	
	@Configurable(comment = "time in ns to clear from ball when it is placed at target")
	private static double	automatedThrowInClearMoveTime						= 1e9;
	
	@Configurable()
	private static double	interceptionSkillSecurityDist						= 150;
	
	@Configurable(comment = "Desired ball velocity at kicker of receiving bot")
	private static double	defaultPassEndVel										= 1.5;
	
	@Configurable(comment = "Desired ball velocity at kicker of receiving bot")
	private static double	defaultPassEndVelReceive							= 1.5;
	
	@Configurable(comment = "Added to kick speed due to ball not rolling after kick")
	private static double	kickSpeedOffset										= 1.0;
	
	@Configurable(comment = "Added time that the pass receiving robot needs to get to his passTarget")
	private static double	neededTimeForPassReceivingBotOffset				= 1.0;
	
	@Configurable()
	private static double	ballStartSpeedOffsetForPassTimeCalculation	= 1.0;
	
	@Configurable()
	private static double	maxAngleforPassMaxSpeed								= 50;
	
	@Configurable()
	private static double	maxAngleForReducedSpeed								= 100;
	
	@Configurable()
	private static double	passSpeedReductionForBadAngles					= 1.00;
	
	@Configurable(comment = "dont move when there is enough time")
	private static boolean	enableRedirectorStopMove							= false;
	
	@Configurable()
	private static long		chooseNewStrategyTimer								= 3_000_000_000L;
	
	@Configurable()
	private static double	acceptBestCatcherBallSpeedTreshold				= 0.8;
	
	@Configurable()
	private static double	desperateShotChipKickLength						= 2000;
	
	@Configurable(comment = "A passTarget is bad, when its rating is smaller than... this")
	private static double	classifyPassTargetAsBad								= 0.5;
	
	@Configurable(comment = "The maximum reasonable angle for redirects")
	private static double	maximumReasonableRedirectAngle					= AngleMath.deg2rad(90);
	
	@Configurable()
	private static double	minDistanceForSpeedAddition						= 3000;
	
	@Configurable()
	private static double	maxDistanceForSpeedAddition						= 9000;
	
	@Configurable()
	private static double	distanceSpeedAddition								= 2;
	
	@Configurable()
	private static double	chipKickCheckDistance								= 2000;
	
	@Configurable()
	private static double	chipKickMinDistToTarget								= 4000;
	
	@Configurable()
	private static boolean	isInterceptorEnabled									= true;
	
	@Configurable(comment = "Should keeper be allowed to leave the penalty area")
	private static boolean	enableInsanityMode									= false;
	
	static
	{
		ConfigRegistration.registerClass("offensive", OffensiveConstants.class);
	}
	
	
	
	public static boolean isEnableInsanityMode()
	{
		return enableInsanityMode;
	}
	
	
	
	public static double getAutomatedThrowInWaitForFreeSightTime()
	{
		return automatedThrowInWaitForFreeSightTime;
	}
	
	
	
	public static double getCheeringStopTimer()
	{
		return cheeringStopTimer;
	}
	
	
	
	public static double getDelayWaitTime()
	{
		return delayWaitTime;
	}
	
	
	
	public static boolean isShowDebugInformations()
	{
		return showDebugInformations;
	}
	
	
	
	public static double getAutomatedThrowInPushDinstance()
	{
		return automatedThrowInPushDinstance;
	}
	
	
	
	public static double getAutomatedThrowInFinalTolerance()
	{
		return automatedThrowInFinalTolerance;
	}
	
	
	
	public static boolean isForcePassWhenIndirectIsCalled()
	{
		return forcePassWhenIndirectIsCalled;
	}
	
	
	
	public static double getMinDistToBall()
	{
		return minDistToBall;
	}
	
	
	
	public static double getDistanceToPenaltyArea()
	{
		return distanceToPenaltyArea;
	}
	
	
	
	public static double getFinalKickStateDistance()
	{
		return finalKickStateDistance;
	}
	
	
	
	public static double getFinalKickStateUpdate()
	{
		return finalKickStateUpdate;
	}
	
	
	
	public static double getChanceToDoSpecialMove()
	{
		return chanceToDoSpecialMove;
	}
	
	
	
	public static boolean isSupportiveAttackerEnabled()
	{
		return enableSupportiveAttacker;
	}
	
	
	
	public static double getAutomatedThrowInClearMoveTime()
	{
		return automatedThrowInClearMoveTime;
	}
	
	
	
	public static double getInterceptionSkillSecurityDist()
	{
		return interceptionSkillSecurityDist;
	}
	
	
	
	public static double getDefaultPassEndVel()
	{
		return defaultPassEndVel;
	}
	
	
	
	public static double getKickSpeedOffset()
	{
		return kickSpeedOffset;
	}
	
	
	
	public static double getNeededTimeForPassReceivingBotOffset()
	{
		return neededTimeForPassReceivingBotOffset;
	}
	
	
	
	public static double getBallStartSpeedOffsetForPassTimeCalculation()
	{
		return ballStartSpeedOffsetForPassTimeCalculation;
	}
	
	
	
	public static double getMaxAngleforPassMaxSpeed()
	{
		return maxAngleforPassMaxSpeed;
	}
	
	
	
	public static double getMaxAngleForReducedSpeed()
	{
		return maxAngleForReducedSpeed;
	}
	
	
	
	public static double getPassSpeedReductionForBadAngles()
	{
		return passSpeedReductionForBadAngles;
	}
	
	
	
	public static boolean isEnableRedirectorStopMove()
	{
		return enableRedirectorStopMove;
	}
	
	
	
	public static double getChipKickMinDistToTarget()
	{
		return chipKickMinDistToTarget;
	}
	
	
	
	public static double getMinDistanceForSpeedAddition()
	{
		return minDistanceForSpeedAddition;
	}
	
	
	
	public static double getMaxDistanceForSpeedAddition()
	{
		return maxDistanceForSpeedAddition;
	}
	
	
	
	public static double getDistanceSpeedAddition()
	{
		return distanceSpeedAddition;
	}
	
	
	
	public static long getChooseNewStrategyTimer()
	{
		return chooseNewStrategyTimer;
	}
	
	
	
	public static double getAcceptBestCatcherBallSpeedTreshold()
	{
		return acceptBestCatcherBallSpeedTreshold;
	}
	
	
	
	public static double getDesperateShotChipKickLength()
	{
		return desperateShotChipKickLength;
	}
	
	
	
	public static double getClassifyPassTargetAsBad()
	{
		return classifyPassTargetAsBad;
	}
	
	
	
	public static double getDefaultPassEndVelReceive()
	{
		return defaultPassEndVelReceive;
	}
	
	
	
	public static double getMaximumReasonableRedirectAngle()
	{
		return maximumReasonableRedirectAngle;
	}
	
	
	
	public static double getChipKickCheckDistance()
	{
		return chipKickCheckDistance;
	}
	
	
	
	public static boolean isInterceptorEnabled()
	{
		return isInterceptorEnabled;
	}
	
}
