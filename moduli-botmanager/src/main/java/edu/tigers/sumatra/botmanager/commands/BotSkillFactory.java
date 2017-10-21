/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.commands;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchCtrl;
import edu.tigers.sumatra.botmanager.serial.SerialDescription;
import edu.tigers.sumatra.botmanager.serial.SerialException;



public final class BotSkillFactory
{
	// Logger
	private static final Logger							log		= Logger.getLogger(BotSkillFactory.class.getName());
																				
	private static BotSkillFactory						instance	= null;
																				
	private final Map<Integer, SerialDescription>	skills	= new HashMap<Integer, SerialDescription>();
																				
																				
	private BotSkillFactory()
	{
	}
	
	
	
	public static synchronized BotSkillFactory getInstance()
	{
		if (instance == null)
		{
			instance = new BotSkillFactory();
		}
		return instance;
	}
	
	
	
	public void loadSkills()
	{
		skills.clear();
		
		for (EBotSkill esk : EBotSkill.values())
		{
			SerialDescription desc;
			try
			{
				desc = new SerialDescription(esk.getClazz());
				
				// do sanity checks for encode and decode - should not throw any exception
				desc.decode(desc.encode(desc.newInstance()));
			} catch (SerialException err)
			{
				log.error("Could not load bot skill: " + esk, err);
				continue;
			}
			
			ABotSkill ask;
			try
			{
				ask = (ABotSkill) desc.newInstance();
			} catch (SerialException err)
			{
				log.error("Could not create instance of: " + esk);
				continue;
			} catch (ClassCastException err)
			{
				log.error(esk + " is not based on ABotSkill!", err);
				continue;
			}
			
			if (ask.getType().getId() != esk.getId())
			{
				log.error("EBotSkill id mismatch in bot skill: " + esk + ". The bot skill does not use the correct enum.");
				continue;
			}
			
			if (skills.get(esk.getId()) != null)
			{
				log.error(esk + "'s skill code is already defined by: "
						+ skills.get(esk.getId()).getClass().getName());
				continue;
			}
			
			skills.put(esk.getId(), desc);
		}
	}
	
	
	
	public ABotSkill decode(final byte[] data, final int skillId)
	{
		if (!skills.containsKey(skillId))
		{
			log.warn("Unknown skill: " + skillId + ", length: " + data.length);
			return null;
		}
		
		SerialDescription skillDesc = skills.get(skillId);
		
		ABotSkill ask;
		
		try
		{
			ask = (ABotSkill) skillDesc.decode(data);
		} catch (SerialException err)
		{
			log.error("Could not parse cmd: " + skillId, err);
			return null;
		}
		
		return ask;
	}
	
	
	
	public byte[] encode(final ABotSkill skill)
	{
		int skillId = skill.getType().getId();
		
		if (!skills.containsKey(skillId))
		{
			log.error("No description for skill: " + skillId);
			return new byte[0];
		}
		
		SerialDescription skillDesc = skills.get(skillId);
		
		byte[] skillData;
		try
		{
			skillData = skillDesc.encode(skill);
		} catch (SerialException err)
		{
			log.error("Could not encode skill: " + skillId, err);
			return new byte[0];
		}
		
		if (skillData.length > TigerSystemMatchCtrl.MAX_SKILL_DATA_SIZE)
		{
			log.error("Skill " + skill.getType() + " exceeds data usage limit of "
					+ TigerSystemMatchCtrl.MAX_SKILL_DATA_SIZE);
			return new byte[0];
		}
		
		return skillData;
	}
	
	
	
	public int getLength(final ABotSkill skill)
	{
		int length;
		int skillId = skill.getType().getId();
		
		if (!skills.containsKey(skillId))
		{
			log.error("No description for skill: " + skillId);
			return 0;
		}
		
		SerialDescription skillDesc = skills.get(skillId);
		
		try
		{
			length = skillDesc.getLength(skill);
		} catch (SerialException err)
		{
			log.error("Could not get length of: " + skill.getType(), err);
			return 0;
		}
		
		return length;
	}
}
