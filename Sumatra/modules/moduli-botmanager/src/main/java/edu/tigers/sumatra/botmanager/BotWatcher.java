/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.tigers.sumatra.botmanager.bots.TigerBotV3;
import edu.tigers.sumatra.botmanager.bots.TigerBotV3.ITigerBotV3Observer;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemConsolePrint;
import edu.tigers.sumatra.botmanager.commands.tigerv2.TigerSystemMatchFeedback;
import edu.tigers.sumatra.export.CSVExporter;
import edu.tigers.sumatra.math.AVector3;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;



public class BotWatcher implements ITigerBotV3Observer
{
	private final TigerBotV3	bot;
	private CSVExporter			exporter	= null;
	private long					frameId	= 0;
													
													
	
	public BotWatcher(final TigerBotV3 bot)
	{
		this.bot = bot;
	}
	
	
	
	public void start()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		exporter = new CSVExporter("data/botstatus/" + sdf.format(new Date()), false);
		bot.addObserver(this);
	}
	
	
	
	public void stop()
	{
		bot.removeObserver(this);
		exporter.close();
	}
	
	
	@Override
	public void onNewFeedbackCmd(final TigerSystemMatchFeedback cmd)
	{
		IVector3 pos = new Vector3(cmd.getPosition(), cmd.getOrientation());
		IVector3 vel = new Vector3(cmd.getVelocity(), cmd.getAngularVelocity());
		List<Number> nbrs = new ArrayList<>();
		nbrs.add(bot.getBotId().getNumber());
		nbrs.addAll(bot.getColor().getNumberList());
		nbrs.addAll(pos.getNumberList());
		nbrs.addAll(vel.getNumberList());
		nbrs.addAll(AVector3.ZERO_VECTOR.getNumberList());
		nbrs.add(frameId);
		nbrs.add(System.nanoTime());
		nbrs.add(cmd.isPositionValid() ? 1 : 0);
		nbrs.add(cmd.isVelocityValid() ? 1 : 0);
		nbrs.add(cmd.isAccelerationValid() ? 1 : 0);
		exporter.addValues(nbrs);
		frameId++;
	}
	
	
	@Override
	public void onConsolePrint(final TigerSystemConsolePrint cmd)
	{
	}
}
