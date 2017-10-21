/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder.traj;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.tigers.sumatra.ai.sisyphus.finder.traj.obstacles.IObstacle;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.x.framework.skillsystem.MovementCon;



public class TrajPathFinderInput
{
	private MovementCon				moveCon					= new MovementCon();
	private List<IDrawableShape>	debugShapes				= new ArrayList<>();
	private boolean					debug						= false;
	
	private double						collisionStepSize		= 0.125;
	private double						obstacleMargin			= 50;
	
	private IVector2					pos						= Vector2.ZERO_VECTOR;
	private IVector2					vel						= Vector2.ZERO_VECTOR;
	private double						orientation;
	private double						aVel;
	private IVector2					dest						= Vector2.ZERO_VECTOR;
	private List<IObstacle>			obstacles				= new ArrayList<>(0);
	private final long				timestamp;
	private double						targetAngle;
	private Random						rnd;
	private boolean					fastStop					= false;
	private double						minTrajTime				= 1;
	private double						collisionLookahead	= 2;
	
	
	
	public TrajPathFinderInput(final long timestamp)
	{
		this.timestamp = timestamp;
		rnd = new Random(timestamp);
	}
	
	
	
	public TrajPathFinderInput(final TrajPathFinderInput input, final long timestamp)
	{
		this(timestamp);
		debug = input.debug;
		pos = input.pos;
		vel = input.vel;
		orientation = input.orientation;
		aVel = input.aVel;
		dest = input.dest;
		obstacles.addAll(input.obstacles);
		targetAngle = input.targetAngle;
		rnd = input.rnd;
		collisionStepSize = input.collisionStepSize;
		obstacleMargin = input.obstacleMargin;
		moveCon = input.getMoveCon();
		fastStop = input.isFastStop();
	}
	
	
	
	public final void setTrackedBot(final ITrackedBot tBot)
	{
		pos = tBot.getPos();
		orientation = tBot.getAngle();
		vel = tBot.getVel();
		aVel = tBot.getaVel();
	}
	
	
	
	public final IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public final void setPos(final IVector2 pos)
	{
		this.pos = pos;
	}
	
	
	
	public final IVector2 getVel()
	{
		return vel;
	}
	
	
	
	public final void setVel(final IVector2 vel)
	{
		this.vel = vel;
	}
	
	
	
	public final IVector2 getDest()
	{
		return dest;
	}
	
	
	
	public final void setDest(final IVector2 dest)
	{
		this.dest = dest;
	}
	
	
	
	public final List<IObstacle> getObstacles()
	{
		return obstacles;
	}
	
	
	
	public final void setObstacles(final List<IObstacle> obstacles)
	{
		this.obstacles = obstacles;
	}
	
	
	
	public final boolean isDebug()
	{
		return debug;
	}
	
	
	
	public final void setDebug(final boolean debug)
	{
		this.debug = debug;
	}
	
	
	
	public final List<IDrawableShape> getDebugShapes()
	{
		return debugShapes;
	}
	
	
	
	public final double getTargetAngle()
	{
		return targetAngle;
	}
	
	
	
	public final void setTargetAngle(final double orientation)
	{
		targetAngle = orientation;
	}
	
	
	
	public final double getOrientation()
	{
		return orientation;
	}
	
	
	
	public final void setOrientation(final double orientation)
	{
		this.orientation = orientation;
	}
	
	
	
	public final double getaVel()
	{
		return aVel;
	}
	
	
	
	public final void setaVel(final double aVel)
	{
		this.aVel = aVel;
	}
	
	
	
	public final void setDebugShapes(final List<IDrawableShape> debugShapes)
	{
		this.debugShapes = debugShapes;
	}
	
	
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public Random getRnd()
	{
		return rnd;
	}
	
	
	
	public void setRnd(final Random rnd)
	{
		this.rnd = rnd;
	}
	
	
	
	public double getCollisionStepSize()
	{
		return collisionStepSize;
	}
	
	
	
	public void setCollisionStepSize(final double collisionStepSize)
	{
		this.collisionStepSize = collisionStepSize;
	}
	
	
	
	public double getObstacleMargin()
	{
		return obstacleMargin;
	}
	
	
	
	public final void setObstacleMargin(final double obstacleMargin)
	{
		this.obstacleMargin = obstacleMargin;
	}
	
	
	
	public boolean isFastStop()
	{
		return fastStop;
	}
	
	
	
	public void setFastStop(final boolean fastStop)
	{
		this.fastStop = fastStop;
	}
	
	
	
	public MovementCon getMoveCon()
	{
		return moveCon;
	}
	
	
	
	public void setMoveCon(final MovementCon moveCon)
	{
		this.moveCon = moveCon;
	}
	
	
	
	public double getMinTrajTime()
	{
		return minTrajTime;
	}
	
	
	
	public void setMinTrajTime(final double minTrajTime)
	{
		this.minTrajTime = minTrajTime;
	}
	
	
	
	public double getCollisionLookahead()
	{
		return collisionLookahead;
	}
	
	
	
	public void setCollisionLookahead(final double collisionLookahead)
	{
		this.collisionLookahead = collisionLookahead;
	}
}
