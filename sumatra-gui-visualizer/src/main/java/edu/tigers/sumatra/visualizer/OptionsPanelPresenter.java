/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer;

import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.visualizer.view.EVisualizerOptions;
import edu.tigers.sumatra.visualizer.view.IOptionsPanelObserver;
import edu.tigers.sumatra.visualizer.view.field.IFieldPanel;



public class OptionsPanelPresenter implements IOptionsPanelObserver
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private final IFieldPanel	fieldPanel;
	private boolean				saveOptions	= true;
														
														
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public OptionsPanelPresenter(final IFieldPanel fieldPanel)
	{
		this.fieldPanel = fieldPanel;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	@Override
	public void onCheckboxClick(final String actionCommand, final boolean isSelected)
	{
		if (saveOptions)
		{
			SumatraModel.getInstance().setUserProperty(
					OptionsPanelPresenter.class.getCanonicalName() + "." + actionCommand,
					String.valueOf(isSelected));
		}
		
		reactOnActionCommand(actionCommand, isSelected);
	}
	
	
	@Override
	public void onActionFired(final EVisualizerOptions option, final boolean state)
	{
		fieldPanel.onOptionChanged(option, state);
	}
	
	
	
	public void reactOnActionCommand(final String actionCommand, final boolean isSelected)
	{
		try
		{
			EVisualizerOptions option = EVisualizerOptions.valueOf(actionCommand);
			fieldPanel.onOptionChanged(option, isSelected);
		} catch (IllegalArgumentException err)
		{
			fieldPanel.setLayerVisiblility(actionCommand, isSelected);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final boolean isSaveOptions()
	{
		return saveOptions;
	}
	
	
	
	public final void setSaveOptions(final boolean saveOptions)
	{
		this.saveOptions = saveOptions;
	}
	
	
}
