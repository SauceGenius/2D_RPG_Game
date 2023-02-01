package ui;

import audio.AudioPlayer;
import core.CollisionBox;
import core.Position;
import input.Input;
import ui.button.CButton;

import java.awt.*;
import java.util.ArrayList;

public abstract class UI {

    protected boolean opened;
    protected boolean mouseOverUI;
    protected boolean draggingItemOver;
    protected Position position;
    protected Dimension dimension;
    protected ArrayList<Image> images;
    protected ArrayList<String> texts;
    protected AudioPlayer audioPlayer;
    protected ArrayList<CButton> buttons;

    public UI(){
        this.mouseOverUI = false;
        this.draggingItemOver = false;
        this.images = new ArrayList<>();
        this.texts = new ArrayList<>();
        this.buttons = new ArrayList<>();
    }

    //public abstract void mousePressedOnUI();

    public void renderButtons(Graphics graphics){
        for(CButton button: buttons){
            button.render(graphics);
        }
    }

    public abstract void update();

    public void update(Input input){
        if(opened){
            if(getCollisionBox().collidesWith(new CollisionBox(new Rectangle(input.getMousePositionRelativeToScreen().intX() - 2, input.getMousePositionRelativeToScreen().intY() - 2, 4, 4)))){
                mouseOverUI = true;
            } else mouseOverUI = false;
        }
    }

    public abstract void render(Graphics graphics);

    public abstract void toggle(AudioPlayer audioPlayer);

    //public abstract void notifyMousePressedOnUI(MouseEvent mouseEvent);

    public CollisionBox getCollisionBox(){
        return new CollisionBox(new Rectangle(position.intX(), position.intY(), dimension.width, dimension.height));
    }

    /** Setters **/
    public void setDraggingItemOver(boolean draggingItemOver) {
        this.draggingItemOver = draggingItemOver;
    }

    /** Getters **/
    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public ArrayList<CButton> getButtons(){
        return buttons;
    }

    public boolean isDraggingItemOver() {
        return draggingItemOver;
    }
}
