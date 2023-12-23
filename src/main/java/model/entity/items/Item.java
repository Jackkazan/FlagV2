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
    private BufferedImage staticImage;
    private BufferedImage animateImage1, animateImage2, animateImage3, animateImage4;
    private int speedChangeAnimateSprite;
    private List<Quest> relatedQuests= new ArrayList<>();

    public Item() {
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
    }

    @Override
    public void draw(Graphics2D graphics2D){

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if(staticImage != null && gsm.getMapManager().getCurrentMap() == this.tileManager)
            graphics2D.drawImage(staticImage, screenX, screenY, (tileSize*imageWidth)/16, (tileSize*imageHeight)/16, null);

    }
    @Override
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

    public void setStaticImage(String pathImage){
        try {
            this.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));

        }catch (IOException e){
            e.printStackTrace();
        }
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

    public boolean questListIsDone() {

        for(Quest quest : relatedQuests){
            if(!quest.isDone())
                return false;
        }
        return true;
    }

    public static class ItemBuilder {
        private Item item;

        public ItemBuilder(int x, int y){
            this.item = new Item();
            this.item.x = x *tileSize;
            this.item.y = y *tileSize;
        }

        public ItemBuilder setRelatedQuests(Quest... quests) {
            this.item.relatedQuests.addAll(Arrays.asList(quests));
            return this;
        }
        public ItemBuilder setRelatedQuests(List<Quest> relatedQuests) {
            this.item.relatedQuests = relatedQuests;
            return this;
        }
        // Metodo per impostare il fattore di scala
        public ItemBuilder setImageDimension(int imageWidth, int imageHeigth) {
            this.item.imageWidth = imageWidth;
            this.item.imageHeight = imageHeigth;
            return this;
        }

        public ItemBuilder setScale(int scale) {
            this.item.scale = scale;
            return this;
        }


        public ItemBuilder setName(String name){
            this.item.name = name;
            return this;
        }

        public ItemBuilder setStaticImage(String pathImage) {
            try{
                this.item.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public ItemBuilder setStaticImage(BufferedImage staticImage) {
            this.item.staticImage = staticImage;
            return this;
        }

        public ItemBuilder setCollisionArea(int larghezza, int altezza) {
            this.item.collisionArea = new Rectangle(this.item.x, this.item.y , larghezza, altezza);
            return this;
        }
        public ItemBuilder setCollisionArea(int x, int y, int larghezza, int altezza) {
            this.item.collisionArea = new Rectangle(x, y , larghezza, altezza);
            return this;
        }

        public ItemBuilder setContainedMap(TileManager tileManager) {
            this.item.tileManager = tileManager;
            return this;
        }
        public ItemBuilder setInteractable(boolean interactable) {
            this.item.isInteractable = interactable;
            return this;
        }

        public ItemBuilder setInteractionAction(Interactable interactionActionItems){
            this.item.interactionAction = interactionActionItems;
            return this;
        }


        public ItemBuilder setSpeedChangeAnimateSprite(int speedChangeAnimateSprite) {
            this.item.speedChangeAnimateSprite = speedChangeAnimateSprite;
            return this;
        }

        public ItemBuilder setAnimateImages(String path1, String path2, String path3, String path4) {
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



    public List<Quest> getRelatedQuests() {
        return relatedQuests;
    }
}


