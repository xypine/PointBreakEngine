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
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jonnelafin
 */
public class kick implements Runnable{
    
    public int loadingsteps = 4;
    public int loading_completed = 0;
    
    public quickEffects tools = new quickEffects();
    
    //Global variables
    public dVector engine_gravity = new dVector(0D, 0.1D);
    public boolean engine_collisions = true;
    
    public boolean bakedLights = false;
    
    public Engine Logic;
    public Editor ea;
    //radiosity rad;
    public VSRadManager rad;
    public boolean tog;
    public kick ref = this;
    public int xd = 50;
    public int yd = 25;
    public Thread c;
    public Thread b;
    public Thread a;
    public objectManager objectManager = new objectManager(this);
    public devkit kit;
    int mode;
    public volatile boolean ready = false;
    
    LinkedList<option> Options = new LinkedList<>();
    public kick(int mode, boolean bakedLights, dVector gravity){
        this.mode = mode;
        this.bakedLights = bakedLights;
        this.engine_gravity = gravity;
        //read config
        Options.add(new option("gravity", engine_gravity));
        Options.add(new option("sizex", xd));
        Options.add(new option("sizey", yd));
        for(String ar : FileLoader.readConfig("config.txt")){
            for(option x : Options){
                if(x.name.equals(ar.split(" ")[0])){
                    x.link = ar.split(" ")[1];
                }
            }
        }
        rad = new VSRadManager(xd, yd, objectManager, ref);
        Logic = new Engine(ref , objectManager, xd, yd, rad, engine_gravity);
        ea = new Editor(ref);
        
        
        
        
        
        // Print some output: goes to your special stream
//        System.out.println("Foofoofoo!");
        // Put things back
//        System.out.flush();
//        System.setOut(old);
        // Show what happened
//        System.out.println("Here: " + baos.toString());
        
        
    }
    public void tog(){
        if(tog){
            Logic.record();
        }
        Logic.setVisible(tog);
        Logic.running = tog;
//        rad.setVisible(tog);
//        rad.running = tog;
        ea.setVisible(!tog);
        ea.running = !tog;
        
        tog = !tog;
    }
    public void stop(){
        Logic.running = false;
        ea.running = false;
    }
    public void continu(){
        Logic.running = tog;
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

    @Override
    public void run() {
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        kit = new devkit(ref);
        
        //ED = new EffectsDemo(ref , objectManager, 50, 25, rad, engine_gravity);
        b  = new Thread(){
            @Override
            public void run(){
                SwingUtilities.invokeLater(ea);
                ea.setVisible(false);
            }
        };
        System.out.println("PointBreakEngine by Elias Eskelinen alias Jonnelafin");
        System.out.println("Starting PointBreakEngine on " + System.getProperty("os.name") + "...");
        
        b.start();
        
        loading_completed++;
        
        a = new Thread(){
                @Override
                public void run(){
        
        
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(ED);
//        SwingUtilities.invokeLater(Logic);
        Logic.run();
           //Thread a = new Thread(Logic, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        //rad.running = true;
        Logic.vA.sSi = true;
        
        Logic.running = true;
        
        loading_completed++;
        
        if(mode == 3){
            try {
                Logic.loadLevel("out.txt");
            } catch (URISyntaxException ex) {
                Logger.getLogger(kick.class.getName()).log(Level.SEVERE, null, ex);
            }
            //wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1, ref));
            gameObject p = new Player(25, 5, 1, "player1", "█", 1F, Color.black, 1, ref);
            
            Logic.oM.addObject(p);
            //wM.oM.addObject(new Player(5, 6, "player1", "█", 1F, Color.black, 3, ref));
            //wM.oM.addObject(new Player(6, 6, "player1", "█", 1F, Color.black, 4, ref));
            //rad.setTitle("VSRad");
            if(!bakedLights){
                Logic.rads.add(7, 20, 1, new Color(2, 2, 2), 1, true);
                Logic.rads.add(49, 10, 2, new Color(1, 1, 1), 1, false);
                Logic.rads.add(39, 20, 1, new Color(1, 0, 0), 1, false);
                Logic.red = Logic.rads.read(999999);
                System.out.println("VSRAD COMPLETE");
            }
            //
            //wM.rads.add(25, 1, 1, new Color(1, 1, 1), 1);
        }
        loading_completed++;
        //wM.running = true;
        System.out.println("THREAD 'A' INITIATED");
        System.out.println("Steps completed: " + loading_completed);
        Logic.vA.sSi = false;
                }
        };
        a.start();
        
        System.out.println("////////////////");
        System.out.println("done initializing");
        String oldo = "";
        loading_completed++;
        System.out.println("Steps completed: " + loading_completed);
        while(!Logic.timer.isRunning()){
            
        }
        ready = true;
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
            try {
                TimeUnit.SECONDS.sleep((long) 0.1);
            } catch (InterruptedException ex) {
                Logger.getLogger(kick.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    class option{
        public String name;
        public Object link;
        public option(String name, Object link){
            this.name = name;
            this.link = link;
        }
    }
}

