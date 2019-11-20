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

import JFUtils.Range;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Jonnelafin
 */
public class AudioPlayer {
    private boolean isPlaying = false;
    public synchronized boolean isPlaying(){
        return isPlaying;
    }
    public synchronized void setPlaying(boolean val){
        this.isPlaying = val;
    }
    public Clip playingClip;
    public synchronized void playSound(AudioClip sfx, double Vol){
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    AudioInputStream stream = sfx.getAudioStream();
                    Clip clip = AudioSystem.getClip();
                    
                    clip.open(stream);
                    setVol(Vol, clip);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }; thread.start();
    }
    public static void setVol(double Vol, Clip clip){
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(Vol) / Math.log(10) * 20);
        gain.setValue(dB);
    }
}
