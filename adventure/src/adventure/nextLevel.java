/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import javalib.funworld.World;
import javalib.worldimages.OverlayImages;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

/**
 *
 * @author laisfb
 */
public class nextLevel extends World {

    int LEVEL;
    int SCORE;
    int time;
    
    public nextLevel(int level, int t, int score) {
        this.LEVEL = level;
        this.SCORE = score;
        this.time = t;
    }

    @Override
    public WorldImage makeImage() {
        
        if (this.LEVEL == 6) {
            TextImage text1 = new TextImage(new Posn(200, 400), "GAME OVER", Color.BLACK);
            text1.size = 100;
            text1.style = 1;
            
            TextImage text2 = new TextImage(new Posn(250, 500), "TOTAL SCORE: " + this.SCORE, Color.BLACK);
            text2.size = 60;
            text2.style = 1;
            
            OverlayImages text = new OverlayImages(text1, text2);
            
            return text;
        }
        
        else {
            TextImage text1 = new TextImage(new Posn(280, 400), "LEVEL " + this.LEVEL, Color.BLACK);
            text1.size = 100;
            text1.style = 1;
            
            TextImage text2 = new TextImage(new Posn(280, 500), "SCORE: " + this.SCORE, Color.BLACK);
            text2.size = 80;
            text2.style = 1;
            
            OverlayImages text = new OverlayImages(text1, text2);
            
            return text;
        }
        
    }
    
    @Override
    public World onTick() {
        if (this.LEVEL == 6)
            return this.endOfWorld("");
        
        else if (time == 2)
            return new TakeRequests(this.LEVEL, this.SCORE);
        
        else
            return new nextLevel(this.LEVEL, this.time + 1, this.SCORE);
    }
    
}
