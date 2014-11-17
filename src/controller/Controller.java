package controller;

import java.io.IOException;
import java.util.ArrayList;

import util.MapHandler;
import util.Point2i;
import util.RuleHandler;
import model.Map;
import agent.Agent;
import agent.HutAgent;

public class Controller {
	private ArrayList<Agent> agents;
	private Map map;
	
	private int totalTimeSteps = 10;
	
	public Controller() {
		agents = new ArrayList<Agent>();
	}
	
	public void run() {
		
		agents.add(new HutAgent(new Point2i(2, 5), map));
		
		for (int currentTimeStep = 0; currentTimeStep < totalTimeSteps; currentTimeStep++) {
			for (Agent agent : agents) {
				agent.move(currentTimeStep);
				if(agent.testCurrentField()) {
					map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), currentTimeStep);
					MapHandler.writeMapToFile(map, currentTimeStep);
				}	
			}
		}
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
