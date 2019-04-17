/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.awt.Color;

/**
 *
 * @author Jonnelafin
 */
public class Player extends gameObject{
    boolean canjump = true;

    public Player(int ypos, int xpos, String tag, String ap, float mas, Color cot, int ID) {
        super(ypos, xpos, tag, ap, mas, cot, ID);
//        this.summon(ypos, xpos, tag, ap, mas);
    }
    
    public void checkInput(Input in){
//        System.out.println("VELX, VELY: " + velx + " , " + vely + "     " + "up, down, left, right: " + in.up() + " " + in.down() + " " + in.right() + " " + in.left() + "      " + "x, y, mouse x, y: " + this.getX() + " , " + this.getY() + "MOUSE:"+ in.MX() + ", " + in.MY());
        //System.out.println(this.colliding);
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
        if(canjump && this.getTag() == "player1"){
            
            this.vely = this.vely + (in.up() + in.down()) * 1.7F;
//            canjump = false;
        }
        if(canjump && this.getTag() == "player2"){
            
            this.vely = this.vely + (in.up2() + in.down2()) * 1.7F;
//            canjump = false;
        }
        if(this.getTag() == "player1"){
            this.velx = this.velx + ((in.right() + in.left()) * 0.4F);
            float orange = ((this.getVX() + 1) * (this.getVY() + 1)) * 0.5F - 0.1F;
            if(orange < 0F){orange = 0F;}
            if(orange > 1F){orange = 0.9F;}
//            this.setColor(new Color(0 + orange * 0.25F, 0 + orange * 0.5F, 0 + orange));
            this.setColor(Color.red);
        }
        if(this.getTag() == "player2"){
            this.velx = this.velx + ((in.right2() + in.left2()) * -0.4F);
            float orange = ((this.getVX() + 1) * (this.getVY() + 1)) * 0.5F - 0.1F;
            if(orange < 0F){orange = 0F;}
            if(orange > 1F){orange = 0.9F;}
//            this.setColor(new Color(0 + orange * 0.25F, 0 + orange * 0.5F, 0 + orange));
            this.setColor(Color.green);
        }
    }
}
