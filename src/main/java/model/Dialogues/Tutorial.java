package model.Dialogues;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {

    private static String tutorialComandi0 = "Premi WASD per muoverti e SPAZIO per attaccare, premi E per avanzare il tutorial";
    private static String tutorialComandi1 = "Premi E per interagire con oggetti o altri NPC, premi E per chiudere il tutorial";
    public static ArrayList<String> tutorialComandi = new ArrayList<>(List.of(tutorialComandi0, tutorialComandi1));
    public static int tutorialComandiIndex = 0;
    private static boolean tutorialComandiDisplayed = false;
    public static void setTutorialComandiDisplayed(boolean e){
        tutorialComandiDisplayed = e;
    }
    public static boolean getTutorialComandiDisplayed(){
        return tutorialComandiDisplayed;
    }

}
