package model;

public abstract class Map {
	
	protected int height = 8;
	protected int width = 8;
	
	public Map() {
		
	}
	
	public Map(int mapWidth, int mapHeight) {
		width = mapWidth;
		height = mapHeight;
	}
}
