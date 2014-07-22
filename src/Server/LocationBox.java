/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.sql.Date;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class LocationBox {

    private String imei;
    private Date sent_time, recieved_time;
    private float latitude, longitude, speed, direction;

    /*
     private long imei;
     private Date time;
     private float latitude, longitude;
     private float speed, direction;
     private int delay;
     */
    public LocationBox(String imei, Date sent_time, Date recieved_time, float latitude, float longitude, float speed, float direction) {
        this.imei = imei;
        this.sent_time = sent_time;
        this.recieved_time = recieved_time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
    }

    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the sent_time
     */
    public Date getSent_time() {
        return sent_time;
    }

    /**
     * @param sent_time the sent_time to set
     */
    public void setSent_time(Date sent_time) {
        this.sent_time = sent_time;
    }

    /**
     * @return the recieved_time
     */
    public Date getRecieved_time() {
        return recieved_time;
    }

    /**
     * @param recieved_time the recieved_time to set
     */
    public void setRecieved_time(Date recieved_time) {
        this.recieved_time = recieved_time;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the direction
     */
    public float getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(float direction) {
        this.direction = direction;
    }

}
