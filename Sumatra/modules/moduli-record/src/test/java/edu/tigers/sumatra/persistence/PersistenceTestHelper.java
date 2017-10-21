/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.tigers.sumatra.ai.FrameFactory;
import edu.tigers.sumatra.ai.data.frames.AIInfoFrame;
import edu.tigers.sumatra.ai.data.frames.VisualizationFrame;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.persistance.RecordBerkeleyPersistence;
import edu.tigers.sumatra.persistance.RecordFrame;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class PersistenceTestHelper
{
	
	public static final int		MANY_FRAMES		= 100;
	
	public static final String	DB_NAME			= "test";
	
	private final FrameFactory	frameFactory	= new FrameFactory();
	
	
	
	public PersistenceTestHelper()
	{
		
	}
	
	
	
	public VisualizationFrame createVisualizationFrame(final WorldFrameWrapper wfw, final ETeamColor teamColor)
	{
		final AIInfoFrame aiFrame = frameFactory.createFullAiInfoFrame(teamColor, wfw);
		return new VisualizationFrame(aiFrame);
	}
	
	
	
	public RecordFrame createRecordFrame(final WorldFrameWrapper wfw)
	{
		RecordFrame recFrame = new RecordFrame(wfw);
		recFrame.addVisFrame(createVisualizationFrame(wfw, ETeamColor.YELLOW));
		recFrame.addVisFrame(createVisualizationFrame(wfw, ETeamColor.BLUE));
		return recFrame;
	}
	
	
	
	public List<RecordFrame> createOneRecordFrame(final WorldFrameWrapper wfw)
	{
		List<RecordFrame> frames = new ArrayList<>(1);
		frames.add(createRecordFrame(wfw));
		return frames;
	}
	
	
	
	public List<RecordFrame> createOneRecordFrame()
	{
		return createOneRecordFrame(frameFactory.createWorldFrameWrapper(0, 0));
	}
	
	
	
	public List<RecordFrame> createManyRecordFrames()
	{
		return createManyRecordFrames(MANY_FRAMES);
	}
	
	
	
	public List<RecordFrame> createManyRecordFrames(final int numFrames)
	{
		List<RecordFrame> frames = new ArrayList<>(numFrames);
		long baseTimestamp = System.nanoTime();
		for (int i = 0; i < numFrames; i++)
		{
			WorldFrameWrapper wfw = frameFactory.createWorldFrameWrapper(i, baseTimestamp + (i * 16));
			frames.add(createRecordFrame(wfw));
		}
		return frames;
	}
	
	
	
	public static void delete(final File file) throws IOException
	{
		if (file.isDirectory())
		{
			
			// directory is empty, then delete it
			if (file.list().length == 0)
			{
				
				file.delete();
				
			} else
			{
				
				// list all the directory contents
				String files[] = file.list();
				
				for (String temp : files)
				{
					// construct the file structure
					File fileDelete = new File(file, temp);
					
					// recursive delete
					delete(fileDelete);
				}
				
				// check the directory again, if empty then delete it
				if (file.list().length == 0)
				{
					file.delete();
				}
			}
			
		} else
		{
			// if file, then delete it
			file.delete();
		}
	}
	
	
	
	public static void cleanup()
	{
		try
		{
			PersistenceTestHelper.delete(new File(RecordBerkeleyPersistence.getDefaultBasePath() + DB_NAME));
		} catch (IOException err)
		{
			err.printStackTrace();
		}
	}
	
	
	
	public void close()
	{
		frameFactory.close();
	}
	
	
	
	public static void printMemoryUsage()
	{
		long usedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		double totalMB = (Runtime.getRuntime().totalMemory()) / 1048576.0D;
		double usedMB = (usedMem) / 1048576.0D;
		System.out.println(String.format("Memory Usage: %.1fMB / %.1fMB", usedMB, totalMB));
	}
}
