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
package PBEngine;

import PBEngine.gameObjects.gameObject;
import JFUtils.Range;
import JFUtils.point.Point2D;
import JFUtils.quickTools;
import PBEngine.Rendering.core.renderType;
import JFUtils.graphing.Graph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.LinkedList;
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
 * @author Elias Eskelinen (Jonnelafin)
 */
public interface devkit_interface{
    public JPanel cont = new JPanel();
    boolean tog = false;
    public Supervisor k = null;
    JButton graphic = new JButton("toggle vector");
    JButton rays = new JButton("toggle rays");
    JLabel time = new JLabel("RAYYYS");
    JTextArea log = new JTextArea("PointBreakEngine devkit");
    JTextField lum = new JTextField(20);
    JScrollPane logs = new JScrollPane(log);

    /**
     *
     * @param k the used supervisor, used for the actions of the commands
     * @return 
     */
    public void togG();
    boolean togV = true;
    public void togV();
    public void remove(Component c);
    public void setVisible(boolean b);
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
                    case "/fontS":
                        try {
                            int s = Integer.parseInt(arr[1]);
                            System.out.println("Setting new GUI Font size to [" + s + "].");
                            k.k.setFontSize(s);
                        } 
                        catch (NumberFormatException numberFormatException) {
                            quickTools.alert(arr[1] + " is not a number: " + numberFormatException);
                        }
                        break;
                    case "/coll":
                        if(arr[1].matches("true")){k.k.engine_collisions = true;}
                        else if(arr[1].matches("false")){k.k.engine_collisions = false;}
                        else{
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/blur":
                        k.k.Logic.blurStrenght = Integer.parseInt(arr[1]);
                        break;
                    case "/newGraph":
                        k.k.grapher = new Graph();
                        break;
                    case "/exit":
                        System.out.println("Stopping the current thread...");
                        boolean yeet = k.k.stopAll();
                        System.out.println("Termination succeeded: " + yeet);
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
                                o.setLocation(new Point2D(x, y));
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
                            k.k.objectManager.addObject(new gameObject(x, y, 1, tag, "N", 1, Color.black, k.k.objectManager.getUsableID(), k.k));
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/NID":
                        System.out.println("Next usable ID: " + k.k.objectManager.getUsableID());
                        break;
                    case "/GAList":
                        System.out.println();
                        LinkedList<gameObject> ga = k.k.objectManager.getObjects();
                        System.out.println(ga.size() + " gameobjects in the record of " + k.k.objectManager + ":");
                        System.out.println("---------------------");
                        for(gameObject iz : ga){
                            System.out.println("ID " + iz.getID() + " :");
                            System.out.println("    Tags:");
                            iz.getTag().forEach(l->{
                                System.out.println("        " + l);
                            });
                            System.out.println("    x:  " + iz.x);
                            System.out.println("    y:  " + iz.y);
                            System.out.println("    Children:");
                            iz.getChildren().forEach(l->{
                                System.out.println("        " + l + " (ID" + l.getID() + ")");
                            });
                            System.out.println("ImageName: " + iz.imageName);
                            System.out.println(".....");
                        }
                        System.out.println("---------------------");
                        
                        break;
                    case "/RADList":
                        LinkedList<VSRad> gas = k.k.rad.getSources();
                        System.out.println(gas.size() + " lights in total:");
                        System.out.println("---------------------");
                        for(VSRad iz : gas){
                            System.out.println("ID " + iz.id + " :");
                            System.out.println("    location:  " + iz.cursor.represent());
                            System.out.println("    color:  " + iz.color.getRGB());
                            System.out.println("    strength: ");
                            System.out.println(".....");
                        }
                        System.out.println("---------------------");
                        
                        break;
                    case "/addCircle":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            double r = Double.valueOf(values[0]);
                            String tag = values[0];
                            gameObject c= new gameObject(x, y, (int) r, "circle", "N", 1, Color.black, k.k.objectManager.getUsableID(), k.k, renderType.circle);
                            System.out.println(c.shape);
                            k.k.objectManager.addObject(c);
                            //k.k.objectManager.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    case "/rm":
                        String values[] = arr[1].split(" ", 2);
                        for(gameObject o : k.k.objectManager.getObjectsByTag(values[0])){
                                k.k.objectManager.removeObject(o);
                            }
                        break;
                    case "/g":
                        String valuesa[] = arr[1].split(" ", 2);
                        try {
                            k.k.engine_gravity = new Point2D(Double.parseDouble(valuesa[0]), Double.parseDouble(valuesa[1]));
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/relight":
                        VSRadManager vsradm = k.k.rad;
                        vsradm.recalculate("IgnoreRecalculation", 1, true);
                        //vsradm.recalculateParent();
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
                    case "/id":
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
                        k.k.Logic.printLevelmap();
                        break;
                    case "/ps":
                        k.k.Logic.running = !k.k.Logic.running;
                        break;
                    case "/nausea":
                        k.k.Logic.Vrenderer.dispEffectsEnabled = true;
                        break;
                    case "/vfx":
                        k.k.Logic.window.useVFX = !k.k.Logic.window.useVFX;
                        System.out.println("VFX IS NOW: " + k.k.Logic.window.useVFX);
                        break;
                    case "/s":
                        String valuesz[] = arr[1].split(" ", 2);
                        try {
                            Point2D n = new Point2D(Double.parseDouble(valuesz[0]), Double.parseDouble(valuesz[1]));
                            System.out.println("Setting sizes to: " + n);
                            k.k.updateSize(n);
                            
                        } catch (NumberFormatException numberFormatException) {
                            quickTools.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/togHide":
                        String valuesza = arr[1];
                        LinkedList<gameObject> ob = k.k.objectManager.getObjectsByTag(valuesza);
                        for(gameObject o : ob){
                            o.setHidden(!o.isHidden());
                        }
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
