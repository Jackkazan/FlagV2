package model.Dialogues;

import java.util.List;

public class Dialogue {
    private String speaker;
    private List<String> questStart;
    private List<String> questInProgress;
    private List<String> questCompleted;
    private List<String> defaultDialogue;
    private List<String> failDialogue;

    public String getSpeaker() {
        return speaker;
    }

    public List<String> getQuestStart() {
        return questStart;
    }

    public List<String> getQuestInProgress() {
        return questInProgress;
    }

    public List<String> getQuestCompleted() {
        return questCompleted;
    }

    public List<String> getDefaultDialogue(){
        return defaultDialogue;
    }

    public List<String> getFailDialogue() {
        return failDialogue;
    }
}