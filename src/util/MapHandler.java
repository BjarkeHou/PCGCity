/**
 * 
 */
package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.BUILDING;
import model.Field;
import model.Map;
import model.TERRAIN;
import agent.*;

public class MapHandler {
	
	public static boolean showAgents = true;

	public static Map loadMap(String pathToFile) throws Exception {
		File file = new File(pathToFile);
		BufferedImage image = ImageIO.read(file);

		TERRAIN[][] terrain = new TERRAIN[image.getWidth()][image.getHeight()];
		boolean foundAStartPositionBefore = false;
		Point2i startPos = null;
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color c = new Color(image.getRGB(x, y));

				// Color Red get cordinates
				if (c.getRed() < 100 && c.getGreen() > 230 && c.getBlue() < 100) {
					terrain[x][y] = TERRAIN.FIELD;
				} else if (c.getRed() < 100 && c.getGreen() < 100 && c.getBlue() > 230) {
					terrain[x][y] = TERRAIN.WATER;
				} else if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
					terrain[x][y] = TERRAIN.ROCK;
				} else if (c.getRed() > 230 && c.getGreen() > 230 && c.getBlue() < 100 && !foundAStartPositionBefore) {
					startPos = new Point2i(x, y);
				} else {
					System.out.println(String.format("Coordinate %d %d", x, y));
					System.out.println("Red Color value = " + c.getRed());
					System.out.println("Green Color value = " + c.getGreen());
					System.out.println("Blue Color value = " + c.getBlue());
				}
			}
		}
		
		if(startPos == null) {
			throw new Exception("Map doesnt contain a start position (marked with yellow)");
		}
				
		Map m = new Map(image.getWidth(), image.getHeight(), terrain, startPos);
		
		return m;
	}

	public static void writeMapToFile(Map map, int timeStep, ArrayList<Agent> agents, String path, boolean showAgents) {

		BufferedImage img = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		img = translateMap(map, img, agents, showAgents);
		
		//File file = new File("/Users/bjarkehou/Desktop/PCGCity/PCGCity_generated_map_ts" + timeStep + ".png");
		File file = new File("C:\\Users\\Oragada\\Desktop\\PCGCity\\PCGCity_generated_map_ts" + timeStep + ".png");
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage convertMapToImage(Map map, ArrayList<Agent> agents, boolean showAgents) {
		BufferedImage img = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return translateMap(map, img, agents, showAgents);
	}
	
	private static BufferedImage translateMap(Map map, BufferedImage outMap, ArrayList<Agent> agents, boolean showAgents) {
		int lowX = map.getStartPos().x();
		int lowY = map.getStartPos().y();
		int highX = map.getStartPos().x();
		int highY = map.getStartPos().y();
		
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				Point2i point = new Point2i(x, y);
				Field field = map.getField(point);
				if(field.building != BUILDING.NONE) {
					// Der er bygning på feltet.
					if(x < lowX) lowX = x;
					if(x > highX) highX = x;
					if(y < lowY) lowY = y;
					if(y > highY) highY = y;
					//pixels[y*map.getWidth() + x] = getColorForBuildingType(map.getField(point).buildingType);
					outMap.setRGB(x, y, getColorForBuilding(field.building));
				} else {
					// Der er ikke bygning på feltet, udskriv terrain.
					//pixels[y*map.getWidth() + x] = getColorForTerrainType(map.getField(point).terrainType);
					outMap.setRGB(x, y, getColorForTerrain(field.terrain));
				}
				if(showAgents){
					for(Agent a : agents){
						if(a.getPos().equals(new Point2i(x,y))){
							outMap.setRGB(x, y, new Color(100,100,100).getRGB());
						}
					}
				}
				
			}
		}
		return outMap;
	}
	
	private static int getColorForBuilding(BUILDING building) {
		switch (building) {
		case STARTPOSITION:
			// Create yellow pixel [255,255,0]
			//return (255<<24) | (255<<16) | (255<<8) | 0;
			return new Color(255,255,0,255).getRGB();
		case HUT:
			// Create red pixel [200,0,0]
//			return (255<<24) | (200<<16) | (0<<8) | 0;
			return new Color(200,0,0,255).getRGB();
		case HOUSE:
			// Create orange pixel [200,100,0]
			return new Color(200, 100, 0,255).getRGB();
		case STORE:
			// Create purple pixel [200,0,200]
//			return (255<<24) | (150<<16) | (0<<8) | 0;
			return new Color(200,0,200,255).getRGB();
		case TOWNHOUSE:
			return new Color(200,157,43,115).getRGB();
		default:
//			return (255<<24) | (255<<16) | (255<<8) | 255;
			return new Color(255,255,255,255).getRGB();
		}
	}
	
	private static int getColorForTerrain(TERRAIN terrain) {
		switch (terrain) {
		case FIELD:
			// Create green pixel [0,255,0]
//			return (255<<24) | (0<<16) | (255<<8) | 0;
			return new Color(0,255,0,255).getRGB();
		case ROCK:
			// Create black pixel [0,0,0]
//			return (255<<24) | (0<<16) | (0<<8) | 0;
			return new Color(0,0,0,255).getRGB();
		case WATER:
			// Create blue pixel [0,0,255]
//			return (255<<24) | (0<<16) | (0<<8) | 255;
			return new Color(0,0,255,255).getRGB();
		case OUTERSPACE:
			// Create white pixel [255,255,255]
//			return (255<<24) | (255<<16) | (255<<8) | 255;
			return new Color(255,255,255,255).getRGB();
		default:
//			return (255<<24) | (255<<16) | (255<<8) | 255;
			return new Color(255,255,255,255).getRGB();
		}
	}

}





















