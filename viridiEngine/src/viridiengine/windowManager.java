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
    Renderer lM = new Renderer();
    JTextArea area;
    
    //GAMEOBJECTS:
    Player go = new Player();
    
    //VARIABLES FOR TICK:
    int tx;
    int ty;
    String ta;
//    
    @Override
    public void run() {
        timer.setRepeats(true);
        timer.start();
        int yd = 25;
        int xd = 43;
        this.setTitle("Viridi Engine");
        this.getContentPane().setSize(yd * 100, xd* 100);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        lM.init(yd, xd);
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
        
        //SUMMON TEST
        
        go.summon(0, 0, "test", " - ");
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
        //UPDATE ARRAY
        lM.change(ty, tx, " . ");
        this.tx = (int) go.getX();
        this.ty = (int) go.getY();
        String ta = go.gAppereance();
        go.update(lM);
        
        lM.change(ty, tx, ta);
        //RENDER
//        lM.fill(Integer.toString(number));
        
        area.setText(fetch(lM));
    }
    String fetch(Renderer render){
        
        
        //RENDER
        tmp = render.gets();
        
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
