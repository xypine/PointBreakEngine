/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class gridEffects {
    static dVector[] dirs = new dVector[8];
    public gridEffects(){
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
    public static Color[][] zero(Color[][] in){
        Color[][] out = in;
        for(Color[] lane : out){
            for(Color i : lane){
                i = new Color(0,0,0);
            }
        }
        return out;
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
        out = gridEffects.zero(out);
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
}
