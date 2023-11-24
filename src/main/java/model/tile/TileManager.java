package model.tile;

import model.toolTMX.TMXReader;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class TileManager {
    GamePanel gp;
    HashMap<Integer, BufferedImage> mappaSprite;
    ArrayList<String> listaMatrici;
    int numLayer;
    int mapTileNum[][][];

    public TileManager(GamePanel gp, String pathTMXMap){

        this.gp = gp;
        TMXReader readMap = new TMXReader(pathTMXMap);
        mappaSprite= readMap.getMappaSprite();
        listaMatrici = readMap.getListaMatrici();
        numLayer=readMap.getNumLayer();
        mapTileNum = new int[numLayer][gp.maxWorldCol][gp.maxWorldRow];
        for(int i=0;i<numLayer;i++)
            loadMap(listaMatrici.get(i).split("\n"), i);

    }

    public void loadMap(String[] righeStringa, int layerIndex){

        // Determina il numero di colonne assumendo che ogni riga abbia la stessa lunghezza
        int colonne = righeStringa[0].split(",").length;

        // Crea la matrice
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

            while(worldCol< gp.maxWorldCol && worldRow <gp.maxWorldRow){

                int tileNum = mapTileNum[layerIndex][worldRow][worldCol];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                    g2.drawImage(mappaSprite.get(tileNum), screenX, screenY, gp.tileSize, gp.tileSize, null);
                }

                worldCol++;
                if(worldCol == gp.maxWorldCol){
                    worldCol=0;
                    worldRow++;

                }
            }
        }
    }
}
