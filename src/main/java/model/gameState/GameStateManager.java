package model.gameState;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.NPCCreator;
import model.entity.Player;
import model.items.KeyItems;
import model.items.ObjectsCreator;
import model.sound.Playlist;
import model.sound.Sound;
import model.tile.MapManager;
import model.tile.TileManager;
import view.GamePanel;

import java.awt.*;
import java.util.List;

public class GameStateManager {


    private GameState playState;
    private GameState menuState;
    private GameState pauseState;

    private GameState currentState;
    private GameState previousState;

    private GamePanel gp;
    KeyHandler keyH;

    private boolean alreadyPaused = false; // necessario onde evitare che il playstate richiami il costruttore del pause state mentre il gioco è in pausa
    private boolean inDialogue = false; //necessario per la logica della pausa durante i dialoghi
    Player player;
    public TileManager tileManagerZonaIniziale;
    public TileManager tileManagerCasettaIniziale;

    public TileManager tileManagerVillaggioSud;

    public TileManager tileManagerNegozioItemsVillaggioSud;


    //gestore mappe
    MapManager mapManager;

    List<KeyItems> keyItemsList;
    java.util.List<Entity> npcList;

    Playlist playlist = new Playlist();
    List<Sound> songList = playlist.getSongList();



    public GameStateManager(){

    }
    public GameStateManager(GamePanel gp) {
        this.gp = gp;
        this.keyH = new KeyHandler(this);
        this.currentState = new MenuState(gp, this, keyH);

    }
    public void Init(){ // inizializza il player e le mappe
        this.player = new Player(gp, this, keyH);
        this.tileManagerZonaIniziale = new TileManager(gp, this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
        this.tileManagerCasettaIniziale = new TileManager(gp, this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
        this.tileManagerVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/VillaggioSud/VillaggioSud.tmx");
        this.tileManagerNegozioItemsVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/NegozioItemsVillaggioSud/NegozioItemsVillaggioSud.tmx");
        this.mapManager = new MapManager(gp, player, tileManagerCasettaIniziale, tileManagerZonaIniziale, tileManagerVillaggioSud, tileManagerNegozioItemsVillaggioSud);
        this.playState = new PlayState(gp, this, mapManager, player, keyH);
        //this.pauseState = new PauseState(gp, this, keyH);
        this.npcList = NPCCreator.createNPCs(gp, this , mapManager, keyH);
        this.keyItemsList = ObjectsCreator.createObjects(this, mapManager, keyH);
    }

    public enum State{MENU, PLAY, PAUSE, PREVIOUS};

    public void setState(State state) {
        switch (state) {
            case MENU:
                currentState = new MenuState(gp, this, keyH);
                break;
            case PLAY:
                if(this.playState == null)
                    Init();
                playMusicLoop(0);
                currentState = playState; // playstate deve essere sempre in memoria
                break;
            case PAUSE:
                //currentState = pauseState;
                stopMusic(0);
                currentState = new PauseState(gp, this, keyH);
                break;
            case PREVIOUS:
                currentState = previousState;

            // Add more cases for additional states as needed
        }
    }
    public void setDialogueState(Entity entity){
        currentState = new DialogueState(gp, this , keyH, entity);
        inDialogue = true;

    }
    public void dialoguePause(){
        previousState = currentState;
        setState(State.PAUSE);
    }
    public void exitDialogue(){
        this.setState(GameStateManager.State.PLAY);
        this.inDialogue = false;
        this.previousState = null;
    } // Logica per uscire dal dialogo

    public void update() {
        currentState.update();
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public GameState getPlayState() {
        return playState;
    }

    public GameState getMenuState() {
        return menuState;
    }

    public GameState getPauseState() {
        return pauseState;
    }
    public Player getPlayer(){
        return this.player;
    }
    public MapManager getMapManager() {
        return this.mapManager;
    }

    public java.util.List<Entity> getNpcList() { return npcList; }

    public List<KeyItems> getKeyItemsList() {
        return keyItemsList;
    }

    public GamePanel getGamePanel() {
        return gp;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public boolean isAlreadyPaused() {
        return alreadyPaused;
    }

    public void setAlreadyPaused(boolean alreadyPaused) {
        this.alreadyPaused = alreadyPaused;
    }

    public boolean isInDialogue() {
        return inDialogue;
    }


    public void playMusicLoop(int numSong) {

        songList.get(numSong).loop();

    }

    public void stopMusic(int numSong){
        songList.get(numSong).stop();
    }

    public List<Sound> getSongList() {
        return songList;
    }

}
