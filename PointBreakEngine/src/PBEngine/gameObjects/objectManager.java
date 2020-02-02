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

package PBEngine.gameObjects;


import JFUtils.Range;
import JFUtils.point.Point2D;
import PBEngine.Supervisor;
import static java.lang.Math.round;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class objectManager {
    private LinkedList<gameObject> objects = new LinkedList<>();
//    gameObject[] obs = new gameObject[];
//    ArrayList<gameObject> objects = new ArrayList<gameObject>();
//    ArrayList<Player> players = new ArrayList<Player>();
    
//    gameObject tmpg;
//    Object tmpg;
    objectContainer tmp;
    int index = 0;
    Supervisor kick;
    public objectManager(Supervisor k){
        this.kick = k;
    }
    //public boolean showDuplicateIDWarning = false;
    public boolean showNoIDWarning = false;
    public void addObject(gameObject tmpO){
        if(getObjectByID(tmpO.getID()) == null){
            this.objects.add(tmpO);
            if(showNoIDWarning){
                System.out.println("    (adding gameobject succesful)");
            }
        }
        else{
            tmpO.setID(getUsableID());
            this.objects.add(tmpO);
        }
    }
    public int getUsableID(){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) objects.clone();
        int id = 0;
        LinkedList<Integer> used = new LinkedList<>();
        copy.forEach((x) -> {
            used.add(x.getID());
        });
        boolean found = false;
        while(!found){
            if(used.contains((Integer)id)){
                id++;
            }
            else{
                found = true;
            }
        }
        return id;
    }
    public void removeObject(gameObject object){
        this.objects.remove(object);
    }
    
//    public String getTypebyTag(String tag){
//        return(objects.get(findGameObject(tag)));
//    }
    
    public gameObject getObjectByTag(String tagToGet){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        return(copy.get(findGameObject(tagToGet)));
    }
    public gameObject getObjectByID(int ID){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        LinkedList<gameObject> objectsFound = new LinkedList<>();
        gameObject latestFound = null;
        for(gameObject i : objects){
            if(i.getID() == ID){
                objectsFound.add(i);
                latestFound = i;
            }
        }
        if(objectsFound.size() > 1){
            System.out.println("WARNING: MULTIPLE OBJECTS WITH THE SAME ID (ID " + ID + "): ");
            for(gameObject x : objectsFound){
                System.out.println("    "+x.tag.getFirst());
            }
            System.out.println("Returning last added...");
        }
        if(objectsFound.isEmpty() && showNoIDWarning){
            System.out.println("No object with the id "+ID);
        }
        return latestFound;
    }
    public LinkedList<gameObject> getObjectsByTag(String tagToGet){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        LinkedList<gameObject> out = new LinkedList<gameObject>();
        for(int i : findGameObjects(tagToGet)){
            out.add(copy.get(i));
        }
        return out;
    }
    public int findGameObject(String tagToGet){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(int i = 0;i<copy.size();i++){
            gameObject tmp = copy.get(i);
            if(tmp.getTag().contains(new String(tagToGet))){
                return(i);
            }
        }
        //System.out.println("No gameobject with tag: " + tagToGet);
        return(-1);
    }
    public LinkedList<Integer> findGameObjects(String tagToGet){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        LinkedList<Integer> out = new LinkedList<>();
        for(int i = 0;i<copy.size();i++){
            gameObject tmp = copy.get(i);
            if(tmp.getTag().contains(new String(tagToGet))){
                out.add(i);
            }
        }
        //System.out.println("No gameobject with tag: " + tagToGet);
        return(out);
    }
    public LinkedList<gameObject> getFlattenedObjects(){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) objects.clone();
        LinkedList<gameObject> tmpob = new LinkedList<gameObject>();
        for(int i = 0;i<copy.size();i++){
            gameObject tmp = copy.get(i);
            if(objectContainer.class.isInstance(tmp)){
                objectContainer tmz = (objectContainer) tmp;
                tmz.objects.forEach(l -> tmpob.add(l));
            }
            else{
                tmpob.add(tmp);
            }
        }
        return(tmpob);
    }
    public boolean colliding(int x, int y, String ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || i.getTag().contains(ignore) || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean colliding(int x, int y, int ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || i.getID() == ignore || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return true;
                }
            }
        }
        return false;
    }
    public gameObject collidingGA(int x, int y, String ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || i.getTag().contains(ignore) || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public gameObject collidingGA(int x, int y, int ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || i.getID() == ignore || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public gameObject collidingGA(int x, int y, LinkedList<Integer> ignore, Object IDONOTSUPPORTTHISBADPRACTISE){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || ignore.contains(i.getID()) || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public boolean circleColliding(double x, double y, double r, LinkedList<String> ignore, Object thisIsABadWayToCodeAndIHateIt){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            try {
                if (i.getTag().contains("cursor") || containsany(ignore, i.getTag()) || i.getTag().contains("nocoll")) {
                } else {//System.out.println(i.getTag().get(0));
                    Point2D a = new Point2D(x, y);
                    Point2D b = new Point2D(i.x, i.y);
                    if (JFUtils.math.General.distance(a, b) < r) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
    public boolean circleColliding(double x, double y, double r, LinkedList<Integer> ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            try {
                if (i.getTag().contains("cursor") || ignore.contains(i.getID()) || i.getTag().contains("nocoll")) {
                } else {//System.out.println(i.getTag().get(0));
                    Point2D a = new Point2D(x, y);
                    Point2D b = new Point2D(i.x, i.y);
                    if (JFUtils.math.General.distance(a, b) < r) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
    public boolean colliding(int x, int y, LinkedList<String> ignore){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            try {
                if (i.getTag().contains("cursor") || containsany(ignore, i.getTag()) || i.getTag().contains("nocoll")) {
                } else {//System.out.println(i.getTag().get(0));
                    if ((round(i.x) == x && round(i.y) == y)) {
                        return true;
                    }
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
    public boolean colliding(int x, int y, LinkedList<Integer> ignore, Object thisObjectIsNotNeededAndIRegretIt){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            try {
                if (i.getTag().contains("cursor") || ignore.contains(i.getID()) || i.getTag().contains("nocoll")) {
                } else {//System.out.println(i.getTag().get(0));
                    if ((round(i.x) == x && round(i.y) == y)) {
                        return true;
                    }
                }
            } catch (Exception e) {
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
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        for(gameObject i : copy){
            if(i.getTag().contains("cursor") || containsany(ignore, i.getTag()) || i.getTag().contains("nocoll")){}
            else{
                if((round(i.x) == x && round(i.y) == y)){
                    return i;
                }
            }
        }
        return null;
    }
    public LinkedList<gameObject> removeLevel(){
        @SuppressWarnings("unchecked")
        LinkedList<gameObject> copy = (LinkedList<gameObject>) getFlattenedObjects().clone();
        LinkedList<gameObject> out = new LinkedList<>();
        for(gameObject ga : copy){
            if(!ga.tag.contains("delete_lc")){
            } else {
                out.add(objects.get(objects.indexOf(ga)));
                removeObject(ga);
            }
        }
        return out;
    }
    public char[][] getCollisionmap(Point2D min, Point2D max, String ignore){
        int sizex = (int) (max.x - min.x);
        int sizey = (int) (max.y - min.y);
        char[][] map = new char[sizex][sizey];
        for(int xp : new Range(map.length)){
            for(int yp : new Range(map[0].length)){
                map[xp][yp] = '1';
                if(colliding(xp, yp, ignore)){
                    map[xp][yp] = '0';
                }
            }
        }
        return map;
    }
    private boolean contains(LinkedList<String> l, String target){
        for(String i : l){
            if(i.equals(target)){
                return true;
            }
        }
        return false;
    }
    /*public void add(gameObject toAdd){
    if(getObjectByID(toAdd.getID()) == null){
    objects.add(toAdd);
    }
    }
    public void remove(gameObject toRemove){
    objects.remove(toRemove);
    }*/
//  public ArrayList<Player> getPlayers(){
//       return(this.players);
//  }
}
