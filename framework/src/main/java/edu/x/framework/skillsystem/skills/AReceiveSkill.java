/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.x.framework.skillsystem.skills;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.data.EShapesLayer;
import edu.tigers.sumatra.ai.data.math.RedirectMath;
import edu.tigers.sumatra.drawable.DrawableCircle;
import edu.tigers.sumatra.drawable.IDrawableShape;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.x.framework.skillsystem.ESkill;
import edu.x.framework.skillsystem.skills.test.PositionSkill;



public abstract class AReceiveSkill extends PositionSkill
{
	private static final Logger	log								= Logger.getLogger(AReceiveSkill.class.getName());
	
	
	@Configurable(comment = "Time in s, where no more repositioning is allowed")
	public static double				minReceiverPositioningTime	= 0.1;
	
	private IVector2					desiredDestination			= null;
	
	
	
	public enum EReceiverState
	{
		
		PREPARE,
		
		REDIRECT,
		
		RECEIVING,
		
		KEEP_BALL_STOPPED,
		
		KEEP_BALL_DRIBBLE
	}
	
	
	
	public AReceiveSkill(final ESkill skillname)
	{
		super(skillname);
		if (skillname == ESkill.REDIRECT)
		{
			// do nothing here... yet
		} else if (skillname == ESkill.RECEIVER)
		{
			// do nothing here... yet
		} else
		{
			log.error("Invalid inheritance of AReceiveSkill");
		}
	}
	
	
	@Override
	protected void beforeStateUpdate()
	{
		super.beforeStateUpdate();
		
		List<IDrawableShape> shapes = new ArrayList<IDrawableShape>();
		
		if (desiredDestination == null)
		{
			desiredDestination = getPos();
			setDestination(desiredDestination);
		}
		
		IVector3 pose = RedirectMath.validateDest(getWorldFrame(), getTBot(), getPose());
		
		Line ballLine;
		TrackedBall ball = getWorldFrame().getBall();
		if (ball.getVel().getLength() > 0.1)
		{
			ballLine = new Line(ball.getPos(), ball.getVel());
		} else
		{
			ballLine = Line.newLine(getDesiredDestination(), ball.getPos());
		}
		IVector2 dest = GeoMath.leadPointOnLine(getDesiredDestination(), ballLine);
		dest = RedirectMath.validateDest(getWorldFrame(), getTBot(), new Vector3(dest, pose.z())).getXYVector();
		
		if (RedirectMath.isPositionReachable(getWorldFrame(), getTBot(), dest))
		{
			setDestination(dest);
		} else
		{
			DrawableCircle dc = new DrawableCircle(pose.getXYVector(), 100, Color.green);
			shapes.add(dc);
			setDestination(pose.getXYVector());
		}
		setOrientation(pose.z());
		DrawableCircle dc = new DrawableCircle(new Circle(desiredDestination, 120), new Color(0, 255, 255, 100));
		dc.setFill(true);
		shapes.add(dc);
		getPathDriver().setShapes(EShapesLayer.REDIRECT_SKILL, shapes);
	}
	
	
	protected abstract IVector3 getPose();
	
	
	
	public IVector2 getDesiredDestination()
	{
		return desiredDestination;
	}
	
	
	
	public void setDesiredDestination(final IVector2 desiredDestination)
	{
		this.desiredDestination = desiredDestination;
	}
	
	
	protected boolean isPointInPenaltyArea(final IVector2 point)
	{
		return Geometry.getPenaltyAreaTheir().isPointInShape(point, -Geometry.getBotRadius());
	}
	
	
	protected boolean isPointInField(final IVector2 point)
	{
		return Geometry.getField().isPointInShape(point, -Geometry.getBotRadius());
	}
}