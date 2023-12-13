package model.gameState;

import controller.KeyHandler;
import model.entity.Entity;
import view.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogueState implements GameState {

    private GamePanel gp;
    private GameStateManager gsm;
    private KeyHandler keyH;
    private Entity npc;

    private String dialogue;
    private ArrayList<String> test = new ArrayList<>();
    private int i = 0;
    private boolean dialogueAdvancing = false;



    public DialogueState(GamePanel gp, GameStateManager gsm, KeyHandler keyH, Entity npc) {
            this.gp = gp;
            this.gsm = gsm;
            this.keyH = keyH;
            this.npc = npc;
            test.add("dialogo 1");
            test.add("dialogo2");
            test.add("dialogo 3 porcamadonna" );
            dialogue = test.get(i);
    }

    @Override
    public void update() {
        gsm.getPlayState().update();
        if (keyH.isPaused()){
            gsm.dialoguePause();
        }
        if(keyH.spacePressed && !dialogueAdvancing){
           advanceDialogue();
           dialogueAdvancing = true;
        }
        if(!keyH.spacePressed) {
            dialogueAdvancing = false;
        }

        // logica dialoghi
    }
    public void advanceDialogue(){
        if(i< test.size()-1) {
            System.out.println("sono stato chiamata");
            i++;
            dialogue = test.get(i);
        }
        else
            gsm.exitDialogue();
    }
    @Override
    public void draw(Graphics g) {
        gsm.getPlayState().draw(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));;
        int textWidth = g.getFontMetrics().stringWidth(dialogue);
        int x = (gp.getScreenWidth()- textWidth) / 2;
        int y = gp.getScreenHeight()/ 2;
        g.drawString(dialogue, x, y);

    }
}
