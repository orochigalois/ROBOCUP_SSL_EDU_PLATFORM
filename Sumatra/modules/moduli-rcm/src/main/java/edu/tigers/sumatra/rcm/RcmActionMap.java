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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.tigers.sumatra.rcm.RcmAction.EActionType;
import edu.x.framework.skillsystem.ESkill;
import net.java.games.input.Component;
import net.java.games.input.Controller;



public class RcmActionMap
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger							log				= Logger
																								.getLogger(RcmActionMap.class.getName());
	private static final String							CONFIG_DIR		= "config/rcm/";
	private static final String							CONFIG_ENDING	= ".rcc";
	private static final String							ENCODING			= "UTF-8";
																						
	private final List<RcmActionMapping>				actionMappings	= new ArrayList<RcmActionMapping>();
	private final Controller								controller;
	private String												configName		= "default";
																						
	private final Map<ERcmControllerConfig, Double>	configValues	= new LinkedHashMap<ERcmControllerConfig, Double>();
																						
																						
	
	public enum ERcmControllerConfig
	{
		
		DEADZONE,;
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public RcmActionMap(final Controller controller)
	{
		this.controller = controller;
		configValues.put(ERcmControllerConfig.DEADZONE, 0.0);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void addMapping(final List<ExtIdentifier> identifiers, final ESkill eSkill)
	{
		RcmAction action = new RcmAction(eSkill, EActionType.SKILL);
		RcmActionMapping mapping = new RcmActionMapping(identifiers, action);
		actionMappings.add(mapping);
	}
	
	
	
	public void addMapping(final List<ExtIdentifier> identifiers, final EControllerAction eAction)
	{
		RcmAction action = new RcmAction(eAction, EActionType.SIMPLE);
		RcmActionMapping mapping = new RcmActionMapping(identifiers, action);
		actionMappings.add(mapping);
	}
	
	
	
	public void addMapping(final RcmActionMapping mapping)
	{
		actionMappings.add(mapping);
	}
	
	
	
	public void removeMapping(final RcmActionMapping mapping)
	{
		actionMappings.remove(mapping);
	}
	
	
	
	public void save(final File file)
	{
		Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
		List<JSONObject> jsonArray = new ArrayList<JSONObject>(actionMappings.size());
		for (RcmActionMapping mapping : actionMappings)
		{
			jsonArray.add(mapping.toJSON());
		}
		for (Map.Entry<ERcmControllerConfig, Double> entry : configValues.entrySet())
		{
			jsonMap.put(entry.getKey().name(), entry.getValue());
		}
		jsonMap.put("mapping", jsonArray);
		
		String json = JSONValue.toJSONString(jsonMap);
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ENCODING));
			bw.write(json);
			
		} catch (IOException err)
		{
			log.error("Could not save config.", err);
		} finally
		{
			if (bw != null)
			{
				try
				{
					bw.close();
				} catch (IOException err)
				{
					log.error("Could not close config file " + file, err);
				}
			}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public boolean load(final File file)
	{
		JSONParser parser = new JSONParser();
		String json = "";
		try
		{
			json = new String(Files.readAllBytes(file.toPath()), ENCODING);
			Object obj = parser.parse(json);
			Map<String, Object> jsonMap = (Map<String, Object>) obj;
			
			List<JSONObject> jsonArray = (List<JSONObject>) jsonMap.get("mapping");
			actionMappings.clear();
			for (JSONObject jsonObj : jsonArray)
			{
				RcmActionMapping mapping = RcmActionMapping.fromJSON(jsonObj);
				actionMappings.add(mapping);
			}
			for (Map.Entry<ERcmControllerConfig, Double> entry : configValues.entrySet())
			{
				configValues.put(entry.getKey(), (Double) jsonMap.get(entry.getKey().name()));
			}
			configName = file.getName();
			log.info("Loaded " + file.getName());
		} catch (IOException err)
		{
			log.error("Could not load config from " + file, err);
			return false;
		} catch (ParseException err)
		{
			log.error("Could not parse json: " + json, err);
			return false;
		} catch (IllegalArgumentException err)
		{
			log.error("Error loading config", err);
			return false;
		}
		return true;
	}
	
	
	private File getDefaultFile(final Controller controller)
	{
		String ctrlName = controller.getName().replaceAll("[ /\\\\]", "_");
		File file = Paths.get(CONFIG_DIR, ctrlName + CONFIG_ENDING).toFile();
		return file;
	}
	
	
	
	public void loadDefault(final Controller controller)
	{
		File file = getDefaultFile(controller);
		if (file.exists() && load(file))
		{
			return;
		}
	}
	
	
	
	public void saveDefault(final Controller controller)
	{
		File file = getDefaultFile(controller);
		save(file);
	}
	
	
	
	public List<ExtComponent> createComponents()
	{
		List<ExtComponent> comps = new LinkedList<ExtComponent>();
		for (RcmActionMapping mapping : actionMappings)
		{
			ExtComponent component = null;
			for (ExtIdentifier extId : mapping.getIdentifiers())
			{
				for (Component comp : controller.getComponents())
				{
					ExtComponent newComp = null;
					if (extId.getIdentifier().equals(comp.getIdentifier().toString()))
					{
						if (comp.isAnalog())
						{
							double min = extId.getParams().getMinValue();
							double max = extId.getParams().getMaxValue();
							newComp = new ExtComponent(new DynamicAxis(comp, min, max), mapping.getAction());
						} else
						{
							double chargeTime = extId.getParams().getChargeTime();
							newComp = new ExtComponent(new ChargeButtonComponent(comp, chargeTime), mapping.getAction());
						}
					} else if (comp.getIdentifier().toString().equals("pov") && extId.getIdentifier().startsWith("pov"))
					{
						newComp = new ExtComponent(new POVToButton(comp, extId.getIdentifier()), mapping.getAction());
					}
					
					if (newComp != null)
					{
						if (component == null)
						{
							comps.add(newComp);
						} else
						{
							component.setDependentComp(newComp);
						}
						component = newComp;
						break;
					}
				}
			}
		}
		return comps;
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public String getConfigName()
	{
		return configName;
	}
	
	
	
	public void setConfigName(final String configName)
	{
		this.configName = configName;
	}
	
	
	
	public Controller getController()
	{
		return controller;
	}
	
	
	
	public final List<RcmActionMapping> getActionMappings()
	{
		return Collections.unmodifiableList(actionMappings);
	}
	
	
	
	public Map<ERcmControllerConfig, Double> getConfigValues()
	{
		return configValues;
	}
}
