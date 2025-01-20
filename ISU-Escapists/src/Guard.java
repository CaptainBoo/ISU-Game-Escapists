
public class Guard extends Person {

	private boolean chasingPlayer;
	private int tilesMoved; // The guard will refresh path after 3 steps.

	public Guard(String name) {
		super(name);
		this.chasingPlayer = false;
	}
	
	public void chasePlayer(int[][] grid, Map map, Player player) {
		int col = (int) Math.round((x) / 50);
		int row = (int) Math.round((y) / 50);
		int playerX = (int) Math.round((map.getX() + 736) / 50);
		int playerY = (int) Math.round((map.getY() + 416) / 50);
		int size = 0;
		if (!currentlyPathfinding) {
			path = findPath(grid, new int[] { col, row }, new int[] { playerX, playerY });
			size = path.size();
			currentlyPathfinding = true;
		}
		System.out.println("destination is " + playerX + " " + playerY);
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
		if (tilesMoved == 3) {
			currentlyPathfinding = false;
			tilesMoved = 0;
		}
		if (path.isEmpty())
			currentlyPathfinding = false;
	}
}
