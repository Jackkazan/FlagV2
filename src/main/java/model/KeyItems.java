package model;

import view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class KeyItems extends Rectangle{
    private String nome;
    private List<KeyItems> litaKeyItems = new ArrayList<>();
    private BufferedImage image;
    private boolean collision;
    private int x;
    private int y;
    private Rectangle collisionArea = new Rectangle();



    public static class KeyItemsBuilder extends KeyItems{
        private KeyItems keyItems;

        public KeyItemsBuilder(GamePanel gamePanel, int x, int y){

        }
    }


}
