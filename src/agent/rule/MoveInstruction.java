package agent.rule;

public abstract class MoveInstruction {
	protected MOVEDIR moveDir;
	protected int magnitude;
	
	protected MoveInstruction(MOVEDIR dir, int mag){
		moveDir = dir;
		magnitude = mag;
	}
	
	MOVEDIR GetMoveDir() {return moveDir;}
	int GetMagnitude() {return magnitude;}
	
	
}
