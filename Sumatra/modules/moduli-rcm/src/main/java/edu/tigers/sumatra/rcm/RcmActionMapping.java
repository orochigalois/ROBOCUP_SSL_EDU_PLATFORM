/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.rcm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;



public class RcmActionMapping
{
	private final List<ExtIdentifier>	identifiers;
	private RcmAction							action;
	
	
	
	public RcmActionMapping(final List<ExtIdentifier> identifiers, final RcmAction action)
	{
		this.identifiers = identifiers;
		this.action = action;
	}
	
	
	
	public final List<ExtIdentifier> getIdentifiers()
	{
		return identifiers;
	}
	
	
	
	public final RcmAction getAction()
	{
		return action;
	}
	
	
	
	public final void setAction(final RcmAction action)
	{
		this.action = action;
	}
	
	
	@Override
	public String toString()
	{
		return "RcmActionMapping [identifiers=" + identifiers + ", action=" + action + "]";
	}
	
	
	
	public JSONObject toJSON()
	{
		Map<String, Object> jsonMapping = new LinkedHashMap<String, Object>();
		jsonMapping.put("action", action.toJSON());
		List<String> jsonIds = new ArrayList<String>(identifiers.size());
		for (ExtIdentifier extId : identifiers)
		{
			jsonIds.add(extId.getExtIdentifier());
		}
		jsonMapping.put("identifiers", jsonIds);
		return new JSONObject(jsonMapping);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static RcmActionMapping fromJSON(final JSONObject jsonObj)
	{
		Map<String, Object> jsonMapping = jsonObj;
		JSONObject jsonAction = (JSONObject) jsonMapping.get("action");
		RcmAction action = RcmAction.fromJSON(jsonAction);
		List<String> jsonIdentifiers = (List<String>) jsonMapping.get("identifiers");
		List<ExtIdentifier> extIds = new ArrayList<ExtIdentifier>(jsonIdentifiers.size());
		for (String strId : jsonIdentifiers)
		{
			extIds.add(ExtIdentifier.valueOf(strId));
		}
		return new RcmActionMapping(extIds, action);
	}
}