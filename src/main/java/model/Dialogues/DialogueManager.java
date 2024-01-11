package model.Dialogues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.Entity;
import model.gameState.GameStateManager;
import model.quests.Objective;
import model.quests.Quest;
import model.quests.QuestManager;

public class DialogueManager {
    private  List<String> dialogueString;
    private Quest currentQuest;
    private boolean shown = false;
    private Map<String, Dialogue> dialogueMap;
    private static DialogueManager instance;
    private GameStateManager gsm;
    private int index;
    public DialogueManager(){
        gsm = GameStateManager.getInstance();
        dialogueMap = new HashMap<>();
        dialogueString = new ArrayList<>();
    }
    public static DialogueManager getInstance(){
        if (instance == null)
            instance = new DialogueManager();
        return instance;
    }
    public void setDialogueMap(String speaker, Dialogue dialogue) {
        dialogueMap.put(speaker, dialogue);
    }
    public void showQuestMessage(List<String> questMessage){
        questMessage.forEach(dialogueString::add);
        if (!gsm.isInDialogue())
            startDialogue();
    }
    public void showObjectiveMessage(Objective objective) {
        if (objective.getObjectiveMessage()!=null){
            dialogueString.addAll(objective.getObjectiveMessage());
            if(dialogueString.size() > 0)
                startDialogue();
        }

    }
     public void startDialogue(){
        index = 0;
        gsm.setState(GameStateManager.State.DIALOGUE);
    }
    public void speak(Entity entity) {
        Dialogue dialogue = dialogueMap.getOrDefault(entity.getName(), dialogueMap.get("Default"));
        if(!dialogue.getSpeaker().equals("Default")) {
            currentQuest = QuestManager.getInstance().getEntityQuest(entity);
            if (currentQuest != null) {
                switch (currentQuest.getProgress()) {
                    case INACTIVE -> dialogueString.addAll(dialogue.getQuestInactive());
                    case INPROGRESS -> dialogueString.addAll(dialogue.getQuestInProgress());
                    case TOCOMPLETE -> dialogueString.addAll(dialogue.getQuestToComplete());
                    case COMPLETED -> dialogueString.addAll(dialogue.getQuestCompleted());
                }
                ;
            } else {
                dialogueString.addAll(dialogue.getDefaultDialogue());
            }
        }
        if (dialogueString.size()>0) {
            startDialogue();
        } else {
            System.out.println("Dialogo non presente");;
        }
    }
    public void exitDialogue(){
        gsm.exitDialogue();
        dialogueString.clear();
       // QuestManager.checkReward(currentQuest);
        currentQuest = null;

    }
    public String getDialogue (){
        System.out.println("sono chiamato" + index);
        if(dialogueString.size()>0 && index < dialogueString.size())
            return dialogueString.get(index++);
        else
            return null;
    }
    public Map<String, Dialogue> getDialogueMap(){
        return dialogueMap;
    }
}