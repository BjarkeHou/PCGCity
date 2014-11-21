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
			for (Agent agent : agents) {
				agent.move(timeStep);
				if(agent.testCurrentField()) {
					map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), timeStep);
				}
				MapHandler.writeMapToFile(map, timeStep);
			}
			currentTimeStep++;
			gui.setCurrentTimeStep(currentTimeStep);
			gui.updateProgressBar(currentTimeStep);
			gui.setCurrentMap(MapHandler.convertMapToImage(map));
		}
	}
	
	public void doOneTimeStep() {
		if(currentTimeStep == totalTimeSteps)
			return;
		
		for (Agent agent : agents) {
			agent.move(currentTimeStep);
			if(agent.testCurrentField()) {
				map.changeBuildingTypeOnField(agent.getPos(), agent.getBuilderType(), currentTimeStep);
			}
			MapHandler.writeMapToFile(map, currentTimeStep);
		}
		currentTimeStep++;
		gui.setCurrentTimeStep(currentTimeStep);
		gui.updateProgressBar(currentTimeStep);
		gui.setCurrentMap(MapHandler.convertMapToImage(map));
	}
	
	public void addNewAgent() {
		agents.add(new HutAgent(new Point2i(2, 5), map));
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
			gui.setInitialMap(MapHandler.convertMapToImage(map));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getTotalTimeStep() {
		return totalTimeSteps;
	}
}
