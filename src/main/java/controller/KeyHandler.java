package controller;

import model.gameState.GameStateManager;
import view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyHandler implements KeyListener {

    private GamePanel gamePanel;
    private GameStateManager gsm;

    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, attackVPressed, spacePressed, enterPressed, yPressed, pPressed;
    private boolean pauseSwitch;

    private boolean showDebugText = false;

    private boolean attacking = false;

    public KeyHandler(){}

    @Override
    public void keyTyped(KeyEvent e) {

    }
    public void setGamePanel() {
        this.gamePanel = gsm.getGamePanel();
    }
    public KeyHandler(GameStateManager gsm) {  // Aggiunto
        this.gsm = gsm;
    }




    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }
        if (code == KeyEvent.VK_Y){
            yPressed = true;
        }
        if (code == KeyEvent.VK_ENTER)
            enterPressed = true;

        if (code == KeyEvent.VK_V) {
            attackVPressed = true;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            interactPressed = true;
        }
        //PAUSA
        if (code == KeyEvent.VK_P) {
            pPressed = true;
            pauseSwitch = true;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER)
            enterPressed = false;
        if (code == KeyEvent.VK_Y){
            yPressed = false;
        }
        if (code == KeyEvent.VK_V){
            attackVPressed = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
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
            pPressed = false;
        }
    }
    public void releaseToggles(){
        pauseSwitch = false;
    }

    public boolean pauseSwitch(){
        return pauseSwitch;
    }

    public boolean isShowDebugText() {

        return showDebugText;
    }


}

