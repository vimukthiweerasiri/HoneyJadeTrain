/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class RasberryHandler {

	private static final Logger logger = Logger.getLogger("RasberryHandler");
	private static RasberryHandler rasberryHandler;
	private static final String RASBERRY_URL = "/rasberry";	//data updates

	private RasberryHandler() {
		// create bindings and start web server
		try {
			logger.log(Level.INFO, "Starting Rasberry handling server...");

			// TODO: add ThreadPool for concurrent processing
			
			HttpServer server = HttpServer.create(
					new InetSocketAddress(8081), 0);
			server.createContext(RASBERRY_URL,
					new RasberryHandler.UpdateHandler());
			server.setExecutor(null); // creates a default executor

			server.start();
			logger.log(Level.INFO, "Rasberry handling server started");
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	public static RasberryHandler getInstance() {
		if (rasberryHandler == null) {
			rasberryHandler = new RasberryHandler();
		}
		return rasberryHandler;
	}

	public void updateFromTrip(int routeID, ArrayList<Station> station,
			boolean[] passedStationIds, String[] estimatedArrivalTime) {
		for (int i = 0; i < passedStationIds.length; i++) {
			if (passedStationIds[i]) {
				Rasberry.getInstance(station.get(i).getId())
						.update(routeID, estimatedArrivalTime[i]);
			}
		}
	}

	// for updating remote Rasberry devices
	class UpdateHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpEx) throws IOException {
			OutputStream os = null;

			try {
				logger.log(Level.INFO, "{0} connected",
						httpEx.getRemoteAddress());

				// get ID of device that sent the request
				String query = httpEx.getRequestURI().getQuery();
				logger.log(Level.INFO, query);

				QueryParser parser = new QueryParser(query);
				int rasberyId = Integer.parseInt(parser.getParameter("id"));
				logger.log(Level.INFO, "Rasberry ID: {0}", rasberyId);

				Rasberry client = Rasberry.getInstance(rasberyId);
				HashMap<Integer, String> data = client.getUpdates();
				
				// create response in JSON format with all trains for the device
				// [[ROUTE_ID,ETA](,)]
				StringBuilder response = new StringBuilder("[");
				for(int routeId: data.keySet()) {
					response.append(String.format("{\"id\":%d,\"eta\":\"%s\"},",
							routeId, data.get(routeId)));
				}
				// terminate JSON array (replacing trailing , if exists)
				if(response.charAt(response.length() - 1) == ',')
					response.setCharAt(response.length() - 1, ']');
				else
					response.append("]");
				
				httpEx.sendResponseHeaders(200, response.length());

				os = httpEx.getResponseBody();
				os.write(response.toString().getBytes());

				logger.log(Level.INFO, "{0} disconnected",
						httpEx.getRemoteAddress());
			} catch (NumberFormatException e) {
				logger.log(Level.SEVERE, e.getMessage());
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
	}
}
