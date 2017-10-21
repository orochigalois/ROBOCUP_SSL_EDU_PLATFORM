/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.tigers.moduli.exceptions.ModuleNotFoundException;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.ids.IBotIDMap;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public final class AutoRefUtil
{
	
	
	public static class ColorFilter implements Predicate<ITrackedBot>
	{
		private static Map<ETeamColor, ColorFilter>	filters;
		private final ETeamColor							color;
		
		static
		{
			Map<ETeamColor, ColorFilter> tempFilters = new HashMap<>();
			Arrays.stream(ETeamColor.values()).forEach(color -> tempFilters.put(color, new ColorFilter(color)));
			filters = Collections.unmodifiableMap(tempFilters);
		}
		
		
		
		public ColorFilter(final ETeamColor color)
		{
			this.color = color;
		}
		
		
		@Override
		public boolean test(final ITrackedBot bot)
		{
			return bot.getBotId().getTeamColor() == color;
		}
		
		
		
		public static ColorFilter get(final ETeamColor color)
		{
			return filters.get(color);
		}
	}
	
	
	public static class ToBotIDMapper implements Function<ITrackedBot, BotID>
	{
		private static final ToBotIDMapper	INSTANCE	= new ToBotIDMapper();
		
		
		@Override
		public BotID apply(final ITrackedBot bot)
		{
			return bot.getBotId();
		}
		
		
		
		public static ToBotIDMapper get()
		{
			return INSTANCE;
		}
		
	}
	
	
	
	public static List<ITrackedBot> filterByColor(final Collection<ITrackedBot> bots, final ETeamColor color)
	{
		return bots.stream()
				.filter(ColorFilter.get(color))
				.collect(Collectors.toList());
	}
	
	
	
	public static List<ITrackedBot> filterByColor(final IBotIDMap<ITrackedBot> bots, final ETeamColor color)
	{
		return filterByColor(bots.values(), color);
	}
	
	
	
	public static Set<BotID> mapToID(final Collection<ITrackedBot> bots)
	{
		return bots.stream()
				.map(ToBotIDMapper.get())
				.collect(Collectors.toSet());
	}
	
	
	
	public static Set<BotID> mapToID(final IBotIDMap<ITrackedBot> bots)
	{
		return mapToID(bots.values());
	}
	
	
	
	public static Optional<AutoRefModule> getAutoRefModule()
	{
		try
		{
			AutoRefModule autoref = (AutoRefModule) SumatraModel.getInstance().getModule(AutoRefModule.MODULE_ID);
			return Optional.of(autoref);
		} catch (ModuleNotFoundException e)
		{
		}
		return Optional.empty();
	}
	
	
	
	public static void ifAutoRefModulePresent(final Consumer<? super AutoRefModule> consumer)
	{
		Optional<AutoRefModule> module = getAutoRefModule();
		module.ifPresent(consumer);
	}
}
