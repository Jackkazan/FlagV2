package model.gameState;

import controller.KeyHandler;
import model.Dialogues.DialogueManager;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private DialogueManager dialogueManager;
    private boolean ePressed = false;
    private boolean eReleased = true;
    private static Font currentFont;
    private static int maxChars = 50;
    private List<String> wrappedDialogue;



    public DialogueState() {
        dialogueBoxWidth = screenWidth - (GamePanel.tileSize * 4);
        dialogueBoxHeight = GamePanel.tileSize*5;
        dialogueBoxX = GamePanel.tileSize * 2;
        dialogueBoxY = screenHeight -(dialogueBoxHeight + GamePanel.tileSize * 2);
        dialogueDisplayed = false;
        wrappedDialogue = new ArrayList<>();
        dialogueManager = DialogueManager.getInstance();
        currentFont = new Font("Arial", Font.BOLD, 24);

    }

    @Override
    public void update() {
        gsm.getPlayState().update();
        // Inizializza il dialogo
        if (dialogue == null)
            advanceDialogue();
        // Il dialogo avanza solo se è stato mostrato tutto
        if (keyH.interactRequest && dialogueDisplayed) {
            advanceDialogue();
            dialogueDisplayed = false;
        }
    }
    public static String wrapText(String inputStr) {
        if (inputStr == null || inputStr.length() <= maxChars) {
            return inputStr;
        }
        StringBuilder wrappedText = new StringBuilder();
        String[] words = inputStr.split("\\s+");
        int currentLength = 0;

        for (String word : words) {
            if (currentLength + word.length() <= maxChars) {
                wrappedText.append(word).append(" ");
                currentLength += word.length() + 1;
            } else {
                wrappedText.append("\n").append(word).append(" ");
                currentLength = word.length() + 1;
            }
        }
        System.out.println(wrappedText.toString().trim());

        return wrappedText.toString().trim();
    }
    public void advanceDialogue(){
        String temp = dialogueManager.getDialogue();
        // Manda a capo il testo automaticamente se supera maxChars
        dialogue = wrapText(temp);
        dialogueText = "";
        index = 0;
        if (dialogue == null){
            dialogueManager.exitDialogue();
        }
    }
   /* public static void setFont(String font){
        currentFont = switch (font){
            case "Message" -> new Font("Arial", Font.ITALIC, 24);
            default -> new Font("Arial", Font.BOLD, 24);
        };
    };*/
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
        g.setFont(currentFont);
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
        for(String line : dialogueText.split("\n")){
            g.drawString(line, x, y);
            y+=25;
        }
    }


}
