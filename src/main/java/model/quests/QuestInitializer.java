package model.quests;
import com.google.gson.Gson;
import model.entities.Entity;
import model.entities.items.Item;
import model.entities.items.ItemCreator;
import model.gameState.GameStateManager;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuestInitializer {

    public static List<Quest> createQuestList() {
        /*ArrayList<Quest> questList=new ArrayList<>();
        Quest appenaSveglio = new Quest("AppenaSveglioTiALziDalLetto", true);
        Quest chiaveRaccoltaCasettaIniziale = new Quest("ChiaveRaccoltaCI", false);
        Quest portaSbloccataCasettaIniziale = new Quest("PortaSbloccataCI", false);
        Quest parlaConLaVecchietta = new Quest("HaiParlatoConVecchietta", false);
        Quest parlaColContadino1PerZucche = new Quest("HaiParlaColContadino1Zucche", false);
        Quest parlaColContadino2PerSpaventapasseri = new Quest("HaiParlaColContadino2PerSpaventapasseri", false);
        Quest interagitoConGliSpaventapasseriOrdineCorretto = new Quest("HaiInteragitoConGliSpaventapasseriNellOrdineCorretto", false);
        Quest estirpatoTutteLeZuccheConIVermi = new Quest("HaiEstirpatoLeZuccheConIVermi", false);
        Quest esciDallaCasetta = new Quest("Esci dalla Casetta", false);

        //0
        questList.add(appenaSveglio);
        //1
        questList.add(chiaveRaccoltaCasettaIniziale);
        //2
        questList.add(portaSbloccataCasettaIniziale);
        //3
        questList.add(parlaConLaVecchietta);
        //4
        questList.add(parlaColContadino1PerZucche);
        //5
        questList.add(parlaColContadino2PerSpaventapasseri);
        //6
        questList.add(interagitoConGliSpaventapasseriOrdineCorretto);
        //7
        questList.add(estirpatoTutteLeZuccheConIVermi);

        return questList;*/

        Gson gson = new Gson();

        try (FileReader reader = new FileReader("src/main/resources/quests/quests.json")) {
            QuestData questData = gson.fromJson(reader, QuestData.class);
            questData.getQuests().forEach(quest -> System.out.println(quest.getAssociatedEntities()));
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
                    System.out.println("Entity: " + entry.getKey().getName() + ", Quest: " + entry.getValue().getTitle());
                }
                for (Map.Entry<Entity, Objective> entry : QuestManager.getObjectiveMap().entrySet()) {
                    System.out.println("Entity: " + entry.getKey().getName() + ", Objective: " + entry.getValue().getTitle() + ", final " + entry.getValue().isFinalObjective());
                }
                return questData.getQuests();
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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


