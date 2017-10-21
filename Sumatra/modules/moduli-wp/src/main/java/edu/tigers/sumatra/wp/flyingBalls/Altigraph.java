/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.flyingBalls;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class Altigraph
{
	@SuppressWarnings("unused")
	private static final Logger	log					= Logger.getLogger(Altigraph.class.getName());
	
	private static final int		MAX_NUMBER_FLYS	= 3;
	
	
	private final List<Fly>			flys					= new CopyOnWriteArrayList<Fly>();
	private boolean					isBallFlying		= false;
	private IVector2					currentBall;
	private Fly							fly;
	
	
	
	public Altigraph()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void addKickerZoneIdentified(final IVector2 roboPos, final double angle)
	{
		// create a new fly and add to fly-list
		final Fly fly = new Fly(roboPos, angle);
		flys.add(fly);
		
		// are there too much flys?
		if (flys.size() > MAX_NUMBER_FLYS)
		{
			// remove first (the oldest fly)
			flys.remove(0);
		}
	}
	
	
	
	public void addCamFrame(final IVector2 ballPos, final int camID)
	{
		// if we current have no fly: skip
		if (flys.isEmpty())
		{
			isBallFlying = false;
			return;
		}
		
		// else append the ball
		currentBall = ballPos;
		
		// delete flys, to which the ball does not fit
		// for all flies try to add the ball
		// if it not fit, delete the fly
		for (Fly iteratorFly : flys)
		{
			// add the ball
			final boolean ok = iteratorFly.addNewBall(ballPos, camID);
			// if the ball wasn't correct in this fly, remember the fly for delete
			if (!ok)
			{
				flys.remove(iteratorFly);
			}
		}
		
		// if we current have no fly: skip
		if (flys.isEmpty())
		{
			isBallFlying = false;
			return;
		}
		// calculate the regressionParabel
		dedectAndCalulateFlyingBall();
	}
	
	
	
	private void dedectAndCalulateFlyingBall()
	{
		// if in the first fly are more than 4 balls
		// calculate the parabel
		fly = flys.get(0);
		
		if (fly.size() >= 4)
		{
			fly.calculateFly();
			
			isBallFlying = true;
			
			if (!fly.isAtLeastMinHighReached())
			{
				flys.remove(fly);
				fly = null;
				isBallFlying = false;
			}
			
		}
		// if not enough balls there, reset the fly-state
		else
		{
			isBallFlying = false;
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public boolean isBallFlying()
	{
		return isBallFlying;
	}
	
	
	
	public IVector3 getCorrectedFrame()
	{
		final IVector3 ball;
		
		if (!isBallFlying || (fly == null))
		{
			ball = new Vector3(currentBall.x(), currentBall.y(), 0.0);
		} else
		{
			ball = new Vector3(fly.getCurrentBallPosition().x(), fly.getCurrentBallPosition().y(),
					fly.getCurrentBallHeight());
		}
		return ball;
	}
	
	
	@Override
	public String toString()
	{
		final StringBuilder str = new StringBuilder();
		str.append("#################################################\n");
		
		for (int i = 0; i < flys.size(); i++)
		{
			str.append("###|> Fly ");
			str.append(i);
			str.append(": ##########################\n");
			str.append(flys.get(i).toString());
			str.append("Balls: ");
			str.append(flys.get(i).size());
			str.append("###Fly ");
			str.append(i);
			str.append(" <| ##########################\n");
		}
		
		str.append("#################################################\n");
		return str.toString();
	}
	
	
	
	public List<Fly> getFlys()
	{
		return flys;
	}
}
