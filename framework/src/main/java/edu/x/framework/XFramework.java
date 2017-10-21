/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework;

import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.ai.data.frames.BaseAiFrame;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.x.framework.skillsystem.ASkillSystem;
import edu.x.framework.skillsystem.skills.ISkill;



public class XFramework
{
	
	static final double			PI			= 3.1415926;										// 360 degrees
	static final BotID			ROBOT_0	= BotID.createBotId(0, ETeamColor.YELLOW);
	static final BotID			ROBOT_1	= BotID.createBotId(1, ETeamColor.YELLOW);
	static final BotID			ROBOT_2	= BotID.createBotId(2, ETeamColor.YELLOW);
	static final BotID			ROBOT_3	= BotID.createBotId(3, ETeamColor.YELLOW);
	static final BotID			ROBOT_4	= BotID.createBotId(4, ETeamColor.YELLOW);
	static final BotID			ROBOT_5	= BotID.createBotId(5, ETeamColor.YELLOW);
	
	
	private final ASkillSystem	skillSystem;
	
	private final XSkills		skills	= new XSkills();
	
	
	
	public XFramework(final ASkillSystem skillSystem)
	{
		this.skillSystem = skillSystem;
	}
	
	
	public void executeSkill(final BotID botID, final ISkill skill)
	{
		skillSystem.execute(botID, skill);
	}
	
	
	public void run(final BaseAiFrame baseAiFrame)
	{
		
		
		// Logic 1
		if (baseAiFrame.getRefereeMsg().getCommand() == Command.NORMAL_START)
		{
			
			System.out.println(baseAiFrame.getWorldFrame().getBot(ROBOT_0).getPos().x());
			
			// Logic 2
			if (baseAiFrame.getWorldFrame().getBall().getPos().x() > 0)
			{
				executeSkill(ROBOT_0, skills.catchSkill());
				executeSkill(ROBOT_1, skills.catchSkill());
				executeSkill(ROBOT_2, skills.catchSkill());
				executeSkill(ROBOT_3, skills.catchSkill());
				executeSkill(ROBOT_4, skills.catchSkill());
				executeSkill(ROBOT_5, skills.catchSkill());
			} else
			{
				executeSkill(ROBOT_0, skills.gotoSkill(-3000, -2000, 0));
				executeSkill(ROBOT_1, skills.gotoSkill(-2000, -2000, PI / 2));
				executeSkill(ROBOT_2, skills.gotoSkill(-1000, -2000, PI));
				executeSkill(ROBOT_3, skills.gotoSkill(0, -2000, 0));
				executeSkill(ROBOT_4, skills.gotoSkill(1000, -2000, PI / 2));
				executeSkill(ROBOT_5, skills.gotoSkill(2000, -2000, PI));
			}
		}
		
		
		// System.out.println(baseAiFrame.getRefereeMsg());
	}
	
	
}
