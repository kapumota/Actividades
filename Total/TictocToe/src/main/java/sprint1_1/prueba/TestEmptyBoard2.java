package sprint1_1.prueba;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1_1.produccion.Board;
public class TestEmptyBoard2 {
    private Board board = new Board();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    // Criterio de aceptacion 1.1
    @Test
    public void testNewBoard() {
        for (int row = 0; row<3; row++) {
            for (int column = 0; column<3; column++) {
                assertEquals("", board.getCell(row, column), 0);
            }
        }
        assertEquals("", board.getTurn(), 'X');
    }
}
