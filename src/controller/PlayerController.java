package controller;

import character.Character;
import core.Position;
import gameobject.LivingObject;
import input.Input;
import input.InputObserver;
import item.EquipableItem;
import item.Item;
import item.Weapon;
import ui.UIManagerObserver;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayerController implements MovementController, InputObserver, UIManagerObserver {

    private Input input;
    private Character character;

    public PlayerController(Input input){
        this.input = input;
    }

    private void equip(EquipableItem item, int indexFrom){
        if(item instanceof Weapon){
            if(!((LivingObject)character.getGameObject()).getStatus().isInCombat()){
                Item equippedItem = character.getEquipment().getItem(0);
                character.getEquipment().equip(item);
                character.getInventory().exchangeItem(indexFrom, equippedItem);
                character.getStats().getHp().resetHp5Timer();
            } else {
                System.out.println("Cant equip while in combat");
            }
        }
    }

    private void unequip(Item item, int index) {
        character.getEquipment().unequip(index);
        character.getInventory().addItem(item);
    }

    public void loseHP(int damage) {
        character.getStats().getHp().setCurrentHp(character.getStats().getCurrentHpValue() - damage);
    }

    @Override
    public void notifyKeyPressed(KeyEvent keyPressed) {

    }

    @Override
    public void notifyMouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void notifyMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public boolean isRequestingUp() {return input.isPressed(KeyEvent.VK_W);}

    @Override
    public boolean isRequestingDown() {return input.isPressed(KeyEvent.VK_S);}

    @Override
    public boolean isRequestingLeft() {return input.isPressed(KeyEvent.VK_A);}

    @Override
    public boolean isRequestingRight() {return input.isPressed(KeyEvent.VK_D);}

    @Override
    public boolean isRequestingSprint() {return input.isPressed(KeyEvent.VK_SHIFT);}

    public boolean isLeftClicking() {return input.isMouseLeftClicked();}

    public boolean isRightClicking() {return input.isMouseRightClicked();}

    public Position getMousePosition() {return input.getMousePositionRelativeToCamera();}

    @Override
    public void notifyRightClickedOnItemInInventory(Item item, int indexFrom) {
        if(item instanceof EquipableItem){
            equip((EquipableItem) item, indexFrom);
        }
    }

    @Override
    public void notifyRightClickedOnItemInCharacterPanel(Item item, int index) {
        unequip(item, index);
    }

    @Override
    public void notifySwapItemsInInventory(int indexFrom, Item itemFrom, int indexTo, Item itemTo) {
        character.getInventory().setItem(indexTo, itemFrom);
        character.getInventory().setItem(indexFrom, itemTo);
    }

    @Override
    public void notifyDraggedItemOutsideInventory(int indexFrom) {
        character.getInventory().removeItem(indexFrom);
    }

    public void setCharacter(Character character){
        this.character = character;
    }
}
