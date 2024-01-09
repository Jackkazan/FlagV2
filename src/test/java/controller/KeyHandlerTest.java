package controller;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import model.gameState.GameStateManager;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.KeyEvent;

public class KeyHandlerTest {

    private GameStateManager mockGSM;
    private KeyHandler keyHandler;

    @Before
    public void setUp() {
        mockGSM = new GameStateManager(); // Assuming you have a MockGameStateManager class for testing
        keyHandler = new KeyHandler(mockGSM);
    }

    @Test
    public void testToggleDebugText() {
        assertFalse(keyHandler.isShowDebugText());

        // Pressing 'T' should toggle debug text
        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_T));
        assertTrue(keyHandler.isShowDebugText());

        // Pressing 'T' again should toggle it back
        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_T));
        assertFalse(keyHandler.isShowDebugText());
    }

    @Test
    public void testPauseToggle() {
        assertFalse(keyHandler.pauseSwitch());


        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_P));
        assertTrue(keyHandler.pauseSwitch());

    }

    @Test
    public void testInteractionKey() {
        assertFalse(keyHandler.interactPressed);

        // Pressing 'E' should set interactPressed to true
        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_E));
        assertTrue(keyHandler.interactPressed);

        // Releasing 'E' should set interactPressed to false
        keyHandler.keyReleased(createKeyEvent(KeyEvent.VK_E));
        assertFalse(keyHandler.interactPressed);
    }

    // Similar tests can be written for other keys and their interactions

    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(new MockComponent(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }

    // You might need to create a MockComponent class to simulate the component in KeyEvent
    private static class MockComponent extends java.awt.Component {
    }
}
