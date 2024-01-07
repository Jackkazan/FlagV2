package model.entities.traps;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.Prototype;
import model.entities.characters.Characters;
import model.entities.characters.enemies.Enemy;
import model.entities.items.Item;
import model.entities.states.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static view.GamePanel.tileSize;

public class Trap extends Item implements Prototype {

    public enum State {IDLE,ATTACK}
    private int damage;
    protected int spriteNum;

    protected int spriteCounter = 0;
    private boolean isAttacking;
    private boolean isAttackAnimationCompleted;
    private Rectangle attackArea;
    private EntityState currentState;
    private BufferedImage animateImage5, animateImage6, animateImage7, animateImage8,animateImage9, animateImage10, animateImage11, animateImage12;

    public Trap(){
        super();
        this.isAttacking= false;
        this.isAttackAnimationCompleted = true;
        this.currentState = new IdleState();
    }

    @Override
    public void draw(Graphics2D graphics2D){
        currentState.draw(graphics2D, this);
    }

    @Override
    public void update() {
        this.checkCollision();
        if(this.isAttacking)
            this.setState(State.ATTACK);
        else {
            this.spriteNum=0;
            this.setState(State.IDLE);
        }
        currentState.update(this);

//        System.out.println("X: "+ this.x+"\n" +
//                "Y: " + this.y+"\n" +
//                "AttackArea\n" +
//                "x: "+ this.attackArea.getX() +"\n" +
//                "y: "+ this.attackArea.getY() + "\n"+
//                "width: "+ this.attackArea.getWidth()+"\n" +
//                "height: "+ this.attackArea.getHeight());
    }

    private void checkCollision() {

        if(this.isPlayerNearby()) {
            this.isAttacking = true;
        }
    }

    public void hitPlayer(){
        if (this.attackArea.intersects(gsm.getPlayer().getCollisionArea())) {
            gsm.getPlayer().setEnemyHitDirection("null");
            gsm.getPlayer().setEnemyHitDamage(this.damage);
            if (!gsm.getPlayer().isHitted()) {
                gsm.getPlayer().setSpriteNum(0);
                gsm.getPlayer().setHitAnimationCompleted(false);
            }
            gsm.getPlayer().setHitted(true);

            System.out.println(this.name + " ha colpito il player");
        }
    }


    public boolean isPlayerNearby() {
        //Definisci la logica per verificare se il giocatore è nelle vicinanze in base alle coordinate e alla dimensione dell'oggetto
        if(this.attackArea!= null && gsm.getPlayer().getCollisionArea().intersects(this.attackArea)){
            System.out.println("Sto collidendo con "+ this.name);
            return true;
        }
        else return false;
    }

    public void setState(State entityState) {
        switch (entityState) {
            case IDLE -> currentState = new IdleState();
            case ATTACK -> currentState = new AttackState();
            default -> {}
        }
    }

    @Override
    public Prototype clone() {
        return super.clone();
    }

    public static class TrapBuilder extends Entity.EntityBuilder<Trap, Trap.TrapBuilder>{

        public TrapBuilder(int x, int y){
            super();
            this.entity.x = x *tileSize;
            this.entity.y = y *tileSize;
        }

        public TrapBuilder setStaticImage(String pathImage) {
            try{
                this.entity.staticImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            }catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public TrapBuilder setSpriteNum(int spriteNum){
            this.entity.spriteNum = spriteNum;
            return this;
        }

        public TrapBuilder setSpeedChangeAnimateSprite(int speedChangeAnimateSprite) {
            this.entity.speedChangeAnimateSprite = speedChangeAnimateSprite;
            return this;
        }

        public TrapBuilder setAttackArea(int x, int y, int width, int height){
            this.entity.attackArea = new Rectangle(x*tileSize,y*tileSize,width,height);
            return this;
        }

        public TrapBuilder set12AnimateImages(String path1, String path2, String path3, String path4,String path5, String path6, String path7, String path8,String path9, String path10, String path11, String path12) {
            try {
                this.entity.animateImage1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path1)));
                this.entity.animateImage2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path2)));
                this.entity.animateImage3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path3)));
                this.entity.animateImage4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path4)));
                this.entity.animateImage5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path5)));
                this.entity.animateImage6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path6)));
                this.entity.animateImage7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path7)));
                this.entity.animateImage8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path8)));
                this.entity.animateImage9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path9)));
                this.entity.animateImage10 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path10)));
                this.entity.animateImage11 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path11)));
                this.entity.animateImage12 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path12)));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }
        public TrapBuilder setDamage(int damage){
            this.entity.damage = damage;
            return this;
        }


        @Override
        public Trap build() {
            return this.entity;
        }

        @Override
        protected Trap createEntity() {
            return new Trap();
        }

    }


    public int getDamage() {
        return damage;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isAttackAnimationCompleted() {
        return isAttackAnimationCompleted;
    }

    public Rectangle getAttackArea() {
        return attackArea;
    }

    public EntityState getCurrentState() {
        return currentState;
    }

    public BufferedImage getAnimateImage5() {
        return animateImage5;
    }

    public BufferedImage getAnimateImage6() {
        return animateImage6;
    }

    public BufferedImage getAnimateImage7() {
        return animateImage7;
    }

    public BufferedImage getAnimateImage8() {
        return animateImage8;
    }

    public BufferedImage getAnimateImage9() {
        return animateImage9;
    }

    public BufferedImage getAnimateImage10() {
        return animateImage10;
    }

    public BufferedImage getAnimateImage11() {
        return animateImage11;
    }

    public BufferedImage getAnimateImage12() {
        return animateImage12;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public void setAttackAnimationCompleted(boolean attackAnimationCompleted) {
        isAttackAnimationCompleted = attackAnimationCompleted;
    }

    public void setAttackArea(int x, int y, int width, int height) {
        this.attackArea = new Rectangle(x*tileSize, y*tileSize, width, height);
    }

    public void setCurrentState(EntityState currentState) {
        this.currentState = currentState;
    }

    public void incrementSpriteCounter(){
        this.spriteCounter++;
    }
}
