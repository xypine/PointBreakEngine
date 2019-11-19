/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import JFUtils.Input;
import JFUtils.Vector;
import JFUtils.Range;
import JFUtils.dVector;
import JFUtils.quickTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.round;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class Engine extends JFrame implements Runnable, ActionListener {
    public Camera cam = new Camera(0, 0);
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
    public dVector gravity;
    quickTools effects = new quickTools();
    public LinkedList<Object> content = new LinkedList<>();
    public float global_brightness = 0.55F;
    public int rayDetail = 0;
    public int vector = 1;
    public int renderRays = 0;
    public LinkedList<Vector> record;
    colorParser cP = new colorParser();
    Timer timer = new Timer(15, this);
    double[][] red;
    int tickC = 0;
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
    objectManager oM;
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
    public Engine(Supervisor ki, objectManager o, int xd, int yd, VSRadManager a, dVector g, String level){
        
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
    public Engine(Supervisor ki, objectManager o, int xd, int yd, VSRadManager a, dVector g, String level, int targetSpeed){
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
        this.setTitle("PointBreakEngine");
        this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        Vrenderer = new Renderer(k);
        Vrenderer.sSi = true;
        this.add(Vrenderer);
        revalidate();
        repaint();
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
        
        this.add(area);
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
        red = rads.read(99);
        screen = "";
        
        if(k.bakedLights){
            System.out.println("Loading baked level lights");

            try {
                bakedcolor = (Color[][]) new FileLoader("null", oM, k).readObject(levelName + "_illumination.txt");
                LoadedRays = (LinkedList<renderContainer>) new FileLoader("null", oM, k).readObject(levelName + "_lights.txt");
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
        revalidate();
        repaint();
        //timerType = 0;
        if (timerType == 0) {
            timer.start();
        } else if(timerType == 1){
            startFullTickTimer();
        }
    }
    //Function for reshfreshing the screen
    private void fresh(){
        this.removeAll();
        for(Object i : content){
            //this.add((Component) i);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.timerType != 0){
            System.out.println("Wrong timerType ignored.");
            return;
        }
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
                    FileLoader lL = new FileLoader("null", oM, k);
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
        revalidate();
        repaint();
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
    public void fulltick(){
        if(this.timerType != 1){
            System.out.println("Wrong timerType ignored.");
            return;
        }
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
                    FileLoader lL = new FileLoader("null", oM, k);
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
        revalidate();
        repaint();
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
    public dVector getCurrentLevelCoord(){
        return currentMap.clone();
    }
    
    
    double mapLoadTime = 0;
    public LinkedList<gameObject> loadLevel(String level) throws URISyntaxException{
        return loadLevel(level, new directory().levels);
    }
    public LinkedList<gameObject> loadLevel(String level, String levelpath) throws URISyntaxException{
        LinkedList<gameObject> out = new LinkedList<>();
        long time = System.nanoTime();
        FileLoader lL = new FileLoader(level, oM, k, levelpath);
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
        FileLoader lL = new FileLoader(level, oM, k, filepath);
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
    
    dVector currentMap = new dVector(1, 1);
    @SuppressWarnings("unchecked")
    public void constructLevelmap(){
        levelmap = mapParser.parseMap(FileLoader.getLevelMap("00.pbMap"));
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
                        dVector currentmap2 = currentMap.clone();
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
        dVector newLevel = dVector.add(currentMap, quickTools.vectorDirs4[direction]);
        System.out.println("current location: "+currentMap.represent()+ " possible new loc: "+newLevel.represent());
        //newLevel.x <= mapw && newLevel.y >= maph
        System.out.println(newLevel.represent());
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
                    bakedcolor = (Color[][]) new FileLoader("null", oM, k).readObject(levelmap[(int)newLevel.x][(int)newLevel.y] + ".pblevel_illumination.txt");
                    System.out.println(levelmap[(int)newLevel.x][(int)newLevel.y] + "_illumination.txt");
                    LoadedRays = (LinkedList<renderContainer>) new FileLoader("null", oM, k).readObject(levelmap[(int)newLevel.x][(int)newLevel.y]+".pblevel_lights.txt");
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
    dVector las;
    public Recorder recorder = new Recorder();
    boolean ve = false;
    Color[][] colored;
    void tick(){
        k.tick_first();
        //System.out.println(input.mapKeyAction());
        //rads.removeA();
        //rads.add(25, 12, 4);
        if(renderRays == 1){
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
            dVector last;
            private xyac(double tx, double ty, String ta, Color tc, dVector last) {
                this.x = tx; this.y = ty; this.a = ta; this.c = tc; this.last = last;
            }
        }
;
        LinkedList<xyac> lis = new LinkedList<xyac>();
        
        LinkedList<dVector> points = new LinkedList<>();
        LinkedList<renderContainer> cont1 = new LinkedList<>();
        LinkedList<Color> colors = new LinkedList<>();
        LinkedList<String> images = new LinkedList<>();
        LinkedList<Integer> sizes = new LinkedList<>();
        LinkedList<Double> rotations = new LinkedList<>();
        
        LinkedList<dVector> points2 = new LinkedList<>();
        LinkedList<renderContainer> cont2 = new LinkedList<>();
        LinkedList<Color> colors2 = new LinkedList<>();
        LinkedList<String> images2 = new LinkedList<>();
        LinkedList<Integer> sizes2 = new LinkedList<>();
        LinkedList<Double> rotations2 = new LinkedList<>();
        objects = oM.getObjects();
        int xp = 0, yp = 0;
        
        double rb[][] = quickTools.blur(rads.getR(xd, yd), xd, yd, blurStrenght);
        double gb[][] = quickTools.blur(rads.getG(xd, yd), xd, yd, blurStrenght);
        double bb[][] = quickTools.blur(rads.getB(xd, yd), xd, yd, blurStrenght);
        if(k.bakedLights){colored = bakedcolor;}
        else{colored = quickTools.parseColor(xd, yd, rb, gb, bb);}
        
        double[][] out = quickTools.blur(red, xd, yd, blurStrenght);
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
                if(overrideRayBG != null){
                    cont2.add( new renderContainer(new dVector(xp,yp), "", new Color((int) overrideRayBG.getRed(),(int) overrideRayBG.getGreen(),(int) overrideRayBG.getBlue()), 1, 0));
                }else{
                    cont2.add( new renderContainer(new dVector(xp,yp), "", new Color((int) r,(int) g,(int) b), 1, 0));
                }
                
                points2.add(new dVector(xp,yp));
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
            this.las = new dVector(p.lastX, p.lastY);
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
            if(p.getTag().contains("player1_torso2")){
                cam.setLocation(p.x, p.y);
            }
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
            points.add(new dVector(txf, tyf));
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
            if(p.visible){cont1.add( new renderContainer(new dVector(txf, tyf), p.imageName, new Color((int)r,(int)g,(int)b), p.size, p.getRadians()));}
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
    String fetch(LegacyRenderer render)
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
    }

    
    
}
