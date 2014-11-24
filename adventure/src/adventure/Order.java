/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.Arrays;
import java.util.Random;
import javalib.worldimages.Posn;

/**
 *
 * @author laisfb
 */
public class Order {
    
    static Food[] everyFood;
    
    Food[] listOfFood;
    int size;
    static Posn balloon;
    
    // pos : the position of the balloon
    Order(Posn pos) {
        
        this.size  = randomInt(5) + 1;
        this.listOfFood = new Food[size];
        
        this.balloon = pos;
        this.everyFood = createListOfFood();
        
        for(int i=0; i<this.size; i++)
            this.listOfFood[i] = randomFood();
        
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
        Food[] everyFood = new Food[9];
        
        everyFood[0] = new Pizza(pos);
        everyFood[1] = new HotDog(pos);
        everyFood[2] = new Hamburger(pos);
        everyFood[3] = new Fries(pos);
        everyFood[4] = new Soda(pos);        
        everyFood[5] = new Sandwich(pos);
        everyFood[6] = new Cookies(pos);        
        everyFood[7] = new Coffee(pos);        
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
