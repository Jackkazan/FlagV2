package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, interactPressed, attackVPressed, spacePressed, enterPressed, yPressed, pPressed, startGame;
    private boolean pauseSwitch;
    public boolean interactRequest = false;

    private boolean enterToggle = false;

    private boolean showDebugText = false;

    private boolean attacking = false;
    private static KeyHandler instance = null;

    public KeyHandler(){}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static KeyHandler getInstance(){
        if (instance == null)
            instance = new KeyHandler();
        return instance;
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
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            startGame = true;
            enterToggle = true;
        }
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
            interactRequest = true;
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
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
            enterToggle = false;
        }
    }
    public void releaseToggles(){
        pauseSwitch = false;
        //interactRequest = false;
    }

    public boolean isEnterToggle() {
        return enterToggle;
    }

    public boolean pauseSwitch(){
        return pauseSwitch;
    }

    public boolean isShowDebugText() {

        return showDebugText;
    }


}

