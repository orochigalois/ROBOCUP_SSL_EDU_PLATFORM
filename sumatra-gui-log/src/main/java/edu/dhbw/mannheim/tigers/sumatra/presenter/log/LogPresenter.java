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

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import edu.dhbw.mannheim.tigers.sumatra.view.log.IFilterPanelObserver;
import edu.dhbw.mannheim.tigers.sumatra.view.log.ISlidePanelObserver;
import edu.dhbw.mannheim.tigers.sumatra.view.log.ITreePanelObserver;
import edu.dhbw.mannheim.tigers.sumatra.view.log.LogPanel;
import edu.tigers.moduli.listenerVariables.ModulesState;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.views.ISumatraView;
import edu.tigers.sumatra.views.ISumatraViewPresenter;



public class LogPresenter extends WriterAppender implements ISumatraViewPresenter, IFilterPanelObserver,
		ISlidePanelObserver, ITreePanelObserver
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	// colors
	
	public static final Color						DEFAULT_COLOR_ALL		= new Color(0, 0, 0);
	
	public static final Color						DEFAULT_COLOR_FATAL	= new Color(128, 0, 128);
	
	public static final Color						DEFAULT_COLOR_ERROR	= new Color(255, 0, 0);
	
	public static final Color						DEFAULT_COLOR_WARN	= new Color(0, 0, 255);
	
	public static final Color						DEFAULT_COLOR_INFO	= new Color(0, 128, 0);
	
	public static final Color						DEFAULT_COLOR_DEBUG	= new Color(96, 96, 96);
	
	public static final Color						DEFAULT_COLOR_TRACE	= new Color(0, 0, 0);
	
	public static final Color						DEFAULT_COLOR			= new Color(0, 0, 0);
																						
	private LogPanel									logPanel					= null;
																						
	
	private static final int						DISPLAY_CAPACITY		= 1000;
																						
	private final LogEventBuffer					eventBuffer				= new LogEventBuffer();
																						
	private List<String>								allowedClasses			= new ArrayList<String>();
	private List<String>								allowedStrings			= new ArrayList<String>();
																						
	private static final String					LOG_LEVEL_KEY			= LogPresenter.class.getName() + ".loglevel";
	private Level										logLevel;
	private int											numFatals				= 0;
	private int											numErrors				= 0;
	private int											numWarnings				= 0;
																						
	private final Map<Integer, AttributeSet>	attributeSets			= new HashMap<Integer, AttributeSet>();
																						
	private final Object								eventSync				= new Object();
																						
	private boolean									freeze					= false;
																						
																						
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public LogPresenter(final boolean addAppender)
	{
		// set initial log level to info to avoid performance issues
		String strLevel = SumatraModel.getInstance().getUserProperty(LOG_LEVEL_KEY, "INFO");
		logLevel = Level.toLevel(strLevel);
		
		logPanel = new LogPanel(DISPLAY_CAPACITY, logLevel);
		
		logPanel.getFilterPanel().addObserver(this);
		logPanel.getSlidePanel().addObserver(this);
		logPanel.getTreePanel().addObserver(this);
		
		// set internal output layout -> see log4j.properties
		setLayout(new PatternLayout("%d{ABSOLUTE} %-5p [%t|%c{1}] %m%n"));
		if (addAppender)
		{
			Logger.getRootLogger().addAppender(this);
		}
		
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		attributeSets.put(Priority.ALL_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_ALL));
		attributeSets.put(Priority.FATAL_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_FATAL));
		attributeSets.put(Priority.ERROR_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_ERROR));
		attributeSets.put(Priority.WARN_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_WARN));
		attributeSets.put(Priority.INFO_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_INFO));
		attributeSets.put(Priority.DEBUG_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_DEBUG));
		attributeSets.put(Level.TRACE_INT,
				sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, DEFAULT_COLOR_TRACE));
	}
	
	
	
	public void clearEventStorage()
	{
		logPanel.getTextPane().clear();
		synchronized (eventSync)
		{
			eventBuffer.clear();
		}
		onLevelChanged(logLevel);
	}
	
	
	@Override
	public void append(final LoggingEvent logEvent)
	{
		synchronized (eventSync)
		{
			eventBuffer.append(logEvent);
		}
		
		if (!freeze)
		{
			appendLoggingEvent(logEvent);
		}
	}
	
	
	@Override
	public void onNewClassList(final List<String> classes)
	{
		allowedClasses = classes;
		
		reappendAllEvents();
	}
	
	
	@Override
	public void onLevelChanged(final Level level)
	{
		if (level.equals(logLevel))
		{
			return;
		}
		
		logLevel = level;
		SumatraModel.getInstance().setUserProperty(LOG_LEVEL_KEY, level.toString());
		logPanel.getTextPane().clear();
		
		reappendAllEvents();
	}
	
	
	@Override
	public void onNewFilter(final List<String> allowed)
	{
		allowedStrings = allowed;
		
		reappendAllEvents();
	}
	
	
	private void reappendAllEvents()
	{
		logPanel.getTextPane().clear();
		numFatals = 0;
		numErrors = 0;
		numWarnings = 0;
		
		synchronized (eventSync)
		{
			for (final LoggingEvent event : eventBuffer)
			{
				appendLoggingEvent(event);
			}
		}
	}
	
	
	
	public void appendLine()
	{
		final StyleContext sc = StyleContext.getDefaultStyleContext();
		final AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.black);
		logPanel.getTextPane().append("--------------------------------------------------------------\n", aset);
	}
	
	
	private void appendLoggingEvent(final LoggingEvent event)
	{
		if (!checkFilters(event))
		{
			return;
		}
		
		switch (event.getLevel().toInt())
		{
			case Priority.FATAL_INT:
				numFatals++;
				logPanel.getFilterPanel().setNumFatals(numFatals);
				break;
			case Priority.ERROR_INT:
				numErrors++;
				logPanel.getFilterPanel().setNumErrors(numErrors);
				break;
			case Priority.WARN_INT:
				numWarnings++;
				logPanel.getFilterPanel().setNumWarnings(numWarnings);
				break;
			default:
		}
		
		logPanel.getTextPane().append(layout.format(event), attributeSets.get(event.getLevel().toInt()));
	}
	
	
	
	private boolean checkClassFilter(final LoggingEvent event)
	{
		if (allowedClasses.isEmpty())
		{
			return true;
		}
		
		// get classname of loggingEvent
		final String from = new PatternLayout("%C{1}").format(event);
		
		for (final String allowed : allowedClasses)
		{
			if (from.startsWith(allowed))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean checkForLogLevel(final LoggingEvent event)
	{
		if (event.getLevel().isGreaterOrEqual(logLevel))
		{
			return true;
		}
		return false;
	}
	
	
	
	private boolean checkStringFilter(final LoggingEvent event)
	{
		if (allowedStrings.isEmpty())
		{
			return true;
		}
		
		for (final String allowed : allowedStrings)
		{
			if (layout.format(event).contains(allowed))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public boolean checkFilters(final LoggingEvent event)
	{
		if (!checkStringFilter(event) || !checkClassFilter(event) || !checkForLogLevel(event))
		{
			return false;
		}
		return true;
	}
	
	
	@Override
	public Component getComponent()
	{
		return logPanel;
	}
	
	
	@Override
	public ISumatraView getSumatraView()
	{
		return logPanel;
	}
	
	
	@Override
	public void onModuliStateChanged(final ModulesState state)
	{
	}
	
	
	@Override
	public void onFreeze(final boolean freeze)
	{
		this.freeze = freeze;
		eventBuffer.setFreeze(freeze);
		if (!freeze)
		{
			reappendAllEvents();
		}
	}
}
