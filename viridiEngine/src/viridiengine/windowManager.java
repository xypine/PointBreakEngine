/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
    Timer timer = new Timer(15, this);
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    Renderer lM = new Renderer();
    JLabel area;
    
    //Input:
    Input input = new Input();
    
    //GAMEOBJECTS:
//    Player p1;
    objectManager oM = new objectManager();
    LinkedList<gameObject> players;
    //VARIABLES FOR TICK:
    int tx;
    int ty;
    String ta;
    private float txf;
    private float tyf;
//    
    @Override
    public void run() {
        
        
        timer.setRepeats(true);
        timer.start();
        
        int yd = 25;
        int xd = 46;
        this.setTitle("Viridi Engine");
        this.setSize(750, 500);
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
        area.setFont(new Font("monospaced", Font.PLAIN, 12));
        area.setForeground(Color.white);
        area.setBackground(Color.black);
        this.add(area);
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        //SUMMON TEST
        
        oM.addObject(new Player(0, 0, "player1", "█", 1F, Color.black));
//        p1 = oM.getPlayer("player1");
        oM.addObject(new Player(5, 0, "player2", "█", 1F, Color.black));
        
        oM.addObject(new Player(5, 5, "nuul", "█", 1F, Color.black));
        
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
        System.out.println(oM.getObjects().size());
        //UPDATE ARRAY
        players = oM.getObjects();
        lM.fill("█", Color.WHITE);
        for(gameObject p : players){
//            lM.change(tx, ty, "█", Color.WHITE);
            
            p.checkInput(input);
            this.txf = p.getX();
            this.tyf = p.getY();
            this.tx = (int) p.getX();
            this.ty = (int) p.getY();
            ta = p.gAppearance();
            Color tc = p.getColor();
            p.update(lM);
            if(p.getTag() == "player1"){
                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.MAGENTA));
            }
            if(p.getTag() == "player2"){
                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.CYAN));
            }
//            System.out.println((p.getVX() + 1F) * (p.getVY()+1F));
            if(p.getTag() == "null" && p.hits > 7){
                oM.removeObject(p);
            }
        
//          System.out.println("pelaaja: x:" + tx + " y:" + ty);
            lM.change(tx, ty, ta, tc);
            //RENDER
//            lM.fill(Integer.toString(number));
        }
        
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
