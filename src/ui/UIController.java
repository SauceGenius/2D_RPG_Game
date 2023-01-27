package ui;

import inventory.Inventory;
import audio.AudioPlayer;
import core.CollisionBox;
import gameobject.Player;
import character.Character;
import gfx.SpriteLibrary;
import input.InputObserver;
import item.Item;
import settings.KeyBinds;
import ui.button.CButton;
import ui.inventoryui.InventorySlot;
import ui.inventoryui.InventoryUI;
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

    private boolean draggingItem = false;
    private Item draggedItem;
    private int draggedOrigin;

    private Character character;
    private SpriteLibrary spriteLibrary;
    private AudioPlayer audioPlayer;
    private ArrayList<UI> uiList;
    private KeyBinds keyBinds;
    private ArrayList<UIManagerObserver> observers;

    public UIController(Character character, SpriteLibrary spriteLibrary, AudioPlayer audioPlayer){
        this.character = character;
        this.spriteLibrary = spriteLibrary;
        this.audioPlayer = audioPlayer;
        this.uiList = new ArrayList<>();
        this.keyBinds = new KeyBinds();
        this.observers = new ArrayList<>();

        Player player = (Player) character.getGameObject();
        addUI(new LogBoxUI(character.getLog()));
        addUI(new InventoryUI(character.getInventory()));
        addUI(new CharacterPanelUI(character));
        addUI(new ActionBarUI(character));
        addUI(new ExpBarUI(character));
        addUI(new PlayerUnitFrame((BufferedImage) spriteLibrary.getUnit("player").get("Idle"), player));
        addUI(new TargetUnitFrame(null, player));
        addUI(new QuestLogUI(character));
    }

    public void update(){
        uiList.forEach(ui -> ui.update());
    }

    public void render(Graphics graphics){
        for(UI ui: uiList){
            ui.render(graphics);
        }
    }

    public void toggle(int type){
        uiList.get(type).toggle(audioPlayer);
    }

    private void drag(Item item, int fromIndex) {
        item.getItemIcon().setDragged(true);
        draggingItem = true;
        draggedItem = item;
        draggedOrigin = fromIndex;
    }

    private void swapItems(Item itemIn, Item itemOut, int toIndex){
        Inventory inventory = character.getInventory();
        inventory.getItems()[toIndex] = itemIn;
        inventory.getItems()[draggedOrigin] = itemOut;

        draggingItem = false;
        draggedItem = null;
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
    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == 3){
            InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
            for(int i = 0; i < inventorySlots.length; i++){
                if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))) && inventorySlots[i].getItem() != null){
                    System.out.println("Right Clicked on: " + inventorySlots[i].getItem().getName());
                    for(UIManagerObserver observer: observers) {
                        observer.notifyPlayerRightClickedOnItem(inventorySlots[i].getItem(), i);
                    }
                }
            }
        }
    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {
        /*if(uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))))
            uiList.get(INVENTORY).mousePressedOnUI();*/


        draggingItem = true;
        InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
        for(int i = 0; i < inventorySlots.length; i++){
            if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))) && inventorySlots[i].getItem() != null){
                drag(inventorySlots[i].getItem(), i);
            }
        }
    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {
        if(draggingItem){
            InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
            for(int i = 0; i < inventorySlots.length; i++){
                if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                    swapItems(draggedItem, inventorySlots[i].getItem(), i);
                    inventorySlots[i].setDraggedOver(false);
                } else {
                    if(inventorySlots[i].getItem() != null){
                        inventorySlots[i].getItem().getItemIcon().setDragged(false);
                    }
                    draggingItem = false;
                }
            }
        }
    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {
        if(draggingItem){
            InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
            for(int i = 0; i < inventorySlots.length; i++){
                if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                    inventorySlots[i].setDraggedOver(true);
                } else inventorySlots[i].setDraggedOver(false);
            }
        }
    }
}
