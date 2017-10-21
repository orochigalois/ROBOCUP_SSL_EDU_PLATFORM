/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.multiteammessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.tigers.moduli.AModule;
import edu.tigers.sumatra.ai.data.MultiTeamMessage;



public abstract class AMultiTeamMessage extends AModule
{
	
	public static final String								MODULE_TYPE	= "AMultiTeamMessage";
	
	public static final String								MODULE_ID	= "multiTeamMessage";
																					
	private final List<IMultiTeamMessageConsumer>	consumers	= new CopyOnWriteArrayList<>();
																					
																					
	protected void notifyNewMultiTeamMessage(final MultiTeamMessage message)
	{
		for (final IMultiTeamMessageConsumer observer : consumers)
		{
			observer.onNewMultiTeamMessage(message);
		}
	}
	
	
	
	public void addMultiTeamMessageConsumer(final IMultiTeamMessageConsumer consumer)
	{
		consumers.add(consumer);
	}
	
	
	
	public void removeMultiTeamMessageConsumer(final IMultiTeamMessageConsumer consumer)
	{
		consumers.remove(consumer);
	}
	
	
	
	public final List<IMultiTeamMessageConsumer> getConsumers()
	{
		return consumers;
	}
}
