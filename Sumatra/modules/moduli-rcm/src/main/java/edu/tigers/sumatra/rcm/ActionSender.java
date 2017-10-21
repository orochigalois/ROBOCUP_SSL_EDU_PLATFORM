/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/


package edu.tigers.sumatra.rcm;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.dhbw.mannheim.tigers.sumatra.proto.BotActionCommandProtos.BotActionCommand;




public class ActionSender
{
	// --------------------------------------------------------------------------
	// --- instance variables ---------------------------------------------------
	// --------------------------------------------------------------------------
	
	private ICommandInterpreter		cmdInterpreter	= new CommandInterpreterStub();
	private final String					identifier;
	private final List<IRCMObserver>	observers		= new CopyOnWriteArrayList<IRCMObserver>();
																	
																	
	// --------------------------------------------------------------------------
	// --- constructor(s) -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ActionSender(final String identifier)
	{
		this.identifier = identifier;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void startSending()
	{
		if ((cmdInterpreter.getBot() != null))
		{
			cmdInterpreter.getBot().setControlledBy(identifier);
		}
	}
	
	
	
	public void stopSending()
	{
		cmdInterpreter.stopAll();
	}
	
	
	
	public void execute(final BotActionCommand newCmd)
	{
		cmdInterpreter.interpret(newCmd);
	}
	
	
	
	public void setInterpreter(final ICommandInterpreter interpreter)
	{
		if (interpreter == null)
		{
			cmdInterpreter = new CommandInterpreterStub();
		} else
		{
			cmdInterpreter = interpreter;
		}
	}
	
	
	
	public void addObserver(final IRCMObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final IRCMObserver observer)
	{
		observers.remove(observer);
	}
	
	
	
	public void notifyNextBot()
	{
		cmdInterpreter.stopAll();
		
		for (IRCMObserver observer : observers)
		{
			observer.onNextBot(this);
		}
	}
	
	
	
	public void notifyPrevBot()
	{
		cmdInterpreter.stopAll();
		
		for (IRCMObserver observer : observers)
		{
			observer.onPrevBot(this);
		}
	}
	
	
	
	public void notifyBotUnassigned()
	{
		cmdInterpreter.stopAll();
		cmdInterpreter.getBot().setControlledBy("");
		cmdInterpreter = new CommandInterpreterStub();
		
		for (IRCMObserver observer : observers)
		{
			observer.onBotUnassigned(this);
		}
	}
	
	
	
	public void notifyTimedout()
	{
		if (cmdInterpreter.getBot().getBotId().isBot())
		{
			notifyBotUnassigned();
		}
	}
	
	
	
	public final ICommandInterpreter getCmdInterpreter()
	{
		return cmdInterpreter;
	}
	
	
	
	public final String getIdentifier()
	{
		return identifier;
	}
}
