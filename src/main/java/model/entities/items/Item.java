package model.entities.items;

import controller.KeyHandler;
import model.entities.Entity;
import model.entities.Prototype;
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

public class Item extends Entity implements Prototype {


    protected BufferedImage animateImage1, animateImage2, animateImage3, animateImage4;
    protected int speedChangeAnimateSprite;
    private List<Quest> relatedQuests= new ArrayList<>();
    private int offsetY;

    public Item() {
        super();
    }

    @Override
    public void draw(Graphics2D graphics2D){

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if(staticImage != null && gsm.getMapManager().getCurrentMap() == this.tileManager)
            graphics2D.drawImage(this.staticImage, screenX, screenY+ this.offsetY, (tileSize*this.imageWidth)/16, (tileSize*this.imageHeight)/16, null);

    }
    @Override
    public void update(){

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

    /*private boolean isPlayerNearby() {
        //Definisci la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.collisionArea!= null && gsm.getPlayer().getInteractionArea().intersects(this.collisionArea)){
            System.out.println("Sto collidendo con "+ this.name);
            return true;
        }
        else return false;
    }
*/

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
            if(!quest.isCompleted())
                return false;
        }
        return true;
    }

    public void setCollisionArea(int x, int y, int collisionWidth, int collisionHeight) {
        this.collisionArea = new Rectangle(x*tileSize,y*tileSize,collisionWidth,collisionHeight);
    }

    public static class ItemBuilder extends EntityBuilder<Item,ItemBuilder>{

        public ItemBuilder(int x, int y){
            super();
            this.entity.x = x *tileSize;
            this.entity.y = y *tileSize;
        }

        public ItemBuilder setStaticImage(String pathImage) {
            try{
                this.entity.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }


        public ItemBuilder setOffsetY(int offsetY) {
            this.entity.offsetY = offsetY;
            return this;
        }

        public ItemBuilder setInteractImage(String pathImage){
            try{
                this.entity.interactImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public ItemBuilder setSpeedChangeAnimateSprite(int speedChangeAnimateSprite) {
            this.entity.speedChangeAnimateSprite = speedChangeAnimateSprite;
            return this;
        }

        public ItemBuilder setAnimateImages(String path1, String path2, String path3, String path4) {
            try {
                this.entity.animateImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path1)));
                this.entity.animateImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path2)));

                this.entity.animateImage3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path3)));
                this.entity.animateImage4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        @Override
        public Item build() {
            return this.entity;
        }

        @Override
        protected Item createEntity() {
            return new Item();
        }
    }

    public BufferedImage getStaticImage() {
        return staticImage;
    }

    public BufferedImage getAnimateImage1() {
        return animateImage1;
    }

    public BufferedImage getAnimateImage2() {
        return animateImage2;
    }

    public BufferedImage getAnimateImage3() {
        return animateImage3;
    }

    public BufferedImage getAnimateImage4() {
        return animateImage4;
    }

    public List<Quest> getRelatedQuests() {
        return relatedQuests;
    }
}


