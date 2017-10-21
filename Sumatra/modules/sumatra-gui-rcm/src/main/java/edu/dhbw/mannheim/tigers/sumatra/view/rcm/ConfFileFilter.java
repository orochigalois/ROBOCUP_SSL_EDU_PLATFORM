/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/


package edu.dhbw.mannheim.tigers.sumatra.view.rcm;

import java.io.File;

import javax.swing.filechooser.FileFilter;




public class ConfFileFilter extends FileFilter
{
	// --------------------------------------------------------------------------
	// --- class variables ------------------------------------------------------
	// --------------------------------------------------------------------------
	private static final String	FILESUFFIX	= ".rcc";
	private static final String	DESCRIPTION	= "Robot Control Configuration (*" + FILESUFFIX + ")";
	
	
	// -----------------------------------------------------------------
	// ----- Constructor -----------------------------------------------
	// -----------------------------------------------------------------
	
	// -----------------------------------------------------------------
	// ----- Methods ---------------------------------------------------
	// -----------------------------------------------------------------
	@Override
	public boolean accept(final File f)
	{
		if (f.isDirectory())
		{
			return true;
		}
		return f.getName().endsWith(FILESUFFIX);
	}
	
	
	@Override
	public String getDescription()
	{
		return DESCRIPTION;
	}
	
	
	
	public String getFileSuffix()
	{
		return FILESUFFIX;
	}
}
