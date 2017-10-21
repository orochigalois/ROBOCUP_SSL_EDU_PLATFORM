/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.gui;

import org.apache.log4j.Logger;

import edu.tigers.autoref.gui.view.AutoRefMainFrame;
import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.sumatra.AMainPresenter;
import edu.tigers.sumatra.model.SumatraModel;



public class AutoRefMainPresenter extends AMainPresenter
{
	private static final Logger		log							= Logger.getLogger(AutoRefMainPresenter.class);
	private static final String		LAST_LAYOUT_FILENAME		= "last.ly";
	private static final String		DEFAULT_LAYOUT				= "default.ly";
	private static final String		KEY_LAYOUT_PROP			= AutoRefMainPresenter.class.getName() + ".layout";
	private static final String		DEFAULT_MODULI_FILENAME	= "moduli.xml";
	
	private final AutoRefMainFrame	mainFrame;
	
	
	
	public AutoRefMainPresenter()
	{
		super(new AutoRefMainFrame());
		mainFrame = (AutoRefMainFrame) getMainFrame();
		
		startupModuli();
		
		mainFrame.activate();
	}
	
	
	private void startupModuli()
	{
		String moduliFile = SumatraModel.getInstance().getCurrentModuliConfig();
		if (SumatraModel.MODULI_CONFIG_FILE_DEFAULT.equals(moduliFile))
		{
			moduliFile = DEFAULT_MODULI_FILENAME;
		}
		
		SumatraModel.getInstance().loadModulesSafe(moduliFile);
		try
		{
			SumatraModel.getInstance().startModules();
		} catch (InitModuleException | StartModuleException e)
		{
			log.error("Module startup exception --- The referee might not function correctly", e);
		}
	}
	
	
	private void shutdownModuli()
	{
		SumatraModel.getInstance().stopModules();
	}
	
	
	@Override
	protected String getLastLayoutFile()
	{
		return LAST_LAYOUT_FILENAME;
	}
	
	
	@Override
	protected String getLayoutKey()
	{
		return KEY_LAYOUT_PROP;
	}
	
	
	@Override
	protected String getDefaultLayout()
	{
		return DEFAULT_LAYOUT;
	}
	
	
	@Override
	public void onExit()
	{
		super.onExit();
		
		shutdownModuli();
		SumatraModel.getInstance().saveUserProperties();
		System.exit(0);
	}
	
}
