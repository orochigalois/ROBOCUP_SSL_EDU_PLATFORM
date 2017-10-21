/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector2;



@Persistent
public class DrawableLine extends Line implements IDrawableShape
{
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	private static final double	ARROW_HEAD_SIZE			= 7;
	private static final double	ARROW_HEAD_SIZE_ANGLE	= 0.5;
	private static final double	ARROW_TEXT_OFFSET_LINE	= 0;
	private static final double	ARROW_TEXT_OFFSET_DIST	= 10;
	
	private Color						color							= Color.red;
	private String						text							= "";
	private ETextLocation			textLocation				= ETextLocation.HEAD;
	private boolean					drawArrowHead				= true;
	
	private transient Stroke		stroke						= null;
	
	
	public enum ETextLocation
	{
		
		HEAD,
		
		CENTER,
		
		TAIL;
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	@SuppressWarnings("unused")
	private DrawableLine()
	{
		super();
	}
	
	
	
	public DrawableLine(final ILine line, final Color color)
	{
		this(line, color, true);
	}
	
	
	
	public DrawableLine(final ILine line, final Color color, final boolean drawArrowHead)
	{
		super(line);
		this.color = color;
		this.drawArrowHead = drawArrowHead;
	}
	
	
	
	public DrawableLine(final ILine line)
	{
		this(line, Color.red, true);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	@Override
	public void paintShape(final Graphics2D g, final IDrawableTool tool, final boolean invert)
	{
		g.setColor(getColor());
		
		Stroke customStroke = getStroke();
		if (customStroke != null)
		{
			g.setStroke(customStroke);
		}
		
		// draw line
		final IVector2 lineStart = tool.transformToGuiCoordinates(supportVector(), invert);
		final IVector2 lineEnd = tool.transformToGuiCoordinates(directionVector().addNew(supportVector()),
				invert);
		g.drawLine((int) lineStart.x(), (int) lineStart.y(), (int) lineEnd.x(), (int) lineEnd.y());
		
		// draw head
		if (isDrawArrowHead())
		{
			final IVector2 arrayHeadBase = lineStart.subtractNew(lineEnd).scaleToNew(ARROW_HEAD_SIZE);
			final IVector2 arrayHeadLeftEnd = arrayHeadBase.turnNew(-ARROW_HEAD_SIZE_ANGLE).add(lineEnd);
			final IVector2 arrayHeadRightEnd = arrayHeadBase.turnNew(ARROW_HEAD_SIZE_ANGLE).add(lineEnd);
			
			g.drawLine((int) lineEnd.x(), (int) lineEnd.y(), (int) arrayHeadLeftEnd.x(), (int) arrayHeadLeftEnd.y());
			g.drawLine((int) lineEnd.x(), (int) lineEnd.y(), (int) arrayHeadRightEnd.x(), (int) arrayHeadRightEnd.y());
		}
		
		// draw text
		if (!text.isEmpty())
		{
			Vector2 base = null;
			switch (textLocation)
			{
				case HEAD:
					base = GeoMath.stepAlongLine(lineEnd, lineStart, ARROW_TEXT_OFFSET_LINE);
					break;
				case TAIL:
					base = GeoMath.stepAlongLine(lineStart, lineEnd, ARROW_TEXT_OFFSET_LINE);
					break;
				case CENTER:
					base = GeoMath.stepAlongLine(lineStart, lineEnd, GeoMath.distancePP(lineStart, lineEnd) / 2.0);
					break;
			}
			if (base != null)
			{
				Vector2 dir = new Vector2(-base.y(), base.x()).scaleTo(ARROW_TEXT_OFFSET_DIST);
				if (dir.x() < 1)
				{
					dir.multiply(-1);
				}
				Vector2 txtbase = base.addNew(dir);
				g.setFont(new Font("", Font.PLAIN, 10));
				g.drawString(text, (float) txtbase.x(), (float) txtbase.y());
			}
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public Color getColor()
	{
		// this may happen with old databases
		if (color == null)
		{
			// standard color
			return Color.red;
		}
		return color;
	}
	
	
	
	@Override
	public void setColor(final Color color)
	{
		this.color = color;
	}
	
	
	
	public final String getText()
	{
		return text;
	}
	
	
	
	public final void setText(final String text)
	{
		this.text = text;
	}
	
	
	
	public final ETextLocation getTextLocation()
	{
		return textLocation;
	}
	
	
	
	public final void setTextLocation(final ETextLocation textLocation)
	{
		this.textLocation = textLocation;
	}
	
	
	
	public final boolean isDrawArrowHead()
	{
		return drawArrowHead;
	}
	
	
	
	public final void setDrawArrowHead(final boolean drawArrowHead)
	{
		this.drawArrowHead = drawArrowHead;
	}
	
	
	
	public final Stroke getStroke()
	{
		return stroke;
	}
	
	
	
	public final void setStroke(final Stroke stroke)
	{
		this.stroke = stroke;
	}
	
}
