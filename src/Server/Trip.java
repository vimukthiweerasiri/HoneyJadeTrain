/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Database.DataHandler;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class Trip {

    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    private ArrayList<Station> stations = new ArrayList<>();
    Time[] stationTimes;

    private boolean setupMode;//setup purpose
    private int numWaypoints;//setup purpose
    private int stationsPassed;
    //making singleton
    public static ArrayList<Trip> trip = new ArrayList<>();
    
     public int routeID;//

    private int[] routeStationIdList;           //removed
    private String[] estimatedArrivalTimeForStations;
    private String[] estimatedArrivalTime;
    private boolean[] passedStationIds;         //is this required?

//    private float startedLatitude;
//    private float startedLongitude;
//    private int passedCheckingFactor;
//    private int nearestLocationId;
//
//    private float[][] wayPoints;            //removed
//    private int[][] IdPreviousNext;         //removed
    private static DataHandler dataReader = null;

    //singleton maker
    public static Trip getInstance(int routeID) {
        for (int i = 0; i < trip.size(); i++) {
            if (trip.get(i).routeID == routeID) {
                return trip.get(i);
            }
        }
        Trip newTrip = new Trip(routeID);
        //newTrip.setRouteId(routeID);
        trip.add(newTrip);
        return newTrip;
    }

    private Trip(int routeId) {
        this.routeID = routeId;
        System.out.println(this.routeID);
        this.setTripData();
        //this.estimatedArrivalTimeForStations();
    }
    //fill data to trip object
    private void setTripData() {
        //initialize waypoints
        try {
            ResultSet rs_1 = dataReader.getWayPointsData(routeID);
            while (rs_1.next()) {
                waypoints.add(new Waypoint(rs_1.getInt(1), rs_1.getInt(2), new Coordinate(rs_1.getFloat(3), rs_1.getFloat(4)), rs_1.getTime(5), rs_1.getInt(6), rs_1.getInt(7)));
                //waypoints.add(;
              //  System.out.println(rs_1.getTime(5).toString());
               // System.out.println(rs_1.getTimestamp(5).toString());
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
        }
        //initializing wayPoints
        try {
            ResultSet rs = dataReader.getStationsData(routeID);
            while (rs.next()) {
                stations.add(new Station(rs.getInt(1), new Coordinate(rs.getFloat(2), rs.getFloat(3)), new Coordinate(rs.getFloat(4), rs.getFloat(5))));
            }
            this.passedStationIds = new boolean[stations.size()];
            this.stationTimes = new Time[stations.size()];

            //initializing the estimated arrival times
            for (int i = 0; i < stations.size(); i++) {
                Coordinate upside = stations.get(i).getUpside();
                Waypoint nearest = getNearestWaypoint(upside.getLatitude(), upside.getLongitude());
                System.out.println(nearest.getActualReachTime().toString());
                stationTimes[i] = nearest.getEstimateReachTime();
            }
            System.out.println("");
        } catch (SQLException ex) {
            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute(LocationBox locationBox) {
        Waypoint waypoint;

        if (!setupMode) {
            waypoint = getNearestWaypoint(locationBox.getLatitude(), locationBox.getLongitude());
            long delay = waypoint.getEstimateReachTime().getTime() - locationBox.getSent_time().getTime();

            for (int i = 0; i < stationTimes.length; i++) {
                System.out.println(delay);
                //stationTimes[i] = new Date(stationTimes[i].getTime() + delay);
                //stationTimes[i] = new Date(delay);
            }
//            System.out.println("");
        } else {
            Station st = stations.get(this.stationsPassed);       //next station
            float stationLength = (float) (Math.pow(st.getUpside().getLatitude() - st.getDownside().getLatitude(), 2) + Math.pow(st.getUpside().getLongitude() - st.getDownside().getLongitude(), 2));
            float a, b;
            a = (float) (Math.pow((locationBox.getLatitude() - st.getUpside().getLatitude()), 2) - Math.pow((locationBox.getLongitude() - st.getUpside().getLongitude()), 2));
            b = (float) (Math.pow((locationBox.getLatitude() - st.getDownside().getLatitude()), 2) - Math.pow((locationBox.getLongitude() - st.getDownside().getLongitude()), 2));

            if (stationLength > a && stationLength > b) {
                stationsPassed++;
                //caltulate number of waypoints
//                waypoint = new Waypoint(numWaypoints, this.routeID, new Coordinate(locationBox.getLatitude(), locationBox.getLongitude()), locationBox.getRecieved_time(), st.getId(), st.getId());
            } else {
 //               waypoint = new Waypoint(numWaypoints, this.routeID, new Coordinate(locationBox.getLatitude(), locationBox.getLongitude()), locationBox.getRecieved_time(), stations.get(stationsPassed - 1).getId(), st.getId());
            }

//            dataReader.saveWaypoint(waypoint);
            numWaypoints++;
        }

    }

    public Waypoint getNearestWaypoint(float latitude, float longitude) {
        float lon, lat, distance, min = Float.MAX_VALUE;
        Waypoint temp = null, nearest = null;
        Coordinate location = null;

        //compare with all waypoint positions
        Iterator<Waypoint> iter = waypoints.iterator();
        while (iter.hasNext()) {
            temp = iter.next();
            location = temp.getLocation();
            lon = location.getLongitude();
            lat = location.getLatitude();

            //calcuate (square of) distance
            distance = (lon - longitude) * (lon - longitude)
                    + (lat - latitude) * (lat - latitude);
            //shortest distance?
            if (distance < min) {
                min = distance;
                nearest = temp;
            }
        }
        //returns null if no waypoints were found
        return nearest;
    }

    public float[] getCordinatesOfTheStations(int stationID, int latitude, int longitude) {
        return null;
    }

    public void countDelayToStations() {

    }

    public void didStationPassed() {
    }

    public void estimateTimeForAllStations() {
    }

    public void getTimeOfNeareestLocation() {
    }

    public void updateRasberryHandler() {
        RasberryHandler.getInstance().updateFromTrip(routeID, routeStationIdList, passedStationIds, estimatedArrivalTime);
    }

    private void estimatedArrivalTimeForStations() {
    }

    public void setRouteId(int rid) {
        routeID = rid;
    }

   

    static {
        try {
            dataReader = new DataHandler();
        } catch (SQLException ex) {
            Logger.getLogger(Trip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //////////////////////
    public int getIdOfNearestLocation(int latitude, int longitude) {
        //get ID from the array
        return 0;
    }
}
