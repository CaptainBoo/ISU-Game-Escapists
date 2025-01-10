import java.awt.Image;

public class Player extends Person {

	public Player() {
		super("player");
	}

	public void move (String dir) {
		if (dir.equals("left")) {
			this.x--;
		} else if (dir.equals("up")) {
			this.y--;
		} else if (dir.equals("right")) {
			this.x++;
		} else if (dir.equals("down")) {
			this.y++;
		}
	}
}
