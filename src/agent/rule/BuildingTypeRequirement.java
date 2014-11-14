package agent.rule;

import model.BUILDINGTYPE;

public class BuildingTypeRequirement extends Requirement {
	BUILDINGTYPE type = BUILDINGTYPE.NONE;
	
	public BuildingTypeRequirement(int value, boolean upperLimit, BUILDINGTYPE type) {
		super(value, upperLimit);
		this.type = type;
	}
	
	public BUILDINGTYPE getType() {
		return type;
	}
}
