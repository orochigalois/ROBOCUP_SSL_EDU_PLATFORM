/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;



public abstract class ACalculator
{
	private static final Logger	log						= Logger.getLogger(ACalculator.class.getName());
	private boolean					active					= true;
																		
	private long						lastCalculationTime	= 0;
																		
	private ECalculator				type						= null;
																		
	private Exception					lastException			= null;
																		
																		
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public final void calculate(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
		if (active && ((type == null)
				|| (TimeUnit.NANOSECONDS.toMillis(
						baseAiFrame.getSimpleWorldFrame().getTimestamp() - lastCalculationTime) >= type.getTimeRateMs())))
		{
			try
			{
				doCalc(newTacticalField, baseAiFrame);
				lastException = null;
			} catch (Exception err)
			{
				if ((lastException == null) || ((err.getMessage() != null)
						&& !err.getMessage().equals(lastException.getMessage())))
				{
					log.error("Error in calculator " + getType().name(), err);
				}
				lastException = err;
				fallbackCalc(newTacticalField, baseAiFrame);
			}
			lastCalculationTime = baseAiFrame.getSimpleWorldFrame().getTimestamp();
		} else
		{
			fallbackCalc(newTacticalField, baseAiFrame);
		}
	}
	
	
	
	public abstract void doCalc(TacticalField newTacticalField, BaseAiFrame baseAiFrame);
	
	
	
	public void fallbackCalc(final TacticalField newTacticalField, final BaseAiFrame baseAiFrame)
	{
	}
	
	
	
	public void deinit()
	{
	}
	
	
	
	public void reset()
	{
		lastCalculationTime = -1;
	}
	
	
	
	public void setActive(final boolean active)
	{
		this.active = active;
	}
	
	
	
	public final ECalculator getType()
	{
		return type;
	}
	
	
	
	public final void setType(final ECalculator type)
	{
		this.type = type;
	}
}
