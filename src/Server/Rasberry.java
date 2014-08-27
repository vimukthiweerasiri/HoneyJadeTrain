/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class Rasberry {

	public static HashMap<Integer, Rasberry> rasberries = new HashMap<>();
	public int rasberryID;
	private HashMap<Integer, String> updates;	// (RouteID, ETA) pairs

	private Rasberry(int rasberryId) {
		this.rasberryID = rasberryId;
		this.updates = new HashMap<>();
	}

	// display details of current Rasberry object
	public void display() {
		System.out.println("Station " + rasberryID + ": ");
		
		// iterate over data in current Rasberry object 
		Iterator<Integer> iter = updates.keySet().iterator();
		int route;
		while(iter.hasNext()) {
			route = iter.next();
			System.out.println("- The train " + route + " will arrive in "
					+ updates.get(route) + " seconds");
		}
	}
	
	// update list with latest details
	public void update(int routeId, String eta) {
		// delete any existing ETA entries for current route
		updates.remove(routeId); 
		// now add the new one
		updates.put(routeId, eta);
	}
	
	public HashMap<Integer, String> getUpdates() {
		return this.updates;
	}

	public static Rasberry getInstance(int rasBID) {
		Rasberry result = rasberries.get(rasBID); 
		if (result == null) {
			result = new Rasberry(rasBID);
			rasberries.put(rasBID, result);
		}
		return result;
	}
}
