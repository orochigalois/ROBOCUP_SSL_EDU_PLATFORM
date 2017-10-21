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

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.export.INumberListable;
import edu.tigers.sumatra.math.IVector;



@Persistent
public abstract class ACamObject implements INumberListable
{
	
	private final double	confidence;
	
	
	private final double	pixelX;
	
	
	private final double	pixelY;
	
	private final long	tCapture;
	private final long	tSent;
	private final int		camId;
	private final long	frameId;
	
	
	protected ACamObject()
	{
		confidence = 0;
		pixelX = 0;
		pixelY = 0;
		tCapture = 0;
		tSent = 0;
		camId = 0;
		frameId = 0;
	}
	
	
	
	public static enum ECamObjectType
	{
		
		Ball,
		
		Robot,
		
		Unknown
	}
	
	
	
	public ACamObject(final double confidence, final double pixelX, final double pixelY, final long tCapture,
			final long tSent,
			final int camId, final long frameId)
	{
		this.confidence = confidence;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.tCapture = tCapture;
		this.tSent = tSent;
		this.camId = camId;
		this.frameId = frameId;
	}
	
	
	
	public ACamObject(final ACamObject o)
	{
		confidence = o.confidence;
		pixelX = o.pixelX;
		pixelY = o.pixelY;
		tCapture = o.tCapture;
		tSent = o.tSent;
		camId = o.camId;
		frameId = o.frameId;
	}
	
	
	
	public abstract IVector getPos();
	
	
	
	public ECamObjectType implementation()
	{
		return ECamObjectType.Unknown;
	}
	
	
	
	public double getPixelY()
	{
		return pixelY;
	}
	
	
	
	public double getPixelX()
	{
		return pixelX;
	}
	
	
	
	public double getConfidence()
	{
		return confidence;
	}
	
	
	
	public final long getTimestamp()
	{
		return tCapture;
	}
	
	
	
	public final int getCameraId()
	{
		return camId;
	}
	
	
	
	public final long getFrameId()
	{
		return frameId;
	}
	
	
	
	public final long gettCapture()
	{
		return tCapture;
	}
	
	
	
	public final long gettSent()
	{
		return tSent;
	}
}
