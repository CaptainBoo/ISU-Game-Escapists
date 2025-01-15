import java.awt.*;
import java.util.*;
import java.util.List;

abstract public class Person {

	private String name;
	protected int health, x, y;
	private Rectangle hitbox;
	
	private boolean currentlyPathfinding;
	private int destX, destY;

	protected int frameIndex;

	private Item[] inventory = new Item[6];

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

	public void movement(int[][]grid, int mapLength, int mapWidth) {
		destX = (int) (Math.random() * mapLength + 1);
		destY = (int) (Math.random() * mapWidth + 1);
		System.out.println("Point is " + destX + " " + destY);
		currentlyPathfinding = true;
		
		List<int[]> path = findPath(grid, new int[]{x/29,y/29}, new int[]{destX/29,destY/29});
		System.out.println(path);
	}

	public List<int[]> findPath(int[][] grid, int[] start, int[] goal) {
		int[]dx = {0, 1, 0, -1}; // Directions: Right, Down, Left Up
		int[]dy = {1, 0, -1, 0};
		int rows = grid.length;
		int cols = grid.length;
		
		Queue <int[]> queue = new LinkedList<>();
		queue.add(start);
		
		while(!queue.isEmpty()) {
			int[] currentPos = queue.poll();
			int cx = currentPos[0], cy = currentPos[1];
			
			if (cx == goal[0] && cy == goal[1]) {
				List<int[]> path = new ArrayList<>();
				while(grid[cx][cy] != -1) {
					path.add(new int[] {cx,cy});
					int temp = grid[cx][cy];
					cx = temp / cols;
					cy = temp % cols;
				}
				path.add(start);
				Collections.reverse(path);
				return path;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = cx + dx[i];
				int ny = cy + dy[i];
				if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && grid[nx][ny] == 0) {
					grid[nx][ny] = cx * cols + cy; // Store the parent info directly in the grid
					queue.add(new int[] {nx, ny});
				}
			}
		}
		return Collections.emptyList();
		
	
		
//		if (!currentlyPathfinding) {
//			findRandomPoint(1000, 1000);
//		}
//		
//		if (Math.hypot(x-destX, y-destY) < 5) {
//			currentlyPathfinding = false;
//			return;
//		}
//		
//		if (x < destX) {
//			x++;
//		}
//		else {
//			x--;
//		}
//		if (y < destY) {
//			y++;
//		}
//		else {
//			y--;
//		}
	}
}