package input;

import core.Position;
import display.Camera;

import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    //Variables
    private ArrayList<InputObserver> inputObservers;
    private Camera camera;
    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;
    private boolean[] pressed;
    private boolean inventoryIsOpen = false;
    private boolean characterSheetIsOpen = false;
    private boolean addingItem = false;
    private boolean looting = false;
    /*private boolean attackRequested = false;*/

    //Constructor
    public Input(){
        pressed = new boolean[255];
        mousePosition = new Position(0,0);
        inputObservers = new ArrayList<>();
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
        notifyKeyTyped(e);

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
    public void keyPressed(KeyEvent e) {pressed[e.getKeyCode()] = true;}

    @Override
    public void keyReleased(KeyEvent e) {pressed[e.getKeyCode()] = false;}

    //Getters
    public boolean isPressed(int keyCode) {return pressed[keyCode];}
    public boolean isInventoryOpen() {return inventoryIsOpen;}
    public boolean isCharacterSheetOpen() {return characterSheetIsOpen;}
    public boolean isAddingItem() {return addingItem;}
    public boolean isLooting() {return looting;}


    public void addInputObserver(InputObserver inputObserver){
        inputObservers.add(inputObserver);
    }

    public void removeInputObserver(InputObserver inputObserver){
        inputObservers.remove(inputObserver);
    }

    public void notifyKeyTyped(KeyEvent keyTyped){
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyKeyPressed(keyTyped);
        }
    }

    //Setters
    public void setAddingItem(boolean addingItem) {this.addingItem = addingItem;}
    public void setLooting(boolean looting) {this.looting = looting;}
    public void setCamera(Camera camera) {this.camera = camera;}

    /*public void setAttackRequested(boolean attackRequested) {
        this.attackRequested = attackRequested;
    }*/

    /*public boolean isAttackRequested() {
        return attackRequested;
    }*/
}

