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
public class TakeRequests extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    int LEVEL = -1;
    int score;  
    int time;
    
    RectangleImage box;
    
    Client[] listOfClients;
    
    TakeRequests(int level, int score) {
        this.LEVEL = level;
        this.score = score;
        this.time = 0;
        
        this.box = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        System.out.println("score: " + this.score);
        if (this.LEVEL == 1) {
            this.listOfClients = new Client[1];
            this.listOfClients[0] = Client.randomClient(this.LEVEL);
        }
        
        else if (this.LEVEL == 2) {
            this.listOfClients = new Client[2];
            this.listOfClients[0] = Client.randomClient(this.LEVEL);
            
            this.listOfClients[1] = Client.randomClient(this.LEVEL);
            Posn pos = this.listOfClients[1].getPosition();
            while(pos.equals(this.listOfClients[0].getPosition())) {
                this.listOfClients[1] = Client.randomClient(this.LEVEL);
                pos = this.listOfClients[1].getPosition();
            }
        }
        
        else {
            this.listOfClients = new Client[3];
            this.listOfClients[0] = new man(this.LEVEL);
            this.listOfClients[1] = new woman(this.LEVEL);
            this.listOfClients[2] = new kid(this.LEVEL);
        }
             
    }

    @Override
    public WorldImage makeImage() {      
        
        FromFileImage bg = new FromFileImage(new Posn(450,330), str + "background.png");
        FromFileImage counter = new FromFileImage(new Posn(450,950), str + "countertop.png");
        OverlayImages img = new OverlayImages(bg, bg);
        
        int i=0;
        while (i < this.listOfClients.length) {
            if (this.listOfClients[i].showHun()) {
                img = new OverlayImages(img, this.listOfClients[i].getImage());

                if (this.listOfClients[i].showRequestHun()) {
                    FromFileImage balloon;

                    int size;
                    Food[] request;
                    WorldImage food;

                    balloon = this.listOfClients[i].getBalloon();
                    img = new OverlayImages(img, balloon);

                    size = this.listOfClients[i].getRequest().getSize();
                    request = this.listOfClients[i].getRequest().getFood();

                    int j = 0;
                    while (j < size) {
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
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make food"
        if (loc.inside(this.box)) {
            System.out.println("Go to kitchen.");
            return new MakeRequests(this.listOfClients, this.LEVEL, this.score);
        }
        
        else {
            int i = 0;
            Posn pos;
            while (i < this.listOfClients.length) {
                pos = this.listOfClients[i].getPosition();
                // System.out.println("MouseClick: (" + loc.x + " , " + loc.y + ")");
                // System.out.println("Client: (" + pos.x + " , " + pos.y + ")");
                // System.out.println("Difference: (" + (abs(pos.x - loc.x)) + " , " + (abs(pos.y - loc.y)) + ")");
                if (loc.insideHalf(this.listOfClients[i].getImage())) {
                    this.listOfClients[i].showRequest();
                    this.listOfClients[i].restartWaiting();
                    this.time = 0;
                }
                i++;
            }

            return this;
        }
    }
    
    @Override
    public World onTick() {        
        if (time >= 3) {
            for (int i=0; i < this.listOfClients.length; i++)
               this.listOfClients[i].dontShowRequest();
        }
        
        // Clients will go away if you take too long to deliver their food
        //   but only the ones that are still there
        for (int i=0; i < this.listOfClients.length; i++) {
            if (this.listOfClients[i].showHun()) {
               this.listOfClients[i].stillWaiting();
               if (this.listOfClients[i].doneWaiting()) {
                   this.listOfClients[i].dontShow();
                   this.score -= 10;
               }
            }
        }
        
        // Making sure there's no negative score
        //   (altough it could be possible)
        if (this.score < 0)
            this.score = 0;
        
        if (Client.allGone(this.listOfClients))
            return new nextLevel(6, 0, this.score);
        
        this.time = this.time + 1;
        return this;
    }
    
    // Two "TakeRequests" are equal if they have the same list of clients
    public boolean equals(World w) {
        if (w instanceof TakeRequests) {
            return Arrays.equals(this.listOfClients, ((TakeRequests)w).listOfClients);
        }
        
        return false;
    }
    
}
