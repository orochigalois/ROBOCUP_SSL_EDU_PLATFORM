/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills.test;

import edu.tigers.sumatra.botmanager.commands.EBotSkill;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.driver.ABaseDriver;
import edu.x.framework.skillsystem.driver.EPathDriver;
import edu.x.framework.skillsystem.skills.AMoveSkill;
import net.sourceforge.jFuzzyLogic.FIS;



public class FuzzySkill extends AMoveSkill
{
	
	
	
	public FuzzySkill(final IVector2 movePos)
	{
		super(ESkill.FUZZY);
		setPathDriver(new FuzzyDriver(movePos));
		
		
	}
	
	
	private static class FuzzyDriver extends ABaseDriver
	{
		private FIS			fis	= null;
										
		private IVector2	movePos;
								
								
		
		public FuzzyDriver(final IVector2 movePos)
		{
			this.movePos = movePos;
			addSupportedCommand(EBotSkill.LOCAL_VELOCITY);
			
			String fileName = "./data/fuzzy/positionController.fcl";
			fis = FIS.load(fileName, true);
			// Error while loading?
			if (fis == null)
			{
				System.err.println("Can't load file: '"
						+ fileName + "'");
				return;
			}
			
			// fis.chart();
		}
		
		
		@Override
		public IVector3 getNextDestination(final ITrackedBot bot, final WorldFrame wFrame)
		{
			throw new IllegalStateException();
		}
		
		
		@Override
		public IVector3 getNextVelocity(final ITrackedBot bot, final WorldFrame wFrame)
		{
			double distanceToTarget = movePos.subtractNew(bot.getPos()).getLength2();
			
			fis.setVariable("distance", distanceToTarget);
			
			// Evaluate
			fis.evaluate();
			
			// fis.getVariable("tip").chartDefuzzifier(true);
			double res = fis.getVariable("speed").defuzzify();
			System.out.println(res);
			IVector2 vel = movePos.subtractNew(bot.getPos()).scaleTo(res);
			return new Vector3(vel, 0);
		}
		
		
		@Override
		public EPathDriver getType()
		{
			return EPathDriver.FUZZY;
		}
		
	}
}
