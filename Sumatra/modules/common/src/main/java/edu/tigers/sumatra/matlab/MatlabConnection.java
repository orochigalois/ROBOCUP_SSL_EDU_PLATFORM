/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.matlab;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;



public final class MatlabConnection
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger			log	= Logger.getLogger(MatlabConnection.class.getName());
	private static volatile MatlabProxy	proxy	= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	private MatlabConnection()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static synchronized MatlabProxy getMatlabProxy() throws MatlabConnectionException
	{
		if ((proxy != null) && proxy.isConnected())
		{
			return proxy;
		}
		
		log.info("Create MatlabProxy connection");
		MatlabProxyFactoryOptions.Builder proxyBuilder = new MatlabProxyFactoryOptions.Builder();
		proxyBuilder.setMatlabStartingDirectory(new File("./matlab"));
		proxyBuilder.setUsePreviouslyControlledSession(true);
		MatlabProxyFactory factory = new MatlabProxyFactory(proxyBuilder.build());
		
		try
		{
			proxy = factory.getProxy();
		} catch (MatlabConnectionException err)
		{
			log.error("Could not connect to matlab.", err);
			throw err;
		}
		
		changeToDefaultDir();
		
		log.info("MatlabProxy connection created.");
		return proxy;
	}
	
	
	
	public static synchronized void changeToDefaultDir() throws MatlabConnectionException
	{
		Path currentRelativePath = Paths.get("");
		String currentAbsolutePath = currentRelativePath.toAbsolutePath().toString();
		String path = currentAbsolutePath + "/matlab";
		try
		{
			getMatlabProxy().eval("cd " + path);
			getMatlabProxy().eval("setup");
		} catch (MatlabInvocationException err)
		{
			log.warn("Could not change to default dir: " + path, err);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
