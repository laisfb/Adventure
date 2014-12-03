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
public class Test {

    private final static boolean testingMode = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // level , showOrders , time
        if (!testingMode) {
            TakeOrders take = new TakeOrders(1, 0);
            take.bigBang(900,900,1);
        }
        else {
            
            for(int i=0; i<100; i++) {
                check_transitions();
                check_TakeOrders();
                check_MakeOrders();
                check_DeliverOrders();
            }
            System.out.println("All tests passed!");
        }

    }

    public static int randomInt(int max) {
	Random r = new Random();
	return r.nextInt(max + 1);
    }
    
    public static Posn randomPos() {
        int x = randomInt(900);
        int y = randomInt(900);
        return new Posn(x,y);
    }

    public static void check_transitions() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeOrders take = new TakeOrders(level, 0);
        MakeOrders make = new MakeOrders(take.listOfClients, take.LEVEL, take.SCORE);
        DeliverOrders deliver = new DeliverOrders(make.listOfClients, make.beingMade, make.LEVEL, 0, make.SCORE);
        
        Posn pos = randomPos(); // new Posn(810, 846);
        // System.out.println("Pos: (" + pos.x + "," + pos.y + ")");
        // System.out.println("Box: (" + take.box.pinhole.x + "," + take.box.pinhole.y + ")");
        
        if (pos.inside(take.box) && !(take.onMouseClicked(pos) instanceof MakeOrders))
            throw new RuntimeException("ERROR IN: check_transitions (TakeOrders -> MakeOrders)");
        
        else if (pos.inside(make.boxRight) && !(make.onMouseClicked(pos) instanceof DeliverOrders))
            throw new RuntimeException("ERROR IN: check_transitions (MakeOrders -> DeliverOrders)");
        
         else if (pos.inside(deliver.boxRight) && !(deliver.onMouseClicked(pos) instanceof MakeOrders))
            throw new RuntimeException("ERROR IN: check_transitions (DeliverOrders -> MakeOrders)");
        
        
        boolean show = false;
        for (int i=0; i<deliver.listOfClients.length; i++)
            if (deliver.listOfClients[i].showHun())
                show = true;
        
        // Just making sure it's not trying to go to the MakeOrders world
        //   or trying to drop an order
        if (!pos.inside(deliver.boxRight) && !pos.inside(deliver.boxLeft) && !show && !(deliver.onMouseClicked(pos) instanceof nextLevel))
                throw new RuntimeException("ERROR IN: check_DeliverOrders (DeliverOrders -> nextLevel)");
        
    }
    
    public static void check_TakeOrders() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeOrders take = new TakeOrders(level, 0);
        
        // In the TakeOrders world there are always between 1 and 3 clients
        if (take.listOfClients.length < 1 || take.listOfClients.length > 3)
            throw new RuntimeException("ERROR IN: check_TakeOrders (size of listOfClients)");            
        
        // Each order must be have at least 3 elements, all different
        for (int i=0; i<take.listOfClients.length; i++) {
            Order ord = take.listOfClients[i].getOrder();
            
            if (ord.size < 3)
                throw new RuntimeException("ERROR IN: check_TakeOrders (size of Order)");
            
            for (int j=0; j<ord.size; j++) {
                if (ord.howMany(ord.listOfFood[j]) != 1)
                    throw new RuntimeException("ERROR IN: check_TakeOrders (repeated foods)");
            }
        }
        
    }
    
    public static void check_MakeOrders() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeOrders take = new TakeOrders(level, 0);
        MakeOrders make = new MakeOrders(take.listOfClients, take.LEVEL, take.SCORE);
        
        // Every time the MakeOrders world is created,
        //   the order being made is empty
        //   and there are always 9 boxes of food
        
        if (make.beingMade.size != 0)
                throw new RuntimeException("ERROR IN: check_MakeOrders (initial size of beingMade)");
        
        if (make.boxes.length != 9)
                throw new RuntimeException("ERROR IN: check_MakeOrders (number of boxes)");
        
        
        
        Posn pos = randomPos();
        
        // If the "Start Over" button is clicked,
        //   the order is restarted, meaning the size is zero        
        if (pos.inside(make.boxLeft) && make.beingMade.size != 0)
            throw new RuntimeException("ERROR IN: check_MakeOrders (size of beingMade when start over)");
        
        
        // To make sure it's not going to change worlds
        while (pos.inside(make.boxLeft) || pos.inside(make.boxRight))
            pos = randomPos(); 
        
        int sizeBefore = make.beingMade.size;
        make = (MakeOrders) make.onMouseClicked(pos);
        
        // Everytime a box is clicked, the order's size increase in one
        for(int i=0; i<9; i++) {
            if (pos.inside(make.boxes[i]) && make.beingMade.size != sizeBefore + 1)
                throw new RuntimeException("ERROR IN: check_MakeOrders (size of updated beingMade)");
        }
        
    }
    
    public static void check_DeliverOrders() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeOrders take = new TakeOrders(level, 0);
        MakeOrders make = new MakeOrders(take.listOfClients, take.LEVEL, take.SCORE);
        DeliverOrders deliver = new DeliverOrders(make.listOfClients, make.beingMade, make.LEVEL, 0, make.SCORE);
        
        // When an order is delivered, it is either right or wrong
        //   if it's right, the client goes away and the score increases
        //   if it's wrong, the client stays and the score decreases
        
        // If the client's order was "asked" again:
        //   a baloon with the same order is shown
        //   the score decreases
        //   the counter of time goes back to zero

    }
}
