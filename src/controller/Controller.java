package controller;

import gui.AppWindow;

import java.io.IOException;
import java.util.ArrayList;

import model.BUILDINGTYPE;
import model.Map;
import util.MapHandler;
import util.Point2i;
import util.Rand;
import util.RuleHandler;
import agent.Agent;
import agent.HutAgent;

public class Controller {
	private ArrayList<Agent> agents;
	private Map map;
	private ArrayList<Point2i> buildingList;
	private AppWindow gui;
	
	private int totalTimeSteps = 200;
	private int currentTimeStep = 0;
	
	private int happyTimeChance = 0;
	private int happyTimeRate = 10;
	private int deathRate = 10;
	
	public Controller() {
		agents = new ArrayList<Agent>();
		buildingList = new ArrayList<Point2i>();

		gui = new AppWindow(this);
		gui.frame.setVisible(true);
	}
	
	public void doRestOfTimeSteps() {
		while(currentTimeStep < totalTimeSteps) {
			performTimeStep();
			
			if(currentTimeStep%happyTimeRate == 0){
				performHappyTime();
		
		}
	}
	
	public void doOneTimeStep() {
		if(currentTimeStep == totalTimeSteps)
			return;
		
		performTimeStep();
		
		if(currentTimeStep%happyTimeRate == 0){
			performHappyTime();
		}
		timeStep(currentTimeStep);
	}

	private void timeStep(int timestep) {
		ArrayList<Agent> agentsToRemove = new ArrayList<Agent>();
	}

	private void performTimeStep() {
		for (Agent agent : agents) {
			//Building
			if(agent.testCurrentField()) {
				map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), timestep);
				if(!buildingList.contains(agent.getPos()))
					buildingList.add(agent.getPos());
			}
			//Moving
			agent.move(currentTimeStep);
			//Retirement
			if(agent.RetirementAge(timestep)) agentsToRemove.add(agent);
			
			if(agent.getInefficiencyCounter() > deathRate)
				removeAgent(agent);
			
		}
		//Retire old agents
		for(Agent a : agentsToRemove) agents.remove(a);
		//Birth new agents
		//TODO
		//Go through each building on the map
		
		//For each building, roll to see if a new agent is birthed
		//If it is, create it and add it to the agents list
		//For start position, determine if a new agent type can be spawned
		//If it can, spawn one
		
		//Update map
		MapHandler.writeMapToFile(map, currentTimeStep, agents);
		currentTimeStep++;
		gui.setCurrentTimeStep(currentTimeStep);
		gui.updateProgressBar(currentTimeStep);
		gui.setCurrentMap(MapHandler.convertMapToImage(map, agents));
	}
	
	private void performHappyTime() {
		for (Point2i point : buildingList) {
			// For each building on the map, test if theyve had happy time.
			if(Rand.GetInt(100) <= happyTimeChance) {
				addNewAgent(point, map.getField(point).buildingType);
			}
		}
	}
	
	public void addNewAgent() {
		if(map == null)
			return;
					
		agents.add(new HutAgent(map.getStartPos(), map, currentTimeStep,20));
		gui.setAmountOfAgentsLbl(agents.size());
	}
	
	public void addNewAgent(Point2i pos, BUILDINGTYPE type) {
		if(map == null)
			return;
		
		switch (type) {
		case HUT:
			agents.add(new HutAgent(pos, map));
			break;
		case MANSION:
			break;
		default:
			break;
		}
		
		gui.setAmountOfAgentsLbl(agents.size());
	}
	
	public void removeAgent(Agent agent) {
		agents.remove(agent);
		gui.setAmountOfAgentsLbl(agents.size());
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
