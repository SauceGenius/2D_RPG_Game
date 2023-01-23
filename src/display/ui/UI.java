package display.ui;

import audio.AudioPlayer;
import core.Position;

import java.awt.*;
import java.util.ArrayList;

public abstract class UI {

    protected boolean enabled;
    protected Position position;
    protected ArrayList<Image> images;
    protected ArrayList<String> texts;

    public UI(){
        images = new ArrayList<>();
        texts = new ArrayList<>();
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public abstract void update(AudioPlayer audioPlayer);
}
