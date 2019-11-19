/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package PBEngine;

import JFUtils.Input;
import JFUtils.Vector;
import JFUtils.Range;
import JFUtils.dVector;
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
    //0: handle by the parent, 1: handle by each children
    public int children_translationMode = 0;
    public boolean onlyColor = false;
    public boolean pureColor = false;
    
    public boolean visible = true;
    
    private double rotation = 0;
    public void setDegrees(double degrees){
        this.rotation = (degrees * (Math.PI /180));
    }
    public void setRadians(double radians){
        this.rotation = radians;
    }
    public double getDegrees(){
        return rotation / (Math.PI / 180);
    }
    public double getRadians(){
        return rotation;
    }
    int collisionCount = 0;
    
    public boolean collisions = true;
    boolean gravity = true;
    
    public boolean brightColor = false;
    
    private gameObject parent = this;
    public boolean isParent = false;
    private LinkedList<gameObject> children = new LinkedList<>();
    public LinkedList<gameObject> getChildren(){
        return this.children;
    }
    public void addChild(gameObject child){
        children.add(child);
    }
    public void removeChild(gameObject child){
        children.remove(child);
    }
    public int children(){
        return children.size();
    }
    private double parent_offsetX = 0;
    private double parent_offsetY = 0;
    
    public int collision_type = 0;
    
    public String imageName = "";
    public int imageid = -1;
    public int preferredLayer = -1;
//    Renderer re = new Renderer();
    //objectManager oM = new objectManager();
    public Supervisor masterParent;
    public gameObject(int xpos, int ypos, int size, String tag, String ap, double mas, Color cot, int ID, Supervisor master){
        this.masterParent = master;
        this.summon(ypos, xpos, tag, ap, mas, cot, ID);
        this.children.add(this);
        this.size = size;
    }
    
    double mass = 1F;
    
    double vely = 0;
    double velx = 0;
    
    double cory = 0;
    double corx = 0;
    
    public int size = 1;
    
    public double lastX = 0;
    public double lastY = 0;
    
    public int hits = 0;
    public boolean collision_Explode = false;
    public double y = 1;
    public double x = 1;
    
    private int id;
    public boolean point2 = false;
    public LinkedList<String> tag = new LinkedList<>();
    private String appereance;
    private Color acolor = Color.RED;

//    gameObject(int y, int x, String tag, String skin, double mass, String type) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public boolean colliding = false;
    public double getY(){return(this.y);}
    public double getX(){return(this.x);}
    public double getVY(){return(this.vely);}
    public double getVX(){return(this.velx);}
    public Color getColor(){return(this.acolor);}
    public void setColor(Color cat){this.acolor = cat;}
    
    public void setParent(gameObject parent){
        this.parent = parent;
        parent.isParent = true;
        parent.addChild(this);
        this.parent_offsetX = parent.x - this.x;
        this.parent_offsetY = parent.y - this.y;
    }
    public gameObject getParent(){
        return this.parent;
    }
    public void clearParent(){
        this.parent.removeChild(this);
        if(parent.children() == 1){
            this.parent.isParent = false;
        }
        this.parent = this;
    }
    public int getID(){return(this.id);}
    public LinkedList<String> getTag(){return(this.tag);}
    
    public double getDistance(double tx, double ty){
        double ry = (double) pow(this.y - ty, 2.0);
        double rx = (double) pow(this.x - tx, 2.0);
        double finish = (double) sqrt(rx + ry);
        return(finish);
    }
    public dVector getLocation(){
        return(new dVector(this.x, this.y));
    }
    public String gAppearance(){return(this.appereance);}
    
    public void summon(int ypos, int xpos, String tag, String ap, double mas, Color cat, int ID){
        this.mass = mas;
        this.y = ypos;
        this.x = xpos;
        this.tag.add(tag);
        this.appereance = ap;
        this.acolor = cat;
        this.id = ID;
    }
    public void setLocation(dVector v){
        this.x = v.x;
        this.y = v.y;
    }
    public void update(int xd, int yd, objectManager oMb){
        //int deltatime = (int) (masterParent.Logic.deltatime * 0.1);
        int deltatime = 1;
        if(this.collision_Explode){
            if(colliding || po != 0 || point2){
                oMb.removeObject(this);
                //oMb.removeObject(this);
            }
        }
        if(this.parent.equals(this)){
            if(this.tag.contains(new String("cursor"))){}
            else{
                //this.checkAdvancedCollisions(oMb, this);
            }
            //if(this.tag.contains(new String("player1")) || this.tag.contains(new String("cursor"))){
            if(!this.tag.contains(new String("static"))){
    //        System.out.println(colliding);
                checkInput(masterParent.Logic.input);
                
                collisionCount = 0;
                if(this.tag.contains(new String("cursor"))){}
                else{
                    long delta = masterParent.getDelta();
    //                this.y = this.y + (this.vely);
    //                this.x = this.x + (this.velx);
                    for (int i : new Range((int) round(Math.abs(this.velx * (delta*0.2) ) * 5))) {

                        doOnPreciseMovement();
                        if(this.velx < 0){this.x = this.x - 0.1F;}
                        if(this.velx > 0){this.x = this.x + 0.1F;}
                        checkAdvancedCollisions(oMb, this);
                        for(dVector dir : Vector.dir()){
                        //    checkAdvancedCollisions(oMb, this, x+dir.x, y+dir.y);
                        }
                        
                    }
                    for (int i : new Range((int) round(Math.abs(this.vely * (delta*0.2) ) * 5))) {

                        doOnPreciseMovement();
                        if(this.vely < 0){this.y = this.y - 0.1F;}
                        if(this.vely > 0){this.y = this.y + 0.1F;}
                        checkAdvancedCollisions(oMb, this);
                        for(dVector dir : Vector.dir()){
                        //    checkAdvancedCollisions(oMb, this, x+dir.x, y+dir.y);
                        }
                    }

                }
                if(collisionCount > 0){colliding = true;}
                else{colliding = false;}
                //if(point2 || Math.round(this.y) > 23.7F){velx = velx * 0.65F;}
    //            if(velx != 0 && Math.round(this.y) > 23.7F){velx = velx * 0.65F;}

                //if(Math.round(this.y) > 23.99F){this.vely = this.vely * -0.2F;}
                //WHAT IS THIS LINE???      else{colliding = false;}
    //            if(velx != 0 && Math.round(this.y) > 23.7F){colliding = true;}
                //if(colliding){velx = velx * 0.65F;}
                //if(colliding || point2){velx = velx * 0.025F;}
                //if(!colliding){velx = velx * 0.95F;}
                if(gravity){
                    if(vely > 1000){vely = 1000.1;}
                    else{vely = vely + masterParent.engine_gravity.y;}
                }
                //else{this.vely = this.vely * -0.75F;}
                
                if(gravity){
                    if(velx > 1000){velx = 1000.1;}
                    else{velx = velx + masterParent.engine_gravity.x;}
                }else{
                    vely = 0;
                }
                //checkAdvancedCollisions(oMb, this, x + velx, y + vely);
                
                if(this.y > yd - 1){
                    overBound(2, xd, yd);
    //                this.vely = this.vely * -0.55F;
                    //this.hits++;
                }
                else if(this.x > xd - 1){
                    overBound(1, xd, yd);
                    //this.velx = this.velx * -0.2F;

                }
                else if(this.y < 0){
                    overBound(0,xd,yd);
                    //this.vely = this.vely * -0.2F;
                }
                else if(this.x < 0){
                    overBound(3, xd, yd);
                    //this.velx = this.velx * -0.2F;
                }
                else{
                    doIfInside();
                }
                
                
                velx = velx * masterParent.world_friction_multiplier;
                vely = vely * masterParent.world_friction_multiplier;
            }
            if(this.tag.contains("static")){
                vely = 0F;
                velx = 0F;
            }
        }
        else if(this.parent.children_translationMode == 1){
            this.x = parent.x + parent_offsetX;
            this.y = parent.y + parent_offsetY;
        }
        if(children_translationMode == 0){
            for(gameObject child : children){
                child.x = this.x + child.parent_offsetX;
                child.y = this.y + child.parent_offsetY;
            }
        }
    }
    public void doIfInside(){
        
    }
    public void overBound(int direction, int xd, int yd){
        if(direction == 2){
                    this.y = 0;
                }
        if(direction == 1){
                    this.x = 0;
                }
        if(direction == 0){
                    this.y = yd - 1;
                }
        if(direction == 3){
                    this.x = xd - 1;
        }
    }
    public void addForce(Vector force){
        this.velx = this.velx + force.x;
        this.vely = this.vely + force.y;
    }
    void checkInput(Input input) {
          //Ignore if not player...
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void point1(gameObject i, gameObject x){
                colliding = true;
                        double ivx = i.velx * -0.15;
                        double ivy = i.vely * -0.15;
//                        float xvx = x.velx * -0.15F;
//                        float xvy = x.vely * -0.15F;
                        corx = i.velx * -1;
                        cory = i.vely * -1;
                        i.velx = i.velx + ivx;
                        i.vely = i.vely + ivy;
    }
    public void point1(gameObject i, gameObject x, double amount){
                        double ivx = i.velx * (-0.15 *amount);
                        double ivy = i.vely * (-0.05 *amount);
//                        float xvx = x.velx * -0.15F;
//                        float xvy = x.vely * -0.15F;
                        i.x = i.x + i.velx * (-1 *amount);
                        i.y = i.y + i.vely * (-1 *amount);
                        i.velx = ivx;
                        i.vely = ivy;
    }
    public void point2(gameObject i, gameObject x){
                colliding = true;
//                        float ivx = i.velx * -0.15F;
                        double ivy = i.vely * -0.05F;
//                        float xvx = x.velx * -0.15F;
//                        float xvy = x.vely * -0.15F;
                        i.x = i.x + i.velx * -0.15F;
                        i.y = i.y + i.vely * -1F;
                        //i.velx = ivx;
                        i.vely = ivy;
    }
    int po = 0;
    private void checkAdvancedCollisions(objectManager o, gameObject i){
        if(!i.masterParent.engine_collisions || !collisions){
            return;
        }
        //checkAdvancedCollisions(o, i, x+(velx / 2), y+(vely / 2));
        LinkedList<String> ignore = tag;
        for(gameObject ga : children){
            for(String tag : ga.getTag()){
                if(!ignore.contains(tag)){
                    ignore.add(tag);
                }
            }
        }
        for(int xc : new Range(size)){
            for(int yc : new Range(size)){
                if((o.colliding((int)Math.round(i.x + xc), (int)Math.round(i.y + yc), ignore)) || (o.colliding((int)Math.ceil(i.x + xc), (int)Math.ceil(i.y + yc), ignore)) || (o.colliding((int)Math.floor(i.x + xc), (int)Math.floor(i.y + yc), ignore))){
//##                if(o.colliding((int)Math.round(i.x + xc), (int)Math.round(i.y + yc), ignore)){
//                    point1(i, o.collidingGA(xc, yc, ignore));
                    this.x = this.x + velx * -1;
                    this.y = this.y + vely * -1;
                    
                    double gy = masterParent.engine_gravity.y;
                    double gx = masterParent.engine_gravity.x;
                    this.velx = this.velx * -0;
                    this.vely = this.vely * -0;
                    if(!((o.colliding((int)Math.round(i.x + xc - gx), (int)Math.round(i.y + yc - gy), ignore)) || (o.colliding((int)Math.ceil(i.x + xc - gx), (int)Math.ceil(i.y + yc - gy), ignore)) || (o.colliding((int)Math.floor(i.x + xc - gx), (int)Math.floor(i.y + yc - gy), ignore)))){
//##                    if(o.colliding((int)Math.round(i.x + xc - gx), (int)Math.round(i.y + yc - gx), ignore)){
                        this.y = this.y - masterParent.engine_gravity.y;
                        this.x = this.x - masterParent.engine_gravity.x;
                    }else{
                        this.y = this.y + masterParent.engine_gravity.y;
                        this.x = this.x + masterParent.engine_gravity.x;
                    }
                    
                    collisionCount++;
                }
                else if(o.colliding((int)Math.round(i.x + xc), (int) Math.ceil(i.y + yc), ignore)){
//                    point2 = true;
//                    point2(i, o.collidingGA((int)Math.round(i.x + xc),(int) Math.round(i.y + yc + 1), ignore));
                }
                else{
                    point2 = false;
                }
            }
        }
    }
    private void checkAdvancedCollisions(objectManager o, gameObject i, double x, double y){
        if(!i.masterParent.engine_collisions || !collisions){
            return;
        }
        LinkedList<String> ignore = tag;
        for(gameObject ga : children){
            for(String tag : ga.getTag()){
                if(!ignore.contains(tag)){
                    ignore.add(tag);
                }
            }
        }
        for(int xc : new Range(size)){
            for(int yc : new Range(size)){
                if(o.colliding((int)Math.round(x + xc), (int)Math.round(y + yc), ignore)){
//                    point1(i, o.collidingGA(xc, yc, ignore));
                    this.x = this.x + velx * -1;
                    this.y = this.y + vely * -1;
                    
                    this.vely = this.vely - masterParent.engine_gravity.y;
                    this.velx = this.velx - masterParent.engine_gravity.x;
                    
                    this.velx = this.velx * -0;
                    this.vely = this.vely * -0;
                    collisionCount++;
                }
                else if(o.colliding((int)Math.round(x + xc), (int) Math.ceil(y + yc), ignore)){
                    //point2 = true;
                    //point2(i, o.collidingGA((int)Math.round(x + xc),(int) Math.round(y + yc + 1), ignore));
                }
                else{
                    colliding = false;
                    point2 = false;
                }
            }
        }
    }
    public void setID(int ID){
        this.id = id;
    }
    public void checkCollisions(objectManager o, gameObject i){
        LinkedList<gameObject> tmpoa = o.getObjects();
        if(this.collision_type == 1){
            dVector[] dirs = Vector.dir();
            int result = 0;
            dVector cursor = new dVector(i.x, i.y);
            if(o.colliding((int)cursor.x,(int) cursor.y, i.tag.get(0))){result++;}
            for(int d : new Range(dirs.length)){
                cursor = new dVector(i.x + dirs[d].x, i.y + dirs[d].y);
                if(o.colliding((int)cursor.x,(int) cursor.y, i.tag.get(0))){result++;}
            }
            if(result != 0){
                colliding = true;
            }
        }
        else{
    //        for(gameObject i : tmpoa){
                for(gameObject x : tmpoa){
                    if(i.getID() == x.getID() || children.contains(x)){

                    }
                    else{
                        int x1 = (int) round(i.getX());
                        int y1 = (int) round(i.getY());

                        double x2 = x.getX();
                        double y2 = x.getY();

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
    public void noclip(){
        
    }
    public void doOnPreciseMovement(){
        
    }
}
