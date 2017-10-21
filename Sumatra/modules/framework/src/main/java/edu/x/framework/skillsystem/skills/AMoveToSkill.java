/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;

import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.shapes.path.IPath;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.EPathDriver;
import edu.x.framework.skillsystem.driver.HermiteSplinePathDriver;
import edu.x.framework.skillsystem.driver.IPathDriver;
import edu.x.framework.skillsystem.driver.LongPathDriver;
import edu.x.framework.skillsystem.driver.MixedPathDriver;
import edu.x.framework.skillsystem.driver.PathPointDriver;



public abstract class AMoveToSkill extends AMoveSkill
{
	@Configurable
	private static EPathDriver	defaultPathDriver	= EPathDriver.MIXED_SPLINE_POS;
	
	@Configurable
	private static EMoveToType	moveToType			= EMoveToType.DEFAULT;
	
	private enum EMoveToType
	{
		DEFAULT,
		TRAJECTORY,
		V2
	}
	
	
	
	protected AMoveToSkill(final ESkill skill)
	{
		super(skill);
	}
	
	
	
	public static AMoveToSkill createMoveToSkill()
	{
		switch (moveToType)
		{
			case DEFAULT:
				return new MoveToSkill();
			case TRAJECTORY:
				return new MoveToTrajSkill();
			case V2:
				return new MoveToV2Skill();
			default:
				throw new IllegalStateException();
		}
	}
	
	
	protected final IPathDriver getPathDriver(final IPath path)
	{
		switch (defaultPathDriver)
		{
			case HERMITE_SPLINE:
				return (new HermiteSplinePathDriver(getTBot(), path));
			case MIXED_SPLINE_POS:
				return (new MixedPathDriver(new HermiteSplinePathDriver(getTBot(), path),
						new PathPointDriver(path, getMoveCon()), getMoveCon().getDestination()));
			case MIXED_LONG_POS:
				return (new MixedPathDriver(new LongPathDriver(getTBot(), path),
						new PathPointDriver(path, getMoveCon()), getMoveCon().getDestination()));
			case PATH_POINT:
				return (new PathPointDriver(path, getMoveCon()));
			case LONG:
				return (new LongPathDriver(getTBot(), path));
			default:
				throw new IllegalStateException();
		}
	}
}
