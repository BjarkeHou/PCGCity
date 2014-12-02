package controller;

import gui.AppWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.*;
import util.MapHandler;
import util.Point2i;
import util.Rand;
import util.RuleHandler;
import agent.Agent;
import agent.HouseAgent;
import agent.HutAgent;
import agent.rule.AgentBirth;

public class Controller {
	private ArrayList<Agent> agents;
	private HashMap<BUILDING, AgentBirth> birthParameters;
	private Map map;
	private ArrayList<Point2i> buildingList;
	private AppWindow gui;
	
	private int totalTimeSteps = 300;
	private int currentTimeStep = 0;
	
	//private int happyTimeChance = 10;
	private int happyTimeRate = 10;
	private int deathRate = 5;
	
	public Controller() {
		agents = new ArrayList<Agent>();
		buildingList = new ArrayList<Point2i>();
		
		CreateBirthParameters();

		gui = new AppWindow(this);
		gui.frame.setVisible(true);
	}

	public void doRestOfTimeSteps() {
		while(currentTimeStep < totalTimeSteps) {
			doOneTimeStep();
		}
	}
	
	public void doOneTimeStep() {
		
		
		timeStep(currentTimeStep);
		
		if(currentTimeStep%happyTimeRate == 0){
			performHappyTime();
		}
	}

	private void timeStep(int timestep) {
		ArrayList<Agent> agentsToRemove = new ArrayList<Agent>();
		
		for (Agent agent : agents) {
			//Building
			if(agent.testCurrentField()) {
				map.changeBuildingOnField(agent.getPos(), agent.getBuilder(), timestep);
				if(!buildingList.contains(agent.getPos()))
					buildingList.add(agent.getPos());
			}
			//Moving
			agent.move(currentTimeStep);
			//Retirement
			if(agent.getInefficiencyCounter() > deathRate) agentsToRemove.add(agent);
			
		}
		//Retire old agents
		for(Agent a : agentsToRemove) agents.remove(a);
		gui.setAmountOfAgentsLbl(agents.size());
		
		//Update map
		MapHandler.writeMapToFile(map, currentTimeStep, agents);
		currentTimeStep++;
		gui.setCurrentTimeStep(currentTimeStep);
		gui.updateProgressBar(currentTimeStep);
		gui.setCurrentMap(MapHandler.convertMapToImage(map, agents));
	}
	
	private void performHappyTime() {
		HashMap<BUILDING, Integer> buildingCount = new HashMap<BUILDING, Integer>();
		for (Point2i point : buildingList) {
			BUILDING b = map.getField(point).building;
			if(!buildingCount.containsKey(b)){
				buildingCount.put(b, 0);
			}
			buildingCount.put(b, buildingCount.get(b)+1);
		}
		//spawn for each building
		for (Point2i point : buildingList){
			double happyRate = birthParameters.get(map.getField(point).building).happy(buildingCount);
			if(happyRate > Rand.GetDouble()){
				addNewAgent(point, map.getField(point).building);
			}
		}
		//spawn for start position
		for (BUILDING b : birthParameters.keySet()){
			double happyRate = birthParameters.get(b).happy(buildingCount);
			if(happyRate > Rand.GetDouble() || (happyRate > 0.0 && (!buildingCount.containsKey(b)))){
				addNewAgent(map.getStartPos(), b);
			}
		}
		
		gui.setAmountOfAgentsLbl(agents.size());
	}
	
	public void addNewAgent() {
		if(map == null)
			return;
					
		agents.add(new HutAgent(map.getStartPos(), map));
		gui.setAmountOfAgentsLbl(agents.size());
	}
	
	public void addNewAgent(Point2i pos, BUILDING type) {
		if(map == null)
			return;
		
		switch (type) {
		case HUT:
			agents.add(new HutAgent(pos, map));
			break;
		case HOUSE:
			agents.add(new HouseAgent(pos, map));
		default:
			break;
		}
		
		gui.setAmountOfAgentsLbl(agents.size());
	}
	
	private void CreateBirthParameters() {
		birthParameters = new HashMap<BUILDING, AgentBirth>();
		birthParameters.put(BUILDING.HUT, new AgentBirth(0.15));
		AgentBirth ab1 = new AgentBirth(0.02);
		ab1.addRestriction(BUILDING.HUT, 20);
		birthParameters.put(BUILDING.HOUSE, ab1);
		
	}
	
	public void loadRuleOnPath(String pathToFile) {
		try {
			RuleHandler.loadRulesFromFile(pathToFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadMapOnPath(String pathToFile) {
		try {
			map = MapHandler.loadMap(pathToFile);
			gui.setInitialMap(MapHandler.convertMapToImage(map, agents));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getTotalTimeStep() {
		return totalTimeSteps;
	}
}
