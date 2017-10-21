/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoref.view.generic;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyFixedViewport;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.painters.TracePainterDisc;
import info.monitorenter.gui.chart.traces.painters.TracePainterLine;
import info.monitorenter.util.Range;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;



public class FixedTimeRangeChartPanel extends JPanel
{
	
	private static final long				serialVersionUID	= -5176647826548801416L;
	
	private long								timeRange;
	
	private Chart2D							mainChart			= new Chart2D();
	private Trace2DLtd						mainTrace			= new Trace2DLtd();
	private Map<String, Trace2DSimple>	horizontalLines	= new HashMap<>();
	
	private boolean							highlightHead		= true;
	private Trace2DLtd						headTrace			= new Trace2DLtd(1);
	
	
	
	public FixedTimeRangeChartPanel(final long timeRange, final boolean highlightHead)
	{
		this.highlightHead = highlightHead;
		
		setLayout(new BorderLayout());
		add(mainChart, BorderLayout.CENTER);
		
		setupChart();
		
		setRange(timeRange);
	}
	
	
	private void setupChart()
	{
		IAxis<?> xAxis = mainChart.getAxisX();
		IAxis<?> yAxis = mainChart.getAxisY();
		
		xAxis.setPaintGrid(true);
		xAxis.setRangePolicy(new RangePolicyFixedViewport());
		yAxis.setPaintGrid(true);
		
		
		mainTrace.setTracePainter(new NoCarriageReturnLinePainter());
		mainTrace.setName(null);
		mainChart.addTrace(mainTrace);
		
		headTrace.setName(null);
		headTrace.setTracePainter(new TracePainterDisc(12));
		mainChart.addTrace(headTrace);
		
		mainChart.setGridColor(Color.LIGHT_GRAY);
	}
	
	
	
	public void addPoint(final long timestamp, final double y)
	{
		double x = (timestamp % timeRange) / 1e9;
		mainTrace.addPoint(x, y);
		
		if (highlightHead)
		{
			headTrace.addPoint(x, y);
		}
	}
	
	
	
	public void clipY(final double min, final double max)
	{
		mainChart.getAxisY().setRangePolicy(new RangePolicyFixedViewport(new Range(min, max)));
	}
	
	
	
	public void setColor(final Color color)
	{
		mainTrace.setColor(color);
		headTrace.setColor(color);
	}
	
	
	
	public void setXTitle(final String title)
	{
		mainChart.getAxisX().getAxisTitle().setTitle(title);
	}
	
	
	
	public void setYTitle(final String title)
	{
		mainChart.getAxisY().getAxisTitle().setTitle(title);
	}
	
	
	
	public int getPointBufferSize()
	{
		return mainTrace.getMaxSize();
	}
	
	
	
	public void setRange(final long range_ns)
	{
		timeRange = range_ns;
		mainChart.getAxisX().setRange(new Range(0, timeRange / 1e9));
	}
	
	
	
	public void setPointBufferSize(final int bufSize)
	{
		mainTrace.setMaxSize(bufSize);
	}
	
	
	
	public void setHighlightHead(final boolean val)
	{
		highlightHead = val;
		if (!highlightHead)
		{
			headTrace.removeAllPoints();
		}
	}
	
	
	
	public void setHorizontalLine(final String name, final Color color, final double yValue)
	{
		if (!horizontalLines.containsKey(name))
		{
			Trace2DSimple trace = new Trace2DSimple(null);
			mainChart.addTrace(trace);
			
			trace.setZIndex(mainTrace.getZIndex() - 1);
			horizontalLines.put(name, trace);
		}
		Trace2DSimple trace = horizontalLines.get(name);
		
		trace.setColor(color);
		trace.removeAllPoints();
		trace.addPoint(Double.MIN_VALUE, yValue);
		trace.addPoint(Double.MAX_VALUE, yValue);
	}
	
	
	
	public void setPointBufferSizeWithPeriod(final long updatePeriod)
	{
		if (updatePeriod <= 0)
		{
			return;
		}
		
		int requiredSize = (int) (((timeRange * 90) / 100) / updatePeriod);
		setPointBufferSize(requiredSize);
	}
	
	
	
	public void clear()
	{
		mainTrace.removeAllPoints();
	}
	
	
	private class NoCarriageReturnLinePainter extends TracePainterLine
	{
		
		
		private static final long	serialVersionUID	= 672321723106037578L;
		
		
		@Override
		public void paintPoint(final int absoluteX, final int absoluteY, final int nextX, final int nextY,
				final Graphics g, final ITracePoint2D original)
		{
			if (Math.abs((nextX - absoluteX)) < 10)
			{
				super.paintPoint(absoluteX, absoluteY, nextX, nextY, g, original);
			}
		}
		
	}
	
	
}
