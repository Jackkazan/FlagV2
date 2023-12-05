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
    private TileManager tileManagerVillaggioSud;
    GamePanel gamePanel;

    public MapManager(GamePanel gamePanel, Player player, TileManager tileManagerCasettaIniziale, TileManager tileManagerZonaIniziale, TileManager tileManagerVillaggioSud) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.tileManagerZonaIniziale = tileManagerZonaIniziale;//Mappa iniziale
        this.tileManagerCasettaIniziale = tileManagerCasettaIniziale;
        this.tileManagerVillaggioSud = tileManagerVillaggioSud;

        setMap(tileManagerCasettaIniziale);
    }

    public void setMap(TileManager newMap){
        currentMap = newMap;
        player.setCurrentCollisionMap(currentMap.getCollisionMap());
    }

    public void manageTransitions(){
        if(currentMap==tileManagerCasettaIniziale && player.onTransitionPoint(4, 10, 1)){
            setMap(tileManagerZonaIniziale);
            player.teleport(18,43);
        }

        if(currentMap == tileManagerZonaIniziale){
            if(player.onTransitionPoint(18,41,1)){
                setMap(tileManagerCasettaIniziale);
                player.teleport(4,9);
            }
            if(player.onTransitionPoint(64,69,3)) {
                setMap(tileManagerVillaggioSud);
                player.teleport(41,13);
            }
        }
        if(currentMap == tileManagerVillaggioSud) {
            if (player.onTransitionPoint(41, 9, 3)) {
                setMap(tileManagerZonaIniziale);
                player.teleport(64, 62);
            }
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
