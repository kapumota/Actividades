package sprint1_0.prueba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1_0.produccion.Board;
import sprint1_0.produccion.GUI;

public class TestBoardGUI {
    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyBoard() {
        new GUI(board);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
