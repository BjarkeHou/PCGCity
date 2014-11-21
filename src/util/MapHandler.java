/**
 * 
 */
package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.BUILDINGTYPE;
import model.Field;
import model.Map;
import model.TERRAINTYPE;

public class MapHandler {

	public static Map loadMap(String pathToFile) throws IOException {
		final File file = new File(pathToFile);
		final BufferedImage image = ImageIO.read(file);

		TERRAINTYPE[][] terrain = new TERRAINTYPE[image.getWidth()][image.getHeight()];

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color c = new Color(image.getRGB(x, y));

				// Color Red get cordinates
				if (c.getGreen() == 255) {
					terrain[x][y] = TERRAINTYPE.FIELD;
				} else if(c.getBlue() == 255) {
					terrain[x][y] = TERRAINTYPE.WATER;
				} else if(c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
					terrain[x][y] = TERRAINTYPE.ROCK;
				} else {
					System.out.println(String.format("Coordinate %d %d", x, y));
					System.out.println("Red Color value = " + c.getRed());
					System.out.println("Green Color value = " + c.getGreen());
					System.out.println("Blue Color value = " + c.getBlue());
				}
			}
		}

		return new Map(image.getWidth(), image.getHeight(), terrain);
	}

	public static void writeMapToFile(Map map, int timeStep) {

		BufferedImage img = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		img = translateMap(map, img);
		
		//File file = new File("/Users/bjarkehou/Desktop/PCGCity/PCGCity_generated_map_ts" + timeStep + ".png");
		File file = new File("C:\\Users\\Oragada\\Desktop\\PCGCity\\PCGCity_generated_map_ts" + timeStep + ".png");
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BufferedImage convertMapToImage(Map map) {
		BufferedImage img = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		return translateMap(map, img);
	}
	
	private static BufferedImage translateMap(Map map, BufferedImage outMap) {
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				Point2i point = new Point2i(x, y);
				Field field = map.getField(point);
				if(field.buildingType != BUILDINGTYPE.NONE) {
					// Der er bygning på feltet.
					//pixels[y*map.getWidth() + x] = getColorForBuildingType(map.getField(point).buildingType);
					outMap.setRGB(x, y, getColorForBuildingType(field.buildingType));
				} else {
					// Der er ikke bygning på feltet, udskriv terrain.
					//pixels[y*map.getWidth() + x] = getColorForTerrainType(map.getField(point).terrainType);
					outMap.setRGB(x, y, getColorForTerrainType(field.terrainType));
				}
			}
		}
		return outMap;
	}
	
	private static int getColorForBuildingType(BUILDINGTYPE buildingType) {
		switch (buildingType) {
		case STARTPOSITION:
			// Create yellow pixel [255,255,0]
			//return (255<<24) | (255<<16) | (255<<8) | 0;
			return new Color(255,255,255,255).getRGB();
		case HUT:
			// Create red pixel [200,0,0]
//			return (255<<24) | (200<<16) | (0<<8) | 0;
			return new Color(200,0,0,255).getRGB();
		case MANSION:
			// Create darker red pixel [150,0,0]
//			return (255<<24) | (150<<16) | (0<<8) | 0;
			return new Color(150,0,0,255).getRGB();
		default:
//			return (255<<24) | (255<<16) | (255<<8) | 255;
			return new Color(255,255,255,255).getRGB();
		}
	}
	
	private static int getColorForTerrainType(TERRAINTYPE terrainType) {
		switch (terrainType) {
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





















