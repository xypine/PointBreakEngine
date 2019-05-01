/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.Math.round;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class Recorder {
    public LinkedList<Vector> recorded = new LinkedList<>();
    public LinkedList<LinkedList<Vector>> locationsByTime;
    public LinkedList<Vector> lastLocations;
    public LinkedList<gameObject> gameObjects;
    private LinkedList<container> changed = new LinkedList<>();
    private String out;
    public int frame = 0;
    public Recorder(){
//        locations = new LinkedList<>();
//        gameObjects = new LinkedList<>();
//        lastLocations = new LinkedList<>();
    }
    public void record(objectManager oM){
        gameObject z = oM.getObjectByTag("player1");
        try{
        recorded.add( new Vector(z.getX(), z.getY()));
        }
        catch(Exception e){System.out.println("");}
        //System.out.println("frame: " + frame + ": " + recorded.getLast().x);
        frame++;
    }
    public void write(LinkedList<Vector> g, String file) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        System.out.println(System.getProperty("user.dir") + "/" + file);
        String tmp = "";
        int idi = 90;
        for(Vector p : g){
            tmp = tmp + p.x + "l" + p.y + ":";
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + file), "utf-8"))) {
            writer.write(tmp);
        }
    }
    public void read(){
        
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
