package model.gameState;

import controller.KeyHandler;
import controller.MouseHandler;
import model.sound.Sound;
import view.GamePanel;

import java.awt.*;

public class PauseState implements GameState{

    GamePanel gp;
    GameStateManager gsm;
    KeyHandler keyH;

    private static int volume;
    private GamePanel gamePanel;  // Aggiungi questo campo
    private int volumeBarHeight=20;  // Aggiungi questo campo
    private int volumeBarWidth =200;  // Nuova variabile di istanza

    private MouseHandler mouseHandler;



    public PauseState(GamePanel gp, GameStateManager gsm, KeyHandler keyH) {
        System.out.println("costruttore pausestate");
        this.gp = gp;
        this.gsm = gsm;
        this.keyH = keyH;
        gsm.setAlreadyPaused(true);

        // Inizializza gamePanel
        this.gamePanel = gp;


        // Inizializza mouseHandler
        mouseHandler = new MouseHandler();
        gamePanel.addMouseListener(mouseHandler);
        gamePanel.addMouseMotionListener(mouseHandler);
    }

    @Override
    public void update() {
        //gsm.getPlayState().update();
        if (!keyH.isPaused()){
            if (gsm.isInDialogue()){
                gsm.setState(GameStateManager.State.PREVIOUS); // Se si è in dialogo uscendo dalla pausa bisogna ritornare allo stato dialogo salvato in previous state
            }
            else{
                gsm.setState(GameStateManager.State.PLAY);
            }
            gsm.setAlreadyPaused(false);
        }
        // Aggiorna il volume quando il mouse è premuto sulla barra del volume
        if (keyH.isPaused() && mouseHandler.isMousePressed()) {
            updateVolume();
        }

    }

    private void updateVolume() {
        int mouseX = mouseHandler.getMouseX();
        int mouseY = mouseHandler.getMouseY();

        int volumeBarX = (gp.getScreenWidth() - volumeBarWidth) / 2;
        int volumeBarY = gp.getScreenHeight() / 2 + 50;

        if (mouseX >= volumeBarX && mouseX <= volumeBarX + volumeBarWidth
                && mouseY >= volumeBarY && mouseY <= volumeBarY + volumeBarHeight) {
            // Calcola il nuovo valore del volume in base alla posizione del mouse sulla barra del volume
            int newVolume = (mouseX - volumeBarX) * 100 / volumeBarWidth;
            // Assicurati che il nuovo volume sia compreso tra 0 e 100
            newVolume = Math.max(0, Math.min(100, newVolume));
            setVolume(newVolume);

            // Cambia il volume effettivo del suono
            for (Sound sound : gsm.getSongList()) {
                sound.setVolume(volume / 100.0f);  // Normalizza il volume a un valore compreso tra 0 e 1
            }
        }
    }

    @Override
    public void draw(Graphics g) {;
        gsm.getPlayState().draw(g);
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Draw volume control bar
        g.setColor(Color.BLUE);  // Change color as needed
        int volumeBarWidth = 200;  // Set width as needed
        int volumeBarHeight = 20;  // Set height as needed
        int volumeBarX = (gp.getScreenWidth() - volumeBarWidth) / 2;
        int volumeBarY = gp.getScreenHeight() / 2 + 50;
        g.fillRect(volumeBarX, volumeBarY, volumeBarWidth, volumeBarHeight);

        // Draw volume indicator
        g.setColor(Color.RED);  // Change color as needed
        int volumeIndicatorWidth = getVolume()*2;
        System.out.println("Volume indicator: "+ volumeIndicatorWidth +
                "\nVolume: "+ volume);
        g.fillRect(volumeBarX, volumeBarY, volumeIndicatorWidth, volumeBarHeight);

        // Draw exit option
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String exitText = "Press P to Exit";
        int exitTextWidth = g.getFontMetrics().stringWidth(exitText);
        int exitX = (gp.getScreenWidth() - exitTextWidth) / 2;
        int exitY = volumeBarY + volumeBarHeight + 30;
        g.drawString(exitText, exitX, exitY);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String pauseText = "Menù Pausa";
        int textWidth = g.getFontMetrics().stringWidth(pauseText);
        int x = (gp.getScreenWidth()- textWidth) / 2;
        int y = gp.getScreenHeight()/ 4;
        g.drawString(pauseText, x, y);

    }

    public int getVolume() {
        return PauseState.volume;
    }

    public void setVolume(int volume) {
        PauseState.volume = volume;
    }
}

