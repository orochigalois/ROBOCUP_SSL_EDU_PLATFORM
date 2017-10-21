/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam.data;

import java.util.ArrayList;
import java.util.List;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ids.AObjectID;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2f;



@Persistent
public class CamRobot extends ACamObject
{
	private final BotID		botId;
	private final IVector2	pos;
	private final double		orientation;
	private final double		height;
									
									
	
	public CamRobot()
	{
		super();
		botId = BotID.get();
		pos = AVector2.ZERO_VECTOR;
		orientation = 0;
		height = 0;
	}
	
	
	
	public CamRobot(final double confidence,
			final double pixelX,
			final double pixelY,
			final long tCapture,
			final long tSent,
			final int camId,
			final long frameId,
			final double x,
			final double y,
			final double orientation,
			final double height,
			final BotID botId)
	{
		super(confidence, pixelX, pixelY, tCapture, tSent, camId, frameId);
		pos = new Vector2f(x, y);
		this.orientation = orientation;
		this.height = height;
		this.botId = botId;
	}
	
	
	
	public static CamRobot fromNumberList(final List<? extends Number> list)
	{
		final long tCapture = list.get(0).longValue();
		final int camId = list.get(1).intValue();
		
		final int id = list.size() <= 2 ? AObjectID.UNINITIALIZED_ID : list.get(2).intValue();
		ETeamColor color = list.size() <= 3 ? ETeamColor.UNINITIALIZED : ETeamColor.fromNumberList(list.get(3));
		BotID botId = BotID.createBotId(id, color);
		
		final double x = list.get(4).doubleValue();
		final double y = list.get(5).doubleValue();
		final double orientation = list.get(6).doubleValue();
		final long frameId = list.size() <= 7 ? 0 : list.get(7).longValue();
		final double pixelX = list.size() <= 8 ? 0 : list.get(8).doubleValue();
		final double pixelY = list.size() <= 9 ? 0 : list.get(9).doubleValue();
		final double height = list.size() <= 10 ? 0 : list.get(10).intValue();
		final double confidence = list.size() <= 11 ? 0 : list.get(11).doubleValue();
		
		final long tSent = list.size() <= 12 ? 0 : list.get(12).longValue();
		
		return new CamRobot(confidence, pixelX, pixelY, tCapture, tSent, camId, frameId, x, y, orientation, height,
				botId);
	}
	
	
	@Override
	public ECamObjectType implementation()
	{
		return ECamObjectType.Robot;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SSLRobot [confidence=");
		builder.append(getConfidence());
		builder.append(", height=");
		builder.append(getHeight());
		builder.append(", orientation=");
		builder.append(getOrientation());
		builder.append(", pixelX=");
		builder.append(getPixelX());
		builder.append(", pixelY=");
		builder.append(getPixelY());
		builder.append(", robotID=");
		builder.append(getRobotID());
		builder.append(", x=");
		builder.append(getPos().x());
		builder.append(", y=");
		builder.append(getPos().y());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	public int getRobotID()
	{
		return botId.getNumber();
	}
	
	
	
	@Override
	public IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public double getOrientation()
	{
		return orientation;
	}
	
	
	
	public double getHeight()
	{
		return height;
	}
	
	
	@Override
	public List<Number> getNumberList()
	{
		List<Number> numbers = new ArrayList<>();
		numbers.add(gettCapture());
		numbers.add(getCameraId());
		numbers.add(getRobotID());
		numbers.addAll(botId.getTeamColor().getNumberList());
		numbers.addAll(pos.getNumberList());
		numbers.add(getOrientation());
		numbers.add(getFrameId());
		numbers.add(getPixelX());
		numbers.add(getPixelY());
		numbers.add(getHeight());
		numbers.add(getConfidence());
		numbers.add(gettSent());
		return numbers;
	}
	
	
	
	public BotID getBotId()
	{
		return botId;
	}
}
