import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] board;
    private String currentPlayer;
    private int moveCount;

    public TicTacToeFrame() {
        currentPlayer = "X";
        moveCount = 0;
        board = new TicTacToeButton[3][3];
        setTitle("Tic Tac Toe");
        setLayout(new GridLayout(4, 3)); // 3x3 grid + one for quit button
        initializeBoard();
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = new TicTacToeButton(row, col);
                board[row][col].setText(" ");
                board[row][col].addActionListener(new ButtonClickListener());
                add(board[row][col]);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();
            if (button.getText().equals(" ")) {
                button.setText(currentPlayer);
                moveCount++;
                if (moveCount >= 5 && checkWin()) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    if (askToPlayAgain()) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else if (moveCount >= 7) {
                    if (checkWin()) {
                        JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                        if (askToPlayAgain()) {
                            resetGame();
                        } else {
                            System.exit(0);
                        }
                    } else if (isBoardFull()) {
                        JOptionPane.showMessageDialog(null, "It's a full board tie!");
                        if (askToPlayAgain()) {
                            resetGame();
                        } else {
                            System.exit(0);
                        }
                    } else if (!canWin()) {
                        JOptionPane.showMessageDialog(null, "It's a tie! No possible moves left.");
                        if (askToPlayAgain()) {
                            resetGame();
                        } else {
                            System.exit(0);
                        }
                    }
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            } else {
                JOptionPane.showMessageDialog(null, "Illegal move! Try again.");
            }
        }
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(currentPlayer) &&
                    board[i][1].getText().equals(currentPlayer) &&
                    board[i][2].getText().equals(currentPlayer)) {
                return true;
            }
            if (board[0][i].getText().equals(currentPlayer) &&
                    board[1][i].getText().equals(currentPlayer) &&
                    board[2][i].getText().equals(currentPlayer)) {
                return true;
            }
        }
        if (board[0][0].getText().equals(currentPlayer) &&
                board[1][1].getText().equals(currentPlayer) &&
                board[2][2].getText().equals(currentPlayer)) {
            return true;
        }
        if (board[0][2].getText().equals(currentPlayer) &&
                board[1][1].getText().equals(currentPlayer) &&
                board[2][0].getText().equals(currentPlayer)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        return moveCount == 9; // The board is full if all 9 moves are made
    }

    private boolean canWin() {
        // Check if there are any possible winning moves left
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals(" ")) {
                    board[row][col].setText(currentPlayer); // Temporarily set the move
                    if (checkWin()) {
                        board[row][col].setText(" "); // Reset the move
                        return true; // A winning move is possible
                    }
                    board[row][col].setText(" "); // Reset the move
                }
            }
        }
        return false; // No winning moves possible
    }

    private boolean askToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText(" ");
            }
        }
        currentPlayer = "X";
        moveCount = 0;
    }
}