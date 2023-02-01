package ui;

import core.Position;
import input.Input;
import audio.AudioPlayer;
import core.CollisionBox;
import gameobject.Player;
import character.Character;
import gfx.SpriteLibrary;
import input.InputObserver;
import item.Item;
import settings.KeyBinds;
import ui.button.CButton;
import ui.equipmentui.CharacterPanelUI;
import ui.slot.EquipmentSlot;
import ui.slot.InventorySlot;
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
    private static final int EXP_BAR = 3;
    private static final int ACTION_BAR = 4;
    private static final int PLAYER_UNIT_FRAME = 5;
    private static final int TARGET_UNIT_FRAME = 6;
    private static final int QUEST_LOG = 7;

    private boolean draggingItem = false;
    private Item draggedItem;
    private int draggedFromIndex;

    private Character character;
    private SpriteLibrary spriteLibrary;
    private AudioPlayer audioPlayer;
    private ArrayList<UI> uiList;
    private KeyBinds keyBinds;
    private ArrayList<UIManagerObserver> observers;
    private Position mousePosition;

    public UIController(Character character, SpriteLibrary spriteLibrary, AudioPlayer audioPlayer){
        this.character = character;
        this.spriteLibrary = spriteLibrary;
        this.audioPlayer = audioPlayer;
        this.uiList = new ArrayList<>();
        this.keyBinds = new KeyBinds();
        this.observers = new ArrayList<>();
        this.mousePosition = new Position(0,0);

        Player player = (Player) character.getGameObject();
        addUI(new LogBoxUI(character.getLog()));
        addUI(new InventoryUI(character.getInventory()));
        addUI(new CharacterPanelUI(character));
        addUI(new ExpBarUI(character));
        addUI(new ActionBarUI(character));
        addUI(new PlayerUnitFrame((BufferedImage) spriteLibrary.getUnit("player").get("Idle"), player));
        addUI(new TargetUnitFrame(null, player));
        addUI(new QuestLogUI(character));
    }

    public void update(){
        uiList.forEach(ui -> ui.update());
    }

    public void update(Input input){
        mousePosition.setX(input.getMousePositionRelativeToScreen().intX());
        mousePosition.setY(input.getMousePositionRelativeToScreen().intY());
        ((UI)uiList.get(INVENTORY)).update(input);
        ((UI)uiList.get(CHARACTER_PANEL)).update(input);
        ((UI)uiList.get(ACTION_BAR)).update(input);

        /*if(input.isMouseRightClicked()){
            System.out.println("Is right clicking");
            if(uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mousePosition.intX() - 2,
                    mousePosition.intY() - 2, 4, 4)))){
                InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
                for(int i = 0; i < inventorySlots.length; i++) {
                    if (inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mousePosition.intX() - 2, mousePosition.intY() - 2, 4, 4))) && inventorySlots[i].getItem() != null) {
                        for(UIManagerObserver observer: observers) {
                            observer.notifyPlayerRightClickedOnItem(inventorySlots[i].getItem(), i);
                        }
                    }
                }
            }
        }*/
    }

    public void render(Graphics graphics){
        for(UI ui: uiList){
            ui.render(graphics);
        }

        if(draggingItem){
            //graphics.drawImage(draggedItem.getItemIcon().getIconImage(), mousePosition.intX() - 17, mousePosition.intY() - 17, null);

            draggedItem.getItemIcon().render(graphics, new Position(mousePosition.intX() - 18, mousePosition.intY() - 18));

            graphics.setColor(new Color(0,0,0,100));
            graphics.fillRect(mousePosition.intX() - 18, mousePosition.intY() - 18, 36 , 36);
        }
    }

    public void toggle(int type){
        uiList.get(type).toggle(audioPlayer);
    }

    private void dragFromInventory(int fromIndex, Item item) {
        item.getItemIcon().setDragged(true);
        draggingItem = true;
        draggedItem = item;
        draggedFromIndex = fromIndex;
    }

    private void draggedItemOutsideInventory(int draggedFromIndex) {
        /** WILL HAVE TO IMPLEMENT A CONFIRM POP UP **/

        for(UIManagerObserver observer: observers){
            observer.notifyDraggedItemOutsideInventory(draggedFromIndex);
            ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots()[draggedFromIndex].setMouseOver(false);
        }
    }

    private void swapItems(int toIndex, Item itemOut){
        for(UIManagerObserver observer: observers) {
            observer.notifySwapItemsInInventory(draggedFromIndex, draggedItem, toIndex, itemOut);
        }

        draggedItem.getItemIcon().setDragged(false);
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

    public void addObserver(UIManagerObserver observer){
        observers.add(observer);
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

        /** Mouse moved in Inventory UI (now in InventoryUI) **/
        /*if(uiList.get(INVENTORY).opened && uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
            InventorySlot[] inventorySlots = ((InventoryUI)uiList.get(INVENTORY)).getInventorySlots();
            for(int i = 0; i < inventorySlots.length; i++){
                if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                    inventorySlots[i].setMouseOver(true);
                } else inventorySlots[i].setMouseOver(false);
            }
        }*/

        /** Mouse moved in Character Panel (now in CharacterPanelUI) **/
        /*else if(uiList.get(CHARACTER_PANEL).opened && uiList.get(CHARACTER_PANEL).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
            EquipmentSlot[] equipmentSlots = ((CharacterPanelUI)uiList.get(CHARACTER_PANEL)).getEquipmentSlots();
            for(int i = 0; i < equipmentSlots.length; i++){
                if(equipmentSlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                    equipmentSlots[i].setMouseOver(true);
                } else equipmentSlots[i].setMouseOver(false);
            }
        }*/
    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == 3){

            /** Right click in inventory to equip **/
            if(uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
                for(int i = 0; i < inventorySlots.length; i++) {
                    if (inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))) && inventorySlots[i].getItem() != null) {
                        for(UIManagerObserver observer: observers) {
                            observer.notifyRightClickedOnItemInInventory(inventorySlots[i].getItem(), i);
                        }
                    }
                }
            }
            else if (uiList.get(CHARACTER_PANEL).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                EquipmentSlot[] equipmentSlots = ((CharacterPanelUI)(uiList.get(CHARACTER_PANEL))).getEquipmentSlots();
                for(int i = 0; i < equipmentSlots.length; i++){
                    if(equipmentSlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))) && equipmentSlots[i].getItem() != null) {
                        for(UIManagerObserver observer: observers) {
                            observer.notifyRightClickedOnItemInCharacterPanel(equipmentSlots[i].getItem(), i);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {

        /** Left click in inventory to drag **/
        if(mouseEvent.getButton() == 1){
            if(((InventoryUI)(uiList.get(INVENTORY))).isMouseOverExitButton()){
                toggle(INVENTORY);
            }
            InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
            for(int i = 0; i < inventorySlots.length; i++){
                if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4))) && inventorySlots[i].getItem() != null){
                    draggingItem = true;
                    dragFromInventory(i, inventorySlots[i].getItem());
                }
            }
        }
    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {
        if(draggingItem){

            /** if dropped in Inventory UI **/
            if(uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                InventorySlot[] inventorySlots = ((InventoryUI)(uiList.get(INVENTORY))).getInventorySlots();
                for(int i = 0; i < inventorySlots.length; i++){
                    if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                        inventorySlots[i].setDraggedOver(false);
                        if(uiList.get(INVENTORY).opened){
                            swapItems(i, inventorySlots[i].getItem());
                        }
                    } else {
                        if(inventorySlots[i].getItem() != null){
                            inventorySlots[i].getItem().getItemIcon().setDragged(false);
                        }
                        draggingItem = false;
                    }
                }
            }

            /** Item dropped in Character Panel **/
            /*else if (){

            }*/

            /** Item dropped in Action Bar **/


            /** Item dropped outside UI **/
            else {
                draggedItemOutsideInventory(draggedFromIndex);
            }

            draggingItem = false;
            draggedItem = null;

            //uiList.forEach(ui -> ui.setDraggingItemOver(false));
        }
    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {
        if(draggingItem){

            /** Item dragged over Inventory UI **/
            if(uiList.get(INVENTORY).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                uiList.get(INVENTORY).setDraggingItemOver(true);
                InventorySlot[] inventorySlots = ((InventoryUI)uiList.get(INVENTORY)).getInventorySlots();
                for(int i = 0; i < inventorySlots.length; i++){
                    if(inventorySlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                        inventorySlots[i].setDraggedOver(true);
                    } else inventorySlots[i].setDraggedOver(false);
                }
            }

            /** Item dragged over Character Panel **/
            else if(uiList.get(CHARACTER_PANEL).getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                uiList.get(CHARACTER_PANEL).setDraggingItemOver(true);
                EquipmentSlot[] equipmentSlots = ((CharacterPanelUI)uiList.get(CHARACTER_PANEL)).getEquipmentSlots();
                for (int i = 0; i < equipmentSlots.length; i++){
                    if(equipmentSlots[i].getCollisionBox().collidesWith(new CollisionBox(new Rectangle(mouseEvent.getX() - 2, mouseEvent.getY() - 2, 4, 4)))){
                        equipmentSlots[i].setDraggedOver(true);
                    } else equipmentSlots[i].setDraggedOver(false);
                }
            }
        }
    }
}
