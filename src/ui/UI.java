package ui;

import audio.AudioPlayer;
import core.Position;
import ui.button.CButton;

import java.awt.*;
import java.util.ArrayList;

public abstract class UI {

    protected boolean opened;
    protected Position position;
    protected ArrayList<Image> images;
    protected ArrayList<String> texts;
    protected AudioPlayer audioPlayer;
    protected ArrayList<CButton> buttons;

    public UI(){
        images = new ArrayList<>();
        texts = new ArrayList<>();
        buttons = new ArrayList<>();
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public ArrayList<CButton> getButtons(){return buttons;}

    public void renderButtons(Graphics graphics){
        for(CButton button: buttons){
            button.render(graphics);
        }
    }

    public abstract void update();

    public abstract void render(Graphics graphics);
    public abstract void toggle(AudioPlayer audioPlayer);
}
