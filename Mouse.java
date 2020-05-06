public class Mouse {
    private int row, prev_row = 0;
    private int col, prev_col = 0;

    public Mouse(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean move(int dest_row, int dest_col) {
        if ( !GameBoard.isInGrid(dest_row, dest_col) || Math.abs(dest_row - row) > 1 || Math.abs(dest_col - col) > 1 ) {
            return false;
        }
        this.prev_row = this.row;
        this.prev_col = this.col;
        this.row = dest_row;
        this.col = dest_col;
        return true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getPrevRow() {
        return prev_row;
    }

    public int getPrevCol() {
        return prev_col;
    }
}
