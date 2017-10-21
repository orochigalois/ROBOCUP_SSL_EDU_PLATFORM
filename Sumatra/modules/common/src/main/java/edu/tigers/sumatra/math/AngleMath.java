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



public final class AngleMath
{
	
	public static final double		PI								= Math.PI;
	
	public static final double		PI_TWO						= Math.PI * 2;
	
	public static final double		PI_HALF						= Math.PI / 2.0;
	
	public static final double		PI_QUART						= Math.PI / 4.0;
	
	public static final double		PI_SQR						= Math.PI * Math.PI;
	
	public static final double		PI_INV						= 1.0 / Math.PI;
	
	public static final double		PI_TWO_INV					= 1.0 / (Math.PI * 2);
																			
	private static final double	DEG_RAD_FACTOR				= 180;
	private static final double	HACKY_MAGIC_NUMBER		= 0.000001;
	
	public static final double		DEG_TO_RAD					= PI / DEG_RAD_FACTOR;
	
	public static final double		RAD_TO_DEG					= DEG_RAD_FACTOR / PI;
																			
	private static final double	ANGLE_EQUAL_TOLERANCE	= 0.01;
																			
																			
	private AngleMath()
	{
	}
	
	
	
	public static double normalizeAngle(final double angle)
	{
		// Don't call this a hack! It's numeric!
		return (angle - (Math.round((angle / (PI_TWO)) - HACKY_MAGIC_NUMBER) * PI_TWO));
	}
	
	
	
	public static double difference(final double a1, final double a2)
	{
		return normalizeAngle(normalizeAngle(a1) - normalizeAngle(a2));
	}
	
	
	
	public static boolean isEqual(final double a1, final double a2, final double tolerance)
	{
		return Math.abs(difference(a1, a2)) < tolerance;
	}
	
	
	
	public static boolean isEqual(final double a1, final double a2)
	{
		return Math.abs(difference(a1, a2)) < ANGLE_EQUAL_TOLERANCE;
	}
	
	
	
	public static double getShortestRotation(final double angle1, final double angle2)
	{
		double rotateDist = 0;
		
		rotateDist = angle2 - angle1;
		if (rotateDist < -AngleMath.PI)
		{
			rotateDist = AngleMath.PI_TWO + rotateDist;
		}
		if (rotateDist > AngleMath.PI)
		{
			rotateDist -= AngleMath.PI_TWO;
		}
		return rotateDist;
	}
	
	
	
	public static double sin(final double number)
	{
		return Math.sin(number);
	}
	
	
	
	public static double cos(final double number)
	{
		return Math.cos(number);
	}
	
	
	
	public static double tan(final double number)
	{
		return Math.tan(number);
	}
	
	
	
	public static double acos(final double number)
	{
		return Math.acos(number);
	}
	
	
	
	public static double asin(final double number)
	{
		return Math.asin(number);
	}
	
	
	
	public static double deg2rad(final double deg)
	{
		return DEG_TO_RAD * deg;
	}
	
	
	
	public static double rad2deg(final double rad)
	{
		return RAD_TO_DEG * rad;
	}
	
	
	
	public static double tanh(final double rad)
	{
		return Math.tanh(rad);
	}
}
