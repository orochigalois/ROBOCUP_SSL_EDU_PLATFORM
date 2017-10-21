/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.components;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;



public abstract class BasePanel<T> extends JPanel
{
	
	
	private static final long	serialVersionUID	= 1L;
	
	private List<T>				observer				= new ArrayList<>();
	
	
	
	public void addObserver(final T observer)
	{
		this.observer.add(observer);
	}
	
	
	
	public void removeObserver(final T observer)
	{
		this.observer.remove(observer);
	}
	
	
	protected List<T> getObserver()
	{
		return observer;
	}
	
	
	protected void informObserver(final Consumer<T> consumer)
	{
		observer.forEach(observer -> consumer.accept(observer));
	}
	
	
	
	public abstract void setPanelEnabled(boolean enabled);
	
}
