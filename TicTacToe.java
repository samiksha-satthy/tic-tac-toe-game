import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons for the board
    private boolean xTurn = true; // Tracks which player's turn it is
    private JLabel statusLabel; // Displays current game status
    private JButton resetButton; // Resets the game board

    // Custom color palette
    private final Color navyBlue = new Color(0, 47, 108);         // Background color
    private final Color skyBlue = new Color(33, 150, 243);        // Reset button color
    private final Color lightBlue = new Color(122, 203, 226);     // Cell background color

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(navyBlue); // Set main window background

        // Status label at the top of the window
        statusLabel = new JLabel("X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setForeground(lightBlue);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(statusLabel, BorderLayout.NORTH);

        // Game board panel
        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.setBackground(navyBlue);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 60);

        // Initialize each button in the 3x3 grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(buttonFont);
                btn.setFocusPainted(false);
                btn.setBackground(lightBlue);
                btn.setForeground(Color.BLACK);
                btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
                btn.setOpaque(true);
                btn.addActionListener(this);
                buttons[i][j] = btn;
                panel.add(btn);
            }
        }
        add(panel, BorderLayout.CENTER);

        // Reset button to clear the board and start a new game
        resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resetButton.setBackground(skyBlue);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resetButton.addActionListener(e -> resetGame());

        // Bottom panel containing the reset button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(navyBlue);
        bottomPanel.add(resetButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles button clicks on the game board.
     * Updates the button's text, checks for win/draw conditions,
     * and toggles the turn.
     */
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals("")) return; // Ignore already clicked cells

        String symbol = xTurn ? "X" : "O";
        clicked.setText(symbol);
        clicked.setForeground(Color.BLACK);
        clicked.setEnabled(false);

        if (checkWin()) {
            statusLabel.setText(symbol + " Wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("Draw!");
        } else {
            xTurn = !xTurn;
            statusLabel.setText((xTurn ? "X" : "O") + "'s Turn");
        }
    }

    /**
     * Checks all win conditions: rows, columns, and diagonals.
     */
    private boolean checkWin() {
        String player = xTurn ? "X" : "O";
        for (int i = 0; i < 3; i++) {
            if (player.equals(buttons[i][0].getText()) &&
                player.equals(buttons[i][1].getText()) &&
                player.equals(buttons[i][2].getText())) return true;

            if (player.equals(buttons[0][i].getText()) &&
                player.equals(buttons[1][i].getText()) &&
                player.equals(buttons[2][i].getText())) return true;
        }

        if (player.equals(buttons[0][0].getText()) &&
            player.equals(buttons[1][1].getText()) &&
            player.equals(buttons[2][2].getText())) return true;

        if (player.equals(buttons[0][2].getText()) &&
            player.equals(buttons[1][1].getText()) &&
            player.equals(buttons[2][0].getText())) return true;

        return false;
    }

    /**
     * Checks if all cells are filled and there is no winner.
     */
    private boolean isDraw() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                if (b.getText().equals("")) return false;
            }
        }
        return true;
    }

    /**
     * Disables all buttons after the game ends.
     */
    private void disableButtons() {
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setEnabled(false);
            }
        }
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        xTurn = true;
        statusLabel.setText("X's Turn");
        for (JButton[] row : buttons) {
            for (JButton b : row) {
                b.setText("");
                b.setEnabled(true);
                b.setBackground(lightBlue);
                b.setForeground(Color.BLACK);
            }
        }
    }

    /**
     * Entry point of the program. Launches the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
