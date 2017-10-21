/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.plays.defense.algorithms.helpers;

import com.github.g3force.instanceables.IInstanceableEnum;
import com.github.g3force.instanceables.InstanceableClass;



public enum EPointOnLineGetter implements IInstanceableEnum
{
	
	
	HEDGEHOG(new InstanceableClass(HedgehogPointCalc.class)),
	
	
	PASSINTERSECTION(new InstanceableClass(PassIntersectionPointCalc.class)),
	
	
	PASSIVEAGRESSIVE(new InstanceableClass(PassiveAgressivePointCalc.class)),
	
	
	ZONEDEFENSE(new InstanceableClass(ZoneDefensePointCalc.class)),
	
	
	RECEIVERBLOCK(new InstanceableClass(ReceiverBlockPointCalc.class))
	
	;
	
	
	private final InstanceableClass	clazz;
	
	
	
	private EPointOnLineGetter(final InstanceableClass clazz)
	{
		this.clazz = clazz;
	}
	
	
	
	@Override
	public final InstanceableClass getInstanceableClass()
	{
		return clazz;
	}
}
