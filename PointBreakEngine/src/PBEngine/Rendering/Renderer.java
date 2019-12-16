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

package PBEngine.Rendering;

import JFUtils.Point2Int;
import JFUtils.Range;
import JFUtils.Point2D;
import JFUtils.quickTools;
import PBEngine.Supervisor;
import PBEngine.directory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import PBEngine.Rendering.extra.*;
import PBEngine.Rendering.core.*;
import java.util.Objects;
/**
 *
 * @author Jonnelafin
 */

public class Renderer extends JPanel{
    double camx = 0;
    double camy = 0;
    
    private Color[][] master;
    public int blur = 0;
    
    int id = 0;
    
    LinkedList<imageWithId> images = new LinkedList<>();
    
    LinkedList<Point2Int> points = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    LinkedList<newVectorLayer> layers = new LinkedList<>();
    float x = 15.34F;
    float y = 22.48F;
    public float factor = 13;
    private int w = 0;  public void setW(int newW){this.w = newW;} public int getW(){return w;}
    private int h = 0;  public void setH(int newH){this.h = newH;} public int getH(){return h;}
    public boolean sSi = false;
    Image image;
    BufferedImage buffer;
    public imageWithId getImage(String name){
        imageWithId imgF = getWithName(name);
        if(imgF != null){
            return imgF;
        }else{
            System.out.println("adding image to memory: " + name);
            File img = new File(name);
            BufferedImage image = null;
            try {
                image = ImageIO.read(img);
            } catch (IOException ex) {
                //Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
                //Logger.getGlobal().warning("COULD NOT LOAD IMAGE: " + ex);
            }
            this.images.add(new imageWithId(image, name.hashCode()));
            return new imageWithId(image, id);
        }
    }
    public imageWithId getWithName(String name){
        for(imageWithId i : images){
            if(i.id==name.hashCode()){
                return i;
            }
        }
        return null;
    }
    public void removeImage(int id){
        this.images.remove(id);
    }
    BufferedImage full = null;
    public Renderer(Supervisor masterKick){
        this.masterkick = masterKick;
        this.xd = masterKick.xd;
        this.yd = masterKick.yd;
    }
    Supervisor masterkick = null;

    /**
     *0: static, 1: follow camera strictly
     */
    public int camMode = 1;
    public boolean drawGrid = false;
    public Color gridColor = Color.white;
    public boolean dispEffectsEnabled = false;
    @Override
    public void paintComponent(Graphics g) {
        Dimension currentSize = null;
        try {
            currentSize = getParent().getSize();
            w = currentSize.width;
            h = currentSize.height;
            
        } catch (Exception e) {
            //Logger.getGlobal().warning("COULD NOT DETERMINE FRAME SIZE: USING THE LAST KNOWN");
            //w = getWidth();
            //h = getHeight();
            currentSize = new Dimension(w, h);
        }
        w = currentSize.width;
        h = currentSize.height;
        this.setSize(currentSize);
        camx = masterkick.Logic.cam.x;
        camy = masterkick.Logic.cam.y;
        
        super.paintComponent(g);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);
        
        if(sSi){
//            g.drawImage(full, 0, 0, w, h, this);
            
            g.setColor(new Color(150, 150, 150));
            g.fillRect((w-300)/(masterkick.loadingsteps), h/2-10, (w-300)/masterkick.loadingsteps*masterkick.loadingsteps, 10);
            g.setColor(new Color(0, 200, 0));
            g.fillRect((w-300)/(masterkick.loadingsteps), h/2-10, (w-300)/masterkick.loadingsteps*masterkick.loading_completed, 10);
            g.setColor(Color.white);
            g.setFont(new Font("Verdana", Font.PLAIN, 34)); 
            g.drawString("Loading, step " + masterkick.loading_completed + " of " + masterkick.loadingsteps, w/3, h/2);
            g.setColor(Color.green);
            /*g.setFont(new Font("Verdana", Font.PLAIN, 14));
            String latest = masterkick.latestConsole.split("\n")[masterkick.latestConsole.split("\n").length-1];
            g.drawString(latest, 0, h/40*38);*/
        }
        else{
            for(int layer : new Range(layers.size())){
                if(sSi){break;}
                newVectorLayer vL = layers.get(layer);
                for(int i : new Range(vL.containers.size())){
                    double rotation = vL.containers.get(i).rotation;
                    renderType shape = vL.containers.get(i).type;
                    Point2D rl = vL.containers.get(i).location;
                    Color c = vL.containers.get(i).color;
                    int size = vL.containers.get(i).size;
                    String imageloc = vL.containers.get(i).ImageName;
                    
                    int tick = masterkick.Logic.tickC;
                    Point2D effectOffSet = new Point2D(0, 0);
                    if(dispEffectsEnabled){
                        effectOffSet.x = Math.cos(rl.y + tick) / 5;
                    }
                    //effectOffSet.y = Math.sin(rl.x + tick);
                    
                    if(shape == renderType.circle){
                        //System.out.println("FOUND A CIRCLE!!! :D");
                        g2d.setColor(Color.RED);
                        if(camMode == 0){//static
                            g2d.fillOval((int)((rl.x + effectOffSet.x) * factor),(int) ((rl.y + effectOffSet.y) *factor), (int) factor * size, (int) factor * size);
                            //g.setColor(Color.gray);
                            //g.fillRect((int)((rl.x + effectOffSet.x) * factor),(int) ((rl.y + effectOffSet.y) *factor), (int) factor * size, (int) factor * size);
                        }
                        if (camMode == 1) {//follow a camera
                            g2d.fillOval((int)(((rl.x + effectOffSet.x) - camx) * factor + (w / 2)),(int) (((rl.y + effectOffSet.y) -camy) *factor + (h / 2)), (int) factor * size, (int) factor * size);
                            //g.fillRect((int) (((rl.x + effectOffSet.x) - camx) * factor + (w / 2)), (int) (((rl.y + effectOffSet.y) - camy) * factor + (h / 2)), (int) factor * size, (int) factor * size);
                        }
                    }
                    else if(imageloc.equals("")){
                        g.setColor(c);
                        if(camMode == 0){//static
                            g.fillRect((int)((rl.x + effectOffSet.x) * factor),(int) ((rl.y + effectOffSet.y) *factor), (int) factor * size, (int) factor * size);
                        }
                        if (camMode == 1) {//follow a camera
                            g.fillRect((int) (((rl.x + effectOffSet.x) - camx) * factor + (w / 2)), (int) (((rl.y + effectOffSet.y) - camy) * factor + (h / 2)), (int) factor * size, (int) factor * size);
                        }
                    }
                    else{
                        try{
    /*                        if(imaged == 999999999){
                                System.out.println("adding to system: " + vL.containers.get(i).ImageName);
                                File img = new File(imageloc);
                                BufferedImage image = ImageIO.read(img);
                                addImage(image, id);
                                vL.containers.get(i).imageId = id;
                                imaged = getImageIndex(vL.containers.get(i).imageId);
                                id++;
                            }*/

                                                         //0.75F
                            double scaleX = 1;
                            double scaleY = 1;
                            imageWithId gImage = getImage(imageloc);
                            buffer = gImage.image;
                            //buffer = new quickTools().colorImage(gImage.image, c.getRed(), c.getGreen(), c.getBlue(), 0.5F);
                            if(rotation != 0){
                                try{
                                    BufferedImage buffer2 = createRotated(buffer, rotation, gc).image;
                                    Point2D scaleDiff = getImgScale(buffer2, buffer);
                                    scaleX = scaleDiff.x;
                                    scaleY = scaleDiff.y;
                                    buffer = buffer2;}
                                catch(Exception e){
                                    //throw e;
                                }
                            }
                            double offsetX = scaleX;
                            double offsetY = scaleY;
                            
                            //offsetX = 0;
                            //offsetY = 0;
                            
                            
                            
                            double xFrom = (rl.x + effectOffSet.x) * factor - offsetX;
                            double yFrom = (rl.y + effectOffSet.y) * factor - offsetY;
                            if(camMode == 1){
                                xFrom = (rl.x - camx + effectOffSet.x) * factor  + (w/2) - offsetX;
                                yFrom = (rl.y - camy + effectOffSet.y) * factor  + (h/2) - offsetY;
                            }
                            double xTo = factor * size + offsetX;
                            double yTo = factor * size + offsetY;
                            
                            /*xFrom = xFrom + effectOffSet.x;
                            yFrom = yFrom + effectOffSet.y;
                            xTo = xTo + effectOffSet.x;
                            yTo = yTo + effectOffSet.y;*/
                            
                            g2d.drawImage(buffer, (int)(xFrom),(int) (yFrom), (int) xTo, (int) yTo, this);
                        }catch(Exception e){
                            g.setColor(Color.MAGENTA);
                            g.fillRect((int)(rl.x-camx*factor + (w/2)),(int) (rl.y-camy*factor + (h/2)), (int) factor, (int) factor);
                            throw e;
                        }
                    }
                }
            }
            
        }
        
        if(drawGrid){
            int XO = 0;
            int YO = 0;
            if(camMode == 1){
                XO = (int) camx;
                YO = (int) camy;
            }
            for(int i=0;i<(w/factor);i++){
                for(int z=0;z<(h/factor);z++){
                    g.setColor(gridColor);
                    g.drawRect((int) (i*factor), (int) (z*factor), (int) factor, (int) factor);
                }
            }
            
        }
        g.setColor(Color.green);
        g.setFont(new Font("Verdana", Font.PLAIN, 14)); 
        try {
            String latest = masterkick.latestConsole.split("\n")[masterkick.latestConsole.split("\n").length - 1];
            g.drawString(latest, 0, h / 40 * 38);
            oldC = latest;
        } catch (Exception e) {
            g.drawString("*: "+oldC, 0, h / 40 * 38);
        }
        /*
        float[][] rs = quickTools.separateRGB(master, w, h).get(0);
        float[][] gs = quickTools.separateRGB(master, w, h).get(1);
        float[][] bs = quickTools.separateRGB(master, w, h).get(2);
        //rs = quickTools.blur(rs, w, h, blur);
        //gs = quickTools.blur(rs, w, h, blur);
        //bs = quickTools.blur(rs, w, h, blur);
        //master = quickTools.parseColor(w, h, rs, gs, bs);
        for(int x : new Range(w)){
            for(int y : new Range(h)){
                Vector rm = new Vector(x, y);
                Color c = master[x][y];
                //g.drawRect((int)(r.x*factor),(int) (r.y*factor), (int) factor, (int) factor);
            }
        }
        */
        if(showMap){
            //g.setColor(Color.WHITE);
            ////g.drawRect(0, 0, WIDTH, HEIGHT);
            //g.fillRect(WIDTH/6, HEIGHT/6, WIDTH-(WIDTH/6), HEIGHT-(HEIGHT/6));
        }
    }
    public boolean showMap = true;
    private String oldC = "";
    public void setImage(String mage) throws IOException{
        File img = new File(mage);
        BufferedImage image = ImageIO.read(img);
        this.image = image;
    }
/*    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, LinkedList<Integer> sizes, float f, int layer){
        newVectorLayer tmp = layers.get(layer);
        tmp.update(p, c, images, sizes, factor);
    }
    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, LinkedList<Integer> sizes, float f, vectorLayer layer){
        layer.update(p, c, images, sizes, factor);
    }*/
    
    public void update(LinkedList<renderContainer> containers, int layer, int tick){
        layers.get(layer).update(containers,tick);
    }
    public void update(LinkedList<renderContainer> containers, newVectorLayer layer, int tick){
        layer.update(containers,tick);
    }
    public void init(double w, double h, int num_layers, boolean showStartingImage){
        this.w = (int) w;
        this.h = (int) h;
        this.setSize(this.w, this.h);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(this.w,this.h));
        this.setOpaque(true);
        this.setBackground(Color.BLACK);
        master = new Color[this.w][this.h];
        master = quickTools.zero(master);
        for(int i : new Range(num_layers)){
            this.addLayer(i, "Layer " + String.valueOf(i), 0);
        }
        this.layers.get(0).blur = 3; //enable blur by default
        //this.setBorder();
        this.sSi = showStartingImage;
    }
    public void addLayer(int position, String title, int blur){
        newVectorLayer tmp = new newVectorLayer();
        tmp.init(title, blur);
        this.layers.add(position, tmp);
    }
    int xd = 0;
    int yd = 0;
    public Renderer(int xd, int yd){
        this.xd = xd;
        this.yd = yd;
        try {
            //master = quickTools.zero(master);
            full = ImageIO.read(new File(new directory().textures + "splash.png"));
        } catch (IOException ex) {
            
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private metaImage createRotated(BufferedImage image, double angle,GraphicsConfiguration gc) {
        if(Objects.isNull(image)){
            System.out.println("COULD NOT ROTATE THE IMAGE, IT IS NULL");
            return null;
        }
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int mw = image.getWidth(), mh = image.getHeight();
        int neww;
        neww = (int) Math.floor(mw * cos + mh * sin);
        int newh = (int) Math.floor(mh* cos + mw * sin);
        int transparency = image.getColorModel().getTransparency();
        //BufferedImage result = gc.createCompatibleImage(neww, newh, transparency);
        BufferedImage result = null;
        try {
            result = new BufferedImage(neww, newh, BufferedImage.TYPE_4BYTE_ABGR);
        } catch (Exception e) {
            return new metaImage(image, new Point2D(mw, mh));
        }
        Graphics2D g = result.createGraphics();
        g.translate((neww - mw) / 2, (newh - mh) / 2);
        g.rotate(angle, mw / 2, mh / 2);
        g.drawRenderedImage(image, null);
        return new metaImage(result, new Point2D(neww, newh));
    }
    public static Point2D getImgScale(BufferedImage source, BufferedImage source2){
        double w1 = source.getWidth();
        double h1 = source.getHeight();
        double w2 = source2.getWidth();
        double h2 = source2.getHeight();
        return new Point2D(w1/w2, h1/h2);
    }
    
}




class msg{
    int life = 0;
    String msg;
}









