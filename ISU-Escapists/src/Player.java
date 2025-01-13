import java.awt.Image;

public class Player extends Person {

	public Player() {
		super("player");
	}

	public void move (String dir) {
		if (dir.equals("left")) {
			this.x-=8;
		} else if (dir.equals("up")) {
			this.y-=8;
		} else if (dir.equals("right")) {
			this.x+=8;
		} else if (dir.equals("down")) {
			this.y+=8;
		}
	}
}
