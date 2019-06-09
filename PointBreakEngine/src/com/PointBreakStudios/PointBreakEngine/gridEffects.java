/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

/**
 *
 * @author Jonnelafin
 */
public class gridEffects {
    dVector[] dirs = new dVector[8];
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
    public float[][] blur(float[][] sauce, int w, int h){
        int x = 0;
        int y = 0;
        x = 0;y = 0;
        float sum = 0;
        float[][] out = new float[w][h];
        for(float[] lane : sauce){
            for(float lany : lane){
                sum = 0;
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
        System.out.println(out[0]);
        return(out);
    }
}
