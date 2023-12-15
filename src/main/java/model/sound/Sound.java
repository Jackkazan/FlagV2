package model.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.net.URL;

public class Sound {

    public Clip clip;
    public Sound() {}
    public void setFile (String pathFile){
        try{
            File f = new File(pathFile);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public void setVolume(float volume) {
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMaximum() - control.getMinimum();
        float gain = (range * volume) + control.getMinimum();
        control.setValue(gain);
    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
        }

}

