package model.gameState;

import model.quests.QuestManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateManagerTest {
    @Test
    public void testInit() {
        GameStateManager gsm = new GameStateManager();
        gsm.init();
        assertNotNull(gsm.getPlayer());
        assertNotNull(gsm.getMapManager());
        assertNotNull(gsm.getPlayState());
        assertNotNull(gsm.getKeyH());
        assertNotNull(gsm.getEntityList());
        assertNotNull(gsm.getKeyItemsList());
        assertNotNull(gsm.getNpcList());
        assertNotNull(gsm.getTrapList());
        assertNotNull(gsm.getCurrentEntityList());
    }

}
