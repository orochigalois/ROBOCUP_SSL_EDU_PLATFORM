/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class MotionContext
{
	private final Map<BotID, BotInfo> bots = new HashMap<>(12);
	
	
	
	public static class BotInfo
	{
		private final BotID		botId;
		private final IVector3	pos;
		private double				kickSpeed				= 0;
		private boolean			chip						= false;
		private double				dribbleRpm				= 0;
		private double				center2DribblerDist	= Geometry.getCenter2DribblerDistDefault();
		private IVector3			vel						= Vector3.ZERO_VECTOR;
		private boolean			ballContact				= false;
		
		
		
		public BotInfo(final BotID botId, final IVector3 pos)
		{
			this.botId = botId;
			this.pos = pos;
		}
		
		
		
		public final double getKickSpeed()
		{
			return kickSpeed;
		}
		
		
		
		public final void setKickSpeed(final double kickSpeed)
		{
			this.kickSpeed = kickSpeed;
		}
		
		
		
		public final IVector3 getPos()
		{
			return pos;
		}
		
		
		
		public final BotID getBotId()
		{
			return botId;
		}
		
		
		
		public double getCenter2DribblerDist()
		{
			return center2DribblerDist;
		}
		
		
		
		public void setCenter2DribblerDist(final double center2DribblerDist)
		{
			this.center2DribblerDist = center2DribblerDist;
		}
		
		
		
		public boolean isChip()
		{
			return chip;
		}
		
		
		
		public void setChip(final boolean chip)
		{
			this.chip = chip;
		}
		
		
		
		public double getDribbleRpm()
		{
			return dribbleRpm;
		}
		
		
		
		public void setDribbleRpm(final double dribbleRpm)
		{
			this.dribbleRpm = dribbleRpm;
		}
		
		
		
		public IVector3 getVel()
		{
			return vel;
		}
		
		
		
		public void setVel(final IVector3 vel)
		{
			this.vel = vel;
		}
		
		
		
		public boolean isBallContact()
		{
			return ballContact;
		}
		
		
		
		public void setBallContact(final boolean ballContact)
		{
			this.ballContact = ballContact;
		}
	}
	
	
	
	public final Map<BotID, BotInfo> getBots()
	{
		return bots;
	}
}
