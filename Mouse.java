public class Mouse {
    private GameBoard game;
    private int row;
    private int col;

    public Mouse(GameBoard game, int row, int col) {
        this.game = game;
        this.row = row;
        this.col = col;
    }

    public boolean move(int dest_row, int dest_col) {
        if ( !isMovePossible() ) {
            return false;
        }
        if ( !isMoveValid(dest_row, dest_col) ) {
            return false;
        }
        game.movePiece(row, col, dest_row, dest_col);
        row = dest_row;
        col = dest_col;
        return true;
    }

    public boolean move() {
        if ( !isMovePossible() ) {
            return false;
        }
        int[] dest = game.findDirection(row, col);
        game.movePiece(row, col, dest[0], dest[1]);
        row = dest[0];
        col = dest[1];
        return true;
    }

    public boolean isMovePossible() {
        int count = 0;
        for (int r = row-1; r <= row+1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                // skip non-hexagonal moves
                if (r != row && (row % 2 == 0 && c == col+1 || row % 2 == 1 && c == col-1)) {
                    continue;
                }
                if (game.isInGrid(r, c) && game.getGrid()[r][c] == Const.PIECE_NONE) {
                    count++;
                }
            }
        }
        return count != 0;
    }

    public boolean isMoveValid(int dest_row, int dest_col) {
        if ( game.isInGrid(dest_row, dest_col) &&
             game.getGrid()[dest_row][dest_col] == Const.PIECE_NONE )
        {
            if (dest_row == row) {
                return Math.abs(dest_col - col) < 2;
            }
            else if ( Math.abs(dest_row-row) < 2 ) {
                return row%2 == 0 && (dest_col == col || dest_col == col-1) ||
                       row%2 == 1 && (dest_col == col || dest_col == col+1);
            }
        }
        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
