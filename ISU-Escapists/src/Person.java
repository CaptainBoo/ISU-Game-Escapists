import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

abstract public class Person {

	private String name;
	protected int health, x, y;
	private Rectangle hitbox;
	
	private boolean currentlyPathfinding;
	private int destX, destY;

	protected int frameIndex;

	private Item[] inventory = new Item[6];

	public Person (String name) {
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(40, 90);
		x = 3000;
		y = 2000;
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Item[] getInventory() {
		return inventory;
	}
	
	public int getFrameIndex() {
		return frameIndex;
	}

	public void findRandomPoint(int mapLength, int mapWidth) {
		destX = (int) (Math.random() * mapLength + 1);
		destY = (int) (Math.random() * mapWidth + 1);
		System.out.println("Point is " + destX + " " + destY);
		currentlyPathfinding = true;
	}
<<<<<<< Updated upstream

	public void NPCmovement() {
		if (!currentlyPathfinding) {
			findRandomPoint(1000, 1000);
		}
		
		if (Math.hypot(x-destX, y-destY) < 5) {
			currentlyPathfinding = false;
			return;
		}
		
		if (x < destX) {
			x++;
		}
		else {
			x--;
		}
		if (y < destY) {
			y++;
		}
		else {
			y--;
		}
		
	}
=======
	public void NPCmovement() {
		if (!currentlyPathfinding) {
			findRandomPoint(1000, 1000);
		}
		
		if (Math.hypot(x-destX, y-destY) < 5) {
			currentlyPathfinding = false;
			return;
		}
		
		if (x < destX) {
			x++;
		}
		else {
			x--;
		}
		if (y < destY) {
			y++;
		}
		else {
			y--;
		}
		
	}

//	public ArrayList<int[]> findPath(int[][] grid, int[] start, int[] goal) {
//		int rows = grid.length;
//		int cols = grid.length;
//		
//		Queue <int[]> queue = new LinkedList<>();
//		queue.add(start);
//		
//		while(!queue.isEmpty()) {
//			int[] currentPos = queue.poll();
//		}
//		
//		if (!currentlyPathfinding) {
//			findRandomPoint(1000, 1000);
//		}
//		
//		if (Math.hypot(x-destX, y-destY) < 5) {
//			currentlyPathfinding = false;
//			return ;
//		}
//		
//		if (x < destX) {
//			x++;
//		}
//		else {
//			x--;
//		}
//		if (y < destY) {
//			y++;
//		}
//		else {
//			y--;
//		}
//	}
>>>>>>> Stashed changes
}