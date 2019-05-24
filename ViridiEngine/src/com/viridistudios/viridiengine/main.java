
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viridistudios.viridiengine;

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Elias
 */
public class main {

    /**
     * @param args the command line arguments
     */
    static LinkedList<Object> list;
    static kick k;
    windowManager main;
    Editor editor;
    boolean running;
    public static void main(String[] args) {
        // TODO code application logic here
        k = new kick(3);
        
        
    }
    public void start(){
        running = true;
        main = k.wM;
        editor = k.ea;
        float c = 0F;
        k = new kick(0);
        while(!main.ready){
            System.out.println("initializing main... " + c);
            c++;
        }
        main.loadLevel("/src/com/viridistudios/viridiengine/levels/out.txt");
        main.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1));
    }
    public void stop(){
        k.stop();
    }
    public void continu(){
        k.continu();
    }
}
