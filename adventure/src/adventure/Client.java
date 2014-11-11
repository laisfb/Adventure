/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public interface Client {
    
    public Order getOrder();
    public WorldImage getImage();
    
}

class kid implements Client {
    
    private String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";

    private Order order;
    
    kid() {
        this.order = new Order();
    }
    
    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(new Posn(750,600), str + "kid.png");
    }
    
}