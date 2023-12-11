package model.quests;

public class  Quest {

    private String questName;
    private boolean isDone;

    public Quest(String questName, boolean b){

        this.questName = questName;
        this.isDone = b;

    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }

}
