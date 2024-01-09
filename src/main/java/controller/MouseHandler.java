package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    private boolean mousePressed;
    private int mouseX;
    private int mouseY;
    private static MouseHandler instance = null;
    public MouseHandler() {
        mousePressed = false;
        mouseX = 0;
        mouseY = 0;
    }
    public static MouseHandler getInstance(){
        if (instance == null)
            instance = new MouseHandler();
        return instance;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        updateMousePosition(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        updateMousePosition(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    public boolean isMousePressed() {
        return mousePressed;
    }
    public boolean isMouseReleased(){
        return !mousePressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    private void updateMousePosition(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
