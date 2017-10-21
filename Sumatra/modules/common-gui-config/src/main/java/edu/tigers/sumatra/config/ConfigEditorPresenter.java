/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.config;

import java.awt.Component;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.IConfigClientsObserver;

import edu.tigers.sumatra.views.ISumatraView;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class ConfigEditorPresenter implements ISumatraViewPresenter, IConfigClientsObserver,
		IConfigEditorViewObserver
{
	private final ConfigEditorPanel view;
	
	
	
	public ConfigEditorPresenter()
	{
		view = new ConfigEditorPanel();
		
		ConfigRegistration.addObserver(this);
		
		for (String client : ConfigRegistration.getConfigClients())
		{
			onNewConfigClient(client);
		}
	}
	
	
	@Override
	public void onNewConfigClient(final String newClient)
	{
		view.addConfigModel(newClient, this);
	}
	
	
	@Override
	public void onApplyPressed(final String configKey)
	{
		ConfigRegistration.applyConfig(configKey);
	}
	
	
	@Override
	public boolean onSavePressed(final String configKey)
	{
		return ConfigRegistration.save(configKey);
	}
	
	
	@Override
	public void onReloadPressed(final String configKey)
	{
		view.refreshConfigModel(configKey, ConfigRegistration.loadConfig(configKey));
	}
	
	
	@Override
	public Component getComponent()
	{
		return view;
	}
	
	
	@Override
	public ISumatraView getSumatraView()
	{
		return view;
	}
}
