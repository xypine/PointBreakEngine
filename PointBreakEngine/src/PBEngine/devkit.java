/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package PBEngine;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Elias Eskelinen (Jonnelafin)
 */
public class devkit extends JFrame implements devkit_interface{
    public JPanel cont = new JPanel();
    boolean tog = false;
    Supervisor k;
    JButton graphic = new JButton("toggle vector");
    JButton rays = new JButton("toggle rays");
    JLabel time = new JLabel("RAYYYS");
    JTextArea log = new JTextArea("PointBreakEngine devkit");
    JTextField lum = new JTextField(20);
    JScrollPane logs = new JScrollPane(log);
    JButton options = new JButton("Options");
    /**
     *
     * @param k the used supervisor, used for the actions of the commands
     */
    public devkit(Supervisor k) {
        this.setTitle("PointBreakEngine devkit");
        this.k = k;
        this.setSize(400, 550);
        this.setLocationRelativeTo(k.Logic.window);
        this.setLocation(1080, 0);
        
        cont.setLayout(new BorderLayout());
        graphic.addActionListener(new BListener(9, this));
        rays.addActionListener(new BListener(2, this));
        lum.addActionListener(new BListener(0, this));
        logs.setWheelScrollingEnabled(true);
        //log.setColumns(1);
        log.setRows(15);
        logs.setWheelScrollingEnabled(true);
        //cont.add(graphic, BorderLayout.NORTH);
        //cont.add(rays, BorderLayout.NORTH);
        options.addActionListener(new BListener(10, this));
        cont.add(options, BorderLayout.EAST);
        //cont.add(time, BorderLayout.EAST);
        cont.add(logs, BorderLayout.CENTER);
        cont.add(lum, BorderLayout.SOUTH);
        this.add(cont);
        this.setVisible(true);
    }
    public void togG(){
        if(tog){
            k.Logic.vector = 1;
        }
        else{
            k.Logic.vector = 0;
        }
        tog = !tog;
    }
    boolean togV = true;
    public void togV(){
        if(togV){
            k.Logic.renderRays = 0;
        }
        else{
            k.Logic.renderRays = 1;
        }
        togV = !togV;
    }

    @Override
    public void setLog(String n) {
        this.log.setText(n);
        JScrollBar vertical = null;
        vertical = logs.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
}
