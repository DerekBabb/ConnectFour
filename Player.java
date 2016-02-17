/*
 *Player.java is an abstract class that is designed
 *to play the game of ConnectFour.
 *
 *Your class should extend Player and you must
 *have a method that overwrites the abstract
 *method play()
 */

abstract class Player
{
  private int color; //1 is black, 2 is red.
                     //Use ConectFourGame.RED, ConnectFourGame.BLACK as reference.
  protected String name;
  
  public Player(int c)
  {
    color = c;
  }
  
  public int getColor()
  {
    return color;
  }
  
  public String getName()
  {
    return name;
  }
  
  abstract int play(int[][] board);
  
  
  
}