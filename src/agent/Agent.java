package agent;

import java.util.ArrayList;
import java.util.Iterator;

import agent.rule.BuildingTypeRequirement;
import agent.rule.CONSTRAINT;
import agent.rule.Requirement;
import agent.rule.Rule;
import agent.rule.TerrainTypeRequirement;
import model.BUILDINGTYPE;
import model.Field;
import model.Map;
import util.Point2i;
import util.Rand;

public abstract class Agent {
	protected Point2i startPos;
	protected Point2i currentPos;
	protected BUILDINGTYPE builder; 
	protected ArrayList<Rule> ruleList;
	
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
	
	public boolean testCurrentField() {
		Map map = Map.GetCurrent();
		boolean totalCondition = true;
		for(Rule rule : ruleList){
			
			boolean ruleCondition = (rule.getConstraint() == CONSTRAINT.ALL ? true : false);
			
			Iterator<Requirement> ite = rule.GetRequirements();
			while(ite.hasNext()){
				Requirement req = ite.next();
				boolean conditionFullfilled = CheckRequirementCondition(map, rule.getRadius(), req);
				ruleCondition = (rule.getConstraint() == CONSTRAINT.ALL ? 
						ruleCondition & conditionFullfilled : 
						ruleCondition | conditionFullfilled);
				
			}
			
			totalCondition = totalCondition & ruleCondition;
		}
		
		return totalCondition;
	}
	
	private boolean CheckRequirementCondition(Map map, int radius, Requirement req) {
		int counter = 0;
		for(int i = (currentPos.x-radius > 0 ? currentPos.x-radius : 0); 
				i < (currentPos.x+radius < map.GetWidth() ? currentPos.x+radius : map.GetWidth()-1); i++){
			for(int j = (currentPos.y-radius > 0 ? currentPos.y-radius : 0); 
			j < (currentPos.y+radius < map.GetHeight() ? currentPos.y+radius : map.GetHeight()-1); j++){
				Field f = map.getField(new Point2i(i,j));
				if(req instanceof BuildingTypeRequirement){
					if(f.buildingType == ((BuildingTypeRequirement) req).getType()) counter++;
				}
				if(req instanceof TerrainTypeRequirement){
					if(f.terrainType == ((TerrainTypeRequirement) req).getType()) counter++;
				}
			}
		}
		if(req.upperLimit){
			if(counter<=req.value) return true;
		}
		else{
			if(counter>=req.value) return true;
		}
		return false;
	}

	public abstract Point2i move();
	
	protected Point2i baseMove(){
		
		int x = Rand.GetInt(3)-1;
		int y = Rand.GetInt(3)-1;
		return new Point2i(x, y);
		
	}
}
