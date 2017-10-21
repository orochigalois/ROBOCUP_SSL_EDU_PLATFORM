/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.pandora.roles.throwin;

import java.awt.Color;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.metis.offense.OffensiveConstants;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.drawable.DrawableText;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.wp.data.Geometry;



public abstract class APlacementRole extends ARole
{
	
	public APlacementRole(final ERole role)
	{
		super(role);
	}
	
	
	protected boolean isBallToCloseToFieldBorder()
	{
		float tol = 0;
		if (getCurrentState().toString().equals("PULL"))
		{
			tol += 15;
		}
		if (!Geometry.getField().isPointInShape(getWFrame().getBall().getPos(), -tol))
		{
			return true;
		}
		return false;
	}
	
	
	protected boolean isBallAtTarget()
	{
		if (GeoMath.distancePP(getAiFrame().getWorldFrame().getBall().getPos(),
				getAiFrame().getTacticalField().getThrowInInfo().getPos()) < (OffensiveConstants
						.getAutomatedThrowInFinalTolerance() - 3))
		{
			return true;
		}
		return false;
	}
	
	
	@Override
	protected void afterUpdate()
	{
	}
	
	
	protected void printText(final String message, final double offset)
	{
		String header = "";
		if (getClass().getName().contains("Secondary"))
		{
			header = "Secondary: ";
		} else if (getClass().getName().contains("Primary"))
		{
			header = "Primary: ";
		}
		getAiFrame().getTacticalField().getDrawableShapes().get(EShapesLayer.AUTOMATED_THROW_IN).add(
				new DrawableText(getAiFrame().getTacticalField().getThrowInInfo().getPos().addNew(new Vector2(offset, 0)),
						header + message,
						Color.CYAN));
	}
}
