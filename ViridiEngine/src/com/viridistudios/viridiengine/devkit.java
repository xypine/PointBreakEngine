/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viridistudios.viridiengine;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    public devkit(kick k) {
        this.setTitle("ViridiEngine devkit");
        this.k = k;
        this.setSize(300, 550);
        
        
        graphic.addActionListener(new BListener(9, this));
        rays.addActionListener(new BListener(2, this));
        cont.add(graphic, BorderLayout.NORTH);
        cont.add(rays, BorderLayout.NORTH);
        cont.add(raysL, BorderLayout.SOUTH);
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
        k.raysL.setText(Boolean.toString(k.togV));
    }
    
}