package model.tile;

import model.entity.player.Player;
import view.GamePanel;

import java.awt.*;

public class MapManager {

    private TileManager currentMap;
    private Player player;

    //Tutte le mappe
    private TileManager tileManagerCasettaIniziale;
    private TileManager tileManagerZonaIniziale;
    private TileManager tileManagerVillaggioSud;
    private TileManager tileManagerNegozioItemsVillaggioSud;

    private TileManager tileManagerPianoTerraTavernaVillaggio;

    private TileManager tileManagerPrimoPianoTavernaVillaggio;
    GamePanel gamePanel;

    public MapManager(){}

    public MapManager(GamePanel gamePanel, Player player, TileManager tileManagerCasettaIniziale, TileManager tileManagerZonaIniziale, TileManager tileManagerVillaggioSud, TileManager tileManagerNegozioItemsVillaggioSud, TileManager tileManagerPianoTerraTavernaVillaggio, TileManager tileManagerPrimoPianoTavernaVillaggio) {
        this.gamePanel = gamePanel;
        this.player = player;
        this.tileManagerZonaIniziale = tileManagerZonaIniziale;//Mappa iniziale
        this.tileManagerCasettaIniziale = tileManagerCasettaIniziale;
        this.tileManagerVillaggioSud = tileManagerVillaggioSud;
        this.tileManagerNegozioItemsVillaggioSud = tileManagerNegozioItemsVillaggioSud;
        this.tileManagerPianoTerraTavernaVillaggio = tileManagerPianoTerraTavernaVillaggio;
        this.tileManagerPrimoPianoTavernaVillaggio = tileManagerPrimoPianoTavernaVillaggio;

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
                player.teleport(41,11);
            }
        }
        if(currentMap == tileManagerVillaggioSud) {
            if (player.onTransitionPoint(41, 7, 3)) {
                setMap(tileManagerZonaIniziale);
                player.teleport(64, 62);
            }
            if (player.onTransitionPoint(29, 27, 1)) {
                setMap(tileManagerNegozioItemsVillaggioSud);
                player.teleport(8, 9);
            }
            if(player.onTransitionPoint(63, 47, 1)) {
                setMap(tileManagerPianoTerraTavernaVillaggio);
                player.teleport(17, 9);
            }

        }

        if(currentMap == tileManagerNegozioItemsVillaggioSud){
            if(player.onTransitionPoint(8, 11, 1)) {
                setMap(tileManagerVillaggioSud);
                player.teleport(30, 29);
            }
        }

        if(currentMap == tileManagerPianoTerraTavernaVillaggio){
            if(player.onTransitionPoint(2, 2, 1)) {
                setMap(tileManagerPrimoPianoTavernaVillaggio);
                player.teleport(2, 5);
            }
            if(player.onTransitionPoint(17, 11, 1)) {
                setMap(tileManagerVillaggioSud);
                player.teleport(64, 51);
            }

        }

        if (currentMap == tileManagerPrimoPianoTavernaVillaggio) {
            if(player.onTransitionPoint(2, 8, 1)) {
                setMap(tileManagerPianoTerraTavernaVillaggio);
                player.teleport(2, 4);
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

    public TileManager getTileManagerNegozioItemsVillaggioSud() {
        return tileManagerNegozioItemsVillaggioSud;
    }

    public TileManager getTileManagerVillaggioSud() {
        return tileManagerVillaggioSud;
    }

    public TileManager getTileManagerPianoTerraTavernaVillaggio() {
        return tileManagerPianoTerraTavernaVillaggio;
    }

    public TileManager getTileManagerPrimoPianoTavernaVillaggio() {
        return tileManagerPrimoPianoTavernaVillaggio;
    }
}
