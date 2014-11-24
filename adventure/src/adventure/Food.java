/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import javalib.funworld.World;
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
    public void setPosition(Posn pos);
    public boolean equals(Food f);
}

abstract class GenericFood implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    @Override
    public Posn getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Posn pos) {
        this.position = pos;
    }
    
    // FromFileImage has a methods "equals"
    @Override
    public boolean equals(Food f) {
        return ( ( (FromFileImage)this.getImage() ).fileName ).equals( ((FromFileImage)f.getImage()).fileName );
    }
    
    public String getStr() {
        return str;
    }
}

class Pizza extends GenericFood {
    
    // pos : the position of the balloon
    Pizza(Posn pos) {
        this.setPosition(new Posn(pos.x - 80, pos.y - 70));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "pizza.gif");
    }
    
    @Override
    public String toString() {
        return "PIZZA";
    }
}

class HotDog extends GenericFood {
    
    // pos : the position of the balloon
    HotDog(Posn pos) {
        this.setPosition(new Posn(pos.x - 10, pos.y - 70));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "hotdog.png");
    }
    
    @Override
    public String toString() {
        return "HOT DOG";
    }
}

class Hamburger extends GenericFood {
    
    // pos : the position of the balloon
    Hamburger(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y - 70));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "hamburger.png");
    }
    
    @Override
    public String toString() {
        return "HAMBURGER";
    }
}

class Fries extends GenericFood {
    
    // pos : the position of the balloon
    Fries(Posn pos) {
        this.setPosition(new Posn(pos.x + 100, pos.y - 65));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "fries.png");
    }
    
    @Override
    public String toString() {
        return "FRIES";
    }
}
class Sandwich extends GenericFood {
    
    // pos : the position of the balloon
    Sandwich(Posn pos) {
        this.setPosition(new Posn(pos.x - 25, pos.y));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "sandwich.png");
    }
    
    @Override
    public String toString() {
        return "SANDWICH";
    }
}

class Cookies extends GenericFood {
    
    // pos : the position of the balloon
    Cookies(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y - 25));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "cookies.png");
    }
    
    @Override
    public String toString() {
        return "COOKIES";
    }
}

class Coffee extends GenericFood {
    
    // pos : the position of the balloon
    Coffee(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y + 20));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "coffee.png");
    }
    
    @Override
    public String toString() {
        return "COFFEE";
    }
}

class Soda extends GenericFood {
    
    // pos : the position of the balloon
    Soda(Posn pos) {
        this.setPosition(new Posn(pos.x - 90, pos.y - 10));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "soda.png");
    }
    
    @Override
    public String toString() {
        return "SODA";
    }
}

class Milk extends GenericFood {
    
    // pos : the position of the balloon
    Milk(Posn pos) {
        this.setPosition(new Posn(pos.x + 100, pos.y));
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.getPosition(), getStr() + "milk.png");
    }
    
    @Override
    public String toString() {
        return "MILK";
    }
}