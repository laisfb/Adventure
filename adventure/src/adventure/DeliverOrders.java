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
public class DeliverOrders extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    Client[] listOfClients;
    Order[] listOfOrders;
    
    RectangleImage box;
    
    boolean gameOver = false;
    
    DeliverOrders(Client[] listOfClients, Order[] listOfOrders) {
        this.listOfClients = listOfClients;        
        this.listOfOrders = listOfOrders;
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
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        TextImage text = new TextImage(new Posn(800, 850), "MAKE ORDERS", Color.BLACK);
        text.size = 15;
        text.style = 1;
        
        img = new OverlayImages(img, this.box);
        img = new OverlayImages(img, text);
        return img;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make orders"
        if (loc.inside(this.box)) {
            System.out.println("Go to kitchen.");
            return new MakeOrders(this.listOfClients);
        }
        
        return this;
    }
    
}
