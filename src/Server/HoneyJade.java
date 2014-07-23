package Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HoneyJade {

    public static void main(String[] args) {
        new HoneyJade();
    }

    private HoneyJade() {
        startawesomeness();
    }

    private void startawesomeness() {
        try {
            //skipping server for now
            //TK103DeviceHandler.getInstance().executeServer();
            TK103DeviceHandler.getInstance().handleData("");
        } catch (InterruptedException ex) {
            Logger.getLogger(HoneyJade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
