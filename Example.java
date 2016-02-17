/*
 *Example is ConnectFour player who randomly
 *chooses the column to play.
 *
 *Essentially the worst AI that could be made.
 */

public class Example extends Player
{
 public Example(int c)
 {
  super(c);
  name = "Example";
 }

 public int play(int[][] board)
 {
  return (int)(Math.random()*7);
 }

}


