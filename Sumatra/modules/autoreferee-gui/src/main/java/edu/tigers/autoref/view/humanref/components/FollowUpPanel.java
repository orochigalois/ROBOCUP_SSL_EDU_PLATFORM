/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.humanref.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.tigers.autoref.util.AutoRefImageRegistry;
import edu.tigers.autoreferee.engine.FollowUpAction;
import edu.tigers.autoreferee.engine.FollowUpAction.EActionType;
import edu.tigers.sumatra.components.JImagePanel;
import edu.tigers.sumatra.ids.ETeamColor;



public class FollowUpPanel extends JPanel
{
	
	
	private static final long	serialVersionUID	= -7928407957473455617L;
	
	private JImagePanel			arrowImagePanel	= new JImagePanel();
	private JLabel					typeLabel			= new JLabel();
	private JTeamLabel			teamLabel			= new JTeamLabel();
	
	
	
	public FollowUpPanel(final Font font)
	{
		setupGUI(font);
	}
	
	
	private void setupGUI(final Font font)
	{
		typeLabel.setText("Init");
		typeLabel.setFont(font);
		
		teamLabel.setFont(font);
		
		arrowImagePanel.setImage(AutoRefImageRegistry.getRightArrow());
		
		setLayout(new MigLayout("align left"));
		add(arrowImagePanel);
		add(typeLabel);
		add(teamLabel);
		
		int iconHeight = (int) (typeLabel.getPreferredSize().height * 2.2f);
		arrowImagePanel.setPreferredSize(new Dimension(iconHeight, iconHeight));
	}
	
	
	
	public void setFollowUp(final FollowUpAction action)
	{
		arrowImagePanel.setVisible(true);
		typeLabel.setText(getTypeText(action.getActionType()));
		ETeamColor color = action.getTeamInFavor();
		if (color.isNonNeutral())
		{
			teamLabel.setVisible(true);
			teamLabel.setTeam(color);
		} else
		{
			teamLabel.setVisible(false);
		}
	}
	
	
	
	public void clear()
	{
		typeLabel.setText(null);
		arrowImagePanel.setVisible(false);
		teamLabel.setVisible(false);
	}
	
	
	private String getTypeText(final EActionType type)
	{
		switch (type)
		{
			case DIRECT_FREE:
				return "Direct Free Kick ";
			case FORCE_START:
				return "Force Start";
			case INDIRECT_FREE:
				return "Indirect Free Kick ";
			case KICK_OFF:
				return "Kick Off ";
			case PENALTY:
				return "Penalty Kick ";
			default:
				return "";
		}
	}
}
