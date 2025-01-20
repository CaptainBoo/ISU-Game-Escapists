import java.util.*;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Map {
	private int[][] mapArr = new int[121][114];
	private int xCord;
	private int yCord;
	private Image mapImg;

	public Map() {
		setCollisionSpaces();
		BufferedImage image;
		try {
			image = ImageIO.read(new File("images/map7.png"));
			int scaledWidth = image.getWidth() * 2; // Adjust the scale factor as needed
			int scaledHeight = image.getHeight() * 2;
			mapImg = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

		} catch (Exception e) {
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

	public int[][] getMapArr() {
		return this.mapArr;
	}

	public void setCollisionSpaces() {
		for (int i = 0; i < mapArr.length; i++) {
			for (int j = 0; j < mapArr[i].length; j++) {
				mapArr[i][j] = 0;
			}
		}
		for (int i = 64; i <= 92; i++) {
			mapArr[58][i] = 1;
		}
	}

	public void move(String dir) {
		if (dir.equals("left")) {
			this.xCord -= 8;
		} else if (dir.equals("up")) {
			this.yCord -= 8;
		} else if (dir.equals("right")) {
			this.xCord += 8;
		} else if (dir.equals("down")) {
			this.yCord += 8;
		}
	}
}