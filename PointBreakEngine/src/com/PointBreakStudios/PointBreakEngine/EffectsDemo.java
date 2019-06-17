/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.LinkedList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class EffectsDemo extends JFrame implements Runnable, ActionListener {
    //Screen components
    int blur = 2;
    public dVector gravity;
    quickEffects GE = new quickEffects();
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
    Renderer renderer = new Renderer();
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
    public EffectsDemo(kick ki, objectManager o, int xd, int yd, VSRadManager a, dVector g){
        
        this.oM = o;
        this.xd = xd;
        this.yd = yd;
        System.out.println("Initializing effect input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out effect input: " + k);
    }
    
    float[][] demo;
    float[][] sauce;
    @Override
    public void run() {
        
        sauce = new float[xd][yd];
        sauce[12][17] = 255F;
        demo = GE.blur(sauce, xd, yd, 2);
        timer.setRepeats(true);
        timer.start();
        
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
        this.setTitle("ViridiEngine effect demo");
        this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
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
        System.out.println("Initializing effects demo...");
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        synchronized(renderer) {
            renderer.init(xd, yd, this);
        }
        red = new float[xd][yd];
        //canvas = renderer.canvas;
        vA = new vectorArea();
        this.add(vA);
        content.add(vA);
        vA.init((int)w, (int)h, 3);
        screen = "";
        
        //fresh();
        System.out.println("Done initializing effects demo!");
        
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
    }
    public boolean ready;
    //Function for reshfreshing the screen
    private void fresh(){
        this.removeAll();
        for(Object i : content){
            //this.add((Component) i);
        }
    }
    
    public boolean running = true;
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
    boolean ve = false;
    int cu = 0;
    void tick(){
        //vector = input.ve;
        demo = GE.blur(sauce, xd, yd, blur);
//        aM.play();
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
        objects = oM.getObjects();
        int xp = 0, yp = 0;
        for(float[] x : demo){
            for(float y : x){
                int i = (int) (y * 1F);
                if(i > 255){
                    i = 255;
                }
                points.add(new Vector(xp,yp));
                colors.add(new Color(i,i,i));
                yp++;
            }
            xp++;
            yp = 0;
        }
        renderer.fill("█", Color.BLACK, "null");
        //renderer.vectorFill(Color.BLACK, vector);
        //Render
        
        //renderer.canvas.clean();
        vA.update(points, colors, new LinkedList<String>(), 20F, 0);
        for(xyac a : lis){
            //renderer.change((int) (a.x), (int) (a.y), a.a, a.c, "n");
            //lM.vChange(a.last.x * 15.34F, a.last.y * 22.48F, a.a, Color.black, vector);
            //renderer.vChange(a.x * 15.34F, a.y * 22.48F, a.c);
        }
        
        //area.setText(fetch(renderer));
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
