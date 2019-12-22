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

import JFUtils.point.Point2D;
import JFUtils.quickTools;
import java.awt.Color;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias Eskelinen <elias.eskelinen@protonmail.com>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static LinkedList<Object> list;
    static public Supervisor k;
    private static boolean blights = false;
    private static Point2D grav = new Point2D(0D, 0D);;
    public Engine main;
    LegacyEditor editor;
    boolean running;
    public static void main(String[] args) {
        // TODO code application logic here
        
        versionCheck.check.doChecks();
        boolean demo = true;
        
        if (args.length > 0) {
            System.out.println("Arguments: ");
            System.out.println("////");
            for (String arg : args) {
                System.out.println("    " + arg);
                if(arg.matches("nodemo")){
                    demo = false;
                }
                if(arg.matches("calclights")){
                    blights = false;
                }
                if(arg.matches("defgrav")){
                    grav = new Point2D(0D, 0.1D);
                }
            }
            System.out.println("////");
        } else {
            System.out.println("Starting without arguments");
        }
        if(demo){
            System.out.println("If you wish not to use the demo, please add the 'nodemo' argument");
            quickTools.alert("demo", "If you wish not to use the demo, please add the 'nodemo' argument");
        //if(args.length != 0){if(args[0].equals("template")){
            k = new Supervisor(3, true, new Point2D(0D, 0D), 10);
        }else{
            k = new Supervisor(0, blights, grav, 10);
        }
        k.run();
//        else{
//            k = new Supervisor(0);
    }
    public void start(){
        running = true;
        main = k.Logic;
        editor = k.ea;
        float c = 0F;
        k = new Supervisor(3, true, new Point2D(0, 0.1D));
        while(!main.ready){
            System.out.println("initializing main... " + c);
            c++;
        }
        try {
            main.loadLevel("out.pblevel");
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.oM.addObject(new Player(5, 5, 2, "player1", "█", 1F, Color.black, 1, main.k));
    }
    public void stop(){
        k.stop();
    }
    public void continu(){
        k.continu();
    }
}
