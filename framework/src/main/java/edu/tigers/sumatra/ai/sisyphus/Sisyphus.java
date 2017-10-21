/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.sisyphus.filter.IPathFilter;
import edu.tigers.sumatra.ai.sisyphus.filter.StubPathFilter;
import edu.tigers.sumatra.ai.sisyphus.finder.IPathFinder;
import edu.tigers.sumatra.ai.sisyphus.finder.StubPathFinder;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.shapes.path.IPath;
import edu.x.framework.skillsystem.MovementCon;





public final class Sisyphus
{
	private static final Random			RND									= new Random(System.currentTimeMillis());
	private IPathFilter						pathFilter							= new StubPathFilter();
	private IPathFinder						pathFinder							= new StubPathFinder();
																							
	private final List<IPathConsumer>	observers							= new CopyOnWriteArrayList<IPathConsumer>();
																							
	private final BotID						botId;
	private PathFinderInput					pathFinderInput					= null;
	private IPath								currentPath							= null;
																							
	
	private final int							customId								= RND.nextInt(Integer.MAX_VALUE);
																							
	@Configurable(comment = "Time [ms] - How often should the pathplanning be executed?")
	private static int						defaultPathPlanningInterval	= 20;
																							
	private int									pathPlanningInterval				= defaultPathPlanningInterval;
																							
																							
	static
	{
		ConfigRegistration.registerClass("sisyphus", Sisyphus.class);
	}
	
	
	
	public Sisyphus(final BotID botId, final MovementCon moveCon)
	{
		this.botId = botId;
		pathFinderInput = new PathFinderInput(botId, moveCon);
	}
	
	
	
	public void addObserver(final IPathConsumer observer)
	{
		observers.add(observer);
	}
	
	
	
	public boolean removeObserver(final IPathConsumer observer)
	{
		return observers.remove(observer);
	}
	
	
	private void notifyNewPath(final IPath path)
	{
		for (IPathConsumer pathConsumer : observers)
		{
			pathConsumer.onNewPath(path);
		}
	}
	
	
	private void notifyPotentialNewPath(final IPath path)
	{
		for (IPathConsumer pathConsumer : observers)
		{
			pathConsumer.onPotentialNewPath(path);
		}
	}
	
	
	protected void onNewPath(final IPath path)
	{
		notifyNewPath(path);
	}
	
	
	protected void onPotentialNewPath(final IPath path)
	{
		notifyPotentialNewPath(path);
	}
	
	
	
	public IPathFilter getPathFilter()
	{
		return pathFilter;
	}
	
	
	
	public void setPathFilter(final IPathFilter pathFilter)
	{
		this.pathFilter = pathFilter;
	}
	
	
	
	public IPathFinder getPathFinder()
	{
		return pathFinder;
	}
	
	
	
	public void setPathFinder(final IPathFinder pathFinder)
	{
		this.pathFinder = pathFinder;
	}
	
	
	
	public final PathFinderInput getPathFinderInput()
	{
		return pathFinderInput;
	}
	
	
	
	public final IPath getCurrentPath()
	{
		return currentPath;
	}
	
	
	
	public final void setCurrentPath(final IPath currentPath)
	{
		this.currentPath = currentPath;
	}
	
	
	
	public final BotID getBotId()
	{
		return botId;
	}
	
	
	
	public final int getCustomId()
	{
		return customId;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + customId;
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
		Sisyphus other = (Sisyphus) obj;
		if (customId != other.customId)
		{
			return false;
		}
		return true;
	}
	
	
	
	public final int getPathPlanningInterval()
	{
		return pathPlanningInterval;
	}
	
	
	
	public final void setPathPlanningInterval(final int pathPlanningInterval)
	{
		this.pathPlanningInterval = pathPlanningInterval;
	}
}
