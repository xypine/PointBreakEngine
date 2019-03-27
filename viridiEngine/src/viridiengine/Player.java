/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

/**
 *
 * @author Jonnelafin
 */
public class Player extends gameObject{
    boolean canjump = true;
    public void checkInput(Input in){
        System.out.println("VELX, VELY: " + velx + " , " + vely + "     " + "up, down, left, right: " + in.up() + " " + in.down() + " " + in.right() + " " + in.left() + "      " + "x, y, input: " + this.getX() + " , " + this.getY());
        
        if(this.vely < -0.5F)
        {
            canjump = false;
            if(this.vely < -0.75F){
                this.vely = -0.8F;
            }
        }
        else{
            canjump = true;
        }
        if(canjump){
            this.vely = this.vely + (in.up() + in.down()) * 1.7F;
//            canjump = false;
        }
        
        this.velx = in.right() + in.left();
        
    }
}
