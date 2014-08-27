/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.HashMap;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class WebServer {

	private static final Logger logger = Logger.getLogger("WebServer");
	public static String MAP_URL = "/map";
	public static String UPDATE_URL = "/update";
	private static WebServer webserver;
	private HashMap<Integer, TrainStatus> statuses = new HashMap<>();

	// represents current status of a moving train (for web viewing)
	private class TrainStatus {

		public int routeID;
		public float latitude;
		public float longitude;
		public String nextStation;
		public String eta;	// time to reach next station

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof TrainStatus)) {
				return false;
			}

			//same routeID => same record
			TrainStatus that = (TrainStatus) o;
			return that.routeID == this.routeID;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 23 * hash + this.routeID;
			return hash;
		}

		public TrainStatus(int routeID, float latitude, float longitude,
				String nextStation, String eta) {
			this.routeID = routeID;
			this.latitude = latitude;
			this.longitude = longitude;
			this.nextStation = nextStation;
			this.eta = eta;
		}
	}

	private WebServer() {

		// create bindings and start web server
		try {
			logger.log(Level.INFO, "Starting web server...");

			HttpServer server = HttpServer.create(
					new InetSocketAddress(80), 0);
			server.createContext(MAP_URL, new MapViewer());
			server.createContext(UPDATE_URL, new UpdateHandler());
			server.setExecutor(null); // creates a default executor

			server.start();
			logger.log(Level.INFO, "Web server started");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static WebServer getInstance() {
		if (webserver == null) {
			webserver = new WebServer();
		}
		return webserver;
	}

	// store updates received from algorithm, for serving to clients
	public void update(int routeId, float latitude, float longitude,
			String nextStation, String eta) {
		TrainStatus newStatus = new TrainStatus(routeId, latitude, longitude,
				nextStation, eta);

		// remove any existing entries
		statuses.remove(routeId);
		// add our new entry
		statuses.put(routeId, newStatus);
	}

	public void logAll() {
		for (TrainStatus ts : statuses.values()) {
			logger.log(Level.INFO, "Route: {0}", ts.routeID);
			logger.log(Level.INFO, "Latitude: {0}", ts.latitude);
			logger.log(Level.INFO, "Longitude: {0}", ts.longitude);
			logger.log(Level.INFO, "Next Station: {0}", ts.nextStation);
			logger.log(Level.INFO, "Next Station ETA: {0}", ts.eta);
		}
	}

	public static void main(String[] arg) {
		final WebServer server = new WebServer();

		// actual coordinates for the 4 towns
		final float lat1 = 7.0667f, lon1 = 79.95f;
		final float lat2 = 7.1333f, lon2 = 80.0f;
		final float lat3 = 6.8494f, lon3 = 79.9236f;
		final float lat4 = 7.75f, lon4 = 80.25f;
		
//		// coordinates for quick demo
//		final float lat1 = 7.0667f, lon1 = 79.95f;
//		final float lat2 = 7.1667f, lon2 = 80.005f;
//		final float lat3 = 7.3667f, lon3 = 80.23f;
//		final float lat4 = 7.0667f, lon4 = 80.1f;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					server.update(1, lat1 + (float)(Math.random()-0.5)*0.002f,
							lon1 + (float)(Math.random()-0.5)*0.002f,
							"Ganemulla", "3:30");
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					server.update(2, lat2 + (float)(Math.random()-0.5)*0.002f,
							lon2 + (float)(Math.random()-0.5)*0.002f,
							"Gampaha", "6:00");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					server.update(3, lat3 + (float)(Math.random()-0.5)*0.002f,
							lon3 + (float)(Math.random()-0.5)*0.002f,
							"Maharagama", "0:05");
					try {
						Thread.sleep(700);
					} catch (InterruptedException ex) {
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					server.update(4, lat4 + (float)(Math.random()-0.5)*0.002f,
							lon4 + (float)(Math.random()-0.5)*0.002f,
							"Kurunegala", "8:30");
					try {
						Thread.sleep(250);
					} catch (InterruptedException ex) {
					}
				}
			}
		}).start();
//		server.update(3, 5.3234f, 80.3546f, "Maharagama", "0:10");
//		server.update(2, 5.1235f, 80.4547f, "Gampaha", "5:30");
//		server.update(3, 5.3233f, 80.3546f, "Maharagama", "0:05");
//		server.update(1, 5.0234f, 80.9548f, "Ganemulla", "2:10");
//		server.update(2, 5.1236f, 80.4549f, "Gampaha", "5:40");
//		server.update(4, 5.0234f, 80.6546f, "Kurunegala", "8:30");
//		server.update(3, 5.3235f, 80.3546f, "Ragama", "13:30");
//		server.logAll();
	}

	// deploys map to client browser
	class MapViewer implements HttpHandler {

		@Override
		public void handle(HttpExchange httpEx) throws IOException {
			OutputStream os = null;
			try {
				logger.log(Level.INFO, "{0} connected",
						httpEx.getRemoteAddress());

				// source file
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream("web/gps.html")));

				String line;
				StringBuilder response = new StringBuilder();
				while ((line = br.readLine()) != null) {
					response.append(line);
					response.append("\n");
				}

				httpEx.sendResponseHeaders(200, response.length());
				httpEx.setAttribute("Content-Type", "application/json");

				os = httpEx.getResponseBody();
				os.write(response.toString().getBytes());
				os.close();

				logger.log(Level.INFO, "{0} disconnected",
						httpEx.getRemoteAddress());
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
	}

	// handles data requests from client browser (async)
	class UpdateHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpEx) throws IOException {
			OutputStream os = null;

			try {
				logger.log(Level.INFO, "{0} connected",
						httpEx.getRemoteAddress());

//				// expected: a comma separated list of route IDs
//				String query = httpEx.getRequestURI().getQuery();
//				logger.log(Level.INFO, query);
//
//				QueryParser parser = new QueryParser(query);
//				String trains = parser.getParameter("trains");
//				String[] ids = trains.split(",");
//				logger.log(Level.INFO, trains);
//
//				// send data for requested route IDs
//				int routeId;
//				TrainStatus status;
				StringBuilder response = new StringBuilder("[");

				// create response in JSON format with all trains for the device
				// [[ROUTE_ID,LATITUDE,LONGITUDE,NEXT_STATION,ETA](,)]
//				for(String id: ids) {
//					try {
//						routeId = Integer.parseInt(id);
//						status = statuses.get(routeId);
//						if(status != null) {
//							response.append(
//								String.format("{\"id\":%d,\"lat\":%f,\"lon\":%f,"
//								+ "\"next\":\"%s\",\"eta\":\"%s\"],",
//									status.nextStation, status.eta));
//						}
//					}
//					catch(NumberFormatException nfe) {
//						logger.log(Level.SEVERE, nfe.getMessage());
//					}
//				}

				// TODO: implement a way to send statuses based on map region

				// send back all train statuses
				for (TrainStatus status : statuses.values()) {
					response.append(
							String.format("{\"id\":%d,\"lat\":%f,\"lon\":%f,"
							+ "\"next\":\"%s\",\"eta\":\"%s\"},",
							status.routeID, status.latitude, status.longitude,
							status.nextStation, status.eta));
				}
				// terminate JSON array (replacing trailing , if exists)
				if (response.charAt(response.length() - 1) == ',') {
					response.setCharAt(response.length() - 1, ']');
				} else {
					response.append("]");
				}

				httpEx.sendResponseHeaders(200, response.length());

				os = httpEx.getResponseBody();
				os.write(response.toString().getBytes());

				logger.log(Level.INFO, "{0} disconnected",
						httpEx.getRemoteAddress());
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
	}
}
