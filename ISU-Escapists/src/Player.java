import java.awt.Image;

public class Player extends Person {
	private int worldX;
	private int worldY;
	private int heat;
	private Item[] inventory = new Item[6];
	//private int frame = 0;
	public Player(Image[] playerFrames) {
		super("player",playerFrames);
		//this.x = 3740;
		//this.y = 2416;
		this.x = 736;
		this.y = 416;
		//g.drawImage(playerFrames[0], this.x-map.getX(), this.y-map.getY(),40,90, this);

	}


	public Item[] getInventory() {
		return inventory;
	}
	public boolean isCollision(Map map,boolean up, boolean down, boolean left, boolean right) {
		int arrRow=0,arrCol=0;

		if (left) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX()-35)/50);
			if (map.getMapArr()[arrRow][arrCol] == 1) {
				return false;
			}
		}if (right) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX())/50);
			if (map.getMapArr()[arrRow][arrCol] == 1) {
				return false;
			}
		}if (up) {
			arrRow = (int)Math.round((this.y+map.getY()-5)/50);
			arrCol = (int)Math.round((this.x+map.getX()-10)/50);
			if (map.getMapArr()[arrRow][arrCol] == 1) {
				return false;
			}

		}if (down) {
			arrRow = (int)Math.round((this.y+map.getY()+10)/50);
			arrCol = (int)Math.round((this.x+map.getX()-10)/50);
			if (map.getMapArr()[arrRow][arrCol] == 1) {
				return false;
			}
		}
		//System.out.println(arrRow + " " + arrCol);
		return true;
	}
	
	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}
	
}
