package agent;

import java.io.IOException;
import java.util.Iterator;

import agent.rule.BuildingTypeRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Requirement;
import agent.rule.Rule;
import agent.rule.TerrainTypeRequirement;
import model.BUILDINGTYPE;
import model.Map;
import model.TERRAINTYPE;
import util.Point2i;
import util.RuleHandler;

public class HutAgent extends Agent {

	public HutAgent(Point2i startPos, Map map) {
		super(startPos, BUILDINGTYPE.HUT, map);
		Rule r1 = new Rule(1, CONSTRAINT.ANY);
		r1.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.STARTPOSITION));
		r1.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.HUT));
		Rule r2 = new Rule(1, CONSTRAINT.ALL);
		r2.addRequirement(new BuildingTypeRequirement(4, true, BUILDINGTYPE.HUT));
		Rule r3 = new Rule(0, CONSTRAINT.ALL);
		r3.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.NONE));
		r3.addRequirement(new TerrainTypeRequirement(1, false, TERRAINTYPE.FIELD));
		
		ruleList.add(r1);
		ruleList.add(r2);
		ruleList.add(r3);
//		try {
//			ruleList = RuleHandler.loadRulesFromFile("/Users/bjarkehou/Desktop/RulesData.json");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public Point2i move(int timestep) {
		Point2i move = currentPos;
		move = move.add(baseMove());
		double maxRadius = Math.sqrt(timestep)/2;
		if(distToStart() > maxRadius) move = move.add(dirToStart());
		return move.add(currentPos);
	}

}
