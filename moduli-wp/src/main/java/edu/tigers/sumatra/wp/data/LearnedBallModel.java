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

import org.apache.log4j.Logger;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.ml.model.ALearnedModel;



public class LearnedBallModel extends ALearnedModel
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(LearnedBallModel.class.getName());
	
	
	
	public LearnedBallModel(final String identifier)
	{
		super("ball", identifier);
		
	}
	
	
	@Override
	protected double[] getDefaultParams()
	{
		return new double[] { -3.7968075, 0.011605997, 1.04270024E-4, -7.2500393E-6, 9.995082E-4, -9.8052085E-5 };
	}
	
	
	
	public IVector2 getPosByTime(final IVector2 currentPos, final IVector2 currentVel, final double time)
	{
		return currentPos.addNew(currentVel.scaleToNew(getDistByTime(currentVel.getLength2(), time)));
	}
	
	
	
	public double getDistByTime(final double currentVel, final double time)
	{
		double tMax = getTimeByVel(currentVel, 0);
		double x = currentVel * 1000;
		double y = Math.min(time, tMax) * 1e3f;
		
		double result = p[0] + (p[1] * x) + (p[2] * y) + (p[3] * x * x) + (p[4] * x * y) + (p[5] * y * y);
		if (result < 0)
		{
			return 0;
		}
		return result;
	}
	
	
	
	public IVector2 getPosByVel(final IVector2 currentPos, final IVector2 currentVel, final double velocity)
	{
		return getPosByTime(currentPos, currentVel, getTimeByVel(currentVel.getLength2(), velocity));
	}
	
	
	
	public double getDistByVel(final double currentVel, final double velocity)
	{
		return getDistByTime(currentVel, getTimeByVel(currentVel, velocity));
	}
	
	
	
	public double getTimeByDist(final double currentVel, final double dist)
	{
		double x = currentVel * 1000;
		
		// see: http://www.wolframalpha.com/input/?i=solve+g%3Da%2Bb*x%2Bc*y%2Bd*x*x%2Be*x*y%2Bf*y*y+for+y
		final double t;
		if (p[5] != 0)
		{
			double breaket2 = (p[0] + (p[1] * x) + (p[3] * x * x)) - dist;
			double sqrt = SumatraMath.square(p[2] + (p[4] * x)) - (4 * p[5] * breaket2);
			if (sqrt < 0)
			{
				// numerical issue
				sqrt = 0;
			}
			double counter = SumatraMath.sqrt(sqrt) - p[2] - (p[4] * x);
			double denominator = 2 * p[5];
			double y = counter / denominator;
			if (y < 0)
			{
				counter = -SumatraMath.sqrt(sqrt) - p[2] - (p[4] * x);
				t = counter / denominator;
			} else
			{
				t = y;
			}
		} else
		{
			// this part should usually not be called, but just in case...
			double counter = (-p[0] - (p[1] * x) - (p[3] * x * x)) + dist;
			double denominator = (p[2] + (p[4] * x));
			if (denominator == 0)
			{
				return 0;
			}
			t = counter / denominator;
		}
		return Math.max(0, t / 1000.0);
	}
	
	
	
	public double getTimeByVel(final double currentVel, final double velocity)
	{
		// d/dt f(vel,t) = newVel -> transpose to t
		return Math.max(0, ((velocity - p[2] - (p[4] * currentVel * 1000)) / (2.0 * p[5])) / 1000.0);
		// return (getVel().getLength2() - velocity) / (0.0.02f * 9.81);
	}
	
	
	
	public double getVelByDist(final double currentVel, final double dist)
	{
		return getVelByTime(currentVel, getTimeByDist(currentVel, dist));
	}
	
	
	
	public double getVelByTime(final double currentVel, final double time)
	{
		double x = currentVel * 1000;
		double y = time * 1e3f;
		// d/dt f(vel,t) = newvel
		double velocity = ((p[2] + (p[4] * x) + (2 * p[5] * y)));
		// double velocity = getVel().getLength2() - (time * (0.02f * 9.81));
		if (velocity < 0)
		{
			velocity = 0;
		}
		return velocity;
	}
	
	
	
	public double getVelForTime(final double endVel, final double time)
	{
		double t = time * 1000;
		// f'(v,t) = vd (note: vd is m/s)
		double v = (endVel - p[2] - (2 * p[5] * t)) / p[4];
		
		if (v < 0)
		{
			v = 0;
		}
		return v / 1000.0;
	}
	
	
	
	public double getVelForDist(final double dist, final double endVel)
	{
		
		double a = p[0], b = p[1], c = p[2], d = p[3], e = p[4], f = p[5], g = dist, w = endVel;
		double denominator = (e * e) - (4 * d * f);
		if ((denominator != 0) && (f != 0))
		{
			double sqrt = SumatraMath.square((2 * c * e) - (4 * b * f))
					- (4 * ((e * e) - (4 * d * f)) * (((-4 * a * f) + (c * c) + (4 * f * g)) - (w * w)));
			double rest = (2 * b * f) - (c * e);
			if (sqrt < 0)
			{
				sqrt = 0;
			}
			double v = ((-SumatraMath.sqrt(sqrt) / 2.0) + rest) / denominator;
			if (v < 0)
			{
				v = ((SumatraMath.sqrt(sqrt) / 2.0) + rest) / denominator;
			}
			return v / 1000.0;
		}
		throw new IllegalStateException("Oh, we get here? :/");
	}
	
	
	
	public double getAcc()
	{
		return 2000 * p[5];
	}
	
	
	@Override
	protected void onNewParameters()
	{
	}
}
