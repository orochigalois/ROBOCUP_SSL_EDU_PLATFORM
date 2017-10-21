/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.sleepycat.persist.model.Persistent;

import edu.tigers.sumatra.ai.data.ballpossession.EBallPossession;
import edu.tigers.sumatra.ai.data.statistics.calculators.PenaltyStats;
import edu.tigers.sumatra.ai.data.statistics.calculators.StatisticData;
import edu.tigers.sumatra.ai.pandora.roles.ERole;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.BotIDMap;
import edu.tigers.sumatra.statistics.MarkovChain;



@Persistent(version = 4)
public class MatchStatistics
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final Logger							log							= Logger.getLogger(MatchStatistics.class
																											.getName());
	
	private final Map<EBallPossession, Percentage>	ballPossessionGeneral;
	private Map<Integer, Percentage>						ballPossessionTigers;
	private final Map<BotID, Percentage>				ballPossessionOpponents;
	
	private Map<BotID, Percentage>						tackleWon;
	private Map<BotID, Percentage>						tackleLost;
	private Percentage										tackleGeneralWon;
	private Percentage										tackleGeneralLost;
	
	private int													possibleTigersGoals		= 0;
	private int													possibleOpponentsGoals	= 0;
	private Map<BotID, Percentage>						possibleBotGoals;
	
	private List<PenaltyStats>								bestPenaltyShooters;
	
	private BotIDMap<Integer>								countFramesAsDefender;
	private BotIDMap<Integer>								countFramesAsOffensive;
	private BotIDMap<Integer>								countFramesAsSupporter;
	
	private BotIDMap<MarkovChain<ERole>>				roleTransitions;
	
	private Map<Integer, Percentage>						passAccuracy;
	
	
	public enum EAvailableStatistic
	{
		
		BallPossession("Ballbesitz"),
		
		DefensiveToOffensive("D->O"),
		
		DefensiveToSupport("D->S"),
		
		FramesAsDefender("Defender"),
		
		FramesAsOffensive("Offensive"),
		
		FramesAsSupport("Supporter"),
		
		GoalsScored("Tore"),
		
		OffensiveToSupport("O->S"),
		
		OffensiveToDefensive("O->D"),
		
		PassTarget("Passziel"),
		
		SupportToDefensive("S->D"),
		
		SupportToOffensive("S->O"),
		
		TacklesWon("Gewonnene Zweikämpfe"),
		
		TacklesLost("Verlorene Zweikämpfe");
		
		private String	descriptor;
		
		
		EAvailableStatistic(final String descriptor)
		{
			this.descriptor = descriptor;
		}
		
		
		
		public String getDescriptor()
		{
			return descriptor;
		}
	}
	
	private Map<EAvailableStatistic, StatisticData>	statistics	= new HashMap<>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public Map<EAvailableStatistic, StatisticData> getStatistics()
	{
		return statistics;
	}
	
	
	
	public void putStatisticData(final EAvailableStatistic key, final StatisticData value)
	{
		statistics.put(key, value);
	}
	
	
	
	public MatchStatistics()
	{
		ballPossessionGeneral = new HashMap<EBallPossession, Percentage>();
		for (EBallPossession bp : EBallPossession.values())
		{
			ballPossessionGeneral.put(bp, new Percentage());
		}
		ballPossessionTigers = new HashMap<Integer, Percentage>();
		ballPossessionOpponents = new HashMap<BotID, Percentage>();
		tackleWon = new HashMap<BotID, Percentage>();
		tackleLost = new HashMap<BotID, Percentage>();
		tackleGeneralWon = new Percentage();
		tackleGeneralLost = new Percentage();
		possibleBotGoals = new HashMap<BotID, Percentage>();
		
		countFramesAsDefender = new BotIDMap<Integer>();
		countFramesAsOffensive = new BotIDMap<Integer>();
		countFramesAsSupporter = new BotIDMap<Integer>();
		
		roleTransitions = new BotIDMap<MarkovChain<ERole>>();
		
		passAccuracy = new HashMap<>();
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void printStatistic()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		StringBuffer message = new StringBuffer("\n");
		for (Entry<EBallPossession, Percentage> entry : ballPossessionGeneral.entrySet())
		{
			message.append(entry.getKey().name()).append(": ").append(df.format((entry.getValue().getPercent() * 100)))
					.append("%").append(" - "
					).append(entry.getValue().getCurrent()).append("\n");
		}
		for (Entry<Integer, Percentage> entry : ballPossessionTigers.entrySet())
		{
			message.append("Tiger: ").append(entry.getKey().intValue()).append(": "
					).append(df.format((entry.getValue().getPercent() * 100))).append("%"
					).append(" - "
					).append(entry.getValue().getCurrent()).append("\n");
		}
		for (Entry<BotID, Percentage> entry : ballPossessionOpponents.entrySet())
		{
			message.append("Opponent: ").append(entry.getKey().getNumber()).append(": "
					).append(df.format((entry.getValue().getPercent() * 100))).append("%"
					).append(" - "
					).append(entry.getValue().getCurrent()).append("\n");
		}
		for (Entry<BotID, Percentage> entry : tackleWon.entrySet())
		{
			message.append("Won: ").append(entry.getKey().getNumber()).append(": ")
					.append(df.format((entry.getValue().getPercent() * 100))
					).append("%"
					).append(" - "
					).append(entry.getValue().getCurrent()).append("\n");
		}
		for (Entry<BotID, Percentage> entry : tackleLost.entrySet())
		{
			message.append("Lost: ").append(entry.getKey().getNumber()).append(": ")
					.append(df.format((entry.getValue().getPercent() * 100))
					).append("%"
					).append(" - "
					).append(entry.getValue().getCurrent()).append("\n");
		}
		message.append("Won: ").append(df.format((tackleGeneralWon.getPercent() * 100))
				).append("%"
				).append(" - "
				).append(tackleGeneralWon.getCurrent()).append("\n");
		message.append("Lost: ").append(df.format((tackleGeneralLost.getPercent() * 100))
				).append("%"
				).append(" - "
				).append(tackleGeneralLost.getCurrent()).append("\n");
		log.trace(message.toString());
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public Map<EBallPossession, Percentage> getBallPossessionGeneral()
	{
		return ballPossessionGeneral;
	}
	
	
	
	public Map<Integer, Percentage> getBallPossessionTigers()
	{
		return ballPossessionTigers;
	}
	
	
	
	public void setBallPossessionTigers(final Map<Integer, Percentage> ballPossessionTigers2)
	{
		ballPossessionTigers = ballPossessionTigers2;
	}
	
	
	
	public Map<BotID, Percentage> getBallPossessionOpponents()
	{
		return ballPossessionOpponents;
	}
	
	
	
	public Map<BotID, Percentage> getTackleWon()
	{
		return tackleWon;
	}
	
	
	
	public void setTackleWon(final Map<BotID, Percentage> tackleWon)
	{
		this.tackleWon = tackleWon;
	}
	
	
	
	public Map<BotID, Percentage> getTackleLost()
	{
		return tackleLost;
	}
	
	
	
	public void setTackleLost(final Map<BotID, Percentage> tackleLost)
	{
		this.tackleLost = tackleLost;
	}
	
	
	
	public void setTackleGeneral(final Percentage tackleGeneralWon, final Percentage tackleGeneralLost)
	{
		this.tackleGeneralWon = tackleGeneralWon;
		this.tackleGeneralLost = tackleGeneralLost;
	}
	
	
	
	public Percentage getTackleGeneralWon()
	{
		return tackleGeneralWon;
	}
	
	
	
	public Percentage getTackleGeneralLost()
	{
		return tackleGeneralLost;
	}
	
	
	
	public int getPossibleTigersGoals()
	{
		return possibleTigersGoals;
	}
	
	
	
	public void setPossibleTigersGoals(final int possibleTigersGoals)
	{
		this.possibleTigersGoals = possibleTigersGoals;
	}
	
	
	
	public int getPossibleOpponentsGoals()
	{
		return possibleOpponentsGoals;
	}
	
	
	
	public void setPossibleOpponentsGoals(final int possibleOpponentsGoals)
	{
		this.possibleOpponentsGoals = possibleOpponentsGoals;
	}
	
	
	
	public void setPossibleBotGoals(final Map<BotID, Percentage> possibleBotGoals)
	{
		this.possibleBotGoals = possibleBotGoals;
	}
	
	
	
	public Map<BotID, Percentage> getPossibleBotGoals()
	{
		return possibleBotGoals;
	}
	
	
	
	public void setBestPenaltyShooterStats(final List<PenaltyStats> bestPenaltyShooters)
	{
		this.bestPenaltyShooters = bestPenaltyShooters;
	}
	
	
	
	public List<PenaltyStats> getBestPenaltyShooterStats()
	{
		return bestPenaltyShooters;
	}
	
	
	
	public BotIDMap<Integer> getCountFramesAsDefender()
	{
		return countFramesAsDefender;
	}
	
	
	
	public void setCountFramesAsDefender(final BotIDMap<Integer> countFramesAsDefender)
	{
		this.countFramesAsDefender = countFramesAsDefender;
	}
	
	
	
	public BotIDMap<Integer> getCountFramesAsOffensive()
	{
		return countFramesAsOffensive;
	}
	
	
	
	public void setCountFramesAsOffensive(final BotIDMap<Integer> countFramesAsOffensive)
	{
		this.countFramesAsOffensive = countFramesAsOffensive;
	}
	
	
	
	public BotIDMap<Integer> getCountFramesAsSupporter()
	{
		return countFramesAsSupporter;
	}
	
	
	
	public void setCountFramesAsSupporter(final BotIDMap<Integer> countFramesAsSupporter)
	{
		this.countFramesAsSupporter = countFramesAsSupporter;
	}
	
	
	
	public BotIDMap<MarkovChain<ERole>> getRoleTransitions()
	{
		return roleTransitions;
	}
	
	
	
	public void setRoleTransitions(final BotIDMap<MarkovChain<ERole>> roleTransitions)
	{
		this.roleTransitions = roleTransitions;
	}
	
	
	
	public Map<Integer, Percentage> getPassAccuracy()
	{
		return passAccuracy;
	}
	
	
	
	public void setPassAccuracy(final Map<Integer, Percentage> passAccuracyBots)
	{
		passAccuracy = passAccuracyBots;
	}
	
}
