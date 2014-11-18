/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import java.util.List;
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
    Posn[] boxPosition = new Posn[9];
    FromFileImage[] boxes = new FromFileImage[9];
    
    MakeOrders(Client[] clients) {
        this.listOfClients = clients;
        this.listOfOrders = new Order[clients.length];
        
        int k = 0;
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                k = 3*i + j;
                boxPosition[k] = new Posn(300*i + 150, 250*j + 120);
                boxes[k] = new FromFileImage(boxPosition[k], str + "box.png");
            }
    }
    
    MakeOrders(Client[] clients, Order[] orders) {
        this.listOfClients = clients;
        this.listOfOrders = orders;
        
        int k = 0;
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++) {
                k = 3*i + j;
                boxPosition[k] = new Posn(300*i + 150, 250*j + 120);
                boxes[k] = new FromFileImage(boxPosition[k], str + "box.png");
            }
    }
    
    @Override
    public WorldImage makeImage() {
        FromFileImage bg = new FromFileImage(new Posn(100,450), str + "kitchen.png");
        TextImage text = new TextImage(new Posn(800, 850), "DELIVER ORDERS", Color.BLACK);
        text.size = 15;
        text.style = 1;
        
        OverlayImages img = new OverlayImages(bg, this.box);
        img = new OverlayImages(img, text);
        
        FromFileImage box;
        String name;
        for(int i=0; i<9; i++) {
            img = new OverlayImages(img, boxes[i]);
                
            name = Order.everyFood[i].toString();
            text = new TextImage(new Posn(boxPosition[i].x - 2*name.length() - 8, boxPosition[i].y + 30), name, Color.BLACK);
            text.size = 20;
            text.style = 1;

            img = new OverlayImages(img, text);
        }
        
        return img;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "deliver orders"
        if (loc.inside(this.box)) {
            System.out.println("Deliver the food.");
            return new DeliverOrders(this.listOfClients, this.listOfOrders);
        }
        else {
            int j=0;
            for(int i=0; i<9; i++) {
                if (loc.inside(boxes[i])) {
                    System.out.println(Order.everyFood[i].toString());
                }
            }
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
