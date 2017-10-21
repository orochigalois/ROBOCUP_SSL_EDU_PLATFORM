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

import javax.swing.JPanel;

import edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots.KickerFirePanel;
import net.miginfocom.swing.MigLayout;



public class BcBotKickerPanel extends JPanel
{
	
	private static final long		serialVersionUID	= -725531316816891840L;
	private final KickerFirePanel	kickerFirePanel	= new KickerFirePanel();
	
	
	
	public BcBotKickerPanel()
	{
		setLayout(new MigLayout("fillx, wrap 1"));
		add(kickerFirePanel);
	}
	
	
	
	public KickerFirePanel getKickerFirePanel()
	{
		return kickerFirePanel;
	}
}
