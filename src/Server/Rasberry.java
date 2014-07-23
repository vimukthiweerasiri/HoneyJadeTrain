/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.ArrayList;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class Rasberry {

    public static ArrayList<Rasberry> rasberry = new ArrayList<>();
    public int rasberryID;

    private Rasberry(int rasberryId) {
        this.rasberryID = rasberryId;
    }

    public void display(int routeID,String message) {
        System.out.println(rasberryID + ": The train "+routeID+" will arrive at " + message);
    }

    public static Rasberry getInstance(int rasBID) {
        for (Rasberry rasberry1 : rasberry) {
            if (rasberry1.rasberryID == rasBID) {
                return rasberry1;
            }
        }
        Rasberry newrasberry = new Rasberry(rasBID);
        rasberry.add(newrasberry);

        return newrasberry;
    }

}
