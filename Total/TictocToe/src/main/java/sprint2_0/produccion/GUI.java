package sprint2_0.produccion;

import java.awt.*;

import javax.swing.*;

/*
 * El código GUI fue escrito por
 * Prof. Chua Hock Chuan, Nanyang Technological University
 */

@SuppressWarnings("serial")
public class GUI extends JFrame {

    public static final int CELL_SIZE = 100;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private int CANVAS_WIDTH;
    private int CANVAS_HEIGHT;

    private GameBoardCanvas gameBoardCanvas;

    private Board board;

    public GUI(Board board) {
        this.board = board;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Tic Tac Toe");
        setVisible(true);
    }

    public Board getBoard(){
        return board;
    }

    private void setContentPane(){
        gameBoardCanvas = new GameBoardCanvas();
        CANVAS_WIDTH = CELL_SIZE * 3;
        CANVAS_HEIGHT = CELL_SIZE * 3;
        gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
    }

    class GameBoardCanvas extends JPanel {

        GameBoardCanvas(){

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }

        private void drawGridLines(Graphics g){
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < 3; row++) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < 3; col++) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
            }

        }

        private void drawBoard(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board.getCell(row,col) == 1) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board.getCell(row,col) == 2) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI(new Board());
            }
        });
    }
}
