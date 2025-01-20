import java.awt.*;
import java.util.*;

abstract public class Person {
	private String name;
	protected int health, x, y;
	private Rectangle hitbox;
	
	private boolean currentlyPathfinding;
	private int destX, destY;
	protected Image[] playerFrames;
	protected int frameIndex;
	protected int characterFrame;
	
	public Person (String name, Image[] playerFrames) {
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(40, 90);
		x = 3000;
		y = 2000;
		this.playerFrames = playerFrames;
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

	
	public int getFrameIndex() {
		return frameIndex;
	}

	public void findRandomPoint(int mapLength, int mapWidth) {
		destX = (int) (Math.random() * mapLength + 1);
		destY = (int) (Math.random() * mapWidth + 1);
		System.out.println("Point is " + destX + " " + destY);
		currentlyPathfinding = true;
	}

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
	
	public Image[] getPlayerFrames() {
		return this.playerFrames;
	}
	
	public int getCharacterFrame() {
		return this.characterFrame;
	}
	
	public void setCharacterFrame(int increment) {
		this.characterFrame+= increment;
	}
}