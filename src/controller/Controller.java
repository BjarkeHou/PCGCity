package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Map;
import util.MapHandler;
import util.RuleHandler;
import agent.Agent;

public class Controller {
	private ArrayList<Agent> agents;
	private Map map;
	
	private int totalTimeSteps = 200;
	
	public Controller() {
		agents = new ArrayList<Agent>();
	}
	
	public void run() {
		for (int currentTimeStep = 0; currentTimeStep < totalTimeSteps; currentTimeStep++) {
			for (Agent agent : agents) {
				agent.move(currentTimeStep);
				if(agent.testCurrentField()) {
					map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), currentTimeStep);
				}
				MapHandler.writeMapToFile(map, currentTimeStep);
			}
		}
		
		agents = new ArrayList<Agent>();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
