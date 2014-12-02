package util;

public class Rand {

	private static java.util.Random rand = new java.util.Random();
	
	public static int GetInt(int i){
		return rand.nextInt(i);
	}
	
	public static double GetDouble(){
		return rand.nextDouble();
	}
	
}
