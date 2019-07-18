/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PBEngine;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author elias
 */
public class devkit extends JFrame{
    JPanel cont = new JPanel();
    boolean tog = false;
    kick k;
    JButton graphic = new JButton("toggle vector");
    JButton rays = new JButton("toggle rays");
    JLabel raysL = new JLabel("RAYYYS");
    JTextField lum = new JTextField(20);
    public devkit(kick k) {
        this.setTitle("PointBreakEngine devkit");
        this.k = k;
        this.setSize(300, 550);
        
        
        graphic.addActionListener(new BListener(9, this));
        rays.addActionListener(new BListener(2, this));
        lum.addActionListener(new BListener(0, this));
        cont.add(graphic, BorderLayout.NORTH);
        cont.add(rays, BorderLayout.NORTH);
        cont.add(raysL, BorderLayout.SOUTH);
        cont.add(lum, BorderLayout.NORTH);
        this.add(cont);
        
        this.setVisible(true);
    }
    public void togG(){
        if(tog){
            k.wM.vector = 1;
        }
        else{
            k.wM.vector = 0;
        }
        tog = !tog;
    }
    boolean togV = true;
    public void togV(){
        if(togV){
            k.wM.renderRays = 0;
        }
        else{
            k.wM.renderRays = 1;
        }
        togV = !togV;
    }
}
class BListener implements ActionListener{
    int type;
    devkit k;
    public BListener(int t, devkit d){
        this.type = t;
        this.k = d;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(type == 9){
            System.out.println("Graphics!");
            k.togG();
        }
        if(type == 2){
            System.out.println("Rays!");
            k.togV();
        }
        try{
            String arr[] = k.lum.getText().split(" ", 2);
            
            if(k.lum.getText().charAt(0) == '/'){
                switch(arr[0]){
                    case "/engine_coll":
                        if(arr[1].matches("true")){k.k.engine_collisions = true;}
                        else if(arr[1].matches("false")){k.k.engine_collisions = false;}
                        else{
                            quickEffects.alert("devkit", "value :"+ arr[1] +": not understood");
                        }
                        break;
                    case "/blur":
                        k.k.wM.blurStrenght = Integer.parseInt(arr[1]);
                        break;
                    case "/tp":
                        try {
                            String values[] = arr[1].split(" ", 2);
                            int x = Integer.parseInt(values[1].split(" ", 2)[0]);
                            int y = Integer.parseInt(values[1].split(" ", 2)[1]);
                            k.k.forwM.getObjectByTag(values[0]).setLocation(new Vector(x, y));
                        } catch (NumberFormatException numberFormatException) {
                            quickEffects.alert("devkit", "value :"+ arr[1] +": not understood");
                            
                        }
                        break;
                    default:
                        quickEffects.alert("devkit", "command not understood");
                        break;
                }
            }
        }
        catch(Exception e){}
        k.raysL.setText(Boolean.toString(k.togV));
    }
    
}