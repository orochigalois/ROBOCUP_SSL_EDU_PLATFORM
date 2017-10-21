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

import java.util.List;
import java.util.Set;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.ShapeMap;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.x.framework.skillsystem.MovementCon;



public interface IPathDriver
{
	
	IVector3 getNextDestination(ITrackedBot bot, WorldFrame wFrame);
	
	
	
	IVector3 getNextVelocity(ITrackedBot bot, WorldFrame wFrame);
	
	
	
	IVector3 getNextLocalVelocity(final ITrackedBot bot, final WorldFrame wFrame, final double dt);
	
	
	
	void update(final ITrackedBot bot, ABot aBot, final WorldFrame wFrame);
	
	
	
	ShapeMap getShapes();
	
	
	
	Set<EBotSkill> getSupportedCommands();
	
	
	
	boolean isDone();
	
	
	
	EPathDriver getType();
	
	
	
	void setShapes(final EShapesLayer layer, final List<IDrawableShape> shapes);
	
	
	
	MovementCon getMoveCon();
	
	
	
	void setMoveCon(MovementCon moveCon);
}
