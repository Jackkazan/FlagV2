package model.quests;

import model.Dialogues.DialogueManager;
import model.data.DataInitializer;
import model.entities.Entity;

import model.gameState.GameStateManager;

import java.util.*;


public class QuestManager {
    private Map<Entity, Quest> questMap;
    private List<Quest> completedQuestList;
    private Map<Entity, Objective> objectiveMap;
   // private Map<Entity, TrickObjective> trickObjectiveMap;
    private static QuestManager instance;
    public QuestManager(){
        questMap = new HashMap();
        completedQuestList = new ArrayList<>();
        objectiveMap = new HashMap();
        //trickObjectiveMap = new HashMap<>();
    }
    public static QuestManager getInstance(){
        if (instance == null)
            instance = new QuestManager();
        return instance;
    }
    public void setQuest(Entity entity, Quest quest){
        questMap.put(entity, quest);
    }
    public void setObjective(Entity entity, Objective objective){
        objectiveMap.put(entity, objective);}
    /*public void setTrickObjective(Entity entity, TrickObjective trickObjective) {
        System.out.println(entity.getName()); trickObjectiveMap.put(entity, trickObjective);}*/
    public boolean handleObjective(Entity entity, Objective objective){
        DialogueManager.getInstance().showObjectiveMessage(objective);

        Quest quest = getQuestMap().get(entity);

        if (!(objective.isTrick())) {
            if (objective.getRequiredObjectivesID() == null || Arrays.stream(objective.getRequiredObjectivesID()).allMatch(id -> quest.getObjectiveById(id).isCompleted())){
                if (quest.getProgress() == Quest.Progress.INACTIVE) {
                    quest.setProgress(Quest.Progress.INPROGRESS);
                }
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
        if((quest = questMap.get(entity))!= null)
            quest.advance(entity);
    }
    public void advanceMotherQuest(Entity entity, Quest quest){
        if(quest.getMotherQuestID()!=0){
            Quest motherQuest = null;
            for (Map.Entry<Entity, Quest> entry : questMap.entrySet()) {
                if (entry.getValue().getID() == quest.getMotherQuestID()){
                    motherQuest = entry.getValue();
                    break;
                }
            }
            if(motherQuest!=null) {
               motherQuest.advance(entity);}

        }

    }
    public void handleFail(Quest quest){
        String action = quest.getQuestFailAction();
        System.out.println(action);
        if(action != null)
            switch (action){
                case "reset" ->{
                    quest.getObjectives().forEach(Objective::setToComplete);
                    quest.getAssociatedEntitiesName().stream().map(DataInitializer::findEntityByName).forEach(entity -> entity.setInteractable(true));
                    GameStateManager.getInstance().playSound(1);
                }
            }
            DialogueManager.getInstance().showQuestMessage(quest.getQuestFailMessage());
    }

    public List<Quest> getCompletedQuestList() {
        return completedQuestList;
    }

    public Map<Entity, Quest> getQuestMap(){
        return questMap;
    }

    public Map<Entity, Objective> getObjectiveMap() {
        return objectiveMap;
    }

   /* public Map<Entity, TrickObjective> getTrickObjectiveMap() {
        return trickObjectiveMap;
    }*/
}

