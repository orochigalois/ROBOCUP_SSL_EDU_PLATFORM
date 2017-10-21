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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.components.EnumCheckBoxPanel.IEnumPanelObserver;



public class EnumCheckBoxPanel<T extends Enum<T>> extends BasePanel<IEnumPanelObserver<T>>
{
	
	private static final long		serialVersionUID	= 5263861341015714105L;
	private static final Logger	log					= Logger.getLogger(EnumCheckBoxPanel.class);
	
	private Function<T, String>	formatter;
	private Class<T>					enumClass;
	private Map<T, JCheckBox>		boxes;
	
	
	
	public EnumCheckBoxPanel(final Class<T> enumClass, final String title, final int orientation)
	{
		this(enumClass, title, orientation, null);
	}
	
	
	
	public EnumCheckBoxPanel(final Class<T> enumClass, final String title, final int orientation,
			final Function<T, String> formatter)
	{
		this.enumClass = enumClass;
		boxes = new EnumMap<>(enumClass);
		this.formatter = formatter;
		
		createBoxes(orientation);
		
		if (title != null)
		{
			setBorder(BorderFactory.createTitledBorder(title));
		}
	}
	
	
	private void createBoxes(final int orientation)
	{
		setLayout(new BoxLayout(this, orientation));
		
		for (T type : enumClass.getEnumConstants())
		{
			JCheckBox checkBox = new JCheckBox(getBoxLabel(type));
			checkBox.setSelected(true);
			checkBox.addActionListener(new CheckBoxActionListener());
			boxes.put(type, checkBox);
			add(checkBox);
		}
	}
	
	
	private String getBoxLabel(final T type)
	{
		if (formatter != null)
		{
			return formatter.apply(type);
		}
		return type.name();
	}
	
	
	private void onSelectionChange(final T type, final boolean value)
	{
		informObserver(obs -> obs.onValueTicked(type, value));
	}
	
	
	@Override
	public void setPanelEnabled(final boolean enabled)
	{
		boxes.values().forEach(box -> box.setEnabled(enabled));
	}
	
	
	
	public Set<T> getValues()
	{
		Set<T> values = new HashSet<>();
		for (T type : boxes.keySet())
		{
			JCheckBox box = boxes.get(type);
			if (box.isSelected())
			{
				values.add(type);
			}
		}
		return values;
	}
	
	
	
	public void setSelectedBoxes(final Set<T> enabledBoxes)
	{
		boxes.keySet().forEach(t -> {
			boxes.get(t).setSelected(enabledBoxes.contains(t));
		});
	}
	
	
	public interface IEnumPanelObserver<E>
	{
		
		
		public void onValueTicked(E type, boolean value);
	}
	
	private class CheckBoxActionListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			try
			{
				T enumValue = Enum.valueOf(enumClass, e.getActionCommand());
				boolean value = ((JCheckBox) e.getSource()).isSelected();
				onSelectionChange(enumValue, value);
			} catch (IllegalArgumentException ex)
			{
				log.warn("Unable to parse \"" + e.getActionCommand() + "\" to enum value");
			}
		}
	}
}
