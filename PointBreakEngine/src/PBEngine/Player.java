/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

import java.awt.Color;

/**
 *
 * @author Jonnelafin
 */
public class Player extends gameObject{
    private boolean noclip = false;
    public int fuel = 136;
    int collision_type = 0;
    boolean canjump = true;
    private final directory dir = new directory();
    public boolean point2_2 = false;
    public Player(int ypos, int xpos, int size, String tag, String ap, float mas, Color cot, int ID, kick master) {
        super(ypos, xpos, size, tag, ap, mas, cot, ID, master);
        this.imageName = dir.textures + "player/player2.png";
//        this.summon(ypos, xpos, tag, ap, mas);
    }
    
    @Override
    public void checkInput(Input in){
        //System.out.println("VELX, VELY: " + velx + " , " + vely + "     " + "up, down, left, right: " + in.up() + " " + in.down() + " " + in.right() + " " + in.left() + "      " + "x, y, mouse x, y: " + this.getX() + " , " + this.getY() + "MOUSE:"+ in.mouseX() + ", " + in.mouseY());
        //System.out.println(this.colliding);
        
        if(colliding || point2 || y > 22){
            fuel = 100;
        }
        if(this.vely < -0.5F && !this.getTag().contains("cursor"))
        {
            canjump = false;
            if(this.vely < -0.75F){
                this.vely = -0.8F;
            }
        }
        else{
            canjump = true;
        }
        
        if(this.getTag().contains("player1")){
            if(fuel > 49 && in.up() != 0){
                fuel = fuel - 54;
                this.vely = this.vely + (in.up() + in.down()) * 1.7F;
            }
            else if(fuel < 101){
                fuel = fuel + 1;
            }
            
//            if(point2_2){this.vely = this.vely * -1;}
//            canjump = false;
        }
        
        if(canjump && this.getTag().contains("player2")){
            
            //this.vely = this.vely + (in.up2() + in.down2()) * 1.7F;
//            if(point2_2){this.vely = this.vely * -1;}
//            canjump = false;
        }
        if(this.getTag().contains("player1")){
            this.velx = this.velx + ((in.right() + in.left()) * 0.15F);
            
            if(in.keyPressed == null){
                
            }
            else{
                if(in.keyPressed.getKeyCode() == 32){
                    gameObject projectile = new gameObject((int)this.x,(int) this.y, 1, "projectile", "", 1, Color.white, 919, masterParent);
                    masterParent.forwM.addObject(projectile);
                    projectile.velx = this.velx * 3;
                    projectile.vely = this.vely * 3;
                    projectile.x = projectile.x + projectile.velx;
                    projectile.y = projectile.y + projectile.vely;
                    projectile.collision_Explode = true;
                    projectile.collision_type = 1;
                }
            }
            //this.velx = this.velx + in.cX / 75;
            //this.vely = this.vely + in.cY / 75;
//            this.setColor(new Color(0 + orange * 0.25F, 0 + orange * 0.5F, 0 + orange));
            //this.setColor(Color.red);
        }
        if(this.getTag().contains("player2")){
            this.velx = this.velx + ((in.right2() + in.left2()) * -0.4F);
            double orange = ((this.getVX() + 1) * (this.getVY() + 1)) * 0.5F - 0.1F;
            if(orange < 0F){orange = 0F;}
            if(orange > 1F){orange = 0.9F;}
//            this.setColor(new Color(0 + orange * 0.25F, 0 + orange * 0.5F, 0 + orange));
            //this.setColor(Color.blue);
        }
        if(this.getTag().contains("cursor") || noclip){
//            System.out.println("VELX, VELY: " + velx + " , " + vely + "     " + "up, down, left, right: " + in.up() + " " + in.down() + " " + in.right() + " " + in.left() + "      " + "x, y, mouse x, y: " + this.getX() + " , " + this.getY() + "MOUSE:"+ in.MX() + ", " + in.MY());
            this.velx = 0F;
            this.vely = 0F;
            
            if(in.left() == -1){
                this.x = this.x - 1;
            }
            if(in.right() == 1){
                this.x = this.x + 1;
            }
            if(in.up() == -1){
                this.y = this.y - 1;
            }
            if(in.down() == 1){
                this.y = this.y + 1;
            }
            if(in.rt == 1){
                
            }
//            this.setColor(new Color(0 + orange * 0.25F, 0 + orange * 0.5F, 0 + orange));
            //this.setColor(Color.green);
        }
    }

    /**
     *Toggle noclip
     */
    @Override
    public void noclip(){
        collisions = !collisions;
        noclip = !noclip;
        gravity = !gravity;
        System.out.println("Noclip set to " + noclip);
    }
}
