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
    //radiosity rad;
    VSRadManager rad;
    boolean tog;
    kick ref = this;
    int xd = 50;
    int yd = 25;
    Thread c;
    Thread b;
    Thread a;
    objectManager forwM = new objectManager();
    public kick(int mode){
        c  = new Thread(){
            @Override
            public void run(){
                rad = new VSRadManager(xd, yd, forwM);
            }
        };
        c.start();
        b  = new Thread(){
            @Override
            public void run(){
                ea = new Editor(ref);
                SwingUtilities.invokeLater(ea);
                ea.setVisible(false);
            }
        };
        b.start();
        a = new Thread(){
                @Override
                public void run(){
        wM = new windowManager(ref , forwM, xd, yd, rad);
        
        
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(rad);
        SwingUtilities.invokeLater(wM);
        
           //Thread a = new Thread(wM, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        //rad.running = true;
        wM.running = false;
        if(mode == 3){
            wM.loadLevel("/src/com/viridistudios/viridiengine/levels/out.txt");
            wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1));
            //rad.setTitle("VSRad");
        }
        wM.running = true;
                }
        };
        a.start();
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
