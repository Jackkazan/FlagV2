package view;

import controller.KeyHandler;
import model.entity.Player;
import model.tile.MapManager;
import model.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    static final int originalTileSize = 16;
    public static final int scale = 2;

    static public final int tileSize = originalTileSize * scale; //48*48
    public final int maxScreenCol = 25; //16 default
    public final int maxScreenRow = 19; //12 default
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow;//576 pixels

    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);

    //settare posizione iniziale 30,50
    public TileManager tileManagerZonaIniziale = new TileManager(this, "src/main/resources/Map/ZonaIniziale/ZonaIniziale.tmx");

    //settare posizione iniziale 29,43
    public TileManager tileManagerCasettaIniziale = new TileManager(this, "src/main/resources/Map/StanzaIntroduzione/Casetta_Iniziale.tmx");

    MapManager mapManager = new MapManager(player, tileManagerCasettaIniziale, tileManagerZonaIniziale);


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
        mapManager.manageTransitions();
    }

    private void updateMapLogic(){

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        mapManager.draw(g2);
        player.draw(g2);
        drawToTempScreen(g2);

        g2.dispose();

        Toolkit.getDefaultToolkit().sync();
    }


    private void drawDebugInfo(Graphics2D graphics2D, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        int x = 10;
        int y = 400;
        int lineHeight = 20;

        graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics2D.setColor(Color.WHITE);

        graphics2D.drawString("WorldX: " + player.getX(), x, y);
        y += lineHeight;
        graphics2D.drawString("WorldY: " + player.getY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + (player.getX() + 48) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + (player.getY() + 48) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);

        System.out.println("Draw Time: "+ passedTime);
    }

    public void drawToTempScreen(Graphics2D g2d) {

        // DEBUG
        long drawStart = 0;
        if (keyH.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        // DEBUG
        if (keyH.isShowDebugText()) {
            drawDebugInfo(g2d, drawStart);
        }
    }

    public Player getPlayer() {
        return player;
    }
}