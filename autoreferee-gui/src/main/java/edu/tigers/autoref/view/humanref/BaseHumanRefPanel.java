/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.humanref;

import java.time.Duration;
import java.util.Map;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.tigers.autoref.view.humanref.components.GameStatePanel;
import edu.tigers.autoref.view.humanref.components.ScorePanel;
import edu.tigers.autoref.view.humanref.components.TimePanel;
import edu.tigers.sumatra.Referee.SSL_Referee.Stage;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.wp.data.EGameStateNeutral;



public class BaseHumanRefPanel extends AHumanRefPanel
{
	
	
	private static final long	serialVersionUID	= -8134789041519501028L;
	
	private TimePanel				timePanel;
	private ScorePanel			goalPanel;
	
	private GameStatePanel		statePanel;
	
	
	
	public BaseHumanRefPanel()
	{
		
	}
	
	
	@Override
	protected void setupGUI()
	{
		timePanel = new TimePanel(regularFont);
		goalPanel = new ScorePanel(smallFont, headerFont);
		statePanel = new GameStatePanel(headerFont);
		
		super.setupGUI();
	}
	
	
	@Override
	protected void fillVerticalLayout()
	{
		JPanel timeAndGoalsPanel = new JPanel(new MigLayout("fillx", "[]20[]"));
		timeAndGoalsPanel.add(timePanel, "alignx left");
		timeAndGoalsPanel.add(goalPanel, "alignx right");
		
		JPanel headerPanel = getHeaderPanel();
		headerPanel.setLayout(new MigLayout("fillx, ins 5", "[fill]"));
		headerPanel.add(timeAndGoalsPanel, "wrap 1%");
		headerPanel.add(statePanel, "center, wrap 2%");
	}
	
	
	@Override
	protected void fillHorizontalLayout()
	{
		JPanel headerPanel = getHeaderPanel();
		
		headerPanel.setLayout(new MigLayout("fill", "[left][fill][right]"));
		headerPanel.add(timePanel, "alignx left, aligny top");
		headerPanel.add(statePanel, "aligny center, alignx center");
		headerPanel.add(goalPanel, "alignx right, aligny top");
	}
	
	
	
	public void setTimeLeft(final Duration duration)
	{
		timePanel.setTimeLeft(duration);
	}
	
	
	
	public void setStage(final Stage stage)
	{
		timePanel.setStage(stage);
	}
	
	
	
	public void setTeamNames(final Map<ETeamColor, String> names)
	{
		goalPanel.setTeamNames(names);
	}
	
	
	
	public void setGoals(final Map<ETeamColor, Integer> goals)
	{
		goalPanel.setTeamScores(goals);
	}
	
	
	
	public void setState(final EGameStateNeutral state)
	{
		statePanel.setState(state);
	}
	
}
