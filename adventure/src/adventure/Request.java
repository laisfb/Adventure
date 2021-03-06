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
public class Request {
    
    public static Food[] everyFood;
    
    public Food[] listOfFood;
    public int size;
    public static Posn balloon;
    
    // pos : the position of the balloon
    Request(Posn pos, int level) {
        
        if (level < 3)
            this.size = 3;
        else
            this.size = level;
        
        this.balloon = pos;
        this.everyFood = createListOfFood();
        
        this.listOfFood = new Food[this.size];
        
        Food f;
        this.listOfFood[0] = randomFood();
        for (int i=1; i<this.size; i++) {
            f = randomFood();
            while (this.hasFood(f,i))
                f = randomFood();
            this.listOfFood[i] = f;
        }
        
        // Use only for test
        //this.size = 9;
        //this.listOfFood = createListOfFood();
    }
    
     Request(Food[] list) {
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
    
    public Request addFood(Food f) {
        Food[] newList = new Food[size+1];
        
        for (int i=0; i<size; i++)
            newList[i] = this.listOfFood[i];
        
        newList[size] = f;
        
        return new Request(newList);
    }
    
    public boolean equals(Request req) {
        if (this.size != req.size)
            return false;
        else {
            for (int i=0; i<size; i++)
                if (!this.listOfFood[i].equals(req.listOfFood[i]))
                    return false;
            
            return true;
        }
    }
    
    // pos : the position of the balloon
    public static Food[] createListOfFood() {
        Posn pos = Request.balloon;
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
    
    public boolean hasFood(Food f, int max) {
        for (int i=0; i<max; i++)
            if (this.listOfFood[i].equals(f))
                return true;
        
        return false;
    }
    
    public int howMany(Food f) {
        int x = 0;
        for (int i=0; i<this.size; i++)
            if (this.listOfFood[i].equals(f))
                x++;
        
        return x;
    }
    
}
