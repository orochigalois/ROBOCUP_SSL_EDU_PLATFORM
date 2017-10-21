/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.tigers.moduli.Moduli;



public final class SumatraModel extends Moduli
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// Logger - Start
	private static final Logger			log								= Logger.getLogger(SumatraModel.class.getName());
	// Logger - End
	
	// --- version ---
	private static final String			VERSION							= "1.0";
	
	// --- singleton ---
	private static final SumatraModel	INSTANCE							= new SumatraModel();
	
	
	private Properties						userSettings					= new Properties();
	
	// --- moduli config ---
	private static final String			KEY_MODULI_CONFIG				= SumatraModel.class.getName() + ".moduliConfig";
	
	public static final String				MODULI_CONFIG_PATH			= "./config/moduli/";
	
	public static final String				MODULI_CONFIG_FILE_DEFAULT	= "moduli_sumatra.xml";
	
	// Application Properties
	private static final String			CONFIG_SETTINGS_PATH			= "./config/";
	
	
	private boolean							productive						= false;
	
	
	// --------------------------------------------------------------------------
	// --- getInstance/constructor(s) -------------------------------------------
	// --------------------------------------------------------------------------
	
	private SumatraModel()
	{
		loadApplicationProperties();
		String prod = System.getProperty("productive");
		if (prod != null)
		{
			productive = Boolean.valueOf(prod);
		}
	}
	
	
	
	public static SumatraModel getInstance()
	{
		return INSTANCE;
	}
	
	
	
	private void loadApplicationProperties()
	{
		// Load user properties
		// Get config file name, which consists of pcName + userName
		userSettings = new Properties();
		final File uf = getUserPropertiesFile();
		
		FileInputStream inUserSettings = null;
		try
		{
			if (uf.exists())
			{
				inUserSettings = new FileInputStream(uf);
				userSettings.load(inUserSettings);
			} else
			{
				if (uf.createNewFile())
				{
					log.debug("new user file created");
				}
			}
		} catch (final IOException err)
		{
			log.warn("Config: " + uf.getPath() + " cannot be read, using default configs!");
		} finally
		{
			try
			{
				if (inUserSettings != null)
				{
					inUserSettings.close();
				}
			} catch (IOException e)
			{
				log.warn("Config: " + uf.getPath() + " cannot be read, using default configs!");
			}
		}
	}
	
	
	@Override
	public void loadModulesSafe(final String filename)
	{
		super.loadModulesSafe(MODULI_CONFIG_PATH + filename);
	}
	
	
	
	public File getUserPropertiesFile()
	{
		final String userName = System.getProperty("user.name");
		final String filename = CONFIG_SETTINGS_PATH + userName + ".props";
		
		return new File(filename);
	}
	
	
	// --------------------------------------------------------------------------
	// --- moduli config --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public String getCurrentModuliConfig()
	{
		return userSettings.getProperty(SumatraModel.KEY_MODULI_CONFIG, MODULI_CONFIG_FILE_DEFAULT);
	}
	
	
	
	public void setCurrentModuliConfig(final String currentModuliConfig)
	{
		userSettings.setProperty(SumatraModel.KEY_MODULI_CONFIG, currentModuliConfig);
	}
	
	
	// --------------------------------------------------------------------------
	// --- app properties -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void setUserSettings(final Properties userSettings)
	{
		this.userSettings = userSettings;
	}
	
	
	
	public Properties getUserSettings()
	{
		return userSettings;
	}
	
	
	
	public String setUserProperty(final String key, final String value)
	{
		Object obj = null;
		if (value == null)
		{
			obj = userSettings.remove(key);
		} else
		{
			obj = userSettings.setProperty(key, value);
		}
		
		if ((obj == null))
		{
			// no previous item
			return null;
		}
		if (!(obj instanceof String))
		{
			log.warn("Object '" + obj + "' (which has been associated to '" + key + "') is no String!");
			return null;
		}
		
		return (String) obj;
	}
	
	
	
	public String getUserProperty(final String key)
	{
		return userSettings.getProperty(key);
	}
	
	
	
	public String getUserProperty(final String key, final String def)
	{
		String val = userSettings.getProperty(key);
		if (val == null)
		{
			return def;
		}
		return val;
	}
	
	
	
	public static String getVersion()
	{
		return VERSION;
	}
	
	
	
	public final boolean isProductive()
	{
		return productive;
	}
	
	
	
	public void saveUserProperties()
	{
		final File uf = getUserPropertiesFile();
		
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(uf);
			userSettings.store(out, null);
		} catch (final IOException err)
		{
			log.warn("Could not write to " + uf.getPath() + ", configuration is not saved");
		}
		
		if (out != null)
		{
			try
			{
				out.close();
				log.trace("Saved configuration to: " + uf.getPath());
			} catch (IOException e)
			{
				log.warn("Could not close " + uf.getPath() + ", configuration is not saved");
			}
		}
	}
	
	
	
	public static void noLogging()
	{
		Logger.getRootLogger().setLevel(Level.OFF);
	}
	
	
	
	public static void changeLogLevel(final Level lvl)
	{
		Appender appender = Logger.getRootLogger().getAppender("console");
		if ((appender != null) && (appender instanceof ConsoleAppender))
		{
			((ConsoleAppender) appender).setThreshold(lvl);
		}
	}
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
