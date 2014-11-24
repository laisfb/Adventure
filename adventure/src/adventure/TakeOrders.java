/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import javalib.funworld.*;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public class TakeOrders extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    int LEVEL = -1;
    
    Client[] listOfClients;
    boolean showOrders;
    int time;
    
    RectangleImage box;
    
    TakeOrders(int level) {
        this.LEVEL = level;
        
        this.listOfClients = new Client[level];
        this.showOrders = false;
        this.time = 0;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        
        listOfClients[0] = new kid();
    }
    
    TakeOrders(int level, Client[] list, boolean showOrders, int time) {
        this.LEVEL = level;
        
        this.listOfClients = new Client[level];
        this.showOrders = showOrders;
        this.time = time;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        
        listOfClients = list;
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
            i = 0;
            
            int size;
            Food[] order;
            WorldImage food;
            
            while(i<listOfClients.length) {
                balloon = listOfClients[i].getBalloon();
                img = new OverlayImages(img, balloon);
                
                size = listOfClients[i].getOrder().getSize();
                order = listOfClients[i].getOrder().getFood();
            
                int j = 0;
                while(j<size) {
                    food = order[j].getImage();
                    img = new OverlayImages(img, food);
                    j++;
                }
                i++;
            }
        }
        
        TextImage text = new TextImage(new Posn(800, 850), "MAKE ORDERS", Color.BLACK);
        text.size = 15;
        text.style = 1;
        
        img = new OverlayImages(img, this.box);
        img = new OverlayImages(img, text);
        return img;
    }
    
    @Override
    public World onTick() {
        if(time < 3)
            return new TakeOrders(LEVEL, this.listOfClients, true, time+1);
        else
            return new TakeOrders(LEVEL, this.listOfClients, false, time+1);
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make orders"
        if (loc.inside(this.box)) {
            System.out.println("Go to kitchen.");
            return new MakeOrders(this.listOfClients);
        }
        
        else {
            int i = 0;
            Posn pos;
            while(i<listOfClients.length) {
                pos = listOfClients[i].getPosition();
                // System.out.println("MouseClick: (" + loc.x + " , " + loc.y + ")");
                // System.out.println("Client: (" + pos.x + " , " + pos.y + ")");
                // System.out.println("Difference: (" + (abs(pos.x - loc.x)) + " , " + (abs(pos.y - loc.y)) + ")");
                if (loc.insideHalf(this.listOfClients[i].getImage())) {
                    return new TakeOrders(this.LEVEL, this.listOfClients, true, 0);                
                }
                i++;
            }

            return this;
        }
    }
    
}
