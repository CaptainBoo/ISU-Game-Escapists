import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.awt.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

@SuppressWarnings("serial")
public class TheEscapists extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	LinkedList<Item> drawerLoot,craftingItems = new LinkedList<>();
	Graphics offScreenBuffer; 	
	Image offScreenImage, startMenu,drawerMenu, craftingMenu,credits, instructions,gameOver,winScreen;
	int FPS = 60;
	int frame = 0;
	int mouseX, mouseY;
	Thread thread;
	Map map;
	boolean left,right,up,down, directionChanged, drawerOpened,lootChanged,crafting,dontRefundItems;
	Image[] images;
	Player player;
	int[] drawerCordsX = {425,544,653,772,891,1010,425,544,653,772,891,1010}; //356 +61
	int[] drawerCordsY = {275,275,275,275,275,275,392,392,392,392,392,392};//363 -81
	int[] inventoryCordsX = {150,265,380,495,610,720};
	int[] craftingCordsX = {600,673,750};
	int[] recipeCordsX = {440,520,595,675,750};
	HashMap<String, String[]> allRecipes = new HashMap<>();
	TreeSet<String> knownRecipes = new TreeSet<>();
	//boolean left, right, up, down;

	Set<Integer> keys = new HashSet<Integer>();

	Image inventory;
	Item[] items;

	Image[] prisonerFrames;
	Image[] guardFrames;

	//Player player = new Player();
	Prisoner[] prisoners;
	Guard[] guards;

	Clip mainMenu, ambient, heat;
	int state = 0; // 0 = main menu, 1 = in game, 2 = heat (guard angry), 3 = credits, 4 = instructions, 5 = game over
	int previousState = -1;

	//Constrctor class where we intiliaze our map and thread. We also adjust our panel variables
	//Parameters: None
	//Return: None
	public TheEscapists() {
		// Make my class instances
		map = new Map();

		// Set up Panel and thread
		setPreferredSize(new Dimension(1472, 832));
		setLocation(100, 100);
		thread = new Thread(this);
		thread.start();
	}

	
	//Overridden paintComponent method which we use to draw all the images for our game
	//including the map, inventory, items, etc.
	//Parameters: Graphics graphic
	//Return: void
	public void paintComponent(Graphics graphic) {
		Graphics2D g = (Graphics2D) graphic;
		super.paintComponent(g);

		if (offScreenBuffer == null) {
			offScreenImage = createImage(this.getWidth(), this.getHeight());
			offScreenBuffer = offScreenImage.getGraphics();
		}

		//		offScreenBuffer.drawImage(map.getImage(),-map.getX(),-map.getY(),this);
		//		offScreenBuffer.setColor(new Color(120,137,148));
		//		offScreenBuffer.fillRect(100, 100, 120, 120);
		//		offScreenBuffer.setColor(new Color(48, 55, 65,150));
		//		offScreenBuffer.fillRect(110, 110, 90, 90);
		//		g.drawImage(offScreenImage, 0, 0, this);

		if (state == 0) { // start menu
			g.drawImage(startMenu, 0, 0, 1472, 832, this);
		} else if (state == 1 || state == 2) { // in game 
			g.drawImage(map.getImage(), -map.getX(), -map.getY(), this);

			//			g.drawImage(itemImages[0], 200, 100, 200, 200, this);
			//			g.drawImage(itemImages[1], 500, 700, 30, 30, this);

			g.drawImage(player.getPlayerFrames()[player.getCharacterFrame()], 736, 416, 40, 90, this);

			for (int i = 0; i < prisoners.length; i++) {
				g.drawImage(prisoners[i].getPlayerFrames()[prisoners[i].getCharacterFrame()], prisoners[i].getX() - map.getX(), prisoners[i].getY() - map.getY(),
						prisoners[i].getHitbox().width, prisoners[i].getHitbox().height, this);
			}
			for (int i = 0; i < guards.length; i++) {

				g.drawImage(guards[i].getPlayerFrames()[0], guards[i].getX() - map.getX(), guards[i].getY() - map.getY(),
						guards[i].getHitbox().width, guards[i].getHitbox().height, this);

			}
			g.drawImage(inventory, 140, 600, this);

			// Inventory
			for (int i = 0; i < player.getInventory().size(); i++) {

			}

			// Heat
			g.setFont(new Font("CourierNew ", Font.BOLD, 40));
			g.setColor(Color.WHITE);

			g.drawString("Heat: " + player.getHeat(), 140, 550);


			//Inventory
			for (int i = 0; i< player.getInventory().size();i++) {
				if (player.getInventory().get(i).isContraband()) {
					g.setColor(new Color (124,72,66));
					g.fillRect(inventoryCordsX[i]+5, 615, 80,80);
				}
				g.drawImage(player.getInventory().get(i).getItemImage(),inventoryCordsX[i],620,80,80,this);
			}
			//Drawers
			if (drawerOpened) {
				g.drawImage(drawerMenu, 400, 50, this);
				for (int i = 0; i< drawerLoot.size();i++) {
					g.drawImage(drawerLoot.get(i).getItemImage(),drawerCordsX[i],drawerCordsY[i],100,100,this);									
				}
			}


			//Crafting
			if (crafting) {
				g.drawImage(craftingMenu,400,50, this);
				for (int i = 0; i < craftingItems.size(); i++) {
					g.drawImage(craftingItems.get(i).getItemImage(),craftingCordsX[i],560,50,50,this);									
				}
				int itemCount = 0;
				//Recipes
				for (String key : knownRecipes) {
					for (int i = 7; i<items.length-1;i++) {
						if (items[i].getItemName().equals(key)) {
							g.drawImage(items[i].getItemImage(),recipeCordsX[itemCount],195,50,50,this);	
							itemCount++;
						}
					}
				}
			}



		} else if (state == 3) { // credits screen
			g.drawImage(credits, 0, 0, 1472, 832, this);
		} else if (state == 4) { // instructions screen
			g.drawImage(instructions, 0, 0, 1472, 832, this);
		} else if (state == 5) { // game over screen
			g.drawImage(gameOver, 0, 0, 1472, 832, this);

		}else if (state == 6 ) {
			g.drawImage(winScreen , 0, 0, 1472, 832, this);
		}

	}

	//This method generates the loot in our drawers. A drawer can generate up to 12 items
	//but its more likely to get low items are harder to get more items.30% for 1 item, 1% for 12
	//Parameters: None
	//Return: LinkedList<Item> which is our list of items for loot
	public LinkedList<Item> generateLoot() {
		int[] weights = {30, 20, 15, 10, 7, 5, 4, 3, 2, 2, 1, 1}; // Probabilities for 1-12
		Random random = new Random();
		int totalWeight = 0;

		// Calculate total weight
		for (int weight : weights) {
			totalWeight += weight;
		}

		// Generate random number and find corresponding number
		int randomValue = random.nextInt(totalWeight);
		int cumulativeWeight = 0;
		int num = 0;

		// Loop through weights array and determine the corresponding num
		for (int i = 0; i < weights.length; i++) {
			cumulativeWeight += weights[i];
			if (randomValue < cumulativeWeight) {
				num = i + 1; // Numbers are 1-indexed
				break; // Break out of the loop as soon as we find the correct number
			}
		}
		LinkedList<Item> loot = new LinkedList<>();
		for (int i = 0; i < num; i++) {
			loot.add(items[(int) (Math.random() * 7)]);
		}
		return loot;


	}
	
	//This is the method in which we initlize all our images and audio
	//Parameters: None
	//Return: Void
	public void initialize() {
		inventory = Toolkit.getDefaultToolkit().getImage("images/inventory.png");


		items = new Item[15];
		items[0] = new Item("comb", Toolkit.getDefaultToolkit().getImage("images/comb.png"),false);
		items[1] = new Item("duct_tape", Toolkit.getDefaultToolkit().getImage("images/duct_tape.png"),true);
		items[2] = new Item("crowbar", Toolkit.getDefaultToolkit().getImage("images/crowbar.png"),true);
		items[3] = new Item("lighter", Toolkit.getDefaultToolkit().getImage("images/lighter.png"),false);
		items[4] = new Item("foil", Toolkit.getDefaultToolkit().getImage("images/foil.png"),true);
		items[5] = new Item("inmate_outfit", Toolkit.getDefaultToolkit().getImage("images/inmate_outfit.png"),false);
		items[6] = new Item("jar_of_ink", Toolkit.getDefaultToolkit().getImage("images/jar_of_ink.png"),false);
		items[7] = new Item("contraband_pouch", Toolkit.getDefaultToolkit().getImage("images/contraband_pouch.png"),false);
		items[8] = new Item("flimsy_pickaxe", Toolkit.getDefaultToolkit().getImage("images/flimsy_pickaxe.png"),true);
		items[9] = new Item("molten_plastic", Toolkit.getDefaultToolkit().getImage("images/molten_plastic.png"),true);
		items[10] = new Item("tool_handle", Toolkit.getDefaultToolkit().getImage("images/tool_handle.png"),false);
		items[11] = new Item("guard_outfit", Toolkit.getDefaultToolkit().getImage("images/guard_outfit.png"),true);
		items[12] = new Item("red_key", Toolkit.getDefaultToolkit().getImage("images/red_key.png"),true);
		items[13] = new Item("medic_outfit", Toolkit.getDefaultToolkit().getImage("images/medic_Outfit.png"),true);
		items[14] = new Item("cyan_key",Toolkit.getDefaultToolkit().getImage("images/cyan_key.png"),true);


		allRecipes.put("flimsy_pickaxe", new String[]{"crowbar", "duct_tape", "tool_handle"});
		allRecipes.put("contraband_pouch", new String[]{"foil", "foil", "duct_tape"});
		allRecipes.put("molten_plastic", new String[]{"comb", "comb", "lighter"});
		allRecipes.put("guard_outfit", new String[]{"jar_of_ink", "jar_of_ink", "inmate_outfit"});
		allRecipes.put("tool_handle", new String[]{"foil", "crowbar", "duct_tape"});

		prisonerFrames = new Image[1];
		prisonerFrames[0] = Toolkit.getDefaultToolkit().getImage("images/escapists_character_temp.png");

		startMenu = Toolkit.getDefaultToolkit().getImage("images/main_menu.png");

		BufferedImage image;
		try {

			image = ImageIO.read(new File("images/drawer.png"));
			int scaledWidth = (int) (image.getWidth() * 0.75); // Explicit cast to int
			int scaledHeight = (int) (image.getHeight() * 0.75);
			drawerMenu = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);


		} catch (Exception e) {
		}
		try {

			image = ImageIO.read(new File("images/craftingMenu.png"));
			int scaledWidth = (int) (image.getWidth() * 0.75); // Explicit cast to int
			int scaledHeight = (int) (image.getHeight() * 0.75);
			craftingMenu = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);


		} catch (Exception e) {
		}


		images = new Image[5];
		prisoners = new Prisoner[2];
		Image[] playerFrames = new Image[16];
		for (int i = 1; i <= 16; i++) {
			playerFrames[i-1] = Toolkit.getDefaultToolkit().getImage("images/playerFrame" + i +".png");
		}
		player = new Player(playerFrames);
		prisoners = new Prisoner[2];
		prisoners[0] = new Prisoner("Prisoner 1",playerFrames);
		prisoners[1] = new Prisoner("Prisoner 2",playerFrames);

		//guards = new Guard[1];
		Image[] guardFrames = new Image[1];
		guardFrames[0] = Toolkit.getDefaultToolkit().getImage("images/Guard.png");
		guards = new Guard[3];
		guards[0] = new Guard("Guard1",guardFrames);
		guards[1] = new Guard("Guard2",guardFrames);
		guards[2] = new Guard("Guard3",guardFrames);


		// Screen images
		startMenu = Toolkit.getDefaultToolkit().getImage("images/main_menu.png");
		credits = Toolkit.getDefaultToolkit().getImage("images/credits.png");
		instructions = Toolkit.getDefaultToolkit().getImage("images/instructions.png");
		gameOver = Toolkit.getDefaultToolkit().getImage("images/gameover.png");
		winScreen = Toolkit.getDefaultToolkit().getImage("images/winScreen.png");
		

		// Initializing audio
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File("sounds/Main Menu.wav"));
			mainMenu = AudioSystem.getClip();
			mainMenu.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("sounds/Center Perks 2.0 - Free Time (0 Stars).wav"));
			ambient = AudioSystem.getClip();
			ambient.open(sound);
			sound = AudioSystem.getAudioInputStream(new File("sounds/Center Perks 2.0 - Roll Call (5 Stars).wav"));
			heat = AudioSystem.getClip();
			heat.open(sound);
		} catch (Exception e) {
			System.err.println("Error loading main menu sound: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//This method handles the movement of our player by moving the map behind him with the move method from the map class
	//Parameters: None
	//Return: Void
	public void move() {

		if (player.isCollision(map,up,down,left,right)) {
			if (left) {
				map.move("left");
			}if (right) {
				map.move("right");
			}if (down) {
				map.move("down");
			}if (up) {
				map.move("up");
			}repaint();
		}
	}

	//This method handles the movement frame of our player to give him animation depending on what direction he is moving in
	//Parameters: None
	//Return: Void
	public void handleFrames() {
		if (up) {
			if (frame % 10 == 0) {
				player.incrementCharacterFrame(1);
				if (player.getCharacterFrame() >=4) {
					player.incrementCharacterFrame(-4);
				}
				//				for (int i = 0; i < prisoners.length;i++) {
				//					prisoners[i].incrementCharacterFrame(1);
				//					if (prisoners[i].getCharacterFrame() >=4) {
				//						prisoners[i].incrementCharacterFrame(-4);
				//					}
				//				}
			}
		}if (down) {
			if (frame % 10 == 0) {
				player.incrementCharacterFrame(1);
				if (player.getCharacterFrame() >=8) {
					player.incrementCharacterFrame(-4);
				}
				//				for (int i = 0; i < prisoners.length;i++) {
				//					prisoners[i].incrementCharacterFrame(1);
				//					if (prisoners[i].getCharacterFrame() >=8) {
				//						prisoners[i].incrementCharacterFrame(-4);
				//					}
				//				}
			}
		}if (left && !up && !down) {
			if (frame % 10 == 0) {
				player.incrementCharacterFrame(1);
				if (player.getCharacterFrame() >=12) {
					player.incrementCharacterFrame(-4);
				}
				//				for (int i = 0; i < prisoners.length;i++) {
				//					prisoners[i].incrementCharacterFrame(1);
				//					if (prisoners[i].getCharacterFrame() >=12) {
				//						prisoners[i].incrementCharacterFrame(-4);
				//					}
				//				}
			}
		}if (right && !up && !down) {
			if (frame % 10 == 0) {
				player.incrementCharacterFrame(1);
				if (player.getCharacterFrame() >=16) {
					player.incrementCharacterFrame(-4);
				}
				//				for (int i = 0; i < prisoners.length;i++) {
				//					prisoners[i].incrementCharacterFrame(1);
				//					if (prisoners[i].getCharacterFrame() >=16) {
				//						prisoners[i].incrementCharacterFrame(-4);
				//					}
				//				}
			}

		}

	}
	
	//this method is called everytime our run method runs. In here we just call lots of our other methods that need to be updated
	//As well as handle movement for prisoners/guards and handle the gamestates
	//Parameters: None
	//Return: Void
	public void update() {

		//System.out.println(lootChanged);

		frame++;
		handleFrames();

		for (Prisoner prisoner : prisoners) {
			prisoner.randomMovement(map.getMapArr(), map,frame);
		}
		for (Guard guard : guards) {

			//			guard.chasePlayer(map.getMapArr(), map, player);

			if (player.getHeat() >= 70 && (Math.hypot(guard.getX() - map.getX() - 736, guard.getY() - map.getY() - 416) < 500)) {
				guard.chasePlayer(map.getMapArr(), map, player);
			} else {
				guard.randomMovement(map.getMapArr(), map,frame);
			}

			if (new Rectangle (guard.getX(), guard.getY(), 40, 90).intersects(new Rectangle(map.getX() + 736, map.getY() + 416, 40, 90))) {
				state = 5;
			}
		}

		if (player.getHeat()>0) {
			if (frame % 60 == 0) player.setHeat(player.getHeat() - 1);			
		}
		if (state == 1 && player.getHeat() >= 70) {
			state = 2;
		}

		if (state == 2 && player.getHeat() < 70) {
			state = 1;
		}


	}

	//This method is where we handle crafting. All recipes start undiscovered and you have to discover them. There are up to 5 different recipes
	//This method uses sort and Arrays.equals to check if the Array of items you provided is equal to the recipe array in our hash map
	//Parameters: None
	//Return: Void
	public void handleCrafting() {
		System.out.println("ran1");
		String[] compareArr =new String[craftingItems.size()] ;
		for (int i = 0; i < craftingItems.size(); i++) {
			compareArr[i]= craftingItems.get(i).getItemName();
		}
		for (String key: allRecipes.keySet()) {
			if (compareArr.length!= allRecipes.get(key).length) {
				continue; 
			}
			Arrays.sort(compareArr);
			String[] tempArr = allRecipes.get(key);
			Arrays.sort(tempArr);
			System.out.println("ran2");
			for (int i = 0; i < 3;i++) {
				System.out.println(tempArr[i] + " " + compareArr[i]);
			}
			if (Arrays.equals(compareArr, tempArr)){
				System.out.println("ran3");
				knownRecipes.add(key);
				dontRefundItems=true;
				for (int i = 7; i<items.length-1;i++) {
					if (items[i].getItemName().equals(key)) {
						player.getInventory().add(items[i]);
					}
				}
			}



		}
	}
	
	//This overridden method is for our thread and it runs every frame.
	//In this method we generate our drawers first loop, start our sounds, handles movement and increments our frames.
	//Parameters: None
	//Return: Void
	public void run() {
		initialize();
		drawerLoot = generateLoot();

		while (true) {
			frame++;
			update();
			this.repaint();

			move();
			runSounds();

			try {
				Thread.sleep(1000 / FPS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//This method starts our song files and has them on loop. It plays different sound tracks depening on what game state we are in.
	//Parameters: None
	//Return: Void
	public void runSounds() {
		if (state != previousState) {
			stopAllSounds();
			if (state == 0) {
				mainMenu.setFramePosition(0);
				mainMenu.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (state == 2) {
				heat.setFramePosition(0);
				heat.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (state == 1){
				ambient.setFramePosition(0);
				ambient.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (state == 6) {
				mainMenu.setFramePosition(0);
				mainMenu.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		previousState = state;	
	}
	//This method stops all our soundtracks so that there will be no audio
	//Parameters: None
	//Return: Void
	public void stopAllSounds() {
		if (mainMenu.isRunning()) mainMenu.stop();
		if (ambient.isRunning()) ambient.stop();
		if (heat.isRunning()) heat.stop();
	}

	//Not used
	public void mouseClicked(MouseEvent e) {

	}


	//This overridden method triggers whenever my mouse is pressed. If we are in a drawer it'll move items
	//When we are in crafting we will try crafting, and otherwise we print the coordinates of our mouse (for coding help)
	//Parameters: MouseEvent e
	//Return: Void
	public void mousePressed(MouseEvent e) {
		
		if (drawerOpened) {

			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			int mouseX = pointerInfo.getLocation().x	;
			int mouseY = pointerInfo.getLocation().y-50;
			int square = -1;
			// Loop through each square
			for (int i = 0; i < drawerCordsX.length; i++) {
				int topLeftX = drawerCordsX[i];
				int topLeftY = drawerCordsY[i];

				if (mouseX >= topLeftX && mouseX <= topLeftX + 99 &&
						mouseY >= topLeftY && mouseY <= topLeftY + 91) {
					square = i+1;
					break; 
				}
			}
			int square2 = -1;
			for (int i = 0; i < 6; i++) {
				int topLeftX = inventoryCordsX[i];
				int topLeftY = 620;
				if (mouseX >= topLeftX && mouseX <= topLeftX + 82 &&
						mouseY >= topLeftY && mouseY <= topLeftY + 82) {
					square2 = i+1;
					break;
				}
			}
			if (square != -1 && square <= drawerLoot.size() && player.getInventory().size()<6) {
				player.getInventory().add(drawerLoot.remove(square-1));
			}if (square2 != -1 && square2 <= player.getInventory().size() & drawerLoot.size() <12) {
				drawerLoot.add(player.getInventory().remove(square2-1));
			}

		}else if (crafting) {
			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			int mouseX = pointerInfo.getLocation().x	;
			int mouseY = pointerInfo.getLocation().y-50;
			int square = -1;
			// Loop through each square
			for (int i = 0; i < 6; i++) {
				int topLeftX = inventoryCordsX[i];
				int topLeftY = 620;
				if (mouseX >= topLeftX && mouseX <= topLeftX + 82 &&
						mouseY >= topLeftY && mouseY <= topLeftY + 82) {
					square = i+1;
					break; 
				}
			}if (square != -1 && square <= player.getInventory().size()) {
				craftingItems.add(player.getInventory().remove(square-1));
			}
			System.out.println(mouseY+50);
			System.out.println(mouseX >= 848 && mouseX <= 1031 && mouseY+50 >= 58250 && mouseY+50 <= 62150);
			if (mouseX >= 848 && mouseX <= 1031 && mouseY+50 >= 629 && mouseY+50 <= 672) {

				handleCrafting();
			}
		}
		else {

			PointerInfo pointerInfo = MouseInfo.getPointerInfo();
			int mouseX = pointerInfo.getLocation().x-74;
			int mouseY = pointerInfo.getLocation().y+3;
			int mouseRow = (int) Math.round((mouseY +map.getY()) / 50)-3;
			int mouseCol = (int) Math.round((mouseX +map.getX()) / 50)+1;
			int arrRow = (int) Math.round((mouseY - 115) / 29.1818) + 1;
			int arrCol = (int) Math.round((mouseX - 10) / 29.1818);
			int worldRow = (int) Math.round((player.getY() + map.getY()) / 50);
			int worldCol = (int) Math.round((player.getX() + map.getX() - 10) / 50);
			int[] returnArr = { arrRow, arrCol };
			System.out.println(mouseY + " " + mouseX);
			System.out.println(arrRow + " " + arrCol);
			System.out.println(worldRow + " " + worldCol);
			System.out.println(mouseRow + " " +mouseCol);
			if (state == 0) {
				if (mouseX >= 564 && mouseX <= 972 && mouseY >= 640 && mouseY <= 810) {
					state = 1;
				}
				else if (mouseX >= 130 && mouseX <= 536 && mouseY >= 640 && mouseY <= 810) {
					state = 3;
				}
				else if (mouseX >= 998 && mouseX <= 1410 && mouseY >= 640 && mouseY <= 810) {
					state = 4;
				}
			} else if (state == 3 || state == 4) {
				if ( mouseX >= 46 && mouseX <= 178 && mouseY >= 717 && mouseY <= 791 || mouseX >= -54 && mouseX <= 69 && mouseY >= 765 && mouseY <= 882) {
					state = 0;
				}
			}
		}
		






	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	//Our main method we adjust our JFrame as well as make our new TheEscapists() instance.
	public static void main(String[] args) {
		JFrame frame = new JFrame("The Escapists");
		TheEscapists gamePanel = new TheEscapists();

		frame.add(gamePanel);
		frame.addKeyListener(gamePanel);
		frame.addMouseListener(gamePanel);
		frame.addMouseMotionListener(gamePanel);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	public void keyTyped(KeyEvent e) {
	}

	//This method is triggered whenever a key is pressed and is used to handle moving and changing state
	//Parameters: KeyEvent e
	//Return: Void
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = true;
			right = false;	
			if (!directionChanged) {
				player.setCharacterFrame(9);
				directionChanged = true;
			}
		}
		if (key == KeyEvent.VK_D) {
			right = true;
			left = false;
			if (!directionChanged) {
				player.setCharacterFrame(15);	
				directionChanged = true;
			}
		}
		if (key == KeyEvent.VK_W) {
			up = true;
			down = false;
			if (!directionChanged) {
				player.setCharacterFrame(1);	
				directionChanged = true;
			}
		}
		if (key == KeyEvent.VK_S) {
			down = true;
			up = false;
			if (!directionChanged) {
				player.setCharacterFrame(5);
				directionChanged = true;
			}
		}
		repaint();
		// 

		if (key == KeyEvent.VK_SPACE) {
			if (state == 0)
				state++;
			else 
				state--;
		}
	}

	//This overridden method is used to stop moving once a key is released and is also used to craft and open drawers.
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = false;
			player.setCharacterFrame(9);
			directionChanged = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
			player.setCharacterFrame(15);
			directionChanged = false;
		}
		if (key == KeyEvent.VK_W) {
			up = false;
			player.setCharacterFrame(1);
			directionChanged = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
			player.setCharacterFrame(5);
			directionChanged = false;	
		}if (key == KeyEvent.VK_E) {
			if (drawerOpened) {
				drawerOpened = false;
				drawerLoot = generateLoot();					

			}else {
				PointerInfo pointerInfo = MouseInfo.getPointerInfo();
				int mouseX = pointerInfo.getLocation().x-74;
				int mouseY = pointerInfo.getLocation().y+3;
				int mouseRow = (int) Math.round((mouseY +map.getY()) / 50)-3;
				int mouseCol = (int) Math.round((mouseX +map.getX()-30	) / 50)+1;
				int playerRow = (int) Math.round((player.getY() + map.getY()) / 50);
				int playerCol = (int) Math.round((player.getX() + map.getX() - 10) / 50);

				if (mouseRow == 90 && mouseCol == 95 && Math.abs(playerRow-mouseRow) <= 2 && Math.abs(playerCol - mouseCol) <= 2) {
					drawerOpened = true;
					drawerLoot.clear();
					drawerLoot.add(items[12]);
				}
				if ((mouseRow == 114 || mouseRow == 115) && (mouseCol== 85||mouseCol == 84) && Math.abs(playerRow-mouseRow) <= 2 && Math.abs(playerCol - mouseCol) <= 2) {
					drawerOpened = true;
					drawerLoot.clear();
					drawerLoot.add(items[13]);
				}
				if (mouseRow == 23 && mouseCol == 110 && Math.abs(playerRow-mouseRow) <= 2 && Math.abs(playerCol - mouseCol) <= 2) {
					drawerOpened = true;
					drawerLoot.clear();
					drawerLoot.add(items[14]);
				}
				if (mouseRow >= 106 && mouseRow <= 108 && mouseCol >= 71 && mouseCol <= 77) {
					state = 6;
				}
				System.out.println(mouseRow + " " + mouseCol);
				if (map.getMapArr()[mouseRow][mouseCol] == 9 && Math.abs(playerRow-mouseRow) <= 2 && Math.abs(playerCol - mouseCol) <= 2) {
					drawerOpened = true;
				}
			}
		}if (key == KeyEvent.VK_Q) {
			crafting = !crafting;
			System.out.println(dontRefundItems);
			if (!dontRefundItems) {
				for (int i = craftingItems.size()-1; i >= 0; i--) {
					player.getInventory().add(craftingItems.remove(i));	
				}
			}else {
				dontRefundItems=false;
			}
			craftingItems.clear();

		}

	}


}