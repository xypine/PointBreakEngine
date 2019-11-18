/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jfUtils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonnelafin
 */
public class quickTools {
    public static int levelUP = 0;
    public static int levelRight = 1;
    public static int levelDown = 2;
    public static int levelLeft = 3;
    
    public static dVector[] vectorDirs4 = {new dVector(0, 1), new dVector(1, 0), new dVector(0, -1), new dVector(-1, 0)}; 
    
    static dVector[] dirs = new dVector[8];
    public quickTools(){
            dirs[0] = new dVector(0.0F,1.0F);
            dirs[1] = new dVector(1.0F,1.0F);
            dirs[2] = new dVector(1.0F,0.0F);
            dirs[3] = new dVector(-1.0F,-1.0F);
            dirs[4] = new dVector(0F,-1.0F);
            dirs[5] = new dVector(-1.0F,-1.0F);
            dirs[6] = new dVector(-1.0F,0F);
            dirs[7] = new dVector(-1.0F,1F);
    }
    public static double[][] blur(double[][] sauce, int w, int h, int times){
        double[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h);
        }
        return out;
        
    }
    public static double[][] blur(double[][] sauce, int w, int h, int times, int divisions){
        double[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h, divisions);
        }
        return out;
        
    }
    public static double[][] blur(double[][] sauce, int w, int h, int times,float factor){
        double[][] out = sauce;
        for(int i : new Range(times)){
            out = blurOnce(out, w, h);
        }
        return out;
        
    }
    private static double[][] blurOnce(double[][] sauce, int w, int h){
        int x = 0;
        int y = 0;
        x = 0;y = 0;
        double sum = 0;
        int calcd = 0;
        int succeede = 9;
        double[][] out = new double[w][h];
        for(double[] lane : sauce){
            for(double lany : lane){
                sum = sauce[x][y];
                calcd = 0;
                for(dVector i : dirs){
                    try{
                        calcd++;
                        sum = sum + sauce[x + (int) i.x][y + (int) i.y];
                    }catch(Exception e){succeede--;/*calcd++;sum = sum + (sum / calcd);*/}
                }
                out[x][y] = 0F;
                try{out[x][y] = sum / 9;}catch(Exception e){throw e;}
                y = y + 1;
            }
            x++;
            y=0;
        }
        return(out);
    }
    private static double[][] blurOnce(double[][] sauce, int w, int h, int divisions){
        int x = 0;
        int y = 0;
        x = 0;y = 0;
        double sum = 0;
        double[][] out = new double[w*divisions][h*divisions];
        for(double[] lane : sauce){
            for(double lany : lane){
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
    public static Color[][] black(int w, int h){
        Color[][] o = new Color[w][h];
        for(Color [] lane : o){
            for (Color c : lane){
                c = Color.black;
            }
        }
        return o;
    }
    
    public static void alert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
    public static void alert(String from, String msg){
        JOptionPane.showMessageDialog(null, msg, from, 0);
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
    
    public static Color[][] parseColor(int w, int h, double[][] r, double[][] g, double[][] b){
        Color[][] out = quickTools.black(w, h);
        LinkedList<double[][]> rgb = new LinkedList<>();
        rgb.add(r);rgb.add(g);rgb.add(b);
        int xp = 0, yp = 0;
        for(Color[] lane : out){
            for(Color i : lane){
                int ri = (int) r[xp][yp];
                int gi = (int) g[xp][yp];
                int bi = (int) b[xp][yp];
                i = new Color(ri,gi,bi);
                out[xp][yp] = i;
                yp++;
            }
            xp++;
            yp = 0;
        }
        return out;
    }
    public static LinkedList<double[][]> separateRGB(Color[][] sauce, int w, int h){
        LinkedList<double[][]> out = new LinkedList<>();
        double[][] r = new double[w][h];
        double[][] g = new double[w][h];
        double[][] b = new double[w][h];
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
        BufferedImage out = multiply(loadImg, mask);
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
    public BufferedImage multiply(BufferedImage master, BufferedImage tint) {
        int imgWidth = master.getWidth();
        int imgHeight = master.getHeight();

        BufferedImage tinted = createCompatibleImage(imgWidth, imgHeight, Transparency.OPAQUE);
        Graphics2D g2 = tinted.createGraphics();
        applyQualityRenderingHints(g2);
        g2.drawImage(master, 0, 0, null);
        g2.setComposite(MultiplyComposite.Multiply);
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
    
    public LinkedList<Object> log = new LinkedList<>();
    public void println(Object in){
        log.add(in);
        System.out.println(in);
    }
    public void print(Object in){
        log.add(in);
        System.out.print(in);
    }
    public static int[][] rotateCW(int[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        return ret;
    }
    public static String[][] rotateCW(String[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        String[][] ret = new String[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        return ret;
    }
    public static String multiplyString(String string, int times){
        String out = "";
        for(int i : new Range(times)){
            out = out + string;
        }
        return out;
    }
    public static Integer[][] clone2d(Integer[][] original){
        Integer[][] cloned = original.clone();
        for(int i : new Range(original.length)){
            cloned[i] = original[i].clone();
        }
        return cloned;
    }
    public static String[][] clone2d(String[][] original){
        String[][] cloned = original.clone();
        for(int i : new Range(original.length)){
            cloned[i] = original[i].clone();
        }
        return cloned;
    }
    public static Color[][] clone2d(Color[][] original){
        Color[][] cloned = original.clone();
        for(int i : new Range(original.length)){
            cloned[i] = original[i].clone();
        }
        return cloned;
    }
    
    public static Integer askInt(String message){
        //JOptionPane pane = new JOptionPane("", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null);
        int choosed = JOptionPane.showConfirmDialog(null, message);
        return choosed;
    }
    public static Integer askInt_slider(String title, String msg, int spacing, int min, int max){
        int chosen = (int) jfUtils.JSliderOnJOptionPane.Ask(title, msg, spacing, min, max);
        return chosen;
    }
}
