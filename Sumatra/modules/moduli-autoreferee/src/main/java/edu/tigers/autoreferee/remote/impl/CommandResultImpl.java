/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.remote.impl;

import edu.tigers.autoreferee.remote.ICommandResult;



public class CommandResultImpl implements ICommandResult
{
	private boolean	completed	= false;
	private boolean	successful	= false;
	
	
	@Override
	public synchronized boolean isCompleted()
	{
		return completed;
	}
	
	
	@Override
	public synchronized boolean isSuccessful()
	{
		return successful;
	}
	
	
	@Override
	public synchronized boolean hasFailed()
	{
		return (completed == true) && (successful == false);
	}
	
	
	
	public synchronized void setResult(final boolean completed, final boolean successful)
	{
		this.completed = completed;
		this.successful = successful;
	}
	
	
	
	public synchronized void setSuccessful()
	{
		setResult(true, true);
	}
	
	
	
	public synchronized void setFailed()
	{
		setResult(true, false);
	}
	
}
