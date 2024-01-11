package model.data;

import com.google.gson.Gson;
import model.Dialogues.DialogueManager;
import model.entities.Entity;
import model.gameState.GameStateManager;
import model.quests.Quest;
import model.quests.QuestManager;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class DataInitializer {

    public static void initializeData(){
        initializeQuest();
        initializeDialogues();

    }
    private static void initializeDialogues() {


        Gson gson = new Gson();

        try (FileReader reader = new FileReader("src/main/resources/dialogues/dialogues.json")) {
            DialogueData dialogueData = gson.fromJson(reader, DialogueData.class);

            if (dialogueData != null && dialogueData.getDialogues() != null) {
                dialogueData.getDialogues().forEach(dialogue -> DialogueManager.getInstance().setDialogueMap(dialogue.getSpeaker(), dialogue));
                ;
            } else {
                System.out.println("Error: Dialoghi non trovati.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore lettura JSON.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deserializzazione JSON.");
        }
    }
    private static void initializeQuest() {

        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/resources/quests/quests.json")) {
        QuestData questData = gson. fromJson(reader, QuestData.class);
        QuestManager questManager = QuestManager.getInstance();
        if (questData != null && questData.getQuests() != null) {
            System.out.println("QuestData: " + questData);
            //QuestManager questManager = QuestManager.getInstance();
            questData.getQuests().forEach(quest -> {
                quest.getAssociatedEntitiesName().stream().filter(Objects::nonNull)
                        .forEach(entityName -> questManager.setQuest(entityName, quest));
                questManager.setQuestIDMap(quest.getID(), quest);
                if(quest.getObjectives()!= null)
                    quest.getObjectives().forEach(objective -> {
                        objective.getObjectiveAssociatedEntitiesName().stream()
                                .filter(Objects::nonNull)
                                .forEach(entity -> questManager.setObjective(entity, objective));
                    });
            });
        } else {
            throw new RuntimeException("Quest non trovate");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static Entity findEntityByName(String entityName) {
        for (Entity entity : GameStateManager.getInstance().getEntityList())
        {
            if(entityName.equals(entity.getName()))
                return entity;
        }

        return null;
    }
}
