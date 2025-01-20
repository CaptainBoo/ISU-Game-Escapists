import java.awt.Image;

public class Guard extends Person {

	private boolean heat;
	
	public Guard(String name,Image[] playerFrames) {
		super(name,playerFrames);
		this.heat = false;
	}

}
