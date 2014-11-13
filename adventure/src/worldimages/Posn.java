package javalib.worldimages;

import static java.lang.Math.abs;

/**
 * To represent a point on the drawing 
 * <CODE>WorldCanvas</CODE> or <CODE>AppletCanvaas</CODE>
 * 
 * @author Viera K. 
 * @since August 2, 2007
 */
public class Posn {
  public int x;
  public int y;

  public Posn(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public boolean closeTo(Posn pos) {
      return (abs(pos.x - this.x)) <= 50 &&
             (abs(pos.y - this.y)) <= 150;
      
              
  }
}