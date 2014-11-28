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
    
    int LEVEL;
    int time = 0;
    int score = 0;
    
    DeliverOrders(Client[] listOfClients, Order beingMade, int level, int time, int score) {
        this.listOfClients = listOfClients;        
        this.done = beingMade;
        //System.out.println("Order size: " + this.done.size);
        
        this.LEVEL = level;
        this.time = time;
        this.score = score;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        
        if(time == 0)
            for (int i=0; i<listOfClients.length; i++)
                listOfClients[i].dontShowOrder();
    }
    
    @Override
    public WorldImage makeImage() {      
        
        FromFileImage bg = new FromFileImage(new Posn(450,330), str + "background.png");
        FromFileImage counter = new FromFileImage(new Posn(450,950), str + "countertop.png");
        OverlayImages img = new OverlayImages(bg, bg);
        
        int i=0;
        while (i<listOfClients.length) {
            if (listOfClients[i].showHun()) {
                img = new OverlayImages(img, listOfClients[i].getImage());

                if (listOfClients[i].showOrderHun()) {
                    FromFileImage balloon;

                    int size;
                    Food[] order;
                    WorldImage food;

                    balloon = listOfClients[i].getBalloon();
                    img = new OverlayImages(img, balloon);

                    size = listOfClients[i].getOrder().getSize();
                    order = listOfClients[i].getOrder().getFood();

                    int j = 0;
                    while (j<size) {
                        food = order[j].getImage();
                        img = new OverlayImages(img, food);
                        j++;

                        int x = food.pinhole.x + 25;
                        int y = food.pinhole.y + food.getHeight()/2;
                        TextImage text = new TextImage(new Posn(x, y), j + "", Color.BLACK);
                        text.size = 15;
                        text.style = 1;
                        img = new OverlayImages(img, text);
                    }
                }
            }
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
            return new MakeOrders(this.listOfClients, this.LEVEL, this.score);
        }
        
        else {
            int i = 0;
            Posn pos;
            while (i<listOfClients.length) {
                pos = listOfClients[i].getPosition();
                
                if (loc.insideHalf(this.listOfClients[i].getImage())) {
                    
                    if (done.getSize() == 0) {
                        this.score -= 5;
                        System.out.println("Empty order! Score: " + this.score);
                        this.listOfClients[i].showOrder();
                        return new DeliverOrders(this.listOfClients, this.done, this.LEVEL, 0, this.score); 
                    }
                    
                    else if (this.done.equals(listOfClients[i].getOrder())) {
                            this.score += 50;
                            System.out.println("Right order! Score: " + this.score);
                            this.listOfClients[i].dontShow();
                            this.done = new Order(new Food[0]);
                    }
                    
                    else {
                        this.score -= 10;
                        System.out.println("Wrong order! Score: " + this.score);
                    }
                }
                i++;
            }

            for (i=0; i<listOfClients.length; i++) {
                if (this.listOfClients[i].showHun())
                    return new DeliverOrders(this.listOfClients, this.done, this.LEVEL, 0, this.score);
            }
            
            return new nextLevel(this.LEVEL + 1, 0, this.score);
        }

    }
    
    public boolean equals(World w) {
        if (w instanceof DeliverOrders) {
            return Arrays.equals(this.listOfClients, ((DeliverOrders)w).listOfClients) &&
                   this.done.equals(((DeliverOrders)w).done);
        }
        
        return false;
    }
    
    @Override
    public World onTick() {
        
        if (this.time >= 3) {
            for (int i=0; i<listOfClients.length; i++)
               listOfClients[i].dontShowOrder();
        }
        
        return new DeliverOrders(this.listOfClients, this.done, this.LEVEL, this.time + 1, this.score);
    }
    
}
