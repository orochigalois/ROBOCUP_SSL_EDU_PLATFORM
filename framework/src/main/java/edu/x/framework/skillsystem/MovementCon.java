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

import edu.tigers.sumatra.ai.sisyphus.finder.traj.PathFinderPrioMap;
import edu.tigers.sumatra.bot.MoveConstraints;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.ITrackedObject;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;



public class MovementCon
{
	private IVector2				destination								= null;
	private Double					targetAngle								= null;
	private DynamicPosition		lookAtTarget							= null;
	
	private boolean				penaltyAreaAllowedOur				= false;
	private boolean				destinationOutsideFieldAllowed	= false;
	private boolean				penaltyAreaAllowedTheir				= false;
	private boolean				isBallObstacle							= true;
	private boolean				isTheirBotsObstacle					= true;
	private boolean				isOurBotsObstacle						= true;
	private boolean				isGoalPostObstacle					= false;
	private boolean				refereeStop								= false;
	private boolean				armChip									= false;
	private boolean				emergencyBreak							= false;
	
	private MoveConstraints		moveConstraints						= new MoveConstraints();
	
	private PathFinderPrioMap	prioMap									= null;
	
	private boolean				isInit									= false;
	
	private boolean				optimizeOrientation					= false;
	
	
	
	public final void update(final SimpleWorldFrame swf, final ITrackedBot bot)
	{
		if (destination == null)
		{
			destination = bot.getPos();
		}
		
		if (targetAngle == null)
		{
			targetAngle = bot.getAngle();
		}
		
		if (lookAtTarget != null)
		{
			lookAtTarget.update(swf);
			targetAngle = lookAtTarget.subtractNew(destination).getAngle();
		}
		
		if (!isInit)
		{
			prioMap = PathFinderPrioMap.byBotId(bot.getTeamColor());
			moveConstraints = new MoveConstraints(bot.getBot().getMoveConstraints());
			isInit = true;
		}
	}
	
	
	
	public void updateDestination(final IVector2 destination)
	{
		if (destination == null)
		{
			assert false : "destination is null!!!";
			return;
		}
		// assert penaltyAreaAllowedOur
		// || !Geometry.getPenaltyAreaOur().isPointInShape(destination, Geometry
		// .getBotRadius()) : "Destination is inside PenaltyArea: " + destination;
		assert (lookAtTarget == null)
				|| (GeoMath.distancePP(lookAtTarget, destination) > 1e-4f) : "lookAtTarget is equal to destination: "
						+ lookAtTarget;
		if (!destinationOutsideFieldAllowed)
		{
			assert Geometry.getFieldWReferee().isPointInShape(destination,
					Geometry.getBotRadius()) : "Destination is outside of field!!";
		}
		this.destination = destination;
	}
	
	
	
	public void updateTargetAngle(final double angle)
	{
		targetAngle = angle;
	}
	
	
	
	public void updateLookAtTarget(final DynamicPosition lookAtTarget)
	{
		this.lookAtTarget = lookAtTarget;
		assert (destination == null)
				|| (GeoMath.distancePP(lookAtTarget, destination) > 1e-4f) : "lookAtTarget is equal to destination: "
						+ lookAtTarget;
	}
	
	
	
	public void updateLookAtTarget(final ITrackedObject object)
	{
		updateLookAtTarget(new DynamicPosition(object));
	}
	
	
	
	public void updateLookAtTarget(final IVector2 lookAtTarget)
	{
		updateLookAtTarget(new DynamicPosition(lookAtTarget));
	}
	
	
	
	public final boolean isPenaltyAreaAllowedOur()
	{
		return penaltyAreaAllowedOur;
	}
	
	
	
	public final boolean isDestinationOutsideFieldAllowed()
	{
		return destinationOutsideFieldAllowed;
	}
	
	
	
	public final void setDestinationOutsideFieldAllowed(final boolean destinationOutside)
	{
		destinationOutsideFieldAllowed = destinationOutside;
	}
	
	
	
	public final void setPenaltyAreaAllowedOur(final boolean penaltyAreaAllowed)
	{
		penaltyAreaAllowedOur = penaltyAreaAllowed;
	}
	
	
	
	public final boolean isPenaltyAreaAllowedTheir()
	{
		return penaltyAreaAllowedTheir;
	}
	
	
	
	public final void setPenaltyAreaAllowedTheir(final boolean penaltyAreaAllowed)
	{
		penaltyAreaAllowedTheir = penaltyAreaAllowed;
	}
	
	
	
	public final boolean isBallObstacle()
	{
		return isBallObstacle;
	}
	
	
	
	public final void setBallObstacle(final boolean isBallObstacle)
	{
		this.isBallObstacle = isBallObstacle;
	}
	
	
	
	public void setBotsObstacle(final boolean isBotsObstacle)
	{
		isTheirBotsObstacle = isBotsObstacle;
		isOurBotsObstacle = isBotsObstacle;
	}
	
	
	
	public void setTheirBotsObstacle(final boolean isBotsObstacle)
	{
		isTheirBotsObstacle = isBotsObstacle;
	}
	
	
	
	public void setOurBotsObstacle(final boolean isBotsObstacle)
	{
		isOurBotsObstacle = isBotsObstacle;
	}
	
	
	
	public boolean isGoalPostObstacle()
	{
		return isGoalPostObstacle;
	}
	
	
	
	public void setGoalPostObstacle(final boolean isGoalPostObstacle)
	{
		this.isGoalPostObstacle = isGoalPostObstacle;
	}
	
	
	
	public boolean isRefereeStop()
	{
		return refereeStop;
	}
	
	
	
	public void setRefereeStop(final boolean refereeStop)
	{
		this.refereeStop = refereeStop;
	}
	
	
	
	public final IVector2 getDestination()
	{
		return destination;
	}
	
	
	
	public final Double getTargetAngle()
	{
		return targetAngle;
	}
	
	
	
	public final PathFinderPrioMap getPrioMap()
	{
		return prioMap;
	}
	
	
	
	public final void setPrioMap(final PathFinderPrioMap prioMap)
	{
		this.prioMap = prioMap;
	}
	
	
	
	public boolean isArmChip()
	{
		return armChip;
	}
	
	
	
	public void setArmChip(final boolean armChip)
	{
		this.armChip = armChip;
	}
	
	
	
	public boolean isTheirBotsObstacle()
	{
		return isTheirBotsObstacle;
	}
	
	
	
	public boolean isOurBotsObstacle()
	{
		return isOurBotsObstacle;
	}
	
	
	
	public MoveConstraints getMoveConstraints()
	{
		return moveConstraints;
	}
	
	
	
	public void setMoveConstraints(final MoveConstraints moveConstraints)
	{
		this.moveConstraints = moveConstraints;
	}
	
	
	
	public boolean isEmergencyBreak()
	{
		return emergencyBreak;
	}
	
	
	
	public void setEmergencyBreak(final boolean emergencyBreak)
	{
		this.emergencyBreak = emergencyBreak;
	}
	
	
	
	public boolean isOptimizeOrientation()
	{
		return optimizeOrientation;
	}
	
	
	
	public void setOptimizeOrientation(final boolean optimizeOrientation)
	{
		this.optimizeOrientation = optimizeOrientation;
	}
}
