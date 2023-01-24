package controller;

import core.Position;
import input.Input;
import input.InputObserver;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PlayerController implements Controller {

    private Input input;

    public PlayerController(Input input){
        this.input = input;
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
