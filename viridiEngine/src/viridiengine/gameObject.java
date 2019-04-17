/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;
import static java.lang.Math.ceil;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class gameObject {
//    Renderer re = new Renderer();
    objectManager oM = new objectManager();
    
    public gameObject(int xpos, int ypos, String tag, String ap, float mas, Color cot, int ID){
        this.summon(ypos, xpos, tag, ap, mas, cot, ID);
    }
    
    float mass = 1F;
    
    float vely = 0;
    float velx = 0;
    
    public int hits = 0;
    
    private float y = 1;
    private float x = 1;
    
    private int id;
    
    private String tag;
    private String appereance;
    private Color acolor = Color.RED;

//    gameObject(int y, int x, String tag, String skin, float mass, String type) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public boolean colliding = false;
    public float getY(){return(this.y);}
    public float getX(){return(this.x);}
    public float getVY(){return(this.vely);}
    public float getVX(){return(this.velx);}
    public Color getColor(){return(this.acolor);}
    public void setColor(Color cat){this.acolor = cat;}
    
    public int getID(){return(this.id);}
    public String getTag(){return(this.tag);}
    
    public float getDistance(float ty, float tx){
        float ry = (float) pow(this.y - ty, 2.0);
        float rx = (float) pow(this.x - tx, 2.0);
        float finish = (float) sqrt(rx + ry);
        return(finish);
    }
    
    public String gAppearance(){return(this.appereance);}
    
    public void summon(int ypos, int xpos, String tag, String ap, float mas, Color cat, int ID){
        this.mass = mas;
        this.y = ypos;
        this.x = xpos;
        this.tag = tag;
        this.appereance = ap;
        this.acolor = cat;
        this.id = ID;
    }
    public void update(Renderer re, objectManager oMb){     
        this.checkCollisions(oMb, this);
        if(this.tag != "static"){
//        System.out.println(colliding);    
            this.y = this.y + (this.vely);
            this.x = this.x + (this.velx);
            if(this.y > re.sizey() - 1){
                this.y = re.sizey() - 1;
//                this.vely = this.vely * -0.55F;
                this.hits++;
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

            if(colliding || velx != 0 && Math.round(this.y) > 23.7F){velx = velx * 0.65F;}
//            if(velx != 0 && Math.round(this.y) > 23.7F){velx = velx * 0.65F;}

            if(Math.round(this.y) > 23.7F){colliding = true;}
            else{colliding = false;}
//            if(velx != 0 && Math.round(this.y) > 23.7F){colliding = true;}
            if(colliding){velx = velx * 0.65F;}
            
            if(!colliding){
                
                if(vely > 3F){vely = 3.1F;}
                else{vely = vely + 0.1F;}
            }
            else{this.vely = this.vely * -0.75F;}
            
        }
        
    }
    void checkInput(Input input) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void checkCollisions(objectManager o, gameObject i){
        LinkedList<gameObject> tmpoa = o.getObjects();
//        for(gameObject i : tmpoa){
            for(gameObject x : tmpoa){
                if(i.getID() == x.getID()){
                    
                }
                else{
                    int x1 = round(i.getX());
                    int y1 = round(i.getY());
                    
                    float x2 = x.getX();
                    float y2 = x.getY();

//                    if(x.tag != "static"){
//                        System.out.print(colliding);
//                        System.out.println("");
//                    }

                    if(x.tag != "static"){
//                        System.out.print(colliding + ", ");
//                        System.out.println("x2, y2:x1,y1 : " + x2 + ", " + y2 + ", "+ x1 + ", " + y1);
                    }

                    if(round(this.x) == round(x2) && round(this.y) == round(y2)){
//                    if(i.getDistance(y2, x2) < 1.1F){
                        colliding = true;
                        float ivx = i.velx * -0.15F;
                        float ivy = i.vely * -0.15F;
                        float xvx = x.velx * -0.15F;
                        float xvy = x.vely * -0.15F;
                        i.x = i.x + i.velx * -1F;
                        i.y = i.y + i.vely * -1F;
                        i.velx = ivx;
                        i.vely = ivy;
                        //x.velx = xvx;
                        //x.vely = xvy;
                    }
//                    }
                    else{
                        if(round(this.x) == round(x2) && ceil(this.y) == ceil(y2)){
                        colliding = true;
                        float ivx = i.velx * -0.15F;
                        float ivy = i.vely * -0.15F;
                        float xvx = x.velx * -0.15F;
                        float xvy = x.vely * -0.15F;
                        i.x = i.x + i.velx * -1F;
                        i.y = i.y + i.vely * -1F;
                        //i.velx = ivx;
                        i.vely = ivy;
                        }
                        else{
                        colliding = false;
                        }
                    }
                    
//                    i.update(r);
//                    x.update(r);
                }
                
            }
    }
    
}
