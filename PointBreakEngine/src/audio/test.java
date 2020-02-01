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

package audio;

import JFUtils.dirs;
import PBEngine.directory;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JSlider;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;
/**
 *
 * @author Jonnelafin
 */
public class test extends JFrame{
    public static void main(String[] args) {
        new test();
    }
    public JSlider vol;
    public JSlider pan;
    public test(){
        this.setSize(300, 300);
        this.setTitle("PBEngine Audiotest");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        vol = new JSlider(0, 200, 100);
        pan = new JSlider(-100, 100, 0);
        this.add(vol);
        this.add(pan);
        
        this.setVisible(true);
        
        boolean play = true;
        File src = new File(new directory().music + "test3.wav");
        
        TinySound.init();
        Music music = TinySound.loadMusic(src);
        music.play(true);
        
        
        while (play) {
            music.setVolume(vol.getValue()/100F);
            music.setPan(pan.getValue()/100F);
        //    t.setVolume(vol.getValue()/100F);
        //    t.setPAN(pan.getValue());
        }
        //t.setLooping(false);
        
    }
}
