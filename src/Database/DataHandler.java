/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Server.Waypoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eranda
 */
public class DataHandler {

    private Connection conn;
    private String hostname;
    private String dbName;
    private String username;
    private String password;
    private static final Logger logger = Logger.getLogger("Database");

    private static DataHandler instance;

    public DataHandler() throws SQLException {
        this.hostname = "localhost";
        this.dbName = "gpsdata";
        this.username = "root";
        this.password = "abc";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            throw new SQLException("Error loading MySQL driver");
        }
        connectToDatabase();
    }

    private void connectToDatabase() throws SQLException {
        StringBuilder connectionUrl = new StringBuilder("jdbc:mysql://");
        connectionUrl.append(hostname);
        connectionUrl.append("/").append(dbName);
        logger.log(Level.INFO, connectionUrl.toString());
        conn = DriverManager.getConnection(connectionUrl.toString(),
                username, password);
    }

    public static DataHandler getInstance() {
        try {
            if (instance == null) {
                instance = new DataHandler();
            }
            return instance;
        } catch (SQLException ex) {
            Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet getRouteID(String imei) throws SQLException {
        return run("SELECT routeID FROM route WHERE imei = '" + imei + "'");
    }
    
    public ResultSet getStationNameById(int id) throws SQLException {
        return run("SELECT stationName FROM `station_rp` WHERE stationID='"+Integer.toString(id)+"'");
    }
    
    public ResultSet run(String query) throws SQLException {
        Statement select = conn.createStatement();
        ResultSet result = select.executeQuery(query);
        return result;
    }

    public ResultSet getStationsData(int routeID) throws SQLException {
        return run("SELECT stationID,up_latitude,up_longitude,down_latitude,down_longitude,stationOrder FROM station NATURAL JOIN route_stations WHERE routeID = '" + routeID + "'ORDER BY stationOrder ASC;");
    }

    public ResultSet getWayPointsData(int routeID) throws SQLException {
        return run("SELECT * FROM waypoint WHERE routeID = '" + routeID + "'ORDER BY waypointIndex ASC;");
        /*float[][] output;
         Statement select = conn.createStatement();
         ResultSet result = select.executeQuery("SELECT latitude,longitude FROM waypoint WHERE routeID = '" + routeID + "' ;");
         ArrayList<Float> temp1 = new ArrayList<>();
         ArrayList<Float> temp2 = new ArrayList<>();
        
         while (result.next()) {
         temp1.add(result.getFloat(1));
         temp2.add(result.getFloat(2));
         }
        
         output = new float[2][temp1.size()];
         for(int i=0, len = temp1.size(); i < len; i++){
         output[0][i] = temp1.get(i);
         output[1][i] = temp1.get(i);
         }
         //output[0] has lattudes, output[1] has longitudes
         return output;
         */
    }

    public void saveWaypoint(Waypoint waypoint) {

    }
}
