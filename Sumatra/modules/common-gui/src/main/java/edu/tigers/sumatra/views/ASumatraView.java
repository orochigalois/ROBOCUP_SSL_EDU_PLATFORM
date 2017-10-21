/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.views;

import java.awt.Component;

import org.apache.log4j.Logger;

import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.model.ModuliStateAdapter;
import edu.tigers.sumatra.model.SumatraModel;
import net.infonode.docking.View;



public abstract class ASumatraView
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final Logger		log			= Logger.getLogger(ASumatraView.class.getName());
	private final ESumatraViewType	type;
	private ISumatraViewPresenter		presenter	= null;
	private View							view			= null;
	private EViewMode						mode			= EViewMode.NORMAL;
	
	
	public enum EViewMode
	{
		
		NORMAL,
		
		REPLAY
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public ASumatraView(final ESumatraViewType type)
	{
		this.type = type;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final void ensureInitialized()
	{
		if (presenter == null)
		{
			log.trace("Creating presenter for view " + type.getTitle());
			presenter = createPresenter();
			getView().setComponent(presenter.getComponent());
			if (mode == EViewMode.NORMAL)
			{
				ModuliStateAdapter.getInstance().addObserver(presenter);
				// let the presenter update itself according to the current moduli state
				ModulesState currentState = SumatraModel.getInstance().getModulesState().get();
				presenter.onModuliStateChanged(currentState);
			}
			log.trace("Presenter created for view " + type.getTitle());
		}
	}
	
	
	
	public final synchronized Component getComponent()
	{
		ensureInitialized();
		return presenter.getComponent();
	}
	
	
	
	public final synchronized ISumatraView getSumatraView()
	{
		ensureInitialized();
		return presenter.getSumatraView();
	}
	
	
	
	public final synchronized View getView()
	{
		if (view == null)
		{
			view = new View(getType().getTitle(), new ViewIcon(), null);
		}
		return view;
	}
	
	
	
	public final synchronized boolean isInitialized()
	{
		return (presenter != null) && (view != null);
	}
	
	
	
	protected abstract ISumatraViewPresenter createPresenter();
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public final ESumatraViewType getType()
	{
		return type;
	}
	
	
	
	public final ISumatraViewPresenter getPresenter()
	{
		return presenter;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ASumatraView [type=");
		builder.append(type);
		builder.append(", isInit=");
		builder.append(isInitialized());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	public final EViewMode getMode()
	{
		return mode;
	}
	
	
	
	public final void setMode(final EViewMode mode)
	{
		this.mode = mode;
	}
}
