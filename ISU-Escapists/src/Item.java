import java.awt.Image;

public class Item implements Comparable<Item> {
	private String itemName;
	private Image itemImage;
	private boolean isContraband;

<<<<<<< Updated upstream
	public Item(String name, Image image) {
=======
	//Construtor for item class. This is where we initilize our variables for our items.
	//Parameters: String name, Image image, boolean isContraband. All necceasary variables for the game.
	//Return: None
	public Item(String name, Image image,boolean isContraband) {
>>>>>>> Stashed changes
		this.itemName = name;
		this.itemImage = image;
	}

	public String getItemName() {
		return this.itemName;
	}

	public Image getItemImage() {
		return this.itemImage;
	}

	//This overridden method sorts the items by NAME in alphabetical order. 
	//This is the natural sort order.
	//Parameter: Item o
	//Return: int
	public int compareTo(Item o) {
		return this.itemName.compareToIgnoreCase(o.itemName);
	}
<<<<<<< Updated upstream
=======
	//This overrideen message returns our item's name so that I can safely print 
	//The item with std.out.prt for debugging.
	//Parameters: None
	//Return: String
	public String toString() {
		return this.itemName;
	}
>>>>>>> Stashed changes

	//Basically a getter
	public boolean isContraband() {
		return isContraband;
	}
}
