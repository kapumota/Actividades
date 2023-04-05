package sprint3_2.prueba;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sprint3_2.produccion.TicTacToeGame;
import sprint3_2.produccion.TicTacToeGame.Cell;

public class TestNoughtMoves {

    private TicTacToeGame game;

    @Before
    public void setUp() throws Exception {
        game = new TicTacToeGame();
        game.makeMove(1, 1);
    }

    // Criterio de aceptacion 3.1
    @Test
    public void testNoughtTurnMoveVacantCell() {
        game.makeMove(0, 0);
        assertEquals("", game.getCell(0, 0), Cell.NOUGHT);
        assertEquals("", game.getTurn(), 'X');
    }

    // Criterio de aceptacion 3.2
    @Test
    public void testNoughtTurnMoveNonVacantCell() {
        game.makeMove(0, 0);
        game.makeMove(1, 0);
        assertEquals("", game.getTurn(), 'O');
        game.makeMove(1, 0);
        assertEquals("", game.getTurn(), 'O');
    }

    // Criterio de aceptacion 3.3
    @Test
    public void testNoughtTurnInvalidRowMove() {
        game.makeMove(4, 0);
        assertEquals("", game.getTurn(), 'O');
    }

    // Criterio de aceptacion 3.3
    @Test
    public void testNoughtTurnInvalidColumnMove() {
        game.makeMove(0, 4);
        assertEquals("", game.getTurn(), 'O');
    }

}
