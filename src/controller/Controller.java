package controller;

import gui.AppWindow;

import java.io.IOException;
import java.util.ArrayList;

import model.Map;
import util.MapHandler;
import util.Point2i;
import util.RuleHandler;
import agent.Agent;
import agent.HutAgent;

public class Controller {
	private ArrayList<Agent> agents;
	private Map map;
	private AppWindow gui;
	
	private int totalTimeSteps = 200;
	private int currentTimeStep = 0;
	
	public Controller() {
		agents = new ArrayList<Agent>();

		gui = new AppWindow(this);
		gui.frame.setVisible(true);
	}
	
	public void doRestOfTimeSteps() {
		for (int timeStep = currentTimeStep; timeStep < totalTimeSteps; timeStep++) {
			timeStep(timeStep);
		}
	}
	
	public void doOneTimeStep() {
		if(currentTimeStep == totalTimeSteps)
			return;
		
		timeStep(currentTimeStep);
	}

	private void timeStep(int timestep) {
		ArrayList<Agent> agentsToRemove = new ArrayList<Agent>();
		for (Agent agent : agents) {
			//Building
			if(agent.testCurrentField()) {
				map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), timestep);
			}
			//Moving
			agent.move(currentTimeStep);
			//Retirement
			if(agent.RetirementAge(timestep)) agentsToRemove.add(agent);
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
	
	public void addNewAgent() {
		if(map == null)
			return;
					
		agents.add(new HutAgent(map.getStartPos(), map, currentTimeStep,20));
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
