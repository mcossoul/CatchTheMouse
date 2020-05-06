public class GameBoard {
	private int[][] grid; 						// the grid that stores the pieces
	private Mouse mouse;

	public GameBoard(int width, int height) {
		grid = new int[height][width];
		mouse = new Mouse(height/2, width/2);

		// Initialize starting positions
		grid[ mouse.getRow() ][ mouse.getCol() ] = 1;
		initWalls(3);
	}

	private void initWalls(int num) {
		int row=0, col=0;
		for (int i = 0; i < num; i++) {
			do {
				row = (int)( Math.random() * grid.length );
				col = (int)( Math.random() * grid[0].length  );
			} while (grid[row][col] != 0);
			grid[row][col] = 2; // place a wall
		}
	}

	// Make the requested move at (row, col) by changing the grid.
	// returns false if no move was made, true if the move was successful.
	public boolean move(int row, int col) {
		// TODO necessary?
		if (!mouse.move(row, col)) { return false; }

		grid[mouse.getRow()][mouse.getCol()] = 1;
		grid[mouse.getPrevRow()][mouse.getPrevCol()] = 0;
		return true;
	}

	/*
	 * Return true if the game is over. False otherwise.
	 */
	public boolean isGameOver() {

		/*** YOU COMPLETE THIS METHOD ***/

		return false;
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public static boolean isInGrid(int row, int col) {
		// TODO
//		return row >=0 && row < grid.length && col >= 0 && col < grid[0].length;
		return true;
	}
}