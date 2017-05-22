package entities;

public class Tile {

	private int type;
	public static final int NORMAL = 1;
	public static final int BLOCKED = 0;
	public static final int PLAYER = 2;
	public static final int WALL = 5;

	public Tile(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
