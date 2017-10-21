/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.configuration.SubnodeConfiguration;

import edu.tigers.moduli.AModule;
import edu.tigers.sumatra.MessagesRobocupSslDetection.SSL_DetectionFrame;
import edu.tigers.sumatra.cam.data.CamGeometry;
import edu.tigers.sumatra.math.IVector3;



public abstract class ACam extends AModule implements IBallReplacer
{
	
	public static final String					MODULE_TYPE		= "ACam";
	
	public static final String					MODULE_ID		= "cam";
																			
	private final List<ICamFrameObserver>	observers		= new CopyOnWriteArrayList<ICamFrameObserver>();
																			
	private IBallReplacer						ballReplacer	= this;
																			
																			
	protected ACam(final SubnodeConfiguration subnodeConfiguration)
	{
	}
	
	
	@Override
	public void deinitModule()
	{
		removeAllObservers();
	}
	
	
	
	public void addObserver(final ICamFrameObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final ICamFrameObserver observer)
	{
		observers.remove(observer);
	}
	
	
	protected void notifyNewCameraFrame(final SSL_DetectionFrame frame, final TimeSync timeSync)
	{
		for (ICamFrameObserver observer : observers)
		{
			observer.onNewCameraFrame(frame, timeSync);
		}
	}
	
	
	protected void notifyNewCameraCalibration(final CamGeometry geometry)
	{
		for (ICamFrameObserver observer : observers)
		{
			observer.onNewCameraGeometry(geometry);
		}
	}
	
	
	protected void notifyVisionLost()
	{
		for (ICamFrameObserver observer : observers)
		{
			observer.onClearCamFrame();
		}
	}
	
	
	protected void removeAllObservers()
	{
		observers.clear();
	}
	
	
	
	public final IBallReplacer getBallReplacer()
	{
		return ballReplacer;
	}
	
	
	
	public final void setBallReplacer(final IBallReplacer ballReplacer)
	{
		this.ballReplacer = ballReplacer;
	}
	
	
	@Override
	public void replaceBall(final IVector3 pos, final IVector3 vel)
	{
		if (ballReplacer != this)
		{
			ballReplacer.replaceBall(pos, vel);
		}
	}
}
