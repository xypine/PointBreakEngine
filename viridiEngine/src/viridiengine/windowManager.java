/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
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
    Timer timer = new Timer(60, this);
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    logicManager lM = new logicManager();
    JTextArea area;
//    
    @Override
    public void run() {
        timer.setRepeats(true);
        timer.start();
        int yd = 800;
        int xd = 800;
        this.setTitle("Viridi Engine");
        this.setSize(yd, xd);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        lM.init(yd * 10 / 175, xd * 100 / 421);
        tmp = lM.gets();
        
        screen = "";
        
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
        
        
        area = new JTextArea(screen);
        area.setEditable(false);
        area.setForeground(Color.white);
        area.setBackground(Color.black);
        this.add(area);
        
        this.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
        System.out.print("another [" + this.number + "] tick passed! (");
        System.out.println(tickC + ")");
        
        //Do updates
        tick();
        
        
        tickC++;
    }
    void tick(){
        lM.fill(Integer.toString(number));
        area.setText(fetch(lM));
    }
    String fetch(logicManager logic){
        tmp = logic.gets();
        
        screen = "";
        
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
        return(screen);
    }
    
}
