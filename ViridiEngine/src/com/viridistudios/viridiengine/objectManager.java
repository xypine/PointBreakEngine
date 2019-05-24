/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;


import static java.lang.Math.round;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class objectManager {
    LinkedList<gameObject> object = new LinkedList<gameObject>();
//    gameObject[] obs = new gameObject[];
//    ArrayList<gameObject> objects = new ArrayList<gameObject>();
//    ArrayList<Player> players = new ArrayList<Player>();
    
//    gameObject tmpg;
//    Object tmpg;
    objectContainer tmp;
    int index = 0;
    public void addObject(gameObject tmpO){
        this.object.add(tmpO);
    }
    
    public void removeObject(gameObject object){
        this.object.remove(object);
    }
    
//    public String getTypebyTag(String tag){
//        return(object.get(findGameObject(tag)));
//    }
    
    public gameObject getObjectByTag(String tagToGet){
        return(object.get(findGameObject(tagToGet)));
    }
     
    public int findGameObject(String tagToGet){
        for(int i = 0;i<object.size();i++){
            gameObject tmp = object.get(i);
            if(tmp.getTag() == tagToGet){
                return(i);
            }
        }
        //System.out.println("No gameobject with tag: " + tagToGet);
        return(99999999);
    }
    public LinkedList<gameObject> getObjects(){
        LinkedList<gameObject> tmpob = new LinkedList<gameObject>();
        for(int i = 0;i<object.size();i++){
            gameObject tmp = object.get(i);
            tmpob.add(tmp);
        }
        return(tmpob);
    }
    public boolean colliding(int x, int y){
        for(gameObject i : object){
            if(i.getTag() == "cursor"){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return true;
                }
            }
        }
        return false;
    }
    public void removeLevel(){
        for(gameObject ga : object){
            if(ga.getTag() == "static"){
                object.remove(ga);
            }
        }
    }
    public void doPhysics(Renderer r, gameObject i){
        LinkedList<gameObject> tmpoa = this.getObjects();
//        for(gameObject i : tmpoa){
            for(gameObject x : tmpoa){
                if(i.getID() == x.getID()){
                    
                }
                else{
                    int x1 = round(i.getX());
                    int y1 = round(i.getY());
                    
                    float x2 = x.getX();
                    float y2 = x.getY();
                    if(i.getDistance(y2, x2) < 1F){
                        float ivx = i.velx * -0.55F;
                        float ivy = i.vely * -0.55F;
                        float xvx = x.velx * -0.55F;
                        float xvy = x.vely * -0.55F;
                        i.velx = ivx;
                        i.vely = ivy;
                        //x.velx = xvx;
                        //x.vely = xvy;
                    }
//                    i.update(r);
//                    x.update(r);
                }
                
            }
//        }
    }
//  public ArrayList<Player> getPlayers(){
//       return(this.players);
//  }
}
