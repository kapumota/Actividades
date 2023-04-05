package sprint3_2.produccion;

public class TicTacToeGame {
    private static final int TOTALROWS = 3;
    private static final int TOTALCOLUMNS = 3;

    public enum Cell {
        EMPTY, CROSS, NOUGHT
    }

    private Cell[][] grid;
    private char turn;

    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private GameState currentGameState;

    public TicTacToeGame() {
        grid = new Cell[TOTALROWS][TOTALCOLUMNS];
        initGame();
    }

    private void initGame() {
        for (int row = 0; row < TOTALROWS; ++row) {
            for (int col = 0; col < TOTALCOLUMNS; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        currentGameState = GameState.PLAYING;
        turn = 'X';
    }

    public void resetGame() {
        initGame();
    }

    public int getTotalRows() {
        return TOTALROWS;
    }

    public int getTotalColumns() {
        return TOTALCOLUMNS;
    }

    /**
     *
     * @precond: none
     * @postcond: retorna Cell.EMPTY, Cell.CROSS, o Cell.NAUGHT if row >= 0 && row
     *            < TOTALROWS && column >= 0 && column < TOTALCOLUMN en otro caso
     *
     */
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

    /**
     * @precond: none
     * @postcond: si (row, column) es una celda vacia valida,
     *            la ficha del jugador se ha colocado en la celda
     *             y el turno ha cambiado al otro jugador
     *
     */
    public void makeMove(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = (turn == 'X') ? Cell.CROSS : Cell.NOUGHT;
            updateGameState(turn, row, column);
            turn = (turn == 'X') ? 'O' : 'X';
        }
    }

    private void updateGameState(char turn, int row, int column) {
        if (hasWon(turn, row, column)) {
            currentGameState = (turn == 'X') ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (isDraw()) {
            currentGameState = GameState.DRAW;
        }

    }

    private boolean isDraw() {
        for (int row = 0; row < TOTALROWS; ++row) {
            for (int col = 0; col < TOTALCOLUMNS; ++col) {
                if (grid[row][col] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasWon(char turn, int row, int column) {
        Cell token = (turn == 'X') ? Cell.CROSS : Cell.NOUGHT;
        return (grid[row][0] == token
                && grid[row][1] == token && grid[row][2] == token
                || grid[0][column] == token
                && grid[1][column] == token && grid[2][column] == token
                || row == column
                && grid[0][0] == token && grid[1][1] == token && grid[2][2] == token
                || row + column == 2
                && grid[0][2] == token && grid[1][1] == token && grid[2][0] == token);
    }

    public GameState getGameState() {
        return currentGameState;
    }

}
