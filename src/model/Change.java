package model;

public class Change {
	private int fieldX;
	private int fieldY;
	private BUILDINGTYPE presentType;
	private int timeStep;
	
	public int getFieldX() {
		return fieldX;
	}
	public int getFieldY() {
		return fieldY;
	}
	public BUILDINGTYPE getPresentType() {
		return presentType;
	}
	
	public int getTimeStep() {
		return timeStep;
	}
	
	public Change(int fieldX, int fieldY,
			BUILDINGTYPE presentType, int timeStep) {
		super();
		this.fieldX = fieldX;
		this.fieldY = fieldY;

		this.presentType = presentType;
		this.timeStep = timeStep;
	}
	
	
}
