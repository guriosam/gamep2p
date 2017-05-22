package entities;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Skin;

public class HUD {

	private List<Skin> skins;

	public HUD() throws IOException {

		skins = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			
		}
	}

	public void draw(Graphics2D j) {
		// TODO Auto-generated method stub
		// vida.draw(j, jogador.getHealth());
		// munição.draw(j, jogador.getTiros());

	}

}
