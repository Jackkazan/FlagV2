package model.gameState;

import controller.KeyHandler;
import model.data.DataInitializer;
import model.entities.Entity;
import model.entities.characters.Characters;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.enemies.EnemyCreator;
import model.entities.characters.npc.Npc;
import model.entities.characters.npc.NpcCreator;
import model.entities.characters.player.Player;
import model.entities.items.Item;
import model.entities.items.ItemCreator;
import model.Dialogues.DialogueManager;
import model.entities.traps.Trap;
import model.entities.traps.TrapCreator;
import model.quests.Quest;
import model.quests.QuestManager;
import model.sound.Playlist;
import model.sound.Sound;
import model.tile.MapManager;
import model.tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class GameStateManager {


    private GameState playState;
    private GameState menuState;
    private GameState pauseState;
    private GameState dialogueState;
    private GameState currentState;
    private GameState previousState;

    //public static GamePanel gp;
    private KeyHandler keyH;
    private boolean inDialogue = false; //necessario per la logica della pausa durante i dialoghi
    Player player;
    private TileManager tileManagerZonaIniziale;
    private TileManager tileManagerCasettaIniziale;

    private TileManager tileManagerVillaggioSud;
    private TileManager tileManagerNegozioItemsVillaggioSud;
    private TileManager tileManagerPianoTerraTavernaVillaggio;
    private TileManager tileManagerPrimoPianoTavernaVillaggio;
    private TileManager tileManagerDungeonSud;


    //gestore mappe
    private MapManager mapManager;

    private List<Item> itemList;
    private List<Npc> npcList;
    private List<Enemy> enemyList;
    private List<Entity> entityList;
    private DialogueManager dialogueManager;
    private QuestManager questManager;
    private List<Entity> currentEntityList;
    private Playlist playlist = new Playlist();
    private List<Sound> songList = playlist.getSongList();
    private List<Quest> questList;
    private List<Trap> trapList;
    private boolean initialized, initializing;
    private static GameStateManager instance = null;

    public GameStateManager() {
        this.keyH = KeyHandler.getInstance();
        this.menuState = new MenuState();
        //this.dialogueManager = new DialogueManager(this);
        this.entityList = new ArrayList<>();

    }
    public static GameStateManager getInstance(){
          if (instance == null)
              instance = new GameStateManager();
          return instance;
    }
    public void init(){ // inizializza il player e tutti i dati
        initializing = true;
        this.player = new Player();
        this.player.setCurrentLife(6);
        this.tileManagerZonaIniziale = new TileManager(this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
        this.tileManagerCasettaIniziale = new TileManager(this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
        this.tileManagerVillaggioSud = new TileManager(this, "src/main/resources/Map/VillaggioSud/VillaggioSud.tmx");
        this.tileManagerNegozioItemsVillaggioSud = new TileManager(this, "src/main/resources/Map/NegozioItemsVillaggioSud/NegozioItemsVillaggioSud.tmx");
        this.tileManagerPianoTerraTavernaVillaggio = new TileManager(this,"src/main/resources/Map/TavernaVillaggio/PianoTerraTavernaVillaggio.tmx");
        this.tileManagerPrimoPianoTavernaVillaggio = new TileManager(this,"src/main/resources/Map/TavernaVillaggio/PrimoPianoTavernaVillaggio.tmx");
        this.tileManagerDungeonSud = new TileManager(this,"src/main/resources/Map/DungeonSud/sud_cave.tmx");
        this.mapManager = new MapManager(player, tileManagerCasettaIniziale, tileManagerZonaIniziale, tileManagerVillaggioSud, tileManagerNegozioItemsVillaggioSud,tileManagerPianoTerraTavernaVillaggio,tileManagerPrimoPianoTavernaVillaggio, tileManagerDungeonSud);
        this.playState = new PlayState(mapManager, player);
        this.pauseState = new PauseState();
        this.dialogueState = new DialogueState();

        this.npcList = NpcCreator.createNPCs(this, mapManager);
        this.itemList = ItemCreator.createObjects(this, mapManager, keyH);
        this.enemyList = EnemyCreator.createEnemies(this, mapManager);
        this.trapList = TrapCreator.createTraps(mapManager);

        this.entityList.addAll(this.npcList);
        this.entityList.addAll(this.itemList);
        this.entityList.addAll(this.enemyList);
        this.entityList.addAll(this.trapList);


        this.questManager = questManager.getInstance();
        this.dialogueManager = dialogueManager.getInstance();

        DataInitializer.initializeData();
        playMusicLoop(0);

        this.currentEntityList= entityList.stream().filter(entity -> entity.getTileManager().equals(mapManager.getCurrentMap())).collect(Collectors.toList());
        initialized = true;
    }
    public boolean isInitialized(){
          return initialized;
    }
    public boolean isInitializing(){
          return initializing;
    }

    public enum State{ MENU, PLAY, PAUSE, DIALOGUE, GAMEOVER, PREVIOUS}

    public void setState(State state) {
        keyH.releaseToggles();
        switch (state) {
            case MENU ->
                currentState = menuState;
            case PLAY ->{
                currentState = playState;
                 }
            case PAUSE -> {
                stopMusic(0);
                previousState = currentState;
                currentState = pauseState;}
            case DIALOGUE -> {
                currentState = dialogueState;
                inDialogue = true;
            }
            case GAMEOVER ->
                currentState = new GameOverState();

            case PREVIOUS ->//Uscendo dalla pausa bisogna tornare allo stato precedente (Non per forza playstate, ma anche dialogue, inventory ecc...)
                currentState = previousState;
        }
    }
    public void exitDialogue(){
        this.setState(GameStateManager.State.PLAY);
        this.inDialogue = false;
    } // Logica per uscire dal dialogo

    public void update() {
        currentState.update();
        keyH.interactRequest = false;
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }
    public void reload(){
          checkPoint();
          setState(State.PLAY);
    }
    public void checkPoint(){
        player.setRespawnValues();
        if(this.mapManager.getCurrentMap() == tileManagerDungeonSud){
            this.mapManager.setMap(tileManagerDungeonSud);
            this.player.teleport(12, 89);
        }
        enemyList.stream().filter(not(Enemy::isDespawned)).forEach(Enemy::respawn);
    }

    public void setQuestList(List<Quest> questList) {
        this.questList = questList;
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
    public List<Quest> getQuestList() {
        return questList;
    }

    public List<Npc> getNpcList() { return this.npcList; }
    public List<Enemy> getEnemyList(){return this.enemyList;}

    public List<Item> getKeyItemsList() {
        return itemList;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }
    public List<Trap> getTrapList(){
        return trapList;
    }

    public boolean isInDialogue() {
        return inDialogue;
    }


    public void playMusicLoop(int numSong) {
        songList.get(numSong).loop();

    }
    public void playSound(int numSong) {
        songList.get(numSong).play();

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

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public void setCurrentEntityList(List<Entity> currentEntityList) {
        this.currentEntityList = currentEntityList;
    }

    public List<Entity> getCurrentEntityList() {
        return currentEntityList;
    }


}
