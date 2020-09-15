/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

/**
 *
 * @author saif8
 */
public class StartApplication {

    /**
     * @param args the command line arguments
     */
    private static UserProcessing userProcessing;
    private static Thread trafficThread;
    public static void main(String[] args) {
        userProcessing=new UserProcessing();
        trafficThread = new Thread(userProcessing);
        trafficThread.start();
    }
    
}
