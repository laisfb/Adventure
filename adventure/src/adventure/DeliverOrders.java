/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import java.util.Arrays;
import javalib.funworld.*;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public class DeliverOrders extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    Client[] listOfClients;
    Order done;
    
    RectangleImage box;
    
    boolean gameOver = false;
    
    DeliverOrders(Client[] listOfClients, Order beingMade) {
        this.listOfClients = listOfClients;        
        this.done = beingMade;
        //System.out.println("Order size: " + this.done.size);
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
    }
    
    @Override
    public WorldImage makeImage() {      
        
        FromFileImage bg = new FromFileImage(new Posn(450,330), str + "background.png");
        FromFileImage counter = new FromFileImage(new Posn(450,950), str + "countertop.png");
        OverlayImages img = new OverlayImages(bg, bg);
        
        int i=0;
        while (i<listOfClients.length && listOfClients[i].showHun()) {
            img = new OverlayImages(img, listOfClients[i].getImage());
            i++;
        }
        
        img = new OverlayImages(img, counter);
        
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
        
        else {
            int i = 0;
            Posn pos;
            while (i<listOfClients.length) {
                pos = listOfClients[i].getPosition();
                if (loc.insideHalf(this.listOfClients[i].getImage())) {
                    if (this.done.equals(listOfClients[i].getOrder())) {
                        System.out.println("Right!");
                        this.listOfClients[i].dontShow();
                    }
                    else {
                        System.out.println("Wrong!");
                    }
                }
                i++;
            }

            return this;
        }

    }
    
    public boolean equals(World w) {
        if (w instanceof DeliverOrders) {
            return Arrays.equals(this.listOfClients, ((DeliverOrders)w).listOfClients) &&
                   this.done.equals(((DeliverOrders)w).done);
        }
        
        return false;
    }
    
}
