package agent.rule;

import model.BUILDING;

public class BuildingRequirement extends Requirement {
	BUILDING b = BUILDING.NONE;
	
	public BuildingRequirement(int value, boolean upperLimit, BUILDING type) {
		super(value, upperLimit);
		this.b = type;
	}
	
	public BUILDING getBuilding() {
		return b;
	}
}
