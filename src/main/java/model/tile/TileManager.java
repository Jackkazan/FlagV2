package model.tile;

import model.entities.Entity;
import model.gameState.GameStateManager;
import model.tile.toolTMX.TMXReader;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static view.GamePanel.renderDistance;
import static view.GamePanel.tileSize;

public class TileManager {
    private GameStateManager gsm;
    private HashMap<Integer, BufferedImage> mappaSprite;
    private ArrayList<String> listaMatrici;
    private int numLayer;
    private int[][][] mapTileNum;

    private ArrayList<Rectangle2D.Double> collisionMap;

    private int maxMapCol;
    private int maxMapRow;
    // int mapWidth = tileSize * maxMapCol;
    // int mapHeigth = tileSize * maxMapRow;
    private BufferedImage bufferedImage;


    public TileManager(GameStateManager gsm, String pathTMXMap){
        this.gsm = gsm;
        TMXReader readMap = new TMXReader(pathTMXMap);
        this.mappaSprite= readMap.getMappaSprite();
        this.listaMatrici = readMap.getListaMatrici();
        this.numLayer=readMap.getNumLayer();
        this.maxMapCol = readMap.getMapWidth();
        this.maxMapRow = readMap.getMapHeigth();
        //matrice a tre livelli che memorizzerà la matrice di ciascun layer
        this.mapTileNum = new int[numLayer][maxMapRow][maxMapCol];
        this.collisionMap = readMap.getCollisionObjects();
        gsm.getPlayer().setCurrentCollisionMap(collisionMap);
        this.bufferedImage = new BufferedImage(maxMapCol * tileSize, maxMapRow * tileSize, BufferedImage.TYPE_INT_ARGB);


        for(int i=0;i<numLayer;i++)
            loadMap(listaMatrici.get(i).split("\n"), i);

    }

    public void loadMap(String[] righeStringa, int layerIndex){

        // Determina il numero di colonne della matrice
        int colonne = righeStringa[0].split(",").length;

        // Crea la matrice temporanea
        int[][] matrice = new int[maxMapRow][colonne];

        // Riempire la matrice con i valori convertiti da stringa a int
        for (int i = 0; i < maxMapRow; i++) {
            String[] valoriRiga = righeStringa[i].split(",");
            for (int j = 0; j < colonne; j++) {
                matrice[i][j] = Integer.parseInt(valoriRiga[j].trim());
            }
        }
        // Assegna la matrice alla corretta posizione in mapTileNum
        mapTileNum[layerIndex] = matrice;

    }

    public void draw(Graphics2D g2d, List<Entity> nearEntityList){
        // Calcola le coordinate del player nella mappa
        int playerMapX = -gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int playerMapY = -gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        createBufferedImage(nearEntityList);
        // Disegna il bufferedImage considerando la posizione del player
        g2d.drawImage(bufferedImage, playerMapX, playerMapY, null);
    }

    private void createBufferedImage(List<Entity> nearEntityList) {
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.clearRect(0,0,bufferedImage.getWidth(), bufferedImage.getHeight());
        
        for (int layerIndex = 0; layerIndex < numLayer; layerIndex++) {
            for (int worldRow = 0; worldRow < maxMapRow; worldRow++) {
                for (int worldCol = 0; worldCol < maxMapCol; worldCol++) {
                    int tileNum = mapTileNum[layerIndex][worldRow][worldCol];

                    int worldX = worldCol * tileSize;
                    int worldY = worldRow * tileSize;
                    if(isTileNearPlayer(worldX,worldY))
                        g2d.drawImage(mappaSprite.get(tileNum), worldX, worldY, tileSize, tileSize, null);
                }
            }
        }
        nearEntityList = nearEntityList.stream()
                .sorted(Comparator.comparing(Entity::getY))
                .collect(toList());

        for (Entity entity : nearEntityList) {
            entity.draw(g2d);
        }


    }

    //per memorizzare nel buffer solo ciò che sta attorno al player
    private boolean isTileNearPlayer(int worldX, int worldY) {

        int playerMapX = gsm.getPlayer().getX();
        int playerMapY = gsm.getPlayer().getY();

        //area renderizzata e disegnata
        int bufferRendering = tileSize * renderDistance;

        return worldX  + bufferRendering > playerMapX  &&
                worldX  - bufferRendering < playerMapX  &&
                worldY  + bufferRendering > playerMapY  &&
                worldY  - bufferRendering < playerMapY ;
    }

    public ArrayList<Rectangle2D.Double> getCollisionMap() {
        return this.collisionMap;
    }

    public int getMaxMapCol() {
        return maxMapCol;
    }

    public int getMaxMapRow() {
        return maxMapRow;
    }
}
