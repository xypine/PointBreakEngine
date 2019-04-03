/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Jonnelafin
 */
public class Input implements KeyListener, MouseListener {
    private int up = 0, down = 0;
    private int right = 0, left = 0;
    private int mx = 0, my = 0;
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    public int up(){return(this.up);}
    public int down(){return(this.down);}
    public int right(){return(this.right);}
    public int left(){return(this.left);}
    
    public int MX(){return(this.mx);}
    public int MY(){return(this.my);}
    
    void mouseMoved(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char ke = e.getKeyChar();
//        System.out.print(ke);
        if(ke == 'w'){
            up = -1;
        }
        if(ke == 's'){
            down = 1;
        }
        if(ke == 'a'){
            left = -1;
        }
        if(ke == 'd'){
            right = 1;
        }
            
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char ke = e.getKeyChar();
        if(ke == 'w'){
            up = 0;
        }
        if(ke == 's'){
            down = 0;
        }
        if(ke == 'a'){
            left = 0;
        }
        if(ke == 'd'){
            right = 0;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}
