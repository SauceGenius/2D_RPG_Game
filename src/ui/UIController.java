package ui;

import audio.AudioPlayer;
import core.CollisionBox;
import gameobject.Player;
import character.Character;
import gfx.SpriteLibrary;
import input.InputObserver;
import settings.KeyBinds;
import ui.button.CButton;
import ui.inventory.InventorySlot;
import ui.inventory.InventoryUI;
import ui.unitframes.PlayerUnitFrame;
import ui.unitframes.TargetUnitFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UIController implements InputObserver {

    private static final int LOG_BOX = 0;
    private static final int INVENTORY = 1;
    private static final int CHARACTER_PANEL = 2;
    private static final int SPELL_BAR = 3;
    private static final int EXP_BAR = 4;
    private static final int PLAYER_UNIT_FRAME = 5;
    private static final int TARGET_UNIT_FRAME = 6;
    private static final int QUEST_LOG = 7;

    private Character character;
    private SpriteLibrary spriteLibrary;
    private AudioPlayer audioPlayer;
    private ArrayList<UI> uiList;
    private KeyBinds keyBinds;

    public UIController(Character character, SpriteLibrary spriteLibrary, AudioPlayer audioPlayer){
        this.character = character;
        this.spriteLibrary = spriteLibrary;
        this.audioPlayer = audioPlayer;
        this.uiList = new ArrayList<>();
        this.keyBinds = new KeyBinds();

        Player player = (Player) character.getGameObject();
        addUI(new LogBoxUI(character.getLog()));
        addUI(new InventoryUI(player.getInventory()));
        addUI(new CharacterPanelUI(character));
        addUI(new ActionBarUI(character));
        addUI(new ExpBarUI(character));
        addUI(new PlayerUnitFrame((BufferedImage) spriteLibrary.getUnit("player").get("Idle"), player));
        addUI(new TargetUnitFrame(null, player));
        addUI(new QuestLogUI(character));
    }

    public void update(){}

    public void render(Graphics graphics){
        for(UI ui: uiList){
            ui.render(graphics);
        }
    }

    public void toggle(int type){uiList.get(type).toggle(audioPlayer);}

    private void addUI(UI ui){
        uiList.add(ui);
    }

    private void remove(UI ui){
        uiList.remove(ui);
    }

    public ArrayList<UI> getUIList(){
        return uiList;
    }

    public ArrayList<CButton> getButtons(){
        ArrayList<CButton> allCButtons = new ArrayList<>();
        for(UI ui: uiList){
            ArrayList<CButton> uiCButtons = ui.getButtons();
            if(uiCButtons.size() > 0){
                for(CButton button: uiCButtons){
                    allCButtons.add(button);
                }
            }
        }
        return allCButtons;
    }

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {
        char keyChar = keyPressed.getKeyChar();
        if(keyChar == KeyBinds.ACTION_BAR_1) {} // Use action 1
        else if(keyChar == KeyBinds.ACTION_BAR_2) {} // Use action 2
        else if(keyChar == KeyBinds.ACTION_BAR_3) {} // Use action 3
        else if(keyChar == KeyBinds.ACTION_BAR_4) {} // Use action 4
        else if(keyChar == KeyBinds.ACTION_BAR_5) {} // Use action 5
        else if(keyChar == KeyBinds.ACTION_BAR_6) {} // Use action 6
        else if(keyChar == KeyBinds.ACTION_BAR_7) {} // Use action 7
        else if(keyChar == KeyBinds.ACTION_BAR_8) {} // Use action 8
        else if(keyChar == KeyBinds.ACTION_BAR_9) {} // Use action 9
        else if(keyChar == KeyBinds.ACTION_BAR_0) {} // Use action 0

        else if(keyChar == KeyBinds.TOGGLE_INVENTORY) toggle(INVENTORY);
        else if(keyChar == KeyBinds.TOGGLE_CHARACTER_PANEL) toggle(CHARACTER_PANEL);
        else if(keyChar == KeyBinds.TOGGLE_QUEST_LOG) toggle(QUEST_LOG);
    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {
        InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
        for(int i = 0; i < inventorySlots.length; i++){
            if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                inventorySlots[i].setMouseOver(true);
            } else inventorySlots[i].setMouseOver(false);
        }

        ((InventoryUI)(uiList.get(INVENTORY))).getItemIcons().forEach(itemIcon -> {
            if(itemIcon.getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                itemIcon.setMouseOver(true);
                System.out.println("Mouse over item icon");
            } else itemIcon.setMouseOver(false);
        });

        character.getInventory().getItems();
    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseClicked) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mousePressed) {

    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseDragged) {

    }
}
