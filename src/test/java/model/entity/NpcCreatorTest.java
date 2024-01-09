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
        // Mock GameStateManager and MapManager
        GameStateManager mockGSM = new GameStateManager();
        MapManager mockMapManager = new MapManager();

        // Call the createNPCs method to get the list of NPCs
        List<Npc> npcList = NpcCreator.createNPCs(mockGSM, mockMapManager);

        // Assert that the list is not null and contains the expected number of NPCs
        assertNotNull(npcList);
        assertEquals(6, npcList.size());

        // Add more assertions based on your specific requirements for the created NPCs
        // For example, you can check if certain NPCs are present in the list with specific properties.
    }
}
