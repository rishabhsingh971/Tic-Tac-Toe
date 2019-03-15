import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TTT implements ActionListener {

	static final int BOARD_SIZE = 3;
	static final JButton[][] board = new JButton[BOARD_SIZE][BOARD_SIZE];
	static JFrame frame;
	static final String XMOVE = "X";
	static final String ZMOVE = "0";

	static final int XWins = 0;
	static final int ZWins = 1;
	static final int Tie = 2;
	static final int Incomplete = 3;

	public boolean crossTurn = true;

	public TTT() {
		frame = new JFrame("Tic Tac Toe");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		frame.setLayout(layout);

		Font font = new Font("Times New Roman", 1, 55);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");

				button.setFont(font);
				button.addActionListener(this);

				board[row][col] = button;
				frame.add(button);
			}
		}

		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();

		if (crossTurn) {
			btn.setText(XMOVE);
		} else {
			btn.setText(ZMOVE);
		}
		btn.setEnabled(false);
		crossTurn = !crossTurn;
		int gs = getGameStatus();

		if (gs != Incomplete) {
			declareWinner(gs);

			int res = JOptionPane.showConfirmDialog(null, "Restart?");

			if (res == JOptionPane.YES_OPTION) {
				for (int row = 0; row < BOARD_SIZE; row++) {
					for (int col = 0; col < BOARD_SIZE; col++) {
						board[row][col].setEnabled(true);
						board[row][col].setText("");
					}
				}

				crossTurn = true;
			} else {
				frame.dispose();
			}
		}
	}

	private int getGameStatus() {
		int row = 0, col = 0;
		String text1 = "", text2 = "";

		// check rows
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;

			while (col < BOARD_SIZE - 1) {
				text1 = board[row][col].getText();
				text2 = board[row][col + 1].getText();

				if (text1 != text2 || text1.length() == 0) {
					break;
				}

				col++;
			}

			if (col == BOARD_SIZE - 1) {
				return text1.equals(ZMOVE) ? ZWins : XWins;
			}

			row++;
		}

		// check columns
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;

			while (row < BOARD_SIZE - 1) {
				text1 = board[row][col].getText();
				text2 = board[row + 1][col].getText();

				if (text1 != text2 || text1.length() == 0) {
					break;
				}

				row++;
			}

			if (row == BOARD_SIZE - 1) {
				return text1.equals(ZMOVE) ? ZWins : XWins;
			}

			col++;
		}

		// check diagonal 1
		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1 && col < BOARD_SIZE - 1) {
			text1 = board[row][col].getText();
			text2 = board[row + 1][col + 1].getText();

			if (text1 != text2 || text1.length() == 0) {
				break;
			}

			row++;
			col++;
		}

		if (row == BOARD_SIZE - 1) {
			return text1.equals(ZMOVE) ? ZWins : XWins;
		}

		// check diagonal 2
		row = 0;
		col = BOARD_SIZE - 1;
		while (row < BOARD_SIZE - 1 && col > 0) {
			text1 = board[row][col].getText();
			text2 = board[row + 1][col - 1].getText();

			if (text1 != text2 || text1.length() == 0) {
				break;
			}

			row++;
			col--;
		}

		if (row == BOARD_SIZE - 1) {
			return text1.equals(ZMOVE) ? ZWins : XWins;
		}

		// check incompleteness
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE) {
				text1 = board[row][col].getText();

				if (text1.length() == 0) {
					return Incomplete;
				}

				col++;
			}

			row++;
		}

		return Tie;
	}

	private void declareWinner(int gs) {
		if (gs == XWins) {
			JOptionPane.showMessageDialog(null, "X wins");
		} else if (gs == ZWins) {
			JOptionPane.showMessageDialog(null, "0 wins");
		} else {
			JOptionPane.showMessageDialog(null, "Its a tie");
		}
	}
}
