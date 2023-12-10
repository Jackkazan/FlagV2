package model.HUD;

import model.view.GamePanel;
import model.view.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart {

    //OBJECT STATS
    private String name;
    private int value;

    private GamePanel gamePanel;
    // IMAGES
    private BufferedImage image1, image2, image3;

    public OBJ_Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setName("Heart");
        setValue(2);

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
    public BufferedImage getImage1() {
        return this.image1;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage1(BufferedImage image1) {
        this.image1 = image1;
    }

    public BufferedImage getImage2() {
        return this.image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }

    public BufferedImage getImage3() {
        return this.image3;
    }

    public void setImage3(BufferedImage image3) {
        this.image3 = image3;
    }
//    @Override
//    public void use() {
//        gamePanel.playSoundEffect(1);
//        gamePanel.getUi().addMessage("Life +" + getValue());
//        gamePanel.getPlayer().setCurrentLife(gamePanel.getPlayer().getCurrentLife() + getValue());
//    }
}

