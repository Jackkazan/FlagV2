package model.dialogue;

import model.Dialogues.DialogueManager;
import model.gameState.GameStateManager;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestDialogue {
    @Before
    public void setup(){
        GameStateManager gsm = new GameStateManager();
        gsm.init();
    }
    @Test
    public void testDialogue(){
        assertNotNull(DialogueManager.getInstance());
        assertNotNull(DialogueManager.getInstance().getDialogueMap());
        assertTrue(DialogueManager.getInstance().getDialogueMap().get("Default").getDefaultDialogue().contains("Ciao"));
    }
}
