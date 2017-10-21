/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.gui.view;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import net.infonode.docking.RootWindow;
import net.infonode.docking.properties.RootWindowProperties;
import edu.dhbw.mannheim.tigers.sumatra.presenter.log.LogView;
import edu.tigers.autoref.view.ballspeed.BallSpeedView;
import edu.tigers.autoref.view.gamelog.GameLogView;
import edu.tigers.autoref.view.humanref.HumanRefView;
import edu.tigers.autoref.view.main.AutoRefView;
import edu.tigers.autoref.view.visualizer.VisualizerAutoRefView;
import edu.tigers.sumatra.AMainFrame;
import edu.tigers.sumatra.config.ConfigEditorView;



public class AutoRefMainFrame extends AMainFrame
{
	
	private static final long	serialVersionUID	= 8459059861313702417L;
	
	
	
	public AutoRefMainFrame()
	{
		setTitle("Autoreferee");
		
		addView(new LogView(true));
		addView(new VisualizerAutoRefView());
		addView(new ConfigEditorView());
		addView(new AutoRefView());
		addView(new GameLogView());
		addView(new BallSpeedView());
		addView(new HumanRefView());
		
		updateViewMenu();
		fillMenuBar();
	}
	
	
	
	private void fillMenuBar()
	{
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new Exit());
		fileMenu.add(exitItem);
		
		getJMenuBar().add(fileMenu);
		
		
		super.addMenuItems();
		
	}
	
	
	@Override
	protected ImageIcon getFrameIcon()
	{
		return loadIconImage("/whistle.png");
	}
	
	
	@Override
	protected RootWindow createRootWindow()
	{
		RootWindow rootWindow = super.createRootWindow();
		
		
		RootWindowProperties windowProps = rootWindow.getRootWindowProperties();
		windowProps.getFloatingWindowProperties().setUseFrame(true);
		
		return rootWindow;
	}
}
