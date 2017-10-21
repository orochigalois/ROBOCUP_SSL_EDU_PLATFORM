/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra;

import java.awt.EventQueue;

import edu.tigers.sumatra.log.JULLoggingBridge;




public final class Sumatra
{
	private Sumatra()
	{
	}
	
	
	static
	{
		// Connect java.util.logging (for jinput)
		JULLoggingBridge.install();
		
		// enable AWT keyboard for RCM
		// -Djinput.plugins=net.java.games.input.AWTEnvironmentPlugin
		// System.setProperty("jinput.plugins", "net.java.games.input.AWTEnvironmentPlugin");
	}
	
	
	
	public static void main(final String[] args)
	{
		EventQueue.invokeLater(() -> new MainPresenter());
	}
	
	
	
	public static void touch()
	{
	}
}
