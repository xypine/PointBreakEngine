/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package PBEngine;

import PBEngine.gameObjects.Player;
import PBEngine.gameObjects.gameObject;
import JFUtils.Input;
import JFUtils.point.Point2D;
import JFUtils.quickTools;
import JFUtils.graphing.Graph;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Jonnelafin
 */
public class Supervisor extends JFUtils.InputActivated implements Runnable{
    public Input customInput = null; //Use only if not null
    
    
    public Graph grapher;
    
    private long delta = 0;
    private final Object deltaLock = new Object();
    public boolean noWindows;
    
    public void setDelta(long x) {
        synchronized (deltaLock) {
            this.delta = x;
        }
    }
    public long getDelta() {
        synchronized (deltaLock) {
            long temp = delta;
            //message = null;
            return temp;
        }
    }
    
    public boolean stopAll(){
        try {
            Thread.currentThread().join();
            return true;
        } catch (InterruptedException ex) {
            return false;
        }
    }
    
    public int loadingsteps = 4;
    public int loading_completed = 0;
    
    public quickTools tools = new quickTools();
    
    //Global variables
    public double world_friction_multiplier = 0.8;
    public Point2D engine_gravity = new Point2D(0D, 0.1D);
    public boolean engine_collisions = true;
    public boolean disableVSRAD = false;
    
    public boolean bakedLights = false;
    
    public Engine Logic;
    public LegacyEditor ea;
    //radiosity rad;
    public VSRadManager rad;
    public boolean tog;
    public Supervisor ref = this;
    public int size = 1;
    public int xd = 50 * size;
    public int yd = 50 * size;
    public Thread c;
    public Thread b;
    public Thread a;
    public objectManager objectManager = new objectManager(this);
    public devkit kit;
    int mode;
    public volatile boolean ready = false;
    
    LinkedList<option> Options = new LinkedList<>();
    
    public boolean readFeatures = true;
    /*public Supervisor(int mode, boolean bakedLights, dVector gravity){
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
    ea = new LegacyEditor(ref);
    
    
    
    
    
    // Print some output: goes to your special stream
    //        System.out.println("Foofoofoo!");
    // Put things back
    //        System.out.flush();
    //        System.setOut(old);
    // Show what happened
    //        System.out.println("Here: " + baos.toString());
    
    
    }*/
    @SuppressWarnings("unchecked")
    public Supervisor(int mode, boolean bakedLights, Point2D gravity, int targetSpeed, Point2D size, HashMap<String, String>... param){
        this.xd = (int) size.x;
        this.yd = (int) size.y;
        SupervisorConst(mode, bakedLights, gravity, targetSpeed, param);
    }
    @SuppressWarnings("unchecked")
    public Supervisor(int mode, boolean bakedLights, Point2D gravity, int targetSpeed, HashMap<String, String>... param){
        SupervisorConst(mode, bakedLights, gravity, targetSpeed, param);
    }
    @SuppressWarnings("unchecked")
    public Supervisor(int mode, boolean bakedLights, Point2D gravity, HashMap<String, String>... param){
        SupervisorConst(mode, bakedLights, gravity, 15, param);
    }
    @SuppressWarnings("unchecked")
    public Supervisor(int mode, boolean bakedLights, Point2D gravity, int targetSpeed){
        SupervisorConst(mode, bakedLights, gravity, targetSpeed);
    }
    @SuppressWarnings("unchecked")
    public Supervisor(int mode, boolean bakedLights, Point2D gravity){
        SupervisorConst(mode, bakedLights, gravity, 15);
    }
    
    public static void setUIFont (java.awt.Font f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof java.awt.Font)
            UIManager.put (key, f);
          }
    }
    
    /**
     * Set the font size for all native UI elements
     * @param size font size to use
     */
    public void setFontSize(int size){
        if(font == null){
            throw new IllegalStateException("Font is not loaded properly: is null");
        }
        font = new Font(font.getName(), Font.PLAIN,size);
        setUIFont(font);
    }
    private Font font = null;
    @SuppressWarnings("unchecked")
    private void SupervisorConst(int mode, boolean bakedLights, Point2D gravity, int targetSpeed, HashMap<String, String>... param){
        versionCheck.check.doChecks();
        
        //Apply basic fonts
        File fontfile = null;
        try {
            fontfile = new File(new directory().fonts + "Roboto-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontfile);
            
            font = new Font(font.getName(), Font.PLAIN,12);
            
            //UIManager.put("Label.font", font);
            setUIFont(font);
        } catch (MalformedURLException ex) {
            quickTools.alert("COULD NOT DOWNLOAD FONTS", "font download failed: " + ex);
        } catch (IOException | FontFormatException ex) {
            quickTools.alert("Loading fonts failed", "loading fonts failed: " + ex);
        }
        
        
        
        this.mode = mode;
        this.bakedLights = bakedLights;
        this.engine_gravity = gravity;
        //read config
        Options.add(new option("gravity", engine_gravity));
        Options.add(new option("sizex", xd));
        Options.add(new option("sizey", yd));
        for(String ar : LevelLoader.readConfig("config.txt")){
            for(option x : Options){
                if(x.name.equals(ar.split(" ")[0])){
                    x.link = ar.split(" ")[1];
                }
            }
        }
        
        HashMap<String, String> paramMap = new HashMap<>();
        
        if(param.length > 0){
            paramMap = param[0];
            if(paramMap.containsKey("loadLevel")){
                this.defaultMap = paramMap.get("loadLevel");
            }
            if(paramMap.containsKey("noplayer")){
                noplayer = true;
            }
            if(paramMap.containsKey("nodefaultlight")){
                nodefaultlight = true;
            }
            if(paramMap.containsKey("nowindows")){
                noWindows = true;
            }
        }
        
        rad = new VSRadManager(xd, yd, objectManager, ref);
        Logic = new Engine(ref , objectManager, xd, yd, rad, engine_gravity, defaultMap, targetSpeed);
        if(noWindows){
            Logic.noWindows = true;
        }
        
        
        if(customInput != null){
            Logic.input = customInput;
        }
        
        //ea = new LegacyEditor(ref);
        
        
        
        
        //LEFT HERE -> maploading?
        
        
        
        // Print some output: goes to your special stream
//        System.out.println("Foofoofoo!");
        // Put things back
//        System.out.flush();
//        System.setOut(old);
        // Show what happened
//        System.out.println("Here: " + baos.toString());
        
        
    }
    private boolean noplayer = false;
    public String defaultMap = "null.pblevel";
    
    public void tick_pre(){
        
    }
    public void tick_late(){
        
    }
    public void tick_first(){
        
    }
    public void tog(){
        if(tog){
        //    Logic.record();
        }
        Logic.window.setVisible(true);
        Logic.running = true;
//        rad.setVisible(tog);
//        rad.running = tog;
        //ea.setVisible(!tog);
        //ea.running = !tog;
        
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
    public int timerType = -1;
    public String features_confFile = "features.txt";
    public boolean features_overrideAllStandard = false;
    public config.configReader features_customReader = null;
    
    private boolean nodefaultlight = false;
    
    @Override
    public void run() {
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use our special stream
        System.setOut(ps);
        
        
        //ED = new EffectsDemo(ref , objectManager, 50, 25, rad, engine_gravity);
        b  = new Thread(){
            @Override
            public void run(){
                //SwingUtilities.invokeLater(ea);
                //ea.setVisible(false);
                //ea.running = false;
            }
        };
        System.out.println("PointBreakEngine by Elias Eskelinen alias Jonnelafin");
        System.out.println("Starting PointBreakEngine on " + System.getProperty("os.name") + "...");
        
        b.start();
        
        loading_completed++;
        
        if (!noWindows) {
                        kit = new devkit(ref);
                    }
        
        a = new Thread(){
                @Override
                public void run(){
        
        
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(ED);
//        SwingUtilities.invokeLater(Logic);
        if(timerType != -1){
            Logic.timerType = timerType;
        }
        Logic.run();
           //Thread a = new Thread(Logic, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        //rad.running = true;
        Logic.Vrenderer.sSi = true;
        
        Logic.running = true;
        
        loading_completed++;
        
        
        
        
        if(mode == 3){
            try {
                LinkedList<gameObject> nulx = Logic.loadLevel(defaultMap, "");
            } catch (URISyntaxException ex) {
                Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!noplayer) {
//wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1, ref));
                gameObject p = new Player(25, 5, 1, "player1", "█", 1F, Color.black, 1, ref);
                gameObject torso = new gameObject(25, 5, 1, "player1_torso", "T", 1F, Color.red, 2, ref);
                gameObject torso2 = new gameObject(25, 5, 1, "player1_torso2", "T", 1F, Color.red, 3, ref);
                //gameObject torso3 = new gameObject(25, 5, 1, "player1_torso2", "T", 1F, Color.red, 3, ref);
                p.addChild(torso);
                torso.addChild(torso2);
                torso2.setParent(torso);
                
                torso.setParent(p);
                torso.tag.add("nocoll");
                torso.collisions = false;
                torso2.tag.add("nocoll");
                torso2.collisions = false;
                torso.visible = false;
                torso2.visible = false;
                
                Camera cam = new Camera(p.x, p.y, p);
                Logic.cam = cam;
                
                Logic.oM.addObject(torso2);
                Logic.oM.addObject(torso);
                Logic.oM.addObject(p);
            }
            //wM.oM.addObject(new Player(5, 6, "player1", "█", 1F, Color.black, 3, ref));
            //wM.oM.addObject(new Player(6, 6, "player1", "█", 1F, Color.black, 4, ref));
            //rad.setTitle("VSRad");
            if(!bakedLights && !nodefaultlight){
                Logic.rads.add(20, 20, 20, new Color(1, 1, 1), 1, true);
                //Logic.rads.add(50, 2, 0, new Color(0, 1, 1), 1, true);
                //Logic.rads.add(49, 10, 2, new Color(1, 1, 1), 1, false);
                //Logic.rads.add(39, 20, 1, new Color(1, 0, 0), 1, false);
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
        Logic.Vrenderer.sSi = false;
                }
        };
        Thread graph;
        graph = new Thread(){
            @Override
            public void run(){
                grapher = new Graph();
                while(true){
                    try {
                        double d = delta/10;
                        grapher.update(d, Logic.tickC);
                    } catch (Exception e) {
                    }
                }
            }
        };
        a.start();
        if (!noWindows) {
            graph.start();
        }
        
        System.out.println("////////////////");
        System.out.println("done initializing");
        String oldo = "";
        loading_completed++;
        System.out.println("Steps completed: " + loading_completed);
        while(!Logic.ready){
            
        }
        
        //Load features.txt config
        if(readFeatures){
            try {
                if(features_customReader == null){
                    config.configReader.load(new directory().config + features_confFile, ref);
                }
                else{
                    Logger.getGlobal().warning("Using a custom config reader, errors may occur...");
                    config.configReader cnf = features_customReader;
                    cnf.load(new directory().config + features_confFile, ref);
                }
            } catch (Exception e) {
                quickTools.alert("unable to read config file", "unable to read config file! Does it exist?");
            }
        }
        
        ready = true;
        //PBEngine.Rendering.MapTest mapTest = new MapTest(ref);
        //mapTest.setVisible(true);
        while(true){
            String newo = baos.toString();
            if(!oldo.equals(newo)){
                JScrollBar vertical = null;
                if (!noWindows) {
                    vertical = kit.logs.getVerticalScrollBar();

                    try {
                        vertical.setValue(vertical.getMaximum());
                    } catch (Exception e) {
                    }
                }
                String diff = difference(oldo, newo);
                //System.setOut(old);
                old.print(diff);
                latestConsole = diff;
                kit.log.setText(newo);
                
                //System.setOut(ps);
                //System.out.flush();
                

                oldo = newo;
                vertical.setValue( vertical.getMaximum() );
                
            }
            
            try {
                TimeUnit.SECONDS.sleep((long) 0.1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public String latestConsole = "";

    public void updateSize(Point2D d) {
        int x = (int) d.x;
        int y = (int) d.y;
        this.xd = x;
        this.yd = y;
        this.Logic.xd = x;
        this.Logic.yd = y;
        this.rad.w = x;
        this.rad.h = y;
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

