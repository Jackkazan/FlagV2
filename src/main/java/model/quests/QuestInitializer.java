package model.quests;

import java.util.ArrayList;

public class QuestInitializer {

    public static ArrayList<Quest> createQuestList(){
        ArrayList<Quest> questList=new ArrayList<>();
        Quest appenaSveglio = new Quest("AppenaSveglioTiALziDalLetto", true);
        Quest chiaveRaccoltaCasettaIniziale = new Quest("ChiaveRaccoltaCI", false);
        Quest portaSbloccataCasettaIniziale = new Quest("PortaSbloccataCI", false);


        //0
        questList.add(appenaSveglio);
        //1
        questList.add(chiaveRaccoltaCasettaIniziale);
        //2
        questList.add(portaSbloccataCasettaIniziale);

        return questList;
    }





}
