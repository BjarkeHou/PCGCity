package agent.rule;

import model.TIER;

public class TierRequirement extends Requirement {
	TIER tier;

	public TierRequirement(int value, boolean upperLimit, TIER t) {
		super(value, upperLimit);
		tier = t;
	}
	
	public TIER GetTier()
	{
		return tier;
	}

}
