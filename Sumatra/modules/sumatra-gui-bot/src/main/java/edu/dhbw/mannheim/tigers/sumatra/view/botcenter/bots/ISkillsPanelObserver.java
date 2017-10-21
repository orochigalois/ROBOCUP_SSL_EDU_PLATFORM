/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots;

import edu.tigers.sumatra.botmanager.commands.botskills.ABotSkill;
import edu.tigers.sumatra.math.Vector2;
import edu.x.framework.skillsystem.skills.ASkill;



public interface ISkillsPanelObserver
{
	
	void onMoveToXY(double x, double y);
	
	
	
	void onRotateAndMoveToXY(double x, double y, double angle);
	
	
	
	void onStraightMove(int distance, double angle);
	
	
	
	void onLookAt(Vector2 lookAtTarget);
	
	
	
	void onDribble(int rpm);
	
	
	
	void onSkill(ASkill skill);
	
	
	
	void onBotSkill(ABotSkill skill);
}
