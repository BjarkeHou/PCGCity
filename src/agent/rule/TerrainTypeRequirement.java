package agent.rule;

import model.TERRAINTYPE;

public class TerrainTypeRequirement extends Requirement {
	TERRAINTYPE type = TERRAINTYPE.FIELD;
	public TerrainTypeRequirement(int value, boolean upperLimit, TERRAINTYPE type) {
		super(value, upperLimit);
		this.type = type;
	}
	
	public TERRAINTYPE getType() {
		return type;
	}
}
