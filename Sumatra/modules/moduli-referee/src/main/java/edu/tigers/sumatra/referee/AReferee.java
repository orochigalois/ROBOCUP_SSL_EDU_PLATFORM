/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.referee;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.tigers.moduli.AModule;
import edu.tigers.sumatra.Referee.SSL_Referee;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.cam.IBallReplacer;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;



public abstract class AReferee extends AModule implements IBallReplacer
{
	
	public static final String					MODULE_TYPE				= "AReferee";
	
	public static final String					MODULE_ID				= "referee";
	
	private final List<IRefereeObserver>	observers				= new CopyOnWriteArrayList<>();
	
	private boolean								receiveExternalMsg	= true;
	private long									lastRefMsgCounter		= 0;
	
	
	
	public AReferee()
	{
	}
	
	
	
	public void addObserver(final IRefereeObserver observer)
	{
		synchronized (observers)
		{
			observers.add(observer);
		}
	}
	
	
	
	public void removeObserver(final IRefereeObserver observer)
	{
		synchronized (observers)
		{
			observers.remove(observer);
		}
	}
	
	
	protected void notifyNewRefereeMsg(final SSL_Referee refMsg)
	{
		for (final IRefereeObserver observer : observers)
		{
			observer.onNewRefereeMsg(refMsg);
		}
	}
	
	
	
	public abstract void sendOwnRefereeMsg(Command cmd, int goalsBlue, int goalsYellow, int timeLeft,
			final long timestamp, IVector2 placementPos);
	
	
	
	@Override
	public abstract void replaceBall(IVector3 pos, IVector3 vel);
	
	
	
	protected boolean isNewMessage(final SSL_Referee msg)
	{
		if (msg.getCommandCounter() != lastRefMsgCounter)
		{
			lastRefMsgCounter = msg.getCommandCounter();
			return true;
		}
		
		return false;
	}
	
	
	
	public boolean isReceiveExternalMsg()
	{
		return receiveExternalMsg;
	}
	
	
	
	public void setReceiveExternalMsg(final boolean receiveExternalMsg)
	{
		this.receiveExternalMsg = receiveExternalMsg;
	}
}
