/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;
import edu.tigers.autoref.view.main.ActiveEnginePanel.IActiveEnginePanelObserver;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.sumatra.components.BasePanel;
import edu.tigers.sumatra.math.IVector2;



public class ActiveEnginePanel extends BasePanel<IActiveEnginePanelObserver>
{
	
	private static final long	serialVersionUID	= -8855537755362886421L;
	
	private JLabel					followUpLabel;
	private JLabel					teamInFavorLabel;
	private JLabel					positionLabel;
	private JButton				proceedButton;
	private JButton				resetButton;
	
	
	public interface IActiveEnginePanelObserver
	{
		
		public void onResetButtonPressed();
		
		
		
		public void onProceedButtonPressed();
	}
	
	
	
	public ActiveEnginePanel()
	{
		setLayout(new MigLayout("fill", "[50%][50%]", ""));
		setBorder(BorderFactory.createTitledBorder("Engine"));
		
		followUpLabel = new JLabel("");
		teamInFavorLabel = new JLabel("");
		positionLabel = new JLabel("");
		
		proceedButton = new JButton("Proceed");
		proceedButton.addActionListener(e -> informObserver(obs -> obs.onProceedButtonPressed()));
		
		resetButton = new JButton("Reset");
		resetButton.addActionListener(e -> informObserver(obs -> obs.onResetButtonPressed()));
		
		add(followUpLabel, "span, wrap");
		add(teamInFavorLabel, "span, wrap");
		add(positionLabel, "span, wrap");
		add(proceedButton, "grow, split 1");
		add(resetButton, "grow");
	}
	
	
	
	public void setNextAction(final FollowUpAction action)
	{
		String actionStr = "";
		String teamStr = "";
		String posStr = "";
		if (action != null)
		{
			actionStr = action.getActionType().toString();
			teamStr = action.getTeamInFavor().toString();
			if (action.getNewBallPosition().isPresent())
			{
				IVector2 pos = action.getNewBallPosition().get();
				posStr = String.format("%.3f | %.3f", pos.x(), pos.y());
			}
		}
		
		followUpLabel.setText("Next action: " + actionStr);
		teamInFavorLabel.setText("Team: " + teamStr);
		positionLabel.setText("Position: " + posStr);
	}
	
	
	
	public void setProceedButtonEnabled(final boolean enabled)
	{
		proceedButton.setEnabled(enabled);
	}
	
	
	@Override
	public void setPanelEnabled(final boolean enabled)
	{
		if (enabled == false)
		{
			setNextAction(null);
		}
		proceedButton.setEnabled(false);
		resetButton.setEnabled(enabled);
	}
}
