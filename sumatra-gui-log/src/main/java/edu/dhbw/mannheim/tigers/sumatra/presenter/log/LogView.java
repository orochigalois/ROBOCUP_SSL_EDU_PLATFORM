/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.presenter.log;

import edu.tigers.sumatra.views.ASumatraView;
import edu.tigers.sumatra.views.ESumatraViewType;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class LogView extends ASumatraView
{
	private final boolean	addAppender;
	
	
	
	public LogView(final boolean addAppender)
	{
		super(ESumatraViewType.LOG);
		this.addAppender = addAppender;
	}
	
	
	@Override
	public ISumatraViewPresenter createPresenter()
	{
		return new LogPresenter(addAppender);
	}
}
