package util;

public class MySimulator {

	private boolean isChristian = false;
	private boolean isSimulating = true;
	
	// Simulation
	private int fertilityRate = 10;
	private int maxTimeSteps = 1001;
	
	// Write files
	private boolean writeFiles = true;
	private int writeRate = 50;
	private boolean showAgents = false;
	private String pathToWriteFileForChristian = "C:\\Users\\Oragada\\Desktop\\PCGCity\\";
	private String pathToWriteFileForBjarke = "/Users/bjarkehou/Desktop/PCGCity/";
	
	// Load files
	private String[] pathToAgentsForChristian = {
			"C:\\Users\\Oragada\\Stuff\\Documents\\Google Drive\\PCGCity\\agents\\HutAgent.json",
			"C:\\Users\\Oragada\\Stuff\\Documents\\Google Drive\\PCGCity\\agents\\HouseAgent.json",
			"C:\\Users\\Oragada\\Stuff\\Documents\\Google Drive\\PCGCity\\agents\\StoreAgent.json",
			"C:\\Users\\Oragada\\Stuff\\Documents\\Google Drive\\PCGCity\\agents\\TownHouseAgent.json"
	};
	private String[] pathToAgentsForBjarke = {
			"/Users/bjarkehou/Google Drive/PCGCity/agents/HutAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/HouseAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/StoreAgent.json",
			"/Users/bjarkehou/Google Drive/PCGCity/agents/TownHouseAgent.json",
	};
	private String pathToMapForChristian = "C:\\Users\\Oragada\\Stuff\\Documents\\Google Drive\\PCGCity\\maps\\map256.png";
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
