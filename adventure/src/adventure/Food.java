/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

/**
 *
 * @author laisfb
 */
public interface Food {
    public WorldImage getImage();
    public Posn getPosition();    
}

class Pizza implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private final Posn position;
    
    // pos : the position of the balloon
    Pizza(Posn pos) {
        this.position = new Posn(pos.x - 80, pos.y - 70);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "pizza.gif");
    }

    @Override
    public Posn getPosition() {
        return this.position;
    }
    
}
