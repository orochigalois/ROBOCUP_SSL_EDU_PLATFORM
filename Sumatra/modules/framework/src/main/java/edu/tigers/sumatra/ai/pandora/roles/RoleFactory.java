/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.g3force.instanceables.InstanceableClass.NotCreateableException;
import com.github.g3force.instanceables.InstanceableParameter;



public final class RoleFactory
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static List<ERole>		availableRoles	= new ArrayList<ERole>();
	private static final Logger	log				= Logger.getLogger(RoleFactory.class.getName());
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	private RoleFactory()
	{
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static ARole createRole(final ERole eRole, final Object... args) throws NotCreateableException
	{
		return (ARole) eRole.getInstanceableClass().newInstance(args);
	}
	
	
	
	public static ARole createDefaultRole(final ERole eRole) throws NotCreateableException
	{
		List<Object> objParams = new ArrayList<Object>(eRole.getInstanceableClass().getParams().size());
		for (InstanceableParameter param : eRole.getInstanceableClass().getParams())
		{
			Object objParam = param.parseString(param.getDefaultValue());
			objParams.add(objParam);
		}
		return createRole(eRole, objParams.toArray());
	}
	
	
	
	public static void selfCheckRoles()
	{
		log.trace("Start selfChecking roles");
		availableRoles.clear();
		for (ERole role : ERole.values())
		{
			try
			{
				ARole aRole = createDefaultRole(role);
				availableRoles.add(role);
				if (!aRole.doSelfCheck())
				{
					log.warn("StateMachine for role " + aRole + " is not complete!");
				}
			} catch (NotCreateableException err)
			{
				log.warn("Role type could not be handled by role factory! Role = " + role, err);
			}
		}
		log.trace("Role selfcheck done");
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static synchronized List<ERole> getAvailableRoles()
	{
		return availableRoles;
	}
}
