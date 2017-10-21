/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistance;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;



@Entity
public class RecordCamFrame
{
	@PrimaryKey
	private final long								timestamp;
	
	private final ExtendedCamDetectionFrame	camFrame;
	
	
	@SuppressWarnings("unused")
	private RecordCamFrame()
	{
		timestamp = 0;
		camFrame = null;
	}
	
	
	
	public RecordCamFrame(final ExtendedCamDetectionFrame camFrame)
	{
		this.camFrame = camFrame;
		timestamp = camFrame.gettCapture();
	}
	
	
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public ExtendedCamDetectionFrame getCamFrame()
	{
		return camFrame;
	}
	
}
