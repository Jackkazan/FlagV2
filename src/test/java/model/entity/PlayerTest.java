package model.entity;

import model.entities.characters.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testDefaultValues() {
        Player player = new Player();

        assertEquals("Player", player.getName());
        assertEquals(0, player.getCurrentLife());
        assertEquals(6, player.getMaxLife());
        assertEquals(4, player.getSpeed());
        assertEquals(5, player.getScale());
        assertEquals(0, player.getSpriteCounter());
        assertEquals(3, player.getSpriteNum());
        assertEquals("down", player.getDirection());
        assertEquals(Player.swordStateAndArmor.IronSwordNoArmor, player.getCurrentSwordStateAndArmor());
        assertEquals(false, player.isAttacking());
        assertEquals(false, player.isHitted());
        assertEquals(true, player.isAttackAnimationCompleted());
        assertNotNull(player.getAttackArea());
        assertNotNull(player.getCollisionArea());
        assertNotNull(player.getInteractionArea());
        assertEquals(2000, player.getHitCooldown());
        assertEquals(1, player.getDamage());
    }

    @Test
    public void testUpdateInteractionArea() {
        Player player = new Player();
        player.setPosition(10, 10);
        player.updateInteractionArea();
    }

    @Test
    public void testUpdateCollisionArea() {
        Player player = new Player();
        player.setPosition(20, 20);
        player.updateCollisionArea();
    }

    @Test
    public void testUpdateAttackArea() {
        Player player = new Player();
        player.setPosition(30, 30);
        player.setDirection("up");
        player.updateAttackArea();
    }

    // Add more tests based on specific functionality and behavior of the Player class.
}
