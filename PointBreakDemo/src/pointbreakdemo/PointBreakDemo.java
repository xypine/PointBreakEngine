/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointbreakdemo;
import PBEngine.*;
import java.awt.Color;
/**
 *
 * @author elias
 */
public class PointBreakDemo {

    /**
     * @param args the command line arguments
     */
    static Main pbengine = new Main();
    public static void main(String[] args) {
        // TODO code application logic here
        String[] argss = new String[1];
        argss[0] = "";
        pbengine.main(argss);
        kick k = pbengine.k;
        k.bakedLights = true;
        k.engine_gravity = new PBEngine.dVector(0D,0D);
        gameObject p = new Player(25, 5, 1, "player1", "█", 1F, Color.black, 1, k);
        k.objectManager.addObject(p);
    }
    public void go(){
        
    }
}
class starter{
    public starter(){
        
    }
}