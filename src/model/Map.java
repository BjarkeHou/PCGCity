package model;

import java.util.ArrayList;
import java.util.HashMap;

import util.Point2i;

public class Map {
	private HashMap<Integer, ArrayList<Change>> changes;
	private int height = 8;
	private int width = 8;
	private Point2i startPos;
	private Field[][] map;
	
	public Map() {
		
	}
	
	public Map(int mapWidth, int mapHeight, TERRAIN[][] terrain, Point2i startPos) {
		width = mapWidth;
		height = mapHeight;
		this.startPos = startPos;
		
		map = new Field[width][height];
		changes = new HashMap<Integer, ArrayList<Change>>();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Field tempField = new Field(terrain[x][y], BUILDING.NONE);
				
				if(x==startPos.x() && y==startPos.y())
					tempField.building = BUILDING.STARTPOSITION;
				
				map[x][y] = tempField;				
			}
		}
	}
	
	public Field getField(Point2i point) {
		if((point.x() >= 0 && point.x() < width) && (point.y()>=0 && point.y() < height))
			return map[point.x()][point.y()];
		else return null;
	}
	
	public BUILDING getBuildingForTimestep(Point2i point, int timestep) {
		for (int i = timestep; i >= 0 ; i--) {
			for (Change change : changes.get(i)) {
				if(change.getPoint().x() == point.x() && change.getPoint().y() == point.y()) {
					return change.getPresentType();
				}
			}
		}
		return BUILDING.NONE;
	}
	
	public void changeBuildingOnField(Point2i point, BUILDING newType, int timestep) {
		map[point.x()][point.y()].building = newType;
		addChange(new Change(point, newType, timestep));
	}
	
	private void addChange(Change c) {
		if(changes.get(c.getTimeStep()) == null) {
			changes.put(c.getTimeStep(), new ArrayList<Change>());
		}
		
		changes.get(c.getTimeStep()).add(c);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Point2i getStartPos() {
		return startPos;
	}
}
