package Server;

public class HoneyJade {

    public static void main(String[] args) {
        new HoneyJade();
    }

    private HoneyJade(){
        startawesomeness();
    }

    private void startawesomeness() {
        //skipping server for now
        //TK103DeviceHandler.getInstance().executeServer();
         TK103DeviceHandler.getInstance().handleData("");
    }

}
