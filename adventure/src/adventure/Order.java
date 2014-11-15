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
    
    Food[] listOfFood;
    int size;
    
    // pos : the position of the balloon
    Order(Posn pos) {
        this.size = 1; //randomInt(5); // 1 <= size <= 5
        listOfFood = new Food[size];
        
        for(int i=0; i<size; i++)
            listOfFood[i] = randomFood(pos);
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Food[] getFood() {
        return this.listOfFood;
    }
    
    public boolean equals(Order ord) {
        return (this.size == ord.size && Arrays.equals(this.listOfFood, ord.listOfFood));
    }
    
    // pos : the position of the balloon
    public static Food randomFood(Posn pos) {
        return new Pizza(pos);
    }
    
    public static int randomInt(int max) {
	Random r = new Random();
	return (r.nextInt(max) + 1);
    }
    
}
