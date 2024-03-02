package model.gameState;


import model.entities.Entity;
import model.entities.characters.player.Player;
import model.entities.traps.Trap;
import model.tile.MapManager;
import view.UI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static view.GamePanel.renderDistance;
import static view.GamePanel.tileSize;

public class PlayState implements GameState{

    private final Player player;

    private final MapManager mapManager;
    private final BufferedImage buffer;

    private List<Entity> nearEntityList;
    private Graphics2D graphics2D;

    public PlayState(MapManager mapManager,Player player) {
        this.mapManager = mapManager;
        this.player = player;
        this.buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        this.nearEntityList = collectNearEntityList(gsm.getCurrentEntityList());
    }

    @Override
    public void update() {

        if (keyH.pauseSwitch() && !gsm.isInDialogue())
            gsm.setState(GameStateManager.State.PAUSE);


        //colleziona solo gli oggetti vicini al player
        nearEntityList = collectNearEntityList(gsm.getCurrentEntityList());


        System.out.println(nearEntityList);


        if(!gsm.isInDialogue())
            player.update();


        for(Entity entity : nearEntityList)
            if(!entity.equals(gsm.player) && entity.getTileManager().equals(mapManager.getCurrentMap()))
                entity.update();



        // Gestione delle transizioni della mappa
        mapManager.manageTransitions();
        //QuestManager.load();

    }

    /*
    @Override
    public void draw(Graphics g) {

        //this.graphics2D = (Graphics2D) g;
        // Disegna sulla buffer
        Graphics2D bufferGraphics = buffer.createGraphics();

        // Cancella completamente l'immagine del buffer
        bufferGraphics.clearRect(0, 0, screenWidth, screenHeight);

        mapManager.draw(bufferGraphics);


        for(Entity entity : nearEntityList)
            entity.draw(bufferGraphics);


        UI.getInstance().draw(bufferGraphics);
        drawToTempScreen();
        // Copia l'intera immagine buffer sulla schermata
        g.drawImage(buffer, 0, 0, null);

        // Dispose del bufferGraphics
        bufferGraphics.dispose();

    }

     */
    @Override
    public void draw(Graphics g) {


        this.graphics2D = (Graphics2D) g;


        mapManager.draw(graphics2D , nearEntityList);
        player.draw(graphics2D);

        UI.getInstance().draw(graphics2D);

        drawToTempScreen();
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
        graphics2D.drawString("ScreenX: " + player.getScreenX(), x, y);
        y += lineHeight;
        graphics2D.drawString("ScreenY: " + player.getScreenY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + (player.getX() + player.getCollisionArea().x) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + (player.getY() + player.getCollisionArea().y) / tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);
    }

    public List<Entity> collectNearEntityList(List<Entity> currentEntityList) {

        // Ordina la lista in base alla distanza tra ciascuna entità e il player
        return currentEntityList.stream()
                .filter(entity -> {
                    double distance = distanceToPlayer(entity, player);
                    return distance < tileSize * renderDistance;
                })
                .sorted(Comparator.comparingDouble((Entity entity) -> distanceToPlayer(entity, player)))
                .collect(Collectors.toList());
    }


    private double distanceToPlayer(Entity entity, Player player) {
        // Calcola la distanza tra l'entità e il player (usando la distanza Euclidea)
        double dx = entity.getX() - player.getX();
        double dy = entity.getY() - player.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Entity> getNearEntityList() {
        return nearEntityList;
    }
}
