/**
 * 
 */
package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		// Skal kunne loade et map i en eller anden form for struktur
	// Give mappet til controller
	// Generelt statiske metoder

}
