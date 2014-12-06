package model;

import java.util.ArrayList;

public enum BUILDING {
	NONE, STARTPOSITION, HUT, HOUSE, STORE, TOWNHOUSE;
	
	public static ArrayList<BUILDING> GetTierBuildings(TIER tier){
		ArrayList<BUILDING> list = new ArrayList<BUILDING>();
		
		switch (tier){
		case T1:
			list.add(HUT);
			break;
		case T2:
			list.add(HOUSE);
			list.add(STORE);
			break;
		case T3:
			list.add(TOWNHOUSE);
			break;
		}
		
		return list;
	}
}