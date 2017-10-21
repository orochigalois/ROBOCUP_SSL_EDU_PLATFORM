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

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import edu.tigers.sumatra.math.Vector2;





public class SnapObjectTest
{
	
	
	@Test
	public void testToJSON()
	{
		SnapObject obj = new SnapObject(new Vector2(), new Vector2());
		assertEquals("{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]}", obj.toJSON().toJSONString());
		obj = new SnapObject(new Vector2(0, 1), new Vector2());
		assertEquals("{\"pos\":[0.0,1.0],\"vel\":[0.0,0.0]}", obj.toJSON().toJSONString());
		obj = new SnapObject(new Vector2(0, 1), new Vector2(3, 4));
		assertEquals("{\"pos\":[0.0,1.0],\"vel\":[3.0,4.0]}", obj.toJSON().toJSONString());
	}
	
	
	
	@Test
	public void testFromJSON() throws ParseException
	{
		JSONParser parser = new JSONParser();
		SnapObject obj = new SnapObject(new Vector2(), new Vector2());
		assertEquals(obj,
				SnapObject.fromJSON((JSONObject) parser.parse("{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]}")));
		obj = new SnapObject(new Vector2(0, 1), new Vector2());
		assertEquals(obj,
				SnapObject.fromJSON((JSONObject) parser.parse("{\"pos\":[0.0,1.0],\"vel\":[0.0,0.0]}")));
		obj = new SnapObject(new Vector2(0, 1), new Vector2(3, 4));
		assertEquals(obj,
				SnapObject.fromJSON((JSONObject) parser.parse("{\"pos\":[0.0,1.0],\"vel\":[3.0,4.0]}")));
	}
	
}
