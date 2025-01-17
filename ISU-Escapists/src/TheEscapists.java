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
	
	int frame = 0;
	Graphics offScreenBuffer;
	Image offScreenImage;
	int FPS = 60;
	int mouseX, mouseY;
	Thread thread;
	Map map;
	boolean left,right,up,down;
	
	Set<Integer> keys = new HashSet<Integer>();

	Image[] images;
	Image[] playerFrames;

	Player player = new Player();
	
	Prisoner[] prisoners;

	public TheEscapists () {
		//Make my class instances
		map = new Map();
				
		//Set up Panel and thread
		setPreferredSize(new Dimension(1472, 832));
		setLocation(100, 100);
		thread = new Thread(this);
		thread.start();
		
		
	}

	public void paintComponent (Graphics graphic) {
		//super.paintComponent(g);
		Graphics2D g = (Graphics2D) graphic;
		super.paintComponent(g);

		if (offScreenBuffer == null)
		{
			offScreenImage = createImage (this.getWidth (), this.getHeight ());
			offScreenBuffer = offScreenImage.getGraphics ();
		}

//		offScreenBuffer.drawImage(map.getImage(),-map.getX(),-map.getY(),this);
//		offScreenBuffer.setColor(new Color(120,137,148));
//		offScreenBuffer.fillRect(100, 100, 120, 120);
//		offScreenBuffer.setColor(new Color(48, 55, 65,150));
//		offScreenBuffer.fillRect(110, 110, 90, 90);
//		g.drawImage(offScreenImage, 0, 0, this);
		
		g.drawImage(map.getImage(),-map.getX(),-map.getY(),this);

		g.drawImage (images[0],200,100, 200, 200,this);
		g.drawImage (images[1],500,700, 30, 30,this);

		
		g.drawImage(playerFrames[0], 736, 416,40,90, this);
		
		for (int i = 0; i < prisoners.length; i++) {
			g.drawImage(playerFrames[0], prisoners[i].getX() - map.getX(), prisoners[i].getY() - map.getY(), prisoners[i].getHitbox().width, prisoners[i].getHitbox().height, this);
	
		}
	}
	public void initialize() {
		
		images = new Image[5];
		prisoners = new Prisoner[1];
		prisoners[0] = new Prisoner("John");
//		prisoners[1] = new Prisoner("Eric");
//		prisoners[1].movement(map.getMapArr(), frame, FPS);
		
		images [0]= Toolkit.getDefaultToolkit().getImage("flimsy_pickaxe.png");
		Image character0 = Toolkit.getDefaultToolkit().getImage("escapists_character_temp.png");

		playerFrames = new Image[1];
		playerFrames[0] = character0;
		
		
	}
	
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
		}else {
			//System.out.println("ran");
		}
	}

	public void update() {
		for (Prisoner prisoner : prisoners) {
			prisoner.movement(map.getMapArr());
		}
	}

	public void run() {
		initialize();
		while(true) {
			frame++;
			
			update();
			this.repaint();
			
			move();
			
			try {
				Thread.sleep(1000/FPS);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		int mouseX = pointerInfo.getLocation().x;
		int mouseY = pointerInfo.getLocation().y;
		int arrRow = (int)Math.round((mouseY-115)/29.1818)+1;
		int arrCol = (int)Math.round((mouseX-10)/29.1818);
		int worldRow = (int)Math.round((player.getY()+map.getY())/50);
		int worldCol = (int)Math.round((player.getX()+map.getX()-10)/50);
		int[] returnArr = {arrRow,arrCol};
		System.out.println(mouseY + " " + mouseX);
		System.out.println(arrRow + " " + arrCol);
		System.out.println(worldRow + " " + worldCol);
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