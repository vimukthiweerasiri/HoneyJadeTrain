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
    private int rasberryID;

    private Rasberry(int rasberryId) {
        rasberryID = rasberryId;
    }

    public void display(String message) {
        System.out.println(rasberryID + ": The train will arrive at " + message);
    }

    public static Rasberry getInstance(int rasBID) {
        for (int i = 0; i < rasberry.size(); i++) {
            if (rasberry.get(i).rasberryID == rasBID) {
                return rasberry.get(i);
            }
        }
        Rasberry newrasberry = new Rasberry(rasBID);
        rasberry.add(newrasberry);

        return newrasberry;
    }

}
