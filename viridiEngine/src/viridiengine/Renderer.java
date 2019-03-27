/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
import java.util.Arrays;

/**
 *
 * @author Jonnelafin
 */
public class Renderer {
    private int a;
    private int b;
    private String[][] space;
    private Color[][] colors;
    private int y;
    private int x;
    
    
    public void init(int y, int x){
        this.y = y;
        this.x = x;
        this.space = new String[x][y];
        this.colors = new Color[x][y];
        this.a = (space.length);
        this.b = (space[0].length);
        colorFill(Color.white);
        fill(" . ");
    }
    
    public String[][] gets(){return(this.space);}
    public Color[][] getc(){return(this.colors);}
    
    void fill(String goal){
        String[][] tmp;
        tmp = this.space;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.space[c][u] = goal;
            }
        }
//        this.space = tmp;
    }
    void colorFill(Color goal){
        Color[][] tmp;
        tmp = this.colors;
        for(int c = 0; c < this.x; c++){
            for(int u = 0; u < this.y; u++){
                this.colors[c][u] = goal;
            }
        }
    }
    void scan(int fx,int fy,int tx,int ty){
        
    }
    void change(int locy,int locx,String to, Color c){
        try{
            this.space[locx][locy] = to;
            colors[locx][locy] = c;
        }
        catch (Exception e){
            System.out.println("Tried writing out of bounds: y(" + locy + "), x(" +locx + ")");
        }
    }
    public int sizey(){return(this.x);}
    public int sizex(){return(this.y);}
    
    
    
    

    

    
}
    
//    for(int i; i < 5; i++){}

