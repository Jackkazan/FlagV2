package model.quests;

import java.util.List;
import java.util.function.Predicate;

public class  Quest {

    private String title;
    List<String> associatedEntities;
    List<Objective> objectives;
    private boolean isCompleted;

    /*public Quest(String title, boolean b){

        this.title = title;
        this.isDone = b;

    }*/

    public List<Objective> getObjectives() {
        return objectives;
    }

    public List<String> getAssociatedEntities() {
        return associatedEntities;
    }

    public String getTitle(){
        return this.title;
    }


   public boolean isCompleted(){
        return isCompleted;
    }

    public void complete() {
        isCompleted = true;
    }
    public Predicate<Quest> isQuestCompleted;

}
