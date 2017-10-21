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

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.evolve.Deleter;
import com.sleepycat.persist.evolve.Mutations;
import com.sleepycat.persist.model.AnnotationModel;
import com.sleepycat.persist.model.EntityModel;

import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.bots.TigerBotV3;
import edu.tigers.sumatra.drawable.ColorProxy;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;



@SuppressWarnings("deprecation")
public class BerkeleyEnv
{
	private static final Logger	log	= Logger.getLogger(BerkeleyEnv.class.getName());
	private Environment				myEnv;
	private EntityStore				store;
	
	
	
	public BerkeleyEnv()
	{
	}
	
	
	
	private Mutations createMutations()
	{
		Mutations mutations = new Mutations();
		mutations.addDeleter(new Deleter(SimpleWorldFrame.class.getCanonicalName(), 2, "frame"));
		mutations.addDeleter(new Deleter(ABot.class.getCanonicalName(), 0, "battery"));
		mutations.addDeleter(new Deleter(TigerBotV3.class.getCanonicalName(), 1, "relBattery"));
		mutations.addDeleter(new Deleter(ABot.class.getCanonicalName(), 1, "performance"));
		mutations.addDeleter(new Deleter(ABot.class.getCanonicalName(), 0, "performance"));
		return mutations;
	}
	
	
	
	public void setup(final File envHome, final boolean readOnly)
	{
		EntityModel model = new AnnotationModel();
		EnvironmentConfig myEnvConfig = new EnvironmentConfig();
		StoreConfig storeConfig = new StoreConfig();
		
		model.registerClass(ColorProxy.class);
		
		myEnvConfig.setReadOnly(readOnly);
		storeConfig.setReadOnly(readOnly);
		
		// If the environment is opened for write, then we want to be
		// able to create the environment and entity store if
		// they do not exist.
		myEnvConfig.setAllowCreate(!readOnly);
		storeConfig.setAllowCreate(!readOnly);
		
		// set mutations for conversions of the db
		storeConfig.setMutations(createMutations());
		
		storeConfig.setModel(model);
		
		// Open the environment and entity store
		myEnv = new Environment(envHome, myEnvConfig);
		store = new EntityStore(myEnv, "EntityStore", storeConfig);
	}
	
	
	
	public EntityStore getEntityStore()
	{
		return store;
	}
	
	
	
	public Environment getEnv()
	{
		return myEnv;
	}
	
	
	
	public void close()
	{
		if (store != null)
		{
			try
			{
				store.close();
				store = null;
			} catch (DatabaseException dbe)
			{
				log.error("Error closing store", dbe);
			}
		}
		
		if (myEnv != null)
		{
			try
			{
				// Finally, close the store and environment.
				myEnv.close();
				myEnv = null;
			} catch (DatabaseException dbe)
			{
				log.error("Error closing myEnv", dbe);
			}
		}
	}
	
	
	
	public boolean isOpen()
	{
		return (myEnv != null) && (store != null);
	}
}
