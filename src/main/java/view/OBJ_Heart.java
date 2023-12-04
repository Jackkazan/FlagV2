package view;

import view.GamePanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends PickUpOnlyObject {

    private final GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        setName("Heart");
        setValue(2);
        setDescription("[" + getName() + "]\nWill restore " + getValue() + " life");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/heart/heart_full.png")));
            setImage1(UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/heart/heart_half.png")));
            setImage2(UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize));

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/heart/heart_blank.png")));
            setImage3(UtilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void use() {
//        gamePanel.playSoundEffect(1);
//        gamePanel.getUi().addMessage("Life +" + getValue());
//        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() + getValue());
//    }
}

