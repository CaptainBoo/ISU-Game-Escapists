import java.awt.Image;

public class Item implements Comparable<Item> {
	private String itemName;
	private Image itemImage;
	private boolean isContraband;

	public Item(String name, Image image,boolean isContraband) {
		this.itemName = name;
		this.itemImage = image;
		this.isContraband = isContraband;
	}

	public String getItemName() {
		return this.itemName;
	}

	public Image getItemImage() {
		return this.itemImage;
	}

	public int compareTo(Item o) {
		return this.itemName.compareToIgnoreCase(o.itemName);
	}
	
	public String toString() {
		return this.itemName;
	}

	public boolean isContraband() {
		return isContraband;
	}
}
