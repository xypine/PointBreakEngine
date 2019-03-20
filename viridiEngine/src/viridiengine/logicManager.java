/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.util.Arrays;

/**
 *
 * @author Jonnelafin
 */
public class logicManager {
    private int a;
    private int b;
    private String[][] space;
    
    public void init(int y, int x){
        this.space = new String[y][x];
        this.a = (space.length);
        this.b = (space[0].length);
        fill(y, x, "-", this.space);
    }
    
    public String[][] gets(){return(this.space);}
    
    void fill(int y, int x, String goal, String[][] s){
        String[][] tmp;
        tmp = s;
        for(int c = 0; c < y; c++){
            for(int u = 0; u < x; u++){
                s[c][u] = goal;
            }
        }
        this.space = tmp;
    }
    
    
    
    

    

    
}
    
//    for(int i; i < 5; i++){}

