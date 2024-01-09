package model.data;

import com.google.gson.Gson;
import model.Dialogues.DialogueManager;
import model.entities.Entity;
import model.gameState.GameStateManager;
import model.quests.Objective;
import model.quests.Quest;
import model.quests.QuestManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataInitializer {
    public static void initializeData(){
        initializeDialogues();
        initializeQuest();
    }
    private static void initializeDialogues() {/*
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("src/main/resources/dialogues/dialogues.json");) {
            DialogueData dialogueData = gson.fromJson(reader, DialogueData.class);
            dialogueData.getDialogues().forEach(dialogue-> System.out.println(dialogue.getSpeaker()));
        } catch (Exception e){
            e.printStackTrace();
        }*/
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("src/main/resources/dialogues/dialogues.json")) {
            DialogueData dialogueData = gson.fromJson(reader, DialogueData.class);

            if (dialogueData != null && dialogueData.getDialogues() != null) {
                dialogueData.getDialogues().forEach(dialogue -> System.out.println("Speaker: " + dialogue.getSpeaker()));
                DialogueManager.setDialogueList(dialogueData.getDialogues());
            } else {
                System.out.println("Error: No dialogues found in JSON file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading JSON file.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deserializing JSON.");
        }

    }
    private static void initializeQuest() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("src/main/resources/quests/quests.json")) {
            QuestData questData = gson.fromJson(reader, QuestData.class);
            if (questData != null && questData.getQuests() != null) {
                questData.getQuests().forEach(quest -> {
                    quest.getAssociatedEntities().stream()
                            .map(entityName -> findEntityByName(entityName))
                            .filter(Objects::nonNull)
                            .forEach(entity -> QuestManager.setQuest(entity, quest));

                        quest.getObjectives().forEach(objective -> {
                        objective.getObjectiveAssociatedEntities().stream()
                                .map(entityName -> findEntityByName(entityName))
                                .filter(Objects::nonNull)
                                .forEach(entity -> QuestManager.setObjective(entity, objective));
                    });
                });
                for (Map.Entry<Entity, Quest> entry : QuestManager.getQuestMap().entrySet()) {
                    System.out.println("Entity: " + entry.getKey().getName() + ", Quest: " + entry.getValue().getTitle() + ", ID " + entry.getValue().getID() );
                }
                for (Map.Entry<Entity, Objective> entry : QuestManager.getObjectiveMap().entrySet()) {
                    System.out.println("Entity: " + entry.getKey().getName() + ", Objective: " + entry.getValue().getTitle() + ", requiredID " + Arrays.toString(entry.getValue().getRequiredObjectivesID()));
                }
            } else {
                throw new RuntimeException("Quest non trovate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    private static Entity findEntityByName(String entityName) {
        for (Entity entity : GameStateManager.getInstance().getEntityList())
        {
            if(entityName.equals(entity.getName()))
                return entity;
        }

        return null;
    }
}
