package agent;

import java.util.ArrayList;

enum CONSTRAINT {
	ANY, ALL
}

public class Rule {
	
	ArrayList<Requirement> requirements;
	int radius = 0;
	CONSTRAINT constraint;
	
	public Rule(int radius, CONSTRAINT constraint) {
		this.radius = radius;
		this.constraint = constraint;
		
		requirements = new ArrayList<Requirement>();
	}
	
	public void addRequirement(Requirement req) {
		requirements.add(req);
	}
}
