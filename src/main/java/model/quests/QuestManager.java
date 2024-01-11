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
    // restituisce all'entità con cui si ha interagito se quest'ultima è collegata ad un obiettivo e se tale obiettivo può essere completato
    public boolean questAction(Entity entity){
        Objective objective = this.objectiveMap.get(entity.getName());
        Quest quest = getQuestMap().get(entity.getName());
        return objective == null || (!quest.isCompleted() && !objective.isCompleted() && handleObjective(entity, objective));
    }
    //gestione dell'obiettivo
    public boolean handleObjective(Entity entity, Objective objective){
        DialogueManager.getInstance().showObjectiveMessage(objective);
        Quest quest = questMap.get(entity.getName());
        // se l'obiettivo è un trick fallisce in automatico
        if (!(objective.isTrick())) {                           // controlla se gli obiettivi propedeutici al corrente sono stati completati
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
    //avanza la quest madre dell'obiettivo
    public void advanceQuest(Entity entity){
        Quest quest;
        if((quest = questMap.get(entity.getName()))!= null)
            quest.advance(entity.getName());
    }
    //notifica la quest madre del completamento del figlio, rimuovendo l'id del figlio dalla lista delle quest da completare prima del completamento
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

    // gestione del fallimento
    public void handleFail(Quest quest){
        String action = quest.getQuestFailAction();
        System.out.println(action);
        if(action != null)
            switch (action){
                case "reset" ->{ // ripristina l'interazione delle entità con cui si è già interagito e ripristina gli obiettivi
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

}

