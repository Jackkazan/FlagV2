package model.entities.states;

import model.entities.Entity;

import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.player.Player;
import model.entities.traps.Trap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static view.GamePanel.tileSize;

public class AttackState implements EntityState {
    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            case "Trap" -> updateTrap((Trap) entity);
            default -> {}
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            case "Trap" -> drawTrap(graphics2D, (Trap) entity);
            default -> {}
        }
    }

    private void updateTrap(Trap trap) {

        if(trap.getSpriteNum()>=6 && trap.getSpriteNum()<=8) {
            trap.hitPlayer();
            //logica dell'hit
        }

        if(trap.getSpriteNum() == 11)
            trap.setAttackAnimationCompleted(true);

        //alternatore di sprite
        trap.incrementSpriteCounter();
        //velocità di cambio sprite 5-10
        if (trap.getSpriteCounter() > 7) {
            trap.setSpriteNum((trap.getSpriteNum() + 1) % 12);
            trap.setSpriteCounter(0);
        }

        if(trap.getSpriteNum()==0 && trap.isAttackAnimationCompleted()) {
            trap.setAttacking(false);
        }

        //System.out.println("Sprite NUM: "+ trap.getSpriteNum());

    }

    private void drawTrap(Graphics2D graphics2D, Trap trap) {
        BufferedImage[] images = { trap.getAnimateImage1(),trap.getAnimateImage2(), trap.getAnimateImage3(), trap.getAnimateImage4(),
                                trap.getAnimateImage5(), trap.getAnimateImage6(), trap.getAnimateImage7(), trap.getAnimateImage8(),
                                trap.getAnimateImage9(), trap.getAnimateImage10(),trap.getAnimateImage11(), trap.getAnimateImage12()};

        graphics2D.drawImage(images[trap.getSpriteNum()], trap.getX(), trap.getY(), ((tileSize*trap.getImageWidth())/16)*trap.getScale(), ((tileSize*trap.getImageHeight())/16)*trap.getScale(), null);

    }

    private void updateEnemy(Enemy enemy){
        enemy.getCollisionArea().setLocation(enemy.getX(), enemy.getY());
        enemy.updateAttackArea();
        if(enemy.getSpriteNum()==3) {
            enemy.hitPlayer();
            enemy.setAttackAnimationCompleted(true);
            //logica dell'hit

        }
        if(enemy.getSpriteNum()==0 && enemy.getAttackAnimationCompleted()) {
            enemy.setAttacking(false);
        }
        //alternatore di sprite
        enemy.incrementSpriteCounter();
        //velocità di cambio sprite 5-10
        if (enemy.getSpriteCounter() > 7) {
            enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 4);
            enemy.setSpriteCounter(0);
        }
        //System.out.println("Sprite num: "+ enemy.getSpriteNum());

    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy){
        BufferedImage[] images = switch (enemy.getDirection()) {
            case "up" -> new BufferedImage[]{enemy.getAttackUp1(), enemy.getAttackUp2(), enemy.getAttackUp3(), enemy.getAttackUp4()};
            case "down" -> new BufferedImage[]{enemy.getAttackDown1(), enemy.getAttackDown2(), enemy.getAttackDown3(), enemy.getAttackDown4()};
            case "left" -> new BufferedImage[]{enemy.getAttackLeft1(), enemy.getAttackLeft2(), enemy.getAttackLeft3(), enemy.getAttackLeft4()};
            case "right" -> new BufferedImage[]{enemy.getAttackRight1(), enemy.getAttackRight2(), enemy.getAttackRight3(), enemy.getAttackRight4()};
            default -> null;
        };


        if (images != null) {
            switch (enemy.getScale()){
                case 4:
                    if(enemy.getAttackAnimationCompleted())
                        graphics2D.drawImage(images[images.length-1], enemy.getX()-(enemy.getIdle1().getWidth()/2), enemy.getY()-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    else
                        graphics2D.drawImage(images[enemy.getSpriteNum()], enemy.getX()-(enemy.getIdle1().getWidth()/2), enemy.getY()-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    break;
                case 8:
                    if(enemy.getAttackAnimationCompleted())
                        graphics2D.drawImage(images[images.length-1], enemy.getX()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -8, enemy.getY()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -tileSize, (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    else
                        graphics2D.drawImage(images[enemy.getSpriteNum()], enemy.getX()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -8, enemy.getY()-(enemy.getImageHeight() / 2) * enemy.getScale()/3 -tileSize, (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
                    break;
            }
            //perché altrimenti prima di finire disegnava lo sprite 0

            }
    }

    private void updatePlayer(Player player){
        if(player.isHitted()) {
            player.setAttackAnimationCompleted(true);
            player.setAttacking(false);
        }else {

            if (player.getSpriteNum() == 3) {
                player.setAttackAnimationCompleted(true);
            }
            if (player.getSpriteNum() == 2)
                player.hitAnEnemy();

            if (player.getSpriteNum() == 0 && player.getAttackAnimationCompleted()) {
                player.setAttacking(false);
            }
            //alternatore di sprite
            player.incrementSpriteCounter();
            //velocità di cambio sprite 5-10
            if (player.getSpriteCounter() > 7) {
                player.setSpriteNum((player.getSpriteNum() + 1) % 4);
                player.setSpriteCounter(0);
            }
        }

    }

    private void drawPlayer(Graphics2D graphics2D, Player player){
        BufferedImage[] images = switch (player.getDirection()) {
            case "up" -> new BufferedImage[]{player.getAttackUp1(), player.getAttackUp2(), player.getAttackUp3(), player.getAttackUp4()};
            case "down" -> new BufferedImage[]{player.getAttackDown1(), player.getAttackDown2(), player.getAttackDown3(), player.getAttackDown4()};
            case "left" -> new BufferedImage[]{player.getAttackLeft1(), player.getAttackLeft2(), player.getAttackLeft3(), player.getAttackLeft4()};
            case "right" -> new BufferedImage[]{player.getAttackRight1(), player.getAttackRight2(), player.getAttackRight3(), player.getAttackRight4()};
            default -> null;
        };
        int offsetX, offsetY, imageWidth, imageHeight;
        switch (player.getDirection()) {
            case "down" -> {
                offsetX = -16;
                offsetY = -32;
                imageWidth = 32;
                imageHeight = 48;
            }
            case "left" -> {
                offsetX = -58;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "right" -> {
                offsetX = -14;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "up" -> {
                offsetX = -16;
                offsetY = -72;
                imageWidth = 32;
                imageHeight = 48;
            }
            default -> {
                offsetX = -16;
                offsetY = -32;
                imageWidth = 32;
                imageHeight = 32;
            }
        }

        if (images != null) {
            int spriteIndex = player.getSpriteNum() % images.length;
            if (player.getAttackAnimationCompleted())
                graphics2D.drawImage(images[images.length-1], player.getScreenX() + offsetX, player.getScreenY() + offsetY, (imageWidth / 2) * player.getScale(), (imageHeight / 2) * player.getScale(), null);
            else
                graphics2D.drawImage(images[spriteIndex], player.getScreenX() + offsetX, player.getScreenY() + offsetY, (imageWidth / 2) * player.getScale(), (imageHeight / 2) * player.getScale(), null);
        }


    }

}