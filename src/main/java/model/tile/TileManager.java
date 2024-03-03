package model.tile;

import model.entities.Entity;
import model.gameState.GameStateManager;
import model.tile.toolTMX.TMXReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.renderDistance;
import static view.GamePanel.tileSize;

public class TileManager {
    private GameStateManager gsm;
    /*
    private HashMap<Integer, BufferedImage> mappaSprite;
    private ArrayList<String> listaMatrici;
    private int numLayer;
    private int[][][] mapTileNum;
    */

    private String nameMap;

    private ArrayList<Rectangle2D.Double> collisionMap;
    private int maxMapCol;
    private int maxMapRow;
    // int mapWidth = tileSize * maxMapCol;
    // int mapHeigth = tileSize * maxMapRow;
    private BufferedImage image;


    public TileManager(GameStateManager gsm, String pathPngMap){

        this.gsm = gsm;

        this.nameMap = pathPngMap;

        // Rimuovi la parte "png Maps/" e ottieni il nome del file senza estensione
        String filename = pathPngMap.substring(pathPngMap.lastIndexOf("/") + 1, pathPngMap.lastIndexOf("."));

        // Costruisci il percorso della mappa di collisione
        String collisionPath = pathPngMap.replace("png Maps/", "collisions Maps/").replace(".png",".tmx");

        System.out.println(collisionPath);

        this.collisionMap = new TMXReader(collisionPath).getCollisionObjects();


        try {
            // Legge l'immagine da un file
            File file = new File(pathPngMap);
            image = ImageIO.read(file);
            // Controlla se l'immagine è stata letta correttamente
            if (image != null) {

                maxMapCol = image.getWidth()/tileSize;
                maxMapRow = image.getHeight()/tileSize;
                System.out.println("Dimensioni dell'immagine: " + maxMapCol + "x" + maxMapRow);

            } else {
                System.out.println("Impossibile leggere l'immagine.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void draw(Graphics2D g2d, List<Entity> nearEntityList){
        // Calcola le coordinate del player nella mappa
        int playerMapX = -gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int playerMapY = -gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        createBufferedImage(nearEntityList);
        // Disegna il bufferedImage considerando la posizione del player
        g2d.drawImage(image, playerMapX, playerMapY, null);


    }

    private void createBufferedImage(List<Entity> nearEntityList) {

        Graphics2D g2d = image.createGraphics();

        for (Entity entity : nearEntityList)
            entity.draw(g2d);

        g2d.clearRect(0,0,maxMapCol, maxMapRow);
        
        for (int worldRow = 0; worldRow < maxMapRow; worldRow++) {
            for (int worldCol = 0; worldCol < maxMapCol; worldCol++) {

                int worldX = worldCol * tileSize;
                int worldY = worldRow * tileSize;

                if(isTileNearPlayer(worldX,worldY)) {
                    // Ottieni la porzione dell'immagine corrispondente al tile 32x32
                    BufferedImage tileImage = image.getSubimage(worldCol * tileSize, worldRow * tileSize, tileSize, tileSize);

                    // Disegna il tile
                    g2d.drawImage(tileImage, worldX, worldY, tileSize, tileSize, null);


                }}
        }
        /*
        nearEntityList = nearEntityList.stream()
                .sorted(Comparator.comparing(Entity::getY))
                .collect(toList());
         */


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

    public String getNameMap() {
        return nameMap;

    }
}
