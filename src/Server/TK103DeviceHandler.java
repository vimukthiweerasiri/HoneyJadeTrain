package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.lang.Thread.sleep;
import java.sql.Date;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class TK103DeviceHandler {

    private static TK103DeviceHandler tK103DeviceHandler;
    public static int i=0;

    private TK103DeviceHandler() {
    }

    public static TK103DeviceHandler getInstance() {
        i++;
        if (tK103DeviceHandler == null) {
            tK103DeviceHandler = new TK103DeviceHandler();
        }
        return tK103DeviceHandler;
    }

    //starts listening to the port and to recieve data
    public void executeServer() {
        //set the port number
        int port = 4480;

        try {
            ServerSocket welcomeSocket = new ServerSocket(port);
            //until shut down
            while (true) {
                //making the socket connection
                System.out.println("waiting for a connection...");
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println(connectionSocket.getInetAddress().toString() + " connected");

                //making the input & output streams
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                int c = 5;
                String s = "";
                while (c > 0) {
                    try {
                        c = inFromClient.read();
                        //System.out.print((char) c);
                        s += (char) c;
                        if (c == ';') {
                            System.out.println(s + " " + new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss").format(Calendar.getInstance().getTime()));
                            {
                                if (s.contains("##")) {
                                    outToClient.writeBytes("LOAD");
                                    sleep(3000);
                                    outToClient.writeBytes("ON");
                                    sleep(1000);
                                    outToClient.writeBytes("ON");
                                    sleep(3000);
                                    outToClient.writeBytes("**,imei:863070014296916,C,10s");
                                    sleep(1000);
                                    outToClient.writeBytes("**,imei:863070014296916,C,10s");
                                    System.out.println("Initializing...");
                                }
                            }
                            handleData(s);
                            s = "";
                        }
                    } catch (IOException e) {
                        try {
                            sleep(2000);
                        } catch (InterruptedException ie) {
                        }
                    } catch (InterruptedException ie) {
                    }
                }
            }
        } catch (IOException ioe) {
        }
    }

    //get recieved string from server and decide the type and what to do
    public void handleData(String s) {
        //DeviceHandlingServer.getInstance().saveLog(s);
        //if it is a LocationBox
        //DeviceHandlingServer.getInstance().saveLocation(null);
        DeviceHandlingServer.getInstance().executeLocation(createLocation(s));

    }

    //decode the incoming string into the parametes which needed to make a LocationBox
    public LocationBox createLocation(String s) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -5);
        return new LocationBox("12345", new Date(cal.getTime().getTime()),
                new Date(Calendar.getInstance().getTime().getTime()),
                5.5042f, 79.8875f, 102.34f, 200.4f);
    }

//    public LocationBox parse(String data) {
//	float speed = 0, direction = 0;
//
//	String[] arr = data.split(",");
//	float latitude = convert(Float.parseFloat(arr[7]) / 100);
//	float longitude = convert(Float.parseFloat(arr[9]) / 100);
//	if (!arr[11].isEmpty()) {
//	    speed = Float.parseFloat(arr[11]);
//	}
//	if (!arr[12].isEmpty()) {
//	    direction = Float.parseFloat(arr[12]);
//	}
//
//	int year = 2000 + Integer.parseInt(arr[2].substring(0, 2));
//	int month = Integer.parseInt(arr[2].substring(2, 4)) - 1;
//	int dayOfMonth = Integer.parseInt(arr[2].substring(4, 6));
//	int hour = Integer.parseInt(arr[2].substring(6, 8));
//	int minute = Integer.parseInt(arr[2].substring(8, 10));
//	int second = Integer.parseInt(arr[5].substring(4, 6));
//
//	Calendar date = Calendar.getInstance();
//	date.set(Calendar.YEAR, year);
//	date.set(Calendar.MONTH, month);
//	date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//	date.set(Calendar.HOUR_OF_DAY, hour);
//	date.set(Calendar.MINUTE, minute);
//	date.set(Calendar.SECOND, second);
//
//	int delay = 0;
//	try {
//	    delay = dbms.getDelay(latitude, longitude, date.getTimeInMillis());
//	} catch (SQLException e) {
//	    logger.log(Level.SEVERE, e.getMessage());
//	    e.printStackTrace();
//	}
//
//	return new LocationBox(Long.parseLong(arr[0].split(":")[1]),
//		date.getTime(), latitude, longitude, speed, direction, delay);
//    }
//
//    private float convert(float longd) {
//	int longi = (int) longd;
//	float longf = (longd - longi) * 100 / 60;
//	float longitude = longi + longf;
//	return longitude;
//    }
}
