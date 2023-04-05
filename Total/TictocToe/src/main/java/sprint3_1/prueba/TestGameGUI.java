package sprint3_1.prueba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3_1.produccion.TicTacToeGUI;
import sprint3_1.produccion.TicTacToeGame;

public class TestGameGUI {
    private TicTacToeGame game;

    @Before
    public void setUp() throws Exception {
        game = new TicTacToeGame();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyBoard() {
        new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        game.makeMove(0, 0);
        game.makeMove(1, 1);
        new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}