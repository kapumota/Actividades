package sprintX_autoplay;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sprintX_autoplay.TicTacToeGame.Cell;
import sprintX_autoplay.TicTacToeGame.GameState;

/*
 * El codigo GUI fue escrito por
 * Prof. Chua Hock Chuan, Nanyang Technological University
 */

@SuppressWarnings("serial")
public class TicTacToeGUI extends JFrame {

    public static final int CELL_SIZE = 100;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    private GameBoardCanvas gameBoardCanvas;
    private JLabel gameStatusBar;

    private TicTacToeGame game;

    public TicTacToeGUI() {
        this(new TicTacToeGame());
    }

    public TicTacToeGUI(TicTacToeGame game) {
        this.game = game;
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Tic Tac Toe");
        setVisible(true);
    }

    private void setContentPane() {
        gameBoardCanvas = new GameBoardCanvas();
        gameBoardCanvas
                .setPreferredSize(new Dimension(CELL_SIZE * game.getTotalRows(), CELL_SIZE * game.getTotalColumns()));
        gameStatusBar = new JLabel("  ");
        gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);
        contentPane.add(gameStatusBar, BorderLayout.PAGE_END);
    }

    class GameBoardCanvas extends JPanel {

        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (game.getGameState() == GameState.PLAYING) {
                        int rowSelected = e.getY() / CELL_SIZE;
                        int colSelected = e.getX() / CELL_SIZE;
                        game.makeMove(rowSelected, colSelected);
                    } else {
                        game.resetGame();
                        ;
                    }
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
            printStatusBar();
        }

        private void drawGridLines(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < game.getTotalRows(); ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF, CELL_SIZE * game.getTotalRows() - 1, GRID_WIDTH,
                        GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < game.getTotalColumns(); ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0, GRID_WIDTH,
                        CELL_SIZE * game.getTotalColumns() - 1, GRID_WIDTH, GRID_WIDTH);
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < game.getTotalRows(); ++row) {
                for (int col = 0; col < game.getTotalColumns(); ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (game.getCell(row, col) == Cell.CROSS) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (game.getCell(row, col) == Cell.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }
        }

        private void printStatusBar() {
            if (game.getGameState() == GameState.PLAYING) {
                gameStatusBar.setForeground(Color.BLACK);
                if (game.getTurn() == 'X') {
                    gameStatusBar.setText("Turno de X");
                } else {
                    gameStatusBar.setText("Turno de O");
                }
            } else if (game.getGameState() == GameState.DRAW) {
                gameStatusBar.setForeground(Color.RED);
                gameStatusBar.setText("Un lanzamiento! Click para jugar otra vez.");
            } else if (game.getGameState() == GameState.CROSS_WON) {
                gameStatusBar.setForeground(Color.RED);
                gameStatusBar.setText("'X' gana! Click para jugar otra vez.");
            } else if (game.getGameState() == GameState.NOUGHT_WON) {
                gameStatusBar.setForeground(Color.RED);
                gameStatusBar.setText("'O' gana! Click para jugar otra vez.");
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI(new AutoTicTacToe('X'));
            }
        });
    }
}