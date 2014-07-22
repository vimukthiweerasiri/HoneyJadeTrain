package Server;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Janaka
 */
public class Waypoint {

    private Time estimateReachTime;
    private Time actualReachTime;
    private Coordinate location;
    private int id;
    private int routeID;
    private int prevStationID;
    private int nextStationID;

    public Waypoint(int id, int routeID, Coordinate location, Time estimateReachTime, int prevStationID, int nextStationID) {
        
        this.estimateReachTime = estimateReachTime;
        this.actualReachTime = this.estimateReachTime;
        this.location = location;
        this.id = id;
        this.routeID = routeID;
        this.prevStationID = prevStationID;
        this.nextStationID = nextStationID;
    }

    /**
     * @return the estimateReachTime
     */
    public Time getEstimateReachTime() {
        return estimateReachTime;
    }

    /**
     * @return the actualReachTime
     */
    public Time getActualReachTime() {
        return actualReachTime;
    }

    /**
     * @param actualReachTime the actualReachTime to set
     */
    public void setActualReachTime(Time actualReachTime) {
        this.actualReachTime = actualReachTime;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the routeID
     */
    public int getRouteID() {
        return routeID;
    }

    /**
     * @return the prevStationID
     */
    public int getPrevStationID() {
        return prevStationID;
    }

    /**
     * @return the nextStationID
     */
    public int getNextStationID() {
        return nextStationID;
    }

    /**
     * @return the location
     */
    public Coordinate getLocation() {
        return location;
    }
}
