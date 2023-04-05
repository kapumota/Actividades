package sprint3_2.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3_2.produccion.TicTacToeGame;
import sprint3_2.produccion.TicTacToeGame.Cell;

public class TestEmptyBoard {

    private TicTacToeGame game = new TicTacToeGame();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNewBoard() {
        for (int row = 0; row <game.getTotalRows(); ++row) {
            for (int col = 0; col < game.getTotalColumns(); ++col) {
                assertEquals("", game.getCell(row, col), Cell.EMPTY);
            }
        }
    }

    @Test
    public void testInvalidRow() {
        assertEquals("", game.getCell(3, 0), null);
    }

    @Test
    public void testInvalidColumn() {
        assertEquals("", game.getCell(0, 3), null);
    }
}
