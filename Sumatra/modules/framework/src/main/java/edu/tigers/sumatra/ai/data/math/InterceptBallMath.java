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

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;
import org.apache.commons.math3.optim.univariate.UnivariatePointValuePair;

import edu.tigers.sumatra.math.SumatraMath;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class InterceptBallMath
{
	private static double	REL_PRECISION	= 1e-10;
	private static double	ABS_PRECISION	= 1e-4;
	
	private static double	ABORT_TIMER		= 100000;
	
	
	
	public static double getBallInterceptionTime(final TrackedBall ball, final ITimeToBallCalc timeToBallCalc)
	{
		double startTime = System.currentTimeMillis();
		
		UnivariateFunction func = new UnivariateFunction()
		{
			@Override
			public double value(final double tBall)
			{
				if ((System.currentTimeMillis() - startTime) > ABORT_TIMER)
				{
					return Double.POSITIVE_INFINITY;
				}
				return tBall - timeToBallCalc.calculateBotTimeToBallAt(tBall);
			}
		};
		UnivariateFunction funcAbs = new UnivariateFunction()
		{
			@Override
			public double value(final double tBall)
			{
				return SumatraMath.abs(func.value(tBall));
			}
		};
		
		BrentOptimizer brentOptimizer = new BrentOptimizer(REL_PRECISION, ABS_PRECISION);
		
		// solve solution
		UnivariatePointValuePair solution = brentOptimizer.optimize(new MaxEval(80),
				new UnivariateObjectiveFunction(funcAbs),
				GoalType.MINIMIZE, new SearchInterval(0, SumatraMath.min(ball.getTimeByVel(0), 6)));
		
		for (int i = 0; (i < 3) && ((System.currentTimeMillis() - startTime) < ABORT_TIMER); i++)
		{
			// search for maximum between 0 and solution
			UnivariatePointValuePair max = brentOptimizer.optimize(new MaxEval(80), new UnivariateObjectiveFunction(func),
					GoalType.MAXIMIZE, new SearchInterval(0, solution.getPoint() + 0.1));
			
			// check for another solution between 0 and local maximum
			solution = brentOptimizer.optimize(new MaxEval(80),
					new UnivariateObjectiveFunction(funcAbs),
					GoalType.MINIMIZE, new SearchInterval(0, max.getPoint() + 0.1));
		}
		return solution.getPoint();
	}
	
	
	public interface ITimeToBallCalc
	{
		
		double calculateBotTimeToBallAt(double tBall);
	}
	
}
