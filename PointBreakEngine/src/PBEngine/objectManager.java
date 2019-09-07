/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;


import static java.lang.Math.round;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class objectManager {
    LinkedList<gameObject> objects = new LinkedList<>();
//    gameObject[] obs = new gameObject[];
//    ArrayList<gameObject> objects = new ArrayList<gameObject>();
//    ArrayList<Player> players = new ArrayList<Player>();
    
//    gameObject tmpg;
//    Object tmpg;
    objectContainer tmp;
    int index = 0;
    kick kick;
    public objectManager(kick k){
        this.kick = k;
    }
    public void addObject(gameObject tmpO){
        this.objects.add(tmpO);
    }
    
    public void removeObject(gameObject object){
        this.objects.remove(object);
    }
    
//    public String getTypebyTag(String tag){
//        return(objects.get(findGameObject(tag)));
//    }
    
    public gameObject getObjectByTag(String tagToGet){
        return(objects.get(findGameObject(tagToGet)));
    }
    public LinkedList<gameObject> getObjectsByTag(String tagToGet){
        LinkedList<gameObject> out = new LinkedList<gameObject>();
        for(int i : findGameObjects(tagToGet)){
            out.add(objects.get(i));
        }
        return out;
    }
    public int findGameObject(String tagToGet){
        for(int i = 0;i<objects.size();i++){
            gameObject tmp = objects.get(i);
            if(tmp.getTag().contains(new String(tagToGet))){
                return(i);
            }
        }
        //System.out.println("No gameobject with tag: " + tagToGet);
        return(99999999);
    }
    public LinkedList<Integer> findGameObjects(String tagToGet){
        LinkedList<Integer> out = new LinkedList<>();
        for(int i = 0;i<objects.size();i++){
            gameObject tmp = objects.get(i);
            if(tmp.getTag().contains(new String(tagToGet))){
                out.add(i);
            }
        }
        //System.out.println("No gameobject with tag: " + tagToGet);
        return(out);
    }
    public LinkedList<gameObject> getObjects(){
        LinkedList<gameObject> tmpob = new LinkedList<gameObject>();
        for(int i = 0;i<objects.size();i++){
            gameObject tmp = objects.get(i);
            tmpob.add(tmp);
        }
        return(tmpob);
    }
    public boolean colliding(int x, int y, String ignore){
        for(gameObject i : objects){
            if(i.getTag().contains("cursor") || i.getTag().contains(ignore)){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return true;
                }
            }
        }
        return false;
    }
    public gameObject collidingGA(int x, int y, String ignore){
        for(gameObject i : objects){
            if(i.getTag().contains("cursor") || i.getTag().contains(ignore)){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public boolean colliding(int x, int y, LinkedList<String> ignore){
        for(gameObject i : objects){
            if(i.getTag().contains("cursor") || containsany(ignore, i.getTag())){}
            else{//System.out.println(i.getTag().get(0));
                if((round(i.x) == x && round(i.y) == y)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean containsany(LinkedList<String> list, LinkedList<String> st){
        for(String s : list){
            if(st.contains(s)){
                return true;
            }
        }
        return false;
    }
    public gameObject collidingGA(int x, int y, LinkedList<String> ignore){
        for(gameObject i : objects){
            if(i.getTag().contains("cursor") || containsany(ignore, i.getTag())){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public void removeLevel(){
        for(gameObject ga : objects){
            if(ga.getTag().contains(new String("static"))){
                objects.remove(ga);
            }
        }
    }
    public char[][] getCollisionmap(dVector min, dVector max, String origin){
        int sizex = (int) (max.x - min.x);
        int sizey = (int) (max.y - min.y);
        char[][] map = new char[sizex][sizey];
        for(int xp : new Range(map.length)){
            for(int yp : new Range(map[0].length)){
                map[xp][yp] = '1';
                if(colliding(xp, yp, origin)){
                    map[xp][yp] = '0';
                }
            }
        }
        return map;
    }
    
//  public ArrayList<Player> getPlayers(){
//       return(this.players);
//  }
}
