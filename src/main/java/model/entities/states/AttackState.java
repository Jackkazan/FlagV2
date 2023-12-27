package model.entities.states;

import model.entities.Entity;
import model.entities.EntityState;
import model.entities.enemies.Enemy;
import model.entities.npc.Npc;
import model.entities.player.Player;

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
    }
    private void drawEnemy(Graphics2D graphics2D, Enemy enemy){
    }

    private void updatePlayer(Player player){
        /*
        player.setAttacking(true);
        player.setAttackAnimationCompleted(false);

        switch (player.getDirection()) {
            case "left" -> player.setDirection("left&attack");
            case "right" -> player.setDirection("right&attack");
            case "down" -> player.setDirection("down&attack");
            case "up" -> player.setDirection("up&attack");
            default -> {}
        }

        // Imposta un timer per la durata dell'animazione dell'attacco
        Timer timer = new Timer(385, e -> {
            player.setSpriteNum(0);
            player.setAttacking(false);
            player.setAttackAnimationCompleted(true);
            // Una volta terminato lo stato di attacco, passa a Idle
            player.setState(Player.State.IDLE);


            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
*/
    }

    private void drawPlayer(Graphics2D graphics2D, Player player){
        /*
        BufferedImage[] images = switch (player.getDirection()) {
            case "up&attack" -> new BufferedImage[]{player.getAttackUp1(), player.getAttackUp2(), player.getAttackUp3(), player.getAttackUp4()};
            case "down&attack" -> new BufferedImage[]{player.getAttackDown1(), player.getAttackDown2(), player.getAttackDown3(), player.getAttackDown4()};
            case "left&attack" -> new BufferedImage[]{player.getAttackLeft1(), player.getAttackLeft2(), player.getAttackLeft3(), player.getAttackLeft4()};
            case "right&attack" -> new BufferedImage[]{player.getAttackRight1(), player.getAttackRight2(), player.getAttackRight3(), player.getAttackRight4()};
            default -> null;
        };
        int offsetX, offsetY, imageWidth, imageHeight;
        switch (player.getDirection()) {
            case "down&attack" -> {
                offsetX = -16;
                offsetY = -32;
                imageWidth = 32;
                imageHeight = 48;
            }
            case "left&attack" -> {
                offsetX = -58;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "right&attack" -> {
                offsetX = -14;
                offsetY = -32;
                imageWidth = 48;
                imageHeight = 32;
            }
            case "up&attack" -> {
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
            if (player.isAttacking())
                graphics2D.drawImage(images[spriteIndex], player.getScreenX() + offsetX - tileSize / 2, player.getScreenY() + offsetY, (imageWidth / 2) * player.getScale(), (imageHeight / 2) * player.getScale(), null);
            else
                graphics2D.drawImage(images[0], player.getScreenX() + offsetX - tileSize / 2, player.getScreenY() + offsetY, (imageWidth / 2) * player.getScale(), (imageHeight / 2) * player.getScale(), null);

        }
    */}

}
