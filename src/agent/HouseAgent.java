package agent;

import agent.rule.BuildingRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Rule;
import agent.rule.TerrainRequirement;
import model.BUILDING;
import model.Map;
import model.TERRAIN;
import util.Point2i;

public class HouseAgent extends Agent {

	public HouseAgent(Point2i startPos, Map m) {
		super("HouseAgent", startPos, BUILDING.HOUSE, 10, m);
		
		//I must build on land
		Rule r1 = new Rule(0, CONSTRAINT.ALL);
		r1.addRequirement(new TerrainRequirement(1, false, TERRAIN.FIELD));

		//I must not build over important buildings
		Rule r2 = new Rule(0, CONSTRAINT.ANY);
		r2.addRequirement(new BuildingRequirement(1, false, BUILDING.NONE));
		r2.addRequirement(new BuildingRequirement(1, false, BUILDING.HUT));
		
		//I want a Garden
		Rule r3 = new Rule(1, CONSTRAINT.ALL);
		r3.addRequirement(new BuildingRequirement(1, false, BUILDING.NONE));
		
		//I must be in the city
		Rule r4 = new Rule(2, CONSTRAINT.ANY);
		r4.addRequirement(new BuildingRequirement(10, false, BUILDING.HUT));
		r4.addRequirement(new BuildingRequirement(7, false, BUILDING.HOUSE));

		ruleList.add(r1);
		ruleList.add(r2);
		ruleList.add(r3);
		ruleList.add(r4);
		// TODO Auto-generated constructor stub
	}

}
