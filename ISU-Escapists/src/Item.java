import java.awt.Image;

public class Item {
	private String itemName;
	private Image itemImage;
	
	public Item (String name, Image image) {
		this.itemName = name;
		this.itemImage = image;
	}

	public String getItemName() {
		return this.itemName;
	}
	
	public Image getItemImage() {
		return this.itemImage;
	}
}
