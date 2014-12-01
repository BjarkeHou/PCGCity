package agent.rule;

import model.TERRAIN;

public class TerrainRequirement extends Requirement {
	TERRAIN t = TERRAIN.FIELD;
	
	public TerrainRequirement(int value, boolean upperLimit, TERRAIN type) {
		super(value, upperLimit);
		this.t = type;
	}
	
	public TERRAIN getTerrain() {
		return t;
	}
}
