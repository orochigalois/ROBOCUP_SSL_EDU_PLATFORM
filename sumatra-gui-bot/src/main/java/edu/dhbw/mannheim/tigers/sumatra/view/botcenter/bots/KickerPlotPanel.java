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

import java.awt.Color;

import javax.swing.JPanel;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.axis.AAxis;
import info.monitorenter.gui.chart.axis.AxisLinear;
import info.monitorenter.gui.chart.axis.scalepolicy.AxisScalePolicyAutomaticBestFit;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyHighestValues;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.util.Range;
import net.miginfocom.swing.MigLayout;



public class KickerPlotPanel extends JPanel
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final long	serialVersionUID	= -2237149164069800940L;
																
	private final Chart2D		chart					= new Chart2D();
	private final ITrace2D		capTrace				= new Trace2DLtd(200);
	private final ITrace2D		chgTrace				= new Trace2DLtd(200);
																
	private long					timeOffset			= 0;
																
																
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public KickerPlotPanel(final double maxCap)
	{
		setLayout(new MigLayout("fill"));
		
		capTrace.setColor(Color.RED);
		capTrace.setName("Capacitor Level");
		chgTrace.setColor(Color.BLUE);
		chgTrace.setName("Charge Current");
		
		final AAxis<AxisScalePolicyAutomaticBestFit> currentAxis = new AxisLinear<AxisScalePolicyAutomaticBestFit>();
		currentAxis.setRange(new Range(0, 10.0));
		
		chart.getAxisY().setRangePolicy(new RangePolicyFixedViewport(new Range(0.0, maxCap)));
		chart.getAxisX().setRangePolicy(new RangePolicyHighestValues(10));
		chart.getAxisX().setMajorTickSpacing(10);
		chart.getAxisX().setMinorTickSpacing(10);
		chart.addAxisYRight(currentAxis);
		currentAxis.setRangePolicy(new RangePolicyFixedViewport(new Range(0.0, 10.0)));
		chart.setBackground(getBackground());
		chart.setForeground(Color.BLACK);
		chart.getAxisX().setAxisTitle(new AxisTitle("t [s]"));
		chart.getAxisY().setAxisTitle(new AxisTitle("U [V]"));
		
		chart.addTrace(capTrace);
		chart.addTrace(chgTrace, chart.getAxisX(), currentAxis);
		
		add(chart, "grow");
		
		timeOffset = System.nanoTime();
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public void addCapLevel(final double lvl)
	{
		capTrace.addPoint((System.nanoTime() - timeOffset) / 1000000000.0, lvl);
	}
	
	
	
	public void addChargeCurrent(final double cur)
	{
		chgTrace.addPoint((System.nanoTime() - timeOffset) / 1000000000.0, cur);
	}
}
