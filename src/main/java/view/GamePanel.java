package view;

import model.gameState.GameStateManager;
import model.quests.Quest;
import model.quests.QuestInitializer;
import model.sound.Playlist;
import model.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    static final int originalTileSize = 16;
    private static final int scale = 2;

    static public final int tileSize = originalTileSize * scale; //48*48
    private final int maxScreenCol = 25; //16 default
    private final int maxScreenRow = 19; //12 default
    private final int screenWidth = tileSize * maxScreenCol; //768 pixels
    private final int screenHeight = tileSize * maxScreenRow;//576
    private GameStateManager gsm;


    Thread gameThread;

    private final UI ui;

    int FPS = 60;


    List<Quest> questList = QuestInitializer.createQuestList();
    //transizione


    public GamePanel(){
        this.gsm = new GameStateManager(this);
        this.addKeyListener(gsm.getKeyH());
        this.ui = new UI(this, this.gsm);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //game loop
    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {

                // 1 UPDATE: Aggiornamento delle informazioni sulla posizione del personaggio
                update();

                //da cambiare con entity
                //enemy.handleInput();
                //enemy.update();

                // 2 DRAW: Disegno dello schermo con le informazioni aggiornate
                //paintImmediately(0, 0, screenWidth, screenHeight);  Non ho idea di come funzini, se repaint da problemi usare questo :)
                repaint(0, 0, screenWidth, screenHeight);

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        gsm.update();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gsm.draw(g);

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

    public GameStateManager getGsm() {
        return gsm;
    }
    public UI getUi() {
        return ui;
    }


    //transizione animata -------------------------------------------------------------------------------
}