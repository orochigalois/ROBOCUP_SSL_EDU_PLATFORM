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

import edu.tigers.sumatra.MessagesRobocupSslGeometry.SSL_GeometryCameraCalibration;



public class CamCalibration
{
	private final int		cameraId;
	
	private final double	focalLength;
	
	private final double	principalPointX;
	private final double	principalPointY;
	
	private final double	distortion;
	
	private final double	q0;
	private final double	q1;
	private final double	q2;
	private final double	q3;
	
	private final double	tx;
	private final double	ty;
	private final double	tz;
	
	private final double	derivedCameraWorldTx;
	private final double	derivedCameraWorldTy;
	private final double	derivedCameraWorldTz;
	
	
	
	public CamCalibration(final int cameraId, final double focalLength, final double principalPointX,
			final double principalPointY,
			final double distortion, final double q0, final double q1, final double q2, final double q3, final double tx,
			final double ty, final double tz,
			final double derivedcameraWorldTx, final double derivedCameraWorldTy, final double derivedCameraWorldTz)
	{
		this.cameraId = cameraId;
		this.focalLength = focalLength;
		this.principalPointX = principalPointX;
		this.principalPointY = principalPointY;
		this.distortion = distortion;
		this.q0 = q0;
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.tx = tx;
		this.ty = ty;
		this.tz = tz;
		derivedCameraWorldTx = derivedcameraWorldTx;
		this.derivedCameraWorldTy = derivedCameraWorldTy;
		this.derivedCameraWorldTz = derivedCameraWorldTz;
	}
	
	
	
	public CamCalibration(final SSL_GeometryCameraCalibration cc)
	{
		cameraId = cc.getCameraId();
		focalLength = cc.getFocalLength();
		principalPointX = cc.getPrincipalPointX();
		principalPointY = cc.getPrincipalPointY();
		distortion = cc.getDistortion();
		q0 = cc.getQ0();
		q1 = cc.getQ1();
		q2 = cc.getQ2();
		q3 = cc.getQ3();
		tx = cc.getTx();
		ty = cc.getTy();
		tz = cc.getTz();
		derivedCameraWorldTx = cc.getDerivedCameraWorldTx();
		derivedCameraWorldTy = cc.getDerivedCameraWorldTy();
		derivedCameraWorldTz = cc.getDerivedCameraWorldTz();
	}
	
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SSLCameraCalibration [cameraId=");
		builder.append(getCameraId());
		builder.append(", focalLength=");
		builder.append(getFocalLength());
		builder.append(", principalPointX=");
		builder.append(getPrincipalPointX());
		builder.append(", principalPointY=");
		builder.append(getPrincipalPointY());
		builder.append(", distortion=");
		builder.append(getDistortion());
		builder.append(", q0=");
		builder.append(getQ0());
		builder.append(", q1=");
		builder.append(getQ1());
		builder.append(", q2=");
		builder.append(getQ2());
		builder.append(", q3=");
		builder.append(getQ3());
		builder.append(", tx=");
		builder.append(getTx());
		builder.append(", ty=");
		builder.append(getTy());
		builder.append(", tz=");
		builder.append(getTz());
		builder.append(", derivedCameraWorldTx=");
		builder.append(getDerivedCameraWorldTx());
		builder.append(", derivedCameraWorldTy=");
		builder.append(getDerivedCameraWorldTy());
		builder.append(", derivedCameraWorldTz=");
		builder.append(getDerivedCameraWorldTz());
		return builder.toString();
	}
	
	
	
	public int getCameraId()
	{
		return cameraId;
	}
	
	
	
	public double getFocalLength()
	{
		return focalLength;
	}
	
	
	
	public double getPrincipalPointX()
	{
		return principalPointX;
	}
	
	
	
	public double getPrincipalPointY()
	{
		return principalPointY;
	}
	
	
	
	public double getDistortion()
	{
		return distortion;
	}
	
	
	
	public double getQ0()
	{
		return q0;
	}
	
	
	
	public double getQ1()
	{
		return q1;
	}
	
	
	
	public double getQ2()
	{
		return q2;
	}
	
	
	
	public double getQ3()
	{
		return q3;
	}
	
	
	
	public double getTx()
	{
		return tx;
	}
	
	
	
	public double getTy()
	{
		return ty;
	}
	
	
	
	public double getTz()
	{
		return tz;
	}
	
	
	
	public double getDerivedCameraWorldTx()
	{
		return derivedCameraWorldTx;
	}
	
	
	
	public double getDerivedCameraWorldTy()
	{
		return derivedCameraWorldTy;
	}
	
	
	
	public double getDerivedCameraWorldTz()
	{
		return derivedCameraWorldTz;
	}
}
