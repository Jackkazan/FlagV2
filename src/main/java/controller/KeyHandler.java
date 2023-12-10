package controller;

import view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private GamePanel gamePanel;

    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, attackPressed;

    private boolean showDebugText = false;
    private boolean pausePressed = false;
    private boolean pauseToggle = true;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public KeyHandler(GamePanel gamePanel) {  // Aggiunto
        this.gamePanel = gamePanel;  // Aggiunto
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_T){
            showDebugText = !showDebugText;
        }

        if(code == KeyEvent.VK_SPACE){
            attackPressed = true;
        }

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_E){
            interactPressed = true;
        }
        //PAUSA
        if (code == KeyEvent.VK_P) {
            if (!pauseToggle) {
                // Se la pausa non è in modalità toggle, imposta isPaused sulla pressione
                pausePressed = true;
            } else {
                // Altrimenti, in modalità toggle, inverte lo stato di pausa quando si preme "P"
                gamePanel.togglePause();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE){
            attackPressed = false;
        }
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_E){
            interactPressed = false;
        }
        //PAUSA
        if (code == KeyEvent.VK_P) {
            if (!pauseToggle) {
                // Se la pausa non è in modalità toggle, imposta isPaused sul rilascio
                pausePressed = false;
                if (gamePanel.isPaused()) {
                    gamePanel.togglePause();
                }
            }
        }
    }

    public boolean isShowDebugText() {

        return showDebugText;
    }

    public boolean isPausePressed() {
        return pausePressed;
    }
}
