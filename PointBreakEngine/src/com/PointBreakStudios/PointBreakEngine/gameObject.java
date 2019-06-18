/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

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
    public String imageName = "";
    public int preferredLayer = -1;
//    Renderer re = new Renderer();
    objectManager oM = new objectManager();
    kick masterParent;
    public gameObject(int xpos, int ypos, String tag, String ap, float mas, Color cot, int ID, kick master){
        this.masterParent = master;
        this.summon(ypos, xpos, tag, ap, mas, cot, ID);
    }
    
    float mass = 1F;
    
    float vely = 0;
    float velx = 0;
    
    public float lastX = 0;
    public float lastY = 0;
    
    public int hits = 0;
    
    public float y = 1;
    public float x = 1;
    
    private int id;
    public boolean point2 = false;
    private LinkedList<String> tag = new LinkedList<>();
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
    public LinkedList<String> getTag(){return(this.tag);}
    
    public float getDistance(float tx, float ty){
        float ry = (float) pow(this.y - ty, 2.0);
        float rx = (float) pow(this.x - tx, 2.0);
        float finish = (float) sqrt(rx + ry);
        return(finish);
    }
    public Vector getLocation(){
        return(new Vector(this.x, this.y));
    }
    public String gAppearance(){return(this.appereance);}
    
    public void summon(int ypos, int xpos, String tag, String ap, float mas, Color cat, int ID){
        this.mass = mas;
        this.y = ypos;
        this.x = xpos;
        this.tag.add(tag);
        this.appereance = ap;
        this.acolor = cat;
        this.id = ID;
    }
    public void setLocation(Vector v){
        this.x = v.x;
        this.y = v.y;
    }
    public void update(Renderer re, objectManager oMb){    
        if(this.tag.contains(new String("cursor"))){}
        else{
            this.checkCollisions(oMb, this);
        }
        if(this.tag.contains(new String("player1")) || this.tag.contains(new String("cursor"))){
//        System.out.println(colliding);
            if(this.tag.contains(new String("cursor"))){}
            else{
//                this.y = this.y + (this.vely);
//                this.x = this.x + (this.velx);
                for (int i : new Range(round(Math.abs(this.velx) * 10))) {
                    
                    if(this.velx < 0){this.x = this.x - 0.1F;}
                    if(this.velx > 0){this.x = this.x + 0.1F;}
                    this.checkCollisions(oMb, this);
                }
                for (int i : new Range(round(Math.abs(this.vely) * 10))) {
                    
                    if(this.vely < 0){this.y = this.y - 0.1F;}
                    if(this.vely > 0){this.y = this.y + 0.1F;}
                    this.checkCollisions(oMb, this);
                }
                  
            }
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

            if(point2 || Math.round(this.y) > 23.7F){velx = velx * 0.65F;}
//            if(velx != 0 && Math.round(this.y) > 23.7F){velx = velx * 0.65F;}

            if(Math.round(this.y) > 23.99F){colliding = true;}
            else{colliding = false;}
//            if(velx != 0 && Math.round(this.y) > 23.7F){colliding = true;}
            if(colliding){velx = velx * 0.65F;}
            
            if(!colliding || point2){
                if(vely > 100F){vely = 100.1F;}
                else{vely = (float) (vely + masterParent.engine_gravity.y);}
            }
            else{this.vely = this.vely * -0.75F;}
            if(!colliding){
                if(velx > 100F){velx = 100.1F;}
                else{velx = (float) (velx + masterParent.engine_gravity.x);}
            }
        }
        if(this.tag.contains(new String("static"))){
            vely = 0F;
            velx = 0F;
        }
    }
    void checkInput(Input input) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void point1(gameObject i, gameObject x){
        colliding = true;
                        float ivx = i.velx * -0.15F;
                        float ivy = i.vely * -0.15F;
                        float xvx = x.velx * -0.15F;
                        float xvy = x.vely * -0.15F;
                        i.x = i.x + i.velx * -1F;
                        i.y = i.y + i.vely * -1F;
                        i.velx = ivx;
                        i.vely = ivy;
    }
    public void point2(gameObject i, gameObject x){
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
    int po = 0;
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

                    if(!this.tag.contains(new String("cursor"))){
//                        System.out.print(colliding + ", ");
//                        System.out.println("x2, y2:x1,y1 : " + x2 + ", " + y2 + ", "+ x1 + ", " + y1);
                    }

                    if(round(this.x) == round(x2) && round(this.y) == round(y2)){
//                    if(i.getDistance(y2, x2) < 1.1F){
//System.out.println("point1");
                    po = 1;
                        
                        //x.velx = xvx;
                        //x.vely = xvy;
                    }
//                    }
                    else{
                        if(round(this.x) == round(x2) && ceil(this.y) == ceil(y2)){
                        point2 = true;
                        
                        po = 2;
//System.out.println("point_2");
                        
                        }
                        else{
                        po = 0;
                        point2 = false;
                        colliding = false;
                        }
                        
                    }
                    if(po == 1){point1(i,x);}
                    if(po == 2){point2(i,x);}
                    
//                    i.update(r);
//                    x.update(r);
                }
                
            }
    }
    
}
