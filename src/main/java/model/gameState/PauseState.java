package model.gameState;

import controller.MouseHandler;
import model.Dialogues.DialogueManager;
import model.sound.Sound;
import view.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.Image.SCALE_DEFAULT;

public class PauseState implements GameState{


    int screenWidth = GamePanel.screenWidth;
    int screenHeight = GamePanel.screenHeight;
    private static int volumeIndicator=90;

    private int volumeBarHeight=20;
    private int volumeBarWidth =200;
    private boolean obscure = false;
    //private boolean released = false;
    private boolean tutorialImageVisible = false;
    private boolean commandButtonClicked = false;

    private Image tutorialImage;

    private MouseHandler mouseHandler;


    public PauseState() {
        System.out.println("costruttore pausestate");
        this.mouseHandler = MouseHandler.getInstance();
        gsm.stopMusic(0);


        // Carica l'immagine del tutorial
        try {
            InputStream inputStream = getClass().getResourceAsStream("/ui/MappaComandi.png");
            tutorialImage = ImageIO.read(inputStream);
            tutorialImage = tutorialImage.getScaledInstance(2560/4, 1440/4, SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            obscureCommandButton();
        }

        // Nuova logica per l'immagine tutorial
        if (keyH.isEnterToggle() && commandButtonClicked) {
            // Inverti lo stato dell'immagine del tutorial
            tutorialImageVisible = false;
        }

        // l'ho commentato per adesso
        /*
        if(mouseHandler.isMouseReleased() && released) {
            System.out.println("Rilasciato");
            DialogueManager.showTutorial();
            released = false;
        }
         */



    }

    private void obscureCommandButton(){
        int mouseX = mouseHandler.getMouseX();
        int mouseY = mouseHandler.getMouseY();

        int commandButtonX = (screenWidth - 100) / 2;
        int commandButtonY = screenHeight/ 2 -40;

        if (mouseX >= commandButtonX && mouseX <= commandButtonX + 120
                && mouseY >= commandButtonY - 40 && mouseY <= commandButtonY) {
            System.out.println("Clicco");
            commandButtonClicked = true;
            // Inverti lo stato dell'immagine del tutorial
            tutorialImageVisible = true;
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



        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String commandText = "Comandi";
        int commandTextWidth = g.getFontMetrics().stringWidth(commandText);
        int commandTextX = (screenWidth - commandTextWidth) / 2;
        int commandTextY = screenHeight/ 2 -50;
        g.drawString("Comandi", commandTextX, commandTextY);

        //Menu command
        if(obscure) {
            g.setColor(new Color(0, 0, 0, 150));
            g.drawString("Comandi", commandTextX, commandTextY);
            obscure=false;
        }



        // Draw exit option
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String exitText = "Premi P per riprendere";
        int exitTextWidth = g.getFontMetrics().stringWidth(exitText);
        int exitX = (screenWidth - exitTextWidth) / 2;
        int exitY = volumeBarY + volumeBarHeight + 150;
        g.drawString(exitText, exitX, exitY);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String pauseText = "Menù Pausa";
        int textWidth = g.getFontMetrics().stringWidth(pauseText);
        int x = (screenWidth - textWidth) / 2;
        int y = screenHeight/ 4 - 50;
        g.drawString(pauseText, x, y);

        // Draw tutorial image if visible
        if (tutorialImageVisible && tutorialImage != null) {

            int panelWidth = GamePanel.screenWidth;  // Ottieni la larghezza del pannello
            int panelHeight = GamePanel.screenHeight;  // Ottieni l'altezza del pannello
            int tutorialx = panelWidth/2 - 2560/8;
            // 2076/3 - panelWidth/2;  // Adjust as needed
            int tutorialy = panelHeight/2 - 1440/8 ; // Regola la posizione Y come desiderato
            g.drawImage(tutorialImage, tutorialx, tutorialy, null);
        }


    }

    public int getVolume() {
        return PauseState.volumeIndicator;
    }

    public void setVolume(int volume) {
        PauseState.volumeIndicator = volume;
    }
}

