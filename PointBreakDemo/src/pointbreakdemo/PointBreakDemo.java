/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointbreakdemo;
import PBEngine.*;
import static PBEngine.Main.k;
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
        new game();
        
    }
    public void go(){
        
    }
}
class game{
    public game(){
        String[] argss = new String[2];
        argss[0] = "nodemo";
        argss[1] = "calclights";
        k = new kick(0, false, new dVector(0, 0));
        Thread A = new Thread(k);
        A.start();
        while(!k.ready){
            
        }
        System.out.println("WOOOOOOOOOOOOOOOOOOOOOOOO");
//        try {
//            k.Logic.loadLevel("out.txt");
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(game.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        k.rad.add(0, 0, 8, new Color(1,1,1), 0, false);
//       k.rad.recalculate();
        System.out.println("wo");
        gameObject p = new Player(25, 5, 1, "player1", "█", 1F, Color.black, 1, k);
        k.objectManager.addObject(p);
        System.out.println(k.objectManager.getObjects());
    }
}