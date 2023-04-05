package sprint1_0.prueba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint1_0.produccion.Board;
import sprint1_0.produccion.Console;

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
}
