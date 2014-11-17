/**
 * 
 */
package util;

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
				final int clr = image.getRGB(x, y);
				final int red = (clr & 0x00ff0000) >> 16;
			final int green = (clr & 0x0000ff00) >> 8;
			final int blue = clr & 0x000000ff;

			// Color Red get cordinates
			if (green == 255) {
				terrain[x][y] = TERRAINTYPE.FIELD;
			} else if(blue == 255) {
				terrain[x][y] = TERRAINTYPE.WATER;
			} else if(red == 0 && green == 0 && blue == 0) {
				terrain[x][y] = TERRAINTYPE.ROCK;
			} else {
				System.out.println(String.format("Coordinate %d %d", x, y));
				System.out.println("Red Color value = " + red);
				System.out.println("Green Color value = " + green);
				System.out.println("Blue Color value = " + blue);
			}
			}
		}

		return new Map(image.getWidth(), image.getHeight(), terrain);
	}

	public static void writeMapToFile(Map map, int timeStep) {

		BufferedImage outMap = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//int[] pixels = new int[map.getWidth() * map.getHeight()];
		
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
		
		//outMap.setRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, 0);
		
		File file = new File("/Users/bjarkehou/Desktop/PCGCity/PCGCity_generated_map_ts" + timeStep + ".png");
		try {
			ImageIO.write(outMap, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	private static int getColorForBuildingType(BUILDINGTYPE buildingType) {
		switch (buildingType) {
		case STARTPOSITION:
			// Create yellow pixel [255,255,0]
			return (255<<24) | (255<<16) | (255<<8) | 0;
		case HUT:
			// Create red pixel [200,0,0]
			return (255<<24) | (200<<16) | (0<<8) | 0;
		case MANSION:
			// Create darker red pixel [150,0,0]
			return (255<<24) | (150<<16) | (0<<8) | 0;
		default:
			return (255<<24) | (255<<16) | (255<<8) | 255;
		}
	}
	
	private static int getColorForTerrainType(TERRAINTYPE terrainType) {
		switch (terrainType) {
		case FIELD:
			// Create green pixel [0,255,0]
			return (255<<24) | (0<<16) | (255<<8) | 0;
		case ROCK:
			// Create black pixel [0,0,0]
			return (255<<24) | (0<<16) | (0<<8) | 0;
		case WATER:
			// Create blue pixel [0,0,255]
			return (255<<24) | (0<<16) | (0<<8) | 255;
		case OUTERSPACE:
			// Create white pixel [255,255,255]
			return (255<<24) | (255<<16) | (255<<8) | 255;
		default:
			return (255<<24) | (255<<16) | (255<<8) | 255;
		}
	}

}





















