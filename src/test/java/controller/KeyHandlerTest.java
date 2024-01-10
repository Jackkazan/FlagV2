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
        mockGSM = new GameStateManager();
        keyHandler = KeyHandler.getInstance();
    }

    @Test
    public void testToggleDebugText() {
        assertFalse(keyHandler.isShowDebugText());

        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_T));
        assertTrue(keyHandler.isShowDebugText());

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

        keyHandler.keyPressed(createKeyEvent(KeyEvent.VK_E));
        assertTrue(keyHandler.interactPressed);

        keyHandler.keyReleased(createKeyEvent(KeyEvent.VK_E));
        assertFalse(keyHandler.interactPressed);
    }


    private KeyEvent createKeyEvent(int keyCode) {
        return new KeyEvent(new MockComponent(), KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED);
    }


    private static class MockComponent extends java.awt.Component {
    }
}
