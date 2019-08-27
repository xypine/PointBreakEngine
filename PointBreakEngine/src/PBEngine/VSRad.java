    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PBEngine;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author elias
 */
public class VSRad {
    int id;
    public int type = 0;
    //type 1 = baked, type 0 = realtime
    public Color color = new Color(0,0,0);
    public float[][] grid;
    public LinkedList<float[][]> rays = new LinkedList<>();
    public int resolution = 1000;
    public int newResolution = 3600;
    public float shutter = 0.01F;
    private Vector source;
    public int width, height;
    dVector[] directions = new dVector[resolution];
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
        dVector cur = new dVector(0,1);
        //System.out.println(dVector.add(cur, new dVector(0.1, -0.1)).represent());
        Range r = new Range(resolution);
        Range nr = new Range(newResolution);
        if(type != 0){directions = new dVector[newResolution];}
        for(int i : nr){
            if(type == 0){break;}
            double dir = (Math.PI * 2) * ((double)i/newResolution);
            dir = (dir) * Math.PI/180.0;
            dVector direc = new dVector(Math.sin(dir), Math.cos(dir));
            directions[i] = direc;
            
        }
        for(int i : r){
            if(type != 0){break;}
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
        strenght = strenght / 100;
        this.from = from;
        this.lastS = strenght;
        float[][] ray = new float[width][height];
        requests++;
//        for(dVector d: directions){
        //    System.out.println("direction " + d.represent());
//        }
        System.out.println("Calculating raycaster "+id+"...");
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        fill(0);
        s = strenght;
        float bOut = 0.9999F;
        float decay = 0.9999F;
        float nullDecay = 0.9975F;
        if(this.type == 0){
            bOut = 0;
            decay = 0.99F;
            nullDecay = 0.995F;
        }
        int res = 0;
        if(resType == 0){
            res = resolution;
        }else{res = newResolution;}
        for(int i : new Range(res)){
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
        System.out.println("raycaster "+id+" calculated");
    }
}

