package model;

public enum TIER {
	T1, T2, T3;
	
	public static TIER Convert(BUILDING b){
		switch (b){
		case HUT:
			return T1;
		case HOUSE:
		case STORE:
			return T2;
		case MANOR:
			return T3;
		default:
			return T1;
		}
		
			
	}
}
