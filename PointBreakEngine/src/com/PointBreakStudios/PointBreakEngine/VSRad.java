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
 * @author elias
 */
public class VSRad {
    public int type = 0;
    //type 1 = baked, type 0 = realtime
    public Color color = new Color(0,0,0);
    public float[][] grid;
    public LinkedList<float[][]> rays = new LinkedList<>();
    public int resolution = 360;
    public float shutter = 0.01F;
    private Vector source;
    public int width, height;
    dVector[] directions = new dVector[resolution];
    private objectManager oM;
    private radiosity demo;
    public VSRad(objectManager oM, Color c, int type){
        this.oM = oM;
        this.color = c;
        this.type = type;
    }
    public void init(int w, int h){
        int state = 0;
        grid = new float[w][h];
        this.width = w;
        this.height = h;
        dVector cur = new dVector(0,1);
        System.out.println(dVector.add(cur, new dVector(0.1, -0.1)).represent());
        Range r = new Range(resolution);
        for(int i : r){
            if(state == 0){
                if(cur.x < 1){
                    //System.out.println("Calculating directions phase 0: " + cur.represent());
                    directions[i] = cur;
                    cur = dVector.add(cur, new dVector(0.1/(resolution * shutter), -0.1/(resolution * shutter)));
                }
                else{
                    state = 1;
                }
            }
            if(state == 1){
                if(cur.x > 0){
                    //System.out.println("Calculating directions phase 1: " + cur.represent());
                    directions[i] = cur;
                    cur = dVector.add(cur, new dVector(-0.1/(resolution * shutter), -0.1/(resolution * shutter)));
                }
                else{
                    state = 2;
                }
            }
            if(state == 2){
                if(cur.x > -1){
                    //System.out.println("Calculating directions phase 2: " + cur.represent());
                    directions[i] = cur;
                    cur = dVector.add(cur, new dVector(-0.1/(resolution * shutter), 0.1/(resolution * shutter)));
                }
                else{
                    state = 3;
                }
            }
            if(state == 3){
                if(cur.y < 1){
                    //System.out.println("Calculating directions phase 3: " + cur.represent());
                    directions[i] = cur;
                    cur = dVector.add(cur, new dVector(0.1/(resolution * shutter), 0.1/(resolution * shutter)));
                }
                else{
                    state = 4; //Finished
                    //System.out.println("Calculating directions phase 4: " + cur.represent());
                    System.out.println("finished calculating directions");
                }
            }
            //directions[i] = new Vector(0,1);
            //directions[i] = new Vector(0.1F,1F);
            //directions[i] = new Vector(0,1);
            fill(0F);
        }
    }
    
    public void fill(float to){
        grid = new float[width][height];
        for(float[] arr: grid){
            for(float i : arr){
                i = to;
            }
        }
    }
    private boolean inside = true;
    dVector cursor = new dVector(0,0);
    int done = 0;
    int hits = 0;
    public float s = 0;
    int requests = 0;
    public float sum = 0;
    dVector dir;
    public dVector from;
    public float lastS;
    public void calculate(dVector from, float strenght, String ignore){
        this.from = from;
        this.lastS = strenght;
        float[][] ray = new float[width][height];
        requests++;
        for(dVector d: directions){
            //System.out.println("direction " + d.represent());
        }
        //System.out.println("Calculating rays...");
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        fill(0);
        s = strenght;
        float bOut = 0.999F;
        float decay = 0.999F;
        float nullDecay = 0.9975F;
        if(this.type == 0){
            bOut = 0;
            decay = 0.99F;
            nullDecay = 0.995F;
        }
        for(int i : new Range(resolution)){
            //System.out.println(done / resolution * 100 + "%: ");
            int hp = 1;
            int hits =0;
            inside = true;
            
            dir = directions[i];
            while(inside){
                //System.out.println(s);
                //demo.setTitle("ViridiEngine radiosity "+s);
                try{
                    //System.out.print(hits + " ");
                    cursor = new dVector(cursor.x + dir.x, cursor.y + dir.y);
                    if(cursor.y > height-1){
                        cursor.y = height - 1;
                        dir.y = dir.y * - 1;
                        s = s * bOut;
                    }
                    if(cursor.y < 0){
                        cursor.y = 0;
                        dir.y = dir.y * - 1;
                        s = s * bOut;
                    }
                    if(cursor.x > width-1){
                        cursor.x = width - 1;
                        dir.x = dir.x * - 1;
                        s = s * bOut;
                    }
                    if(cursor.x < 0){
                        cursor.x = 0;
                        dir.x = dir.x * - 1;
                        s = s * bOut;
                    }
                    if(oM.colliding((int) cursor.x, (int) cursor.y, ignore)){
                        grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                        dir.x = dir.x * -1;
                        dir.y = dir.y * -1;
                        cursor = new dVector(cursor.x + dir.x, cursor.y + dir.y);
                        s = s * decay;
                        //System.out.println(dVector.round(cursor).represent() + " " + requests);
                    }
                    grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                    sum = sum + s;
                }
                catch(Exception e){
                    
                    inside = false;
                    //bounce(hp, dir, s);
                }
                s = s * nullDecay;
                if(s <= 0.0001){
                    inside = false;
                }
            }
            cursor = from;
            s = strenght;
            inside = true;
            done++;
        }
        //rays.add(ray);
        //System.out.println("rays calculated");
    }
}
class VSRadManager{
    public Color[][] colors;
    public LinkedList<VSRad> VSRad = new LinkedList<>();
    private VSRad director;
    private int w, h;
    private objectManager oM;
    private radiosity demo;
    private dVector[] directions;
    private float[][] last;
    public VSRadManager(int w, int h,objectManager oM){
        this.w = w;
        this.h = h;
        this.oM = oM;
        last = new float[w][h];
        director = new VSRad(this.oM, Color.BLACK, 1999);
        director.init(this.w, this.h);
        this.directions = director.directions;
        this.colors = new Color[w][h];
    }
    public void add(int x, int y, float s, Color color, int type){
        VSRad tmp = new VSRad(oM, color, type);        
        tmp.init(w, h);
        tmp.calculate(new dVector(x,y), s, "null");
        System.out.println(tmp.sum);
        this.VSRad.add(tmp);
    }
    int lasthash = 0;
    public float[][] read(){
        float[][] sum = new float[w][h];
        int xp = 0, yp = 0;
        for(VSRad ray : VSRad){
            for(float[] line : ray.grid){
                for(float i : line){
                    if(i > 0){
                        sum[xp][yp] = sum[xp][yp] + i;
                        //System.out.println(colors[xp][yp] + " " + ray.color.getRGB());
                        float r,g,b;
                        try{r = colors[xp][yp].getRed() + (ray.color.getRed() * ray.grid[xp][yp]);}catch(Exception e){r = ray.color.getRed() * ray.grid[xp][yp];}
                        try{g = colors[xp][yp].getGreen()+ (ray.color.getGreen() * ray.grid[xp][yp]);}catch(Exception e){g = ray.color.getGreen() * ray.grid[xp][yp];}
                        try{b = colors[xp][yp].getBlue()+ (ray.color.getBlue() * ray.grid[xp][yp]);}catch(Exception e){b = ray.color.getBlue() * ray.grid[xp][yp];}
                        //r = r / 2;g = g / 2;b = b / 2;
                        if(r > 255){r = 255;}
                        if(g > 255){g = 255;}
                        if(b > 255){b = 255;}
                        colors[xp][yp] = new Color((int)r,(int)g,(int)b);
                    }
                    yp++;
                }
                xp++;
                yp = 0;
            }
            xp = 0;
        }
        if(sum.hashCode() == lasthash){
            System.out.println("no change");
        }
        lasthash = sum.hashCode();
        last = sum;
        return(sum);
    }
    public Color readColor(int x, int y){
        
        //x = x -1;
        //y = y -1;
        //if(x > 49){x = 49;}
        //if(x < 0){x = 0;}
        //if(y > 49){y = 49;}
        //if(y < 0){y = 0;}
        Color sum = new Color(0,0,0);
        int r = 0, g = 0, b = 0;
        for(VSRad ray : VSRad){
            r = Math.round(sum.getRed() + (ray.grid[x][y] * ray.color.getRed()));if(r > 255){r = 255;}
            g = Math.round(sum.getGreen() + (ray.grid[x][y] * ray.color.getGreen()));if(g > 255){g = 255;}
            b = Math.round(sum.getBlue() + (ray.grid[x][y] * ray.color.getBlue()));if(b > 255){b = 255;}
            sum = new Color(r,g,b);
        }
        return(sum);
    }
    public void removeA(){
        for(int i : new Range(VSRad.size())){
            VSRad.remove(0);
        }
    }
    public void recalculate(String ignore, int type){
        for(VSRad i :VSRad){
            if(i.type == type){
                continue;
            }
            i.fill(0);
            this.colors = new Color[w][h];
            i.calculate(i.from, i.lastS, ignore);
        }
    }
    public float[][] getR(int xd, int yd){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getRed();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
    public float[][] getG(int xd, int yd){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getGreen();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
    public float[][] getB(int xd, int yd){
        float[][] out = new float[xd][yd];
        for(int x : new Range(xd)){
            for(int y : new Range(yd)){
                try{out[x][y] = colors[x][y].getBlue();}catch(Exception e){out[x][y] = 0;}
            }
        }
        return out;
    }
}
