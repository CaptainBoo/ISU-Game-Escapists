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
	Image offScreenImage, map;
	int FPS = 60;
	int mouseX, mouseY;
	Thread thread;
	
	Set<Integer> keys = new HashSet<Integer>();

	Image[] images;
	Image[] playerFrames;

	Player player = new Player();
	Prisoner prisoner = new Prisoner("test");

	public TheEscapists () {

		BufferedImage image;
		try {
			image = ImageIO.read(new File("map5.png"));
			int scaledWidth = image.getWidth()*2; // Adjust the scale factor as needed
			int scaledHeight = image.getHeight()*2;
			map = image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);

		}catch (Exception e){
		}
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

		offScreenBuffer.drawImage(map,-3000,-2000,this);
		offScreenBuffer.setColor(new Color(120,137,148));
		offScreenBuffer.fillRect(100, 100, 120, 120);
		offScreenBuffer.setColor(new Color(48, 55, 65,150));
		offScreenBuffer.fillRect(110, 110, 90, 90);
		g.drawImage(offScreenImage, 0, 0, this);

		g.drawImage (images[0],200,100, 200, 200,this);
		g.drawImage (images[1],500,700, 30, 30,this);

		g.drawRect(prisoner.getX(), prisoner.getY(), prisoner.getHitbox().width, prisoner.getHitbox().height);
		g.drawImage(playerFrames[0], player.getX(), player.getY(),40,100, this);

	}



	public void initialize() {
		images = new Image[5];
		images [0]= Toolkit.getDefaultToolkit().getImage("flimsy_pickaxe.png");
		Image character0 = Toolkit.getDefaultToolkit().getImage("escapists_character_temp.png");

		playerFrames = new Image[1];
		playerFrames[0] = character0;
	}

	public void update() {
		prisoner.NPCmovement();
	}

	public void run() {
		initialize();
		while(true) {
			update();
			this.repaint();
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

	public void keyPressed(KeyEvent e) {keys.add(e.getKeyCode());
		runKeys();
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());
	}
	
	public void runKeys() {
		for (Integer key : keys) {
			if (key == KeyEvent.VK_A) {
				player.move("left");
			}
			if (key == KeyEvent.VK_W) {
				player.move("up");
			}
			if (key == KeyEvent.VK_D) {
				player.move("right");
			}
			if (key == KeyEvent.VK_S) {
				player.move("down");
			}
		}
	}
}
