/*
 * The MIT License
 *
 * Copyright 2019 Elias.
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

import PBEngine.directory;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jonnelafin
 */
public class audioSource {
    
    private float volume;
    private Object volumelock = new Object();
    public void setVolume(float v){
        synchronized(volumelock){
            this.volume = v;
        }
    }
    public float getVolume(){
        synchronized(volumelock){
            return this.volume;
        }
    }
    
    AudioInputStream inputStream;
    private FloatControl gainControl;
    private Clip myclip;
    private final Object lock = new Object();
    
    public boolean loop = false;
    
    public audioSource(String audioUrl, boolean loop) throws UnsupportedAudioFileException, IOException{
        this.loop = loop;
        File f = new File(new directory().music + audioUrl);
        inputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());
    }
    public audioSource(String audioUrl, String filepath, boolean loop) throws UnsupportedAudioFileException, IOException{
        this.loop = loop;
        File f = new File(filepath + audioUrl);
        inputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());
    }
    
    public synchronized void play() {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            @Override
            public void run() {
                try {
                    FloatControl newgainControl = null;
                    AudioListener listener = new AudioListener(newgainControl, gainControl);
                    myclip = AudioSystem.getClip();
                    myclip.addLineListener(listener);
                    myclip.open(inputStream);
                    newgainControl = (FloatControl) myclip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl = newgainControl;
                    try {
                        myclip.start();
                        listener.waitUntilDone();
                        while(loop){
                            myclip.start();
                            myclip.loop(myclip.LOOP_CONTINUOUSLY);
                            newgainControl.setValue(volume/100);
                        }
                    } finally {
                        myclip.close();
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}
class AudioListener implements LineListener {
    private boolean done = false;
    FloatControl to;
    FloatControl from;
    public AudioListener(FloatControl to, FloatControl from){
        this.to = to;this.from=from;
    }
    
    @Override public synchronized void update(LineEvent event) {
      Type eventType = event.getType();
      if (eventType == Type.STOP || eventType == Type.CLOSE) {
        done = true;
        notifyAll();
      }
    }
    public synchronized void waitUntilDone() throws InterruptedException {
      while (!done) {
          to = from;
          wait(); }
    }
}