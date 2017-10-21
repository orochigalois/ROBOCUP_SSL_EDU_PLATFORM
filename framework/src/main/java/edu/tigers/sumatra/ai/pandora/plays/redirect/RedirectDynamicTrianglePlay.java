/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



public class RedirectDynamicTrianglePlay extends ARedirectPlay
{
	@Configurable
	private static double	angleMinDeg	= 10;
													
	@Configurable
	private static double	angleMaxDeg	= 80;
													
	@Configurable
	private static double	angleStep	= 0.1;
													
	private double				step			= -angleStep;
	private double				angle			= angleMaxDeg * AngleMath.DEG_TO_RAD;
	private long				tStart;
									
	@Configurable
	private static double	distance		= 3000;
													
	@Configurable
	private static IVector2	redirectPos	= new Vector2(2000, 1000);
													
	@Configurable
	private static IVector2	dir			= new Vector2(-4, -1);
													
	private AthenaAiFrame	latestFrame	= null;
													
													
	
	public RedirectDynamicTrianglePlay()
	{
		super(EPlay.REDIRECT_DYNAMIC_TRIANGLE);
	}
	
	
	@Override
	public void updateBeforeRoles(final AthenaAiFrame frame)
	{
		latestFrame = frame;
		super.updateBeforeRoles(frame);
		
		if (tStart == 0)
		{
			tStart = frame.getWorldFrame().getTimestamp();
		}
		if (((frame.getWorldFrame().getTimestamp() - tStart) / 1e9) > 3)
		{
			angle += step;
			double angleMin = (angleMinDeg * AngleMath.DEG_TO_RAD);
			double angleMax = (angleMaxDeg * AngleMath.DEG_TO_RAD);
			if (angle < angleMin)
			{
				angle = angleMin;
				step *= -1;
			}
			if (angle > angleMax)
			{
				angle = angleMax;
				step *= -1;
			}
			tStart = frame.getWorldFrame().getTimestamp();
		}
		
	}
	
	
	@Override
	protected List<IVector2> getFormation()
	{
		IVector2 destSec1 = redirectPos.addNew(dir.turnNew((angle) / 2.0).scaleTo(distance));
		IVector2 destSec2 = redirectPos.addNew(dir.turnNew((-angle) / 2.0).scaleTo(distance));
		List<IVector2> formation = new ArrayList<IVector2>(3);
		formation.add(redirectPos);
		formation.add(destSec1);
		formation.add(destSec2);
		return formation;
	}
	
	
	@Override
	protected void getReceiveModes(final Map<ARole, EReceiveMode> modes)
	{
		if (getRoles().size() == 3)
		{
			modes.put(getRoles().get(0), EReceiveMode.REDIRECT);
			modes.put(getRoles().get(1), EReceiveMode.REDIRECT);
			modes.put(getRoles().get(2), EReceiveMode.REDIRECT);
		}
	}
	
	
	@Override
	protected int getReceiverTarget(final int roleIdx)
	{
		switch (roleIdx)
		{
			case 0:
				return getRoleIdxFarerFromBall();
			case 1:
				return 0;
			case 2:
				return 0;
		}
		return 0;
	}
	
	
	private int getRoleIdxFarerFromBall()
	{
		if (getRoles().size() < 2)
		{
			return 0;
		}
		double maxDist = 0;
		int idx = 1;
		for (int i = 1; i < getRoles().size(); i++)
		{
			double dist = GeoMath.distancePP(latestFrame.getWorldFrame().getBall().getPos(), getRoles().get(i).getPos());
			if (dist > maxDist)
			{
				maxDist = dist;
				idx = i;
			}
		}
		return idx;
	}
}
