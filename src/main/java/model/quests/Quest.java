package model.quests;

import java.util.List;
import java.util.function.Predicate;

public class  Quest {

    private String title;
    private Progress progress = Progress.START;
    List<String> associatedEntities;
    List<Objective> objectives;
    private boolean started, inProgress, Completed;

    /*public Quest(String title, boolean b){

        this.title = title;
        this.isDone = b;

    }*/
    public void setProgress(Progress progress){
        switch (progress){
            case START ->
                progress = Progress.START;

        }
    }
    public enum Progress{START, INPROGRESS, COMPLETED};


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
        return Completed;
    }

    public void complete() {
        Completed = true;
        QuestManager.addCompletedQuest(this);
    }

}
