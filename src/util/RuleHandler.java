package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agent.rule.BuildingRequirement;
import agent.rule.TerrainRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Rule;

public class RuleHandler {
	
	public static ArrayList<Rule> loadRulesFromFile(String pathToFile) throws IOException {
		if(pathToFile.isEmpty())
			return null;
		
		
		JSONParser jParser = new JSONParser();
		ArrayList<Rule> returnArray = new ArrayList<Rule>();
		
		try {
			JSONObject jObj = (JSONObject) jParser.parse(new FileReader(pathToFile));
			JSONArray jRules = (JSONArray) jObj.get("RULES");
			
			for (Object _rule : jRules) {
				JSONObject jRule = (JSONObject) _rule;
				
				String constraint = (String)jRule.get("CONSTRAINT");
				boolean isAll = constraint.equals("ALL");
				long radius = (long)jRule.get("RADIUS");
				Rule newRule = new Rule((int)radius, isAll ? CONSTRAINT.ALL : CONSTRAINT.ANY);
				
				JSONArray jRequirements = (JSONArray) jRule.get("REQUIREMENTS");
				
				for (Object _req : jRequirements) {
					JSONObject jReq = (JSONObject) _req;
					
					String reqType = (String)jReq.get("TYPE");
					long value = (long)jReq.get("VAL");
					boolean isUpperLimit = (boolean)jReq.get("UPPERLIMIT");
					
					switch (reqType) {
					case "BUILDING":
						BUILDING buildingType = getBuilding((String)jReq.get("TARGET"));
						newRule.addRequirement(new BuildingRequirement((int)value, isUpperLimit, buildingType));
						break;
					case "TERRAIN":
						TERRAIN terrainType = getTerrain((String)jReq.get("TARGET"));
						newRule.addRequirement(new TerrainRequirement((int)value, isUpperLimit, terrainType));
						break;
					default:
						break;
					}
				}
				
				returnArray.add(newRule);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnArray;
	}

	private static BUILDING getBuilding(String val) {
		BUILDING building;
		switch (val) {
		case "STARTPOSITION":
			building = BUILDING.STARTPOSITION;
			break;
		case "NONE":
			building = BUILDING.NONE;
			break;
		case "HUT":
			building = BUILDING.HUT;
			break;
		case "MANSION":
			building = BUILDING.MANSION;
			break;
		default:
			building = BUILDING.NONE;
			break;
		} 
		
		return building;
	}
	
	private static TERRAIN getTerrain(String val) {
		TERRAIN terrain;
		switch (val) {
		case "WATER":
			terrain = TERRAIN.WATER;
			break;
		case "FIELD":
			terrain = TERRAIN.FIELD;
			break;
		case "ROCK":
			terrain = TERRAIN.ROCK;
			break;
		default:
			terrain = TERRAIN.OUTERSPACE;
			break;
		} 
		return terrain;
	}
}
