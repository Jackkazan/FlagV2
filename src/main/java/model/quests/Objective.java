package model.quests;

import java.util.List;

public class Objective {
    String objectiveTitle;
    int objectiveID;
    private List<String> objectiveMessage;
    List<String> objectiveAssociatedEntitiesName;
    private boolean isCompleted = false;
    private int[] requiredObjectivesID;
    private boolean trick = false;
    public Objective(){}

    public Objective(String objectiveTitle, int objectiveID, List<String> objectiveMessage, List<String> objectiveAssociatedEntitiesName, boolean isCompleted, int[] requiredObjectivesID) {
        this.objectiveTitle = objectiveTitle;
        this.objectiveID = objectiveID;
        this.objectiveMessage = objectiveMessage;
        this.objectiveAssociatedEntitiesName = objectiveAssociatedEntitiesName;
        this.isCompleted = isCompleted;
        this.requiredObjectivesID = requiredObjectivesID;
    }

    public String getTitle() {
        return objectiveTitle;
    }
    public List<String> getObjectiveAssociatedEntitiesName() {
        return objectiveAssociatedEntitiesName;
    }
    public List<String> getObjectiveMessage() {
        return objectiveMessage;
    }
    public boolean isTrick(){
        return trick;
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
