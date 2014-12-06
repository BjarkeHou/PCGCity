package controller;

import gui.AppWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.BUILDING;
import model.Map;
import util.AgentHandler;
import util.MapHandler;
import util.MySimulator;
import util.Point2i;
import util.Rand;
import agent.Agent;
import agent.HouseAgent;
import agent.HutAgent;
import agent.rule.AgentBirth;
import agent.rule.Restriction;

public class Controller {
	private ArrayList<Agent> agents;
	//private HashMap<BUILDING, AgentBirth> birthParameters;
	private Map map;
	private ArrayList<Point2i> buildingList;
	private AppWindow gui;
	private AgentHandler agentHandler;
	private MySimulator sim;

	private int currentTimeStep = 0;

	//private int happyTimeChance = 10;
	private int happyTimeRate = 10;
	//private int deathRate = 5;

	public Controller() {
		sim = new MySimulator();

		agents = new ArrayList<Agent>();
		agentHandler = new AgentHandler();
		buildingList = new ArrayList<Point2i>();

		if(!sim.isSimulating()) {
			gui = new AppWindow(this);
			gui.frame.setVisible(true);
		} else {
			loadMapOnPath(sim.getPathToMap());
			for(String path : sim.getPathToAgents()) loadAgentOnPath(path);
			doRestOfTimeSteps(sim.getMaxTimeSteps());
		}
	}

	public void doRestOfTimeSteps(int totalTimeSteps) {
		while(currentTimeStep < totalTimeSteps) {
			doOneTimeStep();
		}
	}

	public void doOneTimeStep() {
		timeStep(currentTimeStep);

		if(sim.isSimulating()) {
			if(currentTimeStep%sim.getFertilityRate() == 0){
				performHappyTime();
			}
		} else {
			if(currentTimeStep%gui.getFertilityRate() == 0){
				performHappyTime();
			} 
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
			if(agent.getRetirementStatus()) agentsToRemove.add(agent);
		}

		//Retire old agents
		for(Agent a : agentsToRemove) agents.remove(a);
		
		if(sim.isSimulating()) {
			if(sim.writeFiles() && timestep%sim.writeRate() == 0)
				MapHandler.writeMapToFile(map, currentTimeStep, agents, sim.getPathToWriteFiles(), sim.showAgents());
			
			currentTimeStep++;
			return;
		}
		
		gui.setAmountOfAgentsLbl(agents.size());

		//Update map
		if(gui.writeFiles() && timestep%gui.getWriteFileRate() == 0)
			MapHandler.writeMapToFile(map, currentTimeStep, agents, gui.getPathToWriteFiles(), gui.showAgents());
		
		currentTimeStep++;
		gui.setCurrentTimeStep(currentTimeStep);
		gui.updateProgressBar(currentTimeStep);
		gui.setCurrentMap(MapHandler.convertMapToImage(map, agents, gui.showAgents()));
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
		ArrayList<Point2i> pointsToRemove = new ArrayList<Point2i>();
		for (Point2i point : buildingList){
			double happyRate = agentHandler.getAgentBirth(map.getField(point).building).happy(buildingCount);
			if(happyRate > Rand.GetDouble()){
				addNewAgent(point, map.getField(point).building);
				pointsToRemove.add(point);
			}
		}

		//Remove points that has spawned before
		for(Point2i point : pointsToRemove) buildingList.remove(point);

		//spawn for start position
		for (BUILDING b : agentHandler.getAllAgentTypes()){
			double happyRate = agentHandler.getAgentBirth(b).happy(buildingCount);
			if(happyRate > Rand.GetDouble() || (happyRate > 0.0 && (!buildingCount.containsKey(b)))){
				addNewAgent(map.getStartPos(), b);
			}
		}

		if(sim.isSimulating()) return;
		gui.setAmountOfAgentsLbl(agents.size());
	}

	public void addNewAgent() {
		if(map == null)
			return;


		// TODO FIX THIS!
		if(!agentHandler.getAllAgentTypes().contains(BUILDING.HUT))
			return;

		Agent a = agentHandler.getAgentOfType(BUILDING.HUT, map, map.getStartPos());
		agents.add(a);
		
		if(sim.isSimulating()) return;
		gui.setAmountOfStartAgentsLbl(agents.size());
		gui.setAmountOfAgentsLbl(agents.size());
	}

	public void addNewAgent(Point2i pos, BUILDING type) {
		if(map == null)
			return;

		if(!agentHandler.getAllAgentTypes().contains(type))
			return;

		agents.add(agentHandler.getAgentOfType(type, map, pos));

		if(sim.isSimulating()) return;
		gui.setAmountOfAgentsLbl(agents.size());
	}

	public void loadAgentOnPath(String pathToFile) {
		try {
			String name = agentHandler.loadAgentFromFile(pathToFile);
			if(sim.isSimulating()) return;
			gui.addAgentType(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadMapOnPath(String pathToFile) {
		try {
			map = MapHandler.loadMap(pathToFile);
			if(sim.isSimulating()) return;
			gui.setInitialMap(MapHandler.convertMapToImage(map, agents, false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
