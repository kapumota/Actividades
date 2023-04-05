package sprint1_0.produccion;

public class Board {
    private int[][] grid;
    private char turn = 'X';

    public Board() {
        grid = new int[3][3];
    }

    public int getCell(int row, int column) {
        return grid[row][column];
    }

    public char getTurn() {
        return turn;
    }
}
