/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.Math.round;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        try{
            gameObject z = oM.getObjectByTag("player1");
            //recorded.add( new Vector(z.getX(), z.getY()));
        }
        catch(Exception e){
            //System.out.println("couldn't record");
        }
        //System.out.println("frame: " + frame + ": " + recorded.getLast().x);
        frame++;
    }
    public void write(LinkedList<Vector> g, String file) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        System.out.println(System.getProperty("user.dir") + "/" + file + "  " + g.size());
        String tmp = "";
        int idi = 90;
        for(Vector p : g){
            tmp = tmp + p.x + "l" + p.y + ":";
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + file), "utf-8"))) {
            writer.write(tmp);
        }
    }
    String filePath = new File("").getAbsolutePath();
    public LinkedList<Vector> read(String file){
        System.out.println("trying to reconstruct recording [" + filePath.concat(file)+"]");
        try {
            Scanner in = new Scanner(new FileReader(filePath.concat(file)));
            String text = "";
            while (in.hasNextLine()) {
                String line = in.nextLine();
                text = text + line;
            }
            in.close();
            int l = 0;
            String ax = "";
            String ay = "";
            LinkedList<Vector> out = new LinkedList<>();
            int framer =0;
            for(char x : text.toCharArray())
            {
                if(x == ':')
                {
                    l = 0;
                    //out.add(new Vector(Float.parseFloat(ax), Float.parseFloat(ay)));
                    //System.out.println("Read frame "+framer+ ": " + Float.parseFloat(ax) + " " + Float.parseFloat(ay));
                    ax = "";
                    ay = "";
                    framer++;
                }
                else if(x == 'l'){
                    l = 1;
                }
                else if(l == 0){
                    if(x == '.'){ax = ax + ".";}
                    else{ax = ax + x;}
                }
                else if(l == 1){
                    if(x == '.'){ay = ay + ".";}
                    else{ay = ay + x;}
                }
            }
            System.out.println("Loaded record ["+file+"] with the lenght of "+out.size()+" frames");
            return(out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Recorder.class.getName()).log(Level.SEVERE, null, ex);
            return new LinkedList<Vector>();
        }
    }
    class container{
        public Vector location;
        public int id;
        public container(Vector v, int ta){
            this.location = v;
            this.id = ta;
        }
    }
    public int toInt(String som){
        String result = "";
        for (int i = 0; i < som.length(); i++) {
            Character character = som.charAt(i);
            if (Character.isDigit(character)) {
                result = result + character;
            }
        }
        return Integer.parseInt(result);
    }
}
