import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

abstract public class Person {

	protected int step = 0;
	protected List<int[]> path = new ArrayList<>();
	private String name;
	protected int health, x, y;
	private Rectangle hitbox;
	protected int dir;

	protected boolean currentlyPathfinding;
	protected int destX;
	protected int destY;

	protected int frameIndex;

	private Item[] inventory = new Item[6];

	// Directions for pathfinding and movement
	// Directions: Down, Right, Up, Left
	private static int[] dx = { 0, 1, 0, -1 };
	private static int[] dy = { 1, 0, -1, 0 };

<<<<<<< Updated upstream
	public Person(String name) {
=======
	//Constructor for our person class. This is the parent of mutliple classes so it has the most 
	//To intilize.
	//Parameters: String name, Image[] playerFrames
	//Return: None 
	public Person(String name, Image[] playerFrames) {
>>>>>>> Stashed changes
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(40, 90);
		x = 3000;
		y = 2000;
	}

	// Getters and setters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Item[] getInventory() {
		return inventory;
	}

	public int getFrameIndex() {
		return frameIndex;
	}

<<<<<<< Updated upstream
	public void randomMovement(int[][] grid, Map map) {
		// Method for NPCs to idle and move around
		// Parameters: 2-d array of map, and actual map object
		// Returns void as position changes directly
=======
	//This is the random movement method that our npcs will use for path finding. It finds the shortest path to a point
	//With a different method and then travels towards that point. However it doesn't check bounderies all that well.
	//Parameter: int[][] grid, Map map, int frame.
	//Return: Void
	public void randomMovement(int[][] grid, Map map,int frame) {
>>>>>>> Stashed changes
		int row = 0, col = 0;
		col = (int) Math.round((x) / 50);
		row = (int) Math.round((y) / 50);
		// Find path using the findPath method
		if (!currentlyPathfinding) {
			while (path.isEmpty()) {
				// Random point
				destX = (int) (Math.random() * grid.length + 1);
				destY = (int) (Math.random() * grid[0].length + 1);
				path = findPath(grid, new int[] { col, row }, new int[] { destX, destY });
			}
			currentlyPathfinding = true;
		}

		// Step is a singular pixel movement
		if (step == 0) {
			int[] nextMove = path.remove(0);
			// Use the directions stored in the path list
			if (nextMove[0] == 0 && nextMove[1] == 1) {
				dir = 0;
			} else if (nextMove[0] == 1 && nextMove[1] == 0) {
				dir = 1;
			} else if (nextMove[0] == 0 && nextMove[1] == -1) {
				dir = 2;
			} else if (nextMove[0] == -1 && nextMove[1] == 0) {
				dir = 3;
			}
		}
		
		if (dir == 0) { // Down
			y++;
		} else if (dir == 1) { // Right
			x++;
		} else if (dir == 2) { // Up
			y--;
		} else if (dir == 3) { // Left
			x--;
		}
		step++;
		// If 50 pixels is moved, that means a full grid tile is traversed
		// Get next point from path
		if (step == 50) {
			step = 0;
		}
		if (path.isEmpty())
			currentlyPathfinding = false;
	}

	//This method calculates the shortest path to a random point by using breadth first search which is a 
	//Algorithm for path findign using queues. Unfotuatelly it doesn't seem to be working
	//Paramters: int[][] map, int[] start, int[] goal
	//Return: ArrayList<> The list of moves needed to reach the point
	public static ArrayList<int[]> findPath(int[][] map, int[] start, int[] goal) {
		// A method to find the fastest path to a random point using BFS
		// Parameters: The 2-d array of map, start position (x, y), and end position (x, y)
		// Returns an array list of arrays containing the direction {xDir, yDir}
		int rows = map.length;
		int cols = map[0].length;

		// Initialize 2 arrays of visited and parents
		// Visited makes sure the path doesn't loop on itself
		// Parent marks the coordinates of the point before the current point
		boolean[][] visited = new boolean[rows][cols];
		int[][] parent = new int[rows][cols];

		// Fill parent with -1 since it will be used to encode information later 
		// The information will be positive
		for (int i = 0; i < parent.length; i++) {
			for (int j = 0; j < parent[0].length; j++) {
				parent[i][j] = -1;
			}
		}

		// Queue of points to visit for bfs
		Queue<int[]> queue = new LinkedList<>();
		queue.add(start);
		visited[start[0]][start[1]] = true;

		// Loop through the queue of points
		while (!queue.isEmpty()) {
			int[] current = queue.poll();
			int cx = current[0], cy = current[1];

			// If the current point is the end position
			if (cx == goal[0] && cy == goal[1]) {
				// Create a list of previous points from start to end
				List<int[]> path = new ArrayList<>();
				// If the parent was accessed
				while (parent[cx][cy] != -1) {
					// Add the last point to the path
					path.add(new int[] { cx, cy });
					// Decode the information from the parent point
					int temp = parent[cx][cy];
					cx = temp / cols;
					cy = temp % cols;
				}
				path.add(start);
				Collections.reverse(path);

				// Convert path to movement directions
				ArrayList<int[]> directions = new ArrayList<>();
				for (int i = 1; i < path.size(); i++) {
					int[] prev = path.get(i - 1);
					int[] curr = path.get(i);
					// Subtract current point from previous point to get change in x and y
					directions.add(new int[] { curr[0] - prev[0], curr[1] - prev[1] });
				}
				return directions;
			}

			// Add points next to the current point using 4 cardinal directions
			for (int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && !visited[nx][ny] && map[nx][ny] == 0) {
					visited[nx][ny] = true;
					parent[nx][ny] = cx * cols + cy; // Encode parent as an integer
					queue.add(new int[] { nx, ny });
				}
			}
		}
		// If no path found
		return new ArrayList<>(Collections.emptyList());
<<<<<<< Updated upstream
=======
		
	}
	
	public Image[] getPlayerFrames() {
		return this.playerFrames;
	}
	
	public int getCharacterFrame() {
		return this.characterFrame;
	}
	
	//This is basically a setter
	public void incrementCharacterFrame(int increment) {
		this.characterFrame+= increment;
	}
	public void setCharacterFrame(int value) {
		this.characterFrame = value;
>>>>>>> Stashed changes
	}
}