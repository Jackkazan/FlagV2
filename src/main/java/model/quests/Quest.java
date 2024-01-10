package model.quests;

import model.Dialogues.DialogueManager;
import model.data.DataInitializer;
import model.entities.Entity;
import model.entities.characters.player.Player;
import model.gameState.GameStateManager;

import java.util.List;
import java.util.Objects;


public class  Quest {

    private int ID;
    private String title;
    private Progress progress = Progress.INACTIVE;
    private int motherQuestID = 0;
    public int getMotherQuestID() {
        return motherQuestID;
    }

    private boolean entitiesInteractionDependency;

    private String questFailAction;
    private List<String> associatedEntitiesName;
    private List<Integer> requiredQuestsID;

    //private List<Entity> associatedEntities;
    private List<Objective> objectives;
    private List<TrickObjective> trickObjectives;
    private List <String> questProgressMessage, questToCompleteMessage, questCompletedMessage, questFailMessage;
    private Reward rewards;
    private String rewarder;
    private boolean rewarded;
    private boolean questSucceedSound;

    public List<String> getQuestProgressMessage() {
        return questProgressMessage;
    }

    public List<String> getQuestToCompleteMessage() {
        return questToCompleteMessage;
    }

    public List<String> getQuestCompletedMessage() {
        return questCompletedMessage;
    }
    public List<String> getQuestFailMessage(){
        return questFailMessage;
    }

    public void setProgress(Progress progress){
        switch (progress){
            case INACTIVE ->
                    this.progress = Progress.INACTIVE;
            case INPROGRESS ->{
                this.progress = Progress.INPROGRESS;
                if(questProgressMessage != null)
                    DialogueManager.getInstance().showQuestMessage(questProgressMessage);
            }
            case TOCOMPLETE ->{
                this.progress = Progress.TOCOMPLETE;
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(questToCompleteMessage);
                if(questToCompleteMessage != null)
                    DialogueManager.getInstance().showQuestMessage(questToCompleteMessage);
            }
            case COMPLETED ->{
                this.progress = Progress.COMPLETED;
                QuestManager.getInstance().addCompletedQuest(this);
                if(questCompletedMessage != null)
                    DialogueManager.getInstance().showQuestMessage(questCompletedMessage);
            }

            }
    }

    public void advance(Entity entity){
        if (this.progress == Progress.INACTIVE){
            System.out.println("check Inactive");
            this.setProgress(Progress.INPROGRESS);
            if (this.hasInteractionDependancy())
                this.associatedEntitiesName.stream().map(DataInitializer::findEntityByName).forEach( (entity1 -> entity1.setInteractable(true)));
        }
        if (this.progress == Progress.INPROGRESS){
            System.out.println("check INPROGRESS");
            if (this.checkRequirementsForCompletion())
                if (this.hasRewards()){
                    this.setProgress(Progress.TOCOMPLETE);
                    if (questSucceedSound)
                        GameStateManager.getInstance().playSound(2);
                }
                else {
                    this.setProgress(Progress.COMPLETED);
                    System.out.println("check completed");
                }
        }
        if (this.progress == Progress.TOCOMPLETE && rewarder!= null && entity.getName().equals(rewarder)){
            this.reward();
            System.out.println("check rewarded");
            this.setProgress(Progress.COMPLETED);
            QuestManager.getInstance().advanceMotherQuest(entity, this);
        }
    }
    public boolean checkRequirementsForCompletion(){
        if (this.requiredQuestsID != null) {
            List<Integer> id = QuestManager.getInstance().getCompletedQuestList().stream().map(Quest::getID).toList();
            if(!id.containsAll(requiredQuestsID)){
                System.out.println(requiredQuestsID);
                System.out.println(id);
                return false;
            }
            if(this.getObjectives()!= null && id.containsAll(requiredQuestsID));
            return true;

        }
        if(this.getObjectives()!= null && this.getObjectives().stream().allMatch(Objective::isCompleted))
            return true;
        return false;
    }
    public String getQuestFailAction() {
        return questFailAction;
    }

    public boolean hasInteractionDependancy() {
        return entitiesInteractionDependency;
    }

    public boolean hasRewards(){
        return this.rewards != null;
    }
    public boolean rewarded(){
        return this.rewarded;
    }
    public void reward(){
        Player player = GameStateManager.getInstance().getPlayer();
        if(this.hasRewards()){
            if (this.rewards.getCoin() !=0)
                player.addBalance(rewards.getCoin());
            if (this.rewards.getUnlock())
                this.getAssociatedEntitiesName().stream().filter(entityName -> entityName.startsWith("Wall")).map(DataInitializer::findEntityByName)
                        .filter(Objects::nonNull).forEach(entity -> entity.setCollisionArea(null));
            if (this.rewards.getWeapon()){
                player.setArmed(true);
            }
        }
    }

    public String  getRewards() {
        if (this.hasRewards())
            return rewards.toString();
        return null;
    }

    public enum Progress{INACTIVE, INPROGRESS, TOCOMPLETE, COMPLETED};


    public List<Objective> getObjectives(){
        return objectives;
    }
    public List<TrickObjective> getTrickObjectives() {
        return trickObjectives;
    }

    public Objective getObjectiveById(int id){
        for(Objective objective : this.getObjectives()){
            if(objective.getObjectiveID() == id)
                return objective;
        }
        return null;
    }


    public List<String> getAssociatedEntitiesName() {
        return associatedEntitiesName;
    }

    public int getID() {
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public Progress getProgress() {
        return progress;
    }

    public boolean isCompleted(){
        return this.progress == Progress.COMPLETED;
    }

}
/*public class  Quest {

    private int ID;
    private String title;
    private Progress progress = Progress.INACTIVE;
    private List<String> associatedEntitiesName;
    private boolean hasReward;
    private Reward rewards;
    private boolean rewarded;
    private List<Objective> objectives;
    private boolean started, inProgress, Completed;

    /*public Quest(String title, boolean b){

        this.title = title;
        this.isDone = b;

    }
    public void setProgress(Progress progress){
        switch (progress){
            case INACTIVE ->
                    progress = Progress.INACTIVE;
            case INPROGRESS ->
                    progress = Progress.INPROGRESS;
            case TOCOMPLETE ->
                    progress = Progress.TOCOMPLETE;
            case COMPLETED -> {
                if(this.getObjectives().stream().allMatch(Objective::isCompleted)){
                    progress = Progress.COMPLETED;
                    //QuestManager.addCompletedQuest(this);
                    if(QuestManager.getInstance().getCompletedQuestList().get(0)!=null)
                        System.out.println(QuestManager.getInstance().getCompletedQuestList().get(0));
                }
            }
        }
    }

    public List<String> getAssociatedEntitiesName() {
        return associatedEntitiesName;
    }

    public enum Progress{INACTIVE, INPROGRESS, TOCOMPLETE, COMPLETED};


    public List<Objective> getObjectives() {
        return objectives;
    }
    public Objective getObjectiveById(int id){
        for(Objective objective : this.getObjectives()){
            if(objective.getObjectiveID() == id)
                return objective;
        }
        return null;
    }
    public boolean hasRewards(){
        return this.rewards != null;
    }
    public boolean rewarded(){
        return this.rewarded;
    }
    public void reward(){

    }
    public String  getRewards() {
        if (this.hasRewards())
            return rewards.toString();
        return null;
    }

    //public List<String> getAssociatedEntities() {
      //  return associatedEntities;
    //}

    public int getID() {
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public Progress getProgress() {
        return progress;
    }

    public boolean isCompleted(){
        return this.progress == Progress.COMPLETED;
    }

}*/

