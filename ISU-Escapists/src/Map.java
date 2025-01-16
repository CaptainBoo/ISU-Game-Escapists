import java.util.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Map {
	private int xCord;
	private int yCord;
	private Image mapImg;
	
	public Map() {
		BufferedImage image;
		try {
<<<<<<< Updated upstream
			image = ImageIO.read(new File("map6.png"));
=======
			image = ImageIO.read(new File("map7.png"));
>>>>>>> Stashed changes
			int scaledWidth = image.getWidth()*2; // Adjust the scale factor as needed
			int scaledHeight = image.getHeight()*2;
			mapImg = image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);

		}catch (Exception e){
		}
		xCord = 3000;
		yCord = 2000;
	}
	
	public int getX() {
		return this.xCord;
	}
	public int getY() {
		return this.yCord;
	}
	
	public Image getImage() {
		return this.mapImg;
	}
	
	public void move (String dir) {
		if (dir.equals("left")) {
			this.xCord-=8;
		} else if (dir.equals("up")) {
			this.yCord-=8;
		} else if (dir.equals("right")) {
			this.xCord+=8;
		} else if (dir.equals("down")) {
			this.yCord+=8;
		}
	}
}