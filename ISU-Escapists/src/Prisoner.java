import java.awt.Image;

public class Prisoner extends Person{
	private int characterFrame;
	private int opinion;
	
	public Prisoner(String name,Image[] playerFrames) {
		super(name, playerFrames);
		this.opinion = 40;
		characterFrame = 0;
	}
	
	public int getOpinion() {
		return this.opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

}
