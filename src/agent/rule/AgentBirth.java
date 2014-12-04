package agent.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.BUILDING;

public class AgentBirth {
	ArrayList<Restriction> restrictions;
	double happyRate;
	//
	public AgentBirth(double birthRate){
		this.happyRate = birthRate;
		restrictions = new ArrayList<Restriction>();
	}
	
	public void addRestriction(Restriction r){
		restrictions.add(r);
	}
	
	public double happy(HashMap<BUILDING, Integer> count)
	{
		for(Restriction r : restrictions){
			if(!count.containsKey(r.getBuildingType())) return 0.0;
			if(count.get(r.getBuildingType()) < r.getAmount()) return 0.0;
		}
		return happyRate;
	}
}
