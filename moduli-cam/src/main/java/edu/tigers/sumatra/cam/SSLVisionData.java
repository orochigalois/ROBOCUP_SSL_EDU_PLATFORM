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

import java.io.Serializable;
import java.util.Arrays;



public class SSLVisionData implements Serializable
{
	private static final long	serialVersionUID	= -1885492267591611080L;
	
	private final long			timestamp;
	private final byte[]			data;
	
	
	
	public SSLVisionData(final long timestamp, final byte[] data)
	{
		this.timestamp = timestamp;
		this.data = Arrays.copyOf(data, data.length);
	}
	
	
	
	public byte[] getData()
	{
		return Arrays.copyOf(data, data.length);
	}
	
	
	
	public long getTimestamp()
	{
		return timestamp;
	}
}
