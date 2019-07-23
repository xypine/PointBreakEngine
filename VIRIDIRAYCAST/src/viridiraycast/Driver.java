/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiraycast;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Jonnelafin
 */
public class Driver implements Runnable, MouseMotionListener, KeyListener{
    int res = 801;
    double rotation = 0;
    private int mouseX=698, mouseY=129;
    private Canvas canvas;
    private Canvas canvas2;
    LinkedList<Line2D.Float> lines;
    LinkedList<LinkedList> three = new LinkedList<>();
    private static final int WIDTH = 800, HEIGHT = 800;
    int[] distances;
    LinkedList<int[]> dists = new LinkedList<>();
    private static final Random random = new Random(100);
    public Driver(){
        distances = new int[res];
        lines = buildLines(12);
        for(int i=0;i<res;i++){
            LinkedList<Line2D.Float> tmp = buildLines(i);
            three.add(tmp);
        }
        JFrame frame = new JFrame();
        JFrame frame2 = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("rays");
        frame2.setTitle("raster");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas = new Canvas());
        frame2.add(canvas2 = new Canvas());
        canvas.addMouseMotionListener(this);
        canvas2.addKeyListener(this);
        frame.setSize(WIDTH, HEIGHT);
        frame2.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame2.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame2.setVisible(true);
        new Thread(this).start();
    }
    int tick = 0;
    @Override
    public void run() {
        while(true){
            render();
            render2();
            //moveForwarddir(rotation);
            if(mouseX > 0){
            //    mouseX--;
                //mouseY++;
            }
            else{
            //    lines = buildLines(3);
            //    mouseX = 1000;
            //    mouseY = 400;
                
            }
            tick++;
        }
    }
    private LinkedList<Line2D.Float> buildLines(int num){
        LinkedList<Line2D.Float> lines = new LinkedList<>();
        for(int i = 0; i < num; i++){
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            lines.add(new Line2D.Float(x1, y1, x2, y2));
        }
        return lines;
    }
    public void render(){
        Vector guide = new Vector(mouseX, mouseY);
        double radians = (rotation) * Math.PI/180.0;
        guide.x += 30 * Math.sin(radians);
        guide.y += 30 * Math.cos(radians);
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null){canvas.createBufferStrategy(2);return;}
        Graphics g = bs.getDrawGraphics();
        
        
        g.setColor(Color.black);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        g.setColor(Color.green);
        g.drawLine((int) mouseX, (int) mouseY, (int)guide.x, (int)guide.y);
        
        g.setColor(Color.red);
        for(Line2D.Float line : lines){
            g.drawLine((int) line.x1, (int) line.y1, (int)line.x2, (int)line.y2);
        }
        g.setColor(Color.white);
        LinkedList<Line2D.Float> rays = calcRays(lines, mouseX, mouseY, res, 3000);
        for(Line2D.Float ray : rays){
            //g.drawLine((int) ray.x1, (int) ray.y1, (int)ray.x2, (int)ray.y2);
        //    g2.drawLine((int) ray.x2, (int) ray.y1, (int)ray.x1, (int)ray.y2);
        }
        
        g.dispose();
        
        bs.show();
        
    }
    public void render2(){
        
        BufferStrategy bs2 = canvas2.getBufferStrategy();
        if (bs2 == null){canvas2.createBufferStrategy(2);return;}
        Graphics g2 = bs2.getDrawGraphics();
        
        g2.setColor(Color.blue);
        g2.fillRect(0, 0, canvas2.getWidth(), canvas2.getHeight());
        
            int i = 1;
            for(int dist : distances){
                int c = 255 - dist;
                if(c > 255){
                    c = 255;
                }
                if(c < 0){
                    c = 0;
                }
                g2.setColor(new Color(0, c, 0));
                if(res <= WIDTH){
                    g2.fillRect(WIDTH / res * i, 0, WIDTH / res, HEIGHT);
                }
                else{
                    System.out.println("resolution too high, setting it to the max (screen width): " + WIDTH);
                    res = WIDTH;
                    break;
                }
                i++;
            }
        g2.dispose();
        
        bs2.show();
    }
    private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int mx, int my, int resolution, int maxDistance) {
        LinkedList<Line2D.Float> rays = new LinkedList<>();
        for(int i = 0; i < resolution; i++){
            //double dir = (2 * ((double)i / resolution)) + (((rotation)/(-2 * Math.PI)) / 3)/3;
            double dir = (Math.PI * 2) * ((double)i / resolution);
//            double dir = 1 * ((double)i / resolution) - rotation;
//            double dir = 2 * ((double)i / resolution) + (tick / 1000);
            float minDist = maxDistance;
            for(Line2D.Float line: lines){
                float dist = getRayCast(mx, my, mx+(float)Math.cos(dir) * maxDistance, my+(float)Math.sin(dir) * maxDistance, line.x1, line.y1, line.x2, line.y2);
                result = getRayCast(mx, my, mx+(float)Math.cos(dir) * maxDistance, my+(float)Math.sin(dir) * maxDistance, line.x1, line.y1, line.x2, line.y2);
                if(dist < minDist && dist > 0){
                    minDist = dist;
                }
            }
            rays.add( new Line2D.Float(mx, my, mx+(float)Math.cos(dir) * minDist, my+(float)Math.sin(dir) * minDist));
            distances[i] = (int) (minDist);
        }
        return rays;
    }
    private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int mx, int my, int resolution, int maxDistance, int layer) {
        LinkedList<Line2D.Float> rays = new LinkedList<>();
        for(int i = 0; i < resolution; i++){
            double dir = (Math.PI * 0.25) * ((double)i / resolution) - rotation;
//            double dir = 1 * ((double)i / resolution) - rotation;
//            double dir = 2 * ((double)i / resolution) + (tick / 1000);
            float minDist = maxDistance;
            for(Line2D.Float line: lines){
                float dist = getRayCast(mx, my, mx+(float)Math.cos(dir) * maxDistance, my+(float)Math.sin(dir) * maxDistance, line.x1, line.y1, line.x2, line.y2);
                result = getRayCast(mx, my, mx+(float)Math.cos(dir) * maxDistance, my+(float)Math.sin(dir) * maxDistance, line.x1, line.y1, line.x2, line.y2);
                if(dist < minDist && dist > 0){
                    minDist = dist;
                }
            }
            rays.add( new Line2D.Float(mx, my, mx+(float)Math.cos(dir) * minDist, my+(float)Math.sin(dir) * minDist));
            dists.get(layer)[i] = (int) (minDist);
        }
        return rays;
    }
    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
        float s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;
        s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;
        s2_y = p3_y - p2_y;

        float s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            float x = p0_x + (t * s1_x);
            float y = p0_y + (t * s1_y);


            return dist(p0_x, p0_y, x, y);
        }

        return -1; // No collision
    }
    float result;
    @Override
    public void mouseDragged(MouseEvent e) {
        //rotation = (float) (rotation - ((mouseX - e.getX()) * 0.001));
       // System.out.println("new rotation: " + rotation);
        System.out.println(mouseX + " "+ mouseY);
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //mouseX = e.getX();
        //mouseY = e.getY();
//    mouseX = 250;
//    mouseY = 250;
    }
    KeyEvent key;
    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Vector dir = new Vector(0, 0);
        dir.x = (float) Math.cos(Math.toRadians(rotation));
        dir.y = (float) Math.sin(Math.toRadians(rotation));
        //dir = Vector.norm((int)dir.x, (int)dir.y);
        //System.out.println(dir.represent());
        System.out.println(rotation);
        switch(e.getKeyCode()){
            case 'w':
                //mouseY = mouseY + 3;
                moveForward(toRadians(rotation));
                break;
            case 's':
                //mouseY = mouseY - 3;
                moveForward(toRadians(rotation + 180));
                break;
            case 'a':
                rotation = rotation + 1F;
                //moveForwarddir(rotation + 90);
                //mouseX = mouseX + 3;
                break;
            case 'd':
                rotation = rotation - 1F;
                //moveForwarddir(rotation - 90);
                //mouseX = mouseX - 3;
                break;
        }
        
                
    }
    void moveForward(double angle)
    {
        mouseX += 5 * Math.sin(angle);
        mouseY += 5 * Math.cos(angle);
    }
    void moveForwarddir(double angle)
    {
        double radians = (angle) * Math.PI/180.0;
        mouseX += 3 * Math.sin(radians);
        mouseY += 3 * Math.cos(radians);
    }
    double toRadians(double rotation)
    {
        return (rotation) * Math.PI/180.0;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
