/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public interface Client {
    
    public Order getOrder();
    public WorldImage getImage();
    public Posn getPosition();
    public FromFileImage getBalloon();
    //public Posn getBalloonPosition();
    
}

class kid implements Client {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    private final Order order;
    private final Posn position;
    private final FromFileImage balloon;
    
    kid() {
        this.position = new Posn(750,600);
        
        Posn pos = new Posn(this.position.x, this.position.y - 300);
        this.balloon = new FromFileImage(pos, str + "balloon.png");
        
        this.order = new Order(this.balloon.pinhole);
    }
    
    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "kid.png");
    }
    
    @Override
    public Posn getPosition() {
        return this.position;
    }
    
    @Override
    public FromFileImage getBalloon() {
        return this.balloon;
    }
    
}