import java.awt.Image;

public class Player extends Person {
	private int worldX;
	private int worldY;
	
	public Player() {
		super("player");
		//this.x = 3740;
		//this.y = 2416;
		this.x = 736;
		this.y = 416;
		//g.drawImage(playerFrames[0], this.x-map.getX(), this.y-map.getY(),40,90, this);

	}

	
	public boolean isCollision(Map map,boolean up, boolean down, boolean left, boolean right) {
		int arrRow=0,arrCol=0;
		
		if (left) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX()-40)/50);
		}else if (right) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX())/50);
		}else if (up) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX())/50);
		}else if (down) {
			arrRow = (int)Math.round((this.y+map.getY()+10)/50);
			arrCol = (int)Math.round((this.x+map.getX())/50);
		}
		//System.out.println(arrRow + " " + arrCol);
		if (map.getMapArr()[arrRow][arrCol] == 1) {
			return false;
		}
		else{
			return true;
		}

	}

	
}
