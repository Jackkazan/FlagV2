package model.data;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import model.entities.characters.npc.Npc;
import model.quests.Quest;

import java.util.List;

public class QuestData {
    @SerializedName("quests")
    private List<Quest> quests;

    public List<Quest> getQuests() {
        return quests;
    }
}
