public class Mouse {
    private int row;
    private int col;
    private boolean stuck = false;

    public Mouse(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // TODO stuck doesn't work for 2-player
    public boolean move(int dest_row, int dest_col) {
        stuck = dest_row == row && dest_col == col;
        row = dest_row;
        col = dest_col;
        return true;
    }

    public boolean isStuck() {
        return stuck;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
