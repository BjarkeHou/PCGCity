package util;

public class MySimulator {

	private boolean isChristian = false;
	private boolean isSimulating = true;
	
	// Simulation
	private int fertilityRate = 10;
	private int maxTimeSteps = 200;
	
	// Write files
	private boolean writeFiles = true;
	private int writeRate = 10;
	private boolean showAgents = true;
	private String pathToWriteFileForChristian = "";
	private String pathToWriteFileForBjarke = "/Users/bjarkehou/Desktop/PCGCity/";
	
	// Load files
	private String[] pathToAgentsForChristian = {
			""
	};
	private String[] pathToAgentsForBjarke = {
			"/Users/bjarkehou/Google Drive/PCGCity/agents/HutAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/HouseAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/StoreAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/TownHouseAgent.json",
	};
	private String pathToMapForChristian = "";
	private String pathToMapForBjarke = "/Users/bjarkehou/Google Drive/PCGCity/maps/map256.png";
	

	
	// Getters
	public boolean isSimulating() {
		return isSimulating;
	}
	
	public String[] getPathToAgents() {
		return isChristian ? pathToAgentsForChristian : pathToAgentsForBjarke;
	}
	
	public String getPathToMap() {
		return isChristian ? pathToMapForChristian : pathToMapForBjarke;
	}
	
	public String getPathToWriteFiles() {
		return isChristian ? pathToWriteFileForChristian : pathToWriteFileForBjarke;
	}
	
	public boolean writeFiles() {
		return writeFiles;
	}
	
	public int writeRate() {
		return writeRate;
	}
	
	public boolean showAgents() {
		return showAgents;
	}
	
	public int getFertilityRate() {
		return fertilityRate;
	}
	
	public int getMaxTimeSteps() {
		return maxTimeSteps;
	}
}
