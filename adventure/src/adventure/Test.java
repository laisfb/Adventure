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

    private final static boolean testingMode = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // level , time
        if (!testingMode) {
            TakeRequests take = new TakeRequests(1, 0);
            take.bigBang(900,900,1);
        }
        else {
            
            for(int i=0; i<500; i++) {
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
    

    // There is only two ways to change a world
    //   - by clicking the button on the right 
    //   - when all clients are gone (next level / game over)
    public static void check_transitions() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeRequests take = new TakeRequests(level, 0);
        MakeRequests make = new MakeRequests(take.listOfClients, take.LEVEL, take.score);
        DeliverRequests deliver = new DeliverRequests(make.listOfClients, make.beingMade, make.LEVEL, 0, make.score);
        
        
        Posn pos = randomPos(); // new Posn(810, 846);
        // System.out.println("Pos: (" + pos.x + "," + pos.y + ")");
        // System.out.println("Box: (" + take.box.pinhole.x + "," + take.box.pinhole.y + ")");
        
        
       // Changing from TakeRequests to MakeRequests
        if (pos.inside(take.box)) {
        
            // Has to change to the right world
            if (!(take.onMouseClicked(pos) instanceof MakeRequests))
                throw new RuntimeException("ERROR IN: check_transitions (TakeRequests -> MakeRequests)");
            
            // Parameters must stay the same:
            //   - the list of clients
            //   - the current level
            //   - the score
            MakeRequests newWorld = (MakeRequests)take.onMouseClicked(pos);
            if (!(take.listOfClients).equals(newWorld.listOfClients) ||
                 (take.LEVEL != newWorld.LEVEL) ||
                 (take.score != newWorld.score))
                throw new RuntimeException("ERROR IN: check_transitions_parameters (TakeRequests -> MakeRequests)");
            
        }
        
        
        // Changing from MakeRequests to DeliverRequests
        if (pos.inside(make.boxRight)) {
            
            // Start a random Request
            //   "pos" can be anything, since it's not going to be shown
            make.beingMade = new Request(pos, make.LEVEL);
            
            // Has to change to the right world
            if (!(make.onMouseClicked(pos) instanceof DeliverRequests))
                throw new RuntimeException("ERROR IN: check_transitions (MakeRequests -> DeliverRequests)");
            
            // Parameters must stay the same:
            //   - the list of clients
            //   - the current level
            //   - the score
            //   - the request being made (most important!)
            DeliverRequests newWorld = (DeliverRequests)make.onMouseClicked(pos);
            if (!(make.listOfClients).equals(newWorld.listOfClients) ||
                 (make.LEVEL != newWorld.LEVEL) ||
                 (make.score != newWorld.score) ||
                !(make.beingMade).equals(newWorld.done))
                throw new RuntimeException("ERROR IN: check_transitions_parameters (MakeRequests -> DeliverRequests)");         
            
        }
                
        // Changing from DeliverRequests to MakeRequests
        if (pos.inside(deliver.boxRight)) {
            
            // Has to change to the right world
            if (!(deliver.onMouseClicked(pos) instanceof MakeRequests))
                throw new RuntimeException("ERROR IN: check_transitions (DeliverRequests -> MakeRequests)");
            
            // Parameters must stay the same:
            //   - the list of clients
            //   - the current level
            //   - the score
            MakeRequests newWorld = (MakeRequests)deliver.onMouseClicked(pos); 
            if (!(deliver.listOfClients).equals(newWorld.listOfClients) ||
                 (deliver.LEVEL != newWorld.LEVEL) ||
                 (deliver.score != newWorld.score))
                throw new RuntimeException("ERROR IN: check_transitions_parameters (DeliverRequests -> MakeRequests)");
            
        }
        
        
        boolean gone = Client.allGone(deliver.listOfClients);
        
        // Just making sure it's not trying to go to the MakeRequests world
        //   or trying to drop a request
        if (!pos.inside(deliver.boxRight) && !pos.inside(deliver.boxLeft) && gone && 
                !((deliver.onMouseClicked(pos) instanceof nextLevel)  || // Case of when all requests are delivered
                  // Cases of when the last client gives up
                  (take.onTick() instanceof nextLevel) ||
                  (make.onTick() instanceof nextLevel) ||
                  (deliver.onTick() instanceof nextLevel)))
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
            
            for (int j=0; j<9; j++) {
                if (req.howMany(Request.everyFood[j]) > 1)
                    throw new RuntimeException("ERROR IN: check_TakeRequests (repeated foods)");
            }
            
        }
        
        // If you click on a client
        //   - the request must show up
        //   - the client's waiting time restart
        //   - the score stays the same
        Posn pos = randomPos();
        
        // To make sure it's not going to change worlds
        while (pos.inside(take.box))
            pos = randomPos(); 
                
        TakeRequests newWorld = (TakeRequests) take.onMouseClicked(pos);
                
        for (int i=0; i<newWorld.listOfClients.length; i++) {
            Client c = newWorld.listOfClients[i];
            if (pos.insideHalf(c.getImage()) &&
                 !(c.showRequestHun() &&
                   c.waiting == 0 &&
                   newWorld.score == take.score))
                throw new RuntimeException("ERROR IN: check_TakeRequests (clicked on a client)");
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
        while (pos.inside(make.boxRight))
            pos = randomPos(); 
        
        // Start a random Request
        //   "pos" can be anything, since it's not going to be shown
        make.beingMade = new Request(pos, make.LEVEL); 
        int sizeBefore = make.beingMade.size;
       
        MakeRequests newWorld = (MakeRequests) make.onMouseClicked(pos);
        
        // If the "Start Over" button is clicked
        //   - the world stays the same,
        //   - the request is restarted, meaning the size is zero,       
        //   - the score stays the same
        if (pos.inside(make.boxLeft) && (!(newWorld instanceof MakeRequests) || 
                                           newWorld.beingMade.size != 0 ||
                                           newWorld.score != make.score))
            throw new RuntimeException("ERROR IN: check_MakeRequests (size of beingMade when start over)");         
                
        // Everytime a box is clicked, the request's size increase in one
        for(int i=0; i<9; i++) {
            if (pos.inside(make.boxes[i]) && newWorld.beingMade.size != sizeBefore + 1)
                throw new RuntimeException("ERROR IN: check_MakeRequests (size of updated beingMade)");
        }
        
    }
    
    public static void check_DeliverRequests() {
        
        int level = randomInt(4) + 1; // From 1 to 5
        TakeRequests take = new TakeRequests(level, 0);
        MakeRequests make = new MakeRequests(take.listOfClients, take.LEVEL, take.score);        
        DeliverRequests deliver = new DeliverRequests(make.listOfClients, make.beingMade, make.LEVEL, 0, make.score);
        
        Posn pos = randomPos();
        
        // To make sure it's not going to change worlds
        while (pos.inside(deliver.boxRight))
            pos = randomPos();
        
        // World generated after the click of the mouse
        DeliverRequests newWorld = (DeliverRequests)(deliver.onMouseClicked(pos));
        
        int oldScore = deliver.score;
        int newScore = newWorld.score;

        // If the "Drop Food" button is clicked
        //   - the world stays the same,
        //   - the request is dropped, meaning the size is now zero,
        //   - the score stays the same
        if (pos.inside(deliver.boxLeft) && (!(newWorld instanceof DeliverRequests) ||
                                              newWorld.done.size != 0 ||
                                              newScore != oldScore))
            throw new RuntimeException("ERROR IN: check_transitions (Dropping the request)");
        
        
        // Pretend to deliver using one of the client's request
        //   "pos" can be anything, since it's not going to be shown
        deliver.done = newWorld.listOfClients[randomInt(deliver.listOfClients.length-1)].getRequest();
        
        
        for (int i=0; i<deliver.listOfClients.length; i++) {
            Client c = newWorld.listOfClients[i];
            
            // When a client is clicked
            // If it's not going to change world
            //   (meaning it's not the last client's request to be delivered)
            if (pos.insideHalf(c.getImage()) && (deliver.onMouseClicked(pos) instanceof DeliverRequests)) {
                
                newWorld = (DeliverRequests)(deliver.onMouseClicked(pos));
                
                // If there is no request to be delivered
                //   then the player is asking the client's request
                // So the request is shown, the overall time goes back to zero
                //   the client's waiting time and the scores decreases a little bit 
                // (never going below zero, so it can be equal to the old one)
                if (deliver.done.size == 0 && (!newWorld.listOfClients[i].showRequestHun() ||
                                                newWorld.listOfClients[i].waiting != 0 ||
                                                deliver.time != 0 || newScore > oldScore))
                    throw new RuntimeException("ERROR IN: check_DeliverRequests (asking the request again)");

                // If there is a request being delivered
                else if (deliver.done.size > 0) {
                    
                    // If it's right, the request is delivered, the client goes away and the score increases
                    if (deliver.done.equals(c.getRequest()) &&
                            (newWorld.done.size != 0 || c.showHun() || newScore <= oldScore))
                        throw new RuntimeException("ERROR IN: check_DeliverRequests (delivering the right order)");
                    
                    // If it's wrong, the request and the client stay, and the score decreases
                    //    (the score never goes below zero, so it can be equal to the old one)
                    else if (!deliver.done.equals(c.getRequest()) &&
                                (newWorld.done.size == 0 || !c.showHun() || newScore > oldScore))
                        throw new RuntimeException("ERROR IN: check_DeliverRequests (delivering the wrong order)");

                }
            }
        }
        
    }
}