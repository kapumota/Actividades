package sprint3_2.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3_2.produccion.TicTacToeGUI;
import sprint3_2.produccion.TicTacToeGame;
import sprint3_2.produccion.TicTacToeGame.GameState;

public class TestCompleteGames {

    private TicTacToeGame game;
    @Before
    public void setUp() throws Exception {
        game = new TicTacToeGame();
    }

    @After
    public void tearDown() throws Exception {
    }

    // Criterio de aceptacion 4.1
    @Test
    public void testXWon() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(0, 1);
        game.makeMove(1, 0);
        game.makeMove(0, 2);
        assertEquals("", game.getGameState(), GameState.CROSS_WON);
        new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Criterio de aceptacion 4.3
    @Test
    public void testOWon() {
        game.makeMove(2, 2);
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        game.makeMove(0, 1);
        game.makeMove(1, 0);
        game.makeMove(0, 2);
        assertEquals("", game.getGameState(), GameState.NOUGHT_WON);
        new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Criterio de aceptacion 4.5
    @Test
    public void testDraw() {
        game.makeMove(0, 1);
        game.makeMove(0, 0);
        game.makeMove(0, 2);
        game.makeMove(1, 2);
        game.makeMove(1, 0);
        game.makeMove(1, 1);
        game.makeMove(2, 2);
        game.makeMove(2, 0);
        game.makeMove(2, 1);
        assertEquals("", game.getGameState(), GameState.DRAW);
        new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}