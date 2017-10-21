/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.WorldFrameFactory;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.WorldFrame;



public class ProperbilityMathTest
{
	
	private WorldFrame	wf			= null;
	private IVector2		vector	= null;
	private long			seed		= 45456;
	
	
	
	@Before
	public void setup()
	{
		WorldFrameFactory.setRandomSeed(seed);
		wf = WorldFrameFactory.createWorldFrame(0, 0);
	}
	
	
	
	@Test
	public void firstGetDirectShootScoreChanceTest()
	{
		
		vector = new Vector2(0, 0);
		
		double result = ProbabilityMath.getDirectShootScoreChance(wf, vector, false);
		Assert.assertEquals(0.9184842959915077, result, 0);
	}
	
	
	
	@Test
	public void secondGetDirectShootScoreChanceTest()
	{
		
		vector = new Vector2(-287 - Geometry.getBotRadius(), 44 - Geometry.getBotRadius());
		
		double result = ProbabilityMath.getDirectShootScoreChance(wf, vector, false);
		Assert.assertEquals(0.6792616747972737, result, 0);
		
	}
	
	
	
	@Test
	public void firstGetFoeScoreChanceWithDefenderTest()
	{
		vector = new Vector2(0, 0);
		
		double result = ProbabilityMath.getFoeScoreChanceWithDefender(wf, vector);
		
		Assert.assertEquals(0.9184842959915086, result, 0);
	}
	
	
	
	@Test
	public void secondGetFoeScoreChanceWithDefenderTest()
	{
		vector = new Vector2(3743 + Geometry.getBotRadius(), 665 + Geometry.getBotRadius());
		
		double result = ProbabilityMath.getFoeScoreChanceWithDefender(wf, vector);
		
		Assert.assertEquals(0, result, 0);
	}
	
	
	
	@Test
	public void firstGetDirectHitChanceTest()
	{
		vector = new Vector2(0, 0);
		IVector2 endVector1 = new Vector2(100, 100);
		IVector2 endVector2 = new Vector2(100, 200);
		
		double result = ProbabilityMath.getDirectHitChance(wf, vector, endVector1, endVector2, false);
		
		Assert.assertEquals(0.9375711076816111, result, 0);
	}
	
	
	
	@Test
	public void secondGetDirectHitChanceTest()
	{
		vector = new Vector2(0, 0);
		IVector2 endVector1 = new Vector2(1000, 1000);
		IVector2 endVector2 = new Vector2(900, 600);
		
		double result = ProbabilityMath.getDirectHitChance(wf, vector, endVector1, endVector2, false);
		
		Assert.assertEquals(0.3271777300376465, result, 0);
	}
}
