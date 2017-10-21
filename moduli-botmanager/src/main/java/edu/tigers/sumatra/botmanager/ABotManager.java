/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import edu.tigers.moduli.AModule;
import edu.tigers.sumatra.botmanager.basestation.IBaseStation;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.ids.BotID;



public abstract class ABotManager extends AModule
{
	
	public static final String			MODULE_TYPE					= "ABotManager";
	
	public static final String			MODULE_ID					= "botmanager";
																				
																				
	
	public static final String			KEY_BOTMANAGER_CONFIG	= ABotManager.class.getName()
																						+ ".botmanagerConfig";
	
	public static final String			BOTMANAGER_CONFIG_PATH	= "./config/botmanager/";
	
	public static final String			VALUE_BOTMANAGER_CONFIG	= "botmanager_sumatra.xml";
																				
																				
	private final Map<BotID, ABot>	botTable						= new ConcurrentSkipListMap<BotID, ABot>(
																						BotID.getComparator());
																						
																						
	
	public abstract void removeBot(BotID id);
	
	
	
	public abstract void addBasestation(IBaseStation baseStation);
	
	
	
	public abstract void removeBasestation(IBaseStation baseStation);
	
	
	
	public abstract Map<BotID, ABot> getAllBots();
	
	
	
	public abstract List<IBaseStation> getBaseStations();
	
	
	private final List<IBotManagerObserver> observers = new ArrayList<IBotManagerObserver>();
	
	
	
	public abstract void chargeAll();
	
	
	
	public abstract void dischargeAll();
	
	
	
	public void addObserver(final IBotManagerObserver o)
	{
		synchronized (observers)
		{
			observers.add(o);
		}
	}
	
	
	
	public void removeObserver(final IBotManagerObserver o)
	{
		synchronized (observers)
		{
			observers.remove(o);
		}
	}
	
	
	protected void notifyBotAdded(final ABot bot)
	{
		synchronized (observers)
		{
			for (final IBotManagerObserver o : observers)
			{
				o.onBotAdded(bot);
			}
		}
	}
	
	
	protected void notifyBotRemoved(final ABot bot)
	{
		synchronized (observers)
		{
			for (final IBotManagerObserver o : observers)
			{
				o.onBotRemoved(bot);
			}
		}
	}
	
	
	
	public Map<BotID, ABot> getBotTable()
	{
		return botTable;
	}
}
