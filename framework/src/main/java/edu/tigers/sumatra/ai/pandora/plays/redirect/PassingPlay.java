/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



public class PassingPlay extends ARedirectPlay
{
	@Configurable(comment = "Center of the circle")
	private static IVector2	center	= new Vector2(0, 0);
	
	
	
	public PassingPlay()
	{
		super(EPlay.PASSING);
	}
	
	
	@Override
	protected List<IVector2> getFormation()
	{
		double angleStep = AngleMath.PI_TWO / getRoles().size();
		double initialAngle = AngleMath.PI_QUART;
		
		List<IVector2> destinations = new ArrayList<IVector2>();
		for (int i = 0; i < getRoles().size(); i++)
		{
			destinations.add(center.addNew(new Vector2(initialAngle + (angleStep * i)).scaleTo(getDistance())));
		}
		return destinations;
	}
	
	
	@Override
	protected int getReceiverTarget(final int roleIdx)
	{
		return (roleIdx + 1) % getRoles().size();
	}
	
	
	@Override
	protected void getReceiveModes(final Map<ARole, EReceiveMode> modes)
	{
		for (ARole role : getRoles())
		{
			modes.put(role, EReceiveMode.RECEIVE);
		}
	}
}
