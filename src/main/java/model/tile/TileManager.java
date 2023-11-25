package model.tile;

import model.collisioni.CollisionObject;
import model.entity.Player;
import model.toolTMX.TMXReader;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static view.GamePanel.tileSize;

public class TileManager {
    GamePanel gp;
    HashMap<Integer, BufferedImage> mappaSprite;
    ArrayList<String> listaMatrici;
    int numLayer;
    int[][][] mapTileNum;

    ArrayList<CollisionObject> collisionMap;

    int maxMapCol;
    int maxMapRow;
    // int mapWidth = tileSize * maxMapCol;
    // int mapHeigth = tileSize * maxMapRow;

    public TileManager(GamePanel gp, String pathTMXMap){

        this.gp = gp;
        TMXReader readMap = new TMXReader(pathTMXMap);
        mappaSprite= readMap.getMappaSprite();
        listaMatrici = readMap.getListaMatrici();
        numLayer=readMap.getNumLayer();
        maxMapCol = readMap.getMapWidth();
        maxMapRow = readMap.getMapHeigth();
        //matrice a tre livelli che memorizzerà la matrice di ciascun layer
        mapTileNum = new int[numLayer][maxMapCol][maxMapRow];
        collisionMap = readMap.getCollisionObjects();

        for(int i=0;i<numLayer;i++)
            loadMap(listaMatrici.get(i).split("\n"), i);

    }

    public void loadMap(String[] righeStringa, int layerIndex){

        // Determina il numero di colonne della matrice
        int colonne = righeStringa[0].split(",").length;

        // Crea la matrice temporanea
        int[][] matrice = new int[righeStringa.length][colonne];

        // Riempire la matrice con i valori convertiti da stringa a int
        for (int i = 0; i < righeStringa.length; i++) {
            String[] valoriRiga = righeStringa[i].split(",");
            for (int j = 0; j < colonne; j++) {
                matrice[i][j] = Integer.parseInt(valoriRiga[j].trim());
            }
        }
        mapTileNum[layerIndex] = matrice;
    }
    public void draw(Graphics2D g2){

        for(int layerIndex=0; layerIndex <numLayer; layerIndex++){

            int worldCol=0;
            int worldRow=0;

            while(worldCol< maxMapCol && worldRow <maxMapRow){

                int tileNum = mapTileNum[layerIndex][worldRow][worldCol];

                int worldX = worldCol * tileSize;
                int worldY = worldRow * tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if(worldX + tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - tileSize < gp.player.worldY + gp.player.screenY){
                    g2.drawImage(mappaSprite.get(tileNum), screenX, screenY, tileSize, tileSize, null);
                }

                worldCol++;
                if(worldCol == maxMapCol){
                    worldCol=0;
                    worldRow++;

                }
            }
        }
    }

    public ArrayList<CollisionObject> getCollisionMap() {
        return collisionMap;
    }
}
