package sprint3_0.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3_0.produccion.Board;
import sprint3_0.produccion.Board.Cell;

public class TestCrossMoves {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @After
    public void tearDown() throws Exception {
    }

    // Criterio de aceptacion 2.1
    @Test
    public void testCrossTurnMoveVacantCell() {
        board.makeMove(0, 0);
        assertEquals("", board.getCell(0, 0), Cell.CROSS);
        assertEquals("", board.getTurn(), 'O');
    }

    // Criterio de aceptacion 2.2
    @Test
    public void testCrossTurnMoveNonVacantCell() {
        board.makeMove(0, 0);
        board.makeMove(1, 0);
        assertEquals("", board.getCell(1, 0), Cell.NOUGHT);
        assertEquals("", board.getTurn(), 'X');
        board.makeMove(0, 0);
        assertEquals("", board.getTurn(), 'X');
    }

    // Criterio de aceptacion 2.3
    @Test
    public void testCrossTurnInvalidRowMove() {
        board.makeMove(4, 0);
        assertEquals("", board.getTurn(), 'X');
    }

    // Criterio de aceptacion 2.3
    @Test
    public void testCrossTurnInvalidColumnMove() {
        board.makeMove(0, 4);
        assertEquals("", board.getTurn(), 'X');
    }

}
