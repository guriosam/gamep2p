package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiles {

	private BufferedImage grass;
	private BufferedImage stone;
	private BufferedImage wall;
	
	public Tiles(){
	}

	public BufferedImage getTile(int tile) throws IOException {

		switch (tile) {
		case 0:
			return getStone();
		case 1:
			return getGrass();
		case 2:
			break;
		case 5:
			return getStone();
		}
		
		return null;

	}

	private BufferedImage getGrass() throws IOException {
		if (grass == null) {
			grass = ImageIO.read((new File(Values.PATH + "tiles/grass.png")));
		}

		return grass;
	}

	private BufferedImage getStone() throws IOException {
		if (stone == null) {
			stone = ImageIO.read(new File(Values.PATH + "tiles/rock.png"));
		}

		return stone;
	}

}
