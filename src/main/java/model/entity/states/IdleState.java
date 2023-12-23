package model.entity.states;

import model.entity.enemies.Enemy;
import model.entity.enemies.EnemyState;
import model.entity.npc.Npc;
import model.entity.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IdleState implements EnemyState {


    @Override
    public void update(Enemy enemy) {
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

    }

    @Override
    public void draw(Graphics2D graphics2D, Enemy enemy) {

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
            graphics2D.drawImage(images[enemy.getSpriteNum()], screenX, screenY, (enemy.getImageWidth()/2)* enemy.getScale() , (enemy.getImageHeight()/2)*enemy.getScale(),null);
        }

    }
}
