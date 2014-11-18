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
    public String toString();
}

class Pizza implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
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

    @Override
    public void setPosition(Posn pos) {
        this.position = pos;
    }
    
    // FromFileImage has a methods "equals"
    @Override
    public boolean equals(Food f) {
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "PIZZA";
    }
}

class HotDog implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    HotDog(Posn pos) {
        this.position = new Posn(pos.x - 10, pos.y - 70);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "hotdog.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "HOT DOG";
    }
}

class Hamburger implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Hamburger(Posn pos) {
        this.position = new Posn(pos.x + 45, pos.y - 70);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "hamburger.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "HAMBURGER";
    }
}

class Fries implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Fries(Posn pos) {
        this.position = new Posn(pos.x + 100, pos.y - 65);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "fries.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "FRIES";
    }
}
class Sandwich implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Sandwich(Posn pos) {
        this.position = new Posn(pos.x - 25, pos.y);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "sandwich.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "SANDWICH";
    }
}

class Cookies implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Cookies(Posn pos) {
        this.position = new Posn(pos.x + 45, pos.y - 25);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "cookies.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "COOKIES";
    }
}

class Coffee implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Coffee(Posn pos) {
        this.position = new Posn(pos.x + 45, pos.y + 20);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "coffee.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "COFFEE";
    }
}

class Soda implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Soda(Posn pos) {
        this.position = new Posn(pos.x - 90, pos.y - 10);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "soda.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "SODA";
    }
}

class Milk implements Food {
    
    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\food\\";
    private Posn position;
    
    // pos : the position of the balloon
    Milk(Posn pos) {
        this.position = new Posn(pos.x + 100, pos.y);
    }

    @Override
    public WorldImage getImage() {
        return new FromFileImage(this.position, str + "milk.png");
    }

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
        return ( (FromFileImage)this.getImage() ).equals(f.getImage());
    }
    
    @Override
    public String toString() {
        return "MILK";
    }
}