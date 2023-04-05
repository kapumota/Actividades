package sprintX_autoplay;

import java.util.Scanner;
import sprintX_autoplay.TicTacToeGame.Cell;
import sprintX_autoplay.TicTacToeGame.GameState;

public class TicTacToeConsole {
    private TicTacToeGame game;

    public TicTacToeConsole(TicTacToeGame board) {
        this.game = board;
    }

    public void displayBoard() {
        for (int row = 0; row < game.getTotalRows(); row++) {
            System.out.println("-------");
            System.out.print("|" + symbol(game.getCell(row, 0)));
            System.out.print("|" + symbol(game.getCell(row, 1)));
            System.out.print("|" + symbol(game.getCell(row, 2)));
            System.out.println("|");
        }
        System.out.println("-------");
    }

    private char symbol(Cell cell) {
        if (cell == Cell.CROSS)
            return 'X';
        else if (cell == Cell.NOUGHT)
            return 'O';
        else
            return ' ';
    }

    private boolean isOver() {
        GameState state = game.getGameState();
        if (state == GameState.PLAYING)
            return false;
        if (game.getGameState() == GameState.DRAW) {
            System.out.println("Lanzamiento!");
        } else if (game.getGameState() == GameState.CROSS_WON) {
            System.out.println("X gana!");
        } else if (game.getGameState() == GameState.NOUGHT_WON) {
            System.out.println("O gana!");
        }
        return true;
    }

    public void play() {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        System.out.println("Bienvenido al juego del TicTacToe!");
        while (!done) {
            int row, column;
            System.out.println("Actual jugador: " + game.getTurn());
            System.out.print("Movimiento en una fila: ");
            row = in.nextInt();
            System.out.print("Movimiento en una columna: ");
            column = in.nextInt();
            if (row < 0 || row > game.getTotalRows() || column < 0 || column > game.getTotalColumns())
                System.out.println("Movimiento invalido en (" + row + "," + column + ")");
            else {
                game.makeMove(row, column);
                displayBoard();
                done = isOver();
            }
        }
        in.close();
    }

    public static void main(String[] args) {
        new TicTacToeConsole(new AutoTicTacToe()).play();
    }
}

