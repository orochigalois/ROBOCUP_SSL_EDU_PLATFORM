/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.filter.ukf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Jama.CholeskyDecomposition;
import Jama.Matrix;
import edu.tigers.sumatra.math.AngleMath;



public abstract class AUnscentedKalmanFilter
{
	
	private double						c;
	
	private final int					numStates;
	private final int					numMeas;
	private final int					numSigma;
	
	
	private final Matrix				Wm;
	
	
	private final Matrix				Wc;
	
	
	private final Matrix				Rv;
	
	
	private final Matrix				Rn;
	
	
	private final Matrix				Px;
	
	
	private final Matrix				Py;
	
	
	private final Matrix				Pxy;
	
	
	private final Matrix				x;
	
	
	private final Matrix				u;
	
	
	private final Matrix				yk;
	
	
	private final Matrix				Xk;
	
	
	private final Matrix				Yk;
	
	protected final List<Integer>	orientationComponentsState	= new ArrayList<Integer>();
	protected final List<Integer>	orientationComponentsMeas	= new ArrayList<Integer>();
	
	
	
	public AUnscentedKalmanFilter(final int numStates, final int numMeas, final int numControl)
	{
		this.numStates = numStates;
		this.numMeas = numMeas;
		
		numSigma = (2 * numStates) + 1;
		
		Wm = new Matrix(1, numSigma);
		Wc = new Matrix(1, numSigma);
		
		Rv = Matrix.identity(numStates, numStates);
		Rn = Matrix.identity(numMeas, numMeas);
		Px = Matrix.identity(numStates, numStates);
		
		Py = new Matrix(numMeas, numMeas);
		Pxy = new Matrix(numStates, numMeas);
		
		x = new Matrix(1, numStates);
		u = new Matrix(1, numControl);
		yk = new Matrix(1, numMeas);
		
		Xk = new Matrix(numSigma, numStates);
		Yk = new Matrix(numSigma, numMeas);
		
		configure(0.01, 2.0, 0.0);
	}
	
	
	
	public void configure(final double alpha, final double beta, final double ki)
	{
		double lambda = (alpha * alpha * (numStates + ki)) - numStates;
		double c = numStates + lambda;
		this.c = Math.sqrt(c);
		
		Wm.set(0, 0, lambda / c);
		Wc.set(0, 0, (lambda / c) + ((1 - (alpha * alpha)) + beta));
		
		for (int i = 1; i < numSigma; i++)
		{
			Wm.set(0, i, 0.5 / c);
			Wc.set(0, i, 0.5 / c);
		}
	}
	
	
	
	private void drawSigmaPoints()
	{
		CholeskyDecomposition decomp = Px.chol();
		// Note: The following assert is commented out on purpose. It may occasionally fail
		// because the matrix Px is not symmetric due to numerical instabilities, but it is always positive semi-definite
		// assert (decomp.isSPD());
		
		// get cholesky
		double[][] chol = decomp.getL().getArray();
		
		double[][] XkA = Xk.getArray();
		double[] xA = x.getArray()[0];
		
		// set Xk - first row
		for (int c = 0; c < numStates; c++)
		{
			XkA[0][c] = xA[c];
		}
		
		// set Xk - other rows
		for (int r = 0; r < numStates; r++)
		{
			for (int c = 0; c < numStates; c++)
			{
				XkA[r + 1][c] = xA[c] + (this.c * chol[c][r]);
				XkA[r + numStates + 1][c] = xA[c] - (this.c * chol[c][r]);
			}
		}
	}
	
	
	
	public double[] predict()
	{
		drawSigmaPoints();
		double[][] XkA = Xk.getArray();
		
		// now call state function with sigma points
		double[] control = u.getArray()[0];
		
		for (int i = 0; i < numSigma; i++)
		{
			stateFunction(XkA[i], control);
		}
		
		// calculate mean
		double[] xA = x.getArray()[0];
		double[] WmA = Wm.getArray()[0];
		Arrays.fill(xA, 0);
		for (int i = 0; i < numSigma; i++)
		{
			for (int c = 0; c < numStates; c++)
			{
				xA[c] += WmA[i] * XkA[i][c];
			}
		}
		
		// calculate covariance
		Px.timesEquals(0);
		double[][] PxA = Px.getArray();
		double[] WcA = Wc.getArray()[0];
		for (int i = 0; i < numSigma; i++)
		{
			double[] XiA = XkA[i];
			
			for (int r = 0; r < numStates; r++)
			{
				for (int c = 0; c < numStates; c++)
				{
					PxA[r][c] += WcA[i] * (XiA[r] - xA[r]) * (XiA[c] - xA[c]);
				}
			}
		}
		
		Px.plusEquals(Rv);
		
		for (Integer i : orientationComponentsState)
		{
			xA[i] = AngleMath.normalizeAngle(xA[i]);
		}
		
		return Arrays.copyOf(xA, xA.length);
	}
	
	
	
	public double[] update(final double[] measurement)
	{
		drawSigmaPoints();
		double[][] XkA = Xk.getArray();
		double[][] YkA = Yk.getArray();
		double[] control = u.getArray()[0];
		
		// call measurement function with sigma points
		for (int i = 0; i < numSigma; i++)
		{
			measurementFunction(XkA[i], control, YkA[i]);
		}
		
		// calculate mean
		double[] ykA = yk.getArray()[0];
		double[] WmA = Wm.getArray()[0];
		Arrays.fill(ykA, 0);
		for (int i = 0; i < numSigma; i++)
		{
			for (int c = 0; c < numMeas; c++)
			{
				ykA[c] += WmA[i] * YkA[i][c];
			}
		}
		
		// calculate covariance
		Py.timesEquals(0);
		double[][] PyA = Py.getArray();
		double[] WcA = Wc.getArray()[0];
		for (int i = 0; i < numSigma; i++)
		{
			double[] YiA = YkA[i];
			
			for (int r = 0; r < numMeas; r++)
			{
				for (int c = 0; c < numMeas; c++)
				{
					PyA[r][c] += WcA[i] * (YiA[r] - ykA[r]) * (YiA[c] - ykA[c]);
				}
			}
		}
		
		Py.plusEquals(Rn);
		
		// calculate cross-covariance
		Pxy.timesEquals(0);
		double[][] PxyA = Pxy.getArray();
		double[] xA = x.getArray()[0];
		for (int i = 0; i < numSigma; i++)
		{
			double[] XiA = XkA[i];
			double[] YiA = YkA[i];
			
			for (int r = 0; r < numStates; r++)
			{
				for (int c = 0; c < numMeas; c++)
				{
					PxyA[r][c] += WcA[i] * (XiA[r] - xA[r]) * (YiA[c] - ykA[c]);
				}
			}
		}
		
		// calculate Kalman gain
		Matrix K = Pxy.times(Py.inverse());
		
		// calculate new estimate
		Matrix y = new Matrix(measurement, numMeas);
		
		Matrix change = y.minus(yk.transpose());
		double[][] changeA = change.getArray();
		for (Integer i : orientationComponentsMeas)
		{
			changeA[i][0] = AngleMath.normalizeAngle(changeA[i][0]);
		}
		
		x.plusEquals(K.times(change).transpose());
		
		for (Integer i : orientationComponentsState)
		{
			xA[i] = AngleMath.normalizeAngle(xA[i]);
		}
		
		// calculate new state covariance
		Px.minusEquals(K.times(Pxy.transpose()));
		
		return Arrays.copyOf(xA, xA.length);
	}
	
	
	
	public void setProcessNoise(final int index, final double value)
	{
		Rv.set(index, index, value);
		Px.set(index, index, value);
	}
	
	
	
	public void setProcessNoise(final double... values)
	{
		for (int i = 0; i < numStates; i++)
		{
			Rv.set(i, i, values[i]);
			Px.set(i, i, values[i]);
		}
	}
	
	
	
	public void setMeasurementNoise(final int index, final double value)
	{
		Rn.set(index, index, value);
	}
	
	
	
	public void setMeasurementNoise(final double... values)
	{
		for (int i = 0; i < numMeas; i++)
		{
			Rn.set(i, i, values[i]);
		}
	}
	
	
	
	public void setState(final double... values)
	{
		for (int i = 0; i < numStates; i++)
		{
			x.set(0, i, values[i]);
		}
	}
	
	
	
	public void setControl(final double... values)
	{
		double[] control = u.getArray()[0];
		System.arraycopy(values, 0, control, 0, values.length);
	}
	
	
	
	public void addOrientationComponent(final int stateIndex, final int measIndex)
	{
		orientationComponentsState.add(stateIndex);
		orientationComponentsMeas.add(measIndex);
	}
	
	
	
	public double[] getState()
	{
		double[] xA = x.getArray()[0];
		
		return Arrays.copyOf(xA, xA.length);
	}
	
	
	
	protected abstract void stateFunction(double[] stateInOut, double[] controlIn);
	
	
	
	protected abstract void measurementFunction(double[] stateIn, double[] controlIn, double[] measOut);
}
