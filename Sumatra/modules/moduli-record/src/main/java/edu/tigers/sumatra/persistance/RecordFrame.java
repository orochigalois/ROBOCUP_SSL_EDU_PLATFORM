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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



@Entity
public class RecordFrame
{
	@PrimaryKey
	private final long											timestamp;
	
	private final long											timestampMs	= System.currentTimeMillis();
	
	private final WorldFrameWrapper							worldFrameWrapper;
	
	private final Map<ETeamColor, VisualizationFrame>	visFrames	= new HashMap<>(2);
	
	
	@SuppressWarnings("unused")
	private RecordFrame()
	{
		timestamp = 0;
		worldFrameWrapper = null;
	}
	
	
	
	public RecordFrame(final WorldFrameWrapper worldFrameWrapper)
	{
		timestamp = worldFrameWrapper.getSimpleWorldFrame().getTimestamp();
		this.worldFrameWrapper = worldFrameWrapper;
	}
	
	
	
	public final long getTimestamp()
	{
		return timestamp;
	}
	
	
	
	public final long getTimestampMs()
	{
		return timestampMs;
	}
	
	
	
	public synchronized final void addVisFrame(final VisualizationFrame visFrame)
	{
		assert visFrame != null;
		visFrames.put(visFrame.getTeamColor(), visFrame);
	}
	
	
	
	public synchronized final VisualizationFrame getVisFrame(final ETeamColor teamColor)
	{
		VisualizationFrame visFrame = visFrames.get(teamColor);
		visFrame.setWorldFrameWrapper(worldFrameWrapper);
		return visFrame;
	}
	
	
	
	public synchronized final Collection<VisualizationFrame> getVisFrames()
	{
		for (VisualizationFrame visFrame : visFrames.values())
		{
			assert visFrame != null;
			visFrame.setWorldFrameWrapper(worldFrameWrapper);
		}
		return Collections.unmodifiableCollection(visFrames.values());
	}
	
	
	
	public final WorldFrameWrapper getWorldFrameWrapper()
	{
		return worldFrameWrapper;
	}
}
