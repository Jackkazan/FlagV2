package model.entity.states;

import model.entity.enemies.Enemy;
import model.entity.enemies.EnemyState;
import model.entity.player.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

import static view.GamePanel.tileSize;

public class MovementState implements EnemyState {
    private Enemy enemy;
    private Player player;
    private final int AGGRO_RANGE = 20 * tileSize; //raggio in pixel

    public MovementState(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void update(Enemy enemy) {
        // Aggiorna lo stato di movimento
        if (player != null) {
            int playerX = player.getX();
            int playerY = player.getY();
            // Calcola la distanza tra l'Enemy e il giocatore
            double distance = Math.hypot(playerX - enemy.getX(), playerY - enemy.getY());

            if (distance <= AGGRO_RANGE) {
                // Il giocatore è nel raggio di inseguimento, muovi l'Enemy verso il giocatore
                enemy.moveTowardsPlayer(playerX, playerY);
            } else {
                // Il giocatore è fuori dal raggio di inseguimento, potresti gestire
                enemy.setState (new IdleState());
            }
        }
    }

    public void draw(Graphics2D graphics2D, Enemy enemy){
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