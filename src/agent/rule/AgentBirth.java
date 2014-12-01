package agent.rule;

import java.util.ArrayList;
import java.util.Random;

import model.BUILDING;

public class AgentBirth {
	ArrayList<BUILDING> birthLocations;
	double birthRate;
	Random rand;
	//
	public AgentBirth(double birthRate){
		this.birthRate = birthRate;
		rand = new Random();
		birthLocations = new ArrayList<BUILDING>();
	}
	
	public void addBirthLocation(BUILDING type){
		birthLocations.add(type);
	}
	
	public boolean birth()
	{
		if (rand.nextDouble() < birthRate){
			return true;
		}
		return false;
	}
}
