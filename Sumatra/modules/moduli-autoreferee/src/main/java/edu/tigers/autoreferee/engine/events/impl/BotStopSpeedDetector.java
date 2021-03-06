/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.engine.events.impl;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.github.g3force.configurable.Configurable;
import com.google.common.collect.Sets;

import edu.tigers.autoreferee.AutoRefConfig;
import edu.tigers.autoreferee.AutoRefUtil;
import edu.tigers.autoreferee.AutoRefUtil.ToBotIDMapper;
import edu.tigers.autoreferee.IAutoRefFrame;
import edu.tigers.autoreferee.engine.events.EGameEvent;
import edu.tigers.autoreferee.engine.events.IGameEvent;
import edu.tigers.autoreferee.engine.events.SpeedViolation;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public class BotStopSpeedDetector extends APreparingGameEventDetector
{
	private static final int		priority						= 1;
	private static final Logger	log							= Logger.getLogger(BotStopSpeedDetector.class);
	
	@Configurable(comment = "[ms] The number of milliseconds that a bot needs violate the stop speed limit to be reported")
	private static long				VIOLATION_THRESHOLD_MS	= 300;
	
	private Map<BotID, Long>		currentViolators			= new HashMap<>();
	private Set<BotID>				lastViolators				= new HashSet<>();
	
	static
	{
		AGameEventDetector.registerClass(BotStopSpeedDetector.class);
	}
	
	
	
	public BotStopSpeedDetector()
	{
		super(EnumSet.of(EGameStateNeutral.STOPPED,
				EGameStateNeutral.PREPARE_KICKOFF_BLUE, EGameStateNeutral.PREPARE_KICKOFF_YELLOW));
	}
	
	
	@Override
	public int getPriority()
	{
		return priority;
	}
	
	
	@Override
	protected void prepare(final IAutoRefFrame frame)
	{
		
		Set<BotID> violators = getViolators(frame.getWorldFrame().getBots().values());
		violators.forEach(id -> currentViolators.put(id, TimeUnit.MILLISECONDS.toNanos(VIOLATION_THRESHOLD_MS)));
		lastViolators.addAll(violators);
	}
	
	
	@Override
	public Optional<IGameEvent> doUpdate(final IAutoRefFrame frame, final List<IGameEvent> violations)
	{
		IBotIDMap<ITrackedBot> bots = frame.getWorldFrame().getBots();
		
		Set<BotID> botIDs = AutoRefUtil.mapToID(bots);
		
		long delta = frame.getTimestamp() - frame.getPreviousFrame().getTimestamp();
		Set<BotID> frameViolators = getViolators(bots.values());
		Set<BotID> frameNonViolators = Sets.difference(botIDs, frameViolators);
		updateCurrentViolators(frameViolators, frameNonViolators, delta);
		
		
		
		Set<BotID> frameCountViolators = currentViolators.keySet().stream()
				.filter(id -> {
					long value = currentViolators.get(id);
					if ((value >= TimeUnit.MILLISECONDS.toNanos(VIOLATION_THRESHOLD_MS))
							|| (lastViolators.contains(id) && (value > 0)))
					{
						return true;
					}
					return false;
				}).collect(Collectors.toSet());
		
		Set<BotID> oldViolators = Sets.difference(lastViolators, frameCountViolators).immutableCopy();
		lastViolators.removeAll(oldViolators);
		
		Optional<BotID> optViolator = Sets.difference(frameCountViolators, lastViolators).stream().findFirst();
		if (optViolator.isPresent())
		{
			BotID violator = optViolator.get();
			ITrackedBot bot = bots.getWithNull(violator);
			if (bot == null)
			{
				log.debug("Bot Stop Speed violator disappeard from the field: " + violator);
				return Optional.empty();
			}
			lastViolators.add(violator);
			
			SpeedViolation violation = new SpeedViolation(EGameEvent.BOT_STOP_SPEED, frame.getTimestamp(), violator,
					null, bot.getVel().getLength());
			return Optional.of(violation);
		}
		
		return Optional.empty();
	}
	
	
	private Set<BotID> getViolators(final Collection<ITrackedBot> bots)
	{
		return bots.stream()
				.filter(bot -> bot.getVel().getLength() > AutoRefConfig.getMaxBotStopSpeed())
				.map(ToBotIDMapper.get())
				.collect(Collectors.toSet());
	}
	
	
	private void updateCurrentViolators(final Set<BotID> violators, final Set<BotID> nonViolators, final long timeDelta)
	{
		for (BotID violator : violators)
		{
			long value = 0;
			if (currentViolators.containsKey(violator))
			{
				value = currentViolators.get(violator);
			}
			if (value < TimeUnit.MILLISECONDS.toNanos(VIOLATION_THRESHOLD_MS))
			{
				value += timeDelta;
			}
			currentViolators.put(violator, value);
		}
		
		for (BotID nonViolator : nonViolators)
		{
			if (currentViolators.containsKey(nonViolator))
			{
				long value = currentViolators.get(nonViolator);
				value -= timeDelta;
				if (value <= 0)
				{
					currentViolators.remove(nonViolator);
				} else
				{
					currentViolators.put(nonViolator, value);
				}
			}
		}
	}
	
	
	@Override
	public void doReset()
	{
		lastViolators.clear();
		currentViolators.clear();
	}
	
}
