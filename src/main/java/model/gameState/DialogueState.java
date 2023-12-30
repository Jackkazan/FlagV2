package model.gameState;

import controller.KeyHandler;
import model.entities.npc.Npc;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class DialogueState implements GameState {

    private GamePanel gp;
    private GameStateManager gsm;
    private KeyHandler keyH;
    private Npc npc;

    private String dialogue;
    private String dialogueText = "";
    private ArrayList<String> test = new ArrayList<>();
    private int i = 0;
    private boolean dialogueAdvancing = false;
    private boolean dialogueDisplayed;
    int index = 0;
    private final int dialogueBoxX;
    private final int dialogueBoxY;
    private final int dialogueBoxWidth;
    private final int dialogueBoxHeight;
    private boolean ePressed = false;
    private boolean eReleased = true;



    public DialogueState(GamePanel gp, GameStateManager gsm, KeyHandler keyH, Npc npc) {
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = keyH;
        this.npc = npc;
        dialogueBoxWidth = gp.getScreenWidth() - (GamePanel.tileSize * 4);
        dialogueBoxHeight = GamePanel.tileSize*5;
        dialogueBoxX = GamePanel.tileSize * 2;
        dialogueBoxY = gp.getScreenHeight() -(dialogueBoxHeight + GamePanel.tileSize * 2);
        test.add("dialogo 1 ");
        test.add("dialogo 2 ");
        test.add("dialogo 3 ");
        dialogue = test.get(i);
    }

    @Override
    public void update() {
        gsm.getPlayState().update();
        if (keyH.pauseSwitch()){
            gsm.setState(GameStateManager.State.PAUSE);
        }
        if(keyH.interactPressed && eReleased &&!dialogueAdvancing && dialogueDisplayed){
           advanceDialogue();
           dialogueAdvancing = true;
           dialogueDisplayed = false;
           ePressed = true;
        }
        if(!keyH.interactPressed) {
            eReleased = true; // Rilasciato il tasto E
            ePressed = false; // Resetta la variabile quando rilasci il tasto E
            dialogueAdvancing = false;
        }
        else{
            eReleased = false; //Quando si tiene premuto
        }


        //logica dialoghi
    }
    public void advanceDialogue(){
        if(i< test.size()-1) {
            dialogueText = "";
            index = 0;
            i++;
            dialogue = test.get(i);
        }
        else {
            //per non far startare il dialogo non appena finito
            keyH.interactPressed = false;
            gsm.exitDialogue();
        }
    }
    @Override
    public void draw(Graphics g) {
        gsm.getPlayState().draw(g);
        this.drawDialogueBox(g, dialogueBoxX, dialogueBoxY, dialogueBoxWidth, dialogueBoxHeight);
        this.drawDialogue(g, dialogueBoxX, dialogueBoxY);
    }

    public void drawDialogueBox(Graphics g, int x, int y, int width, int height){


        // Imposta il colore dello sfondo
        g.setColor(new Color(101,71,42));
        g.fillRoundRect(x, y, width, height, 40, 40);

        g.setColor(Color.BLACK);
        // Disegna il bordo del rettangolo
        g.drawRoundRect(x, y, width, height, 40, 40);

        // Aumenta le coordinate per disegnare il secondo bordo
        int spessoreBordo = 3; // Puoi personalizzare lo spessore del bordo
        x += spessoreBordo;
        y += spessoreBordo;
        width -= 2 * spessoreBordo;
        height -= 2 * spessoreBordo;

        // Imposta il colore di sfondo
        g.setColor(Color.BLACK);

        // Disegna il rettangolo di sfondo
        g.fillRoundRect(x, y, width, height, 90, 90);

        x += spessoreBordo;
        y += spessoreBordo;
        width -= 2 * spessoreBordo;
        height -= 2 * spessoreBordo;

        // Imposta il colore di sfondo
        g.setColor(new Color(171,124,77));

        // Disegna il rettangolo di sfondo
        g.fillRoundRect(x, y, width, height, 40, 40);



    }
    public void drawDialogue(Graphics g, int x, int y){
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));;
        x += (GamePanel.tileSize - 3 );
        y += (GamePanel.tileSize + 3);
        char characterArray[] = dialogue.toCharArray();
        if (index < characterArray.length){
            String temp = String.valueOf(characterArray[index]); // logica per mostrare il testo lettera per lettera
            dialogueText = dialogueText + temp;
            index++;
        }
        else{
            dialogueDisplayed = true;
        }
        g.drawString(dialogueText, x, y);
    }

}
