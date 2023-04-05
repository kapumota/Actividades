package sprint2_1.produccion;

public class Board {
    public enum Cell {EMPTY, CROSS, NOUGHT}

    private static final int TOTALROWS = 3;
    private static final int TOTALCOLUMNS = 3;

    private Cell[][] grid;
    private char turn;

    public Board() {
        grid = new Cell[TOTALROWS][TOTALCOLUMNS];
        initBoard();
    }

    public void initBoard() {
        for (int row = 0; row < TOTALROWS; row++) {
            for (int column = 0; column < TOTALCOLUMNS; column++) {
                grid[row][column] = Cell.EMPTY;
            }
        }
        turn = 'X';
    }

    public int getTotalRows() {
        return TOTALROWS;
    }

    public int getTotalColumns() {
        return TOTALCOLUMNS;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public char getTurn() {
        return turn;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = (turn == 'X') ? Cell.CROSS : Cell.NOUGHT;
            turn = (turn == 'X') ? 'O' : 'X';
        }
    }
}
