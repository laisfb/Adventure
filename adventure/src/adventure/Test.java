/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.Random;
import javalib.worldimages.Posn;
import javax.swing.BoxLayout;

/**
 *
 * @author laisfb
 */
public class Test {

    private final static boolean testingMode = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // level , showRequests , time
        if (!testingMode) {
            TakeRequests take = new TakeRequests(1, 0);
            take.bigBang(900,900,1);
        }
        else {
            
            for(int i=0; i<500; i++) {
                //System.out.println("Test " + (i + 1));
                check_transitions();
                check_TakeRequests();
                check_MakeRequests();
                check_DeliverRequests();
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
        TakeRequests take = new TakeRequests(level, 0);
        MakeRequests make = new MakeRequests(take.listOfClients, take.LEVEL, take.score);
        DeliverRequests deliver = new DeliverRequests(make.listOfClients, make.beingMade, make.LEVEL, 0, make.score);
        
        Posn pos = randomPos(); // new Posn(810, 846);
        // System.out.println("Pos: (" + pos.x + "," + pos.y + ")");
        // System.out.println("Box: (" + take.box.pinhole.x + "," + take.box.pinhole.y + ")");
        
        
        if (pos.inside(take.box) && !(take.onMouseClicked(pos) instanceof MakeRequests))
            throw new RuntimeException("ERROR IN: check_transitions (TakeRequests -> MakeRequests)");
        
        
        if (pos.inside(make.boxRight) && !(make.onMouseClicked(pos) instanceof DeliverRequests))
            throw new RuntimeException("ERROR IN: check_transitions (MakeRequests -> DeliverRequests)");
        
        
        // If the "Start Over" button is clicked
        //   the world stays the same
        //   the request is restarted, meaning the size is zero        
        else if (pos.inside(make.boxLeft) && !(make.onMouseClicked(pos) instanceof MakeRequests) && ((MakeRequests)make.onMouseClicked(pos)).beingMade.size != 0)
            throw new RuntimeException("ERROR IN: check_MakeRequests (size of beingMade when start over)");
        
        
        if (pos.inside(deliver.boxRight) && !(deliver.onMouseClicked(pos) instanceof MakeRequests))
            throw new RuntimeException("ERROR IN: check_transitions (DeliverRequests -> MakeRequests)");
        
        
        // If the "Drop Food" button is clicked
        //   the world stays the same
        //   and the request is dropped, meaning the size is now zero
        else if (pos.inside(deliver.boxLeft) && !(deliver.onMouseClicked(pos) instanceof DeliverRequests) && ((DeliverRequests)deliver.onMouseClicked(pos)).done.size != 0)
            throw new RuntimeException("ERROR IN: check_transitions (Dropping the request)");
        
        
        boolean show = false;
        for (int i=0; i<deliver.listOfClients.length; i++)
            if (deliver.listOfClients[i].showHun())
                show = true;
        
        // Just making sure it's not trying to go to the MakeRequests world
        //   or trying to drop a request
        if (!pos.inside(deliver.boxRight) && !pos.inside(deliver.boxLeft) && !show && !(deliver.onMouseClicked(pos) instanceof nextLevel))
                throw new RuntimeException("ERROR IN: check_DeliverRequests (DeliverRequests -> nextLevel)");
        
    }
    
    public static void check_TakeRequests() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeRequests take = new TakeRequests(level, 0);
        
        // In the TakeRequests world there are always between 1 and 3 clients
        if (take.listOfClients.length < 1 || take.listOfClients.length > 3)
            throw new RuntimeException("ERROR IN: check_TakeRequests (size of listOfClients)");            
        
        // Each request must be have at least 3 elements, all different
        for (int i=0; i<take.listOfClients.length; i++) {
            Request req = take.listOfClients[i].getRequest();
            
            if (req.size < 3)
                throw new RuntimeException("ERROR IN: check_TakeRequests (size of Request)");
            
            for (int j=0; j<req.size; j++) {
                if (req.howMany(req.listOfFood[j]) != 1)
                    throw new RuntimeException("ERROR IN: check_TakeRequests (repeated foods)");
            }
        }
        
    }
    
    public static void check_MakeRequests() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeRequests take = new TakeRequests(level, 0);
        MakeRequests make = new MakeRequests(take.listOfClients, take.LEVEL, take.score);
        
        // Every time the MakeRequests world is created,
        //   the request being made is empty
        //   and there are always 9 boxes of food
        
        if (make.beingMade.size != 0)
                throw new RuntimeException("ERROR IN: check_MakeRequests (initial size of beingMade)");
        
        if (make.boxes.length != 9)
                throw new RuntimeException("ERROR IN: check_MakeRequests (number of boxes)");
        
        
        Posn pos = randomPos();        
        
        // To make sure it's not going to change worlds
        while (pos.inside(make.boxLeft) || pos.inside(make.boxRight))
            pos = randomPos(); 
        
        
        // Start a random Request
        //   "pos" can be anything, since it's not going to be shown
        make.beingMade = new Request(pos, make.LEVEL);
        
        int sizeBefore = make.beingMade.size;
        make = (MakeRequests) make.onMouseClicked(pos);
        
        // Everytime a box is clicked, the request's size increase in one
        for(int i=0; i<9; i++) {
            if (pos.inside(make.boxes[i]) && make.beingMade.size != sizeBefore + 1)
                throw new RuntimeException("ERROR IN: check_MakeRequests (size of updated beingMade)");
        }
        
    }
    
    public static void check_DeliverRequests() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeRequests take = new TakeRequests(level, 0);
        MakeRequests make = new MakeRequests(take.listOfClients, take.LEVEL, take.score);
        
        DeliverRequests deliver = new DeliverRequests(make.listOfClients, make.beingMade, make.LEVEL, 0, make.score);
        
        Posn pos = randomPos();
        
        // To make sure it's not going to change worlds or drop the request
        while (pos.inside(deliver.boxLeft) || pos.inside(deliver.boxRight))
            pos = randomPos(); 
        
        // Pretend to deliver using a random Request
        //   "pos" can be anything, since it's not going to be shown
        deliver.done = new Request(pos, make.LEVEL);        
        
        // World generated after the click of the mouse
        DeliverRequests newDeliver = (DeliverRequests)(deliver.onMouseClicked(pos)); 
        
        
        // The list of clients must stay the same
        //   what changes is if the clients/requests are visible or not
        if (!deliver.listOfClients.equals(newDeliver.listOfClients))
            throw new RuntimeException("ERROR IN: check_DeliverRequests (listOfClients changed)");
        
        
        int oldScore = deliver.score;
        int newScore = newDeliver.score;
        
        for (int i=0; i<deliver.listOfClients.length; i++) {
            Client c = newDeliver.listOfClients[i];
            
            // When a client is clicked
            if (pos.insideHalf(c.getImage())) {
                
                // If there is no request to be delivered
                //   then the player is asking the client's request
                // So the counter goes back to zero
                //   and the scores decreases a little bit (never going below zero, so it can be equal to the old one)
                if (deliver.done.size == 0 && (deliver.time != 0 || newScore > oldScore))
                    throw new RuntimeException("ERROR IN: check_DeliverRequests (asking the request again)");

                // If there is a request being delivered
                else if (deliver.done.size > 0) {
                    
                    // If it's right, the request is delivered, the client goes away and the score increases
                    if (deliver.done.equals(c.getRequest()) &&
                            (newDeliver.done.size != 0 || c.showHun() || newScore <= oldScore))
                        throw new RuntimeException("ERROR IN: check_DeliverRequests (delivering the right order)");
                    
                    // If it's wrong, the request and the client stay, and the score decreases
                    //    (the score never goes below zero, so it can be equal to the old one)
                    else if (!deliver.done.equals(c.getRequest()) &&
                            (newDeliver.done.size == 0 || !c.showHun() || deliver.score > oldScore))
                        throw new RuntimeException("ERROR IN: check_DeliverRequests (delivering the wrong order)");

                }
            }
        }
        
    }
}
