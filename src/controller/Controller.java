package controller;

import java.util.ArrayList;

import model.BuildingMap;
import agent.Agent;

public class Controller {
	private ArrayList<Agent> agents;
	private BuildingMap map;
	
	private int totalTimeSteps = 1;
	
	public Controller() {
		agents = new ArrayList<Agent>();
		map = new BuildingMap();
	}
	
	public void run() {
		for (int currentTimeStep = 0; currentTimeStep < totalTimeSteps; currentTimeStep++) {
			for (Agent agent : agents) {
				agent.move();
				if(agent.testCurrentField()) {
					map.changeField(agent.getPos().x, agent.getPos().y, agent.getBuilderType(), currentTimeStep);
				}	
			}
		}
	}
}
