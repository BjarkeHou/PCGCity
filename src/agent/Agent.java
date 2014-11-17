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
		ruleList = new ArrayList<Rule>();
	}
	
	public void addRule(Rule r){
		ruleList.add(r);
	}
	
	public Point2i getPos() {
		return currentPos;
	}
	
	public BUILDINGTYPE getBuilderType() {
		return builder;
	}
	
	public boolean testCurrentField() {
		Map map = Map.getCurrent();
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

	public abstract Point2i move(int timestep);
	
	protected Point2i baseMove(){
		int x = 0;
		int y = 0;
		while (x==0 && y==0){
			x = Rand.GetInt(3)-1;
			y = Rand.GetInt(3)-1;
		}
		return new Point2i(x, y);
		
	}
	
	protected Point2i dirToStart(){
		Point2i vec = currentPos.vecToOther(startPos);
		boolean ortho = checkOrtho(vec);
		if(ortho){
			if(Math.abs(vec.x()) < Math.abs(vec.y())){
				return new Point2i(0, vec.y()/Math.abs(vec.y()));
			}
			else{
				return new Point2i(vec.x()/Math.abs(vec.x()), 0);
			}
		}
		else
		{
			return new Point2i(vec.x()/Math.abs(vec.x()), vec.y()/Math.abs(vec.y()));
		}
	}

	protected double distToStart(){
		return currentPos.distanceTo(startPos);
	}
	private boolean CheckRequirementCondition(Map m, int radius, Requirement req){
		int counter = 0;
		for(int i = (currentPos.x()-radius > 0 ? currentPos.x()-radius : 0); 
				i < (currentPos.x()+1+radius < m.getWidth() ? currentPos.x()+1+radius : m.getWidth()-1); i++){
			for(int j = (currentPos.y()-radius > 0 ? currentPos.y()-radius : 0); 
			j < (currentPos.y()+1+radius < m.getHeight() ? currentPos.y()+1+radius : m.getHeight()-1); j++){
				Field f = m.getField(new Point2i(i,j));
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

	private boolean checkOrtho(Point2i vec) {
		if(Math.abs(vec.x()) < 2 * Math.abs(vec.y())) return false;
		if(Math.abs(vec.y()) < 2 * Math.abs(vec.x())) return false;
		return true;
	}
}
