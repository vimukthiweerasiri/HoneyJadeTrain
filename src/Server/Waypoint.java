package Server;

import java.sql.Date;

/**
 * @author Janaka
 */
public class Waypoint {

    private Date estimateReachTime;
    private Date actualReachTime;
    private Coordinate location;
    private int id;
    private int routeID;
    private int prevStationID;
    private int nextStationID;

    public Waypoint(int id, int routeID, Coordinate location, Date estimateReachTime, int prevStationID, int nextStationID) {
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
    public Date getEstimateReachTime() {
        return estimateReachTime;
    }

    /**
     * @return the actualReachTime
     */
    public Date getActualReachTime() {
        return actualReachTime;
    }

    /**
     * @param actualReachTime the actualReachTime to set
     */
    public void setActualReachTime(Date actualReachTime) {
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
