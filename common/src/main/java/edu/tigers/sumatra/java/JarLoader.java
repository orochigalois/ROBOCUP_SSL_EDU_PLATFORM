/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.java;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.log4j.Logger;



public class JarLoader
{
	private static final Logger	log	= Logger.getLogger(JarLoader.class.getName());
	
	
	private JarLoader()
	{
	}
	
	
	
	public static void loadJar(final String path)
	{
		URL jarURL;
		try
		{
			jarURL = new URL("file://" + path);
		} catch (MalformedURLException err)
		{
			log.error("Could not create URL with path " + path);
			return;
		}
		Class<URLClassLoader> sysclass = URLClassLoader.class;
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		try
		{
			Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
			AccessController.doPrivileged((PrivilegedAction<Void>)
					() -> {
						method.setAccessible(true);
						return null; // nothing to return
				}
					);
			method.invoke(urlClassLoader, new Object[] { jarURL });
		} catch (Throwable t)
		{
			log.error("Could not load jar with path: " + path);
			return;
		}
		log.debug("Loaded " + path);
	}
}
