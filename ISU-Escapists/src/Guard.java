
public class Guard extends Person {

	private boolean chasingPlayer;
	private int tilesMoved; // The guard will refresh path after 3 steps.

<<<<<<< Updated upstream
	public Guard(String name) {
		super(name);
=======
	//Constructor for the guard class, inherits its playerFrames from its parent using the super constructor
	//Parameters: String name, Image[] playerFrames;
	//Return: None
	public Guard(String name,Image[] playerFrames) {
		super(name,playerFrames);
>>>>>>> Stashed changes
		this.chasingPlayer = false;
	}
	
	//This method is if you have a heat above 70 meaning you have done something band and the guards will now aggro you. 
	//They will path find towards you and if they touch you the game ends
	//The path finding works and they go towards you however, they are slow and can go through walls which is not good
	//Parameters: int[][] grid, Map map, Player player
	public void chasePlayer(int[][] grid, Map map, Player player) {
		// Method for guard to chase the player
		// Parameters: 2-d array of map, actual map object, player object/
		// Returns void as position changes directly
		// Position of guard and player
		int col = (int) Math.round((x) / 50);
		int row = (int) Math.round((y) / 50);
		int playerX = (int) Math.round((map.getX() + 736) / 50);
		int playerY = (int) Math.round((map.getY() + 416) / 50);
		if (!currentlyPathfinding) {
			// Use the findPath method in person class
			path = findPath(grid, new int[] { col, row }, new int[] { playerX, playerY });
			currentlyPathfinding = true;
		}
		// Move toward player similar to randomMovement method in person class
		if (!path.isEmpty() && step == 0) {
			int[] nextMove = path.remove(0);
			if (nextMove[0] == 0 && nextMove[1] == 1) {
				System.out.println("move down");
				dir = 0;
			} else if (nextMove[0] == 1 && nextMove[1] == 0) {
				System.out.println("move right");
				dir = 1;
			} else if (nextMove[0] == 0 && nextMove[1] == -1) {
				System.out.println("move up");
				dir = 2;
			} else if (nextMove[0] == -1 && nextMove[1] == 0) {
				System.out.println("move left");
				dir = 3;
			}
		}
		if (dir == 0) {
			y++;
		} else if (dir == 1) {
			x++;
		} else if (dir == 2) {
			y--;
		} else if (dir == 3) {
			x--;
		}
		step++;
		if (step == 50) {
			step = 0;
			tilesMoved++;
		}
		// After 3 tiles moved, find a new path towards the player
		if (tilesMoved == 3) {
			currentlyPathfinding = false;
			tilesMoved = 0;
		}
		if (path.isEmpty())
			currentlyPathfinding = false;
	}
}
