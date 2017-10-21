/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.generic;

import javax.swing.JLabel;



public class JFormatLabel<T> extends JLabel
{
	
	
	private static final long	serialVersionUID	= 4937004578155936454L;
	
	private LabelFormatter<T>	formatter;
	private T						value;
	
	
	
	public JFormatLabel(final LabelFormatter<T> formatter)
	{
		this.formatter = formatter;
	}
	
	
	
	public void setValue(final T value)
	{
		this.value = value;
		updateAppearance();
	}
	
	
	
	private void updateAppearance()
	{
		formatter.formatLabel(value, this);
	}
	
	
	
	public T getValue()
	{
		return value;
	}
	
	
	
	public LabelFormatter<T> getFormatter()
	{
		return formatter;
	}
	
	
	
	public void setFormatter(final LabelFormatter<T> formatter)
	{
		this.formatter = formatter;
	}
	
	
	@FunctionalInterface
	public interface LabelFormatter<T>
	{
		
		
		public void formatLabel(T value, JLabel label);
	}
}
