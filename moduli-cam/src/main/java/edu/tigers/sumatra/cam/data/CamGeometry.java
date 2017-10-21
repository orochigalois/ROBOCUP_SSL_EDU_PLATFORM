/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam.data;

import java.util.Map;



public class CamGeometry
{
	
	private final Map<Integer, CamCalibration>	calibrations;
	private final CamFieldSize							field;
	
	
	
	public CamGeometry(final Map<Integer, CamCalibration> calibrations, final CamFieldSize field)
	{
		super();
		this.calibrations = calibrations;
		this.field = field;
	}
	
	
	
	public final Map<Integer, CamCalibration> getCalibrations()
	{
		return calibrations;
	}
	
	
	
	public final CamFieldSize getField()
	{
		return field;
	}
}
