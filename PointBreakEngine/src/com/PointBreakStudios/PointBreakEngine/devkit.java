/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.PointBreakStudios.PointBreakEngine;

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
        this.setTitle("ViridiEngine devkit");
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
            //k.k.wM.blurStrenght = Integer.parseInt(k.lum.getText());
            k.k.wM.vA.blur = Integer.parseInt(k.lum.getText());
            //k.k.ED.blur = Integer.parseInt(k.lum.getText());
        }
        catch(Exception e){}
        k.raysL.setText(Boolean.toString(k.togV));
    }
    
}