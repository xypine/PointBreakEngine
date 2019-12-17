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
package PBEngine;

import JFUtils.Point2Int;
import JFUtils.Range;
import JFUtils.Point2D;
import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Elias Eskelinen <elias.eskelinen@protonmail.com>
 */
public class VSRad {
    int id;
    public int type = 0;
    //type 1 = baked, type 0 = realtime
    public Color color = new Color(0,0,0);
    public float[][] grid;
    public LinkedList<float[][]> rays = new LinkedList<>();
    public int resolution = 1000; //1000
    public int newResolution = 3600;
    public float shutter = 0.01F;
    private Point2Int source;
    public int width, height;
    Point2D[] directions = new Point2D[resolution];
    private objectManager oM;
    //private radiosity demo;
    public VSRad(objectManager oM, Color c, int type){
        this.oM = oM;
        this.color = c;
        this.type = type;
    }
    int resType = 1;
    public void init(int w, int h, int type, int id){
        this.id = id;
        System.out.println("Init raycaster " + id);
        resType = type;
      //TYPE
      //0: Old
      //1: New
        int state = 0;
        grid = new float[w][h];
        this.width = w;
        this.height = h;
        Point2D cur = new Point2D(0,1);
        //System.out.println(dVector.add(cur, new dVector(0.1, -0.1)).represent());
        Range r = new Range(resolution);
        Range nr = new Range(newResolution);
        if(type != 0){directions = new Point2D[newResolution];}
        for(int i : nr){
            if(type == 0){break;}
            double dir = (Math.PI * 2) * ((double)i/newResolution);
            dir = (dir) * Math.PI/180.0;
            Point2D direc = new Point2D(Math.sin(dir), Math.cos(dir));
            directions[i] = direc;
            
        }
        for(int i : r){
            if(type != 0){break;}
            directions[i] = new Point2D(0, 0);
            if(state == 0){
                if(cur.x < 1){
                    //System.out.println("Calculating directions phase 0: " + cur.represent());
                    
                    cur = Point2D.add(cur, new Point2D(0.1/(resolution * shutter), -0.1/(resolution * shutter)));
                    directions[i] = cur;
                }
                else{
                    state = 1;
                }
            }
            if(state == 1){
                if(cur.x > 0){
                    //System.out.println("Calculating directions phase 1: " + cur.represent());
                    
                    cur = Point2D.add(cur, new Point2D(-0.1/(resolution * shutter), -0.1/(resolution * shutter)));
                    directions[i] = cur;
                }
                else{
                    state = 2;
                }
            }
            if(state == 2){
                if(cur.x > -1){
                    //System.out.println("Calculating directions phase 2: " + cur.represent());
                    
                    cur = Point2D.add(cur, new Point2D(-0.1/(resolution * shutter), 0.1/(resolution * shutter)));
                    directions[i] = cur;
                }
                else{
                    state = 3;
                }
            }
            if(state == 3){
                if(cur.y < 1){
                    //System.out.println("Calculating directions phase 3: " + cur.represent());
                    
                    cur = Point2D.add(cur, new Point2D(0.1/(resolution * shutter), 0.1/(resolution * shutter)));
                    directions[i] = cur;
                }
                else{
                    state = 4; //Finished
                    //System.out.println("Calculating directions phase 4: " + cur.represent());
                    //System.out.println("finished calculating directions");
                }
            }
            //directions[i] = new Vector(0,1);
            //directions[i] = new Vector(0.1F,1F);
            //directions[i] = new Vector(0,1);
            fill(0F);
        }
        fill(0F);
        System.out.println("done intializing raycaser " + id);
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
    Point2D cursor = new Point2D(0,0);
    int done = 0;
    int hits = 0;
    public float s = 0;
    int requests = 0;
    public float sum = 0;
    Point2D dir = new Point2D(0, 0);
    public Point2D from;
    public float lastS;
    public void calculate(Point2D from, float strenght, String ignoreCollWith){
//        strenght = strenght / 100;
        this.from = from;
        this.lastS = strenght;
        float[][] ray = new float[width][height];
        requests++;
//        for(dVector d: directions){
        //    System.out.println("direction " + d.represent());
//        }
        System.out.println("Calculating raycaster "+id+"...");
        cursor.x = from.x;
        cursor.y = from.y;
        try {
            grid[(int) from.x][(int) from.y] = strenght;
        } catch (Exception e) {
            
        }
        fill(0);
        s = strenght;
        float bOut = 0.3F;
        float decay = 0.995F;
        float nullDecay = 0.999F;
        if(this.type == 0){
            bOut = 0;
            decay = 0.99F;
            nullDecay = 0.995F;
        }
        int res = 0;
        if(resType == 0){
            res = resolution;
        }else{res = newResolution;}
        sum = 0;
        for(int i : new Range(res)){
            System.out.println("(light calculation for raycasterID [" + this.id + "], [" + ((resolution+1) / 100 * done / 100) + "]% done");
            int hp = 1;
            int hits =0;
            inside = true;
            
            dir.x = directions[i].x;
            dir.y = directions[i].y;
            while(inside){
                //System.out.println(s);
                //demo.setTitle("ViridiEngine radiosity "+s);
                try{
                    //System.out.print(hits + " ");
                    //System.out.println(dir.represent());
                    cursor = new Point2D(cursor.x + dir.x, cursor.y + dir.y);
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
                    if(oM.colliding((int) cursor.x, (int) cursor.y, ignoreCollWith)){
                        grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                        if(Math.abs(dir.x) > Math.abs(dir.y)){
                            dir.x = dir.x * -1;
                        }
                        else if(Math.abs(dir.x) < Math.abs(dir.y)){
                            dir.y = dir.y * -1;
                        }
                        else{
                            dir.x = dir.x * -1;
                            dir.y = dir.y * -1;
                        }
                        cursor = new Point2D(cursor.x + dir.x, cursor.y + dir.y);
                        s = s * decay;
                        //System.out.println(dVector.round(cursor).represent() + " " + requests);
                    }
                    grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                    sum = sum + s;
                }
                catch(Exception e){
                    
                    inside = false;throw e;
                    //bounce(hp, dir, s);
                }
                s = s * nullDecay;
                if(s <= 0.00001){
                    inside = false;
                }
            }
            cursor = from;
            s = strenght;
            inside = true;
            done++;
        }
        //rays.add(ray);
        System.out.println("raycaster "+id+" calculated, mass: "+sum);
        System.out.println("You can press the (lowercase) key 'L' to save the lighting, if you are running on the default engine");
    }
}

