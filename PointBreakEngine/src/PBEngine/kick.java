/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jonnelafin
 */
public class kick {
    
    
    quickEffects tools = new quickEffects();
    
    //Global variables
    public dVector engine_gravity = new dVector(0D, 0.1D);
    public boolean engine_collisions = true;
    
    boolean bakedLights = false;
    
    Engine wM;
    Editor ea;
    //radiosity rad;
    VSRadManager rad;
    boolean tog;
    kick ref = this;
    int xd = 50;
    int yd = 25;
        EffectsDemo ED;
    Thread c;
    Thread b;
    Thread a;
    objectManager forwM = new objectManager(this);
    devkit kit;
    public kick(int mode){
        //read config
        System.out.println(FileLoader.readConfig("config.txt"));
        
        
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        
        // Print some output: goes to your special stream
//        System.out.println("Foofoofoo!");
        // Put things back
//        System.out.flush();
//        System.setOut(old);
        // Show what happened
//        System.out.println("Here: " + baos.toString());
        
        
        kit = new devkit(ref);
        //ED = new EffectsDemo(ref , forwM, 50, 25, rad, engine_gravity);
        b  = new Thread(){
            @Override
            public void run(){
                ea = new Editor(ref);
                SwingUtilities.invokeLater(ea);
                ea.setVisible(false);
            }
        };
        System.out.println("Starting PointBreakEngine on " + System.getProperty("os.name") + "...");
        System.out.println("initializing");
        System.out.println("////////////////");
        b.start();
        a = new Thread(){
                @Override
                public void run(){
        wM = new Engine(ref , forwM, xd, yd, rad, engine_gravity);
        
        
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(ED);
//        SwingUtilities.invokeLater(wM);
        wM.run();
           //Thread a = new Thread(wM, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        //rad.running = true;
        wM.vA.sSi = true;
        
        wM.running = true;
        
        if(mode == 3){
            try {
                wM.loadLevel("out.txt");
            } catch (URISyntaxException ex) {
                Logger.getLogger(kick.class.getName()).log(Level.SEVERE, null, ex);
            }
            //wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1, ref));
            gameObject p = new Player(25, 5, 1, "player1", "█", 1F, Color.black, 1, ref);
            
            wM.oM.addObject(p);
            //wM.oM.addObject(new Player(5, 6, "player1", "█", 1F, Color.black, 3, ref));
            //wM.oM.addObject(new Player(6, 6, "player1", "█", 1F, Color.black, 4, ref));
            //rad.setTitle("VSRad");
            if(!bakedLights){
                wM.rads.add(7, 20, 100, new Color(2, 2, 2), 1, true);
                wM.rads.add(49, 1, 200, new Color(1, 1, 1), 1, false);
                wM.rads.add(39, 20, 120, new Color(1, 0, 0), 1, false);
                wM.red = wM.rads.read(999999);
                System.out.println("VSRAD COMPLETE");
            }
            //
            //wM.rads.add(25, 1, 1, new Color(1, 1, 1), 1);
        }
        //wM.running = true;
        System.out.println("THREAD 'A' INITIATED");
        wM.vA.sSi = false;
                }
        };
        a.start();
        
        System.out.println("////////////////");
        System.out.println("done initializing");
        String oldo = "";
        while(true){
            String newo = baos.toString();
            if(!oldo.equals(newo)){
                String diff = difference(oldo, newo);
                //System.setOut(old);
                old.print(diff);
                kit.log.setText(newo);
                
                //System.setOut(ps);
                //System.out.flush();
                JScrollBar vertical = kit.logs.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );

                oldo = newo;
            }
            
        }
    }
    public void tog(){
        if(tog){
            wM.record();
        }
        wM.setVisible(tog);
        wM.running = tog;
//        rad.setVisible(tog);
//        rad.running = tog;
        ea.setVisible(!tog);
        ea.running = !tog;
        
        tog = !tog;
    }
    public void stop(){
        wM.running = false;
        ea.running = false;
    }
    public void continu(){
        wM.running = tog;
        ea.running = !tog;
    }
    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == -1) {
            return "";
        }
     return str2.substring(at);
    }
    public static int indexOfDifference(String str1, String str2) {
        if (str1 == str2) {
            return -1;
        }
        if (str1 == null || str2 == null) {
            return 0;
        }
        int i;
        for (i = 0; i < str1.length() && i < str2.length(); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
        }
        if (i < str2.length() || i < str1.length()) {
            return i;
        }
        return -1;
    }
}

