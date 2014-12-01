package agent.rule;

import java.util.ArrayList;
import java.util.Random;

import model.BUILDINGTYPE;

public class AgentBirth {
	ArrayList<BUILDINGTYPE> birthLocations;
	double birthRate;
	Random rand;
	//
	public AgentBirth(double birthRate){
		this.birthRate = birthRate;
		rand = new Random();
		birthLocations = new ArrayList<BUILDINGTYPE>();
	}
	
	public void addBirthLocation(BUILDINGTYPE type){
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
