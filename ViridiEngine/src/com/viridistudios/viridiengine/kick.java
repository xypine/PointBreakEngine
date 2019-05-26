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
 * @author Jonnelafin
 */
public class kick {
    windowManager wM;
    Editor ea;
    radiosity rad;
    boolean tog;
    kick ref = this;
    public kick(int mode){
        new Thread(){
                @Override
                public void run(){
        wM = new windowManager(ref);
        ea = new Editor(ref);
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(rad);
        SwingUtilities.invokeLater(wM);
        SwingUtilities.invokeLater(ea);
           //Thread a = new Thread(wM, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        ea.setVisible(false);
        wM.setVisible(false);
        //rad.running = true;
        wM.running = false;
        if(mode == 3){
            wM.loadLevel("/src/com/viridistudios/viridiengine/levels/out.txt");
            wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1));
            //rad.setTitle("VSRad");
        }
        wM.running = true;
                }
        }.start();
    }
    public void tog(){
        if(tog){
            wM.record();
        }
        wM.setVisible(tog);
        wM.running = tog;
//        rad.setVisible(tog);
//        rad.running = tog;
        ea.setVisible(!tog);
        ea.running = !tog;
        tog = !tog;
    }
    public void stop(){
        wM.running = false;
        ea.running = false;
    }
    public void continu(){
        wM.running = tog;
        ea.running = !tog;
    }
}
