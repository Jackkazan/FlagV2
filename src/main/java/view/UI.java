package view;

import model.gameState.GameStateManager;
import model.hud.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    private final GameStateManager gsm;
    private Graphics2D graphics2D;
    private final BufferedImage heart_full, heart_half, heart_blank;
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
}

