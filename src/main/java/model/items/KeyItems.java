package model.items;

import controller.KeyHandler;
import model.entity.Entity;
import model.gameState.GameStateManager;
import model.quests.Quest;
import model.tile.TileManager;

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

    private InteractionActionItems interactionAction;
    private BufferedImage animateImage1, animateImage2, animateImage3, animateImage4;
    private int speedChangeAnimateSprite;

    private int x;
    private int y;

    private Rectangle collisionArea;

    private boolean isInteractable = false;
    private List<Quest> relatedQuests= new ArrayList<>();

    private int imageWidth;
    private int imageHeigth;

    private GameStateManager gsm;
    private TileManager tileManager;
    private int scale;


    // Metodo per impostare l'azione durante la costruzione dell'oggetto


    public void setInteractable(boolean interactable) {
        this.isInteractable = interactable;
    }

    public void setPosition(int x, int y){
        this.x = x*tileSize;
        this.y = y*tileSize;

    }
    public void setStaticImage(String pathImage){
        try {
            this.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void setCollisionArea(Rectangle collisionArea) {
        this.collisionArea = collisionArea;
    }

    public void setCollisionAreaPosition(int x, int y) {
        if (collisionArea != null) {
            collisionArea.setLocation(x, y);
        }
    }

    public void setCollisionAreaSize(int width, int height) {
        if (collisionArea != null) {
            collisionArea.setSize(width, height);
        }
    }

    public void setName(String name){
        this.name = name;
    }
    private KeyItems() {}

    public KeyItems clone(){
        KeyItems newKeyItems = new KeyItemsBuilder(gsm, this.x, this.y, keyH)
                .setName(this.name)
                .setImageDimension(this.imageWidth,this.imageHeigth)
                .setCollisionArea(0,0,16,16)
                .setContainedMap(this.tileManager)
                .setInteractible(this.isInteractable)
                .setInteractionAction(this.interactionAction)
                .build();

        return newKeyItems;
    }



    public boolean questListIsDone() {

        for(Quest quest : relatedQuests){
            if(!quest.isDone())
                return false;
        }
        return true;
    }

    public static class KeyItemsBuilder {
        private KeyItems keyItems;

        public KeyItemsBuilder(GameStateManager gsm, int x, int y, KeyHandler keyH){
            this.keyItems = new KeyItems();
            this.keyItems.gsm = gsm;
            this.keyItems.x = x *tileSize;
            this.keyItems.y = y *tileSize;
            this.keyItems.keyH = keyH;
        }

        public KeyItemsBuilder setRelatedQuests(Quest... quests) {
            this.keyItems.relatedQuests.addAll(Arrays.asList(quests));
            return this;
        }
        // Metodo per impostare il fattore di scala
        public KeyItemsBuilder setImageDimension(int imageWidth, int imageHeigth) {
            this.keyItems.imageWidth = imageWidth;
            this.keyItems.imageHeigth = imageHeigth;
            return this;
        }

        public KeyItemsBuilder setScale(int scale) {
            this.keyItems.scale = scale;
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

        public KeyItemsBuilder setCollisionArea(int posX, int posY ,int larghezza, int altezza) {
            this.keyItems.collisionArea = new Rectangle(posX, posY , larghezza, altezza);
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

        public KeyItemsBuilder setInteractionAction(InteractionActionItems interactionActionItems){
            this.keyItems.interactionAction = interactionActionItems;
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

    public static class KeyItemsFatory {

        public static KeyItems createKeyItems(KeyItems referenceKeyItems, String name, int x, int y, String pathImage) {
            KeyItems newKeyItems = referenceKeyItems.clone();
            newKeyItems.setName(name);
            newKeyItems.setPosition(x,y);
            newKeyItems.setStaticImage(pathImage);
            // Puoi impostare eventuali attributi specifici della nuova istanza
            return newKeyItems;
        }


    }

    public BufferedImage draw(Graphics2D graphics2D){

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if(staticImage != null && gsm.getMapManager().getCurrentMap() == this.tileManager)
            graphics2D.drawImage(staticImage, screenX, screenY, (tileSize*imageWidth)/16, (tileSize*imageHeigth)/16, null);

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
        if (this.isInteractable && this.tileManager == gsm.getMapManager().getCurrentMap() && isPlayerNearby()) {
            if(keyH.interactPressed && interactionAction != null) {
                System.out.println("Ho interagioto con "+this.name);
                interactionAction.performAction(this);
            }
        }
    }



    private boolean isPlayerNearby() {
        // Puoi definire la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.collisionArea!= null && gsm.getPlayer().getInteractionArea().intersects(this.collisionArea)){
            System.out.println("Sto collidendo con "+ this.name);
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


