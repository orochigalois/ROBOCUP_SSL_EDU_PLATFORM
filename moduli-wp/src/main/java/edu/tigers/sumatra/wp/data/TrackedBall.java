/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BallID;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3;



@Persistent
public class TrackedBall extends ATrackedObject
{
	private final BallID		id				= new BallID();
	
	
	private final IVector2	pos;
	
	private final IVector2	vel;
	
	private final IVector3	acc;
	
	private final double		height;
	private final double		zVel;
	
	private double				confidence	= 0;
	private boolean			onCam			= true;
	
	
	@SuppressWarnings("unused")
	private TrackedBall()
	{
		super();
		pos = AVector2.ZERO_VECTOR;
		vel = AVector2.ZERO_VECTOR;
		acc = AVector3.ZERO_VECTOR;
		height = 0;
		zVel = 0;
	}
	
	
	
	public TrackedBall(final TrackedBall original)
	{
		super(original);
		pos = new Vector2(original.pos);
		vel = new Vector2(original.vel);
		acc = new Vector3(original.acc);
		height = original.height;
		zVel = original.zVel;
	}
	
	
	
	public TrackedBall(final IVector2 pos, final double height, final IVector2 vel, final double zVel,
			final IVector3 acc)
	{
		super();
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
		this.height = height;
		this.zVel = zVel;
	}
	
	
	
	public TrackedBall(final IVector3 pos, final IVector3 vel, final IVector3 acc)
	{
		this.pos = pos.getXYVector();
		this.vel = vel.getXYVector();
		this.acc = acc;
		height = pos.z();
		zVel = vel.z();
	}
	
	
	
	public static TrackedBall defaultInstance()
	{
		return new TrackedBall(AVector2.ZERO_VECTOR, 0, AVector2.ZERO_VECTOR, 0, AVector3.ZERO_VECTOR);
	}
	
	
	
	public TrackedBall mirrorNew()
	{
		return new TrackedBall(pos.multiplyNew(-1), height, vel.multiplyNew(-1), zVel, new Vector3(acc.getXYVector()
				.multiplyNew(-1), acc.z()));
	}
	
	
	
	public IVector2 getPosByTime(final double time)
	{
		return Geometry.getBallModel().getPosByTime(getPos(), getVel(), time);
	}
	
	
	
	public IVector2 getPosByVel(final double velocity)
	{
		return Geometry.getBallModel().getPosByVel(getPos(), getVel(), velocity);
	}
	
	
	
	public double getTimeByPos(final IVector2 pos)
	{
		return Geometry.getBallModel().getTimeByDist(getVel().getLength2(), GeoMath.distancePP(pos, getPos()));
	}
	
	
	
	public double getTimeByVel(final double velocity)
	{
		return Geometry.getBallModel().getTimeByVel(getVel().getLength2(), velocity);
	}
	
	
	
	public double getVelByPos(final IVector2 pos)
	{
		return Geometry.getBallModel().getVelByDist(getVel().getLength2(), GeoMath.distancePP(pos, getPos()));
	}
	
	
	
	public double getVelByTime(final double time)
	{
		return Geometry.getBallModel().getVelByTime(getVel().getLength2(), time);
	}
	
	
	@Override
	public BallID getBotId()
	{
		return id;
	}
	
	
	@Override
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	@Override
	public IVector2 getVel()
	{
		return vel;
	}
	
	
	@Override
	public IVector2 getAcc()
	{
		return acc.getXYVector();
	}
	
	
	
	public IVector3 getPos3()
	{
		return new Vector3(pos, height);
	}
	
	
	
	public IVector3 getVel3()
	{
		return new Vector3(vel, zVel);
	}
	
	
	
	public IVector3 getAcc3()
	{
		return acc;
	}
	
	
	
	public double getConfidence()
	{
		return confidence;
	}
	
	
	
	public void setConfidence(final double confidence)
	{
		this.confidence = confidence;
	}
	
	
	
	public boolean isOnCam()
	{
		return onCam;
	}
	
	
	
	public void setOnCam(final boolean onCam)
	{
		this.onCam = onCam;
	}
}
