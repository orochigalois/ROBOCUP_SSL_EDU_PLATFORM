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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



@Persistent(version = 1)
public class BotAiInformation
{
	private String		play				= "";
	private String		role				= "";
	private String		skill				= "";
	private String		roleState		= "";
	private String		skillState		= "";
	private String		skillDriver		= "";
	private boolean	ballContact		= false;
	private double		battery			= 0;
	private double		kickerCharge	= 0;
	private IVector2	vel				= Vector2.ZERO_VECTOR;
	private IVector2	pos				= Vector2.ZERO_VECTOR;
	private String		brokenFeatures	= "";
	private double		maxVel			= 0;
	private String		limits			= "";
	private double		dribbleSpeed	= 0;
	private double		kickSpeed		= 0;
	private String		device			= "";
	
	
	
	public BotAiInformation()
	{
		
	}
	
	
	
	public final String getPlay()
	{
		return play;
	}
	
	
	
	public final void setPlay(final String play)
	{
		this.play = play;
	}
	
	
	
	public final String getRole()
	{
		return role;
	}
	
	
	
	public final void setRole(final String role)
	{
		this.role = role;
	}
	
	
	
	public final String getSkill()
	{
		return skill;
	}
	
	
	
	public final void setSkill(final String skill)
	{
		this.skill = skill;
	}
	
	
	
	public final boolean isBallContact()
	{
		return ballContact;
	}
	
	
	
	public final void setBallContact(final boolean ballContact)
	{
		this.ballContact = ballContact;
	}
	
	
	
	public final double getBattery()
	{
		return battery;
	}
	
	
	
	public final void setBattery(final double battery)
	{
		this.battery = battery;
	}
	
	
	
	public final double getKickerCharge()
	{
		return kickerCharge;
	}
	
	
	
	public final void setKickerCharge(final double kickerCharge)
	{
		this.kickerCharge = kickerCharge;
	}
	
	
	
	public final String getRoleState()
	{
		return roleState;
	}
	
	
	
	public final void setRoleState(final String roleState)
	{
		this.roleState = roleState;
	}
	
	
	
	public final IVector2 getVel()
	{
		return vel;
	}
	
	
	
	public final void setVel(final IVector2 vel)
	{
		// avoid numerical problems for zero-vel
		this.vel = vel.addNew(new Vector2(1e-5, 1e-5));
	}
	
	
	
	public final IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public final void setPos(final IVector2 pos)
	{
		this.pos = pos;
	}
	
	
	
	public final String getBrokenFeatures()
	{
		return brokenFeatures;
	}
	
	
	
	public final void setBrokenFeatures(final String brokenFeatures)
	{
		this.brokenFeatures = brokenFeatures;
	}
	
	
	
	public final double getMaxVel()
	{
		return maxVel;
	}
	
	
	
	public final void setMaxVel(final double maxVel)
	{
		this.maxVel = maxVel;
	}
	
	
	
	public final String getSkillState()
	{
		return skillState;
	}
	
	
	
	public final void setSkillState(final String skillState)
	{
		this.skillState = skillState;
	}
	
	
	
	public final String getSkillDriver()
	{
		return skillDriver;
	}
	
	
	
	public final void setSkillDriver(final String skillDriver)
	{
		this.skillDriver = skillDriver;
	}
	
	
	
	public String getLimits()
	{
		return limits;
	}
	
	
	
	public void setLimits(final String limits)
	{
		this.limits = limits;
	}
	
	
	
	public double getDribbleSpeed()
	{
		return dribbleSpeed;
	}
	
	
	
	public void setDribbleSpeed(final double dribbleSpeed)
	{
		this.dribbleSpeed = dribbleSpeed;
	}
	
	
	
	public double getKickSpeed()
	{
		return kickSpeed;
	}
	
	
	
	public void setKickSpeed(final double kickSpeed)
	{
		this.kickSpeed = kickSpeed;
	}
	
	
	
	public String getDevice()
	{
		return device;
	}
	
	
	
	public void setDevice(final String device)
	{
		this.device = device;
	}
}
