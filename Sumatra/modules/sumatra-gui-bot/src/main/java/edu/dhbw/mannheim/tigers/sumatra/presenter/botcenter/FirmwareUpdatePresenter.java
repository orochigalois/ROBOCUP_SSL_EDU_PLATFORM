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

import org.apache.log4j.Logger;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bootloader.FirmwareBotPanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bootloader.FirmwareUpdatePanel;
import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bootloader.FirmwareUpdatePanel.IFirmwareUpdatePanelObserver;
import edu.tigers.moduli.IModuliStateObserver;
import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.bot.IBot;
import edu.tigers.sumatra.botmanager.ABotManager;
import edu.tigers.sumatra.botmanager.basestation.IBaseStation;
import edu.tigers.sumatra.botmanager.bots.Bootloader;
import edu.tigers.sumatra.botmanager.bots.Bootloader.EProcessorID;
import edu.tigers.sumatra.botmanager.bots.Bootloader.IBootloaderObserver;
import edu.tigers.sumatra.botmanager.bots.TigerBotV3;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerBootloaderCheckForUpdates;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.model.SumatraModel;



public class FirmwareUpdatePresenter implements IFirmwareUpdatePanelObserver, IBootloaderObserver, IModuliStateObserver
{
	@SuppressWarnings("unused")
	private static final Logger			log	= Logger.getLogger(FirmwareUpdatePresenter.class.getName());
	private final Bootloader				bootloader;
	private final IBaseStation				baseStation;
	private final FirmwareUpdatePanel	updatePanel;
	
	
	
	public FirmwareUpdatePresenter(final FirmwareUpdatePanel updatePanel, final IBaseStation baseStation)
	{
		this.updatePanel = updatePanel;
		this.baseStation = baseStation;
		bootloader = new Bootloader(baseStation);
		
		onSelectFirmwareFolder(SumatraModel.getInstance().getUserProperty(
				FirmwareUpdatePanel.class.getCanonicalName() + ".firmwareFolder", ""));
	}
	
	
	@Override
	public void onSelectFirmwareFolder(final String folderPath)
	{
		// File testMain = new File(folderPath + "/release/app/run/main.bin");
		// if (!testMain.exists())
		// {
		// log.warn("Compiled firmware binaries not found: " + folderPath);
		// }
		
		Bootloader.setProgramFolder(folderPath + "/release/app/run");
		
		updatePanel.removeAllBotPanels();
	}
	
	
	@Override
	public void onStartFirmwareUpdate()
	{
		ABotManager botManager;
		
		try
		{
			botManager = (ABotManager) SumatraModel.getInstance().getModule(ABotManager.MODULE_ID);
		} catch (ModuleNotFoundException err)
		{
			return;
		}
		
		for (IBot bot : botManager.getAllBots().values())
		{
			if (bot.getType() == EBotType.TIGER_V3)
			{
				TigerBotV3 botV3 = (TigerBotV3) bot;
				botV3.execute(new TigerBootloaderCheckForUpdates());
			}
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public FirmwareUpdatePanel getUpdatePanel()
	{
		return updatePanel;
	}
	
	
	@Override
	public void onBootloaderProgress(final BotID botId, final EProcessorID procId, final long bytesRead,
			final long totalSize)
	{
		FirmwareBotPanel panel = updatePanel.getOrCreateBotPanel(botId);
		panel.setProcessorId(procId);
		panel.setProgress(bytesRead, totalSize);
		panel.setBotId(botId);
	}
	
	
	@Override
	public void onModuliStateChanged(final ModulesState state)
	{
		switch (state)
		{
			case ACTIVE:
			{
				baseStation.addObserver(bootloader);
				updatePanel.addObserver(this);
				bootloader.addObserver(this);
				break;
			}
			case NOT_LOADED:
				break;
			case RESOLVED:
				baseStation.removeObserver(bootloader);
				updatePanel.removeObserver(this);
				bootloader.removeObserver(this);
				break;
			default:
				break;
		}
	}
}
