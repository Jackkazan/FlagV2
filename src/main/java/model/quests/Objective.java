package model.quests;

import java.util.List;

public class Objective {
    String objectiveTitle;
    List<String> objectiveAssociatedEntities;

    private boolean isCompleted = false;

    public String getTitle() {
        return objectiveTitle;
    }

    public List<String> getObjectiveAssociatedEntities() {
        return objectiveAssociatedEntities;
    }
    public boolean isCompleted(){
        return isCompleted;
    }

    public void complete(){
        this.isCompleted = true;
    }
}
