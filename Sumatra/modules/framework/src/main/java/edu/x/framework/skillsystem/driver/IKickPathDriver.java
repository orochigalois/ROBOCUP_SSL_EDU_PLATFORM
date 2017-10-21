/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.driver;

import edu.tigers.sumatra.math.IVector2;
import edu.x.framework.skillsystem.skills.KickSkill.EMoveMode;



public interface IKickPathDriver extends IPathDriver
{
	
	boolean isEnableDribbler();
	
	
	
	boolean armKicker();
	
	
	
	default void setShootSpeed(final double shootSpeed)
	{
	}
	
	
	
	default void setMoveMode(final EMoveMode moveMode)
	{
	}
	
	
	
	default void setRoleReady4Kick(final boolean ready4Kick)
	{
	}
	
	
	
	default boolean isSkillReady4Kick()
	{
		return true;
	}
	
	
	
	default void setDestForAvoidingOpponent(final IVector2 dest)
	{
	}
	
	
	
	default void unsetDestForAvoidingOpponent()
	{
	}
	
	
	
	default void setProtectPos(final IVector2 pos)
	{
	}
}
