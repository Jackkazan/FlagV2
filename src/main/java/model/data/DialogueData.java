package model.data;

import com.google.gson.annotations.SerializedName;
import model.Dialogues.Dialogue;

import java.util.List;

public class DialogueData {
    @SerializedName("dialogues")
    private List<Dialogue> dialogues;

    public List<Dialogue> getDialogues() {
        return dialogues;
    }
}
