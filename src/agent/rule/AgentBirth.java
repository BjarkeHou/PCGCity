package agent.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.BUILDING;

public class AgentBirth {
	HashMap<BUILDING, Integer> restrictions;
	double happyRate;
	//
	public AgentBirth(double birthRate){
		this.happyRate = birthRate;
		restrictions = new HashMap<BUILDING, Integer>();
	}
	
	public void addRestriction(BUILDING type, int value){
		if(value > 0) restrictions.put(type, value);
	}
	
	public double happy(HashMap<BUILDING, Integer> count)
	{
		for(BUILDING b : restrictions.keySet()){
			if(!count.containsKey(b)) return 0.0;
			if(count.get(b) < restrictions.get(b)) return 0.0;
		}
		return happyRate;
	}
}
