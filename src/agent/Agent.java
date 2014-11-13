package agent;

import model.BUILDINGTYPE;
import util.Point2i;
import util.Rand;

public abstract class Agent {
	protected Point2i startPos;
	protected Point2i currentPos;
	protected BUILDINGTYPE builder; 
	
	public Agent(Point2i startPos, BUILDINGTYPE type) {
		this.startPos = startPos;
		builder = type;
	}
	
	public Point2i getPos() {
		return currentPos;
	}
	
	public BUILDINGTYPE getBuilderType() {
		return builder;
	}
	
	public abstract boolean testCurrentField();
	
	public abstract Point2i move();
	
	protected Point2i baseMove(){
		
		int x = Rand.GetInt(3)-1;
		int y = Rand.GetInt(3)-1;
		return new Point2i(x, y);
		
	}
}
