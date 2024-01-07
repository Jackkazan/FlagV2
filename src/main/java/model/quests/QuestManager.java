package model.quests;

import model.entities.Entity;

import java.util.HashMap;
import java.util.Map;


public class QuestManager {
    private static Map<Entity, Quest> questMap = new HashMap();
    private static Map<Entity, Objective> objectiveMap = new HashMap();

    public static Map<Entity, Quest> getQuestMap(){
        return questMap;
    }

    public static Map<Entity, Objective> getObjectiveMap() {
        return objectiveMap;
    }

    public static void setQuest (Entity entity, Quest quest){
        questMap.put(entity, quest);
    }
    public static void setObjective (Entity entity, Objective objective) {objectiveMap.put(entity, objective);}
}
