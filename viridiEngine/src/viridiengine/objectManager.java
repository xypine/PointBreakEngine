/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;


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
        System.out.println("No gameobject with tag: " + tagToGet);
        return(999999);
    }
    public LinkedList<gameObject> getObjects(){
        LinkedList<gameObject> tmpob = new LinkedList<gameObject>();
        for(int i = 0;i<object.size();i++){
            gameObject tmp = object.get(i);
            tmpob.add(tmp);
        }
        return(tmpob);
    }
//  public ArrayList<Player> getPlayers(){
//       return(this.players);
//  }
}
