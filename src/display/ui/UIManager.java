package display.ui;

import audio.AudioPlayer;
import core.Log;
import entity.Player;
import gfx.SpriteLibrary;
import input.InputObserver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIManager implements InputObserver {

    private static final int LOG_BOX = 0;
    private static final int INVENTORY = 1;
    private static final int CHARACTER_PANEL = 2;
    private static final int SPELL_BAR = 3;
    private static final int EXP_BAR = 4;
    private static final int PLAYER_UNIT_FRAME = 5;
    private static final int TARGET_UNIT_FRAME = 6;

    ArrayList<UI> uiList;
    SpriteLibrary spriteLibrary;
    AudioPlayer audioPlayer;

    public UIManager(SpriteLibrary spriteLibrary, AudioPlayer audioPlayer, Log log, Player player){
        uiList = new ArrayList<>();
        this.audioPlayer = audioPlayer;
        addUI(new LogBoxUI(log));
        addUI(new InventoryUI(player.getInventory()));
        addUI(new CharacterPanelUI(player));
        addUI(new SpellBarUI(player));
        addUI(new ExpBarUI(player));
        addUI(new PlayerUnitFrame((BufferedImage) spriteLibrary.getUnit("player").get("Idle"), player));
        addUI(new TargetUnitFrame(null, player));
    }

    public void update(){}

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

    public ArrayList<UI> getUIList(){
        return uiList;
    }

    public UI getUI(int type){return uiList.get(type);}

    public void open(int type){uiList.get(type).open(audioPlayer);}

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {
        switch (keyPressed.getKeyChar()) {
            case 'b' -> open(INVENTORY);
            case 'c' -> open(CHARACTER_PANEL);
        }
    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseClicked) {

    }
}
