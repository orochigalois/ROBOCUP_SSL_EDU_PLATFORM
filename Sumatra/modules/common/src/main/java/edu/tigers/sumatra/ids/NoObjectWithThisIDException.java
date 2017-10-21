/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ids;


public class NoObjectWithThisIDException extends RuntimeException
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long serialVersionUID = -6667693255367884125L;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public NoObjectWithThisIDException()
	{
		super();
	}
	
	
	
	public NoObjectWithThisIDException(final String msg)
	{
		super(msg);
	}
	
	
	
	public NoObjectWithThisIDException(final String msg, final Throwable throwable)
	{
		super(msg, throwable);
	}
}
