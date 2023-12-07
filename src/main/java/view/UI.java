package view;

import model.HUD.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    private final GamePanel gamePanel;
    private Graphics2D graphics2D;
    private final BufferedImage heart_full, heart_half, heart_blank;
    private Font maruMonica;
    private OBJ_Heart heart;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setupFonts();

        this.heart = new OBJ_Heart(gamePanel);
        this.heart_full = heart.getImage1();
        this.heart_half = heart.getImage2();
        this.heart_blank = heart.getImage3();
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
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;

        for (int i = 0; i < gamePanel.getPlayer().getMaxLife() / 2; i++) {
            graphics2D.drawImage(heart_blank, x, y, null);
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;

        for (int i = 0; i < gamePanel.getPlayer().getCurrentLife(); i++) {
            graphics2D.drawImage(heart_half, x, y, null);
            i++;

            if (i < gamePanel.getPlayer().getCurrentLife()) {
                graphics2D.drawImage(heart_full, x, y, null);
            }

            x += gamePanel.tileSize;
        }
    }
}

