/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
    private Color[][] master;
    public int blur = 0;
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    LinkedList<vectorLayer> layers = new LinkedList<>();
    float x = 15.34F;
    float y = 22.48F;
    float factor = 20F / 1F;
    private int w = 0;
    private int h = 0;
    public boolean sSi = false;
    Image image;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //master = quickEffects.zero(master);
        if(sSi){
            g.drawImage(image, w, h, this);
        }
        for(int layer : new Range(layers.size())){
            if(sSi){break;}
            vectorLayer vL = layers.get(layer);
            for(int i : new Range(vL.points.size())){
                Vector rl = vL.points.get(i);
                Color c = vL.colors.get(i);
                String imageloc = vL.images.get(i);
                if(Objects.equals(imageloc, new String(""))){
                    g.setColor(c);
                    g.fillRect((int)(rl.x*factor),(int) (rl.y*factor), (int) factor, (int) factor);
                }
                else{
                    try{
                        File img = new File(imageloc);
                        BufferedImage image = ImageIO.read(img);
                        image = new quickEffects().colorImage(image, c.getRed(), c.getGreen(), c.getBlue(), 0.75F);
                        g.drawImage(image, (int)(rl.x*factor),(int) (rl.y*factor), (int) factor, (int) factor, this);
                    }catch(Exception e){
                        g.setColor(Color.MAGENTA);
                        g.fillRect((int)(rl.x*factor),(int) (rl.y*factor), (int) factor, (int) factor);
                    }
                }
            }
        }
        /*
        float[][] rs = quickEffects.separateRGB(master, w, h).get(0);
        float[][] gs = quickEffects.separateRGB(master, w, h).get(1);
        float[][] bs = quickEffects.separateRGB(master, w, h).get(2);
        //rs = quickEffects.blur(rs, w, h, blur);
        //gs = quickEffects.blur(rs, w, h, blur);
        //bs = quickEffects.blur(rs, w, h, blur);
        //master = quickEffects.parseColor(w, h, rs, gs, bs);
        for(int x : new Range(w)){
            for(int y : new Range(h)){
                Vector rm = new Vector(x, y);
                Color c = master[x][y];
                //g.drawRect((int)(r.x*factor),(int) (r.y*factor), (int) factor, (int) factor);
            }
        }
        */
    }
    public void setImage(String mage) throws IOException{
        File img = new File(mage);
        BufferedImage image = ImageIO.read(img);
        this.image = image;
    }
    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, float f, int layer){
        vectorLayer tmp = layers.get(layer);
        tmp.update(p, c, images, factor);
    }
    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, float f, vectorLayer layer){
        layer.update(p, c, images, factor);
    }
    public void init(int w, int h, int num_layers, boolean showStartingImage){
        this.w = w;
        this.h = h;
        this.setSize(w, h);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(w,h));
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        master = new Color[w][h];
        master = quickEffects.zero(master);
        for(int i : new Range(num_layers)){
            this.addLayer(i, "Layer " + String.valueOf(i), 0);
        }
        this.layers.get(0).blur = 3; //enable blur by default
        //this.setBorder();
    }
    public void addLayer(int position, String title, int blur){
        vectorLayer tmp = new vectorLayer();
        tmp.init(title, blur);
        this.layers.add(position, tmp);
    }
}
class vectorLayer{
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<String> images = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    public int blur;
    float x = 15.34F;
    float y = 22.48F;
    float factor = 2F;
    public int w = 0;
    public int h = 0;
    public String title = "";
    public void init(String title, int blur){
        this.title = title;
    }
    
    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, float factor){
        this.points = p;
        this.colors = c;
        this.images = images;
        this.factor = factor;
    }
}

