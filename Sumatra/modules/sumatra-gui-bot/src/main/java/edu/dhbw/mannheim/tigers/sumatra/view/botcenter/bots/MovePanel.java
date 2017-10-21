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

import net.miginfocom.swing.MigLayout;



public class MovePanel extends JPanel
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final long			serialVersionUID	= -1556982047975006457L;
	private MotorInputPanel				input					= null;
	private MotorEnhancedInputPanel	enhanced				= null;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public MovePanel()
	{
		input = new MotorInputPanel();
		enhanced = new MotorEnhancedInputPanel();
		
		setLayout(new MigLayout());
		add(enhanced, "wrap, aligny top");
		add(input);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public MotorInputPanel getInputPanel()
	{
		return input;
	}
	
	
	
	public MotorEnhancedInputPanel getEnhancedInputPanel()
	{
		return enhanced;
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
