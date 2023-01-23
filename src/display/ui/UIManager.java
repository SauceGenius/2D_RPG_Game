package display.ui;

import audio.AudioPlayer;
import core.Log;
import entity.Inventory;
import input.InputObserver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class UIManager implements InputObserver {

    ArrayList<UI> uiList;
    AudioPlayer audioPlayer;

    public UIManager(AudioPlayer audioPlayer, Log log, Inventory inventory){
        uiList = new ArrayList<>();
        addUI(new LogBoxUI(log));
        addUI(new InventoryUI(audioPlayer, inventory));
    }

    public void update(){

    }

    public void render(Graphics graphics){
        for(UI ui: uiList){
            ui.render(graphics);
        }
    }

    private void addUI(UI ui){
        uiList.add(ui);
    }

    private void remove(UI ui){
        uiList.remove(ui);
    }

    public ArrayList<UI> getUiList(){
        return uiList;
    }
    public UI getLogBoxUI() {return uiList.get(0);}
    public UI getInventoryUI() {return uiList.get(1);}

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {
        if (keyPressed.getKeyChar() == 'b'){
            getInventoryUI().open();
        }
    }
}
