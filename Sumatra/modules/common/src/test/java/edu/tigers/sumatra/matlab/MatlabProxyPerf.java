/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.matlab;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;



@Ignore
public class MatlabProxyPerf
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(MatlabProxyPerf.class.getName());
	
	
	
	@Test
	public void testConnection()
	{
		try
		{
			MatlabProxy mp = MatlabConnection.getMatlabProxy();
			for (int i = 0; i < 100; i++)
			{
				long t0 = System.nanoTime();
				mp.eval("a=1");
				// IVectorN wheelSpeed = new VectorN(4);
				// StringBuilder sb = new StringBuilder();
				// sb.append("mm.getXywSpeed([");
				// sb.append(wheelSpeed.x());
				// sb.append(',');
				// sb.append(wheelSpeed.y());
				// sb.append(',');
				// sb.append(wheelSpeed.z());
				// sb.append(',');
				// sb.append(wheelSpeed.w());
				// sb.append("])");
				// MatlabConnection.getMatlabProxy().returningEval(sb.toString(), 1);
				long t1 = System.nanoTime();
				double t = (t1 - t0) / 1e9;
				System.out.println(t);
			}
		} catch (MatlabConnectionException err)
		{
			log.error("", err);
		} catch (MatlabInvocationException err)
		{
			log.error("", err);
		}
	}
}
