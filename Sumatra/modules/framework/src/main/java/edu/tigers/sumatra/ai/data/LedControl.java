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


public class LedControl
{
	private boolean	leftGreen	= false;
	private boolean	leftRed		= false;
	private boolean	rightGreen	= false;
	private boolean	rightRed		= false;
	
	private boolean	insane		= false;
	
	
	
	public LedControl()
	{
		
	}
	
	
	
	public LedControl(final boolean lg, final boolean lr, final boolean rg, final boolean rr)
	{
		leftGreen = lg;
		leftRed = lr;
		rightGreen = rg;
		rightRed = rr;
	}
	
	
	
	public boolean isLeftGreen()
	{
		return leftGreen;
	}
	
	
	
	public boolean isLeftRed()
	{
		return leftRed;
	}
	
	
	
	public boolean isRightGreen()
	{
		return rightGreen;
	}
	
	
	
	public boolean isRightRed()
	{
		return rightRed;
	}
	
	
	
	public void setLeftGreen(final boolean leftGreen)
	{
		this.leftGreen = leftGreen;
	}
	
	
	
	public void setLeftRed(final boolean leftRed)
	{
		this.leftRed = leftRed;
	}
	
	
	
	public void setRightGreen(final boolean rightGreen)
	{
		this.rightGreen = rightGreen;
	}
	
	
	
	public void setRightRed(final boolean rightRed)
	{
		this.rightRed = rightRed;
	}
	
	
	
	public boolean isInsane()
	{
		return insane;
	}
	
	
	
	public void setInsane(final boolean insane)
	{
		this.insane = insane;
	}
}
