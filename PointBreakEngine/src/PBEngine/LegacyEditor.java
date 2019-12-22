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

import PBEngine.Rendering.legacy.LegacyRenderer;
import JFUtils.Input;
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
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class LegacyEditor extends JFrame implements Runnable, ActionListener {
    public boolean ready = false;
    private Supervisor k;
    colorParser cP = new colorParser();
    Timer timer = new Timer(1, this);
    int tickC = 0;
    int number;
    String screen;
    String[][] tmp;
    LegacyRenderer lM = new LegacyRenderer();
    JLabel area;
    
    double lastTime;
    
    
    //GAMEOBJECTS:
//    Player p1;
    int co = 0;
    objectManager oM = new objectManager(k);
    LinkedList<gameObject> players;
    //VARIABLES FOR TICKS:
    int tx;
    int ty;
    String ta;
    private float txf;
    private float tyf;
//    
    private Input input;
    public LegacyEditor(Supervisor ki){
        System.out.println("Initializing editor input: " + ki);
        this.k = ki;
        input = new Input(k);
        System.out.println("out editor input: " + k);
    }
    //Input:
    LevelLoader aol;
    float size = 1F;
    float w = 767*size;
    float h = 562*size;
    int xd = (int) (w / 15.34);
    int yd = (int) (w / 15.34);
    Question q;
    @Override
    public void run() {
        q = new Question("Properties", "Value", "Set Value");
        
        ready = false;
        
        timer.setRepeats(true);
        timer.start();
        
        //Initiate screen size
        
        float aspect = w / h;
        System.out.println(aspect);
        
//        int xd = 50;
//        int yd = 25;
        xd = (int) (w / 15.34);
        yd = (int) (h / 22.48);
        this.setTitle("ViridiEngine Editor");
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
        
        System.out.println("Initializing editor...");
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.setVisible(false);
        getContentPane().setBackground( Color.black );
        
        synchronized(lM) {
            lM.init(xd, yd, this);
        }
        tmp = lM.gets();
        
        screen = "";
        System.out.println("Done initializing editor!");
        
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
        try {
            //SUMMON TEST
            
            aol = new LevelLoader("null", oM, k);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LegacyEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        oM.addObject(new Player(5, 5, 1, "cursor", "█", 1F, Color.black, 1, k));
        
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
          ready = true;
        
    }
    
    public boolean running = false;
    @Override
    public void actionPerformed(ActionEvent e) {
        
//        double fps = 1000000.0 / (lastTime - (lastTime = System.nanoTime())); //This way, lastTime is assigned and used at the same time.
//        System.out.println("FPS: " + fps);
//        lastTime = System.nanoTime();
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
            if((tickC % 1) == 0){
                tick();
            }
        }
        
//        System.out.println(this.getWidth() + ", " + this.getHeight());
        tickC++;
    }
    boolean saved = false;
    void tick(){
//        System.out.println(oM.getObjects().size());
        //UPDATE ARRAY
        gameObject z = oM.getObjectByTag("cursor");
        int zx = (int) round(z.getX());
        int zy = (int) round(z.getY());
        if(input.tog && !oM.colliding(zx,zy,"null")){
            oM.addObject(new gameObject(zx, zy, 1, "static", "█", 1F, Color.red, 1, k));
            System.out.println("new wall!");
            saved = false;
        }
        if(input.keyPressed != null){//System.out.println(input.keyPressed.getKeyCode());
        if(q.adding && !oM.colliding(zx,zy,"null")){
            try{
                
                oM.addObject(new gameObject(zx, zy, 1, "light", "█", q.s, new Color(q.r,q.g,q.b), 1, k));
                System.out.println("new light!");
                saved = false;
                q.adding = false;
            }catch(Exception e){
                quickTools.alert("FAILED TO ADD A LIGHT", e.getMessage());
            }
        }}
        if(q.adding && oM.colliding(zx,zy,"null")){
            q.adding = false;
        }
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
        players = oM.getObjects();
        if(input.ke == 'l' && !saved){
            try {
                aol.write(players, "out.pblevel");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(LegacyEditor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LegacyEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
            saved = true;
        }
        lM.fill("█", Color.WHITE, "null");
        

//        oM.doPhysics(lM);
        for(gameObject p : players){
//            lM.change(tx, ty, "█", Color.WHITE);
//            System.out.println(p.getTag().getClass() + " " + "static".getClass());
            p.checkInput(input);
            p.update(xd, yd, oM);
//            oM.doPhysics(lM, p);
            this.txf = (float) p.getX();
            this.tyf = (float) p.getY();
            this.tx = (int) round(p.getX());
            this.ty = (int) round(p.getY());
            ta = p.gAppearance();
            Color tc = p.getColor();
//            p.update(lM);
            if(p.getTag().contains(new String("player1"))){
//                oM.addObject(new Player(tx, ty, "null", "█", 1F, Color.MAGENTA));
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

    
    
}
