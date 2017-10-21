/*
 * *********************************************************
 * Copyright (c) 2017 智动天地(北京)科技有限公司
 * All rights reserved.
 * Project: 标准平台决策开发系统
 * Authors:
 * 智动天地(北京)科技有限公司
 * *********************************************************
 */

package edu.tigers.sumatra.visualizer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.visualizer.OptionsPanelPresenter;
import edu.tigers.sumatra.wp.data.ShapeMap.IShapeLayer;


public class VisualizerOptionsMenu extends JMenuBar
{
	private static final long									serialVersionUID	= 6408342941543334436L;
	
	private final List<IOptionsPanelObserver>				observers			= new CopyOnWriteArrayList<IOptionsPanelObserver>();
	private final Map<String, JMenu>							parentMenus			= new HashMap<>();
	private final Map<IShapeLayer, JCheckBoxMenuItem>	checkBoxes			= new HashMap<>();
	private final CheckboxListener							checkboxListener;
	
	
	public VisualizerOptionsMenu()
	{
		// --- checkbox-listener ---
		checkboxListener = new CheckboxListener();
		
		
		JMenu pActions = new JMenu("视觉");
		add(pActions);
		parentMenus.put(pActions.getText(), pActions);
		
		JMenuItem btnTurn = new JMenuItem("方向");
		btnTurn.addActionListener(new TurnFieldListener());
		JMenuItem btnReset = new JMenuItem("重置");
		btnReset.addActionListener(new ResetFieldListener());
		pActions.add(btnTurn);
		pActions.add(btnReset);
		
		// addMenuEntry(EVisualizerOptions.FANCY);
		// addMenuEntry(EVisualizerOptions.YELLOW_AI);
		// addMenuEntry(EVisualizerOptions.BLUE_AI);
		
		
		JMenu pShortcuts = new JMenu("快捷键");
		// add(pShortcuts);
		pShortcuts.add(new JMenuItem("Left mouse click:"));
		pShortcuts.add(new JMenuItem("  ctrl: Look at Ball"));
		pShortcuts.add(new JMenuItem("  shift: Kick to"));
		pShortcuts.add(new JMenuItem("  ctrl+shift: Follow mouse"));
		
		pShortcuts.add(new JMenuItem("Right mouse click:"));
		pShortcuts.add(new JMenuItem("  none: place ball"));
		pShortcuts.add(new JMenuItem("  ctrl: 8m/s to target"));
		pShortcuts.add(new JMenuItem("  shift: stop at target"));
		pShortcuts.add(new JMenuItem("  ctrl+shift: 2m/s at target"));
	}
	
	
	public void addMenuEntry(final IShapeLayer shapeLayer)
	{
		if (checkBoxes.containsKey(shapeLayer))
		{
			return;
		}
		JMenu parent = parentMenus.get(shapeLayer.getCategory());
		if (parent == null)
		{
			parent = new JMenu(shapeLayer.getCategory());
			add(parent);
			parentMenus.put(shapeLayer.getCategory(), parent);
		}
		
		checkBoxes.put(shapeLayer, createCheckBox(shapeLayer, parent));
	}
	
	
	private JCheckBoxMenuItem createCheckBox(final IShapeLayer option, final JMenu parent)
	{
		JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem(option.getLayerName());
		checkbox.addActionListener(checkboxListener);
		checkbox.setActionCommand(option.getId());
		parent.add(checkbox);
		
		String value = SumatraModel.getInstance().getUserProperty(
				OptionsPanelPresenter.class.getCanonicalName() + "." + checkbox.getActionCommand());
		if (value == null)
		{
			checkbox.setSelected(option.isVisibleByDefault());
		} else
		{
			Boolean selected = Boolean.valueOf(value);
			checkbox.setSelected(selected);
		}
		for (final IOptionsPanelObserver o : observers)
		{
			o.onCheckboxClick(checkbox.getActionCommand(), checkbox.isSelected());
		}
		
		return checkbox;
	}
	
	
	public void setInitialButtonState()
	{
		uncheckAllButtons();
		
		for (Map.Entry<IShapeLayer, JCheckBoxMenuItem> entry : checkBoxes.entrySet())
		{
			IShapeLayer shapeLayer = entry.getKey();
			JCheckBoxMenuItem chk = entry.getValue();
			String value = SumatraModel.getInstance().getUserProperty(
					OptionsPanelPresenter.class.getCanonicalName() + "." + chk.getActionCommand());
			if (value == null)
			{
				chk.setSelected(shapeLayer.isVisibleByDefault());
			} else
			{
				Boolean selected = Boolean.valueOf(value);
				chk.setSelected(selected);
			}
			for (final IOptionsPanelObserver o : observers)
			{
				o.onCheckboxClick(chk.getActionCommand(), chk.isSelected());
			}
		}
	}
	
	
	public void setButtonsEnabled(final boolean enable)
	{
		for (JCheckBoxMenuItem cb : checkBoxes.values())
		{
			cb.setEnabled(enable);
		}
	}
	
	
	private void uncheckAllButtons()
	{
		for (JCheckBoxMenuItem cb : checkBoxes.values())
		{
			cb.setSelected(false);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- observer -------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void addObserver(final IOptionsPanelObserver o)
	{
		observers.add(o);
	}
	
	
	public void removeObserver(final IOptionsPanelObserver o)
	{
		observers.remove(o);
	}
	
	
	// --------------------------------------------------------------
	// --- action listener ------------------------------------------
	// --------------------------------------------------------------
	
	protected class CheckboxListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			for (final IOptionsPanelObserver o : observers)
			{
				o.onCheckboxClick(((JCheckBoxMenuItem) e.getSource()).getActionCommand(),
						((JCheckBoxMenuItem) e.getSource()).isSelected());
			}
		}
		
	}
	
	protected class TurnFieldListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			for (final IOptionsPanelObserver o : observers)
			{
				o.onActionFired(EVisualizerOptions.TURN_NEXT, true);
			}
		}
		
	}
	
	protected class ResetFieldListener implements ActionListener
	{
		
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			for (final IOptionsPanelObserver o : observers)
			{
				o.onActionFired(EVisualizerOptions.RESET_FIELD, true);
			}
		}
		
	}
}
