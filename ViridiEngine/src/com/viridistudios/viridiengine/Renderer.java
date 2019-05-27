/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.Math.round;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 *
 * @author Jonnelafin
 */
public class Renderer {
    private int a;
    private int b;
    private String[][] space;
    private Color[][] colors;
    //public vCanvas canvas = new vCanvas();
    private int y;
    private int x;
    private JFrame frame;
    colorParser cP;
    int cW;
    int cH;
    dVector cSize;
    
    public void init(int x, int y, JFrame f){
        this.frame = f;
        cP = new colorParser();
        this.x = y;
        this.y = x;
        this.space = new String[y][x];
//        this.colors = new Color[x][y];
        this.a = (space.length);
        this.b = (space[0].length);
//        colorFill(Color.white);
        fill("█", Color.black, "null");
        initVector(767, 562);
    }
    public void initVector(int x, int y){
        cSize = new dVector(x, y);
        //canvas.setSize(x, y);
        //canvas.setMaximumSize(new Dimension(x,y));
        //canvas.setMinimumSize(new Dimension(x,y));
    }
    
    public String[][] gets(){return(this.space);}
    public Color[][] getc(){return(this.colors);}
    
    void fill(String goal, Color gc, String style){
        String[][] tmp;
        tmp = this.space;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.space[c][u] = cP.parse("█",  gc, style);
                
            }
        }
//        this.space = tmp;
    }
    /*void vectorFill(Color co, int vec){
        //BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.red);
        g.drawRect(0, 0, 767, 562);
        g.setColor(co);
        g.clearRect(0, 0, 767, 562);
        //canvas.setSize(767, 562);
        g.dispose();
        bs.show();
    }*/
    void colorFill(Color goal){
        Color[][] tmp;
        tmp = this.colors;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.colors[c][u] = goal;
            }
        }
    }
    void scan(int fx,int fy,int tx,int ty){
        
    }
    void change(int locy,int locx,String to, Color c, String style){
        try{
            this.space[locx][locy] = cP.parse(to,  c, style);
            
            //this.colors[locx][locy] = c;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    void vChange(float locx,float locy, Color c){
        try{
            //canvas.render(locx, locy, c);
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" + locx + "): ");
            System.out.println(e);
        }
    }
    
    public int sizey(){return(this.x);}
    public int sizex(){return(this.y);}
    
    
    
    

    

    
}
class vectorArea extends JPanel{
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i : new Range(points.size())){
            Vector r = points.get(i);
            Color c = colors.get(i);
            g.setColor(c);
            //g.fillRect((int)(r.x*15.34F),(int) (r.y*22.48F), (int) 15.34F, (int) 22.48F);
            g.drawRect((int)(r.x*15.34F),(int) (r.y*22.48F), (int) 15.34F, (int) 22.48F);
        }
    }
    public void update(LinkedList<Vector> p,LinkedList<Color> c){
        this.points = p;
        this.colors = c;
    }
    public void init(int w, int h){
        this.setSize(w, h);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(w,h));
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        //this.setBorder();
    }
}

