/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author Jonnelafin
 */
class LegacyRenderer {
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
public class Renderer extends JPanel{
    double camx = 0;
    double camy = 0;
    
    private Color[][] master;
    public int blur = 0;
    
    int id = 0;
    
    LinkedList<imageWithId> images = new LinkedList<>();
    
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    LinkedList<newVectorLayer> layers = new LinkedList<>();
    float x = 15.34F;
    float y = 22.48F;
    public float factor = 20F / 2F;
    private int w = 0;
    private int h = 0;
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
                Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
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
    public Renderer(kick masterKick){
        this.masterkick = masterKick;
    }
    kick masterkick = null;

    /**
     *0: static, 1: follow player strictly
     */
    public int camMode = 1;
    @Override
    public void paintComponent(Graphics g) {
        camx = masterkick.Logic.cam.x;
        camy = masterkick.Logic.cam.y;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        super.paintComponent(g);
        
        if(sSi){
//            g.drawImage(full, 0, 0, w, h, this);
            g.setColor(Color.black);
            g.fillRect(0, 0, w, h);
            g.setColor(new Color(255, 255, 255));
            g.fillRect(w/(masterkick.loadingsteps+5), h/2-10, w/masterkick.loadingsteps*masterkick.loadingsteps, 10);
            g.setColor(new Color(0, 200, 0));
            g.fillRect(w/(masterkick.loadingsteps+5), h/2-10, w/masterkick.loadingsteps*masterkick.loading_completed, 10);
            g.setColor(Color.white);
            g.setFont(new Font("Verdana", Font.PLAIN, 34)); 
            g.drawString("Loading, step " + masterkick.loading_completed + " of " + masterkick.loadingsteps, w/3, h/2);
        }
        else{
            for(int layer : new Range(layers.size())){
                if(sSi){break;}
                newVectorLayer vL = layers.get(layer);
                for(int i : new Range(vL.containers.size())){
                    double rotation = vL.containers.get(i).rotation;
                    dVector rl = vL.containers.get(i).location;
                    Color c = vL.containers.get(i).color;
                    int size = vL.containers.get(i).size;
                    String imageloc = vL.containers.get(i).ImageName;
                    if(Objects.equals(imageloc, "")){
                        g.setColor(c);
                        if(camMode == 0){//static
                            g.fillRect((int)(rl.x * factor),(int) (rl.y *factor), (int) factor * size, (int) factor * size);
                        }
                        if (camMode == 1) {//follow camera
                            g.fillRect((int) ((rl.x - camx) * factor + (w / 2)), (int) ((rl.y - camy) * factor + (h / 2)), (int) factor * size, (int) factor * size);
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
                            buffer = new quickTools().colorImage(gImage.image, c.getRed(), c.getGreen(), c.getBlue(), 1F);
                            if(rotation != 0){
                                try{
                                    BufferedImage buffer2 = createRotated(buffer, rotation, gc).image;
                                    dVector scaleDiff = getImgScale(buffer2, buffer);
                                    scaleX = scaleDiff.x;
                                    scaleY = scaleDiff.y;
                                    buffer = buffer2;}
                                catch(Exception e){
                                    throw e;
                                }
                            }
                            double offsetX = scaleX;
                            double offsetY = scaleY;
                            
                            double xFrom = rl.x * factor - offsetX;
                            double yFrom = rl.y * factor - offsetY;
                            if(camMode == 1){
                                xFrom = (rl.x - camx) * factor  + (w/2) - offsetX;
                                yFrom = (rl.y - camy) * factor  + (h/2) - offsetY;
                            }
                            double xTo = factor * size + offsetX;
                            double yTo = factor * size + offsetY;
                            g.drawImage(buffer, (int)(xFrom),(int) (yFrom), (int) xTo, (int) yTo, this);
                        }catch(Exception e){
                            g.setColor(Color.MAGENTA);
                            g.fillRect((int)(rl.x-camx*factor + (w/2)),(int) (rl.y-camy*factor + (h/2)), (int) factor, (int) factor);
                            throw e;
                        }
                    }
                }
            }
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
    }
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
    
    public void update(LinkedList<renderContainer> containers, int layer){
        layers.get(layer).update(containers);
    }
    public void update(LinkedList<renderContainer> containers, newVectorLayer layer){
        layer.update(containers);
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
    public Renderer(){
        try {
            //master = quickTools.zero(master);
            full = ImageIO.read(new File(new directory().textures + "splash.png"));
        } catch (IOException ex) {
            
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private metaImage createRotated(BufferedImage image, double angle,GraphicsConfiguration gc) {
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
            return new metaImage(image, new dVector(mw, mh));
        }
        Graphics2D g = result.createGraphics();
        g.translate((neww - mw) / 2, (newh - mh) / 2);
        g.rotate(angle, mw / 2, mh / 2);
        g.drawRenderedImage(image, null);
        return new metaImage(result, new dVector(neww, newh));
    }
    public static dVector getImgScale(BufferedImage source, BufferedImage source2){
        double w1 = source.getWidth();
        double h1 = source.getHeight();
        double w2 = source2.getWidth();
        double h2 = source2.getHeight();
        return new dVector(w1/w2, h1/h2);
    }
    class metaImage{
        BufferedImage image;
        dVector newSizes;
        public metaImage(BufferedImage image, dVector sizes){
            this.image = image;
            this.newSizes = sizes;
        }
    }
}
class vectorLayer{
    LinkedList<Vector> points = new LinkedList<>();
    LinkedList<String> images = new LinkedList<>();
    LinkedList<Color> colors = new LinkedList<>();
    LinkedList<Integer> sizes = new LinkedList<>();
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
    
    public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, LinkedList<Integer> sizes, float factor){
        this.points = p;
        this.colors = c;
        this.images = images;
        this.factor = factor;
        this.sizes = sizes;
    }
}
class newVectorLayer{
    LinkedList<renderContainer> containers = new LinkedList<>();
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
    
    public void update(LinkedList<renderContainer> containers){
        this.containers = containers;
    }
}
class ImagePanel extends JPanel{

    public BufferedImage image;

    public ImagePanel() {
       try {                
          image = ImageIO.read(new File("image name and path"));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

}
class renderContainer implements java.io.Serializable{
    dVector location;
    String ImageName;
    Color color;
    int size;
    double rotation = 0;
    public renderContainer(dVector location, String ImageName, Color color, int size, double rotation){
        this.location = location;
        this.ImageName = ImageName;
        this.color = color;
        this.size = size;
        this.rotation = rotation;
    }
}
class imageWithId{
    BufferedImage image;
    int id;
    public imageWithId(BufferedImage image, int id){
        this.image = image;
        this.id = id;
    }
}