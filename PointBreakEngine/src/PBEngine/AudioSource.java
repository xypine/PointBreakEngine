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

import java.io.File;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.LinkedList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jonnelafin
 */
public class AudioSource {
   private LinkedList<Clip> clips = new LinkedList<Clip>();
   private LinkedList<FloatControl> controls = new LinkedList<FloatControl>();
   private Clip clip;
   private FloatControl gainControl;
   AudioInputStream audioStream;
   String filePath = new File("").getAbsolutePath();
   public AudioSource() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
       clip = AudioSystem.getClip();
       addSound("src/viridiengine/audio/test.wav");
       clip.open(audioStream);
       clip.setFramePosition(0);
       gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
   }
   public void addSound(String filename) throws UnsupportedAudioFileException, IOException{
       File tmp = new File(filename);
       audioStream = AudioSystem.getAudioInputStream(tmp);
   }
   public void setVolume(float newf){
       double linear = pow(10.0, newf/20.0);
       gainControl.setValue((float) pow(newf, -1));
   }
   public float getVolume(){
       return(gainControl.getValue());
   }
   public void play(){
        Clip c = clip;
        if(!c.isActive()){
            c.setFramePosition(0);
            c.start();
        }
   }
}