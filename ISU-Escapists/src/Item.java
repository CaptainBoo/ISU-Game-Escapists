import java.awt.Image;

public class Item {
	private String itemName;
	private Image itemImage;
	
	public Item (String name) {
		this.itemName = name;
	}

	public String getItemName() {
		return this.itemName;
	}
	
	public Image getItemImage() {
		return this.itemImage;
	}
}
