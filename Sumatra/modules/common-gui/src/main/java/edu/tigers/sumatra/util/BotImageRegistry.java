/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.util;

import javax.swing.ImageIcon;

import edu.tigers.sumatra.ids.BotID;



public class BotImageRegistry
{
	private static final String	prefix			= "/bots/";
	private static final String	yellowSuffix	= "Yellow";
	private static final String	blueSuffix		= "Blue";
	private static final String	extension		= ".png";
	
	private ImageIconCache			cache;
	
	
	
	public BotImageRegistry()
	{
		cache = new ImageIconCache();
	}
	
	
	
	public BotImageRegistry(final ImageIconCache cache)
	{
		this.cache = cache;
	}
	
	
	
	public ImageIcon getBotIcon(final BotID id)
	{
		if (id.isUninitializedID())
		{
			return null;
		}
		String name = buildIconName(id);
		return cache.getImage(name);
	}
	
	
	private String buildIconName(final BotID id)
	{
		StringBuilder path = new StringBuilder();
		path.append(prefix);
		switch (id.getTeamColor())
		{
			case BLUE:
				path.append(blueSuffix);
				break;
			case YELLOW:
				path.append(yellowSuffix);
				break;
			default:
				return null;
		}
		
		path.append(id.getNumber());
		path.append(extension);
		
		return path.toString();
	}
}
