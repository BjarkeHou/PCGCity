package agent.rule;

import model.BUILDING;

public class Restriction {
	private int amount;
	private BUILDING building;
	
	public Restriction(BUILDING building, int amount) {
		this.building = building;
		this.amount = amount;
	}
	
	public BUILDING getBuildingType() {
		return this.building;
	}
	
	public int getAmount() {
		return this.amount;
	}
}
