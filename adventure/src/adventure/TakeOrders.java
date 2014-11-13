/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import static java.lang.Math.abs;
import javalib.funworld.*;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public class TakeOrders extends World {

    private String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    int LEVEL = -1;
    
    Client[] listOfClients;
    boolean showOrders;
    int time;
    
    TakeOrders(int level) {
        this.LEVEL = level;
        
        this.listOfClients = new Client[level];
        this.showOrders = false;
        this.time = 0;
        
        listOfClients[0] = new kid();
    }
    
    TakeOrders(int level, boolean showOrders, int time) {
        this.LEVEL = level;
        
        this.listOfClients = new Client[level];
        this.showOrders = showOrders;
        this.time = time;
        
        listOfClients[0] = new kid();
    }

    @Override
    public WorldImage makeImage() {        
        
        FromFileImage bg = new FromFileImage(new Posn(450,330), str + "background.png");
        FromFileImage counter = new FromFileImage(new Posn(450,950), str + "countertop.png");
        OverlayImages img = new OverlayImages(bg, bg);
        
        int i=0;
        while(i<listOfClients.length) {
            img = new OverlayImages(img, listOfClients[i].getImage());
            i++;
        }
        
        img = new OverlayImages(img, counter);
        
        if(showOrders) {
            FromFileImage balloon;
            Posn pos, newPos;
            i = 0;
            
            while(i<listOfClients.length) {
                pos = listOfClients[i].getPosition();
                newPos = new Posn(pos.x, pos.y - 300);
                
                balloon = new FromFileImage(newPos, str + "balloon.png");
                img = new OverlayImages(img, balloon);
                i++;
            }
        }
        
        return img;
    }
    
    @Override
    public World onTick() {
        if(time < 5)
            return new TakeOrders(LEVEL, true, time+1);
        else
            return new TakeOrders(LEVEL, false, time+1);
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        int i = 0;
        Posn pos;
        
        while(i<listOfClients.length) {
            pos = listOfClients[i].getPosition();
            System.out.println("MouseClick: (" + loc.x + " , " + loc.y + ")");
            System.out.println("Client: (" + pos.x + " , " + pos.y + ")");
            System.out.println("Difference: (" + (abs(pos.x - loc.x)) + " , " + (abs(pos.y - loc.y)) + ")");
            if (loc.closeTo(pos)) {
                System.out.println("Close enough");
                return new TakeOrders(LEVEL, true, 0);                
            }
            i++;
        }
        
        return this;
    }
    
}
