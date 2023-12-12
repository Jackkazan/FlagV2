package model.quests;

import java.util.ArrayList;

public class QuestInitializer {

    public static ArrayList<Quest> createQuestList(){
        ArrayList<Quest> questList=new ArrayList<>();
        Quest appenaSveglio = new Quest("AppenaSveglioTiALziDalLetto", true);
        Quest chiaveRaccoltaCasettaIniziale = new Quest("ChiaveRaccoltaCI", false);
        Quest portaSbloccataCasettaIniziale = new Quest("PortaSbloccataCI", false);
        Quest parlaConLaVecchietta = new Quest("HaiParlatoConVecchietta", false);
        Quest parlaColContadino1PerZucche = new Quest("HaiParlaColContadino1Zucche", false);
        Quest parlaColContadino2PerSpaventapasseri = new Quest("HaiParlaColContadino2PerSpaventapasseri", false);
        Quest interagitoConGliSpaventapasseriOrdineCorretto = new Quest("HaiInteragitoConGliSpaventapasseriNellOrdineCorretto", false);
        Quest estirpatoTutteLeZuccheConIVermi = new Quest("HaiEstirpatoLeZuccheConIVermi", false);


        //0
        questList.add(appenaSveglio);
        //1
        questList.add(chiaveRaccoltaCasettaIniziale);
        //2
        questList.add(portaSbloccataCasettaIniziale);
        //3
        questList.add(parlaConLaVecchietta);
        //4
        questList.add(parlaColContadino1PerZucche);
        //5
        questList.add(parlaColContadino2PerSpaventapasseri);
        //6
        questList.add(interagitoConGliSpaventapasseriOrdineCorretto);
        //7
        questList.add(estirpatoTutteLeZuccheConIVermi);

        return questList;
    }





}
