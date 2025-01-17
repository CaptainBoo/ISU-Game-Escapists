import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

abstract public class Person {

	private String name;
	protected int health, x, y;
	private Rectangle hitbox;

	private boolean currentlyPathfinding;
	private int destX, destY;

	protected int frameIndex;

	private Item[] inventory = new Item[6];

	// Directions for pathfinding and movement
	// Directions: Down, Right, Up, Left
	private static int[] dx = {0, 1, 0, -1};
	private static int[] dy = {1, 0, -1, 0};

	public Person (String name) {
		this.name = name;
		this.health = 100;
		this.hitbox = new Rectangle(40, 90);
		x = 3000;
		y = 2000;

	}

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

	public void movement(int[][]grid) {
		List<int[]> path = null;
		int size = 0;
		if (!currentlyPathfinding) {
			destX = (int) (Math.random() * grid.length + 1);
			destY = (int) (Math.random() * grid[0].length + 1);
			System.out.println("Destination is " + destX + " " + destY);
			path = findPath(grid, new int[]{x/29,y/29}, new int[]{destX,destY});
			size = path.size();
			currentlyPathfinding = true;
		}

		for (int i = 0; i < size; i++) {
			int dir = 0;
			System.out.println("Currently at " + x/29 + " " + y/29);
			int[] nextMove = path.remove(0);
			if (nextMove[0] == 0 && nextMove[1] == 1) {
				System.out.println("move down");
				dir = 0;
			} else if (nextMove[0] == 1 && nextMove[1] == 0){
				System.out.println("move right");
				dir = 1;
			} else if (nextMove[0] == 0 && nextMove[1] == -1) {
				System.out.println("move up"); 
				dir = 2;
			} else if (nextMove[0] == -1 && nextMove[1] == 0) {
				System.out.println("move left");
				dir = 3;
			}
			for (int step = 0; step < 29; step++) {
				if (dir == 0) {
					y++;
				} else if (dir == 1) {
					x++;
				} else if (dir == 2) {
					y--;
				} else if (dir == 3) {
					x--;
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		currentlyPathfinding = false;
	}

	public static List<int[]> findPath(int[][] map, int[] start, int[] goal) {
		int rows = map.length;
		int cols = map[0].length;

		boolean[][] visited = new boolean[rows][cols];
		int[][] parent = new int[rows][cols]; 

		for (int i = 0; i < parent.length; i++) {
			for (int j = 0; j < parent[0].length; j++) {
				parent[i][j] = -1;
			}
		}

		Queue<int[]> queue = new LinkedList<>();
		queue.add(start);
		visited[start[0]][start[1]] = true;

		while (!queue.isEmpty()) {
			int[] current = queue.poll();
			int cx = current[0], cy = current[1];

			if (cx == goal[0] && cy == goal[1]) {
				List<int[]> path = new ArrayList<>();
				while (parent[cx][cy] != -1) {
					path.add(new int[]{cx, cy});
					int temp = parent[cx][cy];
					cx = temp / cols;
					cy = temp % cols;
				}
				path.add(start);
				Collections.reverse(path);

				// Convert path to movement directions
				List<int[]> directions = new ArrayList<>();
				for (int i = 1; i < path.size(); i++) {
					int[] prev = path.get(i - 1);
					int[] curr = path.get(i);
					directions.add(new int[]{curr[0] - prev[0], curr[1] - prev[1]});
				}
				return directions;
			}

			for (int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && !visited[nx][ny] && map[nx][ny] == 0) {
					visited[nx][ny] = true;
					parent[nx][ny] = cx * cols + cy; // Encode parent as an integer
					queue.add(new int[]{nx, ny});
				}
			}
		}

		return Collections.emptyList();
	}
}