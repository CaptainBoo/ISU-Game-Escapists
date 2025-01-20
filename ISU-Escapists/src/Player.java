import java.awt.Image;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.LinkedList;

public class Player extends Person {
	private int worldX;
	private int worldY;
	private int heat;
	private LinkedList<Item> inventory = new LinkedList<>();
	//private int frame = 0;
	public Player(Image[] playerFrames) {
		super("player",playerFrames);
		//this.x = 3740;
		//this.y = 2416;
		this.x = 736;
		this.y = 416;
		//g.drawImage(playerFrames[0], this.x-map.getX(), this.y-map.getY(),40,90, this);

	}


	public LinkedList<Item> getInventory() {
		return inventory;
	}

	public boolean handleCollisions(Map map, int arrRow, int arrCol) {
		LinkedList<Item> sortList = new LinkedList<>(inventory);
		Collections.sort(sortList);
		if (map.getMapArr()[arrRow][arrCol] == 1) {
			return false;
		}else if (map.getMapArr()[arrRow][arrCol] == 3) {
			if (!(Collections.binarySearch(sortList, new Item("red_key",Toolkit.getDefaultToolkit().getImage("red_key.png"),false)) >0)){
				return false;
			};
		}else if (map.getMapArr()[arrRow][arrCol] == 2) {

			if (!(Collections.binarySearch(sortList, new Item("guard_outfit",Toolkit.getDefaultToolkit().getImage("red_key.png"),false)) >=0)){

				return false;
			};
		}else if (map.getMapArr()[arrRow][arrCol] == 6) {
			if(Collections.binarySearch(sortList, new Item("contraband_pouch",Toolkit.getDefaultToolkit().getImage("red_key.png"),false)) <0) {
				Collections.sort(sortList,new SortItemByContraband());
				if (Collections.binarySearch(sortList, new Item("guard_outfit",Toolkit.getDefaultToolkit().getImage("red_key.png"),true))>=0){
					setHeat(100);					
				}

			}
		}
		else if (map.getMapArr()[arrRow][arrCol] == 7) {
			if (!(Collections.binarySearch(sortList, new Item("medic_outfit",Toolkit.getDefaultToolkit().getImage("red_key.png"),false)) >=0)){
				return false;
			};
		}else if (map.getMapArr()[arrRow][arrCol] == 5) {
			if (!(Collections.binarySearch(sortList, new Item("cyan_key",Toolkit.getDefaultToolkit().getImage("red_key.png"),false)) >=0)){
				return false;
			};
		}
		return true;
	}
	public boolean isCollision(Map map,boolean up, boolean down, boolean left, boolean right) {
		int arrRow=0,arrCol=0;

		if (left) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX()-35)/50);
			if(!handleCollisions(map,arrRow,arrCol)) {
				return false;
			}
		}if (right) {
			arrRow = (int)Math.round((this.y+map.getY())/50);
			arrCol = (int)Math.round((this.x+map.getX())/50);
			if(!handleCollisions(map,arrRow,arrCol)) {
				return false;
			}
		}if (up) {
			arrRow = (int)Math.round((this.y+map.getY()-5)/50);
			arrCol = (int)Math.round((this.x+map.getX()-10)/50);
			if(!handleCollisions(map,arrRow,arrCol)) {
				return false;
			}

		}if (down) {
			arrRow = (int)Math.round((this.y+map.getY()+10)/50);
			arrCol = (int)Math.round((this.x+map.getX()-10)/50);
			if(!handleCollisions(map,arrRow,arrCol)) {
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
