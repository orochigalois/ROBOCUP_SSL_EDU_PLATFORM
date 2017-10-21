/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.sim;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.math.IVector2;



public class SumatraBotPair
{
	private SumatraBot	botOne;
	private SumatraBot	botTwo;
	
	
	
	public SumatraBotPair(final SumatraBot botOne, final SumatraBot botTwo)
	{
		this.botOne = botOne;
		this.botTwo = botTwo;
	}
	
	
	
	public List<SumatraBot> getBotPair()
	{
		List<SumatraBot> botPair = new ArrayList<SumatraBot>();
		
		botPair.add(botOne);
		botPair.add(botTwo);
		
		return botPair;
	}
	
	
	
	public IVector2 getVectorBetweenBots()
	{
		IVector2 returnVector = botOne.getPos().getXYVector();
		
		returnVector = returnVector.subtractNew(botTwo.getPos().getXYVector());
		
		return returnVector;
	}
	
}
