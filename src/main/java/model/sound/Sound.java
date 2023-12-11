package model.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {

    public Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {

        //soundURL[0] = getClass().getResource("src/main/resources/music/ost.wav");
        //soundURL[1] = getClass().getResource("/sound/ost.mp3");
        //soundURL[2] = getClass().getResource("/sound/ost.mp3");           <-- examples
        //soundURL[3] = getClass().getResource("/sound/ost.mp3");
        //soundURL[4] = getClass().getResource("/sound/ost.mp3");
    }
        public void setFile (int i){
            try{
                File f = new File("src/main/resources/music/ost.wav");
                AudioInputStream ais = AudioSystem.getAudioInputStream(f.toURI().toURL());
                clip = AudioSystem.getClip();
                clip.open(ais);

            }catch(Exception e){

            }
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

