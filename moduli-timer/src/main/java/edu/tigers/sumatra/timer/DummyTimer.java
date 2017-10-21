/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.timer;


public class DummyTimer implements ITimer
{
	@Override
	public void stop(final ETimable timable, final long id)
	{
	}
	
	
	@Override
	public void stop(final ETimable timable, final long id, final int customId)
	{
	}
	
	
	@Override
	public void start(final ETimable timable, final long id)
	{
	}
	
	
	@Override
	public void start(final ETimable timable, final long id, final int customId)
	{
	}
}
