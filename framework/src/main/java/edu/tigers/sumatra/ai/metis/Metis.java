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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.IConfigClient;
import com.github.g3force.configurable.IConfigObserver;
import com.github.g3force.instanceables.InstanceableClass.NotCreateableException;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.ETeamColor;



public class Metis implements IConfigObserver
{
	static
	{
		for (ECalculator ec : ECalculator.values())
		{
			ConfigRegistration.registerClass("metis", ec.getInstanceableClass().getImpl());
		}
		for (EPlay ec : EPlay.values())
		{
			ConfigRegistration.registerClass("plays", ec.getInstanceableClass().getImpl());
		}
		for (ERole ec : ERole.values())
		{
			ConfigRegistration.registerClass("roles", ec.getInstanceableClass().getImpl());
		}
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	// Logger
	private static final Logger						log			= Logger.getLogger(Metis.class.getName());
	
	private final Map<ECalculator, ACalculator>	calculators	= new LinkedHashMap<ECalculator, ACalculator>();
	private ETeamColor									teamColor	= ETeamColor.UNINITIALIZED;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Metis()
	{
		for (ECalculator eCalc : ECalculator.values())
		{
			if (eCalc.getInstanceableClass().getImpl() != null)
			{
				try
				{
					ACalculator inst = (ACalculator) eCalc.getInstanceableClass().newDefaultInstance();
					inst.setType(eCalc);
					inst.setActive(eCalc.isInitiallyActive());
					calculators.put(eCalc, inst);
				} catch (NotCreateableException e)
				{
					log.error("Could not instantiate calculator: " + eCalc, e);
				}
			}
		}
		
		ConfigRegistration.registerConfigurableCallback("metis", this);
		ConfigRegistration.registerConfigurableCallback("plays", this);
		ConfigRegistration.registerConfigurableCallback("roles", this);
	}
	
	
	
	public MetisAiFrame process(final BaseAiFrame baseAiFrame)
	{
		if (teamColor == ETeamColor.UNINITIALIZED)
		{
			teamColor = baseAiFrame.getTeamColor();
			afterApply(null);
		}
		TacticalField newTacticalField = new TacticalField();
		long time = System.nanoTime();
		for (ACalculator calc : calculators.values())
		{
			calc.calculate(newTacticalField, baseAiFrame);
			long aTime = System.nanoTime();
			int diff = (int) ((aTime - time) * 1e-3f);
			newTacticalField.getMetisCalcTimes().put(calc.getType(), diff);
			time = aTime;
		}
		
		return new MetisAiFrame(baseAiFrame, newTacticalField);
	}
	
	
	@Override
	public void afterApply(final IConfigClient configClient)
	{
		for (ACalculator calc : calculators.values())
		{
			ConfigRegistration.applySpezis(calc, "metis", teamColor.name());
			ConfigRegistration.applySpezis(calc, "plays", teamColor.name());
			ConfigRegistration.applySpezis(calc, "roles", teamColor.name());
		}
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public void setCalculatorActive(final ECalculator calc, final boolean active)
	{
		ACalculator calculator = calculators.get(calc);
		if (calculator != null)
		{
			calculator.setActive(active);
		}
	}
	
	
	
	public void setActiveCalculators(final List<ECalculator> activeCalculators)
	{
		for (ECalculator calc : ECalculator.values())
		{
			if (activeCalculators.contains(calc))
			{
				setCalculatorActive(calc, true);
			} else
			{
				setCalculatorActive(calc, false);
			}
		}
	}
	
	
	
	public void stop()
	{
		for (ACalculator calc : calculators.values())
		{
			calc.deinit();
		}
	}
	
	
	
	public void reset()
	{
		for (ACalculator calc : calculators.values())
		{
			calc.reset();
		}
	}
	
	
	
	public ACalculator getCalculator(final ECalculator eCalc)
	{
		return calculators.get(eCalc);
	}
	
}
