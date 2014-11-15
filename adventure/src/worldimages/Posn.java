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
  
  public boolean equals(Posn pos) {
      return (this.x == pos.x && this.y == pos.y);
  }
  
  // Method used to check if the mouse click was near the client
  public boolean closeTo(Posn pos) {
      return (abs(pos.x - this.x)) <= 50 &&
             (abs(pos.y - this.y)) <= 150;
      
              
  }
  
  // Method used to check if the mouse click was inside the box
  public boolean inside(RectangleImage rect) {
      int height = rect.height;
      int width = rect.width;
      Posn pos = rect.pinhole;
      
      // I have no ideia why there has to be the +- 5
      return this.x > pos.x - (width/2 - 5) &&
             this.x < pos.x + (width/2 + 5) &&
             this.y > pos.y - (height/2) &&
             this.y < pos.y + (height/2);
  }
}