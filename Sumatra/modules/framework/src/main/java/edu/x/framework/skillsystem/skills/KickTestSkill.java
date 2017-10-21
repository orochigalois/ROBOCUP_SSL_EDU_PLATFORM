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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.botmanager.commands.other.EKickerDevice;
import edu.tigers.sumatra.wp.ExportDataContainer;
import edu.tigers.sumatra.wp.IBallWatcherObserver;
import edu.tigers.sumatra.wp.VisionWatcher;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.ExtendedCamDetectionFrame;
import edu.x.framework.skillsystem.ESkill;



public class KickTestSkill extends KickSkill implements IBallWatcherObserver
{
	@SuppressWarnings("unused")
	private static final Logger	log	= Logger.getLogger(KickTestSkill.class.getName());
	private final VisionWatcher	watcher;
	
	
	
	public KickTestSkill(final DynamicPosition target, final double kickSpeed)
	{
		super(ESkill.KICK_TEST, target, EKickMode.FIXED_SPEED, EKickerDevice.STRAIGHT, EMoveMode.CHILL, kickSpeed);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String fileName = "ballKick/" + sdf.format(new Date());
		watcher = new VisionWatcher(fileName);
		watcher.addObserver(this);
		
		getMoveCon().setPenaltyAreaAllowedOur(true);
		getMoveCon().setPenaltyAreaAllowedTheir(true);
	}
	
	
	@Override
	public void onSkillStarted()
	{
		watcher.start();
	}
	
	
	@Override
	public void beforeExport(final Map<String, Object> jsonMapping)
	{
		jsonMapping.put("kickSpeed", getKickSpeed());
	}
	
	
	@Override
	public void onAddCustomData(final ExportDataContainer container, final ExtendedCamDetectionFrame frame)
	{
	}
	
	
	@Override
	public void postProcessing(final String fileName)
	{
	}
}
