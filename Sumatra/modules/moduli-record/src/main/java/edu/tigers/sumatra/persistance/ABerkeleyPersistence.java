/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistance;

import java.io.File;

import org.apache.log4j.Logger;
import org.zeroturnaround.zip.ZipUtil;



public abstract class ABerkeleyPersistence
{
	private static final Logger	log	= Logger.getLogger(ABerkeleyPersistence.class.getName());
	
	private final BerkeleyEnv		env;
	private final String				dbPath;
	
	
	
	public ABerkeleyPersistence(final String dbPath, final boolean readOnly)
	{
		if (dbPath.endsWith(".zip"))
		{
			this.dbPath = dbPath.substring(0, dbPath.length() - 4);
			if (!new File(this.dbPath).exists())
			{
				log.info("Unpacking database...");
				ZipUtil.unpack(new File(dbPath), new File(this.dbPath + "/.."));
				log.info("Unpacking finished.");
			}
		} else
		{
			this.dbPath = dbPath;
		}
		log.debug("Setting up database...");
		env = new BerkeleyEnv();
		File envHome = new File(this.dbPath);
		if (!envHome.exists())
		{
			boolean mkdirs = envHome.mkdirs();
			if (!mkdirs)
			{
				log.error("Could not create " + envHome);
			}
		}
		env.setup(envHome, readOnly);
		attachShutDownHook();
		log.debug("Database ready!");
	}
	
	
	
	public final void close()
	{
		env.close();
	}
	
	
	private void attachShutDownHook()
	{
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				if (env.isOpen())
				{
					log.warn("Database " + dbPath + " was open on shutdown. It would be better to close it explictly");
					close();
				}
			}
		}, "DB Shutdown"));
	}
	
	
	
	public final BerkeleyEnv getEnv()
	{
		return env;
	}
}
