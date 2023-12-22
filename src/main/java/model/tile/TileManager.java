package model.tile;

import model.gameState.GameStateManager;
import model.collisions.CollisionObject;
import model.tile.toolTMX.TMXReader;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import static view.GamePanel.tileSize;

public class TileManager {
    private GamePanel gamePanel;
    private GameStateManager gsm;
    private HashMap<Integer, BufferedImage> mappaSprite;
    private ArrayList<String> listaMatrici;
    private int numLayer;
    private int[][][] mapTileNum;

    private ArrayList<CollisionObject> collisionMap;
    private String currentMap;
    private int maxMapCol;
    private int maxMapRow;
    // int mapWidth = tileSize * maxMapCol;
    // int mapHeigth = tileSize * maxMapRow;


    public TileManager(GamePanel gamePanel, GameStateManager gsm, String pathTMXMap){

        this.gamePanel = gamePanel;
        this.gsm = gsm;
        this.currentMap = pathTMXMap;
        TMXReader readMap = new TMXReader(pathTMXMap);
        this.mappaSprite= readMap.getMappaSprite();
        this.listaMatrici = readMap.getListaMatrici();
        this.numLayer=readMap.getNumLayer();
        this.maxMapCol = readMap.getMapWidth();
        this.maxMapRow = readMap.getMapHeigth();
        //matrice a tre livelli che memorizzerà la matrice di ciascun layer
        this.mapTileNum = new int[numLayer][maxMapCol][maxMapRow];
        this.collisionMap = readMap.getCollisionObjects();
        gsm.getPlayer().setCurrentCollisionMap(collisionMap);

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
                int screenX = worldX - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
                int screenY = worldY - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

                if(worldX + tileSize > gsm.getPlayer().getX() - gsm.getPlayer().getScreenX() &&
                        worldX - tileSize < gsm.getPlayer().getX() + gsm.getPlayer().getScreenX() &&
                        worldY + tileSize > gsm.getPlayer().getY() - gsm.getPlayer().getScreenY() &&
                        worldY - tileSize < gsm.getPlayer().getY() + gsm.getPlayer().getScreenY()){
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
        return this.collisionMap;
    }

    public String getCurrentMap() {
        return this.currentMap;
    }
}
