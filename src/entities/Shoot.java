package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Values;

public class Shoot {

	public double posX;
	public double posY;
	public int facing;
	public BufferedImage bullet;
	public boolean finalize = false;

	public void init(double posX, double posY, int facing) throws IOException {
		this.posX = posX;
		this.posY = posY;
		this.facing = facing;
		bullet = ImageIO.read((new File(Values.PATH + "general/bullet.png")));
	}

	public void update() {
		
		if(finalize){
			return;
		}

		switch (facing) {
		case 1:
			// down
			if (Map.map[(int) (posY + 2) / 2][(int) (posX + 2) / 2] == 0
					|| Map.map[(int) (posY + 2) / 2][(int) (posX + 2) / 2] == 5) {
				finalize = true;
				posX = -5;
				posY = -5;
				break;
			}
			posY += 0.5;
			break;
		case 2:
			// left
			if (Map.map[(int) (posY + 2) / 2][(int) (posX) / 2] == 0
					|| Map.map[(int) (posY + 2) / 2][(int) (posX) / 2] == 5) {
				finalize = true;
				posX = -5;
				posY = -5;
				break;
			}
			posX -= 0.5;
			break;
		case 3:
			// right
			if (Map.map[(int) (posY + 2) / 2][(int) (posX + 3) / 2] == 0
					|| Map.map[(int) (posY + 2) / 2][(int) (posX + 3) / 2] == 5) {
				finalize = true;
				posX = -5;
				posY = -5;
				break;
			}
			posX += 0.5;

			break;
		case 4:
			// up
			if (Map.map[(int) posY / 2][(int) (posX + 2) / 2] == 0
					|| Map.map[(int) posY / 2][(int) (posX + 2) / 2] == 5) {
				finalize = true;
				posX = -5;
				posY = -5;
				break;
			}
			posY -= 0.5;
			break;
		}

		// if (Map.getPosition(posX, posY) == 0 || Map.getPosition(posX, posY)
		// == 5) {
		// posY = -5;
		// posX = -5;
		// finalize = true;
		// }

	}

	public void draw(Graphics2D screen) {
		screen.drawImage(bullet.getSubimage(0, 75 * (facing - 1), 80, 64), (int) (posX) * 16, (int) (posY) * 16, null);
	}
}
