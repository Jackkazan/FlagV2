package model.sound;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private List<Sound> songList;

    private List<Sound> soundEffect;

    public Playlist() {
        songList = new ArrayList<>();
        // Aggiungi le canzoni alla playlist
        addSong("src/main/resources/music/ost.wav");
        //addSong("src/main/resources/music/ost2.wav");
        // Aggiungi altre canzoni quando necessario
        soundEffect= new ArrayList<>();
    }

    private void addSong(String filePath) {
        Sound sound = new Sound();
        sound.setFile(filePath);
        songList.add(sound);
    }

    public List<Sound> getSongList() {
        return songList;
    }
}
