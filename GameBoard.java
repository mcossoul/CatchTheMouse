import javax.naming.InitialContext;

public class GameBoard {
	private static final int MOVE_WEIGHT = 3;

	private int[][] grid, move_grid; // the grid that stores the pieces
	private Mouse mouse;
	private boolean debug = false;

	public GameBoard(int width, int height) {
		grid = new int[height][width];
		move_grid = new int[height][width];
		mouse = new Mouse(height/2, width/2);
	}

	public void reset(int walls) {
		grid[ mouse.getRow() ][ mouse.getCol() ] = Const.PIECE_MOUSE;
		initMoveGrid( MOVE_WEIGHT );
		initWalls( walls );
	}

	private void initMoveGrid( int weight ) {
		for (int r = 1; r <= move_grid.length / 2; r++) {
			for (int c = 1; c <= move_grid[0].length / 2; c++) {
				if (r == 1 || c == 1) {
					replicateQuarters(r, c, weight);
				} else {
					replicateQuarters(r, c, findMinValue(r, c) + weight);
				}
			}
		}
		if (debug) {
			displayGrid(move_grid);
		}
	}

	private void displayGrid(int[][] grid) {
		System.out.println("[DEBUG] grid:");
		for (int r = 0; r < grid.length; r++) {
			if (r%2 == 1) {
				System.out.print("  ");
			}
			for (int c = 0; c < grid[0].length; c++) {
				System.out.print(grid[r][c] + "   ");
			}
			System.out.println();
		}
	}

	private void replicateQuarters(int r, int c, int val) {
		int other_row = move_grid.length-1 - r;
		int other_col = move_grid[0].length-1 - c;

		move_grid[r][c] = val;
		move_grid[r][other_col] = val;
		move_grid[other_row][c] = val;
		move_grid[other_row][other_col] = val;
	}

	private int findMinValue(int row, int col) {
		int min = Integer.MAX_VALUE;
		for (int r = row-1; r <= row+1; r++) {
			for (int c = col-1; c <= col+1 ; c++) {
				// skip non hexagonal moves
				if ( r != row ) {
					if ( row%2 == 0 && c == col+1 || row%2 == 1 && c == col-1) {
						continue;
					}
				}

				if ( isInGrid(r, c) ) {
					if ( move_grid[r][c] > 0 && move_grid[r][c] < min ){
						min = move_grid[r][c];
					}
				}
			}
		}
		return min;
	}

	private int[] findDirection(int row, int col) {
		int min = Integer.MAX_VALUE;
		int[] min_coords = new int[2];
		int count = 0;
		for (int r = row-1; r <= row+1; r++) {
			for (int c = col-1; c <= col+1 ; c++) {
				// skip non hexagonal moves
				if ( r != row ) {
					if ( row%2 == 0 && c == col+1 || row%2 == 1 && c == col-1) {
						continue;
					}
				}

				if ( isInGrid(r, c) && grid[r][c] == Const.PIECE_NONE) {
				    count++;
                    if ( move_grid[r][c] < min ){
						min = move_grid[r][c];
						min_coords[0] = r;
						min_coords[1] = c;
					}
				}
			}
		}
		if (count == 0) { return new int[] {row, col}; }
        return min_coords;
	}

	private void initWalls(int num) {
		int row, col;
		for (int i = 0; i < num; i++) {
			do {
				row = (int)( 1 + Math.random() * (grid.length - 2) );
				col = (int)( 1 + Math.random() * (grid[0].length - 2)  );
			} while ( grid[row][col] != Const.PIECE_NONE );
			addWall(row, col);
		}
	}

	// Make the requested move at (row, col) by changing the grid.
	// returns false if no move was made, true if the move was successful.
	public boolean moveMouse(int dest_row, int dest_col) {
		if ( !isInGrid(dest_row, dest_col) || grid[dest_row][dest_col] != Const.PIECE_NONE ||
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

	public boolean moveMouse() {
		int[] dest = findDirection( mouse.getRow(), mouse.getCol() );
        grid_move( mouse.getRow(), mouse.getCol(), dest[0], dest[1] );
        mouse.move(dest[0], dest[1]);
		return true;
	}

	public boolean addWall(int dest_row, int dest_col) {
		if ( !isInInsideGrid(dest_row, dest_col) || grid[dest_row][dest_col] != Const.PIECE_NONE ) {
			return false;
		}

		grid[ dest_row ][ dest_col ] = Const.PIECE_WALL;
		updateMoveGrid(dest_row, dest_col, 5);
		if (debug) {
			displayGrid( grid );
		}
		return true;
	}

	private void updateMoveGrid(int row, int col, int radius) {
		for (int r = 1; r < move_grid.length-1; r++) {
			for (int c = 1; c < move_grid[0].length-1; c++) {
				int dist =  distance(row, col, r, c);
				if (dist <= radius) {
					move_grid[r][c] += radius - dist + 1;
				}
			}
		}
		if (debug) {
			displayGrid( move_grid );
		}
	}

	private int distance(int row, int col, int r, int c) {
		return (int)( Math.sqrt( Math.pow(row - r, 2) + Math.pow(col - c, 2) ) );
	}

	public boolean grid_move(int row, int col, int dest_row, int dest_col) {
	    if ( dest_row == row && dest_col == col ) { return false; }

        int piece = grid[row][col];
        grid[dest_row][dest_col] = piece;
        grid[row][col] = Const.PIECE_NONE;
        return true;
	}

	/*
	 * Return true if the game is over. False otherwise.
	 */
	public int isGameOver() {
		// Mouse lost
        if ( mouse.isStuck() ) {
            return Const.WIN_WALL;
        }
		// Mouse won: reached a border
        else if (mouse.getRow() == 0 || mouse.getRow() == grid.length-1 || mouse.getCol() == 0 || mouse.getCol() == grid[0].length-1) {
            grid[ mouse.getRow() ][ mouse.getCol() ] = Const.PIECE_CHEESE;
			return Const.WIN_MOUSE;
		}
		return Const.WIN_NONE; // game is not over
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public boolean isInGrid(int row, int col) {
		return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
	}

	public boolean isInInsideGrid(int row, int col) {
		return row > 0 && row < grid.length-1 && col > 0 && col < grid[0].length-1;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
