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
public class TakeRequests extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    int LEVEL = -1;
    int SCORE = 0;
    
    Client[] listOfClients;
    int time;
    
    RectangleImage box;
    
    TakeRequests(int level, int score) {
        this.LEVEL = level;
        this.SCORE = score;
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
    
    TakeRequests(int level, Client[] list, int time, int score) {
        this.LEVEL = level;
        this.SCORE = score;
        this.time = time;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        
        this.listOfClients = list;
    }

    @Override
    public WorldImage makeImage() {      
        
        FromFileImage bg = new FromFileImage(new Posn(450,330), str + "background.png");
        FromFileImage counter = new FromFileImage(new Posn(450,950), str + "countertop.png");
        OverlayImages img = new OverlayImages(bg, bg);
        
        int i=0;
        while (i<listOfClients.length) {
            img = new OverlayImages(img, listOfClients[i].getImage());
            
            if (listOfClients[i].showRequestHun()) {
                FromFileImage balloon;

                int size;
                Food[] request;
                WorldImage food;

                balloon = listOfClients[i].getBalloon();
                img = new OverlayImages(img, balloon);

                size = listOfClients[i].getRequest().getSize();
                request = listOfClients[i].getRequest().getFood();

                int j = 0;
                while (j<size) {
                    food = request[j].getImage();
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
        
        TextImage text = new TextImage(new Posn(800, 850), "MAKE FOOD", Color.BLACK);
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
               listOfClients[i].dontShowRequest();
        }
        
        this.time = this.time + 1;
        return this;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make food"
        if (loc.inside(this.box)) {
            //System.out.println("Go to kitchen.");
            return new MakeRequests(this.listOfClients, this.LEVEL, this.SCORE);
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
                    this.listOfClients[i].showRequest();
                    this.time = 0;
                }
                i++;
            }

            return this;
        }
    }
    
}