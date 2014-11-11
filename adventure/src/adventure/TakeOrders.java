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

    private String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    Client[] listOfClients;
    int LEVEL = -1;

    
    TakeOrders(int level) {
        this.LEVEL = level;
        listOfClients = new Client[level];
        
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
        
        return img;
    }
    
    public World onTick() {
        return this;
    }
    
    
}
