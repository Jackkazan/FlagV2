package view;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.Player;
import model.tile.MapManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    private Player player = new Player(this, keyH);

    //mappe
    public TileManager tileManagerZonaIniziale = new TileManager(this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");
    public TileManager tileManagerCasettaIniziale = new TileManager(this, "src/main/resources/Map/StanzaIntroduzione/Casetta_Iniziale.tmx");
    //gestore mappe
    MapManager mapManager = new MapManager(this, player, tileManagerCasettaIniziale, tileManagerZonaIniziale);

    String path_up1 = "/npc/VecchiettaUp_0.png";
    String path_up2= "/npc/VecchiettaUp_1.png";

    String path_down1 = "/npc/VecchiettaDown_0.png";
    String path_down2 ="/npc/VecchiettaDown_1.png";

    String path_left1= "/npc/VecchiettaLeft_0.png";
    String path_left2= "/npc/VecchiettaLeft_1.png";

    String path_right1= "/npc/VecchiettaRight_0.png";
    String path_right2= "/npc/VecchiettaRight_1.png";



    Entity npc = new Entity.EntityBuilder(this, 22*tileSize, 46*tileSize)
            .setName("Vecchietta")
            .setSpeed(3)
            .setSpeedChangeSprite(40)
            .setSpriteNumLess1(1)
            .setTotalSprite(8)
            .setDefaultDirection("down")
            .setContainedMap(tileManagerZonaIniziale)
            .set8EntityImage(path_up1, path_up2,
                    path_down1, path_down2,
                    path_left1, path_left2,
                    path_right1, path_right2)
            .build();


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
                repaint();
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

    public void update(){

        player.update();
        npc.update();
        mapManager.manageTransitions();
    }

    private void updateMapLogic(){

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.graphics2D = (Graphics2D) g;

        mapManager.draw(graphics2D);
        player.draw(graphics2D);
        npc.draw(graphics2D);
        drawToTempScreen();
        graphics2D.dispose();

    }

    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (keyH.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        // DEBUG
        if (keyH.isShowDebugText()) {
            drawDebugInfo(graphics2D, drawStart);
        }
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


    //transizione animata -------------------------------------------------------------------------------
}