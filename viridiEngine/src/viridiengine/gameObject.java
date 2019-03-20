/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

/**
 *
 * @author Jonnelafin
 */
public class gameObject {
//    Renderer re = new Renderer();
    
    private float vely = 1;
    private float velx = 3;
    
    private float y = 0;
    private float x = 0;
    
    
    private String tag;
    private String appereance;
    
    public float getY(){return(Math.round(this.y));}
    public float getX(){return(Math.round(this.x));}
    public float getVY(){return(this.vely);}
    public float getVX(){return(this.velx);}
    
    public String gAppereance(){return(this.appereance);}
    
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
        }
        if(this.x > re.sizex() - 1){
            this.x = re.sizex() - 1;
            this.velx = this.velx * -0.5F;
        }
        if(this.y < 0){
            this.y = 0;
        }
        if(this.x < 0){
            this.x = 0;
            this.velx = this.velx * -0.5F;
        }
        if(velx >= 0){velx = velx * 0.99F;}
    }
}
