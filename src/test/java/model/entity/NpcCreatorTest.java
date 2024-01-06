package model.entity;

import static org.junit.Assert.*;

import controller.KeyHandler;
import model.gameState.GameStateManager;
import model.tile.MapManager;
import org.junit.Before;
import org.junit.Test;
import view.GamePanel;

import java.util.List;

public class NpcCreatorTest {

    private GamePanel mockGamePanel;
    private GameStateManager mockGSM;
    private MapManager mockMapManager;
    private KeyHandler mockKeyHandler;

    @Before
    public void setUp() {
        mockGamePanel = new GamePanel(); // Assuming you have a MockGamePanel class for testing
        mockGSM = new GameStateManager(); // Assuming you have a MockGameStateManager class for testing
        //mockMapManager = new MapManager(); // Assuming you have a MockMapManager class for testing
        mockKeyHandler = new KeyHandler(); // Assuming you have a MockKeyHandler class for testing
    }

    /*
    @Test
    public void testCreateNPCs() {
        List<Npc> npcList = NPCCreator.createNPCs(mockGamePanel, mockGSM, mockMapManager, mockKeyHandler);
        assertNotNull(npcList);
        assertEquals(3, npcList.size());

        // Add more assertions based on your expected behavior
        // For example, check the properties of each NPC in the list
    }

     */
}
