package model.tile;

import model.entity.Player;
import view.GamePanel;

import java.awt.*;

public class MapManager {

    private TileManager currentMap;
    private Player player;

    //tutte le mappe
    private TileManager tileManagerCasettaIniziale;
    private TileManager tileManagerZonaIniziale;
    GamePanel gamePanel;

    public MapManager(GamePanel gamePanel, Player player, TileManager tileManagerCasettaIniziale, TileManager tileManagerZonaIniziale) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.tileManagerZonaIniziale = tileManagerZonaIniziale;//Mappa iniziale
        this.tileManagerCasettaIniziale = tileManagerCasettaIniziale;

        setMap(tileManagerCasettaIniziale);
    }

    public void setMap(TileManager newMap){
        currentMap = newMap;
        player.setCurrentCollisionMap(currentMap.getCollisionMap());
    }

    public void manageTransitions(){
        if(currentMap==tileManagerCasettaIniziale && player.onTransitionPoint(19, 42, 1)){
            setMap(tileManagerZonaIniziale);
        }
        if(currentMap == tileManagerZonaIniziale && player.onTransitionPoint(19,41,1)){
            setMap(tileManagerCasettaIniziale);
        }


        //questo è a caso, devo implementarlo per bene
        if(currentMap == tileManagerZonaIniziale && player.onTransitionPoint(64,66,3)){
            //setMap(nuovaMappa);
            player.teleport(19,48);
        }
    }

    public void draw(Graphics2D g2){
        currentMap.draw(g2);
    }

    public TileManager getCurrentMap() {
        return currentMap;
    }

    public TileManager getTileManagerCasettaIniziale() {
        return tileManagerCasettaIniziale;
    }

    public TileManager getTileManagerZonaIniziale() {
        return tileManagerZonaIniziale;
    }
}
