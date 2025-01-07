import java.awt.Rectangle;
import java.util.ArrayList;

abstract public class Person {
	
	private String name;
	private int health, x, y;
	private Rectangle hitbox;
	
	private Item[] inventory = new Item[5];
	
	public Person (String name) {
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(10, 10);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public Item[] getInventory() {
		return this.inventory;
	}
}
