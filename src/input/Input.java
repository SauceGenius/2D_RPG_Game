package input;

import core.Position;
import display.Camera;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    //Variables
    private Camera camera;
    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;

    private boolean[] pressed;
    private boolean attackRequested = false;
    private boolean inventoryIsOpen = false;
    private boolean characterSheetIsOpen = false;
    private boolean addingItem = false;
    private boolean looting = false;

    //Constructor
    public Input(){
        pressed = new boolean[255];
        mousePosition = new Position(0,0);
    }

    //////////////// MOUSE ////////////////
    @Override
    public void mouseClicked(MouseEvent e) {mousePosition = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {mousePosition = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());}
    @Override
    public void mouseMoved(MouseEvent e) {mousePosition = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());}

    public void clearMouseClicked() {mouseClicked = false;}
    public Position getMousePosition() {return mousePosition;}

    public boolean isMouseClicked() {return mouseClicked;}
    public boolean isMousePressed() {return mousePressed;}


    ///////////////// KEYBOARD ////////////////
    //Variables

    //Methods
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'b':
                if(inventoryIsOpen == false) {
                    inventoryIsOpen = true;
                } else {
                    inventoryIsOpen = false;}
                break;
            case 'c':
                if(characterSheetIsOpen == false) {
                    characterSheetIsOpen = true;
                } else {
                    characterSheetIsOpen = false;}
                break;
            case 'z':
                if(looting == false) {
                    looting = true;
                } else {looting = false;}
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){
            attackRequested = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {pressed[e.getKeyCode()] = false;
    }

    public boolean isPressed(int keyCode) {
        return pressed[keyCode];
    }
    public boolean isInventoryOpen() {
        return inventoryIsOpen;
    }
    public boolean isCharacterSheetOpen() {return characterSheetIsOpen;}
    public boolean isAddingItem() {
        return addingItem;
    }
    public boolean isLooting() {return looting;}
    public void setAddingItem(boolean addingItem) {
        this.addingItem = addingItem;
    }
    public void setLooting(boolean looting) {this.looting = looting;}

    public void setAttackRequested(boolean attackRequested) {
        this.attackRequested = attackRequested;
    }

    public boolean isAttackRequested() {
        return attackRequested;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}

