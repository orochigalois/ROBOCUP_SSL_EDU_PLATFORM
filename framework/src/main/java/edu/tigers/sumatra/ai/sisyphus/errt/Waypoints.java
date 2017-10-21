/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.errt;

import java.util.ArrayList;
import java.util.List;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.sisyphus.errt.tree.Node;
import edu.tigers.sumatra.math.IVector2;



public class Waypoints
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	// WPC
	@Configurable(comment = "size of waypointcache", defValue = "100")
	private static int				wpcSize						= 100;
	@Configurable(comment = "how much target can differ from target of last cycle to use WPC [mm]", defValue = "100")
	private static double			tollerableTargetShift	= 100;
																			
	
	private final List<IVector2>	waypoints					= new ArrayList<IVector2>();
	
	private Node						goal;
											
											
	static
	{
		ConfigRegistration.registerClass("sisyphus", Waypoints.class);
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Waypoints()
	{
		goal = null;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public boolean equalsGoal(final Node newGoal)
	{
		if (goal != null)
		{
			return goal.equals(newGoal, tollerableTargetShift);
		}
		return false;
	}
	
	
	
	public void clear(final Node newGoal)
	{
		waypoints.clear();
		goal = newGoal;
	}
	
	
	
	public void fillWPC(final List<IVector2> waypointNodes, final Node goal)
	{
		waypoints.addAll(waypointNodes);
		final int waypointsSize = waypoints.size();
		for (int i = wpcSize; i < waypointsSize; i++)
		{
			int rmWaypoint = (int) Math.round(Math.random() * waypoints.size());
			if (rmWaypoint >= 1)
			{
				rmWaypoint -= 1;
			}
			waypoints.remove(rmWaypoint);
		}
		this.goal = goal;
	}
	
	
	
	public boolean isEmpty()
	{
		return (waypoints.isEmpty());
	}
	
	
	
	public IVector2 getArbitraryNode()
	{
		if (waypoints.size() > 0)
		{
			return waypoints.get((int) Math.round(Math.random() * (waypoints.size() - 1)));
		}
		return null;
	}
	
	
	
	public int size()
	{
		return waypoints.size();
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
