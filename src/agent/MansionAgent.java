package agent;

import model.BUILDINGTYPE;
import model.Map;
import util.Point2i;

public class MansionAgent extends Agent {

	public MansionAgent(Point2i startPos, Map map) {
		super(startPos, BUILDINGTYPE.MANSION, map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean testCurrentField() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public Point2i move(int timestep) {
		// TODO Auto-generated method stub
		return new Point2i(0,0);
	}

}
