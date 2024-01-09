package model.gameState;

import controller.KeyHandler;
import model.Dialogues.DialogueManager;
import view.GamePanel;

import java.awt.*;

public class DialogueState implements GameState {

    private String dialogue;
    private String dialogueText = "";

    private int i = 0;
    private boolean dialogueAdvancing = false;
    private boolean dialogueDisplayed;
    int index = 0;
    private final int dialogueBoxX;
    private final int dialogueBoxY;
    private final int dialogueBoxWidth;
    private final int dialogueBoxHeight;
    private final int maxChars = 20;
    private boolean ePressed = false;
    private boolean eReleased = true;



    public DialogueState() {
        dialogueBoxWidth = screenWidth - (GamePanel.tileSize * 4);
        dialogueBoxHeight = GamePanel.tileSize*5;
        dialogueBoxX = GamePanel.tileSize * 2;
        dialogueBoxY = screenHeight -(dialogueBoxHeight + GamePanel.tileSize * 2);
        dialogue = DialogueManager.getDialogue();
        dialogueDisplayed = false;

    }

    @Override
    public void update() {
        gsm.getPlayState().update();
        System.out.println("dialogo" + keyH.interactRequest);
        if (keyH.pauseSwitch()){
            gsm.setState(GameStateManager.State.PAUSE);
        }
        if(keyH.interactRequest /*&& eReleased*/ && dialogueDisplayed){
           advanceDialogue();
           dialogueDisplayed = false;
        }
        //if(!keyH.interactPressed) {
            //eReleased = true; // Rilasciato il tasto E
           // ePressed = false; // Resetta la variabile quando rilasci il tasto E
           // dialogueAdvancing = false;
        //}
        //else{
            //eReleased = false; //Quando si tiene premuto
        //}


        //logica dialoghi
    }
    public void advanceDialogue(){
        dialogue = DialogueManager.getDialogue();
        dialogueText = "";
        index = 0;
        if (dialogue == null){
            gsm.exitDialogue();
        }
    }
    @Override
    public void draw(Graphics g) {
        gsm.getPlayState().draw(g);
        this.drawDialogueBox(g, dialogueBoxX, dialogueBoxY, dialogueBoxWidth, dialogueBoxHeight);
        if(dialogue != null)
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
    public void drawDialogue(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        ;
        x += (GamePanel.tileSize - 3);
        y += (GamePanel.tileSize + 3);
        char[] characterArray = dialogue.toCharArray();
        if (index < characterArray.length) {
            String temp = String.valueOf(characterArray[index]); // logica per mostrare il testo lettera per lettera
            dialogueText = dialogueText + temp;
            index++;
        } else {
            dialogueDisplayed = true;
        }
        g.drawString(dialogueText, x, y);
    }
        /*

        StringBuilder lineBuilder = new StringBuilder();
        StringBuilder wordBuilder = new StringBuilder();

        for (int i = index; i < characterArray.length; i++) {
            char currentChar = characterArray[i];

            if (currentChar == ' ' || currentChar == '\n') {
                // Check if adding the next word exceeds the maxWidth
                if (g.getFontMetrics().stringWidth(lineBuilder.toString() + wordBuilder.toString()) <= maxChars) {
                    lineBuilder.append(wordBuilder);
                    if (currentChar == '\n') {
                        // Start a new line if a newline character is encountered
                        drawLine(g, lineBuilder.toString(), x, y);
                        lineBuilder.setLength(0);  // Clear the lineBuilder for the next line
                        y += g.getFontMetrics().getHeight();
                    } else {
                        lineBuilder.append(" ");  // Add a space between words
                    }
                    wordBuilder.setLength(0);  // Clear the wordBuilder for the next word
                } else {
                    // Start a new line since the word alone exceeds the maxWidth
                    drawLine(g, lineBuilder.toString(), x, y);
                    lineBuilder.setLength(0);
                    y += g.getFontMetrics().getHeight();
                }
            } else {
                wordBuilder.append(currentChar);  // Append characters to form a word
            }
        }

        // Draw the remaining content
        drawLine(g, lineBuilder.toString() + wordBuilder.toString(), x, y);
    }

    private void drawLine(Graphics g, String line, int x, int y) {
        System.out.println(line);
        g.drawString(line, x, y);
    }*/



}
