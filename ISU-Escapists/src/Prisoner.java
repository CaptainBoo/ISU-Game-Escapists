
public class Prisoner extends Person {

	private int opinion;

<<<<<<< Updated upstream
	public Prisoner(String name) {
		super(name);
=======
	//Constructor for prisoner class. Super() initlizes our playerFrames array through inhertience for animations
	//Parameters: String name, Image[] playerFrames
	public Prisoner(String name,Image[] playerFrames) {
		super(name,playerFrames);
>>>>>>> Stashed changes
		this.opinion = 40;
	}

	public int getOpinion() {
		return this.opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

}
