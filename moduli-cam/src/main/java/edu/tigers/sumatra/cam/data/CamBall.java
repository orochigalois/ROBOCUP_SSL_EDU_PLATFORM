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

import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



@Persistent
public class CamBall extends ACamObject
{
	private final int			area;
	
	
	private final IVector3	pos;
	
	
	
	public CamBall()
	{
		super();
		area = 0;
		pos = AVector3.ZERO_VECTOR;
	}
	
	
	
	public CamBall(final double confidence, final int area, final double x, final double y, final double z,
			final double pixelX, final double pixelY, final long tCapture, final long tSent, final int camId,
			final long frameId)
	{
		super(confidence, pixelX, pixelY, tCapture, tSent, camId, frameId);
		this.area = area;
		pos = new Vector3(x, y, z);
	}
	
	
	
	public CamBall(final CamBall base, final IVector2 pos)
	{
		this(base.getConfidence(), base.getArea(), pos.x(), pos.y(), base.getPos().z(), base.getPixelX(),
				base.getPixelY(), base.gettCapture(), base.gettSent(), base.getCameraId(), base.getFrameId());
	}
	
	
	
	public CamBall(final CamBall newCamBall)
	{
		super(newCamBall.getConfidence(), newCamBall.getPixelX(), newCamBall.getPixelY(), newCamBall.gettCapture(),
				newCamBall.gettSent(),
				newCamBall.getCameraId(), newCamBall.getFrameId());
		area = newCamBall.getArea();
		pos = new Vector3(newCamBall.getPos());
	}
	
	
	
	public static CamBall fromNumberList(final List<? extends Number> list)
	{
		final long tCapture = list.get(0).longValue();
		final int camId = list.get(1).intValue();
		final double x = list.get(2).doubleValue();
		final double y = list.get(3).doubleValue();
		final double z = list.get(4).doubleValue();
		final long frameId = list.size() <= 5 ? 0 : list.get(5).longValue();
		final double pixelX = list.size() <= 6 ? 0 : list.get(6).doubleValue();
		final double pixelY = list.size() <= 7 ? 0 : list.get(7).doubleValue();
		final int area = list.size() <= 8 ? 0 : list.get(8).intValue();
		final double confidence = list.size() <= 9 ? 0 : list.get(9).doubleValue();
		final long tSent = list.size() <= 10 ? 0 : list.get(10).longValue();
		
		return new CamBall(confidence, area, x, y, z, pixelX, pixelY, tCapture, tSent, camId, frameId);
	}
	
	
	@Override
	public ECamObjectType implementation()
	{
		return ECamObjectType.Ball;
	}
	
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SSLBall [timestamp=");
		builder.append(getTimestamp());
		builder.append(", camId=");
		builder.append(getCameraId());
		builder.append(", x=");
		builder.append(getPos().x());
		builder.append(", y=");
		builder.append(getPos().y());
		builder.append(", z=");
		builder.append(getPos().z());
		builder.append(", area=");
		builder.append(getArea());
		builder.append(", confidence=");
		builder.append(getConfidence());
		builder.append(", pixelX=");
		builder.append(getPixelX());
		builder.append(", pixelY=");
		builder.append(getPixelY());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	public int getArea()
	{
		return area;
	}
	
	
	
	@Override
	public IVector3 getPos()
	{
		return pos;
	}
	
	
	@Override
	public List<Number> getNumberList()
	{
		List<Number> numbers = new ArrayList<>();
		numbers.add(gettCapture());
		numbers.add(getCameraId());
		numbers.addAll(pos.getNumberList());
		numbers.add(getFrameId());
		numbers.add(getPixelX());
		numbers.add(getPixelY());
		numbers.add(getArea());
		numbers.add(getConfidence());
		numbers.add(gettSent());
		return numbers;
	}
}
