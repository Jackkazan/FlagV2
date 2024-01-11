package model.quests;

import model.Dialogues.DialogueManager;
import model.data.DataInitializer;
import model.entities.Entity;

import model.gameState.GameStateManager;

import java.util.*;


public class QuestManager {
    private Map<String, Quest> questMap;
    private List<Quest> completedQuestList;
    private Map<String, Objective> objectiveMap;
    private Map<Integer, Quest> questIDMap;
    private static QuestManager instance;
    public QuestManager(){
        questMap = new HashMap();
        completedQuestList = new ArrayList<>();
        objectiveMap = new HashMap();
        questIDMap = new HashMap<>();
    }
    public static QuestManager getInstance(){
        if (instance == null)
            instance = new QuestManager();
        return instance;
    }
    public void setQuest(String entityName, Quest quest) {
        questMap.put(entityName, quest);
    }
    public void setObjective(String entityName, Objective objective){
        objectiveMap.put(entityName, objective);}
    public void setQuestIDMap(Integer id, Quest quest){
        questIDMap.put(id, quest);
    }
    public boolean questAction(Entity entity){
        Objective objective = this.objectiveMap.get(entity.getName());
        return objective == null || (!objective.isCompleted() && handleObjective(entity, objective));
    }
    public boolean handleObjective(Entity entity, Objective objective){
        DialogueManager.getInstance().showObjectiveMessage(objective);
        Quest quest = questMap.get(entity.getName());
        if (!(objective.isTrick())) {
            if (objective.getRequiredObjectivesID() == null || Arrays.stream(objective.getRequiredObjectivesID()).allMatch(id -> quest.getObjectiveById(id).isCompleted())){
                if (!quest.started()) quest.start();
                objective.complete();
                advanceQuest(entity);
                return true;
            }
        }
        handleFail(quest);
        return false;
    }
    public void addCompletedQuest(Quest quest){
        completedQuestList.add(quest);
    }
    public void advanceQuest(Entity entity){
        Quest quest;
        if((quest = questMap.get(entity.getName()))!= null)
            quest.advance(entity.getName());
    }
    public void advanceMotherQuest(String entityName, Quest quest){
        if(quest.getMotherQuestIDs()!=null) {
            for (Integer id : quest.getMotherQuestIDs()) {
                Quest motherQuest = questIDMap.get(id);
                if (motherQuest != null) {
                    motherQuest.getRequiredQuestsList().remove(quest.getID());
                    motherQuest.advance(entityName);
                }
            }
        }
    }
    public void handleFail(Quest quest){
        String action = quest.getQuestFailAction();
        System.out.println(action);
        if(action != null)
            switch (action){
                case "reset" ->{
                    quest.getObjectives().forEach(Objective::setToComplete);
                    quest.getAssociatedEntitiesName().stream().map(DataInitializer::findEntityByName)
                            .filter(Objects::nonNull).forEach(entity -> entity.setInteractable(true));
                    GameStateManager.getInstance().playSound(1);
                }
            }
            DialogueManager.getInstance().showQuestMessage(quest.getQuestFailMessage());
    }

    public List<Quest> getCompletedQuestList() {
        return completedQuestList;
    }

    public Map<String, Quest> getQuestMap(){
        return questMap;
    }

    public Map<String, Objective> getObjectiveMap() {
        return objectiveMap;
    }

    public Quest getEntityQuest(Entity entity) {
        return questMap.get(entity.getName());
    }

    public Map<Integer, Quest> getQuestIDmap() {
        return questIDMap;
    }

   /* public Map<Entity, TrickObjective> getTrickObjectiveMap() {
        return trickObjectiveMap;
    }*/
}

