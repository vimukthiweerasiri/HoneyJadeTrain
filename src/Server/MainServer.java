/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Database.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class MainServer {

    private static MainServer mainServer;
    private DataHandler handler;

    private MainServer() {
        handler = DataHandler.getInstance();
    }

    public static MainServer getInstance() {
        if (mainServer == null) {
            mainServer = new MainServer();
        }
        return mainServer;
    }

    public int findRouteIdByImei(String imie) {
        try {
            ResultSet ids = handler.getRouteID(imie);
            if (ids.next()) {
                return ids.getInt("routeID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void executeLocation(LocationBox locationbox) {
        int routeid = findRouteIdByImei(locationbox.getImei());
        Trip.getInstance(routeid).execute(locationbox);
       // Waypoint way0=Trip.getInstance(routeid).getNearestWaypoint((float)(2.9), (float) 5.2);
        //Waypoint way1=Trip.getInstance(routeid).getNearestWaypoint(3, (float) 5.3);
        //Trip.getInstance(routeid).execute(locationbox);
        //System.out.println(Trip.getInstance(routeid).routeID);
    }

    public Trip getInstanceOfTrip() {
        return null;
    }

}
