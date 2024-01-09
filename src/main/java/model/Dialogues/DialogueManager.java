package model.Dialogues;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import model.entities.Entity;

import model.gameState.DialogueState;
import model.gameState.GameStateManager;
import model.quests.Quest;
import model.quests.QuestManager;

public class DialogueManager {
    private static ArrayList<String> test;
    private static List<String> dialogueString;
    private static boolean shown = false;
    private static List <Dialogue> dialogueList;

    private static GameStateManager gsm = GameStateManager.getInstance();
    static private int index;
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


        index = 0;
    }

    public static void showTutorial(){
        for (Dialogue dialogue : dialogueList) {
            if (dialogue.getSpeaker().equals("Tutorial"))
                dialogueString = dialogue.getDefaultDialogue();
        }
        startDialogue();
        }

    public static void setDialogueList(List<Dialogue> dialogues) {
        dialogueList = dialogues;
    }
    public static void showQuestMessage(Quest quest){
        for(Dialogue questDialogue: dialogueList){
            if (quest.getTitle().equals(questDialogue.getSpeaker()))
                dialogueString = questDialogue.getFailDialogue();}
        startDialogue();
    }
     public static void startDialogue(){
        index = 0;
        gsm.setState(GameStateManager.State.DIALOGUE);
    }
    public static void speak(Entity entity){
        Dialogue dialogue = null;
        for(Dialogue entityDialogue : dialogueList){
            System.out.println(entity.getName() + " " + entityDialogue.getSpeaker());
            if (entity.getName().equals(entityDialogue.getSpeaker()))
                dialogue = entityDialogue;
        }
        if (dialogue != null) {
            Quest quest = QuestManager.getQuestMap().get(entity);
            if (quest != null)
                dialogueString =
                        switch (quest.getProgress()) {
                            case START -> dialogue.getQuestStart();
                            case INPROGRESS -> dialogue.getQuestInProgress();
                            case COMPLETED -> dialogue.getQuestCompleted();
                        };
            else
                dialogueString = dialogue.getDefaultDialogue();
            startDialogue();
        }
        else
            throw new RuntimeException("DIALOGUEERROR");


    }
    public static String getDialogue (){
        System.out.println("sono chiamato" + index);
        if(index < dialogueString.size())
            return dialogueString.get(index++);
        else{
            System.out.println("ELLEH");
            return null;}

    }


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