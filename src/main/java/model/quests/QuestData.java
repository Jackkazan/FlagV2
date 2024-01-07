package model.quests;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import model.entities.characters.npc.Npc;

import java.util.List;

public class QuestData {
    @SerializedName("quests")
    private List<Quest> quests;

    public List<Quest> getQuests() {
        return quests;
    }
}
