

public class Render {
	
	private static int x;
	private static int y;
	private static int xMid;
	private static int yMid;
	private static int[][] maze;
	
	public Render(int x, int y, int[][] maze) {
		this.x = x;
		this.y = y;
		this.xMid = (int) Math.floor((double) this.x/2);
		this.yMid = (int) Math.floor((double) this.y/2);
		this.maze = maze;
		
	}
	
	public static int calcDist(int x, int y) {
		int dist = Math.abs(yMid - y)+Math.abs(xMid - x);
		return dist;
	}
	
	public static String distString(int dist){
		String distVal;
		
			if (dist > 99){
				distVal = Integer.toString(dist);
			}else{
				if (dist > 9){
					distVal = "0"+Integer.toString(dist);
				}else{
					distVal = "00"+Integer.toString(dist);
				}
			}
		return distVal;
	}
	
	public static String navString(int dist){
		String distVal;
		

				if (dist > 9){
					distVal = Integer.toString(dist);
				}else{
					distVal = "0"+Integer.toString(dist);
				}

		return distVal;
	}
	
	
	public void display() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	/*
	 * Function: Output a rendering of the map as discovered by MicroMau5 indicating MicroMau5's position
	 */
	
	public static void track(Tuple<Integer,Integer> coord, int[][] expMaze, int[][] distMaze) {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((expMaze[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				String dist = distString(distMaze[j][i]);
				if ((i==coord.y) && (j==coord.x)){
					System.out.print((expMaze[j][i] & 8) == 0 ? "| * ": "  * ");
				}else{
					
					System.out.print((expMaze[j][i] & 8) == 0 ? "|   " : "    ");
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	
	/*
	 * Output a rendering of the map with an integer representation of the walls bounding each cell
	 */
	public void dirmark(int[][] expMaze, int[][] distMaze) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j< x; j++) {
				System.out.print((expMaze[j][i] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			for (int j = 0; j < x; j++) {
				String coord = distString(expMaze[j][i]);
				System.out.print((expMaze[j][i] & 8) == 0 ? "|"+coord+" " : " "+coord+" ");
				distMaze[j][i] = calcDist(i,j);
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	/*
	 * Output a rendering of the map as seen by MicroMau5 with each cell labeled with the least possible distance to the center.
	 */
	public void distmark(int[][] expMaze, int[][] distMaze) {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j< x; j++) {
				if (i>0){
					System.out.print(((expMaze[j][i] & 1) == 0)||((expMaze[j][i-1] & 2)==0) ? "+---" : "+   ");
				}else{
					System.out.print((expMaze[j][i] & 1) == 0 ? "+---" : "+   ");
				}
			}
			System.out.println("+");
			for (int j = 0; j < x; j++) {
				String dist = distString(distMaze[j][i]);
				if (j>0){
					System.out.print(((expMaze[j][i] & 8) == 0)||((expMaze[j-1][i] & 4) == 0) ? "|"+dist : " "+dist);
				}else{
					System.out.print((expMaze[j][i] & 8) == 0 ? "|"+dist : " "+dist);
				}
				
				distMaze[j][i] = calcDist(i,j);
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}

}
