package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.BUILDINGTYPE;
import model.TERRAINTYPE;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agent.rule.BuildingTypeRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Requirement;
import agent.rule.Rule;
import agent.rule.TerrainTypeRequirement;

public class RuleHandler {
	// This class will consist of static methods, to load in JSON files containings rules.
	
	// Load in JSON file
	// Parse JSON to a set of rules with requirements
	
	// Forloop over regler
	// Forloop over requirements
	
	// Return those requirements
	
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
				Rule newRule = new Rule((Integer)jRule.get("RADIUS"), isAll ? CONSTRAINT.ALL : CONSTRAINT.ANY);
				
				JSONArray jRequirements = (JSONArray) jRule.get("REQUIREMENTS");
				
				for (Object _req : jRequirements) {
					JSONObject jReq = (JSONObject) _req;
					
					String reqType = (String)jReq.get("TYPE");
					int value = (Integer)jReq.get("VAL");
					boolean isUpperLimit = (boolean)jReq.get("UPPERLIMIT");
					
					switch (reqType) {
					case "BUILDING":
						BUILDINGTYPE buildingType = getBuildingType((String)jReq.get("TARGET"));
						newRule.addRequirement(new BuildingTypeRequirement(value, isUpperLimit, buildingType));
						break;
					case "TERRAIN":
						TERRAINTYPE terrainType = getTerrainType((String)jReq.get("TARGET"));
						newRule.addRequirement(new TerrainTypeRequirement(value, isUpperLimit, terrainType));
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
	
	private static BUILDINGTYPE getBuildingType(String val) {
		BUILDINGTYPE buildingType;
		switch (val) {
		case "STARTPOSITION":
			buildingType = BUILDINGTYPE.STARTPOSITION;
			break;
		case "NONE":
			buildingType = BUILDINGTYPE.NONE;
			break;
		case "HUT":
			buildingType = BUILDINGTYPE.HUT;
			break;
		case "MANSION":
			buildingType = BUILDINGTYPE.MANSION;
			break;
		default:
			buildingType = BUILDINGTYPE.NONE;
			break;
		} 
		
		return buildingType;
	}
	
	private static TERRAINTYPE getTerrainType(String val) {
		TERRAINTYPE terrainType;
		switch (val) {
		case "WATER":
			terrainType = TERRAINTYPE.WATER;
			break;
		case "FIELD":
			terrainType = TERRAINTYPE.FIELD;
			break;
		case "ROCK":
			terrainType = TERRAINTYPE.ROCK;
			break;
		default:
			terrainType = TERRAINTYPE.OUTERSPACE;
			break;
		} 
		return terrainType;
	}
}
