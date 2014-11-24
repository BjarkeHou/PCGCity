package agent;

import java.io.IOException;
import java.util.Iterator;

import agent.rule.BuildingMoveInstruction;
import agent.rule.BuildingTypeRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.MOVEDIR;
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
		
		//Nothing must be built on the square I am standing on
		Rule r1 = new Rule(0, CONSTRAINT.ALL, null);
		r1.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.NONE));
		r1.addRequirement(new TerrainTypeRequirement(1, false, TERRAINTYPE.FIELD));

		//I must be next to the town
		BuildingMoveInstruction m2 = new BuildingMoveInstruction(MOVEDIR.TO,1,BUILDINGTYPE.STARTPOSITION);
		Rule r2 = new Rule(1, CONSTRAINT.ANY, m2);
		r2.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.STARTPOSITION));
		r2.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.HUT));
		
		//It must not be too tight to built here
		BuildingMoveInstruction m3 = new BuildingMoveInstruction(MOVEDIR.AWAY,1,BUILDINGTYPE.STARTPOSITION);
		Rule r3 = new Rule(1, CONSTRAINT.ALL, m3);
		r3.addRequirement(new BuildingTypeRequirement(3, true, BUILDINGTYPE.HUT));

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

	/*@Override
	public Point2i move(int timestep) {
		Point2i move = currentPos;
		Point2i newMove = move.add(baseMove());
		double maxRadius = Math.sqrt(timestep)/2.0;
		double dist = distToStart();
		if(dist > maxRadius) {
			Point2i dirToS = dirToStart();
			newMove = newMove.add(dirToS);
		}
		newMove = limitMove(newMove);
		this.currentPos = newMove;
		return newMove;
	}*/

}
