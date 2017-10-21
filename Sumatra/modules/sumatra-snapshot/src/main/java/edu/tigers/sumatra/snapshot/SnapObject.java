/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.snapshot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Vector2;



public class SnapObject
{
	private IVector2	pos	= new Vector2();
	private IVector2	vel	= new Vector2();
	
	
	
	public SnapObject(final IVector2 pos, final IVector2 vel)
	{
		this.pos = pos;
		this.vel = vel;
	}
	
	
	
	public final IVector2 getPos()
	{
		return pos;
	}
	
	
	
	public final void setPos(final IVector2 pos)
	{
		this.pos = pos;
	}
	
	
	
	public final IVector2 getVel()
	{
		return vel;
	}
	
	
	
	public final void setVel(final IVector2 vel)
	{
		this.vel = vel;
	}
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((pos == null) ? 0 : pos.hashCode());
		result = (prime * result) + ((vel == null) ? 0 : vel.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		SnapObject other = (SnapObject) obj;
		if (pos == null)
		{
			if (other.pos != null)
			{
				return false;
			}
		} else if (!pos.equals(other.pos))
		{
			return false;
		}
		if (vel == null)
		{
			if (other.vel != null)
			{
				return false;
			}
		} else if (!vel.equals(other.vel))
		{
			return false;
		}
		return true;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON()
	{
		JSONObject obj = new JSONObject();
		obj.put("pos", vec2ToJSON(pos));
		obj.put("vel", vec2ToJSON(vel));
		return obj;
	}
	
	
	
	public static SnapObject fromJSON(final JSONObject obj)
	{
		return new SnapObject(vec2FromJSON((JSONArray) obj.get("pos")), vec2FromJSON((JSONArray) obj.get("vel")));
	}
	
	
	
	@SuppressWarnings("unchecked")
	private static JSONArray vec2ToJSON(final IVector2 vec)
	{
		JSONArray array = new JSONArray();
		array.add(vec.x());
		array.add(vec.y());
		return array;
	}
	
	
	
	private static IVector2 vec2FromJSON(final JSONArray jsonArray)
	{
		return new Vector2((double) jsonArray.get(0), (double) jsonArray.get(1));
	}
	
}
