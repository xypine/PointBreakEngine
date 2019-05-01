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
import java.awt.font.NumericShaper.Range;
import java.beans.EventHandler;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafinelse
 */
public class windowManager extends JFrame implements Runnable, ActionListener {
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
    kick k;
    private Input input;
    public windowManager(kick ki){
        System.out.println("Initializing main input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out main input: " + k);
    }
    private audioManager aM;
    
    @Override
    public void run() {
        
        
        timer.setRepeats(true);
        timer.start();
        
        //Initiate screen size
        float size = 1F;
        float w = 767*size;
        float h = 562*size;
        float aspect = w / h;
        System.out.println(aspect);
        
//        int xd = 50;
//        int yd = 25;
        int xd = (int) (w / 15.34);
        int yd = (int) (h / 22.48);
        this.setTitle("ViridiEngine");
        this.setSize(round(w), round(h));
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
        area.setForeground(Color.white);
        area.setBackground(Color.black);
        
        this.add(area);
        
        System.out.println("Initializing game...");
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        synchronized(lM) {
            lM.init(xd, yd);
        }
        tmp = lM.gets();
        
        screen = "";
        System.out.println("Done initializing game!");
        
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
        
        
        area.setText(fetch(lM));
//        area.setEditable(false);
        
        
        
        //SUMMON TEST
        
        levelLoader lL = new levelLoader("/src/viridiengine/levels/out.txt", oM);
        oM.addObject(new Player(0, 0, "player1", "█", 1F, Color.black, 1));
        //Add audioManager
/*        try {
            
            aM = new audioManager();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(windowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
    }
    
    public boolean running = true;
    @Override
    public void actionPerformed(ActionEvent e) {
        this.number = Integer.parseInt(Integer.toString(tickC).substring(0, 1));
        if(running == true){
            tick();
        }
        
        tickC++;
    }
    public Recorder recorder = new Recorder();
    void tick(){
//        aM.play();
        recorder.record(oM);
        //UPDATE ARRAY
        class xyac
        {
            int x;
            int y;  
            String a;
            Color c;

            private xyac(int tx, int ty, String ta, Color tc) {
                this.x = tx; this.y = ty; this.a = ta; this.c = tc;
            }
        }
;
        LinkedList<xyac> lis = new LinkedList<xyac>();
        objects = oM.getObjects();
        lM.fill("█", Color.BLACK, "null");
        

//        oM.doPhysics(lM);
        for(gameObject p : objects){
//            lM.change(tx, ty, "█", Color.WHITE);
//            System.out.println(p.getTag().getClass() + " " + "static".getClass());
            p.update(lM, oM);
            p.checkInput(input);
            
//            oM.doPhysics(lM, p);
            this.txf = p.getX();
            this.tyf = p.getY();
            this.tx = round(p.getX());
            this.ty = round(p.getY());
            ta = p.gAppearance();
            Color tc = p.getColor();
//            p.update(lM);
            if(p.getTag() == "player1"){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.MAGENTA));
                float d = p.getDistance(25F, 12.5F);
//                aM.setVolume(d/10);
//                System.out.println(aM.getVolume());
                
            }
            if(p.getTag() == "player2"){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.CYAN,co+3));
//                co++;
            }
//            System.out.println((p.getVX() + 1F) * (p.getVY()+1F));
            if(p.getTag() == "null" && p.hits > 7){
//                oM.removeObject(p);
            }
        
//          System.out.println("pelaaja: x:" + tx + " y:" + ty);
/////////////////            lM.change(tx, ty, ta, tc);
            lis.add(new xyac(tx,ty,ta,tc));


            //RENDER
//            lM.fill(Integer.toString(number));
        }
        
        //Render
        for(xyac a : lis){
            lM.change(a.x, a.y, a.a, a.c, "n");
        }
        
        area.setText(fetch(lM));
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
