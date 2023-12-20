package model.entity;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.quests.Interactable;
import model.tile.TileManager;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

//Class for npc and enemy
public class Entity {
    //Essentials
    private GamePanel gamePanel;
    private GameStateManager gsm;
    private KeyHandler keyH;
    public enum swordStateAndArmor { IronSwordNoArmor, IronSwordAndArmor, GoldSwordAndArmor, RubySwordAndArmor }

    //Name entity es. npc_1 ecc...
    private String name;

    //CHARACTER INFO
    private int x;
    private int y;
    private int speed;
    private int speedChangeSprite;
    private int spriteNum;
    private int maxLife;
    private int currentLife;
    private String direction;
    swordStateAndArmor currentSwordStateAndArmor;

    private BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    private int spriteCounter = 0;
    private int totalSprite;
    private int scale;

    private Rectangle collisionArea;

    private TileManager tileManager;
    private boolean isInteractable;

    private Interactable interactionAction;
    private int imageWidth;
    private int imageHeight;

    private int screenX;
    private int screenY;

    private Entity(){}

    public Entity(String entity){
        if(entity.equalsIgnoreCase("Player")){
            this.gamePanel = GameStateManager.gp;
            this.keyH = GameStateManager.keyH;
            this.gsm = GameStateManager.gp.getGsm();
            this.x = GamePanel.tileSize*3;
            this.y = GamePanel.tileSize*4;
            this.screenX = this.getGamePanel().getScreenWidth()/2 - (tileSize/2);
            this.screenY = this.getGamePanel().getScreenHeight()/2 - (tileSize/2);
            this.maxLife = 6;
            this.currentLife = maxLife;
            this.speed = 4;
            this.scale = 5;
            this.spriteCounter = 0;
            this.spriteNum = 3;
            this.direction = "down";
            currentSwordStateAndArmor = swordStateAndArmor.IronSwordNoArmor;
        }
        else if(entity.equalsIgnoreCase("NPC")){
            this.speed = 2;
            this.scale = 5;
            this.totalSprite = 8;
            this.isInteractable = true;
        }
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return this.spriteNum;
    }

    public int getSpriteCounter() {
        return this.spriteCounter;
    }

    public int getMaxLife() {
        return this.maxLife;
    }

    public int getCurrentLife() {
        return this.currentLife;
    }

    public KeyHandler getKeyH() {
        return this.keyH;
    }

    public int getScreenX() {
        return this.screenX;
    }

    public int getScreenY() {
        return this.screenY;
    }

    public GamePanel getGamePanel() {
        return this.gamePanel;
    }

    public GameStateManager getGsm() {
        return this.gsm;
    }

    public KeyHandler getKeyHandler() {
        return this.keyH;
    }
    public void draw(Graphics2D graphics2D) {
        BufferedImage[] images = switch (direction) {
            case "up" -> new BufferedImage[]{up1, up2, up3, up4};
            case "down" -> new BufferedImage[]{down1, down2, down3, down4};
            case "left" -> new BufferedImage[]{left1, left2, left3, left4};
            case "right" -> new BufferedImage[]{right1, right2, right3, right4};
            default -> null;
        };

        int screenX = this.x - gsm.getPlayer().getX() + gsm.getPlayer().getScreenX();
        int screenY = this.y - gsm.getPlayer().getY() + gsm.getPlayer().getScreenY();

        if (images != null && gsm.getMapManager().getCurrentMap() == this.tileManager) {
            graphics2D.drawImage(images[spriteNum], screenX, screenY, (imageWidth/2)*scale , (imageHeight/2)*scale,null);
        }
    }
    public void setPath(){
        // Definisci il percorso predefinito
        int[] pathX = new int[]{this.x, this.x + GamePanel.tileSize, this.x, this.x - GamePanel.tileSize};  // Esempio: movimento orizzontale a destra, poi su, poi a sinistra
        int[] pathY = new int[]{this.y, this.y + GamePanel.tileSize, this.y, this.y - GamePanel.tileSize};       // Modifica il percorso secondo le tue esigenze
        int pathIndex = 0;  // Inizia dal primo punto del percorso

        // Muovi l'entità lungo il percorso predefinito
        this.x = pathX[pathIndex];
        this.y = pathY[pathIndex];
        pathIndex++;
        if(pathIndex == 3) pathIndex = 0;
        // Se hai raggiunto la fine del percorso, ricomincia da capo
    }

    public void update() {
        collisionArea.setLocation(x, y);
        if (totalSprite == 16) {
            //alternatore di sprite
            spriteCounter++;
            //più è alto, più è lento
            if (spriteCounter > speedChangeSprite) {
                spriteNum = (spriteNum + 1) % 4;
                spriteCounter = 0;
            }
        } else {
            spriteCounter++;
            //più è alto, più è lento
            if (spriteCounter > speedChangeSprite) {
                spriteNum = (spriteNum + 1) % 2;
                spriteCounter = 0;

            }
        }
        setPath();
        interact();

    }

    public void interact() {
        // Verifica se il giocatore è nelle vicinanze e ha premuto il tasto "E"
        if (this.isInteractable && this.tileManager == gsm.getMapManager().getCurrentMap() && isPlayerNearby()) {
            if(keyH.interactPressed && interactionAction != null) {
                //System.out.println("Ho interagito con "+this.name);
                interactionAction.performAction(this);
            }
        }
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }



    private boolean isPlayerNearby() {
        // puoi definire la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.collisionArea!= null && gsm.getPlayer().getInteractionArea().intersects(this.collisionArea)){
            System.out.println("Sto collidendo con "+ this.name);
            return true;
        }
        else return false;
    }

    public Rectangle getCollisionArea() {
        return this.collisionArea;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getDirection() {
        return direction;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getScale() {
        return scale;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public static class EntityBuilder {
        private Entity entity;
        private int[] pathX;  // Array delle coordinate x del percorso
        private int[] pathY;  // Array delle coordinate y del percorso
        private int pathIndex;

        public EntityBuilder(GamePanel gamePanel, GameStateManager gsm, int x, int y, KeyHandler keyH, String entity) {
            this.entity = new Entity(entity);
            this.entity.gamePanel = gamePanel;
            this.entity.gsm = gsm;
            this.entity.x = x;
            this.entity.y = y;
            this.entity.keyH = keyH;
        }

        public EntityBuilder setName(String name) {
            this.entity.name = name;
            return this;
        }

        public EntityBuilder setTotalSprite(int totalSprite) {
            this.entity.totalSprite = totalSprite;
            return this;
        }

        public EntityBuilder setSpeed(int speed) {
            this.entity.speed = speed;
            return this;
        }

        public EntityBuilder setSpeedChangeSprite(int speedChangeSprite) {
            this.entity.speedChangeSprite = speedChangeSprite;
            return this;
        }

        public EntityBuilder setCollisionArea(int larghezza, int altezza) {
            this.entity.collisionArea = new Rectangle(this.entity.x, this.entity.y, (larghezza/2)*this.entity.scale, (altezza/2)*this.entity.scale);
            return this;
        }


        public EntityBuilder setSpriteNumLess1(int numSpriteEachDirection) {
            this.entity.spriteNum = numSpriteEachDirection;
            return this;
        }

        public EntityBuilder setDefaultDirection(String direction) {
            this.entity.direction = direction;
            return this;
        }

        public EntityBuilder setIsInteractible(boolean isInteractible){
            this.entity.isInteractable = isInteractible;
            return this;
        }
        public EntityBuilder setInteractionAction(Interactable action) {
            this.entity.interactionAction = action;
            return this;
        }

        public EntityBuilder setImageDimension(int imageWidth, int imageHeight) {
            this.entity.imageWidth = imageWidth;
            this.entity.imageHeight = imageHeight;
            return this;
        }

        public EntityBuilder setScale(int scale) {
            this.entity.scale = scale;
            return this;
        }

        public EntityBuilder setContainedMap(TileManager tileManager) {
            this.entity.tileManager = tileManager;
            return this;
        }

        public EntityBuilder set16EntityImage(String path_up1, String path_up2, String path_up3, String path_up4,
                                              String path_down1, String path_down2, String path_down3, String path_down4,
                                              String path_left1, String path_left2, String path_left3, String path_left4,
                                              String path_right1, String path_right2, String path_right3, String path_right4) {
            try {
                this.entity.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.entity.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));
                this.entity.up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up3)));
                this.entity.up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up4)));

                this.entity.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.entity.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));
                this.entity.down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down3)));
                this.entity.down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down4)));


                this.entity.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.entity.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));
                this.entity.left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left3)));
                this.entity.left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left4)));


                this.entity.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.entity.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));
                this.entity.right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right3)));
                this.entity.right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public EntityBuilder set8EntityImage(String path_up1, String path_up2, String path_down1, String path_down2,
                                             String path_left1, String path_left2, String path_right1, String path_right2) {
            try {
                this.entity.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.entity.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));

                this.entity.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.entity.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));

                this.entity.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.entity.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));

                this.entity.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.entity.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public Entity build() {
            return this.entity;
        }
    }
}
