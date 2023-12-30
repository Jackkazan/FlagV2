package model.entity;

import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.entities.Entity;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class DialogueManager {

    private JsonNode npcDialogues;

    public DialogueManager(Entity npc) {
        /*try {
            // Read the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            npcDialogues = objectMapper.readTree(DialogueManager.class.getResourceAsStream());
            startDialogues(npc.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startDialogues(String npcName) {
        JsonNode npcNode = npcDialogues.get(npcName);

        if (npcNode == null || !npcNode.has("dialogues")) {
            new DialogueException();
            return;
        }

        System.out.println("Starting dialogues with " + npcName);

        Iterator<JsonNode> dialogueIterator = npcNode.get("dialogues").elements();
        while (dialogueIterator.hasNext()) {
            String dialogue = dialogueIterator.next().asText();
            displayDialogue(npcName, dialogue);
        }

        System.out.println("End of dialogues with " + npcName);
    }

    private void displayDialogue(String npcName, String dialogue) {
        System.out.println(npcName + ": " + dialogue);
    }

    private class DialogueException extends IOException{
        public DialogueException() {
            System.out.println("Errore: dialogo non trovato");
        }*/
    }
}