/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

/**
 *
 * @author Jonnelafin
 */
public class gameObject {
//    Renderer re = new Renderer();
    float vely = 0;
    float velx = 0;
    
    private float drag = 0.1F;
    
    private float y = 1;
    private float x = 1;
    
    
    private String tag;
    private String appereance;
    private Color acolor = Color.RED;
    
    public float getY(){return(Math.round(this.y));}
    public float getX(){return(Math.round(this.x));}
    public float getVY(){return(this.vely);}
    public float getVX(){return(this.velx);}
    public Color getColor(){return(this.acolor);}
    public void setColor(Color cat){this.acolor = cat;}
    
    public String getTag(){return(this.tag);}
    
    public float getDistance(int ty, int tx){
        float ry = (float) pow(this.y - ty, 2.0);
        float rx = (float) pow(this.x - tx, 2.0);
        float finish = (float) sqrt(rx + ry);
        return(finish);
    }
    
    public String gAppearance(){return(this.appereance);}
    
    public void summon(int ypos, int xpos, String tag, String ap){
        this.y = ypos;
        this.x = xpos;
        this.tag = tag;
        this.appereance = ap;
    }
    public void update(Renderer re){
        this.y = this.y + this.vely;
        this.x = this.x + this.velx;
        if(this.y > re.sizey() - 1){
            this.y = re.sizey() - 1;
            this.vely = this.vely * -0.55F;
        }
        if(this.x > re.sizex() - 1){
            this.x = re.sizex() - 1;
            this.velx = this.velx * -0.5F;
        }
        if(this.y < 0){
            this.y = 0;
            this.vely = this.vely * -0.55F;
        }
        if(this.x < 0){
            this.x = 0;
            this.velx = this.velx * -0.5F;
        }
        if(velx != 0 && Math.round(this.y) > 23.7F){velx = velx * 0.65F;}
        if(vely > 3F){vely = 3.1F;}
        else{vely = vely + 0.1F;}
    }
    
}
