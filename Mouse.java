public class Mouse {
    private int row, row_prev = 0;
    private int col, col_prev = 0;

    public Mouse(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean move(int dest_row, int dest_col) {
        this.row_prev = this.row;
        this.col_prev = this.col;
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

    public int getRowPrev() {
        return row_prev;
    }

    public int getColPrev() {
        return col_prev;
    }
}
