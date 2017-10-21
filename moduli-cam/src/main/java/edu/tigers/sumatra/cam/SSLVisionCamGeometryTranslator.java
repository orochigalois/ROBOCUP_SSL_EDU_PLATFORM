/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.cam;

import java.util.HashMap;
import java.util.Map;

import edu.tigers.sumatra.MessagesRobocupSslGeometry.SSL_GeometryData;
import edu.tigers.sumatra.cam.data.CamCalibration;
import edu.tigers.sumatra.cam.data.CamFieldSize;
import edu.tigers.sumatra.cam.data.CamGeometry;



public class SSLVisionCamGeometryTranslator
{
	
	public CamGeometry translate(final SSL_GeometryData geometryData)
	{
		Map<Integer, CamCalibration> calibrations = new HashMap<>();
		for (int i = 0; i < geometryData.getCalibCount(); i++)
		{
			CamCalibration calibration = new CamCalibration(geometryData.getCalib(i));
			calibrations.put(calibration.getCameraId(), calibration);
		}
		CamFieldSize fieldSize = new CamFieldSize(geometryData.getField());
		return new CamGeometry(calibrations, fieldSize);
	}
}
