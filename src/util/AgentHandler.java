package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import model.BUILDING;
import model.Map;
import model.TERRAIN;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agent.Agent;
import agent.rule.AgentBirth;
import agent.rule.BuildingRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Restriction;
import agent.rule.Rule;
import agent.rule.TerrainRequirement;

public class AgentHandler {

	private HashMap<BUILDING, Agent> agentTypes = new HashMap<BUILDING, Agent>();
	
	public void loadAgentFromFile(String pathToFile) throws IOException {
		
		// Load agent from file and put it into agentTypes
		// Implement getAgentOfType(type, startPos)
		
		if(pathToFile.isEmpty())
			return;
		
		JSONParser jParser = new JSONParser();
		
		try {
			JSONObject jObj = (JSONObject) jParser.parse(new FileReader(pathToFile));
			
			// Get building type
			BUILDING type = getBuilding((String) jObj.get("BUILDS"));
			
			// Get retirement
			long retirement = (long)jObj.get("RETIRE");
			
			// Get birth restrictions
			double birthRate = (double) jObj.get("BIRTH");
			ArrayList<Restriction> restrictions = extractRestrictions((JSONArray)jObj.get("RESTRICTIONS"));
			AgentBirth agentBirth = new AgentBirth(birthRate);
			for(Restriction r : restrictions) agentBirth.addRestriction(r);
			
			// Get rules
			ArrayList<Rule> rules = extractRules((JSONArray) jObj.get("RULES")); 
			Agent a = new Agent(type, agentBirth, (int)retirement);
			for(Rule r : rules) a.addRule(r);
			agentTypes.put(type, a);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Agent getAgentOfType(BUILDING type, Map m, Point2i startPos) {
		if(agentTypes.containsKey(type)) {
			Agent a = agentTypes.get(type).clone();
			a.supplyMap(m);
			a.supplyStartPos(startPos);
			return a;
		} else { 
			return null;
		}
	}
	
	public AgentBirth getAgentBirth(BUILDING b){
		return agentTypes.get(b).getAgentBirth();
	}
	
	public Set<BUILDING> getAllAgentTypes(){
		return agentTypes.keySet();
	}
	
	private ArrayList<Restriction> extractRestrictions(JSONArray jRestrictions) {
		ArrayList<Restriction> restrictions = new ArrayList<Restriction>();
		for (Object _restriction : jRestrictions) {
			JSONObject jRestriction = (JSONObject)_restriction;
			long amount = (long)jRestriction.get("AMOUNT");
			Restriction r = new Restriction(getBuilding((String)jRestriction.get("BUILDING")), (int)amount);
			restrictions.add(r);
		}
		
		return restrictions;
	}

	private ArrayList<Rule> extractRules(JSONArray jRules) {
		ArrayList<Rule> rules = new ArrayList<Rule>();
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
			
			rules.add(newRule);
		}
		return rules;
	}

	private BUILDING getBuilding(String val) {
		BUILDING type = null;
		
		for(BUILDING building : BUILDING.values()) {
			if(val.equalsIgnoreCase(building.name()))
				type = building;
		}
		
		return type != null ? type : BUILDING.NONE;
	}
	
	private TERRAIN getTerrain(String val) {
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
