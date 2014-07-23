/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Database.DataHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class RasberryHandler {

    private static RasberryHandler rasberryHandler;
    private static DataHandler dataReader = null;
    
    private RasberryHandler() {
        
    }

    public static RasberryHandler getInstance() {
        if (rasberryHandler == null) {
            return new RasberryHandler();
        }
        return rasberryHandler;
    }

    public void updateFromTrip(int routeID,ArrayList<Station> station, boolean[] passedStationIds, String[] estimatedArrivalTime) {
        //assuming that one number of rP for one station
        for (int i = 0; i < passedStationIds.length; i++) {
            //System.out.println(Rasberry.getInstance(station.get(i).getId()).rasberryID);
            Rasberry.getInstance(station.get(i).getId()).display(routeID, estimatedArrivalTime[i]);
            
            
            //System.out.println(routeID + " " + " " + passedStationIds[i] + " " + estimatedArrivalTime[i]);

        }
    }
}
