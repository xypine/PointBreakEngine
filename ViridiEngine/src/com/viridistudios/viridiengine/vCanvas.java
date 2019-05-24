/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viridistudios.viridiengine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import static java.lang.Math.round;

/**
 *
 * @author Jonnelafin
 */
public class vCanvas extends Canvas{
    public void render(float locx,float locy, Color c){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(c);
        g.fillRect(round(locx), round(locy), (int) 15.34F, (int) 22.48F);
        g.dispose();
        bs.show();
    }
}
