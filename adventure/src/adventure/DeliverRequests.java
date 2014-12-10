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
public class DeliverRequests extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";
    
    int LEVEL;
    int time;
    int score;
    
    RectangleImage boxRight;
    RectangleImage boxLeft;
    
    Client[] listOfClients;
    Request done;
    
    DeliverRequests(Client[] listOfClients, Request beingMade, int level, int time, int score) {        
        this.LEVEL = level;
        this.time = time;
        this.score = score;
        System.out.println("score: " + this.score);
        this.boxRight = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        this.boxLeft = new RectangleImage(new Posn(90, 846), 150, 40, Color.ORANGE);        
        
        this.listOfClients = listOfClients;        
        this.done = beingMade;
        //System.out.println("Request size: " + this.done.size);
        
        if(time == 0)
            for (int i=0; i<listOfClients.length; i++)
                listOfClients[i].dontShowRequest();
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
        
        img = new OverlayImages(img, this.boxRight);
        img = new OverlayImages(img, this.boxLeft);
        
        
        TextImage text = new TextImage(new Posn(800, 850), "MAKE FOOD", Color.BLACK);
        text.size = 15;
        text.style = 1;        
        img = new OverlayImages(img, text);
        

        text = new TextImage(new Posn(80, 850), "DROP FOOD", Color.BLACK);
        text.size = 15;
        text.style = 1;
        img = new OverlayImages(img, text);        
        
        return img;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "make food"
        if (loc.inside(this.boxRight)) {
            System.out.println("Go to kitchen.");
            return new MakeRequests(this.listOfClients, this.LEVEL, this.score);
        }
        
        // If clicked whithin the box of "drop food"
        else if (loc.inside(this.boxLeft)) {
            System.out.println("Dropped the request.");
            this.done = new Request(new Food[0]);
            return this;
        }
        
        else {
            int i = 0;
            Posn pos;
            while (i < this.listOfClients.length) {
                pos = this.listOfClients[i].getPosition();
                
                // The clients has to be there to receive the request
                if (this.listOfClients[i].showHun() && loc.insideHalf(this.listOfClients[i].getImage())) {
                    
                    if (done.getSize() == 0) {
                        this.score -= 5;
                        System.out.println("Empty request!");
                        this.listOfClients[i].showRequest();
                        this.listOfClients[i].restartWaiting();
                        this.time = 0;
                        //System.out.println("Show request");
                    }
                    
                    else if (this.done.equals(listOfClients[i].getRequest())) {
                            this.score += 50;
                            System.out.println("Right request!");
                            this.listOfClients[i].dontShow();
                            this.done = new Request(new Food[0]);
                    }
                    
                    else {
                        this.score -= 10;
                        System.out.println("Wrong request!");
                    }
                }
                i++;
            }

            for (i=0; i < this.listOfClients.length; i++) {
                if (this.listOfClients[i].showHun())
                    return this;
            }
            
            return new nextLevel(this.LEVEL + 1, 0, this.score);
        }

    }
    
    @Override
    public World onTick() {
        
        if (this.time >= 3) {
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
    
    // Two "DeliverRequests" are equal if they have the same list of clients and same list of request done
    public boolean equals(World w) {
        if (w instanceof DeliverRequests) {
            return Arrays.equals(this.listOfClients, ((DeliverRequests)w).listOfClients) &&
                   this.done.equals(((DeliverRequests)w).done);
        }
        
        return false;
    }
    
}
