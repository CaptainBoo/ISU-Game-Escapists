package StardewValley.src;

//Estar Guan
//ISU Game
//Monday January 22, 2024
//This is my code for the ISU Game Assignment



import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class StardewValley extends JPanel implements Runnable, KeyListener,MouseListener{
	//To do. Get that parsnip collecting working very important. I just wrote almost 400 lines of code in one day. 
	//Added the different frames for tools, seeds, plant frames, growing system, watering system, inventory system.
	//At this pace we could get the shop system going tmrw!!!!
	//Global variables

	
	//Default Variables
	static JFrame frame;
	Player player;
	int[][] map = new int[24][42],plantTimes = new int[24][42];
	Thread thread;
	int FPS = 60;
	int characterFrame = 0;
	int frameCount = 0;
	Clip backgroundMusic;
	Graphics offScreenBuffer;

	//Dimensions
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	int speed = screenWidth/42/(60/8);
	
	//Booleans
	boolean walking, walkingChange, cords,playerFront = true,
			showInventory = true,rightClickKey,useItem,waitTime,twoDirectionLeft,twoDirectionRight,waterWait1,waterWait2,
			drawAddParsnip,drawAddGold, shopping,onParsnip,showInstructions;
	
	//Inventory variables
	int waterWidth = 42,waterBarX, waterBarY;
	Integer parsnipSeedAmt = 5,beanStarterAmt = 0;
	int parsnipSeedX,parsnipSeedY,beanStarterX, beanStarterY;
	Integer parsnipAmt = 0;
	Integer beanAmt = 0;
	int parsnipX,parsnipY,beanX,beanY;
	int parsnipAddFrame,goldAddFrame;
	String inventory[] = new String[12];
	
	//Directions
	String dir = "";
	String[] directions = {"down", "left","up","right"};
	String selectedItem ="";
	boolean left,right,up,down;
	
	//Tree variables
	int treeImageFrame = 0;
	int treeRemoveRow;
	int treeRemoveCol;
	//This is to count how many times you have swung at a tree
	int treeHitCount = 0;

	//CharacterFrameAnimations
	Image [] characterFramesInHouse = new Image[16];
	Image [] characterFramesOutside = new Image[16];

	//Screen images
	String screen = "homescreen";
	Image offScreenImage,house, outside,homescreen,shopScreen1,shopScreen2, instructions;

	//Object animations
	Image[] axeFrames = new Image[4];
	Image[] waterCanFrames = new Image[4];
	Image[] scytheFrames = new Image[4];
	Image[] hoeFrames = new Image[4];
	Image[] pickaxeFrames = new Image[4];
	Image[] parsnipFrames = new Image[5];
	Image[] beanStarterFrames = new Image[8];


	//Object images
	Image rock;
	Image[] tree = new Image[3];
	Image[] wood = new Image[4];
	Image dryPlantHole, wetPlantHole, parsnipSeed,parsnip, beanStarter,bean;

	//Inventory images
	Image axe, hoe,pickaxe,scythe,waterCan,waterBar;	
	Image [] inventories = new Image[13];

	//Other Images
	Image startButton;
	Image parsnipPickUp;
	Image addGold;
	Image goldImage;
	Image[] numbers = new Image[10];
	int[] moneyCordinates = {103,126,147,168,189,210,231,253};
	Image cursorImage,interactCursorImage,selectCursorImage;
	
	//Other Variables
	int outdoorScreenX = -157;
	int outdoorScreenY = -20;
	int curFrameCount, treeFrameCount;
	int inventoryNumber = 0;
	Integer gold = 1000;
	int waterWaitFrames1,waterRow1,waterCol1,waterRow2,waterCol2,waterWaitFrames2;
	int shoppingIdx = 1;

	//Constructor
	public StardewValley(){

		//Make the map
		mapInit();


		//Make the inventory
		inventoryInit();

		//Make player object
		player=  new Player(800,450);


		//Screen size
		setPreferredSize(screenSize);

		//Images
		images();

		//Cursor
		setCursor("Default");

		//Audio
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("backgroundMusic.wav"));
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(sound);

			FloatControl gainControl = 
					(FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}catch (Exception e) {
		}

		//Start thread
		thread = new Thread(this);
		thread.start();

	}

	//Description: Changes the cursor according to what cursor should be shown at what time
	//Parameters: The name of the cursor
	//Return: Void
	public void setCursor(String cursorName) {
		Toolkit toolkit = Toolkit.getDefaultToolkit ();
		Point hotspot = new Point (0, 0);
		if (cursorName.equals("Default")) {
			Cursor cursor1 = toolkit.createCustomCursor (cursorImage, hotspot, "defaultCursor");
			frame.setCursor(cursor1);
		}else if(cursorName.equals("Interact")) {
			Cursor cursor2 = toolkit.createCustomCursor(interactCursorImage, hotspot, "interactCursor");
			frame.setCursor(cursor2);
		}else if (cursorName.equals("Select")) {
			Cursor cursor3 =toolkit.createCustomCursor(selectCursorImage, hotspot, "selectCursor");
			frame.setCursor(cursor3);
		}
	}

	//Description: Initialize the map outside of the house
	//Parameters: None
	//Return: Void
	public void mapInit() {
		//Map initalizing

		//Trees
		map[8][10] = 1;
		map[10][8] = 1;
		map[3][30] = 1;
		map[14][20] = 1;
		map[17][9] = 1;
		map[20][23] =1;
		map[9][38] = 1;
		map[16][40] = 1;
		map[22][10] = 1;
		map[18][26] = 1;
		map[10][13] = 1;
		map[19][32] = 1;
		map[15][17] = 1;
		map[17][29] = 1;
		map[21][18] = 1;
		map[16][3] = 1;
		map[11][3] = 1;
		map[12][33] = 1;
		map[6][16] = 1;
		map[21][1] = 1;

		//Rocks
		map[8][14] = 3;
		map[14][21] = 3;
		map[13][12] = 3;
		map[4][7] = 3;
		map[3][20] = 3;
		map[3][29] = 3;
		map[3][21] = 3; 
		map[17][7] = 3;
		map[6][19] = 3;
		map[13][24] = 3;
		map[16][14] = 3;
		map[12][27] = 3;
		map[12][17] = 3;
		map[12][30] = 3;
		map[13][36] = 3;
		map[13][22] = 3;
		map[10][17] = 3;
		map[12][14] = 3;
		map[11][22] = 3;
		map[21][27] = 3;
		map[4][13] = 3;
		map[13][25] = 3;
		map[14][32] = 3;

		//Wood
		map[3][1] = 4;
		map[10][1] = 5;
		map[10][12] =6;
		map[15][23] = 7;
		map[12][11] = 4;
		map[4][5] = 5;
		map[9][12] = 6;
		map[18][12] = 7;
		map[6][10] = 4;
		map[13][26] = 5;
		map[16][20] = 6;
		map[13][30] = 7;
		map[13][35] = 4;
		map[19][5] = 5;
		map[12][5] = 6;
		map[2][13] = 7;
		map[15][30] = 4;
		map[15][28] = 5;
		map[7][17] = 6;
		map[9][19] = 7;
		map[22][24] = 4;
		map[19][30] = 5;
		map[16][35] = 6;	
		map[21][14] = 7;
		map[13][23] = 4;
		map[15][14] = 5;
		map[12][24] = 6;
		map[11][29] = 7;
		
		//Leaves for isPlayerInFrontMapObject()
		createLeaves(map);
	}

	//Description: Above each and every tree there are multiple spaces 
	//that are leaves that you need to be able to stand behind
	//Parameters: The map array with all the objects
	//Return: void
	public void createLeaves(int[][] map) {

		for (int i = 0; i < map.length;i++) {
			for (int j = 0; j < map[i].length;j++) {
				if (map[i][j]!= 1) {
					continue;
				}
				try {
					if (map[i-1][j] != 1) {
						map[i-1][j] = 2;
						map[i-1][j-1] = 2;
						map[i-1][j+1] = 2;
						map[i-2][j] = 2;
						map[i-2][j+1] = 2;
						map[i-2][j-1] = 2;
						map[i-3][j] = 2;
						map[i-3][j+1] = 2;
						map[i-3][j-1] = 2; 
						map[i-4][j] = 2;
						map[i-4][j+1] = 2;
						map[i-4][j-1] =2;
					}
				}catch (Exception e) {
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (offScreenBuffer == null)
		{
			offScreenImage = createImage (this.getWidth (), this.getHeight ());
			offScreenBuffer = offScreenImage.getGraphics ();
		}
		if (screen.equals("homescreen")){
			offScreenBuffer.drawImage(homescreen,0,0,screenWidth,screenHeight,this);
			offScreenBuffer.drawImage(startButton, screenWidth/2-75,screenHeight/2+100,this);
		}
		else if (screen.equals("house")) {

			
			offScreenBuffer.setColor(Color.BLACK);
			offScreenBuffer.fillRect(0, 0, getWidth(), getHeight());

			offScreenBuffer.drawImage(house, screenWidth/2-350, screenHeight/2-350,700,700, this);
			offScreenBuffer.drawImage(characterFramesInHouse[characterFrame],player.x,player.y,this);

			offScreenBuffer.drawImage(inventories[inventoryNumber],screenWidth/2-350,20,700,100,this);
			offScreenBuffer.drawImage(goldImage,screenWidth-300,10,this);
			drawGold();
			
			if (showInstructions) {
				offScreenBuffer.drawImage(instructions,screenWidth/2-400,screenHeight/2-300,this);
			}

		}else if (screen.equals("outside")){

			offScreenBuffer.clearRect(0,0,getWidth(),getHeight());
			offScreenBuffer.drawImage(outside,outdoorScreenX,outdoorScreenY,this);
			if (playerFront) {
				//Draw objects before person so person in front
				drawMap();

				if (useItem && !shopping) {
					drawActions();
				}else {
					offScreenBuffer.drawImage(characterFramesOutside[characterFrame],player.x,player.y,this);
				}
			}else{ 		//Draw person first so objects in front
				if (useItem && !shopping) {
					drawActions();
				}else {
					offScreenBuffer.drawImage(characterFramesOutside[characterFrame],player.x,player.y,this);					
				}
				drawMap();

			}


			if (showInventory) {
				offScreenBuffer.drawImage(inventories[inventoryNumber],screenWidth/2-350,700,700,100,this);

				//Draw each inventory item
				offScreenBuffer.drawImage(getInventoryImage(0,396,720),396,720,this);
				offScreenBuffer.drawImage(getInventoryImage(1,455,720),455,720,this);
				offScreenBuffer.drawImage(getInventoryImage(2,515,720),515,720,this);
				offScreenBuffer.drawImage(getInventoryImage(3,570,720),570,720,this);
				offScreenBuffer.drawImage(getInventoryImage(4,625,720),625,720,this);
				offScreenBuffer.drawImage(getInventoryImage(5,680,720),680,720,this);
				offScreenBuffer.drawImage(getInventoryImage(6,735,720),735,720,this);
				offScreenBuffer.drawImage(getInventoryImage(7,792,720),792,720,this);
				offScreenBuffer.drawImage(getInventoryImage(8,850,720),850,720,this);

				//Draw the Water bar
				offScreenBuffer.setColor(new Color(98,163,217));
				offScreenBuffer.drawImage(waterBar,waterBarX,waterBarY,this);
				offScreenBuffer.fillRect(waterBarX+5,waterBarY+15,waterWidth,8);

				//Draw the Parsnip Amounts
				offScreenBuffer.setColor(Color.BLACK);
				offScreenBuffer.setFont(new Font("default", Font.BOLD, 12));
				offScreenBuffer.drawString(parsnipSeedAmt.toString(),parsnipSeedX,parsnipSeedY);
				offScreenBuffer.drawString(parsnipAmt.toString(),parsnipX,parsnipY);
				offScreenBuffer.drawString(beanStarterAmt.toString(),beanStarterX,beanStarterY);
				offScreenBuffer.drawString(beanAmt.toString(),beanX,beanY);
			}
			
			//Draw the gold
			offScreenBuffer.drawImage(goldImage,screenWidth-300,10,this);
			drawGold();
		}
		//Draw the shop UI
		if (shopping && !screen.equals("homescreen")) {
			if (shoppingIdx == 1) {
				offScreenBuffer.drawImage(shopScreen1,screenWidth/2-400,screenHeight/2-300,this);				
			}else {
				offScreenBuffer.drawImage(shopScreen2,screenWidth/2-400,screenHeight/2-300,this);
			}
		}
		
		g.drawImage(offScreenImage, 0, 0, this);
	}

	public static void main(String[] args) {
		System.out.println(screenWidth);
		//The following lines create your window

		//makes a brand new JFrame
		frame = new JFrame ("Example");
		//makes a new copy of your "game" that is also a JPanel
		StardewValley myPanel = new StardewValley ();
		//so your JPanel to the frame so you can actually see it
		frame.add(myPanel);
		//so you can actually get keyboard input
		frame.setFocusable(true);
		frame.addKeyListener(myPanel);
		//so you can actually get mouse input
		frame.addMouseListener(myPanel);
		//self explanatory. You want to see your frame
		frame.setVisible(true);
		//some weird method that you must run
		frame.pack();
		//place your frame in the middle of the screen
		frame.setLocationRelativeTo(null);
		//without this, your thread will keep running even when you windows is closed!
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//self explanatory. You don't want to resize your window because
		//it might mess up your graphics and collisions
		frame.setResizable(false);

	}

	//Description: Keeps the player from going out of bounds when in the house. Also when outside checks if my cursor
	//is on the door to check if going inside.     This code is technically the same as isCollision
	//except I wrote it before isCollision and didn't want to move it there
	//Parameters: None
	//Return: Void
	public void keepInBounds() {
		if (screen.equals("house")) {
			if (player.x < 415) {
				player.x = 415;
			}if (player.x > 985) {
				player.x = 985;
			}
			if (player.y < 360 && (player.x < 800 && player.x > 775)) {
				player.x = 800;
			}else if (player.y < 360 && (player.x > 600 && player.x < 650)) {
				player.x = 600;
			}else if (player.y < 360 && (player.x < 535 && player.x > 520)){
				player.x = 535;
			}
			else if (player.y < 360 && ((player.x >=415 && player.x <=500) || (player.x)>= 650 && player.x < 760)) {
				player.y = 360;
			}if (player.y > 624 && (player.x < 520 || player.x > 560)) {
				player.y = 624;
			}
			if (player.y < 260) {
				player.y = 260;
			}
			if (player.y >= 690) {
				player.x = 914;
				player.y = 266;
				screen = "outside";
			}
			//Going back inside the house
		}else {
			//For the outside I continue to use the array because I like it more.
			int arrRow = (int)Math.round((player.y)/35.0)+1;
			int arrCol = (int)Math.round((player.x)/35.0);
			//System.out.println(arrRow + " " + arrCol) ;
			int[] mouseCords = getMouseArrayPosition();
			//System.out.println(mouseCords[0] + " " + mouseCords[1]	);
			if (!shopping) {
				if ((6 <= mouseCords[0] && mouseCords[0] <= 8) && (mouseCords[1] == 26 || mouseCords[1] == 27)){
					if (Math.abs(arrCol-mouseCords[1]) <= 3 && Math.abs(arrRow-mouseCords[0])<= 3) {
						setCursor("Interact");
						if (rightClickKey) {
							screen = "house";
							setCursor("Default");
							player.x = 800;
							player.y = 450;
							characterFrame = 0;
						}
					}else {
						setCursor("Default");
					}
				}else {
					setCursor("Default");
				}
			}
		}
	}

	//Description: Take the pixel location of the cursor and convert it to its location in our map array
	//Parameters: None
	//Return: int array with the x position in index 0 and y position in index 1
	public int[] getMouseArrayPosition() {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		int mouseX = pointerInfo.getLocation().x;
		int mouseY = pointerInfo.getLocation().y;
		int arrRow = (int)Math.round((mouseY-115)/35.0)+1;
		int arrCol = (int)Math.round((mouseX-10)/35.0);
		int[] returnArr = {arrRow,arrCol};
		return returnArr;
	}

	//Description: Updates every frame does the movement
	//Paramenters: None
	//Return: Void
	public void update() {
		if(up) {

			if (frameCount % 8 == 0) {
				characterFrame++;
				if (characterFrame >=9) {
					characterFrame = 6;
				}
			}

			playerFront = player.isPlayerInFrontMapObject(map, dir);
		

			if (screen.equals("outside")) {
				if (player.isCollision(map,dir,up,down,left,right,twoDirectionLeft,twoDirectionRight)) {
					player.y += -speed;				
				}				
			}else if (screen.equals("house")){
				player.y += -speed;	
			}				


		}
		else if(down) {

			if (frameCount % 8 == 0) {
				characterFrame++;
				if (characterFrame >= 3 ) {
					characterFrame = 0;
				}
			}
			playerFront = player.isPlayerInFrontMapObject(map, dir);

			if (screen.equals("outside")) {
				if (player.isCollision(map,dir,up,down,left,right,twoDirectionLeft,twoDirectionRight)) {
					player.y += speed;				
				}				
			}else if (screen.equals("house")){
				player.y += speed;	
			}	
		}
		if(left) {
			playerFront = player.isPlayerInFrontMapObject(map, dir);
			
			if (screen.equals("outside")) {
				if(player.isCollision(map,dir,up,down,left,right,twoDirectionLeft,twoDirectionRight)) {
					player.x -= speed;				
				}				
			}else if (screen.equals("house")){
				player.x -= speed;				
			}	



			//This !up and !down just prevents my character from doing weird movements when diagonal
			if (!up && !down) {
				if (frameCount % 8 == 0) {
					characterFrame++;
					if (characterFrame >= 6) {
						characterFrame = 3;
					}
				}		
			}
		}
		else if(right) {
			playerFront = player.isPlayerInFrontMapObject(map, dir);

			if (screen.equals("outside")) {
				if(player.isCollision(map,dir,up,down,left,right,twoDirectionLeft,twoDirectionRight)) {
					player.x += speed;				
				}				
			}else if (screen.equals("house")){
				player.x += speed;				
			}	

			if (!up && !down) {
				if (frameCount % 8 == 0) {
					characterFrame++;
					if (characterFrame >= 12) {
						characterFrame = 9;
					}
				}
			}
		}

	}


	//Description: Initialize the inventory this is the default inventory
	//Parameters: None
	//Return: Void
	public void inventoryInit() {
		inventory[0] = "axe";
		inventory[1] = "hoe";
		inventory[2] = "waterCan";
		inventory[3] =  "pickaxe";
		inventory[4] = "scythe";
		inventory[5] = "parsnipSeed";
		for (int i = 6; i <= 11; i++) {
			inventory[i] = "";
		}
	}

	//Description: Get the current frame of whatever item is being used
	//Parameters: None
	//Return: Image (the image of the frame that is going to be displayed)
	public Image getToolImage() {
		Image returnImage=null ;
		//System.out.println(selectedItem);
		if (selectedItem.equals("axe")) {
			for (int i = 0; i < 4; i++) {
				if (dir.equals(directions[i])) {
					returnImage = axeFrames[i];
				}
			}
		}else if (selectedItem.equals("waterCan")) {
			for (int i = 0; i < 4; i ++) {
				if(dir.equals(directions[i])) {
					returnImage = waterCanFrames[i];
				}
			}
		}else if (selectedItem.equals("scythe")) {
			for (int i = 0; i < 4; i++) {
				if (dir.equals(directions[i])) {
					returnImage = scytheFrames[i];
				}
			}
		}else if (selectedItem.equals("hoe")) {
			for (int i = 0; i < 4; i++) {
				if (dir.equals(directions[i])) {
					returnImage = hoeFrames[i];
				}
			}
		}else if (selectedItem.equals("pickaxe")) {
			for (int i = 0; i < 4; i++) {
				if (dir.equals(directions[i])) {
					returnImage = pickaxeFrames[i];
				}
			}
		}else if (selectedItem.equals("parsnipSeed") || selectedItem.equals("beanStarter")) {
			returnImage = characterFramesOutside[characterFrame];
		}
		return returnImage;
	}


	//Drawing methods

	//Description: Draw the image that was recieved by getToolImage() display it for 30 frames
	//Paremeters: None
	//Return: Void
	public void drawActions() {


		Image toolImage = getToolImage();
		if (dir.equals("up") || dir.equals("down")){
			offScreenBuffer.drawImage(toolImage,player.x,player.y,this);						
		}else if (selectedItem.equals("parsnipSeed")) {

			offScreenBuffer.drawImage(toolImage,player.x,player.y,this);

		}
		else {
			offScreenBuffer.drawImage(toolImage,player.x-20,player.y,this);						
		}


		if (!waitTime) {
			curFrameCount = frameCount;
			waitTime = true;
			interactObjects();
		}
		if (frameCount - curFrameCount >= 30) {

			//System.out.println("ran");
			waitTime = false;
			//offScreenBuffer.drawImage(characterFramesOutside[characterFrame],player.x,player.y,this);
			useItem = false;
		}
	}

	//Description: Loop through my map 2d array and for each found object draw it 
	//Parameters: None
	//Return: Void
	public void drawMap() {
		for (int row = 0; row < map.length;row++) {
			for (int col = 0; col < map[row].length;col++) {
				if (map[row][col] == 1) {
					if (row == treeRemoveRow && col == treeRemoveCol) {
						offScreenBuffer.drawImage(tree[treeImageFrame],col*screenWidth/42-25,row*screenWidth/42-140,this);

					}else {

						offScreenBuffer.drawImage(tree[0],col*screenWidth/42-25,row*screenWidth/42-140,this);		
					}
				}
				if (map[row][col] == 3) {
					offScreenBuffer.drawImage(rock,col*screenWidth/42+10,row*screenWidth/42+15,this);
				}if (4 <= map[row][col] && map[row][col] <= 7) {
					offScreenBuffer.drawImage(wood[map[row][col]-4],col*screenWidth/42+5,row*screenWidth/42+14,this);
				}

				if(map[row][col] == 8) {
					offScreenBuffer.drawImage(dryPlantHole, col*screenWidth/42-6,row*screenWidth/42+12,this);
				}if(map[row][col] == 9) {
					offScreenBuffer.drawImage(wetPlantHole, col*screenWidth/42+2,row*screenWidth/42+9,this);
				}

				if (map[row][col] == 10) {
					int[] tempArr = getParsnipInfo(row,col);
					offScreenBuffer.drawImage(dryPlantHole, col*screenWidth/42-6,row*screenWidth/42+12,this);
					offScreenBuffer.drawImage(parsnipFrames[tempArr[0]],tempArr[1],tempArr[2],this);


				}if (map[row][col] == 11) {
					int[] tempArr = getParsnipInfo(row,col);
					offScreenBuffer.drawImage(wetPlantHole, col*screenWidth/42+2,row*screenWidth/42+9,this);
					offScreenBuffer.drawImage(parsnipFrames[tempArr[0]],tempArr[1],tempArr[2],this);
				} 
				
				if (map[row][col] == 12) {
					int[] tempArr = getBeanInfo(row,col);
					offScreenBuffer.drawImage(dryPlantHole, col*screenWidth/42-6,row*screenWidth/42+12,this);
					offScreenBuffer.drawImage(beanStarterFrames[tempArr[0]],tempArr[1],tempArr[2],this);
				}
				
				if (map[row][col] == 13) {
					int[] tempArr = getBeanInfo(row,col);
					offScreenBuffer.drawImage(wetPlantHole, col*screenWidth/42+2,row*screenWidth/42+9,this);
					offScreenBuffer.drawImage(beanStarterFrames[tempArr[0]],tempArr[1],tempArr[2],this);
				} 
				

				if (drawAddParsnip) {
					offScreenBuffer.drawImage(parsnipPickUp,0,700,this);
				}if (drawAddGold) {
					offScreenBuffer.drawImage(addGold,0,700,this);
				}
			}
		}
	}

	//Description: Each object has a different interaction depending on what the item is so this 
	//method tells the code what to do
	//Parameters: None
	//Return: Void
	public void interactObjects() {
		int arrRow=0,arrCol=0;

		int characterY = player.y+10; // I changed this from 15 - 10 !!!


		if (dir.equals("up")) {
			arrRow = (int)Math.round((player.y-10)/35.0)+1;
			arrCol = (int)Math.round((player.x)/35.0);
		}else if (dir.equals("left") || twoDirectionLeft) {
			arrRow = (int)Math.round((characterY)/35.0)+1;
			arrCol = (int)Math.round((player.x-25)/35.0);
		}else if (dir.equals("right") || twoDirectionRight) {
			arrRow = (int)Math.round((characterY)/35.0)+1;
			arrCol = (int)Math.round((player.x+25)/35.0);
		}else if (dir.equals("down")) {
			arrRow = (int)Math.round((characterY+15)/35.0)+1;
			arrCol = (int)Math.round((player.x)/35.0);
		}

		if (selectedItem.equals("axe")) {
			if (map[arrRow][arrCol] == 1) {
				treeFrameCount = frameCount;
				treeHitCount++;
				treeImageFrame++;
				treeRemoveRow = arrRow;
				treeRemoveCol = arrCol;
				//System.out.println(treeHitCount);
				if (treeHitCount == 3) {
					map[arrRow][arrCol] = 0;
					treeHitCount = 0;
					treeImageFrame = 0;
					try {
						if (map[arrRow-1][arrCol] != 1) {
							map[arrRow-1][arrCol] = 0;
							map[arrRow-1][arrCol-1] = 0;
							map[arrRow-1][arrCol+1] = 0;
							map[arrRow-2][arrCol] = 0;
							map[arrRow-2][arrCol+1] = 0;
							map[arrRow-2][arrCol-1] = 0;
							map[arrRow-3][arrCol] = 0;
							map[arrRow-3][arrCol+1] = 0;
							map[arrRow-3][arrCol-1] = 0; 
							map[arrRow-4][arrCol] = 0;
							map[arrRow-4][arrCol+1] = 0;
							map[arrRow-4][arrCol-1] =0;
						}
					}catch (Exception e) {
					}
				}

			}if (map[arrRow][arrCol] >= 4 && map[arrRow][arrCol] <= 7) {
				map[arrRow][arrCol] = 0;
			}
		}

		else if (selectedItem.equals("hoe")) {
			if (map[arrRow][arrCol] == 0) {
				map[arrRow][arrCol] =8;
			}
			else if (map[arrRow][arrCol] == 8 || map[arrRow][arrCol] == 9) {
				map[arrRow][arrCol] = 0;
			}
			else if (map[arrRow][arrCol] == 10 || map[arrRow][arrCol] == 11) {
				if (plantTimes[arrRow][arrCol] / 600 >= 4) {
					map[arrRow][arrCol] = 0;
					addInventoryItems("parsnip");
					addInventoryItems("parsnipSeed");
				}else {
					map[arrRow][arrCol] = 0;
					addInventoryItems("parsnipSeed");
				}
			}else if (map[arrRow][arrCol] == 12 || map[arrRow][arrCol] == 13){
				if (plantTimes[arrRow][arrCol] / 600 >= 7) {
					map[arrRow][arrCol] = 0;
					addInventoryItems("bean");
					addInventoryItems("beanStarter");
				}else {
					map[arrRow][arrCol] = 0;
					addInventoryItems("beanStarter");
				}
			}
		}
		else if (selectedItem.equals("waterCan")) {
			if (waterWidth != 0) {
				if (map[arrRow][arrCol] == 8) {
					
					map[arrRow][arrCol] = 9;
					waterWidth -= 7;	
					if (waterWaitFrames1 == -1) {
						waterWaitFrames1 = frameCount;
						waterRow1 = arrRow;
						waterCol1 = arrCol;						
					}else {
						System.out.println("ran");
						waterWaitFrames2 = frameCount;
						waterRow2 = arrRow;
						waterCol2 = arrCol;	
						
					}
				}
			}if (map[arrRow][arrCol] == 10) {

				map[arrRow][arrCol] = 11;
				waterWidth -= 7;			
				plantTimes[arrRow][arrCol] += 300;
				if (waterWaitFrames1 == -1) {
					waterWaitFrames1 = frameCount;
					waterRow1 = arrRow;
					waterCol1 = arrCol;						
				}
				else {
					waterWaitFrames2 = frameCount;
					waterRow2 = arrRow;
					waterCol2 = arrCol;	
				}
			}
			if (map[arrRow][arrCol] == 12) {
				map[arrRow][arrCol] = 13;
				waterWidth -= 7;			
				plantTimes[arrRow][arrCol] += 300;
				if (waterWaitFrames1 == -1) {
					waterWaitFrames1 = frameCount;
					waterRow1 = arrRow;
					waterCol1 = arrCol;						
				}
				else {
					waterWaitFrames2 = frameCount;
					waterRow2 = arrRow;
					waterCol2 = arrCol;	
				}
			
			}	
			
			if (arrRow >= 21 && arrCol >= 32 && arrCol <= 36) {
				waterWidth = 42;
			}
		}else if (selectedItem.equals("pickaxe")) {
			if (map[arrRow][arrCol] == 3) {
				map[arrRow][arrCol] = 0;
			}
		}
		else if (selectedItem.equals("parsnipSeed")) {
			if (map[arrRow][arrCol] == 8) {
				map[arrRow][arrCol] = 10;
				parsnipSeedAmt--;
				plantTimes[arrRow][arrCol] = 1;
			}else if (map[arrRow][arrCol] == 9) {
				map[arrRow][arrCol] = 11;
				parsnipSeedAmt--;
				plantTimes[arrRow][arrCol] = 1;
			}
			if (parsnipSeedAmt == 0) {
				for (int i = 0; i < 12;i++) {
					if (inventory[i].equals("parsnipSeed")) {
						inventory[i] = "";
					}
				}
				parsnipSeedX = -1;
				parsnipSeedY = -1;
				selectedItem = "";
				useItem = false;
			}
		}else if (selectedItem.equals("beanStarter")) {
			if (map[arrRow][arrCol] == 8) {
				map[arrRow][arrCol] = 12;
				beanStarterAmt--;
				plantTimes[arrRow][arrCol] = 1;
			}else if (map[arrRow][arrCol] == 9) {
				map[arrRow][arrCol] = 13;
				beanStarterAmt--;
				plantTimes[arrRow][arrCol] = 1;
			}
			
			if (beanStarterAmt == 0) {
				for (int i = 0; i < 12;i++) {
					if (inventory[i].equals("beanStarter")) {
						inventory[i] = "";
					}
				}
				beanStarterX = -1;
				beanStarterY = -1;
				selectedItem = "";
				useItem = false;
			}
		}

	}

	//Description: Loop through my plant times 2d array with the time each parsnip has been growing
	//And increment it
	//Parameters: None
	//Return: Void
	public void updatePlantTimes() {
		for (int row = 0; row < map.length;row++) {
			for (int col = 0; col < map[row].length;col++) {
				if (plantTimes[row][col] > 0) {
					plantTimes[row][col]++;
				}
			}
		}
	}

	//Description: Calculate what stage parsnip is at and the coordinates to draw it at
	//Paramenters: int row, int col
	//Return: int array with the stage at index 0, col as index 1, row as index 2
	public int[] getParsnipInfo(int row, int col) {

		//This is because each stage of the parsnip unfortuntaly needs
		//to be planted in different coords because of the image size and shapes
		int[] returnArr = new int[3];
		if (plantTimes[row][col] / 600== 0) {
			returnArr[0] = 0;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42;
		}
		if (plantTimes[row][col]/600 == 1) {
			returnArr[0] = 1;
			returnArr[1] = col*screenWidth/42+5;
			returnArr[2] = row*screenWidth/42+15;
		}if (plantTimes[row][col] / 600 == 2) {
			returnArr[0] = 2;
			returnArr[1] = col*screenWidth/42+8;
			returnArr[2] = row*screenWidth/42+17;
		}if (plantTimes[row][col]/ 600 == 3) {
			returnArr[0] = 3;
			returnArr[1] = col*screenWidth/42+8;
			returnArr[2] = row*screenWidth/42+10;
		}
		if (plantTimes[row][col]/ 600 >= 4) {
			returnArr[0] = 4;
			returnArr[1] = col*screenWidth/42+5	;
			returnArr[2] = row*screenWidth/42+13;
		}
		
		return returnArr;
	}
	
	//Description: Calculate what stage bean stalks are  at and the coordinates to draw it at
	//Paramenters: int row, int col
	//Return: int array with the stage at index 0, col as index 1, row as index 2
	public int[] getBeanInfo(int row, int col) {
		int[] returnArr = new int[3];
		if (plantTimes[row][col] / 600== 0) {
			returnArr[0] = 0;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42-15;
		}
		
		if (plantTimes[row][col]/600 == 1) {
			returnArr[0] = 1;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42-10;
		}if (plantTimes[row][col] / 600 == 2) {
			returnArr[0] = 2;
			returnArr[1] = col*screenWidth/42+3;
			returnArr[2] = row*screenWidth/42-13;
		}if (plantTimes[row][col]/ 600 == 3) {
			returnArr[0] = 3;
			returnArr[1] = col*screenWidth/42+3;
			returnArr[2] = row*screenWidth/42-10;
		}
		if (plantTimes[row][col]/ 600 == 4) {
			returnArr[0] = 4;
			returnArr[1] = col*screenWidth/42+1	;
			returnArr[2] = row*screenWidth/42-8;
		}
		if (plantTimes[row][col]/ 600 == 5) {
			returnArr[0] = 5;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42-3;
		}
		if (plantTimes[row][col]/ 600 == 6) {
			returnArr[0] = 6;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42-4;
		}
		if(plantTimes[row][col] / 600 >= 7) {
			returnArr[0] = 7;
			returnArr[1] = col*screenWidth/42;
			returnArr[2] = row*screenWidth/42;
		}
		
		return returnArr;
	}

	//Description: For each inventory item return the image of that item and if it is a parsnip, waterCar, or parsnipSeed
	//return the coordinates for it's other stuff as well.
	//Parameters: int inventoryIndex which is the current index our inventory is on, xCoord and yCoord, where the images will be drawn
	//return: Image (the image to be drawn)
	public Image getInventoryImage(int inventoryIndex,int xCoord,int yCoord) {
		if (!inventory[inventoryIndex].equals("")) {
			if (inventory[inventoryIndex].equals("axe")) {
				return axe;
			}else if (inventory[inventoryIndex].equals("hoe")) {
				return hoe;
			}else if (inventory[inventoryIndex].equals("scythe")) {
				return scythe;
			}else if (inventory[inventoryIndex].equals("pickaxe")) {
				return pickaxe;
			}else if (inventory[inventoryIndex].equals("waterCan")) {
				waterBarX = xCoord;
				waterBarY = yCoord+30;
				return waterCan;
			}else if (inventory[inventoryIndex].equals("parsnipSeed")) {
				parsnipSeedX = xCoord+30;
				parsnipSeedY = yCoord+3;
				return parsnipSeed;
			}else if (inventory[inventoryIndex].equals("parsnip")) {
				parsnipX = xCoord+30;
				parsnipY = yCoord+3;
				return parsnip;
			}else if (inventory[inventoryIndex].equals("beanStarter")) {
				beanStarterX = xCoord+30;
				beanStarterY = yCoord+3;
				return beanStarter;
			}else if (inventory[inventoryIndex].equals("bean")) {
				beanX = xCoord+30;
				beanY = yCoord+3;
				return bean;
			}
		}
		return null;
	}

	//Description: add either a parsnip or parsnip seed to our inventory. Increment the amount if exists or add it entirely if it doesn't
	//Parameters: String item (tells us what item is being added)
	//Return: Void
	public void addInventoryItems(String item) {
		boolean itemFound = false;
		int firstOpenSlot = -1;

		for (int i = 11; i >= 0;i--) {
			if (inventory[i].equals(item)) {
				if (item.equals("parsnipSeed")) {
					parsnipSeedAmt++;
					itemFound = true;
				}else if (item.equals("parsnip")) {
					parsnipAmt++;
					itemFound = true;
				}else if (item.equals("beanStarter")) {
					beanStarterAmt++;
					itemFound = true;
				}else if (item.equals("bean")) {
					beanAmt++;
					itemFound = true;
				}
			}else if (inventory[i].equals("")) {
				firstOpenSlot = i;
			}
		}
		if (!itemFound) {
			if (firstOpenSlot != 1) {
				inventory[firstOpenSlot] = item;
				
				if (item.equals("parsnip")) {
					parsnipAmt++;
				}else if (item.equals("parsnipSeed")) {
					parsnipSeedAmt++;
				}else if (item.equals("beanStarter")) {
					beanStarterAmt++;
				}else if (item.equals("bean")) {
					beanAmt++;
				}
			}
		}

		if (item.equals("parsnip") && !drawAddParsnip) {
			drawAddParsnip = true;
			parsnipAddFrame = frameCount;
		}
	}

	//Description: Draw the gold numbers cuz each digit is drawn individually at different coordinates
	//Parameters: None
	//Return: Void
	public void drawGold() {
		String goldAmt = gold.toString();
		int idx = goldAmt.length();
		for (char i : goldAmt.toCharArray()) {
			int num = i-48;
			offScreenBuffer.drawImage(numbers[num],screenWidth-moneyCordinates[idx-1],33,this);
			idx--;
		}
	}
	/* Abstract Methods
	 ----------- 	*/

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		//Print characters position

		if (key == KeyEvent.VK_T) {
			if (selectedItem.equals("parsnip")) {
				gold += 30;
				drawAddGold = true;
				goldAddFrame = frameCount;
				parsnipAmt--;
				if (parsnipAmt == 0) {
					for (int i = 0 ; i <12; i++) {
						if (inventory[i].equals("parsnip")) {
							inventory[i] = "";
						}
					}
					parsnipX = -1;
					parsnipY = -1;
					selectedItem = "";
				}
			}
			if (selectedItem.equals("bean")) {
				gold += 120;
				drawAddGold = true;
				goldAddFrame = frameCount;
				beanAmt--;
				if (beanAmt == 0) {
					for (int i = 0 ; i <12; i++) {
						if (inventory[i].equals("bean")) {
							inventory[i] = "";
						}
					}
					beanX = -1;
					beanY = -1;
					selectedItem = "";
				}
			}
		}
		if (key == KeyEvent.VK_G) {
			System.out.println(player.x + " " + player.y);
			int arrRow = (int)Math.round(player.y/35.0)+1;
			int arrCol = (int)Math.round(player.x/35.0);
			System.out.println(arrRow + " " + arrCol);
			cords = true;
		}
		if (key == KeyEvent.VK_E) {
			shopping = !shopping;
		}
		if (key == KeyEvent.VK_H) {
			screen = "homescreen";
		}
		//Toggle inventory bar inventory bar
		if (key == KeyEvent.VK_I) {
			showInventory = !showInventory;
		}
		if (key == KeyEvent.VK_X) {
			rightClickKey = true;
		}

		if(key == KeyEvent.VK_A) {
			if (!walkingChange) {
				characterFrame = 3;
				walkingChange = true;
			}
			left = true;
			right = false;
			dir = "left";
			twoDirectionLeft = true;

		}if(key == KeyEvent.VK_D) {
			if (!walkingChange) {
				characterFrame = 9;
				walkingChange = true;				
			}
			right = true;
			left = false;
			dir = "right";
			twoDirectionRight = true;
		}if(key == KeyEvent.VK_W) {
			//goingUp = true;
			if (!walkingChange) {
				characterFrame = 6;
				walkingChange = true;
			}
			up = true;
			down = false;
			dir = "up";
		}if(key == KeyEvent.VK_S) {
			////goingDown = true;
			if (!walkingChange) {
				characterFrame = 0;
				walkingChange = true;				
			}
			down = true;
			up = false;
			dir = "down";
		}if (key == KeyEvent.VK_1) {
			if (inventoryNumber != 1) {
				inventoryNumber = 1;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_2) {
			if (inventoryNumber != 2) {
				inventoryNumber = 2;				
			}else {
				inventoryNumber =0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_3) {
			if (inventoryNumber != 3) {
				inventoryNumber = 3;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_4) {
			if (inventoryNumber !=4) {
				inventoryNumber = 4;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_5) {
			if (inventoryNumber != 5) {
				inventoryNumber = 5;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_6) {
			if(inventoryNumber != 6) {
				inventoryNumber = 6;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_7) {
			if (inventoryNumber != 7) {
				inventoryNumber = 7;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_8) {
			if (inventoryNumber != 8) {
				inventoryNumber = 8;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_9) {
			if (inventoryNumber != 9) {
				inventoryNumber = 9;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_0) {
			if (inventoryNumber != 10) {
				inventoryNumber = 10;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_MINUS) {
			if (inventoryNumber != 11) {
				inventoryNumber = 11;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}if (key == KeyEvent.VK_EQUALS) {
			if (inventoryNumber != 12) {
				inventoryNumber = 12;				
			}else {
				inventoryNumber = 0;
				selectedItem = "";
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_X) {
			rightClickKey = false;
		}
		else if(key == KeyEvent.VK_A) {

			characterFrame = 3;			
			left = false;
			walkingChange = false;
			dir = "left";
			twoDirectionLeft= false;	

		}else if(key == KeyEvent.VK_D) {

			characterFrame = 9;			
			right = false;
			walkingChange = false;
			dir = "right";
			twoDirectionRight = false;	
		}else if(key == KeyEvent.VK_W) {

			//goingUp = false;
			characterFrame = 6;		

			up = false;
			walkingChange = false;
			dir = "up";		

		}else if(key == KeyEvent.VK_S) {

			//goingDown = false;
			characterFrame = 0;				

			down = false;
			walkingChange = false;
			dir = "down";

		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			//main game loop
			if (inventoryNumber != 0) {
				if (!inventory[inventoryNumber-1].equals("")) {
					selectedItem = inventory[inventoryNumber-1];
				}else {
					selectedItem = "";
				}				
			}

			if (frameCount - treeFrameCount >= 60) {
				treeHitCount = 0;
				treeImageFrame = 0;
			}

			if (frameCount - waterWaitFrames1 >= 60) {
				waterWait1 = false;
				waterWaitFrames1 = -1;
				if (map[waterRow1][waterCol1] == 9) {
					map[waterRow1][waterCol1] = 8;
				}else if (map[waterRow1][waterCol1] == 11) {
					map[waterRow1][waterCol1] = 10;
				}else if (map[waterRow1][waterCol1] == 13) {
					map[waterRow1][waterCol1] = 12;
				}
			}
			if(frameCount - waterWaitFrames2 >= 60) {
				waterWait2 = false;
				waterWaitFrames2 = -1;
				if (map[waterRow2][waterCol2] == 9) {
					map[waterRow2][waterCol2] = 8;
				}else if (map[waterRow2][waterCol2] == 11) {
					map[waterRow2][waterCol2] = 10;
				}else if (map[waterRow2][waterCol2] == 13) {
					map[waterRow2][waterCol2] = 12;
				}
			}

			if(frameCount - parsnipAddFrame >= 120) {
				drawAddParsnip = false;
				parsnipAddFrame = -1;
			}
			if(frameCount - goldAddFrame >= 120) {
				drawAddGold = false;
				goldAddFrame = -1;
			}

			if (!shopping) {
				update();
				updatePlantTimes();				
				frameCount++;
			}else {
				PointerInfo pointerInfo = MouseInfo.getPointerInfo();
				int mouseX = pointerInfo.getLocation().x;
				int mouseY = pointerInfo.getLocation().y;
				//System.out.println(mouseX + " " + mouseY);
				if(shopping) {
					if((mouseX >= 341 && mouseX <= 1155) && (mouseY >= 268 && mouseY <= 340)) 
					{
						setCursor("Select");
					}else {
						setCursor("Default");
					}
				}
			}

			keepInBounds();
			this.repaint();

			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
		int[] mouseCords = getMouseArrayPosition();
		System.out.println(mouseCords[0]);
		System.out.println(mouseCords[1]);

		if (showInstructions) {
			if (e.getX() >= 1145 && e.getX() <= 1175 && e.getY() >= 220 && e.getY() <= 250){
				showInstructions = false;
			}
		}
		if (screen.equals("homescreen")) {
			if ((e.getX() >= 655 && e.getX() <= 830) && (e.getY() >= 600 && e.getY() <= 735)) {
				screen = "house";
				showInstructions = true;
			}
		}else if (screen.equals("outside")) {
			if (!selectedItem.equals("") && !selectedItem.equals("parsnip")) {
				useItem = true;
			}			
		}
		if(shopping) {
			if((e.getX() >= 341 && e.getX() <= 1155) && (e.getY() >= 230 && e.getY()<= 300)) {
				if(gold >= 20) {
					gold -= 20;
					addInventoryItems("parsnipSeed");
				}
			}if (shoppingIdx == 1) {
				if ((e.getX() >= 341 && e.getX() <= 1155) && (e.getY() >= 315 && e.getY() <= 400) && gold >= 300) {
					shoppingIdx = 2;
					gold -= 300;
					addInventoryItems("parsnipSeed");
					addInventoryItems("parsnipSeed");
				}
			}else {
				if ((e.getX() >= 341 && e.getX() <= 1155) && (e.getY() >= 315 && e.getY() <= 400)) {
					if (gold >= 60) {
						gold -= 60;
						addInventoryItems("beanStarter");						
					}
				}

			}
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	//class Map {
	//	//MapOject map [] [] = intitlize      size will be size of our submap
	//	// In map array put Map object
	//	MapObject map[][];
	//	
	//	public Map(int width, int height) {
	//		map = new MapObject[width][height];
	//		
	//		Player player = new Player(20,10);
	//		map[20][10] = player;
	//		
	//	}
	//
	//}



	//Description: Initializes all of my images because there's a lot of them
	//Parameters: None
	//Return: Void
	public void images() {
		//Images
		BufferedImage image;
		//Tree images
		for (int i = 0; i < 3;i++) {
			try {
				image = ImageIO.read(new File("tree" + i + ".png"));	
				int scaledWidth = image.getWidth() / 2;   
				int scaledHeight = image.getHeight() / 2;
				tree[i] = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			}catch (Exception e){
			}
		}

		//Rock Image
		try {
			image= ImageIO.read(new File("rock.png"));
			int scaledWidth = image.getWidth()/1;   
			int scaledHeight = image.getHeight()/1;
			rock = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		}catch(Exception e) {
		}

		//Wood Image
		try {
			for (int i = 1; i < 5; i ++) {
				image = ImageIO.read(new File("wood" + i+".png"));
				int scaledWidth = image.getWidth() / 4;   
				int scaledHeight = image.getHeight() / 4;
				wood[i-1] = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
			}
		}catch (Exception e) {
		}

		//Plant Holes
		dryPlantHole = Toolkit.getDefaultToolkit().getImage("dryPlantHole.png");
		wetPlantHole = Toolkit.getDefaultToolkit().getImage("wetPlantHole.png");

		//Character in house and outside are two different sizes
		for (int i = 0; i < 12;i ++) {

			try { 
				image = ImageIO.read(new File("frame" + i+".png"));
				int scaledWidth = image.getWidth() / 2; // Adjust the scale factor as needed
				int scaledHeight = image.getHeight() / 2;
				Image scaledImage = image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);
				characterFramesInHouse[i] = scaledImage;


			}catch (Exception e){
			}

		}

		for (int i = 0; i < 12;i ++) {
			try { 
				image = ImageIO.read(new File("frame" + i+".png"));
				int scaledWidth = image.getWidth() / 3; // Adjust the scale factor as needed
				int scaledHeight = image.getHeight() / 3;
				Image scaledImage = image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);
				characterFramesOutside[i] = scaledImage;


			}catch (Exception e){
			}

		}

		//Axe frames

		for (int i = 0; i <4; i++) {
			try {
				image = ImageIO.read(new File("axeFrame" + (i+1) +".png"));
				int scaledWidth = image.getWidth()/3;
				int scaledHeight = image.getHeight()/3;
				Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
				axeFrames[i] = scaledImage;
			}catch (Exception e) {
			}
		}

		//Watering Can Frames
		for (int i = 0; i < 4; i++) {
			try {
				image = ImageIO.read(new File("waterCanFrame" + (i+1) +".png"));
				int scaledWidth = image.getWidth()/3;
				int scaledHeight = image.getHeight()/3;
				Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
				waterCanFrames[i] = scaledImage;
			}catch(Exception e) {

			}
		}

		//Scythe Frames
		for (int i = 0; i < 4; i++) {
			try {
				image = ImageIO.read(new File("scytheFrame" + (i+1) + ".png"));
				int scaledWidth = image.getWidth()/3;
				int scaledHeight = image.getHeight()/3;
				Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
				scytheFrames[i] = scaledImage;
			}catch(Exception e) {
			}
		}
		//Hoe Frames
		for (int i = 0; i < 4; i++) {
			try {
				image = ImageIO.read(new File("hoeFrame" + (i+1) + ".png"));
				int scaledWidth = image.getWidth()/3;
				int scaledHeight = image.getHeight()/3;
				Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
				hoeFrames[i] = scaledImage;
			}catch(Exception e) {
			}
		}

		//Pickaxe Frames
		for (int i = 0; i < 4; i ++) {
			try {
				image = ImageIO.read(new File("pickaxeFrame" + (i+1) + ".png"));
				int scaledWidth = image.getWidth()/3;
				int scaledHeight = image.getHeight()/3;
				Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
				pickaxeFrames[i] = scaledImage;
			}catch(Exception e) {
			}
		}

		//Parsnip Frames
		for (int i = 0; i < 5; i++) {
			Image parsnipImage = Toolkit.getDefaultToolkit().getImage("parsnipStage" + (i+1) + ".png");
			parsnipFrames[i] = parsnipImage;
		}
		
		//Bean Starter Frames
		for (int i = 0; i < 8; i++) {
			Image beanStarterImage =Toolkit.getDefaultToolkit().getImage("beanStarter" + (i+1) + ".png");
			beanStarterFrames[i] = beanStarterImage;
		}
		
		//All inventories

		for (int i =0; i <= 12; i++) {
			inventories[i] = Toolkit.getDefaultToolkit().getImage("inventory" + i +".png");
		}


		house = Toolkit.getDefaultToolkit().getImage("House.png");
		homescreen = Toolkit.getDefaultToolkit().getImage("homeScreen.png");
		startButton = Toolkit.getDefaultToolkit().getImage("startButton.png");
		shopScreen1 = Toolkit.getDefaultToolkit().getImage("shop1.png");
		shopScreen2 =  Toolkit.getDefaultToolkit().getImage("shop2.png");
		instructions = Toolkit.getDefaultToolkit().getImage("instructions.png");

		//outside = Toolkit.getDefaultToolkit().getImage("outside.png");
		BufferedImage outsideImagePreScale;
		try {

			outsideImagePreScale = ImageIO.read(new File("clearField.png"));
			outside = outsideImagePreScale.getScaledInstance((int)Math.round(outsideImagePreScale.getWidth()*1.5), (int)Math.round(outsideImagePreScale.getHeight()*1.5), Image.SCALE_SMOOTH);
		}catch (Exception e) {
		}
		try {
			image = ImageIO.read(new File("cursor.png"));	
			cursorImage = image.getScaledInstance(50,50,Image.SCALE_SMOOTH);

		}catch (Exception e){
		}
		try {
			image = ImageIO.read(new File("interactCursor.png"));
			interactCursorImage = image.getScaledInstance(30,30,Image.SCALE_SMOOTH);
		}catch (Exception e) {
		}
		try {
			image = ImageIO.read(new File("selectCursor.png"));
			selectCursorImage = image.getScaledInstance(30,30,Image.SCALE_SMOOTH);
		}catch (Exception e) {
		}

		//Inventory images
		axe = Toolkit.getDefaultToolkit().getImage("axe.png");
		hoe = Toolkit.getDefaultToolkit().getImage("hoe.png");
		waterCan = Toolkit.getDefaultToolkit().getImage("waterCan.png");
		waterBar = Toolkit.getDefaultToolkit().getImage("waterBar.png");
		pickaxe = Toolkit.getDefaultToolkit().getImage("pickaxe.png");
		scythe = Toolkit.getDefaultToolkit().getImage("scythe.png");
		parsnipSeed = Toolkit.getDefaultToolkit().getImage("parsnipSeed.png");
		beanStarter = Toolkit.getDefaultToolkit().getImage("beanStarter.png");
		parsnip = Toolkit.getDefaultToolkit().getImage("parsnip.png");
		bean = Toolkit.getDefaultToolkit().getImage("bean.png");
		parsnipPickUp = Toolkit.getDefaultToolkit().getImage("parsnipPickUp.png");
		addGold = Toolkit.getDefaultToolkit().getImage("addGold.png");
		goldImage = Toolkit.getDefaultToolkit().getImage("gold.png");

		for (int i = 0; i < 10; i++) {
			numbers[i] = Toolkit.getDefaultToolkit().getImage(i +".png");
		}

	}
}







//Description: This is my player class which tracks the coordiantes of my player, and contains two useful methods
//Parameters: x and y which is the starting position of my player
//Return: None it's a class
class Player {//extends MapObject {
	int x;
	int y;
	public Player(int x, int y) {

		this.x = x;
		this.y = y;
	}

	//Description: is the same as keepInBounds() keeps my player from running into objects on the map and walking
	//off the map or into water and other stuff
	//Parameters: map, dir, up,down,left,right,twoDirectionLeft,twoDirectionRight bascailly just the map and anything related to the characters 
	//direction to easily calculate what is in front of where the player is going.
	//Return: None
	public boolean isCollision(int[][] map,String dir,boolean up, boolean down, boolean left, boolean right, boolean twoDirectionLeft,boolean twoDirectionRight) {
		int arrRow=0,arrCol=0;

		int characterY = this.y+10; //I changed this from 15 - 10



		if (dir.equals("left") || twoDirectionLeft) {
			arrRow = (int)Math.round((characterY)/35.0)+1;
			arrCol = (int)Math.round((this.x-25)/35.0);
		}else if (dir.equals("right") || twoDirectionRight) {
			arrRow = (int)Math.round((characterY)/35.0)+1;
			arrCol = (int)Math.round((this.x+25)/35.0);
		}else if (dir.equals("up")) {
			arrRow = (int)Math.round((this.y-10)/35.0)+1;
			arrCol = (int)Math.round((this.x)/35.0);
		}else if (dir.equals("down")) {
			arrRow = (int)Math.round((characterY+15)/35.0)+1;
			arrCol = (int)Math.round((this.x)/35.0);
		}


		//Map borders
		if (arrCol == 39 && arrRow >= 12) {
			return false;
		}else if (arrCol == 40 && arrRow <= 7) {
			return false;
		}else if (arrRow >= 24) {
			return false;
		}
		if (arrCol == -1) {
			return false;
		}if(arrCol == 42 && (right || left)) {
			return false;
		}if (arrRow == 2 && arrCol >= 35) {
			return false;
		}if(arrRow == 0 && arrCol >= 31 && arrCol <= 34) {
			return false;
		}if (arrRow <= 2 && arrCol == 35) {
			return false;
		}if (arrRow <= 2 && arrCol == 30) {
			return false;
		}if (arrCol >= 40 && arrRow == 12) {
			return false;
		}if (arrCol >= 41 && arrRow == 7) {
			return false;
		}if(arrRow <= 2 && arrCol >= 17 && arrCol <= 30) {
			return false;
		}if (arrRow <= 0 && ((arrCol >= 4 && arrCol <= 8) || (arrCol >= 0 && arrCol <= 1))) {
			return false;
		}
		if (arrRow <= -1) {
			return false;
		}

		//Water Boundaries
		if (arrRow >= 22 && arrCol >= 32 && arrCol <= 36) {
			return false;
		}if(arrRow>= 22 && arrCol == 37) {
			return false;
		}

		//Objects
		if (map[arrRow][arrCol] == 1) {
			return false;
		}if (map[arrRow][arrCol] == 3) {
			return false;
		}if (map[arrRow][arrCol] >= 4 && map[arrRow][arrCol] <= 7) {
			return false;
		}

		//House
		if ((arrCol == 21 && 4 <= arrRow && arrRow <= 7) && !left) {
			return false;
		}
		if ((arrCol == 29 && 4 <= arrRow && arrRow <= 7) && !right) {
			return false;	
		}
		if (arrRow <= 7 && (arrCol >= 22 && arrCol <= 28)) {
			return false;
		}
		//Chest
		if(arrRow == 9 && arrCol == 20) {
			return false;
		}

		return true;
	}


	//Description: Checks if the player is in front of the objects or not. This is because when the player
	//is behind the objects he should be drawn that way as well to make the objects look 3d.
	//Parameters: map, dir (the map we're checking through, and dir to see what tile to check
	//Return: Boolean true if player is front false if object is in front
	public boolean isPlayerInFrontMapObject(int[][]map,String dir) {
		int arrRow = 0, arrCol = 0;
		if (dir.equals("up")) {
			arrRow = (int)Math.round((this.y-10)/35.0)+1;
			arrCol = (int)Math.round(this.x/35.0);
		}else if (dir.equals("left")) {
			arrRow = (int)Math.round((this.y)/35.0)+1;
			arrCol = (int)Math.round((this.x-10)/35.0);
		}else if (dir.equals("right")) {
			arrRow = (int)Math.round((this.y)/35.0)+1;
			arrCol = (int)Math.round((this.x+10)/35.0);
		}else if (dir.equals("down")) {
			arrRow = (int)Math.round((this.y+10)/35.0)+1;
			arrCol = (int)Math.round(this.x/35.0);
		}


		//This is because if the player crashes into the border boundary the code crashes cuz index -1
		try {	
			//System.out.println(map[arrRow][arrCol]);
			if (map[arrRow][arrCol] == 2) {
				return false;
			}			
		}catch (Exception e) {
		}
		return true;
	}
}

