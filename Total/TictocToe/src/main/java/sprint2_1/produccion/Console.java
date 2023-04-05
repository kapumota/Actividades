package sprint2_1.produccion;
import sprint2_1.produccion.Board.Cell;

public class Console {
    private Board board;

    public Console(Board board) {
        this.board = board;
    }

    public void displayBoard() {
        for (int row = 0; row<board.getTotalRows(); row++) {
            System.out.println("-------");
            System.out.print("|"+ symbol(board.getCell(row, 0)));
            System.out.print("|"+ symbol(board.getCell(row, 1)));
            System.out.print("|"+ symbol(board.getCell(row, 2)));
            System.out.println("|");
        }
        System.out.println("-------");
    }

    private char symbol(Cell cell) {
        if (cell==Cell.CROSS)
            return 'X';
        else
        if (cell==Cell.NOUGHT)
            return 'O';
        else return ' ';
    }

    public static void main(String[] args) {
        new Console(new Board()).displayBoard();;

    }
}
