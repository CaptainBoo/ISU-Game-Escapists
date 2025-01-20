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

	Graphics offScreenBuffer;
	Image offScreenImage, startMenu, credits, instructions, gameOver;
	int FPS = 60;
	int frame = 0;
	int mouseX, mouseY;
	Thread thread;
	Map map;
	boolean left, right, up, down;

	Set<Integer> keys = new HashSet<Integer>();

	Image inventory;
	Item[] items;

	Image[] prisonerFrames;
	Image[] guardFrames;

	Player player = new Player();

	Prisoner[] prisoners;
	Guard[] guards;

	Clip mainMenu, ambient, heat;
	int state = 0; // 0 = main menu, 1 = in game, 2 = heat (guard angry), 3 = credits, 4 = instructions, 5 = game over
	int previousState = -1;

	public TheEscapists() {
		// Make my class instances
		map = new Map();

		// Set up Panel and thread
		setPreferredSize(new Dimension(1472, 832));
		setLocation(100, 100);
		thread = new Thread(this);
		thread.start();
	}

	public void paintComponent(Graphics graphic) {
		// super.paintComponent(g);
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

		if (state == 0) {
			g.drawImage(startMenu, 0, 0, 1472, 832, this);
		} else if (state == 1 || state == 2) {
			g.drawImage(map.getImage(), -map.getX(), -map.getY(), this);

			//			g.drawImage(itemImages[0], 200, 100, 200, 200, this);
			//			g.drawImage(itemImages[1], 500, 700, 30, 30, this);

			g.drawImage(prisonerFrames[0], 736, 416, 40, 90, this);

			for (int i = 0; i < prisoners.length; i++) {
				g.drawImage(prisonerFrames[0], prisoners[i].getX() - map.getX(), prisoners[i].getY() - map.getY(),
						prisoners[i].getHitbox().width, prisoners[i].getHitbox().height, this);
			}
			for (int i = 0; i < guards.length; i++) {
				//				g.drawImage(prisonerFrames[0], guards[i].getX() - map.getX(), guards[i].getY() - map.getY(),
				//						guards[i].getHitbox().width, guards[i].getHitbox().height, this);

				g.fillRect(guards[i].getX() - map.getX(), guards[i].getY() - map.getY(),
						guards[i].getHitbox().width, guards[i].getHitbox().height);
			}

			g.drawImage(inventory, 140, 600, this);

			// Inventory
			for (int i = 0; i < player.getInventory().length; i++) {

			}

			// Heat
			g.setFont(new Font("CourierNew ", Font.BOLD, 40));
			g.setColor(Color.WHITE);

			g.drawString("Heat: " + player.getHeat(), 140, 550);

		} else if (state == 3) {
			g.drawImage(credits, 0, 0, 1472, 832, this);
		} else if (state == 4) {
			g.drawImage(instructions, 0, 0, 1472, 832, this);
		} else if (state == 5) {
			g.drawImage(gameOver, 0, 0, 1472, 832, this);
		}

	}

	public void initialize() {
		inventory = Toolkit.getDefaultToolkit().getImage("images/inventory.png");

		items = new Item[12];
		items[0] = new Item("comb", Toolkit.getDefaultToolkit().getImage("images/comb.png"));
		items[1] = new Item("contraband_pouch", Toolkit.getDefaultToolkit().getImage("images/contraband_pouch.png"));
		items[2] = new Item("crowbar", Toolkit.getDefaultToolkit().getImage("images/crowbar.png"));
		items[3] = new Item("duct_tape", Toolkit.getDefaultToolkit().getImage("images/duct_tape.png"));
		items[4] = new Item("flimsy_pickaxe", Toolkit.getDefaultToolkit().getImage("images/flimsy_pickaxe.png"));
		items[5] = new Item("foil", Toolkit.getDefaultToolkit().getImage("images/foil.png"));
		items[6] = new Item("inmate_outfit", Toolkit.getDefaultToolkit().getImage("images/inmate_outfit.png"));
		items[7] = new Item("jar_of_ink", Toolkit.getDefaultToolkit().getImage("images/jar_of_ink.png"));
		items[8] = new Item("lighter", Toolkit.getDefaultToolkit().getImage("images/lighter.png"));
		items[9] = new Item("molten_plastic", Toolkit.getDefaultToolkit().getImage("images/molten_plastic.png"));
		items[10] = new Item("red_key", Toolkit.getDefaultToolkit().getImage("images/red_key.png"));
		items[11] = new Item("tool_handle", Toolkit.getDefaultToolkit().getImage("images/tool_handle.png"));

		prisonerFrames = new Image[1];
		prisonerFrames[0] = Toolkit.getDefaultToolkit().getImage("images/escapists_character_temp.png");

		startMenu = Toolkit.getDefaultToolkit().getImage("images/main_menu.png");
		credits = Toolkit.getDefaultToolkit().getImage("images/credits.png");
		instructions = Toolkit.getDefaultToolkit().getImage("images/instructions.png");
		gameOver = Toolkit.getDefaultToolkit().getImage("images/gameover.png");

		prisoners = new Prisoner[2];
		prisoners[0] = new Prisoner("Prisoner 1");
		prisoners[1] = new Prisoner("Prisoner 2");

		guards = new Guard[1];
		guards[0] = new Guard("Guard 1");


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

	public void move() {
		if (player.isCollision(map, up, down, left, right)) {
			if (left) {
				map.move("left");
			}
			if (right) {
				map.move("right");
			}
			if (down) {
				map.move("down");
			}
			if (up) {
				map.move("up");
			}
			repaint();
		}
	}

	public void update() {
		//		System.out.println("player position is " + ((map.getX() + 736) / 50) + " " + ((map.getY() + 416) / 50));
		for (Prisoner prisoner : prisoners) {
			prisoner.randomMovement(map.getMapArr(), map);
		}
		for (Guard guard : guards) {

			//			guard.chasePlayer(map.getMapArr(), map, player);

			if (player.getHeat() >= 70 && (Math.hypot(guard.getX() - map.getX() - 736, guard.getY() - map.getY() - 416) < 500)) {
				guard.chasePlayer(map.getMapArr(), map, player);
			} else {
				guard.randomMovement(map.getMapArr(), map);
			}

			if (new Rectangle (guard.getX(), guard.getY(), 40, 90).intersects(new Rectangle(map.getX() + 736, map.getY() + 416, 40, 90))) {
				state = 5;
			}
		}
		if (frame % 60 == 0) player.setHeat(player.getHeat() - 1);
		if (state == 1 && player.getHeat() >= 70) {
			state = 2;
		}
		if (state == 2 && player.getHeat() < 70) {
			state = 1;
		}
	}

	public void run() {
		initialize();
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
			} 
		}
		previousState = state;	
	}

	public void stopAllSounds() {
		if (mainMenu.isRunning()) mainMenu.stop();
		if (ambient.isRunning()) ambient.stop();
		if (heat.isRunning()) heat.stop();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		int mouseX = pointerInfo.getLocation().x;
		int mouseY = pointerInfo.getLocation().y;
		int arrRow = (int) Math.round((mouseY - 115) / 29.1818) + 1;
		int arrCol = (int) Math.round((mouseX - 10) / 29.1818);
		int worldRow = (int) Math.round((player.getY() + map.getY()) / 50);
		int worldCol = (int) Math.round((player.getX() + map.getX() - 10) / 50);
		int[] returnArr = { arrRow, arrCol };
		System.out.println(mouseY + " " + mouseX);
		System.out.println(arrRow + " " + arrCol);
		System.out.println(worldRow + " " + worldCol);

		if (state == 0) {
			if (mouseX >= 536 && mouseX <= 936 && mouseY >= 620 && mouseY <= 770) {
				state = 1;
			}
			else if (mouseX >= 100 && mouseX <= 500 && mouseY >= 620 && mouseY <= 770) {
				state = 3;
			}
			else if (mouseX >= 972 && mouseX <= 1372 && mouseY >= 620 && mouseY <= 770) {
				state = 4;
			}
		} else if (state == 3 || state == 4) {
			if ( mouseX >= 16 && mouseX <= 146 && mouseY >= 687 && mouseY <= 817) {
				state = 0;
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

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = true;
		}
		if (key == KeyEvent.VK_W) {
			up = true;
		}
		if (key == KeyEvent.VK_D) {
			right = true;
		}
		if (key == KeyEvent.VK_S) {
			down = true;
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

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A) {
			left = false;
		}
		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
		}
	}

}