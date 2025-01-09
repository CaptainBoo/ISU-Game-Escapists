import java.awt.Rectangle;
import java.util.ArrayList;

abstract public class Person {

	private String name;
	private int health, x, y, destX, destY;
	private Rectangle hitbox;
	private boolean currentlyPathfinding;
	private int frameIndex;

	private Item[] inventory = new Item[6];

	public Person (String name) {
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(50, 100);
		this.x = 50;
		this.y = 50;
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

	public void findRandomPoint(int mapLength, int mapWidth) {
		destX = (int) (Math.random() * mapLength + 1);
		destY = (int) (Math.random() * mapWidth + 1);
		System.out.println("Point is " + destX + " " + destY);
		currentlyPathfinding = true;
	}

	public void movement() {
		if (!currentlyPathfinding) {
			findRandomPoint(1000, 1000);
		}
		
		if (x == destX && y == destY) {
			currentlyPathfinding = false;
			return;
		}
		
		if (x < destX) x++;
		else x--;
		if (y < destY) y++;
		else y--;
	}
}
