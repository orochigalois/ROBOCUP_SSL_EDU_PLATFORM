/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.botcenter.bots;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;



public class MotorInputPanel extends JPanel
{
	
	public interface IMotorInputPanelObserver
	{
		
		void onSetSpeed(double x, double y, double w);
	}
	
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final long							serialVersionUID	= -5046635212496152005L;
	private JTextField									xSpeed				= null;
	private JTextField									ySpeed				= null;
	private JTextField									wSpeed				= null;
	
	private final List<IMotorInputPanelObserver>	observers			= new ArrayList<IMotorInputPanelObserver>();
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public MotorInputPanel()
	{
		setLayout(new MigLayout("wrap 2", "[100]10[100,fill]", ""));
		
		xSpeed = new JTextField("0");
		ySpeed = new JTextField("0");
		wSpeed = new JTextField("0");
		
		final JButton setSpeed = new JButton("Set Speed");
		setSpeed.addActionListener(new SetSpeed());
		
		add(new JLabel("X Speed:"));
		add(xSpeed);
		add(new JLabel("Y Speed:"));
		add(ySpeed);
		add(new JLabel("W Speed:"));
		add(wSpeed);
		add(setSpeed, "span 2");
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addObserver(final IMotorInputPanelObserver observer)
	{
		synchronized (observers)
		{
			observers.add(observer);
		}
	}
	
	
	
	public void removeObserver(final IMotorInputPanelObserver observer)
	{
		synchronized (observers)
		{
			observers.remove(observer);
		}
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// -------------------------------------------------------------------------
	
	private class SetSpeed implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			double x;
			double y;
			double w;
			
			try
			{
				x = Double.valueOf(xSpeed.getText());
				y = Double.valueOf(ySpeed.getText());
				w = Double.valueOf(wSpeed.getText());
			}
			
			catch (final NumberFormatException ex)
			{
				return;
			}
			
			notifySetSpeed(x, y, w);
		}
	}
	
	
	private void notifySetSpeed(final double x, final double y, final double w)
	{
		synchronized (observers)
		{
			for (final IMotorInputPanelObserver observer : observers)
			{
				observer.onSetSpeed(x, y, w);
			}
		}
	}
}
