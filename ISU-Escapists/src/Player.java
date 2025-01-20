import java.awt.Image;

public class Player extends Person {
	private int worldX;
	private int worldY;
	
	private int heat;

	public Player() {
		super("player");
		this.x = 736;
		this.y = 416;
		this.heat = 100;

	}
	
	public boolean isCollision(Map map, boolean up, boolean down, boolean left, boolean right) {
		int arrRow = 0, arrCol = 0;

		if (left) {
			arrRow = (int) Math.round((this.y + map.getY()) / 50);
			arrCol = (int) Math.round((this.x + map.getX() - 40) / 50);
		} else if (right) {
			arrRow = (int) Math.round((this.y + map.getY()) / 50);
			arrCol = (int) Math.round((this.x + map.getX()) / 50);
		} else if (up) {
			arrRow = (int) Math.round((this.y + map.getY()) / 50);
			arrCol = (int) Math.round((this.x + map.getX()) / 50);
		} else if (down) {
			arrRow = (int) Math.round((this.y + map.getY() + 10) / 50);
			arrCol = (int) Math.round((this.x + map.getX()) / 50);
		}
		// System.out.println(arrRow + " " + arrCol);
		if (map.getMapArr()[arrRow][arrCol] == 1) {
			return false;
		} else {
			return true;
		}

	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}

}
