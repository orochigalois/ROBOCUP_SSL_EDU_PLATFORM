/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;
import com.github.g3force.instanceables.InstanceableParameter;

import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.x.framework.skillsystem.skills.ASkill;
import edu.x.framework.skillsystem.skills.BlockSkill;
import edu.x.framework.skillsystem.skills.BotSkillWrapperSkill;
import edu.x.framework.skillsystem.skills.CatchSkill;
import edu.x.framework.skillsystem.skills.DelayedKickSkill;
import edu.x.framework.skillsystem.skills.IdleSkill;
import edu.x.framework.skillsystem.skills.InterceptionSkill;
import edu.x.framework.skillsystem.skills.KickSkill;
import edu.x.framework.skillsystem.skills.KickSkill.EKickMode;
import edu.x.framework.skillsystem.skills.KickSkill.EMoveMode;
import edu.x.framework.skillsystem.skills.KickTestSkill;
import edu.x.framework.skillsystem.skills.MoveBangBangSkill;
import edu.x.framework.skillsystem.skills.MoveToSkill;
import edu.x.framework.skillsystem.skills.MoveToTrajSkill;
import edu.x.framework.skillsystem.skills.MoveToV2Skill;
import edu.x.framework.skillsystem.skills.PenaltyKeeperSkill;
import edu.x.framework.skillsystem.skills.PenaltyShootSkill;
import edu.x.framework.skillsystem.skills.PenaltyShootSkill.ERotateDirection;
import edu.x.framework.skillsystem.skills.PenaltyShootSkillNew;
import edu.x.framework.skillsystem.skills.PenaltyShootSkillNew.ERotateTrajDirection;
import edu.x.framework.skillsystem.skills.PullBackSkillV2;
import edu.x.framework.skillsystem.skills.PullBallPathSkill;
import edu.x.framework.skillsystem.skills.PushKickTestSkill;
import edu.x.framework.skillsystem.skills.ReceiverSkill;
import edu.x.framework.skillsystem.skills.ReceiverSkill.EReceiverMode;
import edu.x.framework.skillsystem.skills.RedirectSkill;
import edu.x.framework.skillsystem.skills.StraightMoveSkill;
import edu.x.framework.skillsystem.skills.test.CircleSkill;
import edu.x.framework.skillsystem.skills.test.FuzzySkill;
import edu.x.framework.skillsystem.skills.test.LatencyTestSkill;
import edu.x.framework.skillsystem.skills.test.MoveEvaluateSkill;
import edu.x.framework.skillsystem.skills.test.MoveTestSkill;
import edu.x.framework.skillsystem.skills.test.PosTest2Skill;
import edu.x.framework.skillsystem.skills.test.PosTestSkill;
import edu.x.framework.skillsystem.skills.test.PosTrajTestSkill;
import edu.x.framework.skillsystem.skills.test.PositionSkill;
import edu.x.framework.skillsystem.skills.test.SinWSkill;



public enum ESkill implements IInstanceableEnum
{
	
	POSITION(new InstanceableClass(PositionSkill.class, new InstanceableParameter(IVector2.class, "destination", "0,0"),
			new InstanceableParameter(Double.TYPE, "orientation", "0"))),
	
	
	KICK(new InstanceableClass(KickSkill.class,
			new InstanceableParameter(DynamicPosition.class, "receiver", "4050,0"),
			new InstanceableParameter(EKickMode.class, "kickMode", "MAX"),
			new InstanceableParameter(EKickerDevice.class, "device", "STRAIGHT"),
			new InstanceableParameter(EMoveMode.class, "moveMode", "NORMAL"),
			new InstanceableParameter(Double.TYPE, "kickSpeed", "6"))),
	
	PUSH_KICK_TEST(new InstanceableClass(PushKickTestSkill.class,
			new InstanceableParameter(DynamicPosition.class, "receiver", "4050,0"))),
	
	REDIRECT(new InstanceableClass(RedirectSkill.class, new InstanceableParameter(DynamicPosition.class, "target",
			"3205,0"))),
	
	DELAYED_KICK(new InstanceableClass(DelayedKickSkill.class, new InstanceableParameter(IVector2.class,
			"target",
			"3205,0"))),
	
	CATCH(new InstanceableClass(CatchSkill.class)),
	
	KICK_TEST(new InstanceableClass(KickTestSkill.class,
			new InstanceableParameter(DynamicPosition.class, "target", "0,0"),
			new InstanceableParameter(Double.TYPE, "kickSpeed", "2"))),
	
	
	STRAIGHT_MOVE(new InstanceableClass(StraightMoveSkill.class, new InstanceableParameter(Integer.TYPE, "Distance",
			"1000"), new InstanceableParameter(Double.TYPE, "Angle", "0.0"))),
	
	
	SIN_W(new InstanceableClass(SinWSkill.class)),
	
	
	BOT_SKILL_WRAPPER(new InstanceableClass(BotSkillWrapperSkill.class)),
	
	
	BLOCK(new InstanceableClass(BlockSkill.class, new InstanceableParameter(Double.TYPE, "distToGoal", "500"))),
	
	INTERCEPTION(new InstanceableClass(InterceptionSkill.class)),
	
	RECEIVER(new InstanceableClass(ReceiverSkill.class,
			new InstanceableParameter(EReceiverMode.class, "mode", "STOP_DRIBBLER"))),
	
	
	PENALTY_SHOOT(new InstanceableClass(PenaltyShootSkill.class, new InstanceableParameter(ERotateDirection.class,
			"rotateDirection",
			"LEFT"))),
	
	PENALTY_SHOOT_NEW(new InstanceableClass(PenaltyShootSkillNew.class,
			new InstanceableParameter(ERotateTrajDirection.class, "rotateDirection", "LEFT"))),
	
	PENALTY_KEEPER(new InstanceableClass(PenaltyKeeperSkill.class, new InstanceableParameter(DynamicPosition.class,
			"shooterID",
			"3,YELLOW"))),
	
	CIRCLE(new InstanceableClass(CircleSkill.class, new InstanceableParameter(Double.TYPE, "duration [s]", "5"),
			new InstanceableParameter(Double.TYPE, "speed [m/s]", "1"))),
	
	FUZZY(new InstanceableClass(FuzzySkill.class, new InstanceableParameter(IVector2.class, "movePos", "0,0"))),
	
	// ************************
	// *** utility skills
	// ************************
	
	
	LATENCY_TEST(new InstanceableClass(LatencyTestSkill.class)),
	
	MOVE_TEST(new InstanceableClass(MoveTestSkill.class, new InstanceableParameter(MoveTestSkill.EMode.class, "mode",
			"FWD"), new InstanceableParameter(Boolean.TYPE, "loop", "false"))),
	
	MOVE_EVALUATE(new InstanceableClass(MoveEvaluateSkill.class,
			new InstanceableParameter(IVector2.class, "initPos", "1500,1000"),
			new InstanceableParameter(Double.TYPE, "orientation", "0"),
			new InstanceableParameter(Double.TYPE, "scale", "1000"),
			new InstanceableParameter(Double.TYPE, "startAngleDeg", "0"),
			new InstanceableParameter(Double.TYPE, "stopAngleDeg", "180"),
			new InstanceableParameter(Double.TYPE, "stepAngleDeg", "10"),
			new InstanceableParameter(Double.TYPE, "angleTurn", "0"),
			new InstanceableParameter(Integer.TYPE, "iterations", "1"),
			new InstanceableParameter(String.class, "logFileName", ""))),
	
	
	MOVE_BANG_BANG(new InstanceableClass(MoveBangBangSkill.class)),
	
	
	
	POS_TEST(new InstanceableClass(PosTestSkill.class,
			new InstanceableParameter(IVector3.class, "p1", "-2500,-1000,0"),
			new InstanceableParameter(IVector3.class, "p2", "-500,-1000,0"))),
	
	POS_TEST_2(new InstanceableClass(PosTest2Skill.class,
			new InstanceableParameter(IVector3.class, "p1", "-2500,-1000,0"),
			new InstanceableParameter(IVector3.class, "p2", "-500,-1000,0"),
			new InstanceableParameter(Double.TYPE, "tSwitch", "0.5"))),
	
	POS_TRAJ_TEST(new InstanceableClass(PosTrajTestSkill.class,
			new InstanceableParameter(IVector3.class, "p1", "-2500,-1000,0"),
			new InstanceableParameter(IVector3.class, "p2", "-500,-1000,0"))),
	
	// ************************
	// *** normal skills
	// ************************
	
	IDLE(new InstanceableClass(IdleSkill.class)),
	
	MOVE_TO(new InstanceableClass(MoveToSkill.class)),
	
	MOVE_TO_TRAJ(new InstanceableClass(MoveToTrajSkill.class)),
	
	MOVE_TO_V2(new InstanceableClass(MoveToV2Skill.class)),
	
	// ************************
	// *** to be checked
	// ************************
	
	
	PULL_BACKV2(new InstanceableClass(PullBackSkillV2.class,
			new InstanceableParameter(IVector2.class, "target", "0,0"))),
	
	
	PULL_BALL_PATH(new InstanceableClass(PullBallPathSkill.class)),;
	
	private final InstanceableClass clazz;
	
	
	
	private ESkill(final InstanceableClass clazz)
	{
		this.clazz = clazz;
	}
	
	
	
	@Override
	public final InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
}
