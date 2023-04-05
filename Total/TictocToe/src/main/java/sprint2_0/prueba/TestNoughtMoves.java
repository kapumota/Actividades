package sprint2_0.prueba;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sprint2_0.produccion.Board;

public class TestNoughtMoves {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
        board.makeMove(1, 1);
    }

    // Criterio de aceptacion 3.1
    @Test
    public void testNoughtTurnMoveVacantCell() {
        board.makeMove(0, 0);
        assertEquals("", board.getCell(0, 0), 2);
        assertEquals("", board.getTurn(), 'X');
    }

    // Criterio de aceptacion 3.2
    @Test
    public void testNoughtTurnMoveNonVacantCell() {
        board.makeMove(0, 0);
        board.makeMove(1, 0);
        assertEquals("", board.getTurn(), 'O');
        board.makeMove(1, 0);
        assertEquals("", board.getTurn(), 'O');
    }

    // Criterio de aceptacion 3.3 - 1
    @Test
    public void testNoughtTurnInvalidRowMove() {
        board.makeMove(4, 0);
        assertEquals("", board.getTurn(), 'O');
    }

    // Criterio de aceptacion 3.3 - 1
    @Test
    public void testNoughtTurnInvalidColumnMove() {
        board.makeMove(0, 4);
        assertEquals("", board.getTurn(), 'O');
    }

}
