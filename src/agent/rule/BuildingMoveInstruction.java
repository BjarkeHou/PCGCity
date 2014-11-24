package agent.rule;

import model.BUILDINGTYPE;

public class BuildingMoveInstruction extends MoveInstruction {

	BUILDINGTYPE building;
	
	public BuildingMoveInstruction(MOVEDIR dir, int mag, BUILDINGTYPE build) {
		super(dir, mag);
		building = build;
	}
	
	BUILDINGTYPE GetType() {return building;}

}
