package entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import utils.Values;

public class Arena extends JPanel implements Runnable, KeyListener {

	private Thread thread;
	private boolean running;
	private long targetTime = 1000 / Values.FPS;

	private BufferedImage imagem;
	private Graphics2D screen;

	private int counter;

	private Map map;

	private HashMap<Integer, Player> players;

	private int playerID;

	public Arena() throws IOException {
		super();
		players = new HashMap<>();
		setPreferredSize(new Dimension(Values.HEIGHT, Values.WIDTH));
		setFocusable(true);
		requestFocus();
		addNewPlayer(0);
		counter = 0;
	}

	// Método a ser chamado ao receber comando via rede
	// Adicionar novo player ao jogo
	public void addNewPlayer(int id) throws IOException {
		Player p = new Player(id);
		players.put(id, p);
	}

	// Método para ser chamado ao receber comando via rede
	// Mover o player e mostrar para todos os usuários
	public void move(int id, int direction) {
		players.get(id).move(direction);
	}

	// Método para ser chamado ao receber comando via rede
	// Criar tiro e mostrar pra todos os usuários
	public void shoot(int id) throws IOException {
		players.get(id).shoot();
	}

	public void die(int id) throws IOException {
		// Notify other players and remove from server
	}

	@SuppressWarnings("unused")
	public void run() {

		long start = 0;
		long passed;
		long wait;

		try {
			init();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		while (running) {

			List<Player> deadPlayers = new ArrayList<>();
			List<Shoot> removeShoot = new ArrayList<>();

			update();
			try {
				for (Integer id : players.keySet()) {
					Player p = players.get(id);
					p.update();
					p.draw(screen);

					for (Shoot s : p.shoots) {
						for (Integer id2 : players.keySet()) {
							if (s.posX >= players.get(id2).posX && s.posX <= (players.get(id2).posX + 32)) {
								if (players.get(id2) != p) {
									players.get(id2).damage();
									removeShoot.add(s);
								}

							}

							if (s.posY >= players.get(id2).posY && s.posY <= (players.get(id2).posY + 32)) {
								if (players.get(id2) != p) {
									players.get(id2).damage();
									removeShoot.add(s);
								}
							}

							if (players.get(id2).isDead()) {
								deadPlayers.add(players.get(id2));
							}
						}
					}

					for (Shoot s : removeShoot) {
						if (p.shoots.contains(s)) {
							p.shoots.remove(s);
						}
					}
				}

				for (Player p : deadPlayers) {
					die(p.getId());

					players.remove(p.getId());
				}

				drawScreen();
				map.drawMap(screen);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			passed = System.nanoTime() - start;
			wait = targetTime;

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init() throws IOException {
		imagem = new BufferedImage(Values.HEIGHT, Values.WIDTH, BufferedImage.TYPE_INT_RGB);
		screen = (Graphics2D) imagem.getGraphics();
		running = true;
		map = new Map();
	}

	public void update() {
		counter += 1;

	}

	public void drawScreen() throws IOException {
		Graphics j2 = getGraphics();
		j2.drawImage(imagem, 0, 0, null);
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();

		if (k == KeyEvent.VK_LEFT) {
			move(0, 2);
			players.get(0).setFacing(2);
		}
		if (k == KeyEvent.VK_RIGHT) {
			players.get(0).setFacing(3);
			move(0, 3);

		}
		if (k == KeyEvent.VK_UP) {
			players.get(0).setFacing(4);
			move(0, 4);
		}
		if (k == KeyEvent.VK_DOWN) {
			players.get(0).setFacing(1);
			move(0, 1);
		}
		if (k == KeyEvent.VK_SPACE) {
			try {
				if (counter > 30) {
					counter = 0;
					shoot(0);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		if (players.size() > 0) {

			if (k == KeyEvent.VK_P) {
				try {
					addNewPlayer(1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (k == KeyEvent.VK_A) {
				move(1, 2);
				players.get(1).setFacing(2);
			}
			if (k == KeyEvent.VK_D) {
				players.get(1).setFacing(3);
				move(1, 3);

			}
			if (k == KeyEvent.VK_W) {
				players.get(1).setFacing(4);
				move(1, 4);
			}
			if (k == KeyEvent.VK_S) {
				players.get(1).setFacing(1);
				move(1, 1);
			}

			if (k == KeyEvent.VK_SHIFT) {
				try {
					if (counter > 30) {
						counter = 0;
						shoot(1);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
