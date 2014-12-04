package agent;

import java.util.ArrayList;
import java.util.Iterator;

import agent.rule.*;
import model.*;
import util.Point2i;
import util.Rand;

public class Agent {
	public String name;
	protected Point2i startPos;
	protected Point2i currentPos;
	protected BUILDING build; 
	protected ArrayList<Rule> ruleList;
	private Map map;
	private int retirementValue;
	protected AgentBirth birth;
	
	protected int inefficiencyCounter = 0;
	
	public Agent(BUILDING type, AgentBirth birth, int retirement){
		this.startPos = Point2i.Zero();
		this.currentPos = Point2i.Zero();
		this.birth = birth;
		this.retirementValue = retirement;
		build = type;
		ruleList = new ArrayList<Rule>();
	}
	
	public Agent(Point2i startPos, BUILDING type, Map m){
		this.startPos = startPos;
		this.currentPos = startPos;
		build = type;
		this.map = m;
		ruleList = new ArrayList<Rule>();
	}
	
	public void addRule(Rule r){
		ruleList.add(r);
	}
	
	public void supplyMap(Map m) {
		map = m;
	}
	
	public void supplyStartPos(Point2i startPos) {
		this.startPos = startPos;
		this.currentPos = this.startPos;
	}
	
	public Point2i getPos() {
		return currentPos;
	}
	
	public BUILDING getBuilder() {
		return build;
	}
	
	public boolean testCurrentField() {
		if(map == null)
			return false;
		
		Map map = this.map;
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
			
			//Test for inefficient
			if(totalCondition) inefficiencyCounter = 0;
			else inefficiencyCounter++;
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
				if(req instanceof BuildingRequirement){
					if(f.building == ((BuildingRequirement) req).getBuilding()) counter++;
				}
				if(req instanceof TerrainRequirement){
					if(f.terrain == ((TerrainRequirement) req).getTerrain()) counter++;
				}
				if(req instanceof TierRequirement){
					if(TIER.Convert(f.building) == ((TierRequirement) req).GetTier()) counter++;
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
	
	public boolean getRetirementStatus() {
		return inefficiencyCounter>=retirementValue;
	}
	
	public Agent clone() {
		Agent clone = new Agent(this.build, this.birth, this.retirementValue);
		if(this.map != null) clone.supplyMap(this.map);
		if(this.startPos != Point2i.Zero()) clone.supplyStartPos(this.startPos);
		for(Rule r : ruleList) clone.addRule(r);

		return clone;
	}
}
