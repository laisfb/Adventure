/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.Random;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */

abstract class Client {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";
    
    private Request request;
    private Posn position;
    
    private FromFileImage balloon;
    private FromFileImage picture;
    
    private boolean show = true;
    private boolean showRequest = true;
    
    public String getStr() {
        return this.str;
    }
    
    public Request getRequest() {
        return this.request;
    }
    
    public void setRequest(Request r) {
        this.request = r;
    }
    
    public Posn getPosition() {
        return this.position;
    }
    
    public void setPosition(Posn pos) {
        this.position = pos;
    }
    
    public FromFileImage getBalloon() {
        return this.balloon;
    }
    
    public void setBalloon(FromFileImage img) {
        this.balloon = img;
    }

    public WorldImage getImage() {
        return this.picture;
    }
    
    public void setImage(FromFileImage img) {
        this.picture = img;
    }
    
    public boolean showHun() {
        return this.show;
    }
    
    public void dontShow() {
        this.show = false;
    }
    
    public boolean showRequestHun() {
        return this.showRequest;
    }
    
    public void showRequest() {
        this.showRequest = true;
    }
    
    public void dontShowRequest() {
        this.showRequest = false;
    }
    
    public static int randomInt(int max) {
	Random r = new Random();
	return (r.nextInt(max));
    }
    
    // They are considered equal if their image is the same
    public boolean equals(Client c) {
        return (this.picture.fileName).equals(c.picture.fileName);
        //return (((FromFileImage)this.getImage()).fileName).equals(((FromFileImage)c.getImage()).fileName);
    }
    
    public static Client randomClient(int level) {
        int x = randomInt(3);
        if (x==0)
            return new man(level);
        
        else if (x==1)
            return new woman(level);
        
        else if (x==2)
            return new kid(level);
        
        else
            throw new RuntimeException("ERROR IN: randomClient");
    }
}

class man extends Client {
        
    FromFileImage[] men = new FromFileImage[2];
    
    man(int level) {
        this.setPosition(new Posn(150,550));
        
        Posn pos = new Posn(this.getPosition().x, this.getPosition().y - 330);
        this.setBalloon(new FromFileImage(pos, this.getStr() + "balloon.png"));
        this.setRequest(new Request(this.getBalloon().pinhole, level));
        
        this.men[0] = new FromFileImage(this.getPosition(), this.getStr() + "man1.png");
        this.men[1] = new FromFileImage(this.getPosition(), this.getStr() + "man2.png");
        
        this.setImage(this.men[randomInt(2)]);
    }
    
}

class woman extends Client {
        
    FromFileImage[] women = new FromFileImage[2];
    
    woman(int level) {
        this.setPosition(new Posn(455,520));
        
        Posn pos = new Posn(this.getPosition().x, this.getPosition().y - 300);
        this.setBalloon(new FromFileImage(pos, this.getStr() + "balloon.png"));
        this.setRequest(new Request(this.getBalloon().pinhole, level));
        
        this.women[0] = new FromFileImage(this.getPosition(), this.getStr() + "woman1.png");
        this.women[1] = new FromFileImage(this.getPosition(), this.getStr() + "woman2.png");
        
        this.setImage(this.women[randomInt(2)]);
    }
    
}

class kid extends Client {
        
    FromFileImage[] kids = new FromFileImage[4];
    
    kid(int level) {
        this.setPosition(new Posn(750,600));
        
        Posn pos = new Posn(this.getPosition().x, this.getPosition().y - 300);
        this.setBalloon(new FromFileImage(pos, this.getStr() + "balloon.png"));
        this.setRequest(new Request(this.getBalloon().pinhole, level));
        
        this.kids[0] = new FromFileImage(this.getPosition(), this.getStr() + "kid1.png");
        this.kids[1] = new FromFileImage(this.getPosition(), this.getStr() + "kid2.png");
        this.kids[2] = new FromFileImage(this.getPosition(), this.getStr() + "kid3.png");
        this.kids[3] = new FromFileImage(this.getPosition(), this.getStr() + "kid4.png");
        
        this.setImage(this.kids[randomInt(4)]);
    }
    
}