package model.Dialogues;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import model.entities.Entity;
import model.entities.characters.npc.Npc;
import model.gameState.GameStateManager;
public class DialogueManager {
    private static ArrayList<String> test;
    private GameStateManager gsm;
    static private int i;
    private int pigrizia = 1;

    public DialogueManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.test = new ArrayList<>();
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);
        test.add("dialogo" + pigrizia++);


        i = 0;
    }

    public void startTutorial(int index){
        
    }
    public void startDialogue(Entity entity){
        if (i < test.size())
            gsm.setState(GameStateManager.State.DIALOGUE);
    }
    public static String getDialogue (){
        System.out.println("sono chiamato" + i);
        if(i < test.size())
            return test.get(i++);
        else{
            System.out.println("ELLEH");
            return null;}

    }

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