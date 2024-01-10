package model.quests;

import java.util.List;

public class Objective {
    String objectiveTitle;
    int objectiveID;
    private List<String> objectiveMessage;

    List<String> objectiveAssociatedEntitiesName;


    private boolean isCompleted = false;
    private int[] requiredObjectivesID;

    public String getTitle() {
        return objectiveTitle;
    }
    public List<String> getObjectiveAssociatedEntitiesName() {
        return objectiveAssociatedEntitiesName;
    }
    public List<String> getObjectiveMessage() {
        return objectiveMessage;
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
