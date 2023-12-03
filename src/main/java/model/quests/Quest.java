package model.quests;

public class Quest {

    private String questName;
    private boolean isDone;

    public Quest(String questName){

        this.questName = questName;
        this.isDone = false;

    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }
}
