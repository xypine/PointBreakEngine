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

//L_import PBEngine.Rendering.legacy.LegacyRenderer;
import PBEngine.Rendering.core.*;

import PBEngine.Rendering.Renderer;
import JFUtils.Input;
import JFUtils.point.Point2Int;
import JFUtils.Range;
import JFUtils.point.Point2D;
import JFUtils.quickTools;
import PBEngine.vfx.engineWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static java.lang.Math.round;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class Engine implements Runnable, ActionListener {
    public engineWindow window;
    
    public Camera cam = new Camera(0, 0, null);
    long last_time = System.nanoTime();
    int deltatime = 0;
    
    final int TARGET_FPS = 60;
    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
    
    LinkedList<renderContainer> LoadedRays;
    Color[][] bakedcolor;
    
    public LinkedList<renderContainer> bakedRays;
    public boolean raysBaked = false;
    
    public boolean abright = false;
    
    public volatile boolean ready = false;
    public boolean running = true;
    //Screen components
    int blurStrenght = 1;
    public Point2D gravity;
    quickTools effects = new quickTools();
    public LinkedList<Object> content = new LinkedList<>();
    public float global_brightness = 0.55F;
    public int rayDetail = 0;
    public int vector = 1;
    public int renderRays = 0;
    public LinkedList<Point2Int> record;
    colorParser cP = new colorParser();
    Timer timer = new Timer(15, this);
    double[][] red;
    public int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    //Renderer renderer = new LegacyRenderer();
    JLabel area;
    
    public int xd;
    public int yd;
    double lastTime;
    
    //GAMEOBJECTS:
//    Player p1;
    int co = 0;
    public objectManager oM;
    //objectManager oM = new objectManager();
    LinkedList<gameObject> objects;
    //VARIABLES FOR TICKS:
    int tx;
    int ty;
    String ta;
    private double txf;
    private double tyf;
//    
    public Renderer Vrenderer;
    Supervisor k;
    public Input input;
    public int targetSpeed = 15;
    public Engine(Supervisor ki, objectManager o, int xd, int yd, VSRadManager a, Point2D g, String level){
        
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
        this.rads = a;
        this.levelName = level;
    }
    public Engine(Supervisor ki, objectManager o, int xd, int yd, VSRadManager a, Point2D g, String level, int targetSpeed){
        this.targetSpeed = targetSpeed;
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
        this.rads = a;
        this.levelName = level;
    }
    private AudioSource aM;
    VSRadManager rads;
    public final double h = 540D;
    public final double w = 1080D;
    public final double size = 1D;
    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        System.out.println("USING THE INPUT: " + this.input.toString());
        System.out.println("Engine targetspeed IN: "+targetSpeed);
        timer = new Timer(targetSpeed, this);
        System.out.println("Engine targetspeed OUT: "+targetSpeed);
        System.out.println("Initializing engine...");
        ready = false;
        
        
        timer.setRepeats(true);
        
        //Initiate screen size
        
        /*size = 1F;
        w = 1080*size;
        h = 540*size;*/
        double aspect = w / h;
        System.out.println(aspect);
        
        
//        int xd = 50;
//        int yd = 25;
        //xd = (int) (w / 15.34);
        //yd = (int) (h / 22.48);
        //_this.setTitle("PointBreakEngine");
        //_this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        //_this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //_this.requestFocusInWindow();
        //_this.addKeyListener(input);
        //_this.addMouseMotionListener(input);
        //_this.setVisible(true);
        //_getContentPane().setBackground( Color.black );
        
        Vrenderer = new Renderer(k);
        Vrenderer.sSi = true;
        //_this.add(Vrenderer);
        //_revalidate();
        //_repaint();
        window = new engineWindow(input, k, Vrenderer);
        window.clean();
        
        Vrenderer.init(w,h, 3, false);
        //try {Vrenderer.setImage(new directory().textures + "splash.png");}
        //catch (IOException ex) {quickTools.alert(ex.getMessage());}
        
        content.add(Vrenderer);
        if(Vrenderer.sSi){
            Vrenderer.setVisible(true);
        }
        
        
        area = new JLabel(screen);
        float Daspect = xd / yd;
        System.out.println(Daspect);
        float fontSize = (float) (Daspect * 7.5);
        area.setFont(new Font("monospaced", Font.PLAIN, (int) fontSize));
        //area.setFont(new Font("courier_new", Font.PLAIN, (int) fontSize));
        
        //final Font currFont = area.getFont();
        //area.setFont(new Font("test", currFont.getStyle(), currFont.getSize()));
//        area.setFont("MONOSPACED");
        area.setForeground(Color.black);
        area.setBackground(Color.black);
        
        //_this.add(area);
        content.add(area);
        //fresh();
        
        
        //synchronized(renderer) {
            //renderer.init(xd, yd, this);
        //}
        red = new double[xd][yd];
        //canvas = renderer.canvas;
        
        //rads = new VSRadManager(xd, yd, oM);
        //rads.add(25, 12, 4, new Color(1, 1, 1), 0);
        //rads.add(24, 24, 4, new Color(1, 0, 0), 1);
        //rads.add(25, 12, 4, new Color(1, 1, 1), 0);
        //rads.add(12, 1, 1, new Color(0, 0, 10));
        if(!k.disableVSRAD){red = rads.read(99);}
        screen = "";
        
        if(k.bakedLights){
            System.out.println("Loading baked level lights for the level [" + levelName + "]...");

            try {
                bakedcolor = (Color[][]) new LevelLoader("null", oM, k).readObject(levelName + "_illumination.txt");
                LoadedRays = (LinkedList<renderContainer>) new LevelLoader("null", oM, k).readObject(levelName + "_lights.txt");
            } catch (URISyntaxException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //fresh();
        System.out.println("Done initializing engine!");
        
//        for (String[] y : tmp)
//        {  
//            for (String x : y)
//            {
//                System.out.print(y);
//                screen = screen + y;
//            }
//            System.out.println();
//            screen = screen + "\n";
//        }
        
        
        //area.setText(fetch(renderer));
//        area.setEditable(false);
        
        
        
        
        //SUMMON TEST
        
        //levelLoader lL = new levelLoader("/src/viridiengine/levels/out.pblevel", oM);
        
        
        
        //record = recorder.read("/records/recorded.txt");
        //oM.addObject(new Player(3, 3, "playback", "█", 1F, Color.blue, 11));
        //Add AudioSource
/*        try {
        aM = new AudioSource();
        } catch (LineUnavailableException ex) {
        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        ready = true;
        //_revalidate();
        //_repaint();
        //timerType = 0;
        if (timerType == 0) {
            timer.start();
        } else if(timerType == 1){
            startFullTickTimer();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.timerType != 0){
            System.out.println("Wrong timerType ignored.");
            return;
        }
        generalTick();
        k.tick_pre();
        //fresh();
        long time = System.nanoTime();
        deltatime = (int) ((time - last_time) / 1000000);
        k.setDelta(deltatime);
        last_time = time;
        
        k.kit.time.setText(deltatime + "");
        
        if(input.keyPressed != null){
            if(input.keyPressed.getKeyChar() == 'l' && !raysBaked){
                raysBaked = true;
                System.out.println("saving lights");
                System.out.println("Writing to [" + levelName + "_lights.txt]");
                System.out.println("Writing to [" + levelName + "_illumination.txt]");
                try {
                    LevelLoader lL = new LevelLoader("null", oM, k);
                    lL.writeObject(bakedRays, levelName + "_lights.txt", "");
                    lL.writeObject(colored, levelName + "_illumination.txt", "");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
        if(vector == 0){
            area.setVisible(true);
            Vrenderer.setVisible(false);
        }
        if(vector == 1){
            area.setVisible(false);
            Vrenderer.setVisible(true);
        }
        
        if(running == true){
            tick();
            if((tickC % 1) == 0){
                
            }
            tickC++;
        }
        window.clean();
        //_revalidate();
        //_repaint();
    }
    public int timerType = 1;
    public void startFullTickTimer(){
        Thread a = new Thread() {
            @Override
            public void run(){
                long last_timeF = 99999999;
                long delta = 0;
                while (true) {
                    long time = System.nanoTime();
                    delta = (time - last_timeF) / 1000000;
                    k.setDelta(delta);
                    long catchup = 0;
                    
                    if(delta < targetSpeed){
                        try {
                            catchup = targetSpeed - delta;
                            if(catchup < 0){catchup = 0;}
                            if(catchup > targetSpeed){catchup = targetSpeed;}
                            Thread.sleep(catchup);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    last_timeF = time;
                    fulltick();
                }
            }
        };
        a.start();
    }
    public int mouseX = 0;
    public int mouseY = 0;
    public Point2D mouse_projected = new Point2D(0, 0);
    public void generalTick(){
        mouseX = input.mouseX();
        mouseY = input.mouseY();
        //mouse_projected.x = (mouseX - (window.Vrenderer.getW() / 2)+ cam.x) / window.Vrenderer.factor;
        //mouse_projected.y = (mouseY - (window.Vrenderer.getH() / 2)+ cam.y) / window.Vrenderer.factor;
        //mouse_projected.x = (window.Vrenderer.getW() / 2) - window.Vrenderer.factor / (cam.x - mouseX);
        //mouse_projected.y = (window.Vrenderer.getH() / 2) - window.Vrenderer.factor / (cam.y - mouseY);
        
        //(int) (((rl.x + effectOffSet.x) - cam.x) * factor + (w / 2)), (int) (((rl.y + effectOffSet.y) - camy) * factor + (h / 2)), (int) factor * size, (int) factor * size
        //+(window.Vrenderer.getW()/2)
        //+(window.Vrenderer.getH()/2)
        int w = window.Vrenderer.getW()/2;
        int h = window.Vrenderer.getH()/2;
        mouse_projected.x =( ((mouseX+0)-cam.x-w)*window.Vrenderer.factor) /1000;
        mouse_projected.y =( ((mouseY+0)-cam.y-h)*window.Vrenderer.factor) /1000;
        //System.out.println(mouse_projected);
        //LEFT HERE
        //System.out.println(mouse_projected.represent());
    }
    public void fulltick(){
        if(this.timerType != 1){
            System.out.println("Wrong timerType ignored.");
            return;
        }
        generalTick();
        k.tick_pre();
        //fresh();
        long time = System.nanoTime();
        deltatime = (int) ((time - last_time) / 1000000);
        last_time = time;
        
        k.kit.time.setText(deltatime + "");
        
        if(input.keyPressed != null){
            if(input.keyPressed.getKeyChar() == 'l' && !raysBaked){
                raysBaked = true;
                System.out.println("saving lights");
                System.out.println("Writing to [" + levelName + "_lights.txt]");
                System.out.println("Writing to [" + levelName + "_illumination.txt]");
                try {
                    LevelLoader lL = new LevelLoader("null", oM, k);
                    lL.writeObject(bakedRays, levelName + "_lights.txt", "");
                    lL.writeObject(colored, levelName + "_illumination.txt", "");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
        if(vector == 0){
            area.setVisible(true);
            Vrenderer.setVisible(false);
        }
        if(vector == 1){
            area.setVisible(false);
            Vrenderer.setVisible(true);
        }
        
        if(running == true){
            tick();
            if((tickC % 1) == 0){
                
            }
            tickC++;
        }
        window.clean();
        //_revalidate();
        //_repaint();
    }
    
    private String levelName = "";
    public String getLevelName(){
        return levelName;
    }
    public String setLevelName(){
        throw new SecurityException("not implemented / supported yet");
    }
    
    public String[][] getLevelMap(){
        return this.levelmap;
    }
    public Point2D getCurrentLevelCoord(){
        return currentMap.clone();
    }
    
    
    double mapLoadTime = 0;
    public LinkedList<gameObject> loadLevel(String level) throws URISyntaxException{
        return loadLevel(level, new directory().levels);
    }
    public LinkedList<gameObject> loadLevel(String level, String levelpath) throws URISyntaxException{
        LinkedList<gameObject> out = new LinkedList<>();
        long time = System.nanoTime();
        LevelLoader lL = new LevelLoader(level, oM, k, levelpath);
        while(!lL.done){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for(gameObject x : lL.level){
            x.tag.set(x.tag.indexOf("static"), "newlevel");
            oM.addObject(x);
        }
        out = oM.removeLevel();
        for(gameObject x : lL.level){
            x.tag.set(x.tag.indexOf("newlevel"), "static");
            x.tag.add("delete_lc");
        }
        last_time = System.nanoTime() - time;
        last_time = last_time / 1000000;
        mapLoadTime = last_time;
        System.out.println("Load lenght: " + mapLoadTime);
        System.out.println("Recalculating lights:");
        //k.rad.recalculate("ignoreRecalculation", 1);
        //k.rad.recalculateParent();
        return out;
    }
    public LinkedList<gameObject> loadLevel(String level, String filepath, Color wall, Color light) throws URISyntaxException{
        LinkedList<gameObject> out = new LinkedList<>();
        long time = System.nanoTime();
        LevelLoader lL = new LevelLoader(level, oM, k, filepath);
        while(!lL.done){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(gameObject x : lL.level){
            x.tag.set(x.tag.indexOf("static"), "newlevel");
            oM.addObject(x);
        }
        out = oM.removeLevel();
        for(gameObject x : lL.level){
            x.tag.set(x.tag.indexOf("newlevel"), "static");
            x.setColor(wall);
            if(x.tag.contains("light")){
                x.setColor(light);
            }
            x.imageName = "";
            x.onlyColor = true;
            x.tag.add("delete_lc");
        }
        last_time = System.nanoTime() - time;
        last_time = last_time / 1000000;
        mapLoadTime = last_time;
        System.out.println("Load lenght: " + mapLoadTime);
        System.out.println("Recalculating lights:");
        //k.rad.recalculate("ignoreRecalculation", 1);
        //k.rad.recalculateParent();
        return out;
    }
    
    String[][] levelmap = null;
    LinkedList<gameObject>[][] cachedLevels = null;
    
    Point2D currentMap = new Point2D(1, 1);

    /**
     * loads, parses and constructs the levelmap
     */
    @SuppressWarnings("unchecked")
    public void constructLevelmap(){
        levelmap = mapParser.parseMap(LevelLoader.getLevelMap("00.pbMap"));
        mapw = levelmap[0].length; 
        maph = levelmap.length; 
        
        if(!mapParser.validateMapSize(mapw, maph)){
            Logger.getGlobal().warning("INVALID LEVELMAP (THE WIDTH AND THE HEIGHT DO NOT MATCH). ABORTING...");
            quickTools.alert("LevelMapReconstruction", "INVALID LEVELMAP (THE WIDTH AND THE HEIGHT DO NOT MATCH). ABORTING...");
            return;
        }
        
        String[][] newLevelmap = new String[maph+2][mapw+2];
        //Fill with "block"
        for(int xp : new Range(mapw+2)){
            for(int yp : new Range(maph+2)){
                newLevelmap[xp][yp] = "block";
            }
        }
        //Copy the levelmap to the inner part of the arrays
        int xp = 0, yp = 0;
        for(String[] lane : levelmap){
            for(String i : lane){
                newLevelmap[xp+1][yp+1] = i;
                yp++;
            }xp++;yp=0;
        }
        levelmap = newLevelmap;
        
        cachedLevels = new LinkedList[mapw+2][maph+2];
        
        System.out.println("mapW: "+mapw);
        System.out.println("mapH: "+maph);
        printLevelmap();
    }
    int mapw = 0; 
    int maph = 0; 
    public void printLevelmap(){
        String current = "!";
                        String cached = "*";
                        String pre = "";
                        System.out.println("Levelmap: ");
                        int longest = 0;
                        //Get the longest namelenght
                        for(String[] lane : levelmap){
                            for(String i : lane){
                                if(i.length() > longest){
                                    longest = i.length();
                                }
                            }
                        }
                        
                        String[][] map = null;
                        try{map = new String[levelmap.length][levelmap[0].length];}
                        catch(Exception e){
                            System.out.println("WARNING: Level map is not initialized properly, skipping any action.");
                            return;
                        }
                        for(int i : new Range(levelmap.length)){
                            map[i] = levelmap[i].clone();
                        }
                        Point2D currentmap2 = currentMap.clone();
                        //Mark cached
                        for(int x : new Range(map.length)){
                            for(int y : new Range(map[0].length)){
                                if(cachedLevels[x][y] != null){
                                    map[x][y] = cached + map[x][y];
                                }
                            }
                        }
                        //Print the levelmap
                        map[(int)currentmap2.x][(int)currentmap2.y] = current + map[(int)currentmap2.x][(int)currentmap2.y];
                        map = quickTools.rotateCW(map);
                        map = quickTools.rotateCW(map);
                        map = quickTools.rotateCW(map);
                        for(String[] lane : map){
                            for(String i : lane){
                                pre = "";
                                String name = i;
                                if(name.contains("\n")){
                                    name = name.replaceAll("\n", "");
                                }
                                
                                
                                String padding = quickTools.multiplyString(" ",(longest+4-i.length()));
                                System.out.print(pre+name+padding);
                            }System.out.println("");
                        }
                        System.out.println("end of levelmap.");
    }
    @SuppressWarnings("unchecked")
    public boolean nextLevel(int direction){
        
        if(levelmap == null){
            constructLevelmap();
        }
        Point2D newLevel = Point2D.add(currentMap, quickTools.vectorDirs4[direction]);
        //System.out.println("current location: "+currentMap.represent()+ " possible new loc: "+newLevel.represent());
        //newLevel.x <= mapw && newLevel.y >= maph
        //System.out.println(newLevel.represent());
        if((int)newLevel.x >= mapw && (int)newLevel.y >= maph){
            return false;
        }
        else if(!(levelmap[(int)newLevel.x][(int)newLevel.y]).equals("block")){
            //currentMap = newLevel;
            if(cachedLevels[(int)newLevel.x][(int)newLevel.y] != null){
                try{
                    cachedLevels[(int)currentMap.x][(int)currentMap.y] = oM.removeLevel();
                    for(gameObject x : cachedLevels[(int)newLevel.x][(int)newLevel.y]){
                        oM.addObject(x);
                    }
                    currentMap = newLevel;
                }catch(Exception e){
                    
                }
            }
            else{
                try {
                    LinkedList<gameObject> old = loadLevel(levelmap[(int)newLevel.x][(int)newLevel.y]+".pblevel");
                    cachedLevels[(int)currentMap.x][(int)currentMap.y] = old;
                    System.out.println("new coords: "+newLevel.represent());
                    currentMap = newLevel;
                } catch (URISyntaxException ex) {
                    System.out.println("Unable to load new level");return false;
                } catch (ArrayIndexOutOfBoundsException ea){
                    return false;
                }
            }
            if(k.bakedLights){
                System.out.println("Loading baked level lights");
                try {
                    bakedcolor = (Color[][]) new LevelLoader("null", oM, k).readObject(levelmap[(int)newLevel.x][(int)newLevel.y] + ".pblevel_illumination.txt");
                    System.out.println(levelmap[(int)newLevel.x][(int)newLevel.y] + "_illumination.txt");
                    LoadedRays = (LinkedList<renderContainer>) new LevelLoader("null", oM, k).readObject(levelmap[(int)newLevel.x][(int)newLevel.y]+".pblevel_lights.txt");
                } catch (URISyntaxException | IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                k.rad.recalculate("ignoreRecalculation", 1, true);
                k.rad.recalculateParent();
            }
        }else{
            return false;
        }
        rads.recalculateParent();
        return true;
    }
    Point2D las;
    public Recorder recorder = new Recorder();
    boolean ve = false;
    Color[][] colored;
    void tick(){
        k.tick_first();
        //System.out.println(input.mapKeyAction());
        //rads.removeA();
        //rads.add(25, 12, 4);
        if(renderRays == 1 && !k.disableVSRAD){
            if(rayDetail == 0){
                rads.recalculate("none", 1, false);
                
            }
            if(rayDetail == 1){
                rads.recalculate("none", 0, false);
                red = rads.read(0);
            }
            
        }
/*        if(tickC < record.size()){
            if(oM.findGameObject("playback") != 99999999){
                Vector loc = record.get(tickC);
                System.out.println("Playing back frame " + tickC + ": " + loc.represent());
                oM.getObjectByTag("playback").setLocation(loc);
            }
        }
        else{
            tickC = -1;
        }*/
        //vector = input.ve;
        
//        aM.play();
//        recorder.record(oM);
        //UPDATE ARRAY
        class xyac
        {
            double x;
            double y;  
            String a;
            Color c;
            Point2D last;
            private xyac(double tx, double ty, String ta, Color tc, Point2D last) {
                this.x = tx; this.y = ty; this.a = ta; this.c = tc; this.last = last;
            }
        }
;
        LinkedList<xyac> lis = new LinkedList<xyac>();
        
        LinkedList<Point2D> points = new LinkedList<>();
        LinkedList<renderContainer> cont1 = new LinkedList<>();
        LinkedList<Color> colors = new LinkedList<>();
        LinkedList<String> images = new LinkedList<>();
        LinkedList<Integer> sizes = new LinkedList<>();
        LinkedList<Double> rotations = new LinkedList<>();
        
        LinkedList<Point2D> points2 = new LinkedList<>();
        LinkedList<renderContainer> cont2 = new LinkedList<>();
        LinkedList<Color> colors2 = new LinkedList<>();
        LinkedList<String> images2 = new LinkedList<>();
        LinkedList<Integer> sizes2 = new LinkedList<>();
        LinkedList<Double> rotations2 = new LinkedList<>();
        objects = oM.getObjects();
        int xp = 0, yp = 0;
        
        double[][] rb = null;
        try {
            rb = quickTools.blur(rads.getR(xd, yd), xd, yd, blurStrenght);
        } catch (Exception e) {
        }
        double[][] gb = null;
        try {
            gb = quickTools.blur(rads.getG(xd, yd), xd, yd, blurStrenght);
        } catch (Exception e) {
        }
        double[][] bb = null;
        try {
            bb = quickTools.blur(rads.getB(xd, yd), xd, yd, blurStrenght);
        } catch (Exception e) {
        }
        if(k.bakedLights){colored = bakedcolor;}
        else{try {
                colored = quickTools.parseColor(xd, yd, rb, gb, bb);
            } catch (Exception e) {
            }
}
        double[][] out = null;
        if(k.disableVSRAD){out = new double[][]{{}};}
        
        else{out = quickTools.blur(red, xd, yd, blurStrenght);}
        if(overrideRayBG != null){
            for(int x: new Range(xd)){
                for(int y: new Range(yd)){
                    cont2.add(new renderContainer(new Point2D(x,y), "", new Color((int) overrideRayBG.getRed(),(int) overrideRayBG.getGreen(),(int) overrideRayBG.getBlue()),1, renderType.box, 0));
                }
                    
            }
        }
        
        for(double[] x : out){
            for(double y : x){
                Color c = new Color(0,0,0);
                c = rads.colors[xp][yp];
                double i = y * 1F;
                if(i > 255){
                    i = 255;
                }
                float r = 0,g = 0,b = 0;
                //System.out.println();
                float brightness = 0.0005F;
                      //rads.colors[....
                try{r = (float) (rb[xp][yp] * (y*brightness));}catch(Exception e){r = 0;}
                try{g = (float) (gb[xp][yp] * (y*brightness));}catch(Exception e){g = 0;}
                try{b = (float) (bb[xp][yp] * (y*brightness));}catch(Exception e){b = 0;}
                if(r > 255){r = 255;}
                if(g > 255){g = 255;}
                if(b > 255){b = 255;}
                cont2.add(new renderContainer(new Point2D(xp,yp), "", new Color((int) r,(int) g,(int) b), 1, renderType.box, 0));
                
                
                points2.add(new Point2D(xp,yp));
                colors2.add(new Color((int) r,(int) g,(int) b));
                images2.add("");
                sizes2.add(1);
//                renderer.change(xp, yp,"█",new Color((int) i,(int) i,(int) i), "n");
                //renderer.vChange(xp * 15.34F, yp * 22.48F, new Color((int) i,(int) i,(int) i));
                yp++;
            }
            xp++;
            yp = 0;
        }
        /*for(double[] x : out){
            for(double y : x)
            xp++;
            yp = 0;
            for(double i : x){
                System.out.print((int)i+" ");
            }System.out.println("");
        }
        System.out.println("########################################");*/
//        oM.doPhysics(renderer);

                cam.update();
            if(Objects.isNull(cam.target)){
            }
        for(gameObject p : objects){
//            renderer.change(tx, ty, "█", Color.WHITE);
//            System.out.println(p.getTag().getClass() + " " + "static".getClass());
            p.update(xd, yd, oM);
//            p.checkInput(input);
            
//            oM.doPhysics(renderer, p);
            this.txf = p.getX();
            this.tyf = p.getY();
            this.tx = (int) round(p.getX());
            this.ty = (int) round(p.getY());
            this.las = new Point2D(p.lastX, p.lastY);
            p.lastX = txf;
            p.lastY = tyf;
            ta = p.gAppearance();
            Color tc = p.getColor();
//            p.update(renderer);
            if(p.getTag().contains("player1") || p.getTag().contains("cursor")){
                //cam.setLocation(p.x, p.y);
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.MAGENTA));
                //rads.add(tx, ty, 1);
//                aM.setVolume(d/10);
//                System.out.println(aM.getVolume());
            }else{}
            if(p.getTag().contains(new String("player2"))){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.CYAN,co+3));
//                co++;
            }
//            System.out.println((p.getVX() + 1F) * (p.getVY()+1F));
            if(p.getTag().contains(new String("null")) && p.hits > 7){
//                oM.removeObject(p);
            }
        
//          System.out.println("pelaaja: x:" + tx + " y:" + ty);
/////////////////            renderer.change(tx, ty, ta, tc);
            points.add(new Point2D(txf, tyf));
            images.add(p.imageName);
            float r = tc.getRed();
            float g = tc.getGreen();
            float b = tc.getBlue();
                    //global brightness
            
            try{                                //.readColor(tx, ty).getRed()
                try{r = (r * global_brightness + (colored[tx][ty].getRed()));if(r > 255){r = 255;}}catch(Exception e2){r = 0;}
                try{g = (g * global_brightness + (colored[tx][ty].getGreen()));if(g > 255){g = 255;}}catch(Exception e2){g = 0;}
                try{b = (b * global_brightness + (colored[tx][ty].getBlue()));if(b > 255){b = 255;}}catch(Exception e2){b = 0;}
            //    r = (r * global_brightness + (rads.colors[tx][ty].getRed() * 0.5F) / 2 );if(r > 255){r = 255;}
            //    g = (g * global_brightness + (rads.colors[tx][ty].getGreen() * 0.5F) / 2 );if(g > 255){g = 255;}
            //    b = (b * global_brightness + (rads.colors[tx][ty].getBlue() * 0.5F) / 2 );if(b > 255){b = 255;}
            }catch(Exception e){
                
                try{r = (float) (r * global_brightness * (red[tx][ty] * 0.55F) / 2 );if(r > 255){r = 255;}}catch(Exception e2){r = 0;}
                try{g = (float) (g * global_brightness * (red[tx][ty] * 0.55F) / 2 );if(g > 255){g = 255;}}catch(Exception e2){g = 0;}
                try{b = (float) (b * global_brightness * (red[tx][ty] * 0.55F) / 2 );if(b > 255){b = 255;}}catch(Exception e2){b = 0;}
                throw(e);
            }
            if(abright){
                r = 255;
                g = 255;
                b = 255;
            }
            if(p.pureColor){
                r = tc.getRed();
                g = tc.getGreen();
                b = tc.getBlue();
            }
            if(p.onlyColor){
                r = p.getColor().getRed();
                g = p.getColor().getGreen();
                b = p.getColor().getBlue();
            }
            if(p.visible){cont1.add(new renderContainer(new Point2D(txf, tyf), p.imageName, new Color((int)r,(int)g,(int)b), p.size, p.shape, p.getRadians()));}
            colors.add(new Color((int)r,(int)g,(int)b));
            lis.add(new xyac(tx,ty,ta,tc,las));
            sizes.add(p.size);

            //RENDER
//            renderer.fill(Integer.toString(number));
        }
//        renderer.fill("█", Color.BLACK, "null");
        //renderer.vectorFill(Color.BLACK, vector);
        //Render
        
        //renderer.canvas.clean();
        
//        Vrenderer.update(points, colors, images, sizes, 2F, 1);
//        Vrenderer.update(points2, colors2, images2, sizes2, 2F, 0);
        bakedRays = cont2;
        Vrenderer.update(cont1, 1, tickC);
        if(LoadedRays != null && k.bakedLights){Vrenderer.update(LoadedRays,0,tickC);}
        else{Vrenderer.update(cont2, 0,tickC);}
        
        for(xyac a : lis){
//            renderer.change((int) (a.x), (int) (a.y), a.a, a.c, "n");
            //lM.vChange(a.last.x * 15.34F, a.last.y * 22.48F, a.a, Color.black, vector);
            //renderer.vChange(a.x * 15.34F, a.y * 22.48F, a.c);
        }
        
//        area.setText(fetch(renderer));
        k.tick_late();
    }
    public Color overrideRayBG = null;
    /*_LString fetch(LegacyRenderer render)
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
    
    void record() {
    try {
    
    recorder.write(recorder.recorded, "/src/com/viridistudios/viridiengine/records/recorded.txt");
    } catch (UnsupportedEncodingException ex) {
    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
    Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/

    
    
}
