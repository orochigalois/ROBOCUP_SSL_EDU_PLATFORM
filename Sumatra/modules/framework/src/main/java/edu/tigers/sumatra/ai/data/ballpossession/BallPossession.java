/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.ballpossession;

import java.io.Serializable;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.wp.data.WorldFrame;



@Persistent
public class BallPossession implements Serializable
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long	serialVersionUID	= -1819701506143270823L;
	
	private EBallPossession		eBallPossession;
	private BotID					opponentsId;
	private BotID					tigersId;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public BallPossession()
	{
		eBallPossession = EBallPossession.UNKNOWN;
		opponentsId = BotID.get();
		tigersId = BotID.get();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public boolean isPossessedByOtherSubTeam(final WorldFrame wFrame)
	{
		if (tigersId.isBot() && wFrame.tigerBotsVisible.containsKey(tigersId)
				&& !wFrame.tigerBotsAvailable.containsKey(tigersId))
		{
			return true;
		}
		return false;
	}
	
	
	
	public boolean isEqual(final BallPossession obj)
	{
		return (eBallPossession != obj.getEBallPossession()) && !opponentsId.equals(obj.getOpponentsId())
				&& !tigersId.equals(obj.getTigersId());
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public EBallPossession getEBallPossession()
	{
		return eBallPossession;
	}
	
	
	
	public void setEBallPossession(final EBallPossession eBallPossession)
	{
		this.eBallPossession = eBallPossession;
	}
	
	
	
	public BotID getOpponentsId()
	{
		return opponentsId;
	}
	
	
	
	public void setOpponentsId(final BotID opponentsId)
	{
		this.opponentsId = opponentsId;
	}
	
	
	
	public BotID getTigersId()
	{
		return tigersId;
	}
	
	
	
	public void setTigersId(final BotID tigersId)
	{
		this.tigersId = tigersId;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((eBallPossession == null) ? 0 : eBallPossession.hashCode());
		result = (prime * result) + ((opponentsId == null) ? 0 : opponentsId.hashCode());
		result = (prime * result) + ((tigersId == null) ? 0 : tigersId.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final BallPossession other = (BallPossession) obj;
		if (eBallPossession != other.eBallPossession)
		{
			return false;
		}
		if (opponentsId == null)
		{
			if (other.opponentsId != null)
			{
				return false;
			}
		} else if (!opponentsId.equals(other.opponentsId))
		{
			return false;
		}
		if (tigersId == null)
		{
			if (other.tigersId != null)
			{
				return false;
			}
		} else if (!tigersId.equals(other.tigersId))
		{
			return false;
		}
		return true;
	}
}
