/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.ares;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.ai.data.LedControl;
import edu.tigers.sumatra.ai.data.frames.AthenaAiFrame;
import edu.tigers.sumatra.ai.data.math.OffensiveMath;
import edu.tigers.sumatra.ai.pandora.plays.APlay;
import edu.tigers.sumatra.ai.pandora.plays.EPlay;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.sisyphus.finder.traj.PathFinderPrioMap;
import edu.tigers.sumatra.ids.BotID;
import edu.x.framework.skillsystem.ASkillSystem;
import edu.x.framework.skillsystem.skills.ISkill;
import edu.x.framework.skillsystem.skills.IdleSkill;



public class Ares
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger			log				= Logger.getLogger(Ares.class.getName());
	private final ASkillSystem				skillSystem;
	private final Map<BotID, Boolean>	botIsStopped	= new HashMap<BotID, Boolean>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public Ares(final ASkillSystem skillSystem)
	{
		this.skillSystem = skillSystem;
	}
	
	
	
	public void process(final AthenaAiFrame frame)
	{
		Set<BotID> botsAssigned = new HashSet<BotID>();
		for (APlay play : frame.getPlayStrategy().getActivePlays())
		{
			for (ARole role : play.getRoles())
			{
				BotID botId = role.getBotID();
				if (!botId.isBot())
				{
					log.error("Role " + role.getType() + " has no assigned bot id!");
				}
				if (botsAssigned.contains(botId))
				{
					log.error("Bot with id " + botId.getNumber() + " already has another role assigned. Can not assign "
							+ role.getType());
					continue;
				}
				
				
				botsAssigned.add(botId);
				ISkill skill = role.getNewSkill();
				if (skill != null)
				{
					// # Execute skills!
					skillSystem.execute(role.getBotID(), skill);
					botIsStopped.put(botId, Boolean.FALSE);
				}
			}
		}
		
		Set<BotID> botsLeft = new HashSet<BotID>(frame.getWorldFrame().getTigerBotsAvailable().keySet());
		botsLeft.removeAll(botsAssigned);
		
		for (BotID botId : botsLeft)
		{
			// No role for this bot: Stop him (if not yet done)
			final Boolean stopped = botIsStopped.get(botId);
			if ((stopped == null) || !stopped)
			{
				skillSystem.execute(botId, new IdleSkill());
				botIsStopped.put(botId, Boolean.TRUE);
			}
		}
		
		PathFinderPrioMap map = PathFinderPrioMap.byBotId(frame.getTeamColor());
		int prio = 100;
		for (ARole role : frame.getPlayStrategy().getActiveRoles(EPlay.KEEPER))
		{
			map.setPriority(role.getBotID(), prio);
		}
		prio--;
		if (!frame.getPlayStrategy().getActiveRoles(EPlay.DEFENSIVE).isEmpty())
		{
			for (BotID botId : frame.getTacticalField().getCrucialDefenders())
			{
				map.setPriority(botId, prio);
			}
		}
		prio--;
		for (ARole role : frame.getPlayStrategy().getActiveRoles(EPlay.DEFENSIVE))
		{
			if (!frame.getTacticalField().getCrucialDefenders().contains(role.getBotID()))
			{
				map.setPriority(role.getBotID(), prio);
			}
		}
		prio--;
		for (ARole role : frame.getPlayStrategy().getActiveRoles(EPlay.OFFENSIVE).stream()
				.sorted((r1, r2) -> BotID.getComparator().compare(r1.getBotID(), r2.getBotID()))
				.collect(Collectors.toList()))
		{
			map.setPriority(role.getBotID(), prio--);
		}
		prio--;
		for (ARole role : frame.getPlayStrategy().getActiveRoles(EPlay.SUPPORT).stream()
				.sorted((r1, r2) -> BotID.getComparator().compare(r1.getBotID(), r2.getBotID()))
				.collect(Collectors.toList()))
		{
			map.setPriority(role.getBotID(), prio--);
		}
		
		List<ISkill> skills = skillSystem.getCurrentSkills();
		for (ISkill skill : skills)
		{
			if (skill.getBotId().getTeamColor() == frame.getTeamColor())
			{
				LedControl led = frame.getTacticalField().getLedData().get(skill.getBotId());
				if (led != null)
				{
					led.setInsane(OffensiveMath.isKeeperInsane(frame, frame.getTacticalField()));
					skill.setLedControl(led);
				}
				skill.getMoveCon().setPrioMap(map);
				frame.getTacticalField().getDrawableShapes().merge(skill.getShapes());
			}
		}
	}
	
	
	
	public ASkillSystem getSkillSystem()
	{
		return skillSystem;
	}
}
