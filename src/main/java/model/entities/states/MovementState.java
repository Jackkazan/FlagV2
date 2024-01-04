package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.characters.enemies.Enemy;
import model.entities.characters.npc.Npc;
import model.entities.characters.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

import static model.gameState.GameStateManager.keyH;


public class MovementState implements EntityState {

    @Override
    public void update(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Npc" -> updateNpc((Npc) entity);
            case "Player" -> updatePlayer((Player) entity);
            case "Enemy" -> updateEnemy((Enemy) entity);
            default -> {}
        }
    }
    @Override
    public void draw(Graphics2D graphics2D, Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Npc" -> drawNpc(graphics2D, (Npc) entity);
            case "Player" -> drawPlayer(graphics2D, (Player) entity);
            case "Enemy" -> drawEnemy(graphics2D, (Enemy) entity);
            default -> {}
        }
    }

    private void updatePlayer(Player player) {
        int nextX = player.getX();
        int nextY = player.getY();

        if (keyH.rightPressed) {
            player.setDirection("right");
            nextX += player.getSpeed();
        }
        if (keyH.leftPressed) {
            player.setDirection("left");
            nextX -= player.getSpeed();
        }
        if (keyH.upPressed) {
            player.setDirection("up");
            nextY -= player.getSpeed();
        }
        if (keyH.downPressed) {
            player.setDirection("down");
            nextY += player.getSpeed();
        }

        if (!player.collidesWithObjects(nextX, nextY) && !player.collidesWithNpcs(nextX, nextY) && !player.collidesWithItems(nextX, nextY)) {
            player.setX(nextX);
            player.setY(nextY);

            // Aggiorna l'area di interazione
            player.updateInteractionArea();
            // Aggiorna la collisionArea del giocatore
            player.updateCollisionArea();
            // Aggiorna l'area di attacco del player a seconda della direzione
            player.updateAttackArea();


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
            case "up" -> new BufferedImage[]{player.getUp1(), player.getUp2(), player.getUp3(), player.getUp4()};
            case "down" -> new BufferedImage[]{player.getDown1(), player.getDown2(), player.getDown3(), player.getDown4()};
            case "left" -> new BufferedImage[]{player.getLeft1(), player.getLeft2(), player.getLeft3(), player.getLeft4()};
            case "right" -> new BufferedImage[]{player.getRight1(), player.getRight2(), player.getRight3(), player.getRight4()};
            default -> null;
        };

        if (images != null) {
            int spriteIndex = player.getSpriteNum() % images.length;

            graphics2D.drawImage(images[spriteIndex], player.getScreenX()-16, player.getScreenY()-32, (player.getImageWidth()/2) *player.getScale(), (player.getImageHeight()/2)*player.getScale(), null);
        }
    }

    private void updateNpc(Npc npc) {
    }
    private void drawNpc(Graphics2D graphics2D, Npc npc) {
    }

    private void updateEnemy(Enemy enemy) {
        enemy.getCollisionArea().setLocation(enemy.getX(), enemy.getY());
        if (enemy.getTotalSprite() == 16) {
            // alternatore di sprite
            enemy.incrementSpriteCounter();
            // più è alto, più è lento
            if (enemy.getSpriteCounter() > enemy.getSpeedChangeSprite()) {
                enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 4);
                enemy.setSpriteCounter(0);
            }
        } else {
            enemy.incrementSpriteCounter();
            // più è alto, più è lento
            if (enemy.getSpriteCounter() > enemy.getSpeedChangeSprite()) {
                enemy.setSpriteNum((enemy.getSpriteNum() + 1) % 2);
                enemy.setSpriteCounter(0);
            }
        }
        double distance = Math.hypot(enemy.getGsm().getPlayer().getX() - enemy.getX(), enemy.getGsm().getPlayer().getY() - enemy.getY());
        if (distance <= enemy.getAggroRange()) {
            enemy.moveTowardsPlayer(enemy.getGsm().getPlayer().getX(), enemy.getGsm().getPlayer().getY());
        } else {
            enemy.setState(Enemy.State.IDLE);
        }

    }

    private void drawEnemy(Graphics2D graphics2D, Enemy enemy) {
        BufferedImage[] images = switch (enemy.getDirection()) {
            case "up" -> new BufferedImage[]{enemy.getUp1(), enemy.getUp2(), enemy.getUp3(), enemy.getUp4()};
            case "down" -> new BufferedImage[]{enemy.getDown1(), enemy.getDown2(), enemy.getDown3(), enemy.getDown4()};
            case "left" -> new BufferedImage[]{enemy.getLeft1(), enemy.getLeft2(), enemy.getLeft3(), enemy.getLeft4()};
            case "right" -> new BufferedImage[]{enemy.getRight1(), enemy.getRight2(), enemy.getRight3(), enemy.getRight4()};
            default -> null;
        };

        int screenX = enemy.getX() - enemy.getGsm().getPlayer().getX() + enemy.getGsm().getPlayer().getScreenX();
        int screenY = enemy.getY() - enemy.getGsm().getPlayer().getY() + enemy.getGsm().getPlayer().getScreenY();

        if (images != null && enemy.getGsm().getMapManager().getCurrentMap() == enemy.getTileManager()) {
            graphics2D.drawImage(images[enemy.getSpriteNum()], screenX-(enemy.getIdle1().getWidth()/2), screenY-(enemy.getIdle1().getHeight()/2), (enemy.getImageWidth() / 2) * enemy.getScale(), (enemy.getImageHeight() / 2) * enemy.getScale(), null);
        }
    }

}