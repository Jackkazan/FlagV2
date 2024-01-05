package model.gameState;

import controller.KeyHandler;
import model.entities.Entity;
import model.entities.items.Item;
import model.entities.characters.player.Player;
import model.tile.MapManager;
import view.GamePanel;
import view.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static view.GamePanel.tileSize;

public class PlayState implements GameState{

    private Player player;
    private GameStateManager gsm;
    private MapManager mapManager;

    private KeyHandler keyH;
    private Graphics2D graphics2D;

    private BufferedImage buffer;


    List<Item> itemList;
    public PlayState(GameStateManager gsm, MapManager mapManager,Player player, KeyHandler keyH) {
        this.gsm = gsm;
        this.mapManager = mapManager;
        this.player = player;
        this.keyH = keyH;
        this.buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);

    }

    @Override
    public void update() {
        if (keyH.pauseSwitch() && !gsm.isInDialogue())
            gsm.setState(GameStateManager.State.PAUSE);

        if(!gsm.isInDialogue())
            player.update();

        for(Entity entity : gsm.getCurrentEntityList())
            if(!entity.equals(gsm.player) && entity.getTileManager().equals(mapManager.getCurrentMap()))
                entity.update();

        //ordina la lista
        gsm.setCurrentEntityList(gsm.getCurrentEntityList().stream()
                .sorted(Comparator.comparing(Entity:: getY))
                .collect(Collectors.toList()));

        // Gestione delle transizioni della mappa
        mapManager.manageTransitions();

    }

    @Override
    public void draw(Graphics g) {

        //this.graphics2D = (Graphics2D) g;
        // Disegna sulla buffer
        Graphics2D bufferGraphics = buffer.createGraphics();
        // Cancella completamente l'immagine del buffer
        bufferGraphics.clearRect(0, 0, screenWidth, screenHeight);

        mapManager.draw(bufferGraphics);

        for(Entity entity : gsm.getCurrentEntityList()) {
            System.out.println("Entità da disegnare "+ entity.getName());
            entity.draw(bufferGraphics);
        }

        UI.getInstance().draw(bufferGraphics);
        drawToTempScreen();
        // Copia l'intera immagine buffer sulla schermata
        g.drawImage(buffer, 0, 0, null);

        // Dispose del bufferGraphics
        bufferGraphics.dispose();

    }

//DEBUG
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
