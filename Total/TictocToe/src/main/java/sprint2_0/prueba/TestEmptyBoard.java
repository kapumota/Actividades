package sprint2_0.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint2_0.produccion.Board;

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
        for (int row = 0; row <3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals("", board.getCell(row, col), 0);
            }
        }
    }

    @Test
    public void testInvalidRow() {
        assertEquals("", board.getCell(3, 0), -1);
    }

    @Test
    public void testInvalidColumn() {
        assertEquals("", board.getCell(0, 3), -1);
    }
}