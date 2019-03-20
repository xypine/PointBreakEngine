/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class windowManager extends JFrame implements Runnable, ActionListener {
    Timer ajastin = new Timer(100, this);
    ajastin.setRepeats(true);
    ajastin.start();
    @Override
    public void run() {
        int yd = 800;
        int xd = 800;
        this.setTitle("Viridi Engine");
        this.setSize(yd, xd);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        logicManager lM = new logicManager();
        lM.init(yd * 10 / 175, xd * 100 / 421);
        String[][] tmp = lM.gets();
        
        String screen = "";
        
        for (String[] x : tmp)
        {  
            for (String y : x)
            {
//                System.out.print(y);
                screen = screen + y;
            }
//            System.out.println();
            screen = screen + "\n";
        }
        
        
        JTextArea area = new JTextArea(screen);
        area.setEditable(false);
        this.add(area);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
