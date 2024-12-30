import java.util.ArrayList;

abstract public class NPC {
	private String name;
	private int opinion;
	private int health;
	
	ArrayList<Item> Inventory = new ArrayList<>(5);
	
	public NPC (String name) {
		this.setName(name);
		setOpinion(40);
		setHealth(100);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpinion() {
		return opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
