/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.basestation;

import javax.swing.JPanel;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bootloader.FirmwareUpdatePanel;
import net.miginfocom.swing.MigLayout;



public class BaseStationPanel extends JPanel
{
	
	private static final long						serialVersionUID		= -2888008314485655476L;
	
	private final String								name;
	private final BaseStationControlPanel		controlPanel			= new BaseStationControlPanel();
	private final BaseStationWifiStatsPanel	wifiStatsPanel			= new BaseStationWifiStatsPanel();
	private final BaseStationEthStatsPanel		ethStatsPanel			= new BaseStationEthStatsPanel();
	private final BaseStationNtpStatsPanel		ntpStatsPanel			= new BaseStationNtpStatsPanel();
	private final FirmwareUpdatePanel			firmwareUpdatePanel	= new FirmwareUpdatePanel();
	
	
	
	public BaseStationPanel(final String name)
	{
		this.name = name;
		setLayout(new MigLayout("wrap 1"));
		
		add(wifiStatsPanel);
		add(controlPanel);
		add(ethStatsPanel);
		add(ntpStatsPanel);
		add(firmwareUpdatePanel);
	}
	
	
	
	public BaseStationWifiStatsPanel getWifiStatsPanel()
	{
		return wifiStatsPanel;
	}
	
	
	
	public BaseStationEthStatsPanel getEthStatsPanel()
	{
		return ethStatsPanel;
	}
	
	
	
	public BaseStationControlPanel getControlPanel()
	{
		return controlPanel;
	}
	
	
	
	public final FirmwareUpdatePanel getFirmwareUpdatePanel()
	{
		return firmwareUpdatePanel;
	}
	
	
	
	@Override
	public final String getName()
	{
		return name;
	}
	
	
	
	public BaseStationNtpStatsPanel getNtpStatsPanel()
	{
		return ntpStatsPanel;
	}
}
