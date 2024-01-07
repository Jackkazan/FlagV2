package model.quests;

import java.util.List;

public class Objective {
    String objectiveTitle;
    private boolean finalObjective;

    List<String> objectiveAssociatedEntities;

    private boolean isCompleted = false;

    public String getTitle() {
        return objectiveTitle;
    }
    public boolean isFinalObjective(){
        return finalObjective;
    }
    public List<String> getObjectiveAssociatedEntities() {
        return objectiveAssociatedEntities;
    }
    public boolean isCompleted(){
        return isCompleted;
    }
    public void setToComplete(){
        isCompleted = false;
    }

    public void complete(){
        this.isCompleted = true;
    }
}
