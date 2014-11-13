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
    
}

class kid implements Client {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    private final Order order;
    private final Posn position;
    
    kid() {
        this.order = new Order();
        this.position = new Posn(750,600);
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
    
}