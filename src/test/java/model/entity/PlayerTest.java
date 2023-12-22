package model.entity;

import controller.KeyHandler;
import model.entity.player.Player;
import model.gameState.GameStateManager;
import view.GamePanel;

public class PlayerTest {

    private GamePanel mockGamePanel;
    private GameStateManager mockGSM;
    private KeyHandler mockKeyHandler;
    private Player player;

    /*
    @Before
    public void setUp() {
        mockGamePanel = new GamePanel(); // Assuming you have a MockGamePanel class for testing
        mockGSM = new GameStateManager(); // Assuming you have a MockGameStateManager class for testing
        mockKeyHandler = new KeyHandler(mockGSM);
        player = new Player(mockGamePanel, mockGSM, mockKeyHandler);
    }

    @Test
    public void testDefaultValues() {
        assertEquals(tileSize * 3, player.getX());
        assertEquals(tileSize * 4, player.getY());
        assertEquals(6, player.getMaxLife());
        assertEquals(4, player.getSpeed());
        assertEquals("down", player.getDirection());
        assertEquals(Player.swordStateAndArmor.IronSwordNoArmor, player.currentSwordStateAndArmor);
    }

    @Test
    public void testSetCurrentSwordStateAndArmor() {
        player.setCurrentSwordStateAndArmor(Player.swordStateAndArmor.GoldSwordAndArmor);
        assertEquals(Player.swordStateAndArmor.GoldSwordAndArmor, player.currentSwordStateAndArmor);
    }

     */
}
