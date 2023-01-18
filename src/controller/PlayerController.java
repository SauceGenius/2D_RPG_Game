package controller;

import core.Position;
import input.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements Controller {

    private Input input;

    public PlayerController(Input input){
        this.input = input;
    }

    @Override
    public boolean isRequestingUp() {
        return input.isPressed(KeyEvent.VK_W);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_D);
    }

    @Override
    public boolean isRequestingAttack() {
        if(input.isAttackRequested()){
            input.setAttackRequested(false);
            return true;
        }
        else return false;
    }

    public void attackDone(boolean attackDone){
        if(attackDone){
            this.input.setAttackRequested(false);
        }
        else {
            this.input.setAttackRequested(true);
        }
    }

    @Override
    public boolean isRequestingSprint() {return input.isPressed(KeyEvent.VK_SHIFT);}

    public boolean isClicking() {return input.isMouseClicked();}

    public boolean isInventoryOpen() {
        return input.isInventoryOpen();}

    public boolean isCharacterSheetOpen() {
        return input.isCharacterSheetOpen();}


    public boolean isAddingItem() {
        if(input.isAddingItem() == true){
            this.input.setAddingItem(false);
            return true;} else{
            return false;
        }
    }

    public boolean isRequestingLoot() {
        if(input.isLooting() == true){
            this.input.setLooting(false);
            return true;} else{
            return false;
        }
    }

    public Position getMousePosition(){
        return input.getMousePosition();
    }
}