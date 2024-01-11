package model.quests;

import model.Dialogues.DialogueManager;
import model.data.DataInitializer;
import model.entities.Entity;
import model.entities.characters.player.Player;
import model.gameState.GameStateManager;

import java.util.List;
import java.util.Objects;


public class  Quest {

    private Integer ID; //Id della quest
    private String title; // titolo , da usare se si vuole implementare un journal
    private Progress progress = Progress.INACTIVE;
    private List<Integer> motherQuestIDs; // Quest che dipendono da this
    private boolean entitiesInteractionDependency; // Se true non è possibile interagire agli oggetti collegati
    // alle quest fin quando non startano (se ne necessario si può bypassare settando interazione nel entitybuilder)

    private String questFailAction; // Azione da fare se si fallisce la quest, se null viene solo mostrato il messaggio di fail (se presente)
    private List<String> associatedEntitiesName; // lista delle entità associate
    private List<Integer> requiredQuestsID; // lista degli ID delle quest richieste
    private List<Objective> objectives; // Lista degli obiettivi
    //Messaggi da mostrare quando la quest avanza
    private List <String> questProgressMessage;
    private List <String> questToCompleteMessage;
    private List <String> questCompletedMessage;
    private List <String> questFailMessage;
    //premio
    private Reward rewards;
    //nome dell'entità che da il premio
    private String rewarder;
    //se true la quest causa un suono una volta completata
    private boolean questSucceedSound;

    public Quest(int ID, String title, Progress progress, List<Integer> motherQuestIDs, boolean entitiesInteractionDependency, String questFailAction, List<String> associatedEntitiesName,
                 List<Integer> requiredQuestsID, List<Objective> objectives, List<String> questProgressMessage, List<String> questToCompleteMessage, List<String> questCompletedMessage, List<String> questFailMessage, Reward rewards, String rewarder, boolean questSucceedSound) {
        this.ID = ID;
        this.title = title;
        this.progress = progress;
        this.motherQuestIDs = motherQuestIDs;
        this.entitiesInteractionDependency = entitiesInteractionDependency;
        this.questFailAction = questFailAction;
        this.associatedEntitiesName = associatedEntitiesName;
        this.requiredQuestsID = requiredQuestsID;
        this.objectives = objectives;
        //this.trickObjectives = trickObjectives;
        this.questProgressMessage = questProgressMessage;
        this.questToCompleteMessage = questToCompleteMessage;
        this.questCompletedMessage = questCompletedMessage;
        this.questFailMessage = questFailMessage;
        this.rewards = rewards;
        this.rewarder = rewarder;
        this.questSucceedSound = questSucceedSound;
    }
    public Quest(){}
    public enum Progress{INACTIVE, INPROGRESS, TOCOMPLETE, COMPLETED}

    public void setProgress(Progress progress){
        switch (progress){
            case INACTIVE ->
                    this.progress = Progress.INACTIVE;
            case INPROGRESS ->{
                this.progress = Progress.INPROGRESS;
                this.setEntitiesInteractable();
                if(questProgressMessage != null)
                    DialogueManager.getInstance().showQuestMessage(getQuestProgressMessage());
            }
            case TOCOMPLETE ->{
                this.progress = Progress.TOCOMPLETE;
                if (questSucceedSound)
                    GameStateManager.getInstance().playSound(2);
                if(questToCompleteMessage != null)
                    DialogueManager.getInstance().showQuestMessage(getQuestToCompleteMessage());
            }
            case COMPLETED ->{
                this.progress = Progress.COMPLETED;
                QuestManager.getInstance().addCompletedQuest(this);
                if(questCompletedMessage != null)
                    DialogueManager.getInstance().showQuestMessage(getQuestCompletedMessage());
            }

            }
    }

    public void advance(String entityName){
        if (this.progress == Progress.INACTIVE){
            this.setProgress(Progress.INPROGRESS);
        }
        if (this.progress == Progress.INPROGRESS){
            if (this.checkRequirementsForCompletion())  // controlla se la quest può essere completata.
                if (this.hasRewards()){                 // se la quest ha una reward viene settata a to complete
                    this.setProgress(Progress.TOCOMPLETE);
                }
                else {
                    this.setProgress(Progress.COMPLETED);
                }
        }
        // se l'entità con cui si ha interagito è collegata alla quest si da il premio
        if (this.progress == Progress.TOCOMPLETE && entityName.equals(rewarder)){
            this.reward();
            this.setProgress(Progress.COMPLETED);
            QuestManager.getInstance().advanceMotherQuest(entityName, this);
        }
    }
    public void start(){
        this.setProgress(Progress.INPROGRESS);
    }
    public void setEntitiesInteractable(){
        if (this.hasInteractionDependancy())
            this.associatedEntitiesName.stream().map(DataInitializer::findEntityByName)
                    .filter(Objects::nonNull).forEach( (entity1 -> entity1.setInteractable(true)));
    }
    public boolean checkRequirementsForCompletion(){
        return isRequiredQuestListEmpty() && this.allObjectivesCompleted();
    }
    public boolean isRequiredQuestListEmpty(){
        return this.requiredQuestsID == null || this.requiredQuestsID.isEmpty();
    }
    public boolean allObjectivesCompleted(){
        return this.getObjectives() == null || this.getObjectives().stream().filter(objective -> !objective.isTrick()).allMatch(Objective::isCompleted);
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
    public boolean started(){
        return this.progress != Progress.INACTIVE;
    }
    public String  getRewards() {
        if (this.hasRewards())
            return rewards.toString();
        return null;
    }
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
    public List<Integer> getRequiredQuestsList() {
        return requiredQuestsID;
    }
    public List<Objective> getObjectives(){
        return objectives;
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

    public Integer getID() {
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }

    public Progress getProgress() {
        return progress;
    }
    public String getRewarder() {
        return rewarder;
    }
    public boolean isCompleted(){
        return this.progress == Progress.COMPLETED || this.progress == Progress.TOCOMPLETE;
    }
    public List<Integer> getMotherQuestIDs(){
        return motherQuestIDs;
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

