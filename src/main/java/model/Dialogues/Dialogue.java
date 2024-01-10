package model.Dialogues;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Dialogue {

    private String speaker;

    private List<String> questInactive;

    private List<String> questInProgress;
    private List<String> questToComplete;
    private List<String> questCompleted;
    private List<String> defaultDialogue;
    
    private List<String> failDialogue;

    public String getSpeaker() {
        return speaker;
    }

    public List<String> getQuestInactive() {
        return questInactive;
    }

    public List<String> getQuestInProgress() {
        return questInProgress;
    }
    public List<String> getQuestToComplete(){
        return questToComplete;
    }

    public List<String> getQuestCompleted() {
        return questCompleted;
    }

    public List<String> getDefaultDialogue(){
        return defaultDialogue;
    }

}