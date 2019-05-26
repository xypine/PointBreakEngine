/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viridistudios.viridiengine;

/**
 *
 * @author elias
 */
public class VSRad {
    public float[][] grid;
    public int resolution = 10000;
    public float shutter = 0.001F;
    private Vector source;
    public int width, height;
    dVector[] directions = new dVector[resolution];
    private objectManager oM;
    private radiosity demo;
    public VSRad(objectManager oM, radiosity r){
        this.oM = oM;
        this.demo = r;
    }
    public void init(int w, int h){
        int state = 0;
        grid = new float[w][h];
        this.width = w;
        this.height = h;
        dVector cur = new dVector(0,1);
        System.out.println(dVector.add(cur, new dVector(0.1, -0.1)).represent());
        for(int i : new Range(resolution)){
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
    dVector cursor;
    int done = 0;
    int hits = 0;
    public float s = 0;
    public void calculate(dVector from, float strenght){
        for(dVector d: directions){
            //System.out.println("direction " + d.represent());
        }
        System.out.println("Calculating rays...");
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        s = strenght;
        
        for(int i : new Range(resolution)){
            //System.out.println(done / resolution * 100 + "%: ");
            int hp = 1;
            int hits =0;
            dVector dir = directions[i];
            while(inside){
                //System.out.println(s);
                //demo.setTitle("ViridiEngine radiosity "+s);
                try{
                    //System.out.print(hits + " ");
                    cursor = new dVector(cursor.x + dir.x, cursor.y + dir.y);
                    if(cursor.y > height-1){
                        cursor.y = height - 1;
                        dir.y = dir.y * - 1;
                        s = s * 0.7F;
                    }
                    if(cursor.y < 0){
                        cursor.y = 0;
                        dir.y = dir.y * - 1;
                        s = s * 0.7F;
                    }
                    if(cursor.x > width-1){
                        cursor.x = width - 1;
                        dir.x = dir.x * - 1;
                        s = s * 0.7F;
                    }
                    if(cursor.x < 0){
                        cursor.x = 0;
                        dir.x = dir.x * - 1;
                        s = s * 0.7F;
                    }
                    if(oM.colliding((int) cursor.x, (int) cursor.y)){
                        dir.x = dir.x * -1;
                        dir.y = dir.y * -1;
                        cursor = new dVector(cursor.x + dir.x, cursor.y + dir.y);
                        s = s * 0.5F;
                    }
                    grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                }
                catch(Exception e){
                    inside = false;
                    //bounce(hp, dir, s);
                }
                s = s * 0.99F;
                if(s < 0.09){
                    inside = false;
                }
            }
            cursor = from;
            s = strenght;
            inside = true;
            done++;
        }
        System.out.println("rays calculated");
    }
    private void bounce(int hp, dVector dir, float s){
                    if(hp > 0 && s > 0){
                        try{
                            dir = dVector.multiply(dir, new dVector(-1, -1));
                            cursor = new dVector(cursor.x + dir.x, cursor.y + dir.y);
                            grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                            hp = hp -1;
                            s = s * 0.7F;
                            if(s < 0.001){
                                inside = false;
                            }
                            hits++;
                            //System.out.print(".");
                        }
                        catch(Exception d){
                            inside = false;
                        }
                    }
                    else{
                        inside = false;
                    }
    }
    
}
