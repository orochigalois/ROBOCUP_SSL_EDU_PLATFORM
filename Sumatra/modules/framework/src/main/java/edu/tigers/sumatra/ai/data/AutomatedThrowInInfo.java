/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector2;



public class AutomatedThrowInInfo
{
	
	public enum EPrepareThrowInAction
	{
		
		MOVE_BALL_FROM_WALL,
		
		PASS_TO_RECEIVER_DIRECTLY,
		
		FINE_ADJUSTMENT;
	}
	
	private EPrepareThrowInAction	action			= null;
	private IVector2					pos				= null;
	private boolean					receiverReady	= false;
	private boolean					finished			= false;
	private List<BotID>				desiredBots		= new ArrayList<BotID>();
	
	
	
	public EPrepareThrowInAction getAction()
	{
		return action;
	}
	
	
	
	public void setAction(final EPrepareThrowInAction action)
	{
		this.action = action;
	}
	
	
	
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public void setPos(final IVector2 pos)
	{
		this.pos = pos;
	}
	
	
	
	public boolean isReceiverReady()
	{
		return receiverReady;
	}
	
	
	
	public void setReceiverReady(final boolean receiverReady)
	{
		this.receiverReady = receiverReady;
	}
	
	
	
	public boolean isFinished()
	{
		return finished;
	}
	
	
	
	public void setFinished(final boolean finished)
	{
		this.finished = finished;
	}
	
	
	
	public List<BotID> getDesiredBots()
	{
		return desiredBots;
	}
	
	
	
	public void setDesiredBots(final List<BotID> desiredBots)
	{
		this.desiredBots = desiredBots;
	}
	
}
