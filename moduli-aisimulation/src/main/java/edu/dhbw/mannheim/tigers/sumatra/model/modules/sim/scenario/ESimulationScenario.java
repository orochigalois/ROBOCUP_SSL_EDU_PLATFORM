/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.model.modules.sim.scenario;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;



public enum ESimulationScenario implements IInstanceableEnum
{
	
	DEFAULT(new InstanceableClass(DefaultSimScenario.class)),
	
	TEST(new InstanceableClass(TestSimScenario.class)),
	
	REFEREE_STOP(new InstanceableClass(RefereeStopSimScenario.class)),
	
	REFEREE_DIRECT_KICK(new InstanceableClass(RefereeDirectKickSimScenario.class)),
	
	REFEREE_KICKOFF(new InstanceableClass(RefereeKickoffSimScenario.class));
	
	private final InstanceableClass	clazz;
	
	
	
	private ESimulationScenario(final InstanceableClass clazz)
	{
		this.clazz = clazz;
	}
	
	
	@Override
	public InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
}
