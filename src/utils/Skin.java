package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Skin {

	private BufferedImage skin;

	public Skin(int number) throws IOException {
		String path = "player/skin_" + number + ".png";
		
		skin = ImageIO.read((new File(Values.PATH + path)));
	}

	public BufferedImage getSkin() {
		return skin;
	}

}
