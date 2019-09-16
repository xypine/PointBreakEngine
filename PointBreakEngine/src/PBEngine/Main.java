
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PBEngine;

import java.awt.Color;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    static LinkedList<Object> list;
    static public kick k;
    private static boolean blights = false;
    private static dVector grav = new dVector(0D, 0D);;
    public Engine main;
    Editor editor;
    boolean running;
    public static void main(String[] args) {
        // TODO code application logic here
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
                    grav = new dVector(0D, 0.1D);
                }
            }
            System.out.println("////");
        } else {
            System.out.println("Starting without arguments");
        }
        if(demo){
            System.out.println("If you wish not to use the demo, please add the 'nodemo' argument");
            quickEffects.alert("demo", "If you wish not to use the demo, please add the 'nodemo' argument");
        //if(args.length != 0){if(args[0].equals("template")){
            k = new kick(3, false, new dVector(0D, 0D));
        }else{
            k = new kick(0, blights, grav);
        }
        k.run();
//        else{
//            k = new kick(0);
    }
    public void start(){
        running = true;
        main = k.Logic;
        editor = k.ea;
        float c = 0F;
        k = new kick(3, true, new dVector(0, 0.1D));
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
