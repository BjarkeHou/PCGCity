package model;

import java.util.ArrayList;
import java.util.HashMap;

import util.Point2i;

public class Map {
	private HashMap<Integer, ArrayList<Change>> changes;
	private int height = 8;
	private int width = 8;
	private Field[][] map;
	
	public Map() {
		
	}
	
	public Map(int mapWidth, int mapHeight, TERRAINTYPE[][] terrain) {
		width = mapWidth;
		height = mapHeight;
		
		map = new Field[width][height];
		changes = new HashMap<Integer, ArrayList<Change>>();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Field tempField = new Field(terrain[x][y], BUILDINGTYPE.NONE);
				map[x][y] = tempField;				
			}
		}
	}
	
	public Field getField(Point2i point) {
		if((point.x >= 0 && point.x < width) && (point.y>=0 && point.y < height))
			return map[point.x][point.y];
		else return null;
	}
	
	public BUILDINGTYPE getBuildingTypeForTimestep(Point2i point, int timestep) {
		for (int i = timestep; i >= 0 ; i--) {
			for (Change change : changes.get(i)) {
				if(change.getPoint().x == point.x && change.getPoint().y == point.y) {
					return change.getPresentType();
				}
			}
		}
		return BUILDINGTYPE.NONE;
	}
	
	public void changeBuildingTypeOnField(Point2i point, BUILDINGTYPE newType, int timestep) {
		map[point.x][point.y].buildingType = newType;
		addChange(new Change(point, newType, timestep));
	}
	
	private void addChange(Change c) {
		if(changes.get(c.getTimeStep()) == null) {
			changes.put(c.getTimeStep(), new ArrayList<Change>());
		}
		
		changes.get(c.getTimeStep()).add(c);
	}

	public static Map getCurrent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
