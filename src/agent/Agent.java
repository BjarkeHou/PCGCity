package agent;

import java.util.ArrayList;
import java.util.Iterator;

import agent.rule.*;
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
	protected Point2i moveModifiers;
	private Map map;
	
	public Agent(Point2i startPos, BUILDINGTYPE type, Map m) {
		this.startPos = startPos;
		this.currentPos = startPos;
		builder = type;
		ruleList = new ArrayList<Rule>();
		map = m;
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
		Map map = this.map;
		moveModifiers = Point2i.Zero();
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
			
			//check movement
			if(!ruleCondition){

				Point2i dir = Point2i.Zero();
				MoveInstruction mi = rule.GetMovement();
				if(mi.GetMagnitude() > 0){
					if(mi instanceof BuildingMoveInstruction){
						BuildingMoveInstruction bmi = (BuildingMoveInstruction) mi;
						switch (bmi.GetType()){
							case STARTPOSITION:
								dir = dirToField(startPos);
								break;
							//more types
							default:
								break;
						}
					}
					/*if(req instanceof TerrainMoveInstruction){
					if(f.terrainType == ((TerrainTypeRequirement) req).getType()) counter++;
					}*/
				}	
				moveModifiers = moveModifiers.add(dir.magnitude(mi.GetMagnitude()));
			}
			
			totalCondition = totalCondition & ruleCondition;
		}
		
		return totalCondition;
	}

	public void move(int timestep){
		currentPos = limitMove(currentPos.add(moveModifiers).add(baseMove()));
	}
	
	protected Point2i baseMove(){
		int x = 0;
		int y = 0;
		while (x==0 && y==0){
			x = Rand.GetInt(3)-1;
			y = Rand.GetInt(3)-1;
		}
		return new Point2i(x, y);
		
	}
	
	protected Point2i dirToField(Point2i field){
		Point2i vec = currentPos.vecToOther(field);
		return vec.GetDirUnit();
		
	}

	protected double distToField(Point2i field){
		return currentPos.distanceTo(field);
	}
	
	protected Point2i limitMove(Point2i sMove)
	{
		return sMove.mapClamp(map.getWidth(),map.getHeight());
	}
	
	private boolean CheckRequirementCondition(Map m, int radius, Requirement req){
		int counter = 0;
		for(int i = (currentPos.x()-radius > 0 ? currentPos.x()-radius : 0); 
		i < (currentPos.x()+1+radius < m.getWidth() ? currentPos.x()+1+radius : m.getWidth()); i++){
			for(int j = (currentPos.y()-radius > 0 ? currentPos.y()-radius : 0); 
			j < (currentPos.y()+1+radius < m.getHeight() ? currentPos.y()+1+radius : m.getHeight()); j++){
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
}
