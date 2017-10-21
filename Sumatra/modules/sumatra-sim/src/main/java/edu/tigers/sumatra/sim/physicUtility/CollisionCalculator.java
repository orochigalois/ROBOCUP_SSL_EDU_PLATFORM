/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.sim.physicUtility;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.sim.SumatraBot;
import edu.tigers.sumatra.sim.SumatraBotPair;



public class CollisionCalculator
{
	private List<Double>		masses				= new ArrayList<>();
	private List<Vector3>	velocitiesBegin	= new ArrayList<>();
	private List<Vector3>	velocitiesResult	= new ArrayList<>();
	
	
	// private final double collisionNumber = 0;
	
	
	
	public CollisionCalculator(final SumatraBotPair collidingBots)
	{
		List<SumatraBot> botPair = collidingBots.getBotPair();
		
		masses.add(botPair.get(0).getMass());
		masses.add(botPair.get(1).getMass());
		
		velocitiesBegin.add(new Vector3(botPair.get(0).getVel()));
		velocitiesBegin.add(new Vector3(botPair.get(1).getVel()));
		
		calculateCollisionResult();
	}
	
	
	
	private void calculateCollisionResult()
	{
		for (int i = 0; i < masses.size(); i++)
		{
			Vector3 resultingVelocity = velocitiesBegin.get(0).multiplyNew(masses.get(0));
			resultingVelocity.add(velocitiesBegin.get(1).multiplyNew(masses.get(1)));
			resultingVelocity.multiply(1 / (masses.get(0) + masses.get(1)));
			
			velocitiesResult.add(resultingVelocity);
		}
	}
	
	
	
	public List<Vector3> getResultVelocities()
	{
		return velocitiesResult;
	}
}
