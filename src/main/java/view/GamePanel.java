package view;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.NPCCreator;
import model.entity.Player;
import model.items.KeyItems;
import model.items.ObjectsCreator;
import model.tile.MapManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    static final int originalTileSize = 16;
    private static final int scale = 2;

    static public final int tileSize = originalTileSize * scale; //48*48
    private final int maxScreenCol = 25; //16 default
    private final int maxScreenRow = 19; //12 default
    private final int screenWidth = tileSize * maxScreenCol; //768 pixels
    private final int screenHeight = tileSize * maxScreenRow;//576 pixels
    private Graphics2D graphics2D;
    private BufferedImage buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    private Player player = new Player(this, keyH);

    //mappe
    public TileManager tileManagerZonaIniziale = new TileManager(this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
    public TileManager tileManagerCasettaIniziale = new TileManager(this, "src/main/resources/Map/StanzaIntroduzione/CasettaIniziale.tmx");
    //gestore mappe
    MapManager mapManager = new MapManager(this, player, tileManagerCasettaIniziale, tileManagerZonaIniziale);

    List<Entity> npcList = NPCCreator.createNPCs(this, mapManager, keyH);

    List<KeyItems> keyItemsList = ObjectsCreator.createObjects(this, mapManager, keyH);

    //transizione


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //game loop
    @Override
    public void run() {

        double drawInterval = 1000000000.0/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                //1 UPDATE: update information character positions
                update();
                //2 DRAW: draw the screen with the updated information
                paintImmediately(0,0, screenWidth,screenHeight);
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

        // Creazione di una lista temporanea per gli oggetti da rimuovere
        List<KeyItems> itemsToRemove = new ArrayList<>();

        // Aggiornamento degli NPC
        for (Entity npc : npcList) {
            npc.update();
        }

        // Aggiornamento degli oggetti e identificazione di quelli da rimuovere
        for (KeyItems items : keyItemsList) {
            items.update();

            // Aggiungi gli oggetti da rimuovere alla lista temporanea
            if (items.shouldBeRemoved()) {
                itemsToRemove.add(items);
            }
        }

        // Rimuovi gli oggetti dalla lista principale
        keyItemsList.removeAll(itemsToRemove);

        // Gestione delle transizioni della mappa
        mapManager.manageTransitions();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.graphics2D = (Graphics2D) g;

        // Disegna sulla buffer
        Graphics2D bufferGraphics = buffer.createGraphics();
        // Cancella completamente l'immagine del buffer
        bufferGraphics.clearRect(0, 0, screenWidth, screenHeight);
        mapManager.draw(bufferGraphics);
        for (Entity npc : npcList) {
            npc.draw(bufferGraphics);
        }
        for (KeyItems items : keyItemsList) {
            items.draw(bufferGraphics);
        }
        player.draw(bufferGraphics);
        drawToTempScreen();


        // Copia l'intera immagine buffer sulla schermata
        g.drawImage(buffer, 0, 0, this);

        graphics2D.dispose();
        // Dispose del bufferGraphics
        bufferGraphics.dispose();

    }

    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (keyH.isShowDebugText()) {
            drawStart = System.nanoTime();
        }
        // Crea un nuovo Graphics2D per il buffer
        Graphics2D bufferGraphics = buffer.createGraphics();

        // DEBUG
        if (keyH.isShowDebugText()) {
            drawDebugInfo(bufferGraphics, drawStart);
        }

        bufferGraphics.dispose();
    }

    //DEBUG
    private void drawDebugInfo(Graphics2D graphics2D, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        int x = 10;
        int y = 400;
        int lineHeight = 20;

        graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics2D.setColor(Color.WHITE);

        graphics2D.drawString("X: " + player.getX(), x, y);
        y += lineHeight;
        graphics2D.drawString("Y: " + player.getY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + (player.getX() + player.getCollisionArea().x) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + (player.getY() + player.getCollisionArea().y) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Graphics2D getGraphics2D() {
        return this.graphics2D;
    }

    public int getMaxScreenCol() {
        return this.maxScreenCol;
    }

    public int getMaxScreenRow() {
        return this.maxScreenRow;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getScale() { return this.scale; }

    public MapManager getMapManager() {
        return mapManager;
    }

    public List<Entity> getNpcList() {
        return npcList;
    }

    public List<KeyItems> getKeyItemsList() {
        return keyItemsList;
    }



    //transizione animata -------------------------------------------------------------------------------
}