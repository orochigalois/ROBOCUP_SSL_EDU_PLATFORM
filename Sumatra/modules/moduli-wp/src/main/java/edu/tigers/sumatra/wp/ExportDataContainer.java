/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import Jama.Matrix;
import edu.tigers.sumatra.cam.data.CamBall;
import edu.tigers.sumatra.cam.data.CamRobot;
import edu.tigers.sumatra.export.IJsonString;
import edu.tigers.sumatra.export.INumberListable;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.AVector;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.TrackedBall;
import edu.tigers.sumatra.wp.data.TrackedBot;



public class ExportDataContainer
{
	@SuppressWarnings("unused")
	private static final Logger						log						= Logger.getLogger(ExportDataContainer.class
			.getName());
	
	private final List<CamBall>						balls						= new ArrayList<>();
	private final List<CamRobot>						rawBots					= new ArrayList<>();
	private final List<WpBot>							wpBots					= new ArrayList<>();
	private final List<WpBot>							isBots					= new ArrayList<>();
	private final List<SkillBot>						skillBots				= new ArrayList<>();
	private CamBall										curBall					= new CamBall();
	private WpBall											wpBall					= new WpBall();
	private FrameInfo										frameInfo				= new FrameInfo();
	private final Map<String, INumberListable>	customNumberListable	= new HashMap<>();
	
	
	
	public static WpBot trackedBot2WpBot(final ITrackedBot tBot, final long frameId, final long timestamp)
	{
		IVector3 pos = new Vector3(tBot.getPos(), tBot.getAngle());
		IVector3 vel = new Vector3(tBot.getVel(), tBot.getaVel());
		IVector3 acc = new Vector3(tBot.getAcc(), tBot.getaAcc());
		return new WpBot(pos, vel, acc, tBot.getBotId().getNumber(), tBot.getBotId().getTeamColor(), frameId, timestamp);
	}
	
	
	
	public static List<Number> toNumberList(final Matrix m)
	{
		double[] arr = m.getRowPackedCopy();
		List<Number> list = new ArrayList<>(arr.length);
		for (double d : arr)
		{
			list.add(d);
		}
		return list;
	}
	
	
	
	public static List<CamBall> readRawBall(final String folder, final String filename)
	{
		try
		{
			return Files
					.lines(Paths.get(folder + "/" + filename + ".csv"))
					.filter(line -> !line.startsWith("#"))
					.map(line -> line.split(","))
					.map(arr -> Arrays.asList(arr).stream()
							.map(s -> Double.valueOf(s))
							.collect(Collectors.toList()))
					.map(l -> CamBall.fromNumberList(l))
					.collect(Collectors.toList());
		} catch (IOException err)
		{
			log.error("Could not read raw balls.", err);
			return new ArrayList<>();
		}
	}
	
	
	
	public static List<FrameInfo> readFrameInfo(final String folder, final String filename)
	{
		try
		{
			return Files
					.lines(Paths.get(folder + "/" + filename + ".csv"))
					.filter(line -> !line.startsWith("#"))
					.map(line -> line.split(","))
					.map(arr -> Arrays.asList(arr).stream()
							.map(s -> Double.valueOf(s))
							.collect(Collectors.toList()))
					.map(l -> FrameInfo.fromNumberList(l))
					.collect(Collectors.toList());
		} catch (IOException err)
		{
			return new ArrayList<>();
		}
	}
	
	
	
	public static List<CamRobot> readRawBots(final String folder, final String filename, final ETeamColor color)
	{
		int colorNumber = color.getNumberList().get(0).intValue();
		try
		{
			return Files
					.lines(Paths.get(folder + "/" + filename + ".csv"))
					.filter(line -> !line.startsWith("#"))
					.map(line -> line.split(","))
					.filter(arr -> Integer.valueOf(arr[3]) == colorNumber)
					.map(arr -> Arrays.asList(arr).stream()
							.map(s -> Double.valueOf(s))
							.collect(Collectors.toList()))
					.map(l -> CamRobot.fromNumberList(l))
					.collect(Collectors.toList());
		} catch (IOException err)
		{
			throw new IllegalStateException();
		}
	}
	
	
	
	public final List<CamBall> getBalls()
	{
		return balls;
	}
	
	
	
	public final CamBall getCurBall()
	{
		return curBall;
	}
	
	
	
	public final void setCurBall(final CamBall curBall)
	{
		this.curBall = curBall;
	}
	
	
	
	public final WpBall getWpBall()
	{
		return wpBall;
	}
	
	
	
	public final void setWpBall(final WpBall wpBall)
	{
		this.wpBall = wpBall;
	}
	
	
	
	public final List<CamRobot> getRawBots()
	{
		return rawBots;
	}
	
	
	
	public final List<WpBot> getWpBots()
	{
		return wpBots;
	}
	
	
	
	public final List<SkillBot> getSkillBots()
	{
		return skillBots;
	}
	
	
	
	public List<WpBot> getIsBots()
	{
		return isBots;
	}
	
	
	
	public final Map<String, INumberListable> getCustomNumberListable()
	{
		return customNumberListable;
	}
	
	
	
	public FrameInfo getFrameInfo()
	{
		return frameInfo;
	}
	
	
	
	public void setFrameInfo(final FrameInfo frameInfo)
	{
		this.frameInfo = frameInfo;
	}
	
	
	
	public static class WpBall implements IJsonString, INumberListable
	{
		private IVector3	pos			= Vector3.ZERO_VECTOR;
		private IVector3	vel			= Vector3.ZERO_VECTOR;
		private IVector3	acc			= Vector3.ZERO_VECTOR;
		private long		frameId		= -1;
		private long		timestamp	= 0;
		private double		confidence	= 0;
		
		
		
		public WpBall()
		{
		}
		
		
		
		public WpBall(final IVector3 pos, final IVector3 vel, final IVector3 acc, final long frameId,
				final long timestamp, final double confidence)
		{
			super();
			this.pos = pos;
			this.vel = vel;
			this.acc = acc;
			this.frameId = frameId;
			this.timestamp = timestamp;
			this.confidence = confidence;
		}
		
		
		
		public static WpBall fromNumberList(final List<? extends Number> list)
		{
			return new WpBall(AVector.fromNumberList(list.subList(0, 3)).getXYZVector(),
					AVector.fromNumberList(list.subList(3, 6)).getXYZVector(),
					AVector.fromNumberList(list.subList(6, 9)).getXYZVector(),
					list.size() > 10 ? list.get(10).longValue() : -1,
					list.size() > 11 ? list.get(11).longValue() : -1,
					list.size() > 12 ? list.get(12).doubleValue() : 0);
		}
		
		
		
		public TrackedBall toTrackedBall()
		{
			return new TrackedBall(pos, vel, acc);
		}
		
		
		@Override
		public JSONObject toJSON()
		{
			Map<String, Object> jsonMapping = new LinkedHashMap<String, Object>();
			jsonMapping.put("pos", pos.toJSON());
			jsonMapping.put("vel", vel.toJSON());
			jsonMapping.put("frameId", frameId);
			jsonMapping.put("timestamp", timestamp);
			return new JSONObject(jsonMapping);
		}
		
		
		@Override
		public List<Number> getNumberList()
		{
			List<Number> numbers = new ArrayList<>();
			numbers.addAll(pos.getNumberList());
			numbers.addAll(vel.getNumberList());
			numbers.addAll(acc.getNumberList());
			numbers.add(frameId);
			numbers.add(timestamp);
			numbers.add(confidence);
			return numbers;
		}
		
		
		
		public final IVector3 getPos()
		{
			return pos;
		}
		
		
		
		public final void setPos(final IVector3 pos)
		{
			this.pos = pos;
		}
		
		
		
		public final IVector3 getVel()
		{
			return vel;
		}
		
		
		
		public final void setVel(final IVector3 vel)
		{
			this.vel = vel;
		}
		
		
		
		public final IVector3 getAcc()
		{
			return acc;
		}
		
		
		
		public final void setAcc(final IVector3 acc)
		{
			this.acc = acc;
		}
		
		
		
		public final long getFrameId()
		{
			return frameId;
		}
		
		
		
		public final void setFrameId(final long frameId)
		{
			this.frameId = frameId;
		}
		
		
		
		public final long getTimestamp()
		{
			return timestamp;
		}
		
		
		
		public final void setTimestamp(final long timestamp)
		{
			this.timestamp = timestamp;
		}
		
		
		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("WpBall [pos=");
			builder.append(pos);
			builder.append(", vel=");
			builder.append(vel);
			builder.append(", acc=");
			builder.append(acc);
			builder.append(", frameId=");
			builder.append(frameId);
			builder.append(", timestamp=");
			builder.append(timestamp);
			builder.append("]");
			return builder.toString();
		}
	}
	
	
	
	public static class WpBot implements IJsonString, INumberListable
	{
		private int				id				= -1;
		private ETeamColor	color			= ETeamColor.UNINITIALIZED;
		private IVector3		pos			= Vector3.ZERO_VECTOR;
		private IVector3		vel			= Vector3.ZERO_VECTOR;
		private IVector3		acc			= Vector3.ZERO_VECTOR;
		private long			frameId		= -1;
		private long			timestamp	= 0;
		
		
		
		public WpBot()
		{
		}
		
		
		
		public WpBot(final IVector3 pos, final IVector3 vel, final IVector3 acc, final int id, final ETeamColor color,
				final long frameId, final long timestamp)
		{
			super();
			this.pos = pos;
			this.vel = vel;
			this.acc = acc;
			this.id = id;
			this.color = color;
			this.frameId = frameId;
			this.timestamp = timestamp;
		}
		
		
		
		public static WpBot fromNumberList(final List<? extends Number> list)
		{
			throw new IllegalStateException("check");
			// return new WpBot(AVector.fromNumberList(list.subList(0, 3)).getXYZVector(),
			// AVector.fromNumberList(list.subList(3, 6)).getXYZVector(),
			// AVector.fromNumberList(list.subList(6, 9)).getXYZVector(),
			// list.get(9).intValue(),
			// ETeamColor.fromNumberList(list.get(10)),
			// list.size() > 11 ? list.get(11).longValue() : -1,
			// list.size() > 12 ? list.get(12).longValue() : -1);
		}
		
		
		
		public ITrackedBot toTrackedBot()
		{
			BotID botId = BotID.createBotId(id, color);
			TrackedBot tBot = new TrackedBot(timestamp, botId);
			tBot.setPos(pos.getXYVector());
			tBot.setVel(vel.getXYVector());
			tBot.setAcc(acc.getXYVector());
			tBot.setAngle(pos.z());
			tBot.setaVel(vel.z());
			tBot.setaAcc(acc.z());
			return tBot;
		}
		
		
		@Override
		public JSONObject toJSON()
		{
			Map<String, Object> jsonMapping = new LinkedHashMap<String, Object>();
			jsonMapping.put("pos", pos.toJSON());
			jsonMapping.put("vel", vel.toJSON());
			jsonMapping.put("acc", acc.toJSON());
			jsonMapping.put("id", id);
			jsonMapping.put("color", color.name());
			jsonMapping.put("frameId", frameId);
			jsonMapping.put("timestamp", timestamp);
			return new JSONObject(jsonMapping);
		}
		
		
		@Override
		public List<Number> getNumberList()
		{
			List<Number> numbers = new ArrayList<>();
			numbers.add(id);
			numbers.addAll(color.getNumberList());
			numbers.addAll(pos.getNumberList());
			numbers.addAll(vel.getNumberList());
			numbers.addAll(acc.getNumberList());
			numbers.add(frameId);
			numbers.add(timestamp);
			return numbers;
		}
		
		
		
		public final int getId()
		{
			return id;
		}
		
		
		
		public final void setId(final int id)
		{
			this.id = id;
		}
		
		
		
		public final ETeamColor getColor()
		{
			return color;
		}
		
		
		
		public final void setColor(final ETeamColor color)
		{
			this.color = color;
		}
		
		
		
		public final IVector3 getPos()
		{
			return pos;
		}
		
		
		
		public final void setPos(final IVector3 pos)
		{
			this.pos = pos;
		}
		
		
		
		public final IVector3 getVel()
		{
			return vel;
		}
		
		
		
		public final void setVel(final IVector3 vel)
		{
			this.vel = vel;
		}
		
		
		
		public final long getFrameId()
		{
			return frameId;
		}
		
		
		
		public final void setFrameId(final long frameId)
		{
			this.frameId = frameId;
		}
		
		
		
		public final long getTimestamp()
		{
			return timestamp;
		}
		
		
		
		public final void setTimestamp(final long timestamp)
		{
			this.timestamp = timestamp;
		}
		
		
		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			builder.append("WpBot [id=");
			builder.append(id);
			builder.append(", color=");
			builder.append(color);
			builder.append(", pos=");
			builder.append(pos);
			builder.append(", vel=");
			builder.append(vel);
			builder.append(", frameId=");
			builder.append(frameId);
			builder.append(", timestamp=");
			builder.append(timestamp);
			builder.append("]");
			return builder.toString();
		}
		
		
		
		public IVector3 getAcc()
		{
			return acc;
		}
		
		
		
		public void setAcc(final IVector3 acc)
		{
			this.acc = acc;
		}
	}
	
	
	public static class SkillBot implements IJsonString, INumberListable
	{
		private int				id				= -1;
		private ETeamColor	color			= ETeamColor.UNINITIALIZED;
		private long			timestamp	= 0;
		private IVector3		trajVel		= Vector3.ZERO_VECTOR;
		private IVector3		trajPos		= Vector3.ZERO_VECTOR;
		private IVector3		setVel		= Vector3.ZERO_VECTOR;
		private IVector3		setPos		= Vector3.ZERO_VECTOR;
		private IVector3		localVel		= Vector3.ZERO_VECTOR;
		
		
		
		public SkillBot()
		{
		}
		
		
		
		public SkillBot(final int id, final ETeamColor color, final long timestamp)
		{
			super();
			this.id = id;
			this.color = color;
			this.timestamp = timestamp;
		}
		
		
		
		public static SkillBot fromNumberList(final List<? extends Number> list)
		{
			// SkillBot sb = new SkillBot(list.get(0).intValue(),
			// ETeamColor.fromNumberList(list.get(1)),
			// list.get(2).longValue());
			throw new NotImplementedException();
		}
		
		
		@Override
		public JSONObject toJSON()
		{
			Map<String, Object> jsonMapping = new LinkedHashMap<String, Object>();
			jsonMapping.put("trajVel", trajVel.toJSON());
			jsonMapping.put("trajPos", trajPos.toJSON());
			jsonMapping.put("setVel", setVel.toJSON());
			jsonMapping.put("setPos", setPos.toJSON());
			jsonMapping.put("localVel", localVel.toJSON());
			jsonMapping.put("id", id);
			jsonMapping.put("color", color.name());
			jsonMapping.put("timestamp", timestamp);
			return new JSONObject(jsonMapping);
		}
		
		
		@Override
		public List<Number> getNumberList()
		{
			List<Number> numbers = new ArrayList<>();
			numbers.add(id);
			numbers.addAll(color.getNumberList());
			numbers.add(timestamp);
			numbers.add(trajVel.x());
			numbers.add(trajVel.y());
			numbers.add(trajPos.x());
			numbers.add(trajPos.y());
			numbers.addAll(setVel.getNumberList());
			numbers.addAll(setPos.getNumberList());
			numbers.addAll(localVel.getNumberList());
			numbers.add(trajVel.z());
			numbers.add(trajPos.z());
			return numbers;
		}
		
		
		
		public int getId()
		{
			return id;
		}
		
		
		
		public void setId(final int id)
		{
			this.id = id;
		}
		
		
		
		public ETeamColor getColor()
		{
			return color;
		}
		
		
		
		public void setColor(final ETeamColor color)
		{
			this.color = color;
		}
		
		
		
		public long getTimestamp()
		{
			return timestamp;
		}
		
		
		
		public void setTimestamp(final long timestamp)
		{
			this.timestamp = timestamp;
		}
		
		
		
		public IVector3 getTrajVel()
		{
			return trajVel;
		}
		
		
		
		public void setTrajVel(final IVector3 trajVel)
		{
			this.trajVel = trajVel;
		}
		
		
		
		public IVector3 getTrajPos()
		{
			return trajPos;
		}
		
		
		
		public void setTrajPos(final IVector3 trajPos)
		{
			this.trajPos = trajPos;
		}
		
		
		
		public IVector3 getSetVel()
		{
			return setVel;
		}
		
		
		
		public void setSetVel(final IVector3 setVel)
		{
			this.setVel = setVel;
		}
		
		
		
		public IVector3 getSetPos()
		{
			return setPos;
		}
		
		
		
		public void setSetPos(final IVector3 setPos)
		{
			this.setPos = setPos;
		}
		
		
		
		public IVector3 getLocalVel()
		{
			return localVel;
		}
		
		
		
		public void setLocalVel(final IVector3 localVel)
		{
			this.localVel = localVel;
		}
	}
	
	
	public static class FrameInfo implements IJsonString, INumberListable
	{
		private long	frameId;
		private int		camId;
		private long	tCapture;
		private long	tSent;
		private long	tRecorded;
		
		
		
		public FrameInfo()
		{
		}
		
		
		
		public FrameInfo(final long frameId, final int camId, final long tCapture, final long tSent, final long tRecorded)
		{
			super();
			this.frameId = frameId;
			this.camId = camId;
			this.tCapture = tCapture;
			this.tSent = tSent;
			this.tRecorded = tRecorded;
		}
		
		
		
		public static FrameInfo fromNumberList(final List<? extends Number> list)
		{
			return new FrameInfo(list.get(0).longValue(),
					list.get(1).intValue(),
					list.get(2).longValue(),
					list.get(3).longValue(),
					list.get(4).longValue());
		}
		
		
		@Override
		public JSONObject toJSON()
		{
			Map<String, Object> jsonMapping = new LinkedHashMap<String, Object>();
			jsonMapping.put("frameId", frameId);
			jsonMapping.put("camId", camId);
			jsonMapping.put("tCapture", tCapture);
			jsonMapping.put("tSent", tSent);
			jsonMapping.put("tRecorded", tRecorded);
			return new JSONObject(jsonMapping);
		}
		
		
		@Override
		public List<Number> getNumberList()
		{
			List<Number> numbers = new ArrayList<>();
			numbers.add(frameId);
			numbers.add(camId);
			numbers.add(tCapture);
			numbers.add(tSent);
			numbers.add(tRecorded);
			return numbers;
		}
		
		
		
		public long getFrameId()
		{
			return frameId;
		}
		
		
		
		public void setFrameId(final long frameId)
		{
			this.frameId = frameId;
		}
		
		
		
		public int getCamId()
		{
			return camId;
		}
		
		
		
		public void setCamId(final int camId)
		{
			this.camId = camId;
		}
		
		
		
		public long gettCapture()
		{
			return tCapture;
		}
		
		
		
		public void settCapture(final long tCapture)
		{
			this.tCapture = tCapture;
		}
		
		
		
		public long gettSent()
		{
			return tSent;
		}
		
		
		
		public void settSent(final long tSent)
		{
			this.tSent = tSent;
		}
		
		
		
		public long gettRecorded()
		{
			return tRecorded;
		}
		
		
		
		public void settRecorded(final long tRecorded)
		{
			this.tRecorded = tRecorded;
		}
	}
}
