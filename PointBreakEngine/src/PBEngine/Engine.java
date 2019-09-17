/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

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
    Camera cam = new Camera(0, 0);
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
    quickEffects effects = new quickEffects();
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
    public Renderer vA;
    kick k;
    public Input input;
    public Engine(kick ki, objectManager o, int xd, int yd, VSRadManager a, dVector g){
        
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
        this.rads = a;
    }
    private AudioSource aM;
    VSRadManager rads;
    
    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        System.out.println("Initializing engine...");
        ready = false;
        
        
        timer.setRepeats(true);
        
        //Initiate screen size
        float size = 1F;
        float w = 1080*size;
        float h = 540*size;
        float aspect = w / h;
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
        
        vA = new Renderer(k);
        vA.sSi = true;
        this.add(vA);
        revalidate();
        repaint();
        vA.init((int)w, (int)h, 3, false);
        //try {vA.setImage(new directory().textures + "splash.png");}
        //catch (IOException ex) {quickEffects.alert(ex.getMessage());}
        
        content.add(vA);
        if(vA.sSi){
            vA.setVisible(true);
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
                bakedcolor = (Color[][]) new FileLoader("null", oM, k).readObject("out_illumination.txt");
                LoadedRays = (LinkedList<renderContainer>) new FileLoader("null", oM, k).readObject("out_lights.txt");
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
        timer.start();
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
        //fresh();
        long time = System.nanoTime();
        deltatime = (int) ((time - last_time) / 1000000);
        last_time = time;
        
        k.kit.time.setText(deltatime + "");
        
        if(input.keyPressed != null){
            if(input.keyPressed.getKeyChar() == 'l' && !raysBaked){
                raysBaked = true;
                System.out.println("saving lights");
                try {
                    FileLoader lL = new FileLoader("null", oM, k);
                    lL.writeObject(bakedRays, "out_lights.txt");
                    lL.writeObject(colored, "out_illumination.txt");
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
            vA.setVisible(false);
        }
        if(vector == 1){
            area.setVisible(false);
            vA.setVisible(true);
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
    double mapLoadTime = 0;
    public void loadLevel(String level) throws URISyntaxException{
        long time = System.nanoTime();
        oM.removeLevel();
        FileLoader lL = new FileLoader(level, oM, k);
        last_time = System.nanoTime() - time;
        last_time = last_time / 1000000;
        mapLoadTime = last_time;
        System.out.println("Load lenght: " + mapLoadTime);
        System.out.println("Recalculating lights:");
        //k.rad.recalculate("ignoreRecalculation", 1);
        //k.rad.recalculateParent();
    }
    dVector las;
    public Recorder recorder = new Recorder();
    boolean ve = false;
    Color[][] colored;
    void tick(){
        
        //rads.removeA();
        //rads.add(25, 12, 4);
        if(renderRays == 1){
            if(rayDetail == 0){
                rads.recalculate("none", 1);
                
            }
            if(rayDetail == 1){
                rads.recalculate("none", 0);
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
        
        double rb[][] = quickEffects.blur(rads.getR(xd, yd), xd, yd, blurStrenght);
        double gb[][] = quickEffects.blur(rads.getG(xd, yd), xd, yd, blurStrenght);
        double bb[][] = quickEffects.blur(rads.getB(xd, yd), xd, yd, blurStrenght);
        if((bakedcolor != null && !k.bakedLights)){colored = bakedcolor;}
        else{colored = quickEffects.parseColor(xd, yd, rb, gb, bb);}
        
        double[][] out = quickEffects.blur(red, xd, yd, blurStrenght);
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
                
                cont2.add( new renderContainer(new dVector(xp,yp), "", new Color((int) r,(int) g,(int) b), 1, 0));
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
            if(p.getTag().contains(new String("player1"))){
                cam.setLocation(p.x, p.y);
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
            cont1.add( new renderContainer(new dVector(txf, tyf), p.imageName, new Color((int)r,(int)g,(int)b), p.size, p.getRadians()));
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
        
//        vA.update(points, colors, images, sizes, 2F, 1);
//        vA.update(points2, colors2, images2, sizes2, 2F, 0);
        bakedRays = cont2;
        vA.update(cont1, 1);
        if(LoadedRays != null && k.bakedLights){vA.update(LoadedRays,0);}
        else{vA.update(cont2, 0);}
        
        for(xyac a : lis){
//            renderer.change((int) (a.x), (int) (a.y), a.a, a.c, "n");
            //lM.vChange(a.last.x * 15.34F, a.last.y * 22.48F, a.a, Color.black, vector);
            //renderer.vChange(a.x * 15.34F, a.y * 22.48F, a.c);
        }
        
//        area.setText(fetch(renderer));
    }
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
