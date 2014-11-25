package agent.rule;

import java.util.ArrayList;
import java.util.Iterator;

import model.BUILDINGTYPE;



public class Rule {
	
	ArrayList<Requirement> requirements;
	MoveInstruction movement;
	private int radius = 0;
	private CONSTRAINT constraint;
	
	public Rule(int radius, CONSTRAINT constraint, MoveInstruction instruct) {
		this.radius = radius;
		this.constraint = constraint;
		if(instruct != null) this.movement = instruct;
		else movement = new BuildingMoveInstruction(MOVEDIR.TO, 0, BUILDINGTYPE.STARTPOSITION);
		
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
	
	public MoveInstruction GetMovement(){
		return movement;
	}
}
