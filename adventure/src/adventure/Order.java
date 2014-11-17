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
    
    Food[] everyFood;
    
    Food[] listOfFood;
    int size;
    
    // pos : the position of the balloon
    Order(Posn pos) {
        createListOfFood(pos);
        
        // Use only for test
        //this.size = 9;
        //this.listOfFood = everyFood;
        
        this.size  = randomInt(5) + 1;
        this.listOfFood = new Food[size];
        
        for(int i=0; i<size; i++)
            this.listOfFood[i] = randomFood();
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Food[] getFood() {
        return this.listOfFood;
    }
    
    public boolean equals(Order ord) {
        return (this.size == ord.size && Arrays.equals(this.everyFood, ord.everyFood));
    }
    
    // pos : the position of the balloon
    public void createListOfFood(Posn pos) {
        this.everyFood = new Food[9];
        
        everyFood[0] = new Pizza(pos);
        everyFood[1] = new HotDog(pos);
        everyFood[2] = new Hamburger(pos);
        everyFood[3] = new Fries(pos);
        everyFood[4] = new Sandwich(pos);
        everyFood[5] = new Cookies(pos);
        
        everyFood[6] = new Coffee(pos);
        everyFood[7] = new Soda(pos);
        everyFood[8] = new Milk(pos);
    }
    
    public Food randomFood() {
        return this.everyFood[randomInt(everyFood.length)]; // 0 <= i < lenght
    }
    
    public static int randomInt(int max) {
	Random r = new Random();
	return (r.nextInt(max));
    }
    
}
