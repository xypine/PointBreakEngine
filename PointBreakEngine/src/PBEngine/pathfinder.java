/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import jfUtils.Vector;

/**
 *
 * @author Jonnelafin
 */
public class pathfinder {
    int w;
    int h;
    
    int[][] steps;
    public pathfinder(int w, int h){
        this.w = w;
        this.h = h;
        this.steps = new int[w][h];
    }
    public void calculate(Vector from, Vector to){
        Vector cur = new Vector(from.x, from.y);
        while(cur != to){
            
        }
    }
}
