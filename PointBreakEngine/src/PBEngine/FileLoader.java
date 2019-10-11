/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import static java.lang.Math.round;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonnelafin
 */
public class FileLoader {
    public boolean done = true;
    private directory dir= new directory();
    private kick master;
    private int dotC = 0;
    private boolean inSentence = false;
    public int count = 0;
    int x = 0;
    int y = 0;
    int mass = 1;
    String tag;
    String appereance;
    Color c;
    int id;
    String filePath = dir.levels;
    
    List<String> levels;
    public FileLoader(String file, objectManager oM, kick master) throws URISyntaxException{
        this.done = false;
        levels = getLevels(filePath);
        this.master = master;
        if(file == "null"){
            return;
        }
        
        String arr[] = file.split(" ", 2);
        
        
        String text = file;
        
        try {
            if(!arr[0].equals("!random")){
                Scanner in = new Scanner(new FileReader(filePath + file));
                text = "";
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    text = text + line;
                }
                in.close();
            }
        }
        catch (FileNotFoundException ex) {
                try{
                    fetch(fallback, oM, master.Logic.rads);
                    System.out.println("!!! level " + filePath + file + " FAILED TO LOAD!!!");
                    quickTools.alert("Level not found!", "!!! level " + filePath + file + " FAILED TO LOAD!!!");
                    System.out.println("fallback level loaded with " + count + " objects!");
    //            fetch(in.toString(), oM);
                }
                catch(Exception o){
                    Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, o);
                    quickTools.alert("LevelLoading", o.getMessage());
                }
            }
        if(arr[0].equals("!random")){
            text = file;
        }
        fetch(text, oM, master.Logic.rads);
        System.out.println("Level ["+filePath.concat(file)+"] loaded with " + count + " objects!");
//            fetch(in.toString(), oM);
        
    }
    public FileLoader(String file, objectManager oM, kick master, String filepath1) throws URISyntaxException{
        this.done = false;
        levels = getLevels(filePath);
        this.master = master;
        if(file == "null"){
            return;
        }
        
        String arr[] = file.split(" ", 2);
        
        
        String text = file;
        
        try {
            if(!arr[0].equals("!random")){
                Scanner in = new Scanner(new FileReader(filepath1 + file));
                text = "";
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    text = text + line;
                }
                in.close();
            }
        }
        catch (FileNotFoundException ex) {
                try{
                    fetch(fallback, oM, master.Logic.rads);
                    System.out.println("!!! level " + filePath + file + " FAILED TO LOAD!!!");
                    quickTools.alert("Level not found!", "!!! level " + filePath + file + " FAILED TO LOAD!!!");
                    System.out.println("fallback level loaded with " + count + " objects!");
    //            fetch(in.toString(), oM);
                }
                catch(Exception o){
                    Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, o);
                    quickTools.alert("LevelLoading", o.getMessage());
                }
            }
        if(arr[0].equals("!random")){
            text = file;
        }
        fetch(text, oM, master.Logic.rads);
        System.out.println("Level ["+filePath.concat(file)+"] loaded with " + count + " objects!");
//            fetch(in.toString(), oM);
        
    }
    public LinkedList<gameObject> level = new LinkedList<>();
    public void fetch(String i, objectManager oM, VSRadManager rads){
        LinkedList<gameObject> newObjects = new LinkedList<>();
        boolean meta = false;
        int metachar = 0;
        char version = 0;
        String name = "";
        String tmp = "";
        String arr[] = i.split(" ", 2);
        int xd = rads.masterParent.xd;
        int yd = rads.masterParent.yd;
        if(arr[0].equals("!random")){
            arr[1] = arr[1].replaceAll(".pblevel", "");
            Random rnd = new Random(Integer.parseInt(arr[1]));
            rnd.setSeed(Integer.parseInt(arr[1]));
            for(int y : new Range(Integer.parseInt(arr[1]))){
                int xp = (int) (Math.random() * ( xd - 0 ));
                int yp = (int) (Math.random() * ( yd - 0 ));
                
                //xd = rnd.nextInt(xd);
                //yd = rnd.nextInt(yd);
                
                gameObject tm = new gameObject(xp, yp, 1, "static", this.appereance, this.mass, Color.black, this.id, master);
                tm.imageName = dir.textures + "walls/walls0.png";
                newObjects.add(tm);
                id++;
                count++;
            }
            return;
        }
        for(char x : i.toCharArray()){
            if(meta){
                switch(metachar){
                    case 0:
                        version = x;metachar++;
                        break;
                    case 13:
                        meta = false;
                        System.out.println("Loading level that was created with version " + version +", level: " + name);
                        break;
                    default:
                        if(x != '*'){
                            name = name + x;
                        }metachar++;
                    
                }
            }
            if(x == '#'){
                meta = true;
            }
            else if(x == ':'){                                                                          //this.c
                //if(tag == "light"){rads.add(x, y, mass, c, 1, false);}
                if(tag.equals("static")){gameObject tm = new gameObject(this.x, this.y, 1, this.tag, this.appereance, this.mass, Color.black, this.id, master);
                tm.imageName = dir.textures + "walls/walls0.png";
                newObjects.add(tm);}
                count++;
                //System.out.println(tm.getTag());
                dotC = 0;
                tmp = "";
                //System.out.println(this.tag);
            }
            else if(x == '.'){
//                System.out.println(tmp);
                if(dotC == 0){
                    this.x = toInt(tmp);
                }
                if(dotC == 1){
                    this.y = toInt(tmp);
                }
                if(dotC == 2){
                    this.tag = tmp;
                }
                if(dotC == 3){
                    this.appereance = tmp;
                }
                if(dotC == 4){
                    this.mass = toInt(tmp);
                }
                if(dotC == 5){
                    this.c = getColorByName(tmp);
                }
                if(dotC == 6){
                    this.id = toInt(tmp);
                }
                dotC++;
                tmp = "";
            }
            else{
                tmp = tmp + x;
            }
            
            
        }
        this.level = newObjects;
        this.done = true;
        //try{rads.recalculate();}catch(Exception e){quickTools.alert("FAILED TO RECALCULATE VSRAD, nullpointerexception?", e.getMessage());throw e;}
    }
    public void write(LinkedList<gameObject> g, String file) throws FileNotFoundException, UnsupportedEncodingException, IOException{

        System.out.println("Saving level to: "+dir.levels + file + "...");
        System.out.println("");

        String tmp = "";
        int idi = 90;
        for(gameObject p : g){
            if(p.getTag().contains("static")){
                tmp = tmp + round(p.x) + "." + round(p.y) + ".static.█.1.green." + idi + ".:";
                System.out.print(".");
                idi++;
            }
            else if(p.getTag().contains("light")){
                tmp = tmp + round(p.x) + "." + round(p.y) + ".light.█."+(int)(p.mass)+".green." + idi + ".:";
                System.out.print(".");
                idi++;
            }
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir.levels + file), "utf-8"))) {
            writer.write(tmp);
        }
    }
    public void write(LinkedList<gameObject> g, String file, String filepath1) throws FileNotFoundException, UnsupportedEncodingException, IOException{

        System.out.println("Saving level to: "+filepath1 + file + "...");
        System.out.println("");

        String tmp = "";
        int idi = 90;
        for(gameObject p : g){
            if(p.getTag().contains("light")){
                tmp = tmp + round(p.x) + "." + round(p.y) + ".light.█."+p.mass+".green." + idi + ".:";
                System.out.print("*");
                idi++;
            }
            else if(p.getTag().contains("static")){
                tmp = tmp + round(p.x) + "." + round(p.y) + ".static.█.1.green." + idi + ".:";
                System.out.print(".");
                idi++;
            }
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath1 + file), "utf-8"))) {
            writer.write(tmp);
        }
    }
    public void writeObject(Object o, String file) throws FileNotFoundException, IOException{
      ObjectOutputStream objOut = new ObjectOutputStream(new
              ///"out_lights.txt"
      FileOutputStream(dir.levels + file));
      objOut.writeObject(o);
      objOut.close();
    }
    public Object readObject(String file) throws IOException, ClassNotFoundException{
        Object out;
        FileInputStream fileIn =new FileInputStream(dir.levels + file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
            out = in.readObject();
            in.close();
            fileIn.close();
        return out;
    }
    public int toInt(String som){
        String result = "";
        for (int i = 0; i < som.length(); i++) {
            Character character = som.charAt(i);
            if (Character.isDigit(character)) {
                result = result + character;
            }
        }
        int out = 0;
        try {
            out = Integer.parseInt(result);
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ERROR WITH toInt: "+numberFormatException);
        }
        return out;
    }
    public static Color getColorByName(String name) {
        try {
            return (Color)Color.class.getField(name.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            quickTools.alert(name);
            e.printStackTrace();
            return null;
        }
    }
    static String readFile(String path, Charset encoding) throws IOException 
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    public static LinkedList<String> readConfig(String FileName){
        LinkedList<String> out = new LinkedList<>();
        try {
            
            String outa = readFile(new directory().root + FileName, Charset.defaultCharset());
            String current = "";
            for(int x : new Range(outa.length())){
                char i = outa.charAt(x);
                if(i == '\n'){
                    out.add(current);
                    current = "";
                }
                else{
                    current = current + i;
                }
            }
            
        } catch (IOException iOException) {
        }return out;
    }
    public static List<String> getLevels(String dir){
        List<String> results = new ArrayList<String>();


        File[] files = new File(dir).listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 
        
        System.out.println("Probing directory "+dir+" for levels:");
        for (File file : files) {
            if (file.isFile()) {
                try {
                    if (file.getName().endsWith(".pblevel")) {
                        System.out.println("    "+file.getName());
                    }
                } catch (Exception e) {
                }
                results.add(file.getName());
            }
        }
        System.out.println("Probe complete.");
        return results;
    }
    public static String getLevelMap(String name){
        try {
            return readFile(new directory().levels + name, Charset.defaultCharset());
        } catch (IOException ex) {
            System.out.println("Unable to load levelmap!");
            return "";
        }
    }
    
    String empty = "";
    String fallback = "21.24.static.█.1.green.90.:21.23.static.█.1.green.91.:21.22.static.█.1.green.92.:21.21.static.█.1.green.93.:21.20.static.█.1.green.94.:21.19.static.█.1.green.95.:22.19.static.█.1.green.96.:23.19.static.█.1.green.97.:24.19.static.█.1.green.98.:25.19.static.█.1.green.99.:26.19.static.█.1.green.100.:27.19.static.█.1.green.101.:28.19.static.█.1.green.102.:29.19.static.█.1.green.103.:30.19.static.█.1.green.104.:30.20.static.█.1.green.105.:30.21.static.█.1.green.106.:30.22.static.█.1.green.107.:30.23.static.█.1.green.108.:30.24.static.█.1.green.109.:49.19.static.█.1.green.110.:48.19.static.█.1.green.111.:47.19.static.█.1.green.112.:46.19.static.█.1.green.113.:45.19.static.█.1.green.114.:44.19.static.█.1.green.115.:43.19.static.█.1.green.116.:43.18.static.█.1.green.117.:43.17.static.█.1.green.118.:43.16.static.█.1.green.119.:43.15.static.█.1.green.120.:43.14.static.█.1.green.121.:44.14.static.█.1.green.122.:45.14.static.█.1.green.123.:46.14.static.█.1.green.124.:47.14.static.█.1.green.125.:48.14.static.█.1.green.126.:49.14.static.█.1.green.127.:14.0.static.█.1.green.128.:13.1.static.█.1.green.129.:12.2.static.█.1.green.130.:11.3.static.█.1.green.131.:11.20.static.█.1.green.132.:11.19.static.█.1.green.133.:10.18.static.█.1.green.134.:9.17.static.█.1.green.135.:8.16.static.█.1.green.136.:8.15.static.█.1.green.137.:33.11.static.█.1.green.138.:32.11.static.█.1.green.139.:31.11.static.█.1.green.140.:30.11.static.█.1.green.141.:29.11.static.█.1.green.142.:28.11.static.█.1.green.143.:49.4.static.█.1.green.144.:48.4.static.█.1.green.145.:47.4.static.█.1.green.146.:46.4.static.█.1.green.147.:45.4.static.█.1.green.148.:44.4.static.█.1.green.149.:44.3.static.█.1.green.150.:45.3.static.█.1.green.151.:46.3.static.█.1.green.152.:47.3.static.█.1.green.153.:48.3.static.█.1.green.154.:49.3.static.█.1.green.155.:49.5.static.█.1.green.156.:48.5.static.█.1.green.157.:47.5.static.█.1.green.158.:46.5.static.█.1.green.159.:45.5.static.█.1.green.160.:44.5.static.█.1.green.161.:43.5.static.█.1.green.162.:43.4.static.█.1.green.163.:43.3.static.█.1.green.164.:";
}
