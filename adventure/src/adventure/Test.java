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
            TakeOrders take = new TakeOrders(1);
            take.bigBang(900,900,1);
        }
        else {
            check_transitions();
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
        TakeOrders take = new TakeOrders(1);
        take.bigBang(900,900,1);
        //check_TakeOrders(deliver);
        
        MakeOrders make = new MakeOrders(take.listOfClients,1);
        DeliverOrders deliver = new DeliverOrders(make.listOfClients, make.beingMade, 1);
        
        Posn pos = randomPos();
        // System.out.println("Pos: (" + pos.x + "," + pos.y + ")");
        // System.out.println("Box: (" + take.box.pinhole.x + "," + take.box.pinhole.y + ")");
        
        if (pos.inside(take.box)) {
            if (make.equals(take.onMouseClicked(pos))) {
                make.bigBang(900,900,1);
                //check_MakeOrders(make);
            }
            else
                throw new RuntimeException("ERROR IN: check_transitions (takeOrders -> makeOrders)");
        }
        
        if (pos.inside(make.boxRight)) {
            if (deliver.equals(make.onMouseClicked(pos))) {
                deliver.bigBang(900,900,1);
                //check_DeliverOrders(deliver);
            }
            else
                throw new RuntimeException("ERROR IN: check_transitions (takeOrders -> makeOrders)");
        }
        
    }



}
