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
    
    
    private RasberryHandler() {
        
    }

    public static RasberryHandler getInstance() {
        if (rasberryHandler == null) {
            return new RasberryHandler();
        }
        return rasberryHandler;
    }

    public void updateFromTrip(int routeID,ArrayList<Station> station,boolean[] passedStationIds, String[] estimatedArrivalTime) {
        for(int i=0;i<passedStationIds.length;i++){
            if(passedStationIds[i]) Rasberry.getInstance(station.get(i).getId()).display(routeID, estimatedArrivalTime[i]);
        }
    }
    
   
}
