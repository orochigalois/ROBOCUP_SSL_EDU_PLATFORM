/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.motorlearner.collector;

import org.apache.log4j.Logger;

import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.sumatra.botmanager.ABotManager;
import edu.tigers.sumatra.botmanager.BotManager;
import edu.tigers.sumatra.botmanager.basestation.IBaseStation;
import edu.tigers.sumatra.botmanager.basestation.IBaseStationObserver;
import edu.tigers.sumatra.export.INumberListable;
import edu.tigers.sumatra.model.SumatraModel;



public abstract class ABotDataCollector<ResultType extends INumberListable> extends ADataCollector<ResultType>
		implements IBaseStationObserver
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ABotDataCollector.class.getName());
	
	
	
	protected ABotDataCollector(final EDataCollector type)
	{
		super(type);
	}
	
	
	@Override
	public void start()
	{
		super.start();
		try
		{
			BotManager bm = (BotManager) SumatraModel.getInstance().getModule(ABotManager.MODULE_ID);
			for (IBaseStation ibs : bm.getBaseStations())
			{
				ibs.addObserver(this);
			}
		} catch (ModuleNotFoundException err)
		{
			log.warn("Could not get BotManager");
		}
	}
	
	
	@Override
	public void stop()
	{
		super.stop();
		try
		{
			BotManager bm = (BotManager) SumatraModel.getInstance().getModule(ABotManager.MODULE_ID);
			for (IBaseStation ibs : bm.getBaseStations())
			{
				ibs.removeObserver(this);
			}
		} catch (ModuleNotFoundException err)
		{
			log.warn("Could not get BotManager");
		}
	}
	
}
