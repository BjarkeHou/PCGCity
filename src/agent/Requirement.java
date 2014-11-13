package agent;

public abstract class Requirement {
	int value = 0;
	boolean upperLimit = false;
	
	protected Requirement(int value, boolean upperLimit) {
		this.value = value;
		this.upperLimit = upperLimit;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isUpperLimit() {
		return upperLimit;
	}
}
