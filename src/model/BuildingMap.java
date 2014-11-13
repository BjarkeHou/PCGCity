package model;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingMap extends Map {
	HashMap<Integer, ArrayList<Change>> changes;
	BUILDINGTYPE[][] map;
	
	
	public BuildingMap() {
		super();
		changes = new HashMap<Integer, ArrayList<Change>>();
		map = new BUILDINGTYPE[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = BUILDINGTYPE.NONE;
			}
		}
	}
	
	public BUILDINGTYPE getCurrentField(int x, int y) {
		return map[x][y];
	}
	
	public BUILDINGTYPE getFieldForTimestep(int x, int y, int timestep) {
		for (int i = timestep; i >= 0 ; i--) {
			for (Change change : changes.get(i)) {
				if(change.getFieldX() == x && change.getFieldY() == y) {
					return change.getPresentType();
				}
			}
		}
		return BUILDINGTYPE.NONE;
	}
	
	public void changeField(int x, int y, BUILDINGTYPE type, int timestep) {
		map[x][y] = type;
		addChange(new Change(x, y, type, timestep));
	}
	
	private void addChange(Change c) {
		if(changes.get(c.getTimeStep()) == null) {
			changes.put(c.getTimeStep(), new ArrayList<Change>());
		}
		
		changes.get(c.getTimeStep()).add(c);
	}
}
