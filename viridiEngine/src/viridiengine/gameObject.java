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
    
    private int vely = 1;
    private int velx = 1;
    
    private int y = 0;
    private int x = 0;
    
    private String tag;
    private String appereance;
    
    public int getY(){return(this.y);}
    public int getX(){return(this.x);}
    public int getVY(){return(this.vely);}
    public int getVX(){return(this.velx);}
    
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
        }
    }
}
