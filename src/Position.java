public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Position pos) {
        if (row == pos.getRow() && col == pos.getCol()) {
            return true;
        }
        return false;
    }

    public Position translate(Direction dir) {
        return new Position(row + dir.getYchange(), col + dir.getXchange());
    }

    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}