/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee;

import java.util.Optional;



@FunctionalInterface
public interface CheckedRunnable
{
	
	
	public static Optional<Exception> execAndCatchAll(final CheckedRunnable f)
	{
		return f.execAndCatchAll();
	}
	
	
	
	public default Optional<Exception> execAndCatchAll()
	{
		try
		{
			run();
		} catch (Exception e)
		{
			return Optional.of(e);
		}
		return Optional.empty();
	}
	
	
	
	public void run() throws Exception;
}
