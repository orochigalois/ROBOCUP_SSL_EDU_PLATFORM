/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus;

import edu.tigers.sumatra.ai.sisyphus.finder.FieldInformation;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;
import edu.x.framework.skillsystem.MovementCon;



public final class PathFinderInput
{
	private final BotID					botId;
	private final MovementCon			moveCon;
	
	private final FieldInformation	fieldInfo;
	
	
	
	public PathFinderInput(final BotID botId, final MovementCon moveCon)
	{
		this.botId = botId;
		this.moveCon = moveCon;
		fieldInfo = new FieldInformation(botId, moveCon);
	}
	
	
	
	public BotID getBotId()
	{
		return botId;
	}
	
	
	
	public double getDstOrient()
	{
		return moveCon.getTargetAngle();
	}
	
	
	
	public IVector2 getDestination()
	{
		return moveCon.getDestination();
	}
	
	
	
	public MovementCon getMoveCon()
	{
		return moveCon;
	}
	
	
	
	public FieldInformation getFieldInfo()
	{
		return fieldInfo;
	}
}