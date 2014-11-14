package model;

public class Field {
	public TERRAINTYPE terrainType;
	public BUILDINGTYPE buildingType;
	
	public Field(TERRAINTYPE terrain, BUILDINGTYPE building) {
		this.terrainType = terrain;
		this.buildingType = building;
	}
}
