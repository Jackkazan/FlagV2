package model.gameState;

import controller.KeyHandler;
import model.entity.Entity;
import model.items.KeyItems;
import model.entity.Player;
import model.sound.Playlist;
import model.sound.Sound;
import model.tile.MapManager;
import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static view.GamePanel.tileSize;

public class PlayState implements GameState{
    private Player player;
    private GamePanel gp;
    private GameStateManager gsm;
    private MapManager mapManager;

    private KeyHandler keyH;
    private Graphics2D graphics2D;

    private BufferedImage buffer;


    List<KeyItems> keyItemsList;
    public PlayState(GamePanel gp, GameStateManager gsm, MapManager mapManager,Player player, KeyHandler keyH) {
        this.gp = gp;
        this.gsm = gsm;
        this.mapManager = mapManager;
        this.player = player;
        this.keyH = keyH;
        this.buffer = new BufferedImage(gp.getScreenWidth(), gp.getScreenHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void update() {
        if (keyH.isPaused() && !gsm.isAlreadyPaused() && !gsm.isInDialogue()){
            gsm.setState(GameStateManager.State.PAUSE);
        }
        if(!gsm.isInDialogue())
            player.update();

        // Aggiornamento degli NPC
        for (Entity npc : gsm.getNpcList()) {
            npc.update();
        }

        // Aggiornamento degli oggetti
        for (KeyItems items : gsm.getKeyItemsList()) {
            items.update();
        }

        // Gestione delle transizioni della mappa
        mapManager.manageTransitions();
    }

    @Override
    public void draw(Graphics g) {

        //this.graphics2D = (Graphics2D) g;
        // Disegna sulla buffer
        Graphics2D bufferGraphics = buffer.createGraphics();
        // Cancella completamente l'immagine del buffer
        bufferGraphics.clearRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        mapManager.draw(bufferGraphics);
        for (Entity npc : gsm.getNpcList()) {
            npc.draw(bufferGraphics);
        }
        for (KeyItems items : gsm.getKeyItemsList()) {
            items.draw(bufferGraphics);
        }
        player.draw(bufferGraphics);
        gp.getUi().draw(bufferGraphics);
        drawToTempScreen();
        // Copia l'intera immagine buffer sulla schermata
        g.drawImage(buffer, 0, 0, gp);
        //graphics2D.dispose();
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

}
