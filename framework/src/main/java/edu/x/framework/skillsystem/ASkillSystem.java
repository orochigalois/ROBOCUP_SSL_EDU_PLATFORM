/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.tigers.moduli.AModule;
import edu.tigers.moduli.exceptions.InitModuleException;
import edu.tigers.moduli.exceptions.StartModuleException;
import edu.tigers.sumatra.ai.sisyphus.PathFinderThread;
import edu.tigers.sumatra.botmanager.bots.ABot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;
import edu.x.framework.skillsystem.skills.ISkill;



public abstract class ASkillSystem extends AModule
{
	
	public static final String						MODULE_TYPE	= "AMoveSystem";
	
	public static final String						MODULE_ID	= "skillsystem";
	
	private final List<ISkillSystemObserver>	observers	= new CopyOnWriteArrayList<ISkillSystemObserver>();
	
	
	
	public abstract void execute(BotID botId, ISkill skill);
	
	
	
	public abstract void reset(final BotID botId);
	
	
	
	public abstract void reset(ETeamColor color);
	
	
	
	public abstract PathFinderThread getPathFinderScheduler();
	
	
	
	public abstract List<ISkill> getCurrentSkills();
	
	
	
	public abstract void process(final WorldFrameWrapper wfw);
	
	
	@Override
	public void deinitModule()
	{
	}
	
	
	@Override
	public void initModule() throws InitModuleException
	{
	}
	
	
	@Override
	public void startModule() throws StartModuleException
	{
	}
	
	
	@Override
	public void stopModule()
	{
	}
	
	
	
	public abstract void emergencyStop();
	
	
	protected void notifyCommandSent(final ABot bot, final long timestamp)
	{
		for (ISkillSystemObserver observer : observers)
		{
			observer.onCommandSent(bot, timestamp);
		}
	}
	
	
	
	public void addObserver(final ISkillSystemObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final ISkillSystemObserver observer)
	{
		observers.remove(observer);
	}
}
