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
public class MakeOrders extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    Client[] listOfClients;
    Order[] listOfOrders;
    
    RectangleImage box;
    
    MakeOrders(Client[] list) {
        this.listOfClients = list;
        this.listOfOrders = new Order[list.length];
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
    }
    
    @Override
    public WorldImage makeImage() {
        FromFileImage bg = new FromFileImage(new Posn(100,450), str + "kitchen.png");
        TextImage text = new TextImage(new Posn(800, 850), "DELIVER ORDERS", Color.BLACK);
        text.size = 15;
        text.style = 1;
        
        OverlayImages img = new OverlayImages(bg, this.box);
        img = new OverlayImages(img, text);
        
        //img = new OverlayImages(img, foods());
        
        return img;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "deliver orders"
        if (loc.inside(this.box)) {
            System.out.println("Deliver the food.");
            return new DeliverOrders(this.listOfClients, this.listOfOrders);
        }
        
        return this;
    }
    
    // Two "MakeOrders" are equal if they have the same list of clients and same list of orders made
    public boolean equals(World w) {
        if(w instanceof MakeOrders) {
            return Arrays.equals(this.listOfClients, ((MakeOrders)w).listOfClients) &&
                   Arrays.equals(this.listOfOrders,  ((MakeOrders)w).listOfOrders);
        }
        
        return false;
    }
    
}