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
public class WebServer {
    public static WebServer webserver;
    private WebServer(){}
    
    public static WebServer getInstance(){
        if(webserver==null){
            webserver=new WebServer();
        }
        return webserver;
    }
    
    public void handleData(int routeID,ArrayList<Station> station,boolean[] passedStationIds, String[] estimatedArrivalTime){
        //"routeID" gives the id of the route
        //"stations" gives a list of stations where it stops
        //"passedStationIds" gives a binary array | passed=false | havent passed yet = true
        //"estimatedArrivalTime" gives a String of the time that the train will come
    }
}
