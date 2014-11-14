package agent;

public abstract class Requirement {
	public int value = 0;
	public boolean upperLimit = false;
	
	protected Requirement(int value, boolean upperLimit) {
		this.value = value;
		this.upperLimit = upperLimit;
	}
}
