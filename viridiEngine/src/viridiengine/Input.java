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
    private kick ki;
    private int up = 0, down = 0;
    private int right = 0, left = 0;
    private int up2 = 0, down2 = 0;
    private int right2 = 0, left2 = 0;
    private int mx = 0, my = 0;
    public int rt = 0;
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    public int up(){return(this.up);}
    public int down(){return(this.down);}
    public int right(){return(this.right);}
    public int left(){return(this.left);}
    
    public int up2(){return(this.up2);}
    public int down2(){return(this.down2);}
    public int right2(){return(this.right2);}
    public int left2(){return(this.left2);}
    
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
        int kee = e.getKeyCode();
//        System.out.println(kee);
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
        
        if(kee == 37){
        //arrow left "a"
            left2 = 1;
        }
        if(kee == 38){
        //arrow up "w"
            up2 = -1;
        }
        if(kee == 39){
        //arrow right "d"
            right2 = -1;
        }
        if(kee == 40){
        //arrow down "s"
            down2 = 1;
        }
        if(ke == 'e'){ki.tog();}
        if(ke == 'r'){rt = 1;}
    }
    
    public Input(kick k){
        this.ki = k;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        char ke = e.getKeyChar();
        int kee = e.getKeyCode();
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
        
        if(kee == 37){
            left2 = 0;
        }
        if(kee == 38){
            up2 = 0;
        }
        if(kee == 39){
            right2 = 0;
        }
        if(kee == 40){
            down2 = 0;
        }
        if(ke == 'r'){rt = 0;}
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
