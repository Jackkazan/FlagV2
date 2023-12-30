package model.gameState;

import controller.KeyHandler;
import model.entities.enemies.Enemy;
import model.entities.enemies.EnemyCreator;
import model.entities.npc.Npc;
import model.entities.npc.NpcCreator;
import model.entities.player.Player;
import model.entities.items.Item;
import model.entities.items.ItemCreator;
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

    public static GamePanel gp;
    public static KeyHandler keyH;

    private boolean inDialogue = false; //necessario per la logica della pausa durante i dialoghi
    Player player;
    public TileManager tileManagerZonaIniziale;
    public TileManager tileManagerCasettaIniziale;

    public TileManager tileManagerVillaggioSud;

    public TileManager tileManagerNegozioItemsVillaggioSud;

    public TileManager tileManagerPianoTerraTavernaVillaggio;
    public TileManager tileManagerPrimoPianoTavernaVillaggio;


    //gestore mappe
    MapManager mapManager;

    List<Item> itemList;
    List<Npc> npcList;

    Playlist playlist = new Playlist();
    List<Sound> songList = playlist.getSongList();
    List<Enemy> enemyList;


    public GameStateManager(){

    }
    public GameStateManager(GamePanel gp) {
        this.gp = gp;
        this.keyH = new KeyHandler(this);
        this.currentState = new MenuState(gp, this, keyH);

    }
    public void Init(){ // inizializza il player e le mappe
        this.player = new Player(gp,this, keyH);
        this.tileManagerZonaIniziale = new TileManager(gp, this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
        this.tileManagerCasettaIniziale = new TileManager(gp, this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
        this.tileManagerVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/VillaggioSud/VillaggioSud.tmx");
        this.tileManagerNegozioItemsVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/NegozioItemsVillaggioSud/NegozioItemsVillaggioSud.tmx");
        this.tileManagerPianoTerraTavernaVillaggio = new TileManager(gp,this,"src/main/resources/Map/TavernaVillaggio/PianoTerraTavernaVillaggio.tmx");
        this.tileManagerPrimoPianoTavernaVillaggio = new TileManager(gp,this,"src/main/resources/Map/TavernaVillaggio/PrimoPianoTavernaVillaggio.tmx");
        this.mapManager = new MapManager(gp, player, tileManagerCasettaIniziale, tileManagerZonaIniziale, tileManagerVillaggioSud, tileManagerNegozioItemsVillaggioSud,tileManagerPianoTerraTavernaVillaggio,tileManagerPrimoPianoTavernaVillaggio);
        this.playState = new PlayState(gp, this, mapManager, player, keyH);
        playMusicLoop(0);
        //this.pauseState = new PauseState(gp, this, keyH);
        this.npcList = NpcCreator.createNPCs(this, mapManager);
        this.itemList = ItemCreator.createObjects(this, mapManager, keyH);
        this.enemyList = EnemyCreator.createEnemies(this, mapManager);
    }

    public enum State{MENU, PLAY, PAUSE, PREVIOUS};

    public void setState(State state) {
        keyH.releaseToggles();
        switch (state) {
            case MENU:
                currentState = new MenuState(gp, this, keyH);
                break;
            case PLAY:
                if(this.playState == null)
                    Init();
                currentState = playState; // playstate deve essere sempre in memoria
                break;
            case PAUSE:
                stopMusic(0);
                previousState = currentState;
                currentState = new PauseState(gp, this, keyH);
                break;
            case PREVIOUS: //Uscendo dalla pausa bisogna tornare allo stato precedente (Non per forza playstate, ma anche dialogue, inventory ecc...)
                currentState = previousState;

        }
    }
    public void setDialogueState(Npc npc){
        currentState = new DialogueState(gp, this , keyH, npc);
        inDialogue = true;

    }
    public void exitDialogue(){
        this.setState(GameStateManager.State.PLAY);
        this.inDialogue = false;
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

    public Player getPlayer(){
        return this.player;
    }
    public MapManager getMapManager() {
        return this.mapManager;
    }

    public List<Npc> getNpcList() { return this.npcList; }
    public List<Enemy> getEnemyList(){return this.enemyList;}

    public List<Item> getKeyItemsList() {
        return itemList;
    }

    public GamePanel getGamePanel() {
        return gp;
    }

    public KeyHandler getKeyH() {
        return keyH;
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
