/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.presenter.botcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.BotConfigPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.BotConfigPanel.IBotConfigPanelObserver;
import edu.tigers.sumatra.botmanager.ABotManager;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.botmanager.commands.ACommand;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigFileStructure;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigItemDesc;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigQueryFileList;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigRead;
import edu.tigers.sumatra.botmanager.commands.tigerv3.TigerConfigWrite;



public class ConfigPresenter implements IBotConfigPanelObserver
{
	private final BotConfigPanel				configPanel;
	private ABot									bot;
	private ABotManager							botManager	= null;
	private final Map<Integer, ConfigFile>	files			= new HashMap<Integer, ConfigFile>();
	private static final Logger				log			= Logger.getLogger(ConfigPresenter.class.getName());
	
	
	
	public ConfigPresenter(final BotConfigPanel configPanel, final ABot bot)
	{
		this.configPanel = configPanel;
		this.bot = bot;
		
		this.configPanel.addObserver(this);
	}
	
	
	
	public void setBot(final ABot bot)
	{
		this.bot = bot;
	}
	
	
	
	public void setBotManager(final ABotManager botManager)
	{
		this.botManager = botManager;
	}
	
	
	
	public void onNewCommand(final ACommand cmd)
	{
		switch (cmd.getType())
		{
			case CMD_CONFIG_FILE_STRUCTURE:
			{
				TigerConfigFileStructure structure = (TigerConfigFileStructure) cmd;
				
				if (files.remove(structure.getConfigId()) != null)
				{
					configPanel.removeConfigFile(structure.getConfigId());
				}
				
				ConfigFile cfgFile = new ConfigFile(structure);
				files.put(structure.getConfigId(), cfgFile);
				
				bot.execute(cfgFile.getNextRequest());
			}
				break;
			case CMD_CONFIG_ITEM_DESC:
			{
				TigerConfigItemDesc desc = (TigerConfigItemDesc) cmd;
				
				ConfigFile cfgFile = files.get(desc.getConfigId());
				if (cfgFile == null)
				{
					return;
				}
				
				cfgFile.setItemDesc(desc);
				
				if (cfgFile.isComplete())
				{
					bot.execute(new TigerConfigRead(desc.getConfigId()));
				}
				else
				{
					bot.execute(cfgFile.getNextRequest());
				}
			}
				break;
			case CMD_CONFIG_READ:
			{
				TigerConfigRead read = (TigerConfigRead) cmd;
				
				ConfigFile cfgFile = files.get(read.getConfigId());
				if (cfgFile == null)
				{
					return;
				}
				
				cfgFile.setValues(read);
				
				log.info("Config complete:" + cfgFile.getName());
				
				configPanel.addConfigFile(cfgFile);
			}
				break;
			default:
				break;
		}
	}
	
	
	@Override
	public void onQueryFileList()
	{
		bot.execute(new TigerConfigQueryFileList());
	}
	
	
	@Override
	public void onSave(final ConfigFile file)
	{
		bot.execute(file.getWriteCmd());
	}
	
	
	@Override
	public void onSaveToAll(final ConfigFile file)
	{
		if (botManager == null)
		{
			return;
		}
		
		for (ABot bot : botManager.getAllBots().values())
		{
			bot.execute(file.getWriteCmd());
		}
	}
	
	
	@Override
	public void onRefresh(final ConfigFile file)
	{
		bot.execute(new TigerConfigRead(file.getConfigId()));
	}
	
	
	public final class ConfigFile
	{
		private final TigerConfigFileStructure	structure;
		private String									name		= null;
		private final List<String>					names		= new ArrayList<String>();
		private List<String>							values	= new ArrayList<String>();
		
		
		
		public ConfigFile(final TigerConfigFileStructure structure)
		{
			this.structure = structure;
		}
		
		
		
		public boolean isComplete()
		{
			return structure.getElements().size() == names.size();
		}
		
		
		
		public String getName()
		{
			return name;
		}
		
		
		
		public int getConfigId()
		{
			return structure.getConfigId();
		}
		
		
		
		public TigerConfigItemDesc getNextRequest()
		{
			if (name == null)
			{
				return new TigerConfigItemDesc(structure.getConfigId(), TigerConfigItemDesc.CONFIG_ITEM_FILE_NAME);
			}
			
			if (isComplete())
			{
				return null;
			}
			
			return new TigerConfigItemDesc(structure.getConfigId(), names.size());
		}
		
		
		
		public void setItemDesc(final TigerConfigItemDesc desc)
		{
			if (desc.getElement() == TigerConfigItemDesc.CONFIG_ITEM_FILE_NAME)
			{
				name = desc.getName();
				return;
			}
			
			if (names.size() != desc.getElement())
			{
				log.warn("Element mismatch: " + names + "(" + desc.getName() + ") " + names.size() + "!="
						+ desc.getElement());
				return;
			}
			
			names.add(desc.getName());
		}
		
		
		
		public void setValues(final TigerConfigRead read)
		{
			values = read.getData(structure);
		}
		
		
		
		public TigerConfigWrite getWriteCmd()
		{
			TigerConfigWrite write = new TigerConfigWrite(structure.getConfigId());
			write.setData(structure, values);
			
			return write;
		}
		
		
		
		public List<String> getNames()
		{
			return names;
		}
		
		
		
		public List<String> getValues()
		{
			return values;
		}
	}
}