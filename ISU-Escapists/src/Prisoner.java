
public class Prisoner extends Person{
	
	private int opinion;
	
	public Prisoner(String name) {
		super(name);
		this.opinion = 40;
	}
	
	public int getOpinion() {
		return this.opinion;
	}

	public void setOpinion(int opinion) {
		this.opinion = opinion;
	}

}
