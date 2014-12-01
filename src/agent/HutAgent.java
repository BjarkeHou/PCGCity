package agent;

import agent.rule.*;
import model.BUILDINGTYPE;
import model.Map;
import model.TERRAINTYPE;
import util.Point2i;

public class HutAgent extends Agent {

	
	public HutAgent(Point2i startPos, Map map) {
		super(startPos, BUILDINGTYPE.HUT, map);
		
		birth = new AgentBirth(0.05);
		birth.addBirthLocation(BUILDINGTYPE.HUT);
		birth.addBirthLocation(BUILDINGTYPE.STARTPOSITION);
		
		//Nothing must be built on the square I am standing on
		Rule r1 = new Rule(0, CONSTRAINT.ALL);
		r1.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.NONE));
		r1.addRequirement(new TerrainTypeRequirement(1, false, TERRAINTYPE.FIELD));

		//I must be next to the town
		Rule r2 = new Rule(1, CONSTRAINT.ANY);
		r2.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.STARTPOSITION));
		r2.addRequirement(new BuildingTypeRequirement(1, false, BUILDINGTYPE.HUT));
		
		//It must not be too tight to built here
		Rule r3 = new Rule(1, CONSTRAINT.ALL);
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

	

}
