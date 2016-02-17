/*
 *Human.java will extend Player.java
 *for the ConnectFour game.
 *
 *Human will allow a user to input their column
 *selection rather than having the AI select.
 */

import javax.swing.JOptionPane;

public class Human extends Player
{

	public Human(int c)
	{
		super(c);
		name = JOptionPane.showInputDialog(null, "Enter your name:");
	}

	public int play(int[][] board)
	{
		JOptionPane p = new JOptionPane();

		String move = p.showInputDialog(null, name + ": Enter the column to place your piece (1 - 7)");
		int col = Integer.parseInt(move)-1;
		if(col < 0 || col > 6 )
			return play(board);
		return col;
	}
}