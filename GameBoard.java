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
				row = (int)( 1 + Math.random() * (grid.length - 2) );
				col = (int)( 1 + Math.random() * (grid[0].length - 2)  );
			} while (grid[row][col] != 0);
			grid[row][col] = 2; // place a wall
		}
	}

	// Make the requested move at (row, col) by changing the grid.
	// returns false if no move was made, true if the move was successful.
	public boolean moveMouse(int dest_row, int dest_col) {
		if ( !isInGrid(dest_row, dest_col) || grid[dest_row][dest_col] != 0 ||
				Math.abs(dest_row - mouse.getRow()) > 1  || Math.abs(dest_col - mouse.getCol()) > 1) {
			return false;
		}

		// Check diagonal moves
		if ( dest_row != mouse.getRow()	) {
			if ( dest_col%2 == 0 && dest_col < mouse.getCol() ||
				 dest_col%2 == 1 && dest_col > mouse.getCol() ) {
				return false;
			}
		}

        grid_move( mouse.getRow(), mouse.getCol(), dest_row, dest_col );
        mouse.move(dest_row, dest_col);
		return true;
	}

	public boolean addWall(int dest_row, int dest_col) {
		if ( !isInInsideGrid(dest_row, dest_col) || grid[dest_row][dest_col] !=0) {
			return false;
		}

		grid[ dest_row ][ dest_col ] = 2;
		return true;
	}

	public void grid_move(int row, int col, int dest_row, int dest_col) {
		int piece = grid[row][col];
		grid[ dest_row ][ dest_col ] = piece;
		grid[ row ][ col ] = 0;
	}

	/*
	 * Return true if the game is over. False otherwise.
	 */
	public boolean isGameOver() {
		if (mouse.getRow() == 0 || mouse.getRow() == grid.length-1 || mouse.getCol() == 0 || mouse.getCol() == grid[0].length-1) {
			grid[ mouse.getRow() ][ mouse.getCol() ] = 3;
			return true;
		}
		return false;
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public boolean isInGrid(int row, int col) {
		return row >=0 && row < grid.length && col >= 0 && col < grid[0].length;
	}

	public boolean isInInsideGrid(int row, int col) {
		return row >0 && row < grid.length-1 && col > 0 && col < grid[0].length-1;
	}
}
