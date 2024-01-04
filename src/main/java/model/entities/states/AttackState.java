package model.entities.states;

import model.entities.Entity;

import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AttackState implements EntityState {
    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }
    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}
        }
    }

    private void updateEnemy(Enemy enemy){
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

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (images != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            //perché altrimenti prima di finire disegnava lo sprite 0
            if(enemy.getAttackAnimationCompleted())
                graphics2D.drawImage(images[images.length-1], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
            else
                graphics2D.drawImage(images[enemy.getSpriteNum()], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
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