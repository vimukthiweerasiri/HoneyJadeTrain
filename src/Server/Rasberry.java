/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Database.DataHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class Rasberry {

    public static ArrayList<Rasberry> rasberry = new ArrayList<>();
    public int rasberryID;
    private String stationName;
    private static DataHandler dataReader = null;

    private Rasberry(int rasberryId) {
        this.rasberryID = rasberryId;
        ResultSet rs=null;
        
        try {
            rs = dataReader.getStationNameById(rasberryId);
        } catch (SQLException ex) {
            Logger.getLogger(Rasberry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            
            while (rs.next()) {
                stationName=rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Rasberry.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }

    public void display(int id,String message) {
        System.out.println(stationName + ": The train "+id+" will arrive at " + message);
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

     static {
        try {
            dataReader = new DataHandler();
        } catch (SQLException ex) {
            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
