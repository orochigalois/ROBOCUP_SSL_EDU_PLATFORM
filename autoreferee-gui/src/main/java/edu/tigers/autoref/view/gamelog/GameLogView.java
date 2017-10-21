/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.gamelog;

import edu.tigers.autoref.presenter.GameLogPresenter;
import edu.tigers.sumatra.views.ASumatraView;
import edu.tigers.sumatra.views.ESumatraViewType;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class GameLogView extends ASumatraView
{
	
	
	public GameLogView()
	{
		super(ESumatraViewType.AUTOREFEREE_GAME_LOG);
	}
	
	
	@Override
	protected ISumatraViewPresenter createPresenter()
	{
		return new GameLogPresenter();
	}
	
}
