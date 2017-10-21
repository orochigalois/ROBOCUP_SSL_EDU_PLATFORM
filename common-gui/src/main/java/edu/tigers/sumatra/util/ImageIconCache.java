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

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;



public class ImageIconCache
{
	private static final ImageIconCache	globalCache	= new ImageIconCache();
	
	private Map<String, CacheEntry>		cache			= new ConcurrentHashMap<>();
	
	
	
	public ImageIconCache()
	{
	}
	
	
	
	public static ImageIconCache getGlobalCache()
	{
		return globalCache;
	}
	
	
	
	public ImageIcon getImage(final String name)
	{
		return getOrLoadImage(name);
	}
	
	
	
	public ImageIcon getImageOrDefault(final String name, final ImageIcon defValue)
	{
		ImageIcon image = getOrLoadImage(name);
		if (image == null)
		{
			return defValue;
		}
		return image;
	}
	
	
	
	public ImageIcon getImageSafe(final String name)
	{
		return getImageOrDefault(name, new ImageIcon());
	}
	
	
	private ImageIcon getOrLoadImage(final String name)
	{
		CacheEntry entry = cache.get(name);
		if (entry != null)
		{
			return entry.getImage();
		}
		
		ImageIcon image = loadImage(name);
		cache.put(name, new CacheEntry(image));
		return image;
	}
	
	
	
	private ImageIcon loadImage(final String name)
	{
		URL resource = ImageIconCache.class.getResource(name);
		if (resource != null)
		{
			return new ImageIcon(resource);
		}
		return null;
	}
	
	
	
	public void clearCache()
	{
		cache.clear();
	}
	
	private static class CacheEntry
	{
		
		private final ImageIcon	img;
		
		
		public CacheEntry(final ImageIcon img)
		{
			this.img = img;
		}
		
		
		public ImageIcon getImage()
		{
			return img;
		}
		
	}
}
