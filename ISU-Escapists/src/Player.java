import java.awt.Image;

public class Player extends Person {
	private int worldX;
	private int worldY;
	
	private int heat;
<<<<<<< Updated upstream

	public Player() {
		super("player");
=======
	private LinkedList<Item> inventory = new LinkedList<>();
	//private int frame = 0;
	
	//Constructor for player class, super set up our character frames for animation
	//Parameters: Image[] playerFrames
	//Return: None
	public Player(Image[] playerFrames) {
		super("player",playerFrames);
		//this.x = 3740;
		//this.y = 2416;
>>>>>>> Stashed changes
		this.x = 736;
		this.y = 416;
		this.heat = 100;

	}
<<<<<<< Updated upstream
	
	public boolean isCollision(Map map, boolean up, boolean down, boolean left, boolean right) {
		int arrRow = 0, arrCol = 0;
=======


	public LinkedList<Item> getInventory() {
		return inventory;
	}

	//This method checks if there are collisions occuring that restrict our movements with requirements
	//IF requirements are met you can pass.
	//Parameters(Map map, int arrRow, int arrCol) row and col are the square you are GOING to be on.
	//Return boolean (yes/no) collision
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
				if (Collections.binarySearch(sortList, new Item("guard_outfit",Toolkit.getDefaultToolkit().getImage("red_key.png"),true),new SortItemByContraband())>=0){
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
	
	//This method is combed with handleCollision. This method checks what square you are going to be on depending on the direction
	//you are moving at.
	//Parameters: Map map, boolean up, boolean down, boolean left, boolean right. Checks what directions are true
	public boolean isCollision(Map map,boolean up, boolean down, boolean left, boolean right) {
		int arrRow=0,arrCol=0;
>>>>>>> Stashed changes

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
