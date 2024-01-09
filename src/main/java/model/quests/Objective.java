package model.quests;

import java.util.List;

public class Objective {
    String objectiveTitle;
    int objectiveID;
    private boolean finalObjective;

    List<String> objectiveAssociatedEntities;

    private boolean isCompleted = false;
    private int[] requiredObjectivesID;

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
    public int[] getRequiredObjectivesID(){
        return requiredObjectivesID;
    };

    public int getObjectiveID() {
        return objectiveID;
    }

    public void complete(){
        this.isCompleted = true;
    }
}
