package sprint2_1.prueba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint2_1.produccion.Console;
import sprint2_1.produccion.Board;

public class TestBoardConsole {
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
        new Console(board).displayBoard();
    }

    @Test
    public void testNonEmptyBoard() {
        board.makeMove(0, 0);
        board.makeMove(1, 1);
        new Console(board).displayBoard();
    }

}
