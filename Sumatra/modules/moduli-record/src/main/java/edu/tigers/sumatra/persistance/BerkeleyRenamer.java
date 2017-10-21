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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.wp.data.WorldFrameWrapper;



public class BerkeleyRenamer
{
	static
	{
		// init logging
		SumatraModel.changeLogLevel(Level.INFO);
	}
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BerkeleyRenamer.class.getName());
	
	
	
	public BerkeleyRenamer()
	{
	}
	
	
	
	public static void main(final String[] args) throws IOException
	{
		BerkeleyRenamer bp = new BerkeleyRenamer();
		Files.list(Paths.get(RecordBerkeleyPersistence.getDefaultBasePath()))
				.filter(path -> path.toFile().isDirectory())
				.filter(fileName -> fileName.toFile().getName().startsWith("record_"))
				.sorted()
				.forEach(path -> bp.processDb(path.toFile().getAbsolutePath()));
	}
	
	
	
	public void processDb(final String dbPath)
	{
		log.info("Processing " + dbPath);
		RecordBerkeleyPersistence db = new RecordBerkeleyPersistence(dbPath, true);
		RecordFrame frame = db.getRecordFrame(db.getFirstKey());
		String dbname = getDbOutName(frame);
		db.close();
		
		File f = new File(RecordBerkeleyPersistence.getDefaultBasePath() + "/" + dbPath);
		File nf = new File(RecordBerkeleyPersistence.getDefaultBasePath() + "/" + dbname);
		System.out.println(f + " -> " + nf);
		f.renameTo(nf);
	}
	
	
	private String getDbOutName(final RecordFrame frame)
	{
		if (frame == null)
		{
			log.error("Can not determine DB name: No frame available!");
			return "undetermined-" + System.currentTimeMillis();
		}
		WorldFrameWrapper lastFrame = frame.getWorldFrameWrapper();
		
		if (lastFrame.getRefereeMsg() != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
			String time = sdf.format(new Date(lastFrame.getRefereeMsg().getPacketTimestamp()));
			String blueName = lastFrame.getRefereeMsg().getTeamInfoBlue().getName().replaceAll("/", "_");
			String yellowName = lastFrame.getRefereeMsg().getTeamInfoYellow().getName().replaceAll("/", "_");
			blueName = blueName.replaceAll("[ ]", "_");
			yellowName = yellowName.replaceAll("[ ]", "_");
			
			return String.format("%s_%s_vs_%s", time, yellowName, blueName);
		}
		return String.valueOf(System.currentTimeMillis());
	}
}
