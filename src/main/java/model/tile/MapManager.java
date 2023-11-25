package model.tile;

import model.entity.Player;

import java.awt.*;

public class MapManager {

    private TileManager currentMap;
    private Player player;

    //tutte le mappe
    private TileManager tileManagerCasettaIniziale;
    private TileManager tileManagerZonaIniziale;

    public MapManager(Player player, TileManager tileManagerCasettaIniziale, TileManager tileManagerZonaIniziale) {
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
        if(currentMap==tileManagerCasettaIniziale && player.onTransitionPoint(30, 47, 1)){
            setMap(tileManagerZonaIniziale);
        }
        if(currentMap == tileManagerZonaIniziale && player.onTransitionPoint(30,46,1)){
            setMap(tileManagerCasettaIniziale);
        }
    }

    public void draw(Graphics2D g2){
        currentMap.draw(g2);
    }

}
