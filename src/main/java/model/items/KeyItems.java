package model.items;

import controller.KeyHandler;
import model.entity.Entity;
import model.quests.Quest;
import model.tile.TileManager;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class KeyItems {
    private KeyHandler keyH;
    //Name object
    private String name;

    private BufferedImage staticImage;

    private BufferedImage animateImage1, animateImage2, animateImage3, animateImage4;
    private int speedChangeAnimateSprite;

    private int x;
    private int y;

    private Rectangle collisionArea;

    private boolean isInteractable = false;
    private List<Quest> relatedQuests= new ArrayList<>();

    private int scaleWidth = 1;
    private int scaleHeigth = 1;

    private GamePanel gamePanel;
    private TileManager tileManager;

    private InteractionAction interactionAction;
    // Metodo per impostare l'azione durante la costruzione dell'oggetto
    public void setInteractionAction(InteractionAction action) {
        this.interactionAction = action;
    }

    public void setInteractable(boolean interactable) {
        this.isInteractable = interactable;
    }

    public void setStaticImage(String pathImage) {
        try {
            this.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setCollisionArea(Rectangle collisionArea) {
        this.collisionArea = collisionArea;
    }

    private KeyItems() {}

    public boolean questListIsDone() {

        for(Quest quest : relatedQuests){
            if(!quest.isDone())
                return false;
        }
        return true;
    }

    public static class KeyItemsBuilder {
        private KeyItems keyItems;

        public KeyItemsBuilder(GamePanel gamePanel, int x, int y, KeyHandler keyH){
            this.keyItems = new KeyItems();
            this.keyItems.gamePanel = gamePanel;
            this.keyItems.x = x;
            this.keyItems.y = y;
            this.keyItems.keyH = keyH;
        }

        public KeyItemsBuilder setRelatedQuests(Quest... quests) {
            this.keyItems.relatedQuests.addAll(Arrays.asList(quests));
            return this;
        }
        // Metodo per impostare il fattore di scala
        public KeyItemsBuilder setScale(int scaleWidth, int scaleHeigth) {
            this.keyItems.scaleWidth = scaleWidth;
            this.keyItems.scaleHeigth = scaleHeigth;
            return this;
        }


        public KeyItemsBuilder setName(String name){
            this.keyItems.name = name;
            return this;
        }

        public KeyItemsBuilder setStaticImage(String pathImage) {
            try{
                this.keyItems.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public KeyItemsBuilder setCollisionArea(int larghezza, int altezza) {
            this.keyItems.collisionArea = new Rectangle(keyItems.x, keyItems.y, larghezza, altezza);
            return this;
        }

        public KeyItemsBuilder setContainedMap(TileManager tileManager) {
            this.keyItems.tileManager = tileManager;
            return this;
        }
        public KeyItemsBuilder setInteractible(boolean interactible) {
            this.keyItems.isInteractable = interactible;
            return this;
        }


        public KeyItemsBuilder setSpeedChangeAnimateSprite(int speedChangeAnimateSprite) {
            this.keyItems.speedChangeAnimateSprite = speedChangeAnimateSprite;
            return this;
        }

        public KeyItemsBuilder setAnimateImages(String path1, String path2, String path3, String path4) {
            try {
                this.keyItems.animateImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path1)));
                this.keyItems.animateImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path2)));

                this.keyItems.animateImage3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path3)));
                this.keyItems.animateImage4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public KeyItems build() {
            return this.keyItems;
        }

    }

    public BufferedImage draw(Graphics2D graphics2D){

        int screenX = this.x - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX();
        int screenY = this.y - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY();


        // se succede qualcosa l'immagine può cambiare avviando animazione o altro
        if(false){
            BufferedImage[] images = {animateImage1, animateImage2, animateImage3, animateImage4};
            //...............


            //-----------------
        }
        else{
            if(staticImage != null && gamePanel.getMapManager().getCurrentMap() == this.tileManager)
         {
            graphics2D.drawImage(staticImage, screenX, screenY, tileSize*scaleWidth, tileSize*scaleHeigth, null);
        }
    }
        return null;
    }

    public void update() {

        if(collisionArea != null)
            collisionArea.setLocation(x, y);
        // animazione se succede evento o altro
        interact();

    }

    public void interact() {
        // Verifica se il giocatore è nelle vicinanze e ha premuto il tasto "E"
        if (this.isInteractable && this.tileManager == gamePanel.getMapManager().getCurrentMap() && isPlayerNearby()) {
            if(keyH.interactPressed && interactionAction != null) {
                //System.out.println("Ho interagioto con "+this.name);
                interactionAction.performAction(this);
            }
        }
    }



    private boolean isPlayerNearby() {
        // Puoi definire la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.collisionArea!= null && gamePanel.getPlayer().getInteractionArea().intersects(this.collisionArea)){
            //System.out.println("Sto collidendo con "+ this.name);
            return true;
        }
        else return false;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public String getName() {
        return name;
    }

    public List<Quest> getRelatedQuests() {
        return relatedQuests;
    }
}


