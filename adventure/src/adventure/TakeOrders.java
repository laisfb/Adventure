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
    int time;
    
    RectangleImage box;
    
    TakeOrders(int level) {
        this.LEVEL = level;
        
        this.time = 0;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        
        if (this.LEVEL == 1) {
            this.listOfClients = new Client[1];
            listOfClients[0] = Client.randomClient(this.LEVEL);
        }
        
        else if (this.LEVEL == 2) {
            this.listOfClients = new Client[2];
            listOfClients[0] = Client.randomClient(this.LEVEL);
            
            listOfClients[1] = Client.randomClient(this.LEVEL);
            Posn pos = listOfClients[1].getPosition();
            while(pos.equals(listOfClients[0].getPosition())) {
                listOfClients[1] = Client.randomClient(this.LEVEL);
                pos = listOfClients[1].getPosition();
            }
        }
        
        else {
            this.listOfClients = new Client[3];
            listOfClients[0] = new man(this.LEVEL);
            listOfClients[1] = new woman(this.LEVEL);
            listOfClients[2] = new kid(this.LEVEL);
        }
             
    }
    
    TakeOrders(int level, Client[] list, int time) {
        this.LEVEL = level;
        
        this.listOfClients = new Client[level];
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
        while (i<listOfClients.length) {
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
    public World onTick() {
        
        if (time >= 3) {
            for (int i=0; i<listOfClients.length; i++)
               listOfClients[i].dontShowOrder();
        }
        
        return new TakeOrders(this.LEVEL, this.listOfClients, this.time + 1);
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make orders"
        if (loc.inside(this.box)) {
            //System.out.println("Go to kitchen.");
            return new MakeOrders(this.listOfClients, this.LEVEL, 0);
        }
        
        else {
            int i = 0;
            Posn pos;
            while (i<listOfClients.length) {
                pos = listOfClients[i].getPosition();
                // System.out.println("MouseClick: (" + loc.x + " , " + loc.y + ")");
                // System.out.println("Client: (" + pos.x + " , " + pos.y + ")");
                // System.out.println("Difference: (" + (abs(pos.x - loc.x)) + " , " + (abs(pos.y - loc.y)) + ")");
                if (loc.insideHalf(this.listOfClients[i].getImage())) {
                    this.listOfClients[i].showOrder();
                    return new TakeOrders(this.LEVEL, this.listOfClients, 0);                
                }
                i++;
            }

            return this;
        }
    }
    
}
