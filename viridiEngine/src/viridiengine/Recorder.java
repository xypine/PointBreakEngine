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
public class Recorder {
    public LinkedList<Vector> locations;
    public LinkedList<LinkedList<Vector>> locationsByTime;
    public LinkedList<Vector> lastLocations;
    public LinkedList<gameObject> gameObjects;
    private LinkedList<container> changed = new LinkedList<>();
    private String out;
    public int frame = 0;
    public Recorder(){
        locations = new LinkedList<>();
        gameObjects = new LinkedList<>();
        lastLocations = new LinkedList<>();
    }
    public void record(objectManager oM){
        lastLocations = locations;
        locations.clear();
        for(gameObject i : oM.getObjects()){
            locations.add(i.getLocation());
        }
        if(locations.toString().equals(lastLocations.toString())){}
        else{
            int index = 0;
            changed.clear();
            for(Vector i : locations){
                if(i.equals(lastLocations.get(index))){}
                else{
                    changed.add(new container(Vector.subtract(i, lastLocations.get(index)), oM.getObjects().get(index).getID()) );
                }
                index++;
            }
            System.out.println(changed);
        }
        System.out.println("frame " + frame);
        frame++;
    }
    class container{
        public Vector location;
        public int id;
        public container(Vector v, int ta){
            this.location = v;
            this.id = ta;
        }
    }
}
