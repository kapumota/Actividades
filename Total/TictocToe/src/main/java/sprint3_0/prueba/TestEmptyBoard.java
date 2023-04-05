package sprint3_0.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3_0.produccion.Board;
import sprint3_0.produccion.Board.Cell;

public class TestEmptyBoard {

    private Board board = new Board();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNewBoard() {
        for (int row = 0; row <board.getTotalRows(); ++row) {
            for (int col = 0; col < board.getTotalColumns(); ++col) {
                assertEquals("", board.getCell(row, col), Cell.EMPTY);
            }
        }
    }

    @Test
    public void testInvalidRow() {
        assertEquals("", board.getCell(3, 0), null);
    }

    @Test
    public void testInvalidColumn() {
        assertEquals("", board.getCell(0, 3), null);
    }
}