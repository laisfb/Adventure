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

abstract class Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    
    private Posn position;
    public FromFileImage picture;
        
    public Posn getPosition() {
        return this.position;
    }

    public void setPosition(Posn pos) {
        this.position = pos;
    }

    public WorldImage getImage() {
        return this.picture;
    }
    
    public void setImage(FromFileImage img) {
        this.picture = img;
    }
    
    // They are considered equal if their image is the same
    public boolean equals(Food f) {
        return (this.picture.fileName).equals(f.picture.fileName);
        //return (((FromFileImage)this.getImage()).fileName).equals(((FromFileImage)f.getImage()).fileName);
    }
    
    public String getStr() {
        return str;
    }
}

class Pizza extends Food {
    
    // pos : the position of the balloon
    Pizza(Posn pos) {
        this.setPosition(new Posn(pos.x - 80, pos.y - 70));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "pizza.gif"));
    }
    
    public String toString() {
        return "PIZZA";
    }
}

class HotDog extends Food {
    
    // pos : the position of the balloon
    HotDog(Posn pos) {
        this.setPosition(new Posn(pos.x - 10, pos.y - 70));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "hotdog.png"));
    }
    
    public String toString() {
        return "HOT DOG";
    }
}

class Burger extends Food {
    
    // pos : the position of the balloon
    Burger(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y - 70));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "burger.png"));
    }
    
    public String toString() {
        return "BURGER";
    }
}

class Fries extends Food {
    
    // pos : the position of the balloon
    Fries(Posn pos) {
        this.setPosition(new Posn(pos.x + 100, pos.y - 65));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "fries.png"));
    }
    
    public String toString() {
        return "FRIES";
    }
}
class Sandwich extends Food {
    
    // pos : the position of the balloon
    Sandwich(Posn pos) {
        this.setPosition(new Posn(pos.x - 25, pos.y));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "sandwich.png"));
    }
    
    public String toString() {
        return "SANDWICH";
    }
}

class Cookies extends Food {
    
    // pos : the position of the balloon
    Cookies(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y - 25));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "cookies.png"));
    }
    
    public String toString() {
        return "COOKIES";
    }
}

class Coffee extends Food {
    
    // pos : the position of the balloon
    Coffee(Posn pos) {
        this.setPosition(new Posn(pos.x + 45, pos.y + 20));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "coffee.png"));
    }
    
    public String toString() {
        return "COFFEE";
    }
}

class Soda extends Food {
    
    // pos : the position of the balloon
    Soda(Posn pos) {
        this.setPosition(new Posn(pos.x - 90, pos.y - 10));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "soda.png"));
    }
    
    public String toString() {
        return "SODA";
    }
}

class Milk extends Food {
    
    // pos : the position of the balloon
    Milk(Posn pos) {
        this.setPosition(new Posn(pos.x + 100, pos.y));
        this.setImage(new FromFileImage(this.getPosition(), getStr() + "milk.png"));
    }
    
    public String toString() {
        return "MILK";
    }
}