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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.round;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class engine extends JFrame implements Runnable, ActionListener {
    public boolean ready = false;
    public boolean running = true;
    //Screen components
    int blurStrenght = 3;
    public dVector gravity;
    quickEffects effects = new quickEffects();
    public LinkedList<Object> content = new LinkedList<>();
    public float global_brightness = 0.55F;
    public int rayDetail = 0;
    public int vector = 1;
    public int renderRays = 1;
    public LinkedList<Vector> record;
    colorParser cP = new colorParser();
    Timer timer = new Timer(15, this);
    float[][] red;
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    //Renderer renderer = new Renderer();
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
    private float txf;
    private float tyf;
//    
    public vectorArea vA;
    kick k;
    private Input input;
    public engine(kick ki, objectManager o, int xd, int yd, VSRadManager a, dVector g){
        
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
    }
    private AudioSource aM;
    VSRadManager rads;
    
    @Override
    public void run() {
        System.out.println("Initializing engine...");
        ready = false;
        this.rads = new VSRadManager(xd, yd, oM);
        
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
        
        vA = new vectorArea();
        vA.init((int)w, (int)h, 3, false);
        //try {vA.setImage(new directory().textures + "splash.png");}
        //catch (IOException ex) {quickEffects.alert(ex.getMessage());}
        this.add(vA);
        content.add(vA);
        if(vA.sSi){
            vA.setVisible(true);
        }
        ImagePanel splash = new ImagePanel();
        try{
            File img = new File(new directory().textures + "splash.png");
            BufferedImage image = ImageIO.read(img);
            splash.image = image;
            this.add(splash);
        } catch (IOException ex) {
            quickEffects.alert(ex.getMessage());
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
        red = new float[xd][yd];
        //canvas = renderer.canvas;
        
        //rads = new VSRadManager(xd, yd, oM);
        //rads.add(25, 12, 4, new Color(1, 1, 1), 0);
        //rads.add(24, 24, 4, new Color(1, 0, 0), 1);
        //rads.add(25, 12, 4, new Color(1, 1, 1), 0);
        //rads.add(12, 1, 1, new Color(0, 0, 10));
        red = rads.read(99);
        screen = "";
        
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
        
        //levelLoader lL = new levelLoader("/src/viridiengine/levels/out.txt", oM);
        
        
        
        //record = recorder.read("/records/recorded.txt");
        //oM.addObject(new Player(3, 3, "playback", "█", 1F, Color.blue, 11));
        //Add AudioSource
/*        try {
        aM = new AudioSource();
        } catch (LineUnavailableException ex) {
        Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
        Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
        ready = true;
        revalidate();
        repaint();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            quickEffects.alert(ex.getMessage());
        }
        try{this.remove(splash);}catch(Exception e){}
        vA.sSi = false;
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
            tickC++;
        }
        revalidate();
        repaint();
    }
    public void loadLevel(String level) throws URISyntaxException{
        oM.removeLevel();
        levelLoader lL = new levelLoader(level, oM, k);
    }
    Vector las;
    public Recorder recorder = new Recorder();
    boolean ve = false;
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
        recorder.record(oM);
        //UPDATE ARRAY
        class xyac
        {
            float x;
            float y;  
            String a;
            Color c;
            Vector last;
            private xyac(float tx, float ty, String ta, Color tc, Vector last) {
                this.x = tx; this.y = ty; this.a = ta; this.c = tc; this.last = last;
            }
        }
;
        LinkedList<xyac> lis = new LinkedList<xyac>();
        
        LinkedList<Vector> points = new LinkedList<>();
        LinkedList<Color> colors = new LinkedList<>();
        LinkedList<String> images = new LinkedList<>();
        
        LinkedList<Vector> points2 = new LinkedList<>();
        LinkedList<Color> colors2 = new LinkedList<>();
        LinkedList<String> images2 = new LinkedList<>();
        objects = oM.getObjects();
        int xp = 0, yp = 0;
        /*
        float rb[][] = effects.blur(rads.getR(xd, yd), xd, yd, blurStrenght);
        float gb[][] = effects.blur(rads.getG(xd, yd), xd, yd, blurStrenght);
        float bb[][] = effects.blur(rads.getB(xd, yd), xd, yd, blurStrenght);
        Color[][] colored = quickEffects.parseColor(xd, yd, rb, gb, bb);
        */
        float[][] out = quickEffects.blur(red, xd, yd, blurStrenght);
        for(float[] x : out){
            for(float y : x){
                Color c = new Color(0,0,0);
                c = rads.colors[xp][yp];
                double i = y * 1F;
                if(i > 255){
                    i = 255;
                }
                float r = 0,g = 0,b = 0;
                //System.out.println();
                float brightness = 0.001F;
                      //rads.colors[....
                try{r = rads.colors[xp][yp].getRed() * (y*brightness);}catch(Exception e){r = 0F;}
                try{g = rads.colors[xp][yp].getGreen() * (y*brightness);}catch(Exception e){g = 0F;}
                try{b = rads.colors[xp][yp].getBlue() * (y*brightness);}catch(Exception e){b = 0F;}
                if(r > 255){r = 255;}
                if(g > 255){g = 255;}
                if(b > 255){b = 255;}
                
                points2.add(new Vector(xp,yp));
                colors2.add(new Color((int) r,(int) g,(int) b));
                images2.add("");
//                renderer.change(xp, yp,"█",new Color((int) i,(int) i,(int) i), "n");
                //renderer.vChange(xp * 15.34F, yp * 22.48F, new Color((int) i,(int) i,(int) i));
                yp++;
            }
            xp++;
            yp = 0;
        }
//        oM.doPhysics(renderer);
        for(gameObject p : objects){
//            renderer.change(tx, ty, "█", Color.WHITE);
//            System.out.println(p.getTag().getClass() + " " + "static".getClass());
            p.update(xd, yd, oM);
            p.checkInput(input);
            
//            oM.doPhysics(renderer, p);
            this.txf = p.getX();
            this.tyf = p.getY();
            this.tx = round(p.getX());
            this.ty = round(p.getY());
            this.las = new Vector(p.lastX, p.lastY);
            p.lastX = txf;
            p.lastY = tyf;
            ta = p.gAppearance();
            Color tc = p.getColor();
//            p.update(renderer);
            if(p.getTag().contains(new String("player1"))){
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
            points.add(new Vector(txf, tyf));
            images.add(p.imageName);
            float r = tc.getRed();
            float g = tc.getGreen();
            float b = tc.getBlue();
                    //global brightness
                    
            try{                                //.readColor(tx, ty).getRed()
                try{r = (r * global_brightness + (rads.readColor(tx, ty).getRed()));if(r > 255){r = 255;}}catch(Exception e2){r = 0;}
                try{g = (g * global_brightness + (rads.readColor(tx, ty).getGreen()));if(g > 255){g = 255;}}catch(Exception e2){g = 0;}
                try{b = (b * global_brightness + (rads.readColor(tx, ty).getBlue()));if(b > 255){b = 255;}}catch(Exception e2){b = 0;}
            //    r = (r * global_brightness + (rads.colors[tx][ty].getRed() * 0.5F) / 2 );if(r > 255){r = 255;}
            //    g = (g * global_brightness + (rads.colors[tx][ty].getGreen() * 0.5F) / 2 );if(g > 255){g = 255;}
            //    b = (b * global_brightness + (rads.colors[tx][ty].getBlue() * 0.5F) / 2 );if(b > 255){b = 255;}
            }catch(Exception e){
                
                try{r = (r * global_brightness + (red[tx][ty] * 0.55F) / 2 );if(r > 255){r = 255;}}catch(Exception e2){r = 0;}
                try{g = (g * global_brightness + (red[tx][ty] * 0.55F) / 2 );if(g > 255){g = 255;}}catch(Exception e2){g = 0;}
                try{b = (b * global_brightness + (red[tx][ty] * 0.55F) / 2 );if(b > 255){b = 255;}}catch(Exception e2){b = 0;}
                throw(e);
            }
            
            colors.add(new Color((int)r,(int)g,(int)b));
            lis.add(new xyac(tx,ty,ta,tc,las));


            //RENDER
//            renderer.fill(Integer.toString(number));
        }
//        renderer.fill("█", Color.BLACK, "null");
        //renderer.vectorFill(Color.BLACK, vector);
        //Render
        
        //renderer.canvas.clean();
        vA.update(points, colors, images, 2F, 1);
        vA.update(points2, colors2, images2, 2F, 0);
        for(xyac a : lis){
//            renderer.change((int) (a.x), (int) (a.y), a.a, a.c, "n");
            //lM.vChange(a.last.x * 15.34F, a.last.y * 22.48F, a.a, Color.black, vector);
            //renderer.vChange(a.x * 15.34F, a.y * 22.48F, a.c);
        }
        
//        area.setText(fetch(renderer));
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

    void record() {
        try {
            
            recorder.write(recorder.recorded, "/src/com/viridistudios/viridiengine/records/recorded.txt");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(engine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
