/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;

import java.awt.Color;
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
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonnelafin
 */
public class levelLoader {
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
    String filePath = new File("").getAbsolutePath();
    public levelLoader(String file, objectManager oM) throws URISyntaxException{
        try {
            Scanner in = new Scanner(new FileReader(filePath.concat(file)));
            String text = "";
            while (in.hasNextLine()) {
                String line = in.nextLine();
                text = text + line;
            }
            in.close();
            fetch(text, oM);
            System.out.println("Level ["+filePath.concat(file)+"] loaded with " + count + " objects!");
//            fetch(in.toString(), oM);
        } catch (FileNotFoundException ex) {
            try{
            fetch(fallback, oM);
            System.out.println("fallback level loaded with " + count + " objects!");
//            fetch(in.toString(), oM);
            }
            catch(Exception o){
                Logger.getLogger(levelLoader.class.getName()).log(Level.SEVERE, null, o);
            }
            
        }
    }
    
    public void fetch(String i, objectManager oM){
        String tmp = "";
        for(char x : i.toCharArray()){
            
            if(x == ':'){
                gameObject tm = new gameObject(this.x, this.y, this.tag, this.appereance, this.mass, this.c, this.id);
                oM.addObject(tm);
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
    }
    public void write(LinkedList<gameObject> g, String file) throws FileNotFoundException, UnsupportedEncodingException, IOException{
        System.out.println(System.getProperty("user.dir") + "/" + file);
        String tmp = "";
        int idi = 90;
        for(gameObject p : g){
            if(!p.getTag().contains(new String("cursor"))){
                tmp = tmp + round(p.x) + "." + round(p.y) + ".static.█.1.green." + idi + ".:";
                idi++;
            }
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + file), "utf-8"))) {
            writer.write(tmp);
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
    public static Color getColorByName(String name) {
        try {
            return (Color)Color.class.getField(name.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    String fallback = "21.24.static.█.1.green.90.:21.23.static.█.1.green.91.:21.22.static.█.1.green.92.:21.21.static.█.1.green.93.:21.20.static.█.1.green.94.:21.19.static.█.1.green.95.:22.19.static.█.1.green.96.:23.19.static.█.1.green.97.:24.19.static.█.1.green.98.:25.19.static.█.1.green.99.:26.19.static.█.1.green.100.:27.19.static.█.1.green.101.:28.19.static.█.1.green.102.:29.19.static.█.1.green.103.:30.19.static.█.1.green.104.:30.20.static.█.1.green.105.:30.21.static.█.1.green.106.:30.22.static.█.1.green.107.:30.23.static.█.1.green.108.:30.24.static.█.1.green.109.:49.19.static.█.1.green.110.:48.19.static.█.1.green.111.:47.19.static.█.1.green.112.:46.19.static.█.1.green.113.:45.19.static.█.1.green.114.:44.19.static.█.1.green.115.:43.19.static.█.1.green.116.:43.18.static.█.1.green.117.:43.17.static.█.1.green.118.:43.16.static.█.1.green.119.:43.15.static.█.1.green.120.:43.14.static.█.1.green.121.:44.14.static.█.1.green.122.:45.14.static.█.1.green.123.:46.14.static.█.1.green.124.:47.14.static.█.1.green.125.:48.14.static.█.1.green.126.:49.14.static.█.1.green.127.:14.0.static.█.1.green.128.:13.1.static.█.1.green.129.:12.2.static.█.1.green.130.:11.3.static.█.1.green.131.:11.20.static.█.1.green.132.:11.19.static.█.1.green.133.:10.18.static.█.1.green.134.:9.17.static.█.1.green.135.:8.16.static.█.1.green.136.:8.15.static.█.1.green.137.:33.11.static.█.1.green.138.:32.11.static.█.1.green.139.:31.11.static.█.1.green.140.:30.11.static.█.1.green.141.:29.11.static.█.1.green.142.:28.11.static.█.1.green.143.:49.4.static.█.1.green.144.:48.4.static.█.1.green.145.:47.4.static.█.1.green.146.:46.4.static.█.1.green.147.:45.4.static.█.1.green.148.:44.4.static.█.1.green.149.:44.3.static.█.1.green.150.:45.3.static.█.1.green.151.:46.3.static.█.1.green.152.:47.3.static.█.1.green.153.:48.3.static.█.1.green.154.:49.3.static.█.1.green.155.:49.5.static.█.1.green.156.:48.5.static.█.1.green.157.:47.5.static.█.1.green.158.:46.5.static.█.1.green.159.:45.5.static.█.1.green.160.:44.5.static.█.1.green.161.:43.5.static.█.1.green.162.:43.4.static.█.1.green.163.:43.3.static.█.1.green.164.:";
}
