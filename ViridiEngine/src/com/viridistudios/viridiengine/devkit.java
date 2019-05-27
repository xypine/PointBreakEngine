/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viridistudios.viridiengine;

import java.awt.Button;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author elias
 */
public class devkit extends JFrame{
    JPanel cont = new JPanel();
    Button graphic = new Button("toggle vector");
    public devkit() {
        this.setTitle("ViridiEngine devkit");
        this.setSize(300, 550);
        
        
        
        cont.add(graphic);
        this.add(cont);
        
        this.setVisible(true);
    }
    
}
