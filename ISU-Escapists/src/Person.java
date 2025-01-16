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
//		System.out.println("Point is " + destX + " " + destY);
		currentlyPathfinding = true;
		
		List<int[]> path = findPath(grid, new int[]{x/29,y/29}, new int[]{destX/29,destY/29});
		for (int i = 0; i < path.size(); i++) {
			System.out.println(Arrays.toString(path.get(i)));
		}
	}
	
	public static List<int[]> findPath(int[][] map, int[] start, int[] goal) {
	    int rows = map.length;
	    int cols = map[0].length;
	    // Directions: Down, Right, Up, Left
	    int[] dx = {0, 1, 0, -1};
	    int[] dy = {1, 0, -1, 0};

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