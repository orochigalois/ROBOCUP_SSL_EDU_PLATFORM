/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.model.SumatraModel;
import net.infonode.gui.laf.InfoNodeLookAndFeel;



public abstract class AMainPresenter implements IMainFrameObserver
{
	private static final Logger	log						= Logger.getLogger(AMainPresenter.class.getName());
	
	private static final String	LAYOUT_CONFIG_PATH	= "./config/gui/";
	
	private final AMainFrame		mainFrame;
	
	static
	{
		InfoNodeLookAndFeel.install();
	}
	
	
	protected AMainPresenter(final AMainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		loadPosition(mainFrame);
		
		final String layout = SumatraModel.getInstance().getUserProperty(getLayoutKey(), getDefaultLayout());
		onLoadLayout(layout);
		
		refreshLayoutItems();
		
		mainFrame.addObserver(this);
	}
	
	
	
	protected final AMainFrame getMainFrame()
	{
		return mainFrame;
	}
	
	
	@Override
	public void onSaveLayout()
	{
		// --- Ask for the filename ---
		final String initial = SumatraModel.getInstance().getUserProperty(getLayoutKey());
		String filename = JOptionPane.showInputDialog(null, "Please specify the name of the layout file:", initial);
		
		if (filename == null)
		{
			return;
		}
		
		// --- add .ly if necessary ---
		if (!filename.endsWith(".ly"))
		{
			filename += ".ly";
		}
		
		final String filenameWithPath = LAYOUT_CONFIG_PATH + filename;
		
		mainFrame.saveLayout(filenameWithPath);
		
		// --- DEBUG msg ---
		log.debug("Saved layout to: " + filename);
		
		refreshLayoutItems();
	}
	
	
	@Override
	public void onDeleteLayout()
	{
		// --- delete current layout ---
		final String currentLayout = getCurrentLayout();
		final File file = new File(LAYOUT_CONFIG_PATH + currentLayout);
		
		// --- if file exists -> delete ---
		if (file.exists())
		{
			boolean deleted = file.delete();
			if (!deleted)
			{
				log.error("Could not delete file:" + file);
			}
		}
		
		refreshLayoutItems();
		
		log.debug("Deleted layout: " + currentLayout);
	}
	
	
	@Override
	public void onRefreshLayoutItems()
	{
		refreshLayoutItems();
	}
	
	
	@Override
	public void onLoadLayout(final String filename)
	{
		String path = LAYOUT_CONFIG_PATH + filename;
		setCurrentLayout(filename);
		
		if (!new File(path).exists())
		{
			log.warn("Layout file: " + path + " does not exist, falling back to " + getDefaultLayout());
			path = LAYOUT_CONFIG_PATH + getDefaultLayout();
			setCurrentLayout(getDefaultLayout());
		}
		
		mainFrame.loadLayout(path);
		
		log.trace("Loaded layout: " + path);
	}
	
	
	@Override
	public void onExit()
	{
		// ### Persist user settings
		final Properties appProps = SumatraModel.getInstance().getUserSettings();
		
		// --- save gui position ---
		savePosition(mainFrame, appProps);
		
		// save current layout to separat file
		saveCurrentLayout();
		// save last layout for next usage
		SumatraModel.getInstance().setUserProperty(getLayoutKey(), getLastLayoutFile());
	}
	
	
	
	protected abstract String getLastLayoutFile();
	
	
	
	protected abstract String getLayoutKey();
	
	
	
	protected abstract String getDefaultLayout();
	
	
	private void refreshLayoutItems()
	{
		final ArrayList<String> filenames = new ArrayList<String>();
		
		// --- read all available layouts from guiLayout-folder ---
		final File dir = new File(LAYOUT_CONFIG_PATH);
		final File[] fileList = dir.listFiles();
		
		if (fileList != null)
		{
			for (final File file : fileList)
			{
				if (!file.isHidden() && !file.getName().startsWith("."))
				{
					filenames.add(file.getName());
				}
			}
		}
		
		mainFrame.setMenuLayoutItems(filenames);
		mainFrame.selectLayoutItem(getCurrentLayout());
	}
	
	
	private void saveCurrentLayout()
	{
		final String filenameWithPath = LAYOUT_CONFIG_PATH + getLastLayoutFile();
		
		mainFrame.saveLayout(filenameWithPath);
	}
	
	
	
	private void loadPosition(final JFrame frame)
	{
		log.trace("Load Position");
		final String prefix = this.getClass().getName();
		Properties appProps = SumatraModel.getInstance().getUserSettings();
		
		final int displayCount = getInt(appProps, prefix + ".disyplayCount", 1);
		int x = 0;
		int y = 0;
		
		if (displayCount == getNumberOfDisplays())
		{
			
			x = getInt(appProps, prefix + ".x", 0);
			y = getInt(appProps, prefix + ".y", 0);
		}
		frame.setLocation(x, y);
		log.trace("Position: " + x + "," + y);
		
		final String strMaximized = appProps.getProperty(prefix + ".maximized", "true");
		final boolean maximized = Boolean.valueOf(strMaximized);
		
		if (maximized)
		{
			log.trace("window maximized");
			frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
		} else
		{
			final int w = getInt(appProps, prefix + ".w", 1456);
			final int h = getInt(appProps, prefix + ".h", 886);
			
			frame.setSize(new Dimension(w, h));
		}
	}
	
	
	
	private void savePosition(final JFrame frame, final Properties appProps)
	{
		log.trace("Save position");
		final String prefix = this.getClass().getName();
		appProps.setProperty(prefix + ".disyplayCount", "" + getNumberOfDisplays());
		appProps.setProperty(prefix + ".x", "" + frame.getX());
		appProps.setProperty(prefix + ".y", "" + frame.getY());
		appProps.setProperty(prefix + ".w", "" + frame.getWidth());
		appProps.setProperty(prefix + ".h", "" + frame.getHeight());
		appProps.setProperty(prefix + ".maximized",
				String.valueOf((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH));
	}
	
	
	
	private int getInt(final Properties props, final String name, final int defaultValue)
	{
		final String v = props.getProperty(name);
		if (v == null)
		{
			return defaultValue;
		}
		return Integer.parseInt(v);
		
	}
	
	
	
	private int getNumberOfDisplays()
	{
		try
		{
			// Get local graphics environment
			final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			return env.getScreenDevices().length;
			
		} catch (final HeadlessException e)
		{
			log.fatal("HeadlessException", e);
		}
		
		return 1;
	}
	
	
	private String getCurrentLayout()
	{
		return SumatraModel.getInstance().getUserProperty(getLayoutKey(), getDefaultLayout());
	}
	
	
	private void setCurrentLayout(final String newLayout)
	{
		SumatraModel.getInstance().setUserProperty(getLayoutKey(), newLayout);
	}
}
