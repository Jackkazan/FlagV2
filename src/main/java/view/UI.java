package view;

import model.gameState.GameStateManager;
import model.gameState.PauseState;
import model.hud.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static java.awt.Image.SCALE_DEFAULT;
import static java.awt.Image.SCALE_SMOOTH;

public class UI {

    private final GameStateManager gsm;
    private Graphics2D graphics2D;
    private final BufferedImage heart_full, heart_half, heart_blank;
    private Image compassImage;
    private Font maruMonica;
    private OBJ_Heart heart;
    private static UI instance = null;
    public UI() {
        this.gsm = GameStateManager.getInstance();
        setupFonts();
        this.heart = new OBJ_Heart();
        this.heart_full = heart.getImage1();
        this.heart_half = heart.getImage2();
        this.heart_blank = heart.getImage3();

        // Carica l'immagine desideratah
        try {
            InputStream inputStream = getClass().getResourceAsStream("/ui/bussola.png");
            compassImage = ImageIO.read(inputStream);
            compassImage = compassImage.getScaledInstance(1543/15, 1868/15, SCALE_DEFAULT);

            //compassImage = UtilityTool.scaleImage(compassImage, GamePanel.tileSize, GamePanel.tileSize); // Ridimensiona l'immagine
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static UI getInstance(){
        if (instance == null)
            instance = new UI();
        return instance;
    }

    private void setupFonts() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
            this.maruMonica = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        drawPlayerLife();
    }

    private void drawPlayerLife() {
        int x = GamePanel.tileSize / 2;
        int y = GamePanel.tileSize / 2;

        for (int i = 0; i < gsm.getPlayer().getMaxLife() / 2; i++) {
            graphics2D.drawImage(heart_blank, x, y, null);
            x += GamePanel.tileSize;
        }

        x = GamePanel.tileSize / 2;
        y = GamePanel.tileSize / 2;

        for (int i = 0; i < gsm.getPlayer().getCurrentLife(); i++) {
            graphics2D.drawImage(heart_half, x, y, null);
            i++;

            if (i < gsm.getPlayer().getCurrentLife()) {
                graphics2D.drawImage(heart_full, x, y, null);
            }

            x += GamePanel.tileSize;
        }
    }

    private void drawCompass() {
        if (compassImage == null) {
            System.err.println("Error: Compass image not loaded.");
            return;
        }

        int panelWidth = GamePanel.screenWidth;  // Ottieni la larghezza del pannello
        int panelHeight = GamePanel.screenHeight;  // Ottieni l'altezza del pannello

        int x = GamePanel.tileSize / 4;  // Adjust as needed
        int y = panelHeight - 1868/15 - GamePanel.tileSize / 4;  // Regola la posizione Y come desiderato

        // Draw the compass image
        graphics2D.drawImage(compassImage, x, y, null);
    }
}

