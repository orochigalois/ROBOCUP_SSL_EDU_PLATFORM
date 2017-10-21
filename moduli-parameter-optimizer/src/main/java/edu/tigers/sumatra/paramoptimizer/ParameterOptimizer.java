/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.sumatra.paramoptimizer;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.log4j.Logger;

import edu.tigers.moduli.AModule;
import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.paramoptimizer.redirect.RedirectDetector;
import edu.tigers.sumatra.wp.AWorldPredictor;
import edu.tigers.sumatra.wp.IWorldFrameObserver;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;





public class ParameterOptimizer extends AModule implements IWorldFrameObserver
{
	@SuppressWarnings("unused")
	private static final Logger	log					= Logger.getLogger(ParameterOptimizer.class.getName());
	
	
	public static final String		MODULE_TYPE			= "ParameterOptimizer";
	
	public static final String		MODULE_ID			= "ParameterOptimizer";
	
	@SuppressWarnings("unused")
	private RedirectDetector		redirectDetector	= new RedirectDetector();
	
	
	
	public ParameterOptimizer(final SubnodeConfiguration config)
	{
	}
	
	
	@Override
	public void onNewWorldFrame(final WorldFrameWrapper wFrameWrapper)
	{
		// SimpleWorldFrame swf = wFrameWrapper.getSimpleWorldFrame();
		
		// Optional<RedirectDataSet> rds = redirectDetector.process(swf);
		// if (rds.isPresent())
		{
			// RedirectParamCalc.forBot(rds.get().getBot()).update(rds.get());
		}
	}
	
	
	@Override
	public void initModule() throws InitModuleException
	{
	}
	
	
	@Override
	public void deinitModule()
	{
	}
	
	
	@Override
	public void startModule() throws StartModuleException
	{
		try
		{
			AWorldPredictor wp = (AWorldPredictor) SumatraModel.getInstance().getModule(AWorldPredictor.MODULE_ID);
			wp.addWorldFrameConsumer(this);
		} catch (ModuleNotFoundException e)
		{
			log.error("Could not find WP module", e);
		}
	}
	
	
	@Override
	public void stopModule()
	{
		try
		{
			AWorldPredictor wp = (AWorldPredictor) SumatraModel.getInstance().getModule(AWorldPredictor.MODULE_ID);
			wp.removeWorldFrameConsumer(this);
		} catch (ModuleNotFoundException e)
		{
			log.error("Could not find WP module", e);
		}
	}
}
