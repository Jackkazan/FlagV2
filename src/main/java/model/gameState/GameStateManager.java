package model.gameState;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.NPCCreator;
import model.entity.Player;
import model.items.KeyItems;
import model.items.ObjectsCreator;
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

    private boolean alreadyPaused = false;
    private boolean inDialogue = false;
    Player player;
    public TileManager tileManagerZonaIniziale;
    public TileManager tileManagerCasettaIniziale;

    public TileManager tileManagerVillaggioSud;

    public TileManager tileManagerNegozioItemsVillaggioSud;


    //gestore mappe
    MapManager mapManager;

    List<KeyItems> keyItemsList;
    java.util.List<Entity> npcList;



    public GameStateManager(GamePanel gp) {
        this.gp = gp;
        this.keyH = new KeyHandler(this);
        this.currentState = new MenuState(gp, this);

    }
    public void Init(){
        this.player = new Player(gp, this, keyH);
        this.tileManagerZonaIniziale = new TileManager(gp, this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
        this.tileManagerCasettaIniziale = new TileManager(gp, this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
        this.tileManagerVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/VillaggioSud/VillaggioSud.tmx");
        this. tileManagerNegozioItemsVillaggioSud = new TileManager(gp, this, "src/main/resources/Map/NegozioItemsVillaggioSud/NegozioItemsVillaggioSud.tmx");
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
                currentState = new MenuState(gp, this);
                break;
            case PLAY:
                if(this.playState == null)
                    Init();
                currentState = playState;
                break;
            case PAUSE:
                //currentState = pauseState;
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
    }
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


}
