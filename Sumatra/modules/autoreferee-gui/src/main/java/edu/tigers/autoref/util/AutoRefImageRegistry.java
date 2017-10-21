/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.util;

import javax.swing.ImageIcon;

import edu.tigers.autoreferee.engine.events.EGameEvent;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.util.BotImageRegistry;
import edu.tigers.sumatra.util.ImageIconCache;



public class AutoRefImageRegistry
{
	private static final String				blueFlagName	= "/blue_flag.png";
	private static final String				yellowFlagName	= "/yellow_flag.png";
	
	private static final String				blueCardName	= "/blue_card.png";
	private static final String				yellowCardName	= "/yellow_card.png";
	
	private static final String				urgentSignName	= "/urgent.png";
	private static final String				bulbSignName	= "/bulb.png";
	
	private static final String				rightArrowName	= "/right-arrow.png";
	
	private static final ImageIconCache		cache				= ImageIconCache.getGlobalCache();
	private static final BotImageRegistry	botRegistry		= new BotImageRegistry(cache);
	
	
	
	public static ImageIcon getTeamFlagIcon(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return cache.getImage(blueFlagName);
			case YELLOW:
				return cache.getImage(yellowFlagName);
			default:
				return null;
		}
	}
	
	
	
	public static ImageIcon getTeamCard(final ETeamColor color)
	{
		switch (color)
		{
			case BLUE:
				return cache.getImage(blueCardName);
			case YELLOW:
				return cache.getImage(yellowCardName);
			default:
				return null;
		}
	}
	
	
	
	public static ImageIcon getBotIcon(final BotID id)
	{
		return botRegistry.getBotIcon(id);
	}
	
	
	
	public static ImageIcon getEventIcon(final EGameEvent eventType)
	{
		switch (eventType.getCategory())
		{
			case GENERAL:
				return cache.getImage(bulbSignName);
			case VIOLATION:
				return cache.getImage(urgentSignName);
			default:
				return null;
		}
	}
	
	
	
	public static ImageIcon getRightArrow()
	{
		return cache.getImage(rightArrowName);
	}
}
