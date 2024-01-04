package model.gameState;

import controller.KeyHandler;
import model.entities.Entity;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.enemies.EnemyCreator;
import model.entities.characters.npc.Npc;
import model.entities.characters.npc.NpcCreator;
import model.entities.characters.player.Player;
import model.entities.items.Item;
import model.entities.items.ItemCreator;
import model.Dialogues.DialogueManager;
import model.sound.Playlist;
import model.sound.Sound;
import model.tile.MapManager;
import model.tile.TileManager;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateManager {


    private GameState playState;
    private GameState menuState;
    private GameState pauseState;

    private GameState currentState;
    private GameState previousState;

    //public static GamePanel gp;
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
    DialogueManager dialogueManager;

    List<Item> itemList;
    List<Npc> npcList;
    List<Enemy> enemyList;

    List<Entity> entityList;
    Playlist playlist = new Playlist();
    List<Sound> songList = playlist.getSongList();
    private static GameStateManager instance = null;


      public DialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public GameStateManager() {
        this.keyH = KeyHandler.getInstance();
        this.currentState = new MenuState(this, keyH);
        this.dialogueManager = new DialogueManager(this);

    }
    public static GameStateManager getInstance(){
          if (instance == null)
              instance = new GameStateManager();
          return instance;
    }
    public void Init(){ // inizializza il player e le mappe
        this.player = new Player();
        this.tileManagerZonaIniziale = new TileManager(this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
        this.tileManagerCasettaIniziale = new TileManager(this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
        this.tileManagerVillaggioSud = new TileManager(this, "src/main/resources/Map/VillaggioSud/VillaggioSud.tmx");
        this.tileManagerNegozioItemsVillaggioSud = new TileManager(this, "src/main/resources/Map/NegozioItemsVillaggioSud/NegozioItemsVillaggioSud.tmx");
        this.tileManagerPianoTerraTavernaVillaggio = new TileManager(this,"src/main/resources/Map/TavernaVillaggio/PianoTerraTavernaVillaggio.tmx");
        this.tileManagerPrimoPianoTavernaVillaggio = new TileManager(this,"src/main/resources/Map/TavernaVillaggio/PrimoPianoTavernaVillaggio.tmx");
        this.mapManager = new MapManager(player, tileManagerCasettaIniziale, tileManagerZonaIniziale, tileManagerVillaggioSud, tileManagerNegozioItemsVillaggioSud,tileManagerPianoTerraTavernaVillaggio,tileManagerPrimoPianoTavernaVillaggio);
        this.playState = new PlayState(this, mapManager, player, keyH);
        playMusicLoop(0);
        //this.pauseState = new PauseState(gp, this, keyH);

        this.npcList = NpcCreator.createNPCs(this, mapManager);
        this.itemList = ItemCreator.createObjects(this, mapManager, keyH);
        this.enemyList = EnemyCreator.createEnemies(this, mapManager);

        this.entityList = new ArrayList<>();
        this.entityList.add(player);
        this.entityList.addAll(this.npcList);
        this.entityList.addAll(this.enemyList);
        this.entityList.addAll(this.itemList);

    }

    public enum State{MENU, PLAY, PAUSE, DIALOGUE, PREVIOUS};

    public void setState(State state) {
        keyH.releaseToggles();
        switch (state) {
            case MENU:
                currentState = new MenuState(this, keyH);
                break;
            case PLAY:
                if(this.playState == null)
                    Init();
                System.out.println("ohh");
                currentState = playState; // playstate deve essere sempre in memoria
                break;
            case PAUSE:
                stopMusic(0);
                previousState = currentState;
                currentState = new PauseState(this, keyH);
                break;
            case DIALOGUE:
                currentState = new DialogueState(this, keyH);
                inDialogue = true;
                break;
            case PREVIOUS: //Uscendo dalla pausa bisogna tornare allo stato precedente (Non per forza playstate, ma anche dialogue, inventory ecc...)
                currentState = previousState;

        }
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

    //public GamePanel getGamePanel() {return gp;}

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

    public List<Entity> getEntityList() {
        return entityList;
    }
}
