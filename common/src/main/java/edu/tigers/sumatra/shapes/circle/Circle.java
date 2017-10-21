/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.shapes.circle;

import java.util.List;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.MathException;
import edu.tigers.sumatra.math.Vector2;



@Persistent(version = 1)
public class Circle extends ACircle
{
	
	private IVector2	center;
	
	
	private double		radius;
	
	
	protected Circle()
	{
		this(AVector2.ZERO_VECTOR, 1);
	}
	
	
	
	public Circle(final IVector2 center, final double radius)
	{
		if (radius <= 0)
		{
			throw new IllegalArgumentException("Radius of a circle must not be smaller than zero!");
		}
		this.center = new Vector2(center);
		this.radius = radius;
	}
	
	
	
	public Circle(final ICircle c)
	{
		this(c.center(), c.radius());
	}
	
	
	
	public static Circle circleFrom3Points(final IVector2 P1, final IVector2 P2, final IVector2 P3)
			throws MathException
	{
		RealMatrix A = new Array2DRowRealMatrix(new double[][] { { 1, P1.x(), P1.y() }, { 1, P2.x(), P2.y() },
				{ 1, P3.x(), P3.y() } }, false);
		
		DecompositionSolver solver = new LUDecomposition(A).getSolver();
		
		RealVector B = new ArrayRealVector(new double[] { (P1.x() * P1.x()) + (P1.y() * P1.y()),
				(P2.x() * P2.x()) + (P2.y() * P2.y()), (P3.x() * P3.x()) + (P3.y() * P3.y()) }, false);
		
		RealVector solution;
		try
		{
			solution = solver.solve(B);
		} catch (SingularMatrixException err)
		{
			throw new MathException("Infinite circle => line");
		}
		
		RealVector center = solution.getSubVector(1, 2).mapMultiplyToSelf(0.5);
		
		double sq = center.ebeMultiply(center).getL1Norm() + solution.getEntry(0);
		double radius = Math.sqrt(sq);
		
		return new Circle(new Vector2(center), radius);
	}
	
	
	
	public static Circle circleFromNPoints(final List<IVector2> P)
			throws MathException
	{
		RealMatrix A = new Array2DRowRealMatrix(P.size(), 3);
		RealVector B = new ArrayRealVector(P.size());
		for (int i = 0; i < P.size(); i++)
		{
			IVector2 p = P.get(i);
			A.setEntry(i, 0, 1);
			A.setEntry(i, 1, p.x());
			A.setEntry(i, 2, p.y());
			B.setEntry(i, (p.x() * p.x()) + (p.y() * p.y()));
		}
		
		DecompositionSolver solver = new QRDecomposition(A).getSolver();
		
		RealVector solution;
		try
		{
			solution = solver.solve(B);
		} catch (SingularMatrixException err)
		{
			throw new MathException("Infinite circle => line");
		}
		
		RealVector center = solution.getSubVector(1, 2).mapMultiplyToSelf(0.5);
		
		double sq = center.ebeMultiply(center).getL1Norm() + solution.getEntry(0);
		double radius = Math.sqrt(sq);
		
		return new Circle(new Vector2(center), radius);
	}
	
	
	
	public static Circle getNewCircle(final IVector2 center, final double radius)
	{
		return new Circle(center, radius);
	}
	
	
	@Override
	public double radius()
	{
		return radius;
	}
	
	
	@Override
	public IVector2 center()
	{
		return center;
	}
	
	
	@Override
	public String toString()
	{
		return "Center = " + center().toString() + "\nRadius = " + radius();
	}
}
