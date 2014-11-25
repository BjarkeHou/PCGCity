package agent.rule;

import model.TERRAINTYPE;


public class TerrainMoveInstruction extends MoveInstruction {
	
	private TERRAINTYPE terrain;
	
	TerrainMoveInstruction(MOVEDIR dir, int mag, TERRAINTYPE ter){
		super(dir, mag);
		terrain = ter;
	}
	
	public TERRAINTYPE getType(){
		return terrain;
	}

}
