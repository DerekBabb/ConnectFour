/*
 *ConnectFour.java
 *
 *This is the main implementation for the connect four game
 *To add your own AI to the file, change
 *the class that is instantiated for p1 and p2.
 */

import javax.swing.*;
import java.awt.*;

public class ConnectFourGame extends JPanel
{
  //board is an array of int, 0 for empty, 1 for black and 2 for red
  private static final int EMPTY = 0;
  private static final int BLACK = 1;
  private static final int RED = 2;
  private int[][] board;
  private Player p1, p2;
  private String winMsg = "";
  private Color winColor=Color.BLACK;
  
  //Stats variables
  private boolean statsRound;
  private int games;
  private int[] wins = new int[3];
  
  
  public ConnectFourGame()
  {
    //Setup your players here
    /****************************************************/
    p1 = new Example(BLACK);
    p2 = new Human(RED);
    /****************************************************/
    board = new int[6][7];
    for(int row = 0; row < 6; row++)
      for(int col = 0; col < 7; col++)
      board[row][col] = 0;
    
    games = 1;
    splashScreen();
    
  }
  
  public void splashScreen()
  {
    String input = JOptionPane.showInputDialog(null, "Enter number of games:");
    games = Integer.parseInt(input);
    statsRound = games > 1;
  }
  
  
  public void setupGUI()
  {
    JFrame jf = new JFrame("Connect Four");
    Container cp = jf.getContentPane();
    jf.setSize(500, 500);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setLocationRelativeTo(null);
    
    cp.add(this);
    jf.setVisible(true);
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    int width = getWidth();
    int height = getHeight();
    //Draw the names
    Font f = new Font("Sans Serif", Font.BOLD, 20);
    g.setFont(f);
    g.setColor(Color.BLACK);
    g.drawString(p1.getName(), 30,30);
    
    g.setColor(Color.RED);
    g.drawString(p2.getName(), width - 130, 30);
    
    g.setColor(winColor);
    g.drawString(winMsg, width/2-50, 75);
    
    g.setColor(Color.YELLOW);
    g.fillRect(0, 100, width, height-100);
    
    //Draw the grid for C4
    g.setColor(Color.BLACK);
    int rowHeight = (height-100)/6;
    for(int rows = 0; rows < 6; rows++)
    {
      g.drawLine(0, rows * rowHeight + 100, width,rows * rowHeight + 100);
    }
    
    int colWidth = (width)/7;
    for(int cols = 0; cols<7; cols++)
    {
      g.drawLine(cols * colWidth, 100, cols*colWidth, height);
    }
    
    //paint the pieces on the board
    for (int row = 0; row < 6; row++)
    {
      for(int col = 0; col < 7; col++)
      {
        if(board[row][col] == 0)
          g.setColor(Color.WHITE);
        else if(board[row][col] == 1)
          g.setColor(Color.BLACK);
        else
          g.setColor(Color.RED);
        
        g.fillOval(col * colWidth + 5,row * rowHeight + 100 + 5, colWidth - 10, rowHeight - 10);
      }
    }
  }
  
  public void drawBoard()
  {
    System.out.println("_______________");
    for(int row = 0; row < 6; row++)
    {
      System.out.print("|");
      for(int col = 0; col < 7; col++)
      {
        if(board[row][col] == 0)
          System.out.print(" ");
        else if(board[row][col] == 1)
          System.out.print("B");
        else
          System.out.print("R");
        
        System.out.print("|");
      }
      System.out.println();
    }
  }
  
  public boolean playTurn(Player p)
  {
    int[][] tempBoard = new int[6][7];
    for(int i = 0; i < 6; i++)
      for(int j = 0; j < 7; j++)
      tempBoard[i][j] = board[i][j];
    int spot = p.play(tempBoard);
    if(spot < 0 || spot > 6)
      return false;
    for(int row = 5; row >= 0; row--)
    {
      if(board[row][spot] == 0)
      {
        board[row][spot] = p.getColor();
        return true;
      }
      
    }
    return false;
  }
  
  public boolean checkWin()
  {
    //check 4 in a single row
    for(int row = 0; row < board.length; row++)
    {
      for(int col = 0; col < board[row].length-3; col++)
      {
        if(board[row][col] != 0 && board[row][col] == board[row][col+1] &&board[row][col] == board[row][col+2]&& board[row][col] == board[row][col+3])
          return true;
      }
    }
    
    //check 4 in a single column
    for(int col = 0; col < board[0].length; col++)
    {
      for (int row = board.length - 1; row >= 3; row--)
      {
        if(board[row][col] != 0 && board[row][col] == board[row-1][col] && board[row][col] == board[row-2][col] && board[row][col] == board[row-3][col])
        {
          return true;
        }
      }
    }
    //check for diag
    for(int row = 0; row < board.length-3; row++)
    {
      for(int col = 0; col <board[0].length-3; col++)
      {
        if(board[row][col] != 0 && board[row][col] == board[row+1][col+1] && board[row][col] == board[row+2][col+2] && board[row][col] == board[row+3][col+3])
        {
          return true;
        }
      }
    }
    for(int row = 3; row < board.length; row++)
    {
      for(int col = 0; col <board[0].length-3; col++)
      {
        if(board[row][col] != 0 && board[row][col] == board[row-1][col+1] && board[row][col] == board[row-2][col+2] && board[row][col] == board[row-3][col+3])
        {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean checkTie()
  {
    for(int col = 0; col < 7; col++)
      if(board[0][col] == 0)
      return false;
    
    return true;
  }
  
  public char game(Player plyr1, Player plyr2)
  {
    boolean playerOneTurn = true;
    //drawBoard();
    if(!statsRound)
      setupGUI();
    boolean validMove = true;
    while(!checkWin() && !checkTie())
    {
      repaint();
      if(playerOneTurn)
        validMove = playTurn(plyr1);
      else
        validMove = playTurn(plyr2);
      repaint();
      //drawBoard();
      try
      {
        if(!statsRound)
          Thread.sleep(100);
      }
      catch (Exception e){};
      if(validMove)
        playerOneTurn = !playerOneTurn;
    }
    if(playerOneTurn && !checkTie()) // remember this has just been flipped so it is now opposite
    {
      winMsg = plyr2.getName() + " wins!!";
      if(plyr2.getColor() == 1)
        winColor = Color.BLACK;
      else
        winColor = Color.RED;
      repaint();
      return 2;
    }
    
    
    else if(!playerOneTurn && !checkTie())
    {
      winMsg = plyr1.getName() + " wins!!";
      if(plyr1.getColor() == 1)
        winColor = Color.BLACK;
      else
        winColor = Color.RED;
      repaint();
      return 1;
    }
    else
    {
      winMsg = "TIE";
      winColor = Color.BLUE;
      repaint();
      return 0;
    }
    
  }
  
  public void reset()
  {
    for(int row = 0; row < 6; row++)
      for(int col = 0; col < 7; col++)
      board[row][col] = 0;
  }
  
  public void gameLoop()
  {
    if(statsRound)
    {
      for(int g = 0; g < games; g++)
      {
        if(g < games/2)
          wins[game(p1, p2)]++;
        else
        {
          int winner = game(p2, p1);
          if(winner == 1)
            wins[2]++;
          else if(winner == 2)
            wins[1]++;
          else
            wins[0]++;
        }
        //   drawBoard();
        reset();
      }
      String msg = p1.getName() + ": " + wins[1];
      msg += "\n" + p2.getName() + ": " + wins[2];
      msg += "\n" + "Ties: " + wins[0];
      JOptionPane.showMessageDialog(null, msg, "Stats Summary", JOptionPane.INFORMATION_MESSAGE);
    }
    else
      game(p1, p2);
  }
  
  public static void main(String[] args)
  {
    ConnectFourGame cfg = new ConnectFourGame();
    cfg.gameLoop();
  }
}