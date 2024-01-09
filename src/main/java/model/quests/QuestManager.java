package model.quests;

import model.Dialogues.DialogueManager;
import model.entities.Entity;
import model.entities.items.Item;
import model.gameState.GameStateManager;

import java.util.*;

import static java.util.function.Predicate.not;


public class QuestManager {
    private static Map<Entity, Quest> questMap = new HashMap();
    private static List<Quest> completedQuestList = new ArrayList<>();
    private static Map<Entity, Objective> objectiveMap = new HashMap();



    public static void setQuest (Entity entity, Quest quest){
        questMap.put(entity, quest);
    }
    public static void setObjective (Entity entity, Objective objective) {objectiveMap.put(entity, objective);}
    public static boolean handleObjective(Entity entity, Objective objective){
        Quest quest = questMap.get(entity);
        if (objective.getRequiredObjectivesID() == null){
            if(quest.getProgress() == Quest.Progress.START)
                quest.setProgress(Quest.Progress.INPROGRESS);
            objective.complete();
            quest.setProgress(Quest.Progress.COMPLETED);
            return true;
        }
        else if(Arrays.stream(objective.getRequiredObjectivesID()).mapToObj(quest::getObjectiveById).allMatch(Objective::isCompleted)){
            objective.complete();
            quest.setProgress(Quest.Progress.COMPLETED);
            return true;
        }
        handleFail(quest);
        return false;
    }
    public static void handleFail(Quest quest){
        DialogueManager.showQuestMessage(quest);
    }
    public static void addCompletedQuest(Quest quest){
        completedQuestList.add(quest);
    }
    private static boolean test = false;
    public static void load(){
        for (Quest quest : completedQuestList) {
            for (Objective objective : quest.getObjectives())
            for (Iterator<Map.Entry<Entity, Objective>> iterator = objectiveMap.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<Entity, Objective> entry = iterator.next();
                if (entry.getValue() != null && entry.getValue().equals(objective)) {
                    Entity entity = entry.getKey();
                    System.out.println(entity.getName());
                    if (entity instanceof Item) {
                        Item itemEntity = (Item) entity;
                        itemEntity.loadProgress();
                    }
                    // Remove the entry to avoid concurrent modification
                    iterator.remove();
                }
            }
        }

        // Load progress for completed quests
        for (int i = 0; i< completedQuestList.size(); i++) {
            Quest quest = completedQuestList.get(0);
            for (Iterator<Map.Entry<Entity, Quest>> iterator = questMap.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<Entity, Quest> entry = iterator.next();
                if (entry.getValue() != null && entry.getValue().equals(quest)) {
                    Entity entity = entry.getKey();
                    System.out.println(entity.getName());
                    if (entity instanceof Item) {
                        Item itemEntity = (Item) entity;
                        itemEntity.loadProgress();
                    }
                    // Remove the entry to avoid concurrent modification
                    iterator.remove();
                }
            }
        }
    }

    public static List<Quest> getCompletedQuestList() {
        return completedQuestList;
    }

    public static Map<Entity, Quest> getQuestMap(){
        return questMap;
    }

    public static Map<Entity, Objective> getObjectiveMap() {
        return objectiveMap;
    }

}
