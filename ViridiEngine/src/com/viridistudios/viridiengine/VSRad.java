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
    
    public void calculate(dVector from, float strenght){
        for(dVector d: directions){
            //System.out.println("direction " + d.represent());
        }
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        float s = strenght;
        
        for(int i : new Range(resolution)){
            int hp = 0;
            dVector dir;
            while(inside){
                
                try{
                    cursor = new dVector(cursor.x + directions[i].x, cursor.y + directions[i].y);
                    grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                }
                catch(Exception e){
                    
                    if(hp > 0){
                        try{
                            cursor = new dVector(cursor.x + dVector.multiply(directions[i], new dVector(-1, -1)).x, dVector.multiply(directions[i], new dVector(-1, -1)).y);
                            grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                            hp = hp -1;
                            s = s * 0.5F;
                        }
                        catch(Exception d){
                            inside = false;
                        }
                    }
                    else{
                        inside = false;
                    }
                }
                if(s - 1 > 0){
                s = s * 0.999F;
                }
                else{
                    s = 0;
                }
            }
            cursor = from;
            s = strenght;
            inside = true;
        }
    }
    
    
}
