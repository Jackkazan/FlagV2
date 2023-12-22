package model.entity.items;

import controller.KeyHandler;
import model.entity.Entity;
import model.entity.Prototype;
import model.gameState.GameStateManager;
import model.entity.Interactable;
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

public class Item extends Entity implements Prototype {
    private KeyHandler keyH;
    //Name object
    private String name;

    private BufferedImage staticImage;

    private Interactable interactionAction;
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



    // Metodo per clonare l'oggetto
    @Override
    public Prototype clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setName(String name){
        this.name = name;
    }
    private Item() {}

    public boolean questListIsDone() {

        for(Quest quest : relatedQuests){
            if(!quest.isDone())
                return false;
        }
        return true;
    }

    public static class KeyItemsBuilder {
        private Item item;

        public KeyItemsBuilder(GameStateManager gsm, int x, int y, KeyHandler keyH){
            this.item = new Item();
            this.item.gsm = gsm;
            this.item.x = x *tileSize;
            this.item.y = y *tileSize;
            this.item.keyH = keyH;
        }

        public KeyItemsBuilder setRelatedQuests(Quest... quests) {
            this.item.relatedQuests.addAll(Arrays.asList(quests));
            return this;
        }
        public KeyItemsBuilder setRelatedQuests(List<Quest> relatedQuests) {
            this.item.relatedQuests = relatedQuests;
            return this;
        }
        // Metodo per impostare il fattore di scala
        public KeyItemsBuilder setImageDimension(int imageWidth, int imageHeigth) {
            this.item.imageWidth = imageWidth;
            this.item.imageHeigth = imageHeigth;
            return this;
        }

        public KeyItemsBuilder setScale(int scale) {
            this.item.scale = scale;
            return this;
        }


        public KeyItemsBuilder setName(String name){
            this.item.name = name;
            return this;
        }

        public KeyItemsBuilder setStaticImage(String pathImage) {
            try{
                this.item.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public KeyItemsBuilder setStaticImage(BufferedImage staticImage) {
            this.item.staticImage = staticImage;
            return this;
        }

        public KeyItemsBuilder setCollisionArea(int larghezza, int altezza) {
            this.item.collisionArea = new Rectangle(this.item.x, this.item.y , larghezza, altezza);
            return this;
        }
        public KeyItemsBuilder setCollisionArea(int x, int y, int larghezza, int altezza) {
            this.item.collisionArea = new Rectangle(x, y , larghezza, altezza);
            return this;
        }

        public KeyItemsBuilder setContainedMap(TileManager tileManager) {
            this.item.tileManager = tileManager;
            return this;
        }
        public KeyItemsBuilder setInteractable(boolean interactable) {
            this.item.isInteractable = interactable;
            return this;
        }

        public KeyItemsBuilder setInteractionAction(Interactable interactionActionItems){
            this.item.interactionAction = interactionActionItems;
            return this;
        }


        public KeyItemsBuilder setSpeedChangeAnimateSprite(int speedChangeAnimateSprite) {
            this.item.speedChangeAnimateSprite = speedChangeAnimateSprite;
            return this;
        }

        public KeyItemsBuilder setAnimateImages(String path1, String path2, String path3, String path4) {
            try {
                this.item.animateImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path1)));
                this.item.animateImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path2)));

                this.item.animateImage3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path3)));
                this.item.animateImage4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Item build() {
            return this.item;
        }

    }


    public void draw(Graphics2D graphics2D){

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if(staticImage != null && gsm.getMapManager().getCurrentMap() == this.tileManager)
            graphics2D.drawImage(staticImage, screenX, screenY, (tileSize*imageWidth)/16, (tileSize*imageHeigth)/16, null);

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
        //Definisci la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
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


