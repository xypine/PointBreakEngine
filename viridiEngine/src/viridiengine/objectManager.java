/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.util.ArrayList;

/**
 *
 * @author Jonnelafin
 */
public class objectManager {
    ArrayList<gameObject> objects = new ArrayList<gameObject>();
    ArrayList<Player> players = new ArrayList<Player>();
    
    gameObject tmpg;
    Player tmpp;
    public void addObject(int x, int y, String tag, String skin){
        tmpg = new gameObject();
        tmpg.summon(y,x,tag,skin);
        objects.add(tmpg);
        
    }
    public gameObject getObject(String tagToGet){
        return(objects.get(findGameObject(tagToGet)));
    }
     
    
    public void addPlayer(int x, int y, String tag, String skin){
        tmpp = new Player();
        tmpp.summon(y,x,tag,skin);
        players.add(tmpp);
        
    }
    public Player getPlayer(String tagToGet){
        return(players.get(findPlayer(tagToGet)));
    }
    public int findPlayer(String tagToGet){
        for(int i = 0;i<players.size();i++){
            Player tmp = players.get(i);
            if(tmp.getTag() == tagToGet){
                return(i);
            }
        }
        System.out.println("No player with tag: " + tagToGet);
        return(999999);
    }
    public int findGameObject(String tagToGet){
        for(int i = 0;i<objects.size();i++){
            gameObject tmp = objects.get(i);
            if(tmp.getTag() == tagToGet){
                return(i);
            }
        }
        System.out.println("No gameobject with tag: " + tagToGet);
        return(999999);
    }
}
