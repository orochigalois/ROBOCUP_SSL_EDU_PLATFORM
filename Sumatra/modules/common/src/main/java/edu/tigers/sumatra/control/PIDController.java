/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.control;


public class PIDController
{
	private double		p;
	private double		i;
	private double		d;
	private double		maximumOutput	= Double.MAX_VALUE;
	private double		minimumOutput	= -Double.MAX_VALUE;
	private double		maximumInput	= 0.0;
	private double		minimumInput	= 0.0;
	private boolean	continuous		= false;
	private double		prevError		= 0.0;
	private double		totalError		= 0.0;
	private double		setpoint			= 0.0;
	private double		error				= 0.0;
	private double		result			= 0.0;
	private double		input				= 0.0;
												
												
	
	public PIDController(final double Kp, final double Ki, final double Kd)
	{
		p = Kp;
		i = Ki;
		d = Kd;
	}
	
	
	
	public PIDController(final double Kp, final double Ki, final double Kd, final boolean continuous)
	{
		p = Kp;
		i = Ki;
		d = Kd;
		this.continuous = continuous;
	}
	
	
	
	public void update(final double input)
	{
		this.input = input;
		
		// Calculate the error signal
		error = setpoint - input;
		
		// If continuous is set to true allow wrap around
		if (continuous)
		{
			if (Math.abs(error) > ((maximumInput - minimumInput) / 2.0))
			{
				if (error > 0)
				{
					error = (error - maximumInput) + minimumInput;
				} else
				{
					error = (error +
							maximumInput) - minimumInput;
				}
			}
		}
		
		
		if ((((totalError + error) * i) < maximumOutput) &&
				(((totalError + error) * i) > minimumOutput))
		{
			totalError += error;
		}
		
		// Perform the primary PID calculation
		result = ((p * error) + (i * totalError) + (d * (error - prevError)));
		
		// Set the current error to the previous error for the next cycle
		prevError = error;
		
		// Make sure the final result is within bounds
		if (result > maximumOutput)
		{
			result = maximumOutput;
		} else if (result < minimumOutput)
		{
			result = minimumOutput;
		}
	}
	
	
	
	public double getP()
	{
		return p;
	}
	
	
	
	public double getI()
	{
		return i;
	}
	
	
	
	public double getD()
	{
		return d;
	}
	
	
	
	public void setContinuous(final boolean continuous)
	{
		this.continuous = continuous;
	}
	
	
	
	public void setContinuous()
	{
		this.setContinuous(true);
	}
	
	
	
	public void setInputRange(final double minimumInput, final double maximumInput)
	{
		this.minimumInput = minimumInput;
		this.maximumInput = maximumInput;
		setSetpoint(setpoint);
	}
	
	
	
	public void setOutputRange(final double minimumOutput, final double maximumOutput)
	{
		this.minimumOutput = minimumOutput;
		this.maximumOutput = maximumOutput;
	}
	
	
	
	public void setSetpoint(final double setpoint)
	{
		if (maximumInput > minimumInput)
		{
			if (setpoint > maximumInput)
			{
				this.setpoint = maximumInput;
			} else if (setpoint < minimumInput)
			{
				this.setpoint = minimumInput;
			} else
			{
				this.setpoint = setpoint;
			}
		} else
		{
			this.setpoint = setpoint;
		}
	}
	
	
	
	public double getSetpoint()
	{
		return setpoint;
	}
	
	
	
	public double getError()
	{
		return error;
	}
	
	
	
	public double getResult()
	{
		return result;
	}
	
	
	
	public void reset()
	{
		prevError = 0;
		totalError = 0;
		result = 0;
	}
	
	
	
	public final double getInput()
	{
		return input;
	}
	
	
	
	public void setP(final double p)
	{
		this.p = p;
	}
	
	
	
	public void setI(final double i)
	{
		this.i = i;
	}
	
	
	
	public void setD(final double d)
	{
		this.d = d;
	}
}