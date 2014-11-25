package javalib.worldimages;

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
  public boolean insideHalf(WorldImage img) {
      int height = img.getHeight();
      int width = img.getWidth();
      Posn pos = img.pinhole;
      
      return this.x > pos.x - width/2 &&
             this.x < pos.x + width/2 &&
             this.y > pos.y - height/2 &&
             this.y < 640 ;
  }
  
  // Method used to check if the mouse click was inside the image
  public boolean inside(WorldImage img) {
      int height = img.getHeight();
      int width = img.getWidth();
      Posn pos = img.pinhole;
      
      return this.x > pos.x - width/2 &&
             this.x < pos.x + width/2 &&
             this.y > pos.y - height/2 &&
             this.y < pos.y + height/2;
  }
}