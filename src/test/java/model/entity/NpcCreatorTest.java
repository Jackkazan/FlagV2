package model.entity;

import model.entities.characters.npc.Npc;
import model.entities.characters.npc.NpcCreator;
import model.gameState.GameStateManager;
import model.tile.MapManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NpcCreatorTest {

    @Test
    public void testCreateNPCs() {
        GameStateManager mockGSM = new GameStateManager();
        MapManager mockMapManager = new MapManager();
        List<Npc> npcList = NpcCreator.createNPCs(mockGSM, mockMapManager);
        assertNotNull(npcList);
        assertEquals(6, npcList.size());
    }
}
