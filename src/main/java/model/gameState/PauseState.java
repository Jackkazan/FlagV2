package model.gameState;

import controller.KeyHandler;
import controller.MouseHandler;
import model.sound.Sound;
import view.GamePanel;

import java.awt.*;

public class PauseState implements GameState{


    GameStateManager gsm;
    KeyHandler keyH;

    int screenWidth = GamePanel.screenWidth;
    int screenHeight = GamePanel.screenHeight;
    private static int volumeIndicator=90;

    private int volumeBarHeight=20;
    private int volumeBarWidth =200;

    private MouseHandler mouseHandler;



    public PauseState(GameStateManager gsm, KeyHandler keyH) {
        System.out.println("costruttore pausestate");
        this.mouseHandler = MouseHandler.getInstance();
        this.gsm = gsm;
        this.keyH = keyH;
        gsm.stopMusic(0);

    }

    @Override
    public void update() {
        //gsm.getPlayState().update();


        if (keyH.pauseSwitch()){
                gsm.setState(GameStateManager.State.PREVIOUS);// Uscendo dalla pausa bisogna ritornare allo stato precedente
                gsm.playMusicLoop(0);
        }
        // Aggiorna il volume quando il mouse è premuto sulla barra del volume
        if (mouseHandler.isMousePressed()) {
            updateVolume();
        }

    }

    private void updateVolume() {
        int mouseX = mouseHandler.getMouseX();
        int mouseY = mouseHandler.getMouseY();

        int volumeBarX = (screenWidth - volumeBarWidth) / 2;
        int volumeBarY = screenHeight / 2 + 50;

        if (mouseX >= volumeBarX && mouseX <= volumeBarX + volumeBarWidth
                && mouseY >= volumeBarY && mouseY <= volumeBarY + volumeBarHeight) {
            // Calcola il nuovo valore del volume in base alla posizione del mouse sulla barra del volume
            int newVolume = (mouseX - volumeBarX) * 100 / volumeBarWidth;
            //Il nuovo volume deve essere compreso tra 0 e 100
            volumeIndicator = Math.max(0, Math.min(100, newVolume));

            // Cambia il volume effettivo del suono
            for (Sound sound : gsm.getSongList()) {
                sound.setVolume(volumeIndicator  / 100.0f);  // Normalizza il volume a un valore compreso tra 0 e 1
            }
        }
    }

    @Override
    public void draw(Graphics g) {;
        gsm.getPlayState().draw(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String volumeText = "Volume: ";
        int volumeTextWidth = g.getFontMetrics().stringWidth(volumeText);
        int volumeTextX = (screenWidth - volumeTextWidth) / 2;
        int volumeTextY = screenHeight/ 2+30;
        g.drawString(volumeText, volumeTextX, volumeTextY);


        // Draw volume control bar
        g.setColor(Color.GRAY);  // Change color as needed
        int volumeBarWidth = 200;  // Set width as needed
        int volumeBarHeight = 20;  // Set height as needed
        int volumeBarX = (screenWidth - volumeBarWidth) / 2;
        int volumeBarY = screenHeight / 2 + 50;
        g.fillRect(volumeBarX, volumeBarY, volumeBarWidth, volumeBarHeight);

        // Draw volume indicator
        g.setColor(Color.WHITE);  // Change color as needed
        int volumeIndicatorWidth = volumeIndicator*2;
        g.fillRect(volumeBarX, volumeBarY, volumeIndicatorWidth, volumeBarHeight);


        // Draw exit option
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String exitText = "Press P to Exit";
        int exitTextWidth = g.getFontMetrics().stringWidth(exitText);
        int exitX = (screenWidth - exitTextWidth) / 2;
        int exitY = volumeBarY + volumeBarHeight + 100;
        g.drawString(exitText, exitX, exitY);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String pauseText = "Menù Pausa";
        int textWidth = g.getFontMetrics().stringWidth(pauseText);
        int x = (screenWidth - textWidth) / 2;
        int y = screenHeight/ 4;
        g.drawString(pauseText, x, y);

    }

    public int getVolume() {
        return PauseState.volumeIndicator;
    }

    public void setVolume(int volume) {
        PauseState.volumeIndicator = volume;
    }
}

