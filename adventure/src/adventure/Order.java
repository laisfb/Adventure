/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.Random;
import javalib.worldimages.Posn;

/**
 *
 * @author laisfb
 */
public class Order {
    
    public static Food[] everyFood;
    
    public Food[] listOfFood;
    public int size;
    public static Posn balloon;
    
    // pos : the position of the balloon
    Order(Posn pos, int level) {
        
        if(level < 3)
            this.size = 3;
        else
            this.size = level;
        
        this.listOfFood = new Food[this.size];
        
        this.balloon = pos;
        this.everyFood = createListOfFood();
        
        Food f;
        this.listOfFood[0] = randomFood();
        for(int i=1; i<this.size; i++) {
            f = randomFood();
            while (f.isOn(this.listOfFood,i))
                f = randomFood();
            this.listOfFood[i] = f;
        }
        
        // Use only for test
        //this.size = 9;
        //this.listOfFood = createListOfFood();
    }
    
     Order(Food[] list) {
        this.size = list.length;
        this.listOfFood = list;
        
       this.everyFood = createListOfFood();
     }
    
    public int getSize() {
        return this.size;
    }
    
    public Food[] getFood() {
        return this.listOfFood;
    }
    
    public Order addFood(Food f) {
        Food[] newList = new Food[size+1];
        
        for(int i=0; i<size; i++)
            newList[i] = this.listOfFood[i];
        
        newList[size] = f;
        
        return new Order(newList);
    }
    
    public boolean equals(Order ord) {
        if (this.size != ord.size)
            return false;
        else {
            for(int i=0; i<size; i++)
                if (!this.listOfFood[i].equals(ord.listOfFood[i]))
                    return false;
            
            return true;
        }
    }
    
    // pos : the position of the balloon
    public static Food[] createListOfFood() {
        Posn pos = Order.balloon;
        everyFood = new Food[9];
        
        everyFood[0] = new Pizza(pos);
        everyFood[1] = new HotDog(pos);
        everyFood[2] = new Burger(pos);
        everyFood[3] = new Fries(pos);
        everyFood[4] = new Soda(pos);
        everyFood[5] = new Sandwich(pos);
        everyFood[6] = new Coffee(pos);
        everyFood[7] = new Cookies(pos);
        everyFood[8] = new Milk(pos);
        
        return everyFood;
    }
    
    public static Food randomFood() {
        Food[] list = createListOfFood();
        return list[randomInt(list.length)]; // 0 <= i < lenght
    }
    
    public static int randomInt(int max) {
	Random r = new Random();
	return (r.nextInt(max));
    }
    
}
