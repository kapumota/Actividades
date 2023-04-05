package sprint2_0.produccion;

public class Board {
    private int[][] grid;
    private char turn = 'X';

    public Board() {
        grid = new int[3][3];
    }

    public int getCell(int row, int column) {
        if (row >= 0 && row < 3 && column >= 0 && column < 3)
            return grid[row][column];
        else
            return -1;
    }

    public char getTurn() {
        return turn;
    }

    public void makeMove(int row, int column) {
        if (row >= 0 && row < 3 && column >= 0 && column < 3
                && grid[row][column] == 0) {
            grid[row][column] = (turn == 'X')? 1 : 2;
            turn = (turn == 'X')? 'O' : 'X';
        }
    }
}
