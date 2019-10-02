/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PBEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author elias
 */
public class devkit extends JFrame{
    public JPanel cont = new JPanel();
    boolean tog = false;
    kick k;
    JButton graphic = new JButton("toggle vector");
    JButton rays = new JButton("toggle rays");
    JLabel time = new JLabel("RAYYYS");
    JTextArea log = new JTextArea("PointBreakEngine devkit");
    JTextField lum = new JTextField(20);
    JScrollPane logs = new JScrollPane(log);
    public devkit(kick k) {
        this.setTitle("PointBreakEngine devkit");
        this.k = k;
        this.setSize(400, 550);
        this.setLocationRelativeTo(k.Logic);
        this.setLocation(1080, 0);
        
        cont.setLayout(new BorderLayout());
        graphic.addActionListener(new BListener(9, this));
        rays.addActionListener(new BListener(2, this));
        lum.addActionListener(new BListener(0, this));
        logs.setWheelScrollingEnabled(true);
        //log.setColumns(1);
        log.setRows(15);
        logs.setWheelScrollingEnabled(true);
        //cont.add(graphic, BorderLayout.NORTH);
        //cont.add(rays, BorderLayout.NORTH);
        cont.add(time, BorderLayout.EAST);
        cont.add(logs, BorderLayout.CENTER);
        cont.add(lum, BorderLayout.SOUTH);
        this.add(cont);
        this.setVisible(true);
    }
    public void togG(){
        if(tog){
            k.Logic.vector = 1;
        }
        else{
            k.Logic.vector = 0;
        }
        tog = !tog;
    }
    boolean togV = true;
    public void togV(){
        if(togV){
            k.Logic.renderRays = 0;
        }
        else{
            k.Logic.renderRays = 1;
        }
        togV = !togV;
    }
}
class BListener implements ActionListener{
    boolean abright = false;
    int type;
    devkit k;
    public BListener(int t, devkit d){
        this.type = t;
        this.k = d;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(type == 9){
            System.out.println("Graphics!");
            k.togG();
        }
        if(type == 2){
            System.out.println("Rays!");
            k.togV();
        }
        try{
            String arr[] = k.lum.getText().split(" ", 2);
            
            if(k.lum.getText().charAt(0) == '/'){
                switch(arr[0]){
                    case "/collisions":
                        if(arr[1].matches("true")){k.k.engine_collisions = true;}
                        else if(arr[1].matches("false")){k.k.engine_collisions = false;}
                        else{
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/blur":
                        k.k.Logic.blurStrenght = Integer.parseInt(arr[1]);
                        break;
                    case "/bright":
                        k.k.Logic.global_brightness = Float.parseFloat(arr[1]);
                        break;
                    case "/tp":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            for(gameObject o : k.k.objectManager.getObjectsByTag(values[0])){
                                o.setLocation(new dVector(x, y));
                            }
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/noclip":
                        k.k.Logic.oM.getObjectByTag("player1").noclip();
                        break;
                    case "/abright":
                        abright = !abright;
                        k.k.Logic.abright = abright;
                        System.out.println(abright + ", " + k.k.Logic.abright);
                        break;
                    case "/add":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            String tag = values[0];
                            k.k.objectManager.addObject(new gameObject(x, y, 1, tag, "N", 1, Color.black, 1919, k.k));
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/rm":
                        String values[] = arr[1].split(" ", 2);
                        for(gameObject o : k.k.objectManager.getObjectsByTag(values[0])){
                                k.k.objectManager.remove(o);
                            }
                        break;
                    case "/relight":
                        VSRadManager vsradm = k.k.rad;
                        vsradm.recalculate("aaaaaaaaaaaaaaa", 1);
                        vsradm.recalculateParent();
                        break;
                    case "/lvl":
                        Thread a = new Thread(){
                            @Override
                            public void run(){
                                try {
                                    k.k.Logic.loadLevel(arr[1] + ".pblevel");
                                } catch (URISyntaxException ex) {
                                    Logger.getLogger(BListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        a.start();
                        break;
                    case "/lvltest":
                        Thread b = new Thread(){
                            @Override
                            public void run(){
                                boolean map = false;
                                double sum = 0;
                                for(int i : new Range(Integer.parseInt(arr[1]))){
                                    String lo = "out2.txt";
                                    if(map){
                                        lo = "shadingtest.txt";
                                    }
                                    try {
                                        k.k.Logic.loadLevel("!random 25");
                                    } catch (URISyntaxException ex) {
                                        Logger.getLogger(BListener.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    sum = sum + k.k.Logic.mapLoadTime;
                                    map = !map;
                                }
                                System.out.println("Map loading time test completed in " + sum + " ms!");
                            }
                        };
                        b.start();
                        break;
                    case "/z":
                        k.k.Logic.Vrenderer.factor = Integer.parseInt(arr[1]);
                        break;
                    case "/lum":
                        k.k.Logic.global_brightness = Integer.parseInt(arr[1]);
                        System.out.println(k.k.Logic.global_brightness);
                        break;
                    case "/tags":
                        int id = Integer.parseInt(arr[1]);
                        System.out.println("Object ID"+id+" tags:");
                        for(String x : k.k.objectManager.getObjectByID(id).getTag()){
                            System.out.println("    "+x);
                        }
                        break;
                    case "/rc_levelmap":
                        k.k.Logic.constructLevelmap();
                        break;
                    case "/map":
                        String current = "!";
                        String cached = "*";
                        String pre = "";
                        System.out.println("Levelmap: ");
                        int longest = 0;
                        //Get the longest namelenght
                        for(String[] lane : k.k.Logic.levelmap){
                            for(String i : lane){
                                if(i.length() > longest){
                                    longest = i.length();
                                }
                            }
                        }
                        
                        String[][] map = null;
                        try{map = new String[k.k.Logic.levelmap.length][k.k.Logic.levelmap[0].length];}
                        catch(Exception e){
                            System.out.println("WARNING: Level map is not initialized properly, skipping action.");
                            break;
                        }
                        for(int i : new Range(k.k.Logic.levelmap.length)){
                            map[i] = k.k.Logic.levelmap[i].clone();
                        }
                        dVector currentmap = k.k.Logic.currentMap;
                        //Mark cached
                        for(int x : new Range(map.length)){
                            for(int y : new Range(map[0].length)){
                                if(k.k.Logic.cachedLevels[x][y] != null){
                                    map[x][y] = cached + map[x][y];
                                }
                            }
                        }
                        //Print the levelmap
                        map[(int)currentmap.x][(int)currentmap.y] = current + map[(int)currentmap.x][(int)currentmap.y];
                        map = quickTools.rotateCW(map);
                        map = quickTools.rotateCW(map);
                        map = quickTools.rotateCW(map);
                        for(String[] lane : map){
                            for(String i : lane){
                                pre = "";
                                String name = i;
                                if(name.contains("\n")){
                                    name = name.replaceAll("\n", "");
                                }
                                
                                
                                String padding = " ".repeat(longest+4-i.length());
                                System.out.print(pre+name+padding);
                            }System.out.println("");
                        }
                        System.out.println("end of levelmap.");
                        break;
                    default:
                        quickTools.alert("devkit", "command not understood");
                        break;
                }
            }
            else{
                System.out.println(k.lum.getText());
            }
        }
        catch(Exception e){}
        k.time.setText(Boolean.toString(k.togV));
    }
    
}
