/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.NumericShaper.Range;
import java.beans.EventHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Jonnelafin
 */
public class radiosity extends JFrame implements Runnable, ActionListener {
    private kick k;
    colorParser cP = new colorParser();
    Timer timer = new Timer(1, this);
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    Renderer lM = new Renderer();
    JLabel area;
    
    double lastTime;
    
    
    //GAMEOBJECTS:
//    Player p1;
    int co = 0;
    objectManager oM = new objectManager();
    LinkedList<gameObject> objects;
    //VARIABLES FOR TICKS:
    int tx;
    int ty;
    String ta;
    private float txf;
    private float tyf;
//    
    public float[][] radiosity;
    private Input input;
    public radiosity(kick ki){
        
    }
    //Input:
    levelLoader aol;
    float[][] grid;
    
    VSRadManager rads;
    @Override
    public void run() {

        
        timer.setRepeats(false);
        timer.start();
        
        //Initiate screen size
        float size = 1F;
        float w = 767*size;
        float h = 562*size;
        float aspect = w / h;
        System.out.println(aspect);
        
        int xd = 50;
        int yd = 25;
        //int xd = (int) (w / 15.34);
        //int yd = (int) (h / 22.48);
        this.setTitle("ViridiEngine rad");
        this.setSize(round(w), round(h));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        area = new JLabel(screen);
        float Daspect = xd / yd;
        System.out.println(Daspect);
        float fontSize = (float) (7*Daspect);
        area.setFont(new Font("monospaced", Font.PLAIN, (int) fontSize));
        //area.setFont(new Font("courier_new", Font.PLAIN, (int) fontSize));
        
        //final Font currFont = area.getFont();
        //area.setFont(new Font("test", currFont.getStyle(), currFont.getSize()));
//        area.setFont("MONOSPACED");
        area.setForeground(Color.white);
        area.setBackground(Color.black);
        
        this.add(area);
        
        System.out.println("Initializing VSRad...");
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        synchronized(lM) {
            lM.init(xd, yd, this);
        }
        tmp = lM.gets();
        
        screen = "";
        rads = new VSRadManager(yd, yd, oM);
        oM.addObject(new gameObject(9, 17, "static", "a", 1, Color.red, 71));
        oM.addObject(new gameObject(9, 16, "static", "a", 1, Color.red, 72));
        oM.addObject(new gameObject(10, 16, "static", "a", 1, Color.red, 73));
        //rad.calculate(new dVector(10, 10), 4);
        //rads.add(7, 15, 7);
        //rads.add(10,20, 70);
        //rads.add(49,49, 45);
        //rads.add(45,5, 10);
        //rads.add(0,0, 10);
        //rad.calculate(new dVector(34, 4), 200);
        //rad.calculate(new dVector(15, 8), 4);
        //grid = rad.out();
        
//        area.setEditable(false);
        System.out.println("Done initializing VSRad!");
        
        
        //SUMMON TEST
        
        aol = new levelLoader("/src/com/viridistudios/viridiengine/levels/out.txt", oM);
        
        
//        p1 = oM.getPlayer("player1");
//        oM.addObject(new Player(5, 0, "player2", "█", 1F, Color.black, 2));
//        oM.addObject(new gameObject(14, 15, "static", "█", 1F, Color.DARK_GRAY, 90));
//        oM.addObject(new gameObject(15, 15, "static", "█", 1F, Color.LIGHT_GRAY, 91));

//        oM.addObject(new gameObject(16, 15, "static", "T", 1F, Color.DARK_GRAY, 92));

//       oM.addObject(new gameObject(16, 15, "static", "T", 1F, Color.DARK_GRAY, 92));

//        oM.addObject(new gameObject(17, 15, "static", "E", 1F, Color.DARK_GRAY, 93));
//        oM.addObject(new gameObject(18, 15, "static", "S", 1F, Color.DARK_GRAY, 94));
//        oM.addObject(new gameObject(19, 15, "static", "T", 1F, Color.DARK_GRAY, 95));
//        oM.addObject(new gameObject(20, 15, "static", "I", 1F, Color.DARK_GRAY, 96));
//        oM.addObject(new gameObject(21, 15, "static", "█", 1F, Color.LIGHT_GRAY, 97));
//        oM.addObject(new gameObject(22, 15, "static", "█", 1F, Color.DARK_GRAY, 98));
        
//        for(int i : Range.between(1, 3);)
//        oM.addObject(new Player(5, 5, "nuul", "█", 1F, Color.black));
        System.out.println("Starting ticks...");
    }
    
    public boolean running = false;
    @Override
    
    public void actionPerformed(ActionEvent e) {
        

        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
//        System.out.print("another [" + this.number + "] tick passed! (");
//        System.out.println(tickC + ")");
        
        //Do updates
//        float w = this.getWidth();
//        float h = this.getHeight();
//        float aspect = w / h;
//        float fontSize = (float) (aspect * 10.99087420387603);
//        area.setFont(new Font("monospaced", Font.PLAIN, (int) fontSize));
        
        if(running == true){
            tick();
        }
        
//        System.out.println(this.getWidth() + ", " + this.getHeight());
        tickC++;
    }
    boolean saved = false;
    void tick(){
        //rad.fill(0);
        //rad.calculate(new dVector(12, 10), 4);
        //rad.calculate(new dVector(2, number + 2), 2);
        //this.setTitle("ViridiEngine radiosity "+rad.s);
        rads.add(10,20, 70);
        
        int xp = 0, yp = 0;
        for(float[] x : rads.read()){
            for(float y : x){
                double i = y * 1.2F;
                if(i > 255){
                    i = 255;
                }
                lM.change(xp, yp,"0",new Color((int) i,(int) i,(int) i), "n");
                //lM.change(xp, yp, "0", Color.red, "i");
                yp++;
            }
            xp++;
            yp = 0;
        }
        
        //Render
        
        area.setText(fetch(lM));
        running = false;
        System.out.println("render succeeded");
    }
    String fetch(Renderer render)
    {
//        System.out.println("Started fetching!");
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
//        System.out.println("Done fetching!");
        return(screen);
    }

    
    
}
