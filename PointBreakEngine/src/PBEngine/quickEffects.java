/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonnelafin
 */
public class quickEffects {
    static dVector[] dirs = new dVector[8];
    public quickEffects(){
            dirs[0] = new dVector(0.0F,1.0F);
            dirs[1] = new dVector(1.0F,1.0F);
            dirs[2] = new dVector(1.0F,0.0F);
            dirs[3] = new dVector(-1.0F,-1.0F);
            dirs[4] = new dVector(0F,-1.0F);
            dirs[5] = new dVector(-1.0F,-1.0F);
            dirs[6] = new dVector(-1.0F,0F);
            dirs[7] = new dVector(-1.0F,1F);
    }
    public static float[][] blur(float[][] sauce, int w, int h, int times){
        float[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h);
        }
        return out;
        
    }
    public static float[][] blur(float[][] sauce, int w, int h, int times, int divisions){
        float[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h, divisions);
        }
        return out;
        
    }
    public static float[][] blur(float[][] sauce, int w, int h, int times,float factor){
        float[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h);
        }
        return out;
        
    }
    private static float[][] blurOnce(float[][] sauce, int w, int h){
        int x = 0;
        int y = 0;
        x = 0;y = 0;
        float sum = 0;
        float[][] out = new float[w][h];
        for(float[] lane : sauce){
            for(float lany : lane){
                sum = sauce[x][y];
                for(dVector i : dirs){
                    try{
                        sum = sum + sauce[x + (int) i.x][y + (int) i.y];
                    }catch(Exception e){}
                }
                try{out[x][y] = sum / 9;}catch(Exception e){System.out.println(new Vector(x,y).represent());}
                y = y + 1;
            }
            x++;
            y=0;
        }
        return(out);
    }
    private static float[][] blurOnce(float[][] sauce, int w, int h, int divisions){
        int x = 0;
        int y = 0;
        x = 0;y = 0;
        float sum = 0;
        float[][] out = new float[w*divisions][h*divisions];
        for(float[] lane : sauce){
            for(float lany : lane){
                sum = sauce[x][y];
                for(dVector i : dirs){
                    try{
                        sum = sum + sauce[x + (int) i.x][y + (int) i.y];
                    }catch(Exception e){}
                }
                try{out[x][y] = sum / 9;}catch(Exception e){System.out.println(new Vector(x,y).represent());}
                y = y + 1;
            }
            x++;
            y=0;
        }
        return(out);
    }
    public static Color[][] zero(Color[][] in){
        Color[][] out = in;
        for(Color[] lane : out){
            for(Color i : lane){
                i = new Color(0,0,0);
            }
        }
        return out;
    }
    
    public static void alert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    
    
    public float[][] getR(int xd, int yd, Color[][] colors){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getRed();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
    public float[][] getG(int xd, int yd, Color[][] colors){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getGreen();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
    public float[][] getB(int xd, int yd, Color[][] colors){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getBlue();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
    
    public static Color[][] parseColor(int w, int h, float[][] r, float[][] g, float[][] b){
        Color[][] out = new Color[w][h];
        out = quickEffects.zero(out);
        LinkedList<float[][]> rgb = new LinkedList<>();
        rgb.add(r);rgb.add(g);rgb.add(b);
        int xp = 0, yp = 0;
        for(Color[] lane : out){
            for(Color i : lane){
                int ri = (int) r[xp][yp];
                int gi = (int) g[xp][yp];
                int bi = (int) b[xp][yp];
                i = new Color(ri,gi,bi);
                yp++;
            }
            xp++;
            yp = 0;
        }
        return out;
    }
    public static LinkedList<float[][]> separateRGB(Color[][] sauce, int w, int h){
        LinkedList<float[][]> out = new LinkedList<>();
        float[][] r = new float[w][h];
        float[][] g = new float[w][h];
        float[][] b = new float[w][h];
        int x = 0, y = 0;
        for(Color [] lane : sauce){
            for(Color c : lane){
                try{r[x][y] = c.getRed();}catch(Exception e){r[x][y] = 0F;}
                try{g[x][y] = c.getGreen();}catch(Exception e){g[x][y] = 0F;}
                try{b[x][y] = c.getBlue();}catch(Exception e){b[x][y] = 0F;}
                y++;
            }
            x++;
            y = 0;
        }
        out.addLast(r);out.addLast(g);out.addLast(b);
        return out;
    }
    public BufferedImage colorImage(BufferedImage loadImg, int red, int green, int blue, float alpha) {
        /*
        BufferedImage img = new BufferedImage(loadImg.getWidth(), loadImg.getHeight(),
            BufferedImage.TRANSLUCENT);
        Graphics2D graphics = img.createGraphics(); 
        Color newColor = new Color(red, green, blue, 0  alpha needs to be zero );
        graphics.setXORMode(newColor);
        graphics.drawImage(loadImg, null, 0, 0);
        graphics.dispose();
        return img;
        */
        Color rgb = new Color(red,green,blue);
        BufferedImage mask = generateMask(loadImg, rgb, alpha);
        BufferedImage out = tint(loadImg, mask);
        return out;
    }
    public static BufferedImage generateMask(BufferedImage imgSource, Color color, float alpha) {
        int imgWidth = imgSource.getWidth();
        int imgHeight = imgSource.getHeight();

        BufferedImage imgMask = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = imgMask.createGraphics();
        applyQualityRenderingHints(g2);

        g2.drawImage(imgSource, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
        g2.setColor(color);

        g2.fillRect(0, 0, imgSource.getWidth(), imgSource.getHeight());
        g2.dispose();

        return imgMask;
    }

    public BufferedImage tint(BufferedImage master, BufferedImage tint) {
        int imgWidth = master.getWidth();
        int imgHeight = master.getHeight();

        BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight, Transparency.TRANSLUCENT);
        Graphics2D g2 = tinted.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(master, 0, 0, null);
        g2.drawImage(tint, 0, 0, null);
        g2.dispose();

        return tinted;
    }
    public static BufferedImage createCompatibleImage(int width, int height, int transparency) {
        BufferedImage image = getGraphicsConfiguration().createCompatibleImage(width, height, transparency);
        image.coerceData(true);
        return image;
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
    public static GraphicsConfiguration getGraphicsConfiguration() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
}
