/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.neural;

import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.math.IVector3;



public class NeuralBallPredictionData implements INeuralPredicitonData
{
	private IVector3	pos;
	private IVector3	vel;
	private IVector3	acc;
	private CamBall	lastball;
	private long		timestamp;
	
	
	
	public void update(final IVector3 p, final IVector3 v, final IVector3 a, final CamBall cb, final long time)
	{
		pos = p;
		vel = v;
		acc = a;
		lastball = cb;
		timestamp = time;
	}
	
	
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public IVector3 getPos()
	{
		return pos;
	}
	
	
	
	public IVector3 getVel()
	{
		return vel;
	}
	
	
	
	public IVector3 getAcc()
	{
		return acc;
	}
	
	
	
	public CamBall getLastball()
	{
		return lastball;
	}
}
