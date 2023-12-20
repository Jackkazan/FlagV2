package model.entity;

import model.gameState.GameStateManager;
import model.tile.MapManager;
import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

public class Enemy extends Entity{

    public Enemy(){
        super("Enemy");
    }
    public static List<Entity> createEnemy(GameStateManager gsm, MapManager mapManager) {
        int x_slime = 27*tileSize;
        int y_slime = 46*tileSize;

        List<Entity> enemyList = new ArrayList<>();

        String img1 = "/testEnemy/monster/greenslime_down_1.png";
        String img2 = "/testEnemy/monster/greenslime_down_2.png";
        String img3 = "/testEnemy/monster/greenslime_down_1.png";
        String img4 = "/testEnemy/monster/greenslime_down_2.png";
        String img5 = "/testEnemy/monster/greenslime_down_1.png";
        String img6 = "/testEnemy/monster/greenslime_down_2.png";
        String img7 = "/testEnemy/monster/greenslime_down_1.png";
        String img8 = "/testEnemy/monster/greenslime_down_2.png";


        Entity slime1 = new Entity.EntityBuilder(x_slime, y_slime,"Enemy")
                .setName("Slime1")
                .setMaxLife(6)
                .setCurrentLife(6)
                .setDamage(3)
                .setSpeedChangeSprite(10)
                .setSpriteNumLess1(1)
                .setCollisionArea(16,16)
                .setImageDimension(16,16)
                .setDefaultDirection("left")
                .setContainedMap(mapManager.getTileManagerZonaIniziale())
                .set8EntityImage(img1, img2, img3, img4, img5, img6, img7, img8)
                .build();

        enemyList.add(slime1);
        return enemyList;
    }
}
