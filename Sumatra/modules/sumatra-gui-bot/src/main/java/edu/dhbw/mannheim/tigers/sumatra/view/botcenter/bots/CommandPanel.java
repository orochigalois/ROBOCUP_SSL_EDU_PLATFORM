/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots;

import javax.swing.JPanel;

import com.github.g3force.instanceables.IInstanceableObserver;
import com.github.g3force.instanceables.InstanceablePanel;

import edu.tigers.sumatra.botmanager.commands.ECommand;
import edu.tigers.sumatra.model.SumatraModel;



public class CommandPanel extends JPanel
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final long			serialVersionUID	= -8818621974705939633L;
	
	private final InstanceablePanel	instPanel			= new InstanceablePanel(ECommand.values(), SumatraModel
																				.getInstance().getUserSettings());
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public CommandPanel()
	{
		add(instPanel);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public void addObserver(final IInstanceableObserver obs)
	{
		instPanel.addObserver(obs);
	}
	
	
	
	public void removeObserver(final IInstanceableObserver obs)
	{
		instPanel.removeObserver(obs);
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
