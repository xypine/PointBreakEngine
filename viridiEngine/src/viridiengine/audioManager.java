/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.io.File;
import java.io.IOException;
import static java.lang.Math.pow;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class audioManager {
   private LinkedList<Clip> clips = new LinkedList<Clip>();
   private LinkedList<FloatControl> controls = new LinkedList<FloatControl>();
   private Clip clip;
   private FloatControl gainControl;
   AudioInputStream audioStream;
   String filePath = new File("").getAbsolutePath();
   public audioManager() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
       clip = AudioSystem.getClip();
       addSound("src/viridiengine/audio/test.wav");
       clip.open(audioStream);
       clip.setFramePosition(0);
       gainControl = 
    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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