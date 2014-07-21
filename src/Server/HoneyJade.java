/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Vimukthi Weerasiri
 */
public class HoneyJade {

    public static void main(String[] args) {
        new HoneyJade();
    }

    private HoneyJade(){
        startawesomeness();
    }

    private void startawesomeness() {
//        TK103DeviceHandler.getInstance().executeServer();
         TK103DeviceHandler.getInstance().handleData("");
    }

}
