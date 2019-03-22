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
    public void checkInput(Input in){
        System.out.println("VELX, VELY: " + velx + " , " + vely + "     " + "up, down, left, right: " + in.up() + " " + in.down() + " " + in.right() + " " + in.left());
        this.vely = this.vely + (in.up() + in.down()) * 10.4F;
        this.velx = in.right() + in.left();
        
    }
}
