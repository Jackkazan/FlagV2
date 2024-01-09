package model.quests;

import java.util.List;
import java.util.function.Predicate;

public class  Quest {

    private int ID;
    private String title;
    private Progress progress = Progress.START;
    private List<String> associatedEntities;
    private List<Objective> objectives;
    private boolean started, inProgress, Completed;

    /*public Quest(String title, boolean b){

        this.title = title;
        this.isDone = b;

    }*/
    public void setProgress(Progress progress){
        switch (progress){
            case START ->
                progress = Progress.START;
            case INPROGRESS ->
                progress = Progress.INPROGRESS;
            case COMPLETED -> {
                if(this.getObjectives().stream().allMatch(Objective::isCompleted)){
                    progress = Progress.COMPLETED;
                    QuestManager.addCompletedQuest(this);
                    if(QuestManager.getCompletedQuestList().get(0)!=null)
                        System.out.println(QuestManager.getCompletedQuestList().get(0));
                }
            }
        }
    }
    public enum Progress{START, INPROGRESS, COMPLETED};


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

    public List<String> getAssociatedEntities() {
        return associatedEntities;
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
