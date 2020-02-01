/*
 * The MIT License
 *
 * Copyright 2020 Elias.
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

package PBEngine.settings;

import PBEngine.Supervisor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jonnelafin
 */
public class settingsUI {
    public static void showSettingsUI(Supervisor s){
        showSettingsUI(s, true, false);
    }
    public static void showSettingsUI(Supervisor s, boolean pauseGame, boolean pauseMusic){
        
        JFrame UI = new JFrame("PBEngine settings");
        doneL l = new doneL(s, UI);
        UI.setSize(600, 500);
        UI.setLayout(new FlowLayout());
        JPanel music = new JPanel(new FlowLayout());
        JSlider vol = new JSlider(0, 200, 0);
        music.add(new JLabel("Music volume:"));
        music.add(vol);
        
        JButton done = new JButton("Done");
        done.addActionListener(l);
        vol.addChangeListener(l);
        
        UI.add(music);
        UI.add(done);
        
        UI.setVisible(true);
        if(pauseGame){
            s.stop(pauseMusic);
            //s.Logic.running = false;
        }
        UI.addComponentListener(l);
    }
}
class doneL implements ActionListener, ComponentListener, ChangeListener{
    Supervisor s;
    JFrame f;
    public doneL(Supervisor s, JFrame f) {
        this.s = s;
        this.f = f;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        s.continu();
        f.setVisible(false);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void componentResized(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        s.continu();
    }

    

   

    @Override
    public void stateChanged(ChangeEvent e) {
        double val = ((JSlider) e.getSource()).getValue()/100F;
        //System.out.println(val);
        s.musicTrack.setVolume(val);
    }
    
}