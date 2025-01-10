import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

@SuppressWarnings("serial")
public class TheEscapists extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	Graphics offScreenBuffer;
	Image offScreenImage, map;
	int FPS = 60;
	int mouseX, mouseY;
	Thread thread;
	
	public TheEscapists () {
		BufferedImage image;
		try { 
			image = ImageIO.read(new File("map.jpeg"));
			int scaledWidth = image.getWidth() *9; // Adjust the scale factor as needed
			int scaledHeight = image.getHeight() * 9;
			map = image.getScaledInstance(scaledWidth,scaledHeight,Image.SCALE_SMOOTH);

		}catch (Exception e){
		}
		setPreferredSize(new Dimension(1920, 1088));
		setLocation(100, 100);
		thread = new Thread(this);
		thread.start();

		addMouseListener(this);
		addMouseMotionListener (this);
		
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
		
		offScreenBuffer.drawImage(map,-500,0,this);
		offScreenBuffer.setColor(new Color(120,137,148));
		offScreenBuffer.fillRect(100, 100, 120, 120);
		offScreenBuffer.setColor(new Color(48, 55, 65,150));
		offScreenBuffer.fillRect(110, 110, 90, 90);
		g.drawImage(offScreenImage, 0, 0, this);
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
	
	public void initialize() {
		
	}
	
	public void update() {
		
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
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
}
