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

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;
import edu.tigers.sumatra.math.Vector2;



public class SnapshotTest
{
	
	
	@Test
	public void testToJSON()
	{
		Snapshot snapshot = new Snapshot(new HashMap<>(), new SnapObject(new Vector2(), new Vector2()));
		assertEquals("{\"ball\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"bots\":[]}",
				snapshot.toJSON().toJSONString());
		
		Map<BotID, SnapObject> bots = new HashMap<>();
		bots.put(BotID.createBotId(0, ETeamColor.BLUE), new SnapObject(new Vector2(), new Vector2()));
		snapshot = new Snapshot(bots, new SnapObject(new Vector2(), new Vector2()));
		assertEquals(
				"{\"ball\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"bots\":[{\"obj\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"id\":{\"number\":0,\"color\":\"BLUE\"}}]}",
				snapshot.toJSON().toJSONString());
	}
	
	
	
	@Test
	public void testFromJSON() throws ParseException
	{
		JSONParser parser = new JSONParser();
		Snapshot snapshot = new Snapshot(new HashMap<>(), new SnapObject(new Vector2(), new Vector2()));
		assertEquals(snapshot,
				Snapshot.fromJSON(
						(JSONObject) parser.parse("{\"ball\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"bots\":[]}")));
		
		Map<BotID, SnapObject> bots = new HashMap<>();
		bots.put(BotID.createBotId(0, ETeamColor.BLUE), new SnapObject(new Vector2(), new Vector2()));
		snapshot = new Snapshot(bots, new SnapObject(new Vector2(), new Vector2()));
		assertEquals(snapshot,
				Snapshot.fromJSON(
						(JSONObject) parser.parse(
								"{\"ball\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"bots\":[{\"obj\":{\"pos\":[0.0,0.0],\"vel\":[0.0,0.0]},\"id\":{\"number\":0,\"color\":\"BLUE\"}}]}")));
	}
	
}
