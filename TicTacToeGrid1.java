import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGrid extends JPanel implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean player1Turn = true;
    private int[] scores = {0, 0};
    private JLabel statusLabel, scoreLabel;
    private JFrame frame;
    
    public TicTacToeGrid() {
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (!btn.getText().equals("")) return;
        
        btn.setText(player1Turn ? "X" : "O");
        btn.setForeground(player1Turn ? Color.RED : Color.BLUE);
        
        if (checkWinner()) {
            String winner = "Player " + (player1Turn ? "1 (X)" : "2 (O)");
            scores[player1Turn ? 0 : 1]++;
            updateScore();
            if (JOptionPane.showConfirmDialog(frame, winner + " wins!\nPlay again?", 
                "Game Over", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                restartGame();
            } else endGame();
        } else if (isBoardFull()) {
            if (JOptionPane.showConfirmDialog(frame, "It's a tie!\nPlay again?", 
                "Game Over", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                restartGame();
            } else endGame();
        } else {
            player1Turn = !player1Turn;
            statusLabel.setText("Player " + (player1Turn ? "1" : "2") + "'s Turn (" + 
                               (player1Turn ? "X" : "O") + ")");
        }
    }
    
    private boolean checkWinner() {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) return true;
        }
        return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
               checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
    }
    
    private boolean checkLine(JButton a, JButton b, JButton c) {
        return !a.getText().equals("") && a.getText().equals(b.getText()) && 
               b.getText().equals(c.getText());
    }
    
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) return false;
            }
        }
        return true;
    }
    
    private void updateScore() {
        scoreLabel.setText("Score - Player 1: " + scores[0] + " | Player 2: " + scores[1]);
    }
    
    private void restartGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
        player1Turn = true;
        statusLabel.setText("Player 1's Turn (X)");
    }
    
    private void endGame() {
        JOptionPane.showMessageDialog(frame, "Thanks for playing!\n\nFinal Score:\n" +
            "Player 1: " + scores[0] + "\nPlayer 2: " + scores[1], "Game Ended", 
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGrid game = new TicTacToeGrid();
            game.frame = new JFrame("Tic Tac Toe Game");
            game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.frame.setLayout(new BorderLayout());
            
            JPanel topPanel = new JPanel(new GridLayout(2, 1));
            game.statusLabel = new JLabel("Player 1's Turn (X)", SwingConstants.CENTER);
            game.statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
            game.scoreLabel = new JLabel("Score - Player 1: 0 | Player 2: 0", SwingConstants.CENTER);
            topPanel.add(game.statusLabel);
            topPanel.add(game.scoreLabel);
            
            JPanel bottomPanel = new JPanel(new FlowLayout());
            JButton restartBtn = new JButton("Restart Game");
            JButton endBtn = new JButton("End Game");
            restartBtn.addActionListener(e -> game.restartGame());
            endBtn.addActionListener(e -> game.endGame());
            bottomPanel.add(restartBtn);
            bottomPanel.add(endBtn);
            
            game.frame.add(topPanel, BorderLayout.NORTH);
            game.frame.add(game, BorderLayout.CENTER);
            game.frame.add(bottomPanel, BorderLayout.SOUTH);
            game.frame.setSize(450, 550);
            game.frame.setLocationRelativeTo(null);
            game.frame.setVisible(true);
            
            JOptionPane.showMessageDialog(game.frame, "Welcome to Tic-Tac-Toe!\n\n" +
                "Player 1: X\nPlayer 2: O\n\nLet's play!", "Welcome", 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
}