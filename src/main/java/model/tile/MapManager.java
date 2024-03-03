package model.tile;

import model.entities.Entity;
import model.entities.characters.player.Player;
import model.gameState.GameStateManager;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MapManager {

    private TileManager currentMap;
    private Player player;

    private GameStateManager gsm;

    //Tutte le mappe
    private TileManager tileManagerCasettaIniziale;
    private TileManager tileManagerZonaIniziale;
    private TileManager tileManagerVillaggioSud;
    private TileManager tileManagerNegozioItemsVillaggioSud;

    private TileManager tileManagerPianoTerraTavernaVillaggio;

    private TileManager tileManagerPrimoPianoTavernaVillaggio;
    private TileManager tileManagerDungeonSud;

    public MapManager(Player player, TileManager tileManagerCasettaIniziale, TileManager tileManagerZonaIniziale , TileManager tileManagerVillaggioSud,
                      TileManager tileManagerNegozioItemsVillaggioSud, TileManager tileManagerPianoTerraTavernaVillaggio, TileManager tileManagerPrimoPianoTavernaVillaggio, TileManager tileManagerDungeonSud) {
        this.player = player;
        this.gsm = GameStateManager.getInstance();
        this.tileManagerZonaIniziale = tileManagerZonaIniziale;//Mappa iniziale
        this.tileManagerCasettaIniziale = tileManagerCasettaIniziale;

        this.tileManagerVillaggioSud = tileManagerVillaggioSud;
        this.tileManagerNegozioItemsVillaggioSud = tileManagerNegozioItemsVillaggioSud;
        this.tileManagerPianoTerraTavernaVillaggio = tileManagerPianoTerraTavernaVillaggio;
        this.tileManagerPrimoPianoTavernaVillaggio = tileManagerPrimoPianoTavernaVillaggio;
        this.tileManagerDungeonSud = tileManagerDungeonSud;


        //da cambiare
        setMap(tileManagerCasettaIniziale);
    }

    public MapManager() {}

    public void setPlayer(Player player){
        this.player = player;
    }


    public void setMap(TileManager nextMap){
        currentMap = nextMap;
        player.setContainedMapName(currentMap.getNameMap());
        player.setTileManager(nextMap);
        player.setCurrentCollisionMap(nextMap.getCollisionMap());
        gsm.setCurrentEntityList(gsm.getEntityList().stream().filter(entity -> entity.getContainedMapName().equals(nextMap.getNameMap())).collect(Collectors.toList()));
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
            if(player.onTransitionPoint(49,84,1)){
                setMap(tileManagerDungeonSud);
                player.teleport(12,89);
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
            if(player.onTransitionPoint(1, 8, 1)) {
                setMap(tileManagerPianoTerraTavernaVillaggio);
                player.teleport(2, 4);
            }
        }

        if(currentMap == tileManagerDungeonSud) {
            if (player.onTransitionPoint(12, 92, 2)) {
                setMap(tileManagerVillaggioSud);
                player.teleport(49, 87);
            }
            if (player.onTransitionPoint(87, 14, 1))
                player.teleport(63, 35);


        }
    }

    public void draw(Graphics2D g2, List<Entity> nearEntityList){
        currentMap.draw(g2, nearEntityList);
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

    public TileManager getTileManagerDungeonSud() {
        return tileManagerDungeonSud;
    }

}
