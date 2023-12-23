package model.entities.npc;

import model.entities.Interactable;
import model.entities.Entity;
import model.gameState.GameStateManager;
import model.tile.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

//Class for npc
public class Npc extends Entity {

    protected int speed;
    protected int speedChangeSprite;

    protected int spriteNum;
    protected BufferedImage
            up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;
    protected String direction;
    protected int spriteCounter = 0;
    protected int totalSprite;

    public Npc (){
        this.gsm = GameStateManager.gp.getGsm();
        this.keyH = GameStateManager.keyH;
    }

    public static class NpcBuilder {
        private final Npc npc;
        private int[] pathX;  // Array delle coordinate x del percorso
        private int[] pathY;  // Array delle coordinate y del percorso
        private int pathIndex;

        public NpcBuilder( int x, int y) {
            this.npc = new Npc();
            this.npc.x = x * tileSize;
            this.npc.y = y * tileSize;
        }

        public NpcBuilder setName(String name) {
            this.npc.name = name;
            return this;
        }

        public NpcBuilder setTotalSprite(int totalSprite) {
            this.npc.totalSprite = totalSprite;
            return this;
        }

        public NpcBuilder setSpeed(int speed) {
            this.npc.speed = speed;
            return this;
        }

        public NpcBuilder setSpeedChangeSprite(int speedChangeSprite) {
            this.npc.speedChangeSprite = speedChangeSprite;
            return this;
        }

        public NpcBuilder setCollisionArea(int larghezza, int altezza) {
            this.npc.collisionArea = new Rectangle(this.npc.x, this.npc.y, (larghezza/2)*this.npc.scale, (altezza/2)*this.npc.scale);
            return this;
        }


        public NpcBuilder setSpriteNumLess1(int numSpriteEachDirection) {
            this.npc.spriteNum = numSpriteEachDirection;
            return this;
        }

        public NpcBuilder setDefaultDirection(String direction) {
            this.npc.direction = direction;
            return this;
        }

        public NpcBuilder setIsInteractible(boolean isInteractible){
            this.npc.isInteractable = isInteractible;
            return this;
        }
        public NpcBuilder setInteractionAction(Interactable action) {
            this.npc.interactionAction = action;
            return this;
        }

        public NpcBuilder setImageDimension(int imageWidth, int imageHeight) {
            this.npc.imageWidth = imageWidth;
            this.npc.imageHeight = imageHeight;
            return this;
        }

        public NpcBuilder setScale(int scale) {
            this.npc.scale = scale;
            return this;
        }

        public NpcBuilder setContainedMap(TileManager tileManager) {
            this.npc.tileManager = tileManager;
            return this;
        }

        public NpcBuilder set16EntityImage(String path_up1, String path_up2, String path_up3, String path_up4,
                                              String path_down1, String path_down2, String path_down3, String path_down4,
                                              String path_left1, String path_left2, String path_left3, String path_left4,
                                              String path_right1, String path_right2, String path_right3, String path_right4) {
            try {
                this.npc.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.npc.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));
                this.npc.up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up3)));
                this.npc.up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up4)));

                this.npc.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.npc.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));
                this.npc.down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down3)));
                this.npc.down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down4)));


                this.npc.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.npc.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));
                this.npc.left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left3)));
                this.npc.left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left4)));


                this.npc.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.npc.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));
                this.npc.right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right3)));
                this.npc.right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right4)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public NpcBuilder set8EntityImage(String path_up1, String path_up2, String path_down1, String path_down2,
                                             String path_left1, String path_left2, String path_right1, String path_right2) {
            try {
                this.npc.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up1)));
                this.npc.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_up2)));

                this.npc.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down1)));
                this.npc.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_down2)));

                this.npc.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left1)));
                this.npc.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_left2)));

                this.npc.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right1)));
                this.npc.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path_right2)));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public Npc build() {
            return this.npc;
        }
    }

    @Override
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


    @Override
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

    public void setPath(){
        // Definisci il percorso predefinito
        int[] pathX = new int[]{this.x, this.x + tileSize, this.x, this.x - tileSize};  // Esempio: movimento orizzontale a destra, poi su, poi a sinistra
        int[] pathY = new int[]{this.y, this.y + tileSize, this.y, this.y - tileSize};       // Modifica il percorso secondo le tue esigenze
        int pathIndex = 0;  // Inizia dal primo punto del percorso

        // Muovi l'entità lungo il percorso predefinito
        this.x = pathX[pathIndex];
        this.y = pathY[pathIndex];
        pathIndex++;
        if(pathIndex == 3) pathIndex = 0;
        // Se hai raggiunto la fine del percorso, ricomincia da capo
    }

    public void interact() {
        // Verifica se il giocatore è nelle vicinanze e ha premuto il tasto "E"
        if (this.tileManager == gsm.getMapManager().getCurrentMap() && this.isInteractable   && isPlayerNearby()) {
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

    public int getSpeed() {
        return this.speed;
    }

    public String getDirection() {
        return direction;
    }

    public int getSpeedChangeSprite() {
        return speedChangeSprite;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public BufferedImage getUp3() {
        return up3;
    }

    public BufferedImage getUp4() {
        return up4;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public BufferedImage getDown3() {
        return down3;
    }

    public BufferedImage getDown4() {
        return down4;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public BufferedImage getLeft3() {
        return left3;
    }

    public BufferedImage getLeft4() {
        return left4;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public BufferedImage getRight3() {
        return right3;
    }

    public BufferedImage getRight4() {
        return right4;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public int getTotalSprite() {
        return totalSprite;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpeedChangeSprite(int speedChangeSprite) {
        this.speedChangeSprite = speedChangeSprite;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public void setTotalSprite(int totalSprite) {
        this.totalSprite = totalSprite;
    }
}
