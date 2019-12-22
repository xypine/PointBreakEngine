/*
 * The MIT License
 *
 * Copyright 2019 eliase.
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
package PBEngine.Rendering.legacy;

import JFUtils.point.Point2D;
import PBEngine.colorParser;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Elias Eskelinen (Jonnelafin)e
 */
public class LegacyRenderer {
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
    Point2D cSize;
    
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
        cSize = new Point2D(x, y);
        //canvas.setSize(x, y);
        //canvas.setMaximumSize(new Dimension(x,y));
        //canvas.setMinimumSize(new Dimension(x,y));
    }
    
    public String[][] gets(){return(this.space);}
    public Color[][] getc(){return(this.colors);}
    
    public void fill(String goal, Color gc, String style){
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
    public void change(int locy,int locx,String to, Color c, String style){
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