package model.quests;

import model.gameState.GameStateManager;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQuest {
    GameStateManager gsm;
    QuestManager questManager;
    @Before
    public void setup(){
        gsm = GameStateManager.getInstance();
        gsm.init();
    }
    @Test
    public void testQuest(){
        setup();
        questManager = QuestManager.getInstance();
        assertNotNull(questManager);
        assertNotNull(questManager.getQuestMap());
        assertNotNull(questManager.getObjectiveMap());
        assertNotNull(questManager.getQuestIDmap());
    }
    @Test
    public void testMotherQuest(){
        setup();
        questManager = QuestManager.getInstance();
        assertNotNull(questManager);
        Quest quest3 = questManager.getQuestIDmap().get(3);
        Quest quest4 = questManager.getQuestIDmap().get(4);
        quest3.setProgress(Quest.Progress.TOCOMPLETE);
        quest4.setProgress(Quest.Progress.TOCOMPLETE);
        Quest questVecchietta = questManager.getQuestIDmap().get(2);
        questVecchietta.advance(questVecchietta.getRewarder());
        assertSame(Quest.Progress.INPROGRESS, questVecchietta.getProgress());
        quest3.advance(quest3.getRewarder());
        quest4.advance(quest4.getRewarder());
        questVecchietta.advance(questVecchietta.getRewarder());
        assertSame(Quest.Progress.COMPLETED, questVecchietta.getProgress());
        Quest questFabbro = questManager.getQuestIDmap().get(5);
        assertSame(Quest.Progress.TOCOMPLETE, questFabbro.getProgress());


    }
}
