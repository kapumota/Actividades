package sprint1_1.produccion;

public class Console {
    private Board board;

    public Console(Board board) {
        this.board = board;
    }

    public void displayBoard() {
        for (int row = 0; row<3; row++) {
            System.out.println("-------");
            System.out.print("|"+ board.getCell(row, 0));
            System.out.print("|"+ board.getCell(row, 1));
            System.out.print("|"+ board.getCell(row, 2));
            System.out.println("|");
        }
        System.out.println("-------");
    }

    public static void main(String[] args) {
        new Console(new Board()).displayBoard();;

    }
}
