package entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import utils.Skin;
import utils.Values;

public class Player {

	public double posX;
	public double posY;
	private int id;
	private int health;
	private Skin skin;
	public List<Shoot> shoots;
	private int facing;
	private int direction;
	private int life;

	private int counter;
	private int frameCounter;
	private boolean dead;

	public Player(int id) throws IOException {
		life = 3;
		this.setId(id);
		shoots = new ArrayList<>();
		posX = 10;
		posY = 10;
		health = 30;
		skin = new Skin(id);
		counter = 0;
		facing = 3;
		direction = 1;
		frameCounter = 0;
	}

	public void update() {
		
		for (Shoot shoot : shoots) {
			counter += 1;
			shoot.update();
		}

		if (counter == 3000) {
			counter = 0;
		}

	}

	public void draw(Graphics2D screen) throws IOException {

		screen.drawImage(skin.getSkin().getSubimage(frameCounter * 32, 48 * (direction - 1), 32, 48), (int) posX * 16,
				(int) posY * 16, null);

		for (Shoot shoot : shoots) {
			if (shoot.finalize) {
				continue;
			}
			shoot.draw(screen);
			shoot.update();
		}

	}

	public void move(int direction) {

		this.direction = direction;

		frameCounter += 1;

		if (frameCounter == 4) {
			frameCounter = 0;
		}

		double walkingFrame = 0.5;

		switch (direction) {
		// UP
		case 4:
			if (Map.map[(int) (posY - 1) / 2][(int) posX / 2] == 0
					|| Map.map[(int) (posY - 1) / 2][(int) posX / 2] == 5) {
				break;
			}

			posY -= walkingFrame;
			break;
		case 1:
			if (Map.map[(int) (posY + 2) / 2][(int) posX / 2] == 0
					|| Map.map[(int) (posY + 2) / 2][(int) posX / 2] == 5) {
				break;
			}
			posY += walkingFrame;
			break;
		case 2:
			if (Map.map[(int) posY / 2][(int) (posX - 1) / 2] == 0
					|| Map.map[(int) posY / 2][(int) (posX - 1) / 2] == 5) {
				break;
			}
			posX -= walkingFrame;

			break;
		case 3:
			if (Map.map[(int) posY / 2][(int) (posX + 1) / 2] == 0
					|| Map.map[(int) posY / 2][(int) (posX + 1) / 2] == 5) {
				break;
			}
			posX += walkingFrame;

			break;
		case -1:
			posX = -1;
			posY = -1;
			break;
		}

	}

	public void shoot() throws IOException {
		Shoot shoot = new Shoot();
		shoot.init(posX, posY, facing);
		shoots.add(shoot);
	}
	
	public void damage(){
		life -= 1;
		
		if(life == 0){
			die();
		}
	}

	public void die() {
		setDead(true);
		posX = -5;
		posY = -5;
	}

	public void setFacing(int i) {
		facing = i;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
