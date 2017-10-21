/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.math;

import java.util.function.Function;

import org.apache.commons.math3.linear.RealVector;

import edu.tigers.sumatra.export.IJsonString;
import edu.tigers.sumatra.export.INumberListable;



public interface IVector extends IJsonString, INumberListable
{
	
	double x();
	
	
	
	double y();
	
	
	
	default double z()
	{
		return 0;
	}
	
	
	
	default double w()
	{
		return 0;
	}
	
	
	
	double get(int i);
	
	
	
	int getNumDimensions();
	
	
	
	IVector addNew(IVector vector);
	
	
	
	IVector subtractNew(IVector vector);
	
	
	
	IVector multiplyNew(IVector vector);
	
	
	
	IVector multiplyNew(double f);
	
	
	
	IVector normalizeNew();
	
	
	
	IVector absNew();
	
	
	
	IVector applyNew(Function<Double, Double> function);
	
	
	
	double getLength();
	
	
	
	double getLength2();
	
	
	
	boolean isZeroVector();
	
	
	
	boolean isFinite();
	
	
	
	boolean equals(IVector vec, double tolerance);
	
	
	
	String getSaveableString();
	
	
	
	double[] toArray();
	
	
	
	double[] toDoubleArray();
	
	
	
	IVector2 getXYVector();
	
	
	
	IVector3 getXYZVector();
	
	
	
	RealVector toRealVector();
}
