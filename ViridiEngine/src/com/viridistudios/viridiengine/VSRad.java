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
    private Vector source;
    public int width, height;
    Vector[] directions = new Vector[8];
    public void init(int w, int h){
        grid = new float[w][h];
        
        directions[0] = new Vector(0, 1);
        directions[1] = new Vector(1, 1);
        directions[2] = new Vector(1, 0);
        directions[3] = new Vector(1, -1);
        directions[4] = new Vector(0, -1);
        directions[5] = new Vector(-1, -1);
        directions[6] = new Vector(-1, 0);
        directions[7] = new Vector(-1, 1);
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
        cursor = from;
        grid[(int) from.x][(int) from.y] = strenght;
        float s = strenght;
        for(int i : new Range(8)){
            while(inside){
                try{
                    cursor = new Vector(cursor.x + directions[i].x, cursor.y + directions[i].y);
                    grid[(int) cursor.x][(int) cursor.y] = strenght;
                }
                catch(Exception e){
                    inside = false;
                }
                strenght--;
            }
            cursor = from;
            s = strenght;
            inside = true;
        }
    }
    public String pwts(int x, int y){ 
        try{
            return(Float.toString(grid[x][y]));
        }catch(Exception e){
            
        }
        return("0");
    }
    
}
