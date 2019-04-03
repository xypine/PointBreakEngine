/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class windowManager extends JFrame implements Runnable, ActionListener {
    colorParser cP = new colorParser();
    Timer timer = new Timer(60, this);
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    Renderer lM = new Renderer();
    JLabel area;
    
    //Input:
    Input input = new Input();
    
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
        int xd = 46;
        this.setTitle("Viridi Engine");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        lM.init(xd, yd);
        tmp = lM.gets();
        
        screen = "";
        
        for (String[] y : tmp)
        {  
            for (String x : y)
            {
//                System.out.print(y);
                screen = screen + y;
            }
//            System.out.println();
            screen = screen + "\n";
        }
        
        
        area = new JLabel(screen);
//        area.setEditable(false);
        area.setForeground(Color.white);
        area.setBackground(Color.black);
        this.add(area);
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        //SUMMON TEST
        
        go.summon(0, 0, "test", "-");
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
//        System.out.print("another [" + this.number + "] tick passed! (");
//        System.out.println(tickC + ")");
        
        //Do updates
        tick();
        
        
        tickC++;
    }
    void tick(){
        //UPDATE ARRAY
        lM.change(tx, ty, " . ", Color.cyan);
        go.checkInput(input);
        this.tx = (int) go.getX();
        this.ty = (int) go.getY();
        String ta = go.gAppearance();
        go.update(lM);
        
//        System.out.println("pelaaja: x:" + tx + " y:" + ty);
        lM.change(tx, ty, ta, Color.RED);
        //RENDER
//        lM.fill(Integer.toString(number));
        
        area.setText(fetch(lM));
    }
    String fetch(Renderer render)
    {
        int cx = 0;
        int cy = 0;
        
        //RENDER
        tmp = render.gets();
        Color[][] colors = render.getc();
        
        screen = "<html>";
        
        for (String[] y : tmp)
        {  
            
            screen = screen + "<p>";
            for (String x : y)
            {
                
//                System.out.print(y);
                System.out.println(x);
                try
                {
                    
                    screen += x;
                }
                catch(Exception e)
                {
                    System.out.println("failed to fetch screen at: " + cx + " " + cy);
                }
            }
            cx++;
        }
        screen = screen + "</p>";
//            System.out.println();
        cy++;
            
        
        screen = screen + "</html>";
//        System.out.println(colors);
        return(screen);
    }

    
    
}
