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

			mapArr[58][i] =1;
		}
		for (int i = 59;i <= 77; i++ ) {
			mapArr[i][64] = 1;
		}
		for (int i = 65;i <=101;i++) {
			mapArr[77][i] = 1;
		}for (int i = 105; i <= 106;i++) {
			mapArr[77][i] = 1;
		}
		for (int i = 34; i <= 112; i++) {
			mapArr[i][92] = 1;
		}
		for (int i = 67; i <= 92;i++) {
			mapArr[67][i] = 1;
		}for (int i = 111; i<= 118;i++) {
			mapArr[i][91] = 1;
		}
		for (int i = 82;i<= 91;i++) {
			mapArr[118][i] = 1;
		}
		for (int i = 111;i<= 118;i++) {
			mapArr[i][82] = 1;
		}for (int i = 101;i<= 111;i++) {
			mapArr[i][79] = 1;
		}
		for (int i = 101; i <=111;i++) {
			mapArr[i][66] = 1;
		}for (int i = 38; i <= 79;i++) {
			mapArr[101][i] = 1;
		}
		for (int i =11; i<= 38;i++) {
			mapArr[111][i] = 1;
		}for (int i = 37;i <=111;i++) {
			mapArr[i][11] = 1;
		}for (int i = 93;i <=100;i++) {
			mapArr[97][i] = 1;
			mapArr[107][i] =1;
		}for (int i = 97; i <=107;i++) {
			mapArr[i][100] = 1;
		}for (int i = 93;i <= 98;i++) {
			mapArr[94][i]=1;	
		}for (int i= 89; i<=94;i++) {
			mapArr[i][98]=1;
		}for (int i = 93;i<= 106;i++) {
			mapArr[89][i] = 1;
		}for (int i =66;i<= 89;i++){
			mapArr[i][106]=1;
		}for (int i =80;i<= 86;i++){
			mapArr[i][102]=1;
		}
		for (int i = 93; i <= 102; i++) {
			mapArr[80][i] = 1;
		}
		for (int i = 63; i <= 66; i++) {
			mapArr[i][105] = 1;
		}for (int i = 54; i <= 63; i++) {
			mapArr[i][107]=1;
		}
		for (int i = 100; i <= 107; i++) {
			mapArr[54][i] = 1;
		}for (int i = 54; i <= 63; i++) {
			mapArr[i][100] = 1;
		}for (int i = 100; i <= 102; i++) {
			mapArr[63][i] = 1;
		}for (int i = 63; i <= 67; i++) {
			mapArr[i][102] = 1;
		}for (int i = 95; i <= 102; i++) {
			mapArr[67][i] = 1;
		}for (int i = 66; i <= 76; i++) {
			mapArr[i][95] = 1;
		}for (int i = 18; i <= 34; i++) {
			mapArr[i][93]= 1;
		}for (int i = 93; i <= 112; i++) {
			mapArr[19][i] = 1;
		}for (int i = 19; i <= 32; i++) {
			mapArr[i][112] =1;
		}for (int i = 105; i<=112; i++) {
			mapArr[25][i] = 1;
		}for (int i = 18; i<= 25;i++ ) {
			mapArr[i][105] =1;
		}for (int i = 93; i<= 111; i++) {
			mapArr[32][i] =1;
		}for (int i = 22; i<=93; i++) {
			mapArr[18][i] = 1;
		}
		for (int i = 53;i <= 81; i++)
		{
			mapArr[7][i] = 1;
		}for (int i = 7; i <=18; i++) {
			mapArr[i][68] = 1;
			mapArr[i][80] = 1;
		}for (int i = 68; i <= 81; i++) {
			mapArr[13][i] = 1;
		}for (int i = 19; i <= 33; i++) {
			mapArr[i][53] = 1;
		}for (int i = 26; i <= 37;i++) {
			mapArr[i][38] = 1;
		}for (int i = 11; i <= 38; i++) {
			mapArr[37][i] = 1;
		}
		for (int i = 19; i <= 73; i++) {
			mapArr[i][22] =1;
		}for (int i = 11; i <= 22; i++) {
			mapArr[73][i] = 1;
		}for (int i = 1; i <= 11; i++) {
			mapArr[72][i] = 1;
		}for (int i = 72; i <= 96; i++) {
			mapArr[i][1] =1;
		}for (int i = 1; i <= 30; i++) {
			mapArr[96][i] = 1;
		}for (int i = 11; i <= 30; i++) {
			mapArr[103][i] = 1;
		}for (int i = 96; i <= 103; i++) {
			mapArr[i][25] = 1;
			mapArr[i][30] = 1;
			mapArr[i][20] = 1;
			mapArr[i][15] = 1;
		}
		for (int i = 100; i<= 111; i++) {
			mapArr[i][38] = 1;	
		}for (int i = 42; i <= 70; i++) {
			mapArr[i][16] =1;
			mapArr[i][17] = 1;
		}for (int i = 36; i<= 52;i ++) {
			mapArr[i][63] =1;
		}
		mapArr[49][63] = 0;
		mapArr[44][63] = 0;
		mapArr[40][63] = 0;
		for (int i = 34; i<= 52; i++) {
			mapArr[i][71] =1;
			mapArr[i][78] =1;
			mapArr[i][85] =1;
		}for (int i = 64; i <= 92; i++) {
			mapArr[43][i] = 1;
			mapArr[53][i] = 1;
		}
		for (int i = 2;i <= 10;i++) {
			mapArr[82][i] =1;
			mapArr[83][i] =1;
		}
		for (int i = 34; i <=51 ;i++) {
			mapArr[i][64] = 1;
		}
		for (int i = 65; i <= 91; i++) {
			mapArr[34][i] = 1;
		}
		
		for (int i = 19; i <= 24; i++) {
			mapArr[i][38] = 1;
		}for (int i = 39; i <= 52; i++) {
			mapArr[33][i] = 1;
		}
		//Turning the doors into 0s
		
		//key:
		/*
		0 is free
		1 is collision
		2 is contraband door
		3 is red door
		4 is gray door
		5 is escape door
		6 is contraband detector
		7 is that one nurse door
		8 are the cell doors
		9 are the drawers
		//Dont ask why theres doors at 82 8
		*/
		mapArr[91][92] = 2;
		mapArr[88][92] = 3;
		mapArr[87][92] = 3;
		mapArr[102][92] = 3;
		mapArr[102][100] = 3;
		mapArr[111][86] = 3;
		mapArr[105][66] = 4;
		mapArr[106][66] = 4;
		mapArr[101][54] = 5;
		mapArr[101][53] = 5;
		mapArr[108][30] = 6;
		mapArr[92][30] = 6;
		mapArr[91][30] = 6;
		mapArr[80][11] = 3;
		mapArr[79][11] = 3;
		mapArr[73][16] = 4;
		mapArr[73][17] = 4;
		mapArr[39][22] = 4;
		mapArr[38][22]	 = 4;
		mapArr[37][30] = 4;
		mapArr[37][29] = 4;
		mapArr[33][45] = 6;
		mapArr[33][46] = 6;
		mapArr[24][53] = 6;
		mapArr[25][53] = 6;
		mapArr[18][63] = 4;
		mapArr[18][71] = 4;
		mapArr[18][72] = 4;
		mapArr[24][92] = 6;
		mapArr[25][92] = 6;
		mapArr[26][92] = 6;
		mapArr[24][93] = 4;
		mapArr[25][93] = 4;
		mapArr[26][93] = 4;
		mapArr[25][108] = 7;
		mapArr[52][68] = 8;
		mapArr[52][75] = 8;
		mapArr[52][82] = 8;
		mapArr[52][88] = 8;
		mapArr[58][68] = 8;
		mapArr[58][75] = 8;
		mapArr[58][82] = 8;
		mapArr[58][88] = 8;
		mapArr[53][68] = 8;
		mapArr[53][75] = 8;
		mapArr[53][82] = 8;
		mapArr[53][88] = 8;
		mapArr[34][68] = 8;
		mapArr[34][75] = 8;
		mapArr[34][82] = 8;
		mapArr[34][88] = 8;
		mapArr[76][68] = 8;
		mapArr[76][75] = 8;
		mapArr[76][82] = 8;
		mapArr[76][88] = 8;
		//Adding drawers
		mapArr[49][72] = 9;
		mapArr[49][65] = 9;
		mapArr[49][79] = 9;
		mapArr[49][86] = 9; 
		mapArr[37][70] = 9;
		mapArr[37][77] = 9;	
		mapArr[37][84] = 9;
		mapArr[37][91] = 9;
		mapArr[61][72] = 9;
		mapArr[61][65] = 9;
		mapArr[61][79] = 9;
		mapArr[61][86] = 9; 
		mapArr[73][70] = 9;
		mapArr[73][77] = 9;	
		mapArr[73][84] = 9;
		mapArr[73][91] = 9;
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