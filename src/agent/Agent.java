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
	private int birthTimestep;
	private int retirementAge;
	protected AgentBirth birth;
	
	public Agent(Point2i startPos, BUILDINGTYPE type, Map m, int timestep, int retirement) {
		this.startPos = startPos;
		this.currentPos = startPos;
		builder = type;
		ruleList = new ArrayList<Rule>();
		map = m;
		birthTimestep = timestep;
		retirementAge = retirement;
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
			
			totalCondition = totalCondition & ruleCondition;
		}
		
		return totalCondition;
	}
	
	public Point2i move(int timestep) {
		Point2i move = currentPos;
		Point2i newMove = move.add(baseMove(1));
		double maxRadius = Math.sqrt(timestep)/2.0;
		double dist = distToField(startPos);
		if(dist > maxRadius) {
			Point2i dirToS = dirToField(startPos);
			newMove = newMove.add(dirToS);
		}
		newMove = limitMove(newMove);
		this.currentPos = newMove;
		return newMove;
	}
	
	public boolean RetirementAge(int currentTimestep){
		if((currentTimestep - birthTimestep) > retirementAge){
			return true;
		}
		return false;
	}
	/*public void move(int timestep){
		int mag = (timestep/100)+1;
		currentPos = limitMove(currentPos.add(moveModifiers).add(baseMove(mag)));
	}*/
	
	protected Point2i baseMove(int magnitude){
		int x = 0;
		int y = 0;
		while (x==0 && y==0){
			x = Rand.GetInt(3)-1;
			y = Rand.GetInt(3)-1;
		}
		return (new Point2i(x, y)).magnitude(magnitude);
		
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
