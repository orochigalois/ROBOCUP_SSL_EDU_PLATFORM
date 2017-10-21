/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import edu.tigers.sumatra.botmanager.bots.communication.ENetworkState;



public class NetStateIndicator extends JTextField
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long	serialVersionUID	= 4127310126348792093L;
	
	private ENetworkState		state					= ENetworkState.OFFLINE;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public NetStateIndicator()
	{
		setEditable(false);
		
		setConnectionState(state);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void setConnectionState(final ENetworkState state)
	{
		this.state = state;
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				switch (state)
				{
					case OFFLINE:
						setText("Offline");
						setBackground(new Color(255, 128, 128));
						break;
					case CONNECTING:
						setText("Connecting");
						setBackground(Color.CYAN);
						break;
					case ONLINE:
						setText("Online");
						setBackground(Color.GREEN);
						break;
				}
			}
		});
	}
	
	
	
	public ENetworkState getState()
	{
		return state;
	}
}
