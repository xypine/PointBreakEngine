/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.PointBreakStudios.PointBreakEngine;

import java.awt.Color;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jonnelafin
 */
public class kick {
    dVector engine_gravity = new dVector(0D, 0.1D);
    engine wM;
    Editor ea;
    //radiosity rad;
    VSRadManager rad;
    boolean tog;
    kick ref = this;
    int xd = 50;
    int yd = 25;
        EffectsDemo ED;
    Thread c;
    Thread b;
    Thread a;
    objectManager forwM = new objectManager();
    public kick(int mode){
        devkit kit = new devkit(ref);
        //ED = new EffectsDemo(ref , forwM, 50, 25, rad, engine_gravity);
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
        wM = new engine(ref , forwM, xd, yd, rad, engine_gravity);
        
        
        //rad = new radiosity(ref);
        //SwingUtilities.invokeLater(ED);
        SwingUtilities.invokeLater(wM);
           //Thread a = new Thread(wM, "Thread 1");
           //Thread b = new Thread(ea, "Thread 2");
           //a.start();
           //b.start();
        
        //rad.running = true;
        wM.running = true;
        if(mode == 3){
            try {
                wM.loadLevel("/src/com/PointBreakStudios/PointBreakEngine/levels/out.txt");
            } catch (URISyntaxException ex) {
                Logger.getLogger(kick.class.getName()).log(Level.SEVERE, null, ex);
            }
            wM.oM.addObject(new Player(5, 5, "player1", "█", 1F, Color.black, 1, ref));
            //rad.setTitle("VSRad");
        }
        //wM.running = true;
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
