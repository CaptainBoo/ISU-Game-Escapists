import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

@SuppressWarnings("serial")
public class TheEscapists extends JPanel implements Runnable, MouseListener, MouseMotionListener {
	
	int FPS = 60;
	int mouseX, mouseY;
	Thread thread;
	
	Image[] images;
	
	Prisoner hi = new Prisoner("hi");
	
	public TheEscapists () {
		
		setPreferredSize(new Dimension(1920, 1024));
		setLocation(100, 100);
		thread = new Thread(this);
		thread.start();

		addMouseListener(this);
		addMouseMotionListener (this);
		
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawRect(10, 10, 100, 100);
		
		g2.drawImage (images[0],10,70, 200, 200,this);
		g2.drawImage (images[1],100,700, 144, 213,this);
		
		g2.drawRect(hi.getX(), hi.getY(), hi.getHitbox().width,  hi.getHitbox().height);
	}

	
	
	public void initialize() {
		images = new Image[5];
		images [0]= Toolkit.getDefaultToolkit().getImage("flimsy_pickaxe.png");
		images [1]= Toolkit.getDefaultToolkit().getImage("escapists_character_temp.png");
		
		
	}
	
	public void update() {
		hi.movement();
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
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
}
