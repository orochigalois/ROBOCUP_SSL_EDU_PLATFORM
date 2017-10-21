/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.remote;



public interface ICommandResult
{
	
	public boolean isCompleted();
	
	
	
	public boolean isSuccessful();
	
	
	
	public boolean hasFailed();
}
