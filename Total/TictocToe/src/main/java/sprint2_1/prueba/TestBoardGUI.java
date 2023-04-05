package sprint2_1.prueba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint2_1.produccion.Board;
import sprint2_1.produccion.GUI;

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

    @Test
    public void testNonEmptyBoard() {
        board.makeMove(0, 0);
        board.makeMove(1, 1);
        new GUI(board);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}