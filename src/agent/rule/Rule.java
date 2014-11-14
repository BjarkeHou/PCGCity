package agent.rule;

import java.util.ArrayList;
import java.util.Iterator;



public class Rule {
	
	ArrayList<Requirement> requirements;
	private int radius = 0;
	private CONSTRAINT constraint;
	
	public Rule(int radius, CONSTRAINT constraint) {
		this.radius = radius;
		this.constraint = constraint;
		
		requirements = new ArrayList<Requirement>();
	}
	
	public void addRequirement(Requirement req) {
		requirements.add(req);
	}
	
	public Iterator<Requirement> GetRequirements(){
		return requirements.iterator();
	}

	public int getRadius() {
		return radius;
	}

	public CONSTRAINT getConstraint() {
		return constraint;
	}
}
