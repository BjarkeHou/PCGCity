package model;

import util.Point2i;

public class Change {
	Point2i point;
	private BUILDINGTYPE presentType;
	private int timeStep;
	
	public Point2i getPoint() {
		return point;
	}
	
	public BUILDINGTYPE getPresentType() {
		return presentType;
	}
	
	public int getTimeStep() {
		return timeStep;
	}
	
	public Change(Point2i point,
			BUILDINGTYPE presentType, int timeStep) {
		super();
		this.point = point;
		this.presentType = presentType;
		this.timeStep = timeStep;
	}
	
	
}
