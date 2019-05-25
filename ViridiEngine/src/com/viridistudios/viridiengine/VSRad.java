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
    public int resolution = 360;
    private Vector source;
    public int width, height;
    Vector[] directions = new Vector[resolution];
    public void init(int w, int h){
        int state = 0;
        grid = new float[w][h];
        Vector cur = new Vector(0,1);
        System.out.println(Vector.add(cur, new Vector(0.1F, -0.1F)).represent());
        for(int i : new Range(resolution)){
            if(state == 0){
                if(cur.x < 1){
                    System.out.println("Calculating directions phase 0: " + cur.represent());
                    directions[i] = cur;
                    cur = Vector.add(cur, new Vector(0.1F, -0.1F));
                }
                else{
                    state = 1;
                }
            }
            if(state == 1){
                if(cur.x > 0){
                    System.out.println("Calculating directions phase 1: " + cur.represent());
                    directions[i] = cur;
                    cur = Vector.add(cur, new Vector(-0.1F, -0.1F));
                }
                else{
                    state = 2;
                }
            }
            if(state == 2){
                if(cur.x > -1){
                    System.out.println("Calculating directions phase 2: " + cur.represent());
                    directions[i] = cur;
                    cur = Vector.add(cur, new Vector(-0.1F, 0.1F));
                }
                else{
                    state = 3;
                }
            }
            if(state == 3){
                if(cur.y < 1){
                    System.out.println("Calculating directions phase 3: " + cur.represent());
                    directions[i] = cur;
                    cur = Vector.add(cur, new Vector(0.1F, 0.1F));
                }
                else{
                    state = 4; //Finished
                    System.out.println("Calculating directions phase 4: " + cur.represent());
                }
            }
            directions[i] = new Vector(0,1);
            directions[i] = new Vector(0.1F,1F);
            directions[i] = new Vector(0,1);
        }
    }
    
    public void fill(float to){
        for(float[] arr: grid){
            for(float i : arr){
                i = to;
            }
        }
    }
    private boolean inside = true;
    Vector cursor;
    public void calculate(Vector from, float strenght){
        for(Vector d: directions){
            System.out.println("direction " + d.represent());
        }
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        float s = strenght;
        for(int i : new Range(resolution)){
            while(inside){
                try{
                    cursor = new Vector(cursor.x + directions[i].x, cursor.y + directions[i].y);
                    grid[(int) cursor.x][(int) cursor.y] = grid[(int) cursor.x][(int) cursor.y] + s;
                }
                catch(Exception e){
                    inside = false;
                }
                if(s - 1 > 0){
                s--;
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
