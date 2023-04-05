package sprintX_autoplay;

import java.util.Random;

public class AutoTicTacToe extends TicTacToeGame {
    private char autoPlayer;

    public AutoTicTacToe() {
        this('O');
    }

    public AutoTicTacToe(char player) {
        this.autoPlayer = player;
        if (autoPlayer=='X') {
            makeFirstXMove();
        }
    }

    @Override
    public void resetGame() {
        super.resetGame();
        if (autoPlayer=='X') {
            makeFirstXMove();
        }
    }

    private void makeFirstXMove() {
        Random random = new Random();
        int position = random.nextInt(9);
        super.makeMove(position/3, position%3);
    }

    @Override
    public void makeMove(int row, int column) {
        if (row >= 0 && row < TOTALROWS && column >= 0 && column < TOTALCOLUMNS && grid[row][column] == Cell.EMPTY) {
            super.makeMove(row, column);
            if (turn == autoPlayer && currentGameState == GameState.PLAYING) {
                makeAutoMove();
            }
        }
    }

    private void makeAutoMove() {
        if (!makeWinningMove()) {
            if (!blockOpponentWinningMove())
                makeRandomMove();
        }

    }

    private boolean makeWinningMove() {
        return false;
    }

    private boolean blockOpponentWinningMove() {
        return false;
    }

    private void makeRandomMove() {
        int numberOfEmptyCells = getNumberOfEmptyCells();
        Random random = new Random();
        int targetMove = random.nextInt(numberOfEmptyCells);
        int index=0;
        for (int row = 0; row < TOTALROWS; ++row) {
            for (int col = 0; col < TOTALCOLUMNS; ++col) {
                if (grid[row][col] == Cell.EMPTY) {
                    if (targetMove == index) {
                        super.makeMove(row, col);
                        return;
                    } else
                        index++;
                }
            }
        }
    }

    private int getNumberOfEmptyCells() {
        int numberOfEmptyCells = 0;
        for (int row = 0; row < TOTALROWS; ++row) {
            for (int col = 0; col < TOTALCOLUMNS; ++col) {
                if (grid[row][col] == Cell.EMPTY) {
                    numberOfEmptyCells++;
                }
            }
        }
        return numberOfEmptyCells;
    }

}
