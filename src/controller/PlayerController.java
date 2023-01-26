package controller;

import core.Position;
import input.Input;
import input.InputObserver;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayerController implements MovementController, InputObserver {

    private Input input;

    public PlayerController(Input input){
        this.input = input;
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

    public boolean isClicking() {return input.isMouseClicked();}

    public boolean isRightClicking() {return input.isMouseRightClicked();}

    public Position getMousePosition() {return input.getMousePosition();}
}
