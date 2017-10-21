/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.metis.defense.data;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.ITrackedObject;



@Persistent(version = 1)
public class DefensePoint extends Vector2
{
	private final IVector2		protectAgainst;
	private final FoeBotData	foeBotData;
										
										
	@SuppressWarnings("unused")
	private DefensePoint()
	{
		protectAgainst = null;
		foeBotData = null;
	}
	
	
	
	public DefensePoint(final IVector2 point, final ITrackedObject protectAgainst)
	{
		super(point);
		this.protectAgainst = protectAgainst.getPos();
		foeBotData = null;
	}
	
	
	
	public DefensePoint(final DefensePoint copyPoint)
	{
		
		super(copyPoint);
		protectAgainst = copyPoint.protectAgainst;
		foeBotData = copyPoint.foeBotData;
	}
	
	
	
	public DefensePoint(final IVector2 point, final FoeBotData foeBotData)
	{
		super(point);
		protectAgainst = foeBotData.getFoeBot().getPos();
		this.foeBotData = foeBotData;
	}
	
	
	
	public DefensePoint(final IVector2 point, final IVector2 protectAgainst)
	{
		super(point);
		this.protectAgainst = protectAgainst;
		foeBotData = null;
	}
	
	
	
	public DefensePoint(final IVector2 point)
	{
		super(point);
		protectAgainst = null;
		foeBotData = null;
	}
	
	
	
	public final IVector2 getProtectAgainst()
	{
		return protectAgainst;
	}
	
	
	
	public final FoeBotData getFoeBotData()
	{
		return foeBotData;
	}
	
	
}
