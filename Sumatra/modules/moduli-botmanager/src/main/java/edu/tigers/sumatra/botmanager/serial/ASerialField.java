/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.botmanager.serial;

import java.lang.reflect.Field;

import edu.tigers.sumatra.botmanager.serial.SerialData.ESerialDataType;



public abstract class ASerialField
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	protected final Field				field;
	protected final ESerialDataType	type;
	protected final int					offset;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	protected ASerialField(final Field field, final ESerialDataType type, final int offset)
	{
		this.field = field;
		this.type = type;
		this.offset = offset;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public abstract void decode(byte[] data, Object obj) throws SerialException;
	
	
	
	public abstract void encode(byte[] data, Object obj) throws SerialException;
	
	
	
	public abstract int getLength(Object obj) throws SerialException;
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
