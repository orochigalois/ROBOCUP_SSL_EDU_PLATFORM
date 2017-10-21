/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.presenter.replay;

import java.awt.Component;

import edu.dhbw.mannheim.tigers.sumatra.view.replay.ReplayPanel;
import edu.tigers.sumatra.views.ASumatraViewPresenter;
import edu.tigers.sumatra.views.ISumatraView;



public class ReplayControlPresenter extends ASumatraViewPresenter
{
	private final ReplayPanel	panel	= new ReplayPanel();
	
	
	@Override
	public Component getComponent()
	{
		return panel;
	}
	
	
	@Override
	public ISumatraView getSumatraView()
	{
		return panel;
	}
	
	
	
	public ReplayPanel getReplayPanel()
	{
		return panel;
	}
	
}
