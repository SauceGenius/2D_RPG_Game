package input;

import core.Position;
import mainFrame.Camera;

import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener {

    //Variables
    private ArrayList<InputObserver> inputObservers;
    private Camera camera;
    private Position mousePositionRelativeToCamera;
    private Position mousePositionRelativeToScreen;
    private boolean mouseLeftPressed;
    private boolean mouseRightPressed;
    private boolean mouseLeftClicked;
    private boolean mouseRightClicked;
    private boolean mouseReleased;
    private boolean[] pressed;
    private boolean addingItem = false;
    private boolean looting = false;

    //Constructor
    public Input(){
        pressed = new boolean[255];
        mousePositionRelativeToCamera = new Position(0,0);
        mousePositionRelativeToScreen = new Position(0,0);
        inputObservers = new ArrayList<>();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case 1 -> mouseLeftPressed = true;
            case 3 -> mouseRightPressed = true;
        }

        notifyMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseReleased = true;

        if(mouseLeftPressed){
            mouseLeftClicked = true;
            mouseLeftPressed = false;
        }
        else if(mouseRightPressed){
            mouseRightClicked = true;
            mouseRightPressed = false;
        }

        notifyMouseReleased(e);
    }

    /** MOUSE **/
    @Override
    public void mouseClicked(MouseEvent e) {
        if(camera != null) mousePositionRelativeToCamera = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());
        mousePositionRelativeToScreen = new Position(e.getPoint().getX(), e.getPoint().getY());

        notifyMouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        notifyMouseDragged(e);
        if(camera != null) mousePositionRelativeToCamera = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());
        mousePositionRelativeToScreen = new Position(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        notifyMouseMoved(e);
        if(camera != null) mousePositionRelativeToCamera = new Position(e.getPoint().getX() + camera.getPosition().getX(), e.getPoint().getY() + camera.getPosition().getY());
        mousePositionRelativeToScreen = new Position(e.getPoint().getX(), e.getPoint().getY());
    }


    public Position getMousePositionRelativeToCamera() {
        return mousePositionRelativeToCamera;
    }

    public boolean isMouseLeftPressed() {
        return mouseLeftPressed;
    }

    public boolean isMouseLeftClicked() {
        return mouseLeftClicked;
    }

    public boolean isMouseRightClicked() {
        return mouseRightClicked;
    }

    public void clearMouse() {
        mouseLeftClicked = false;
        mouseRightClicked = false;
        mouseReleased = false;
    }


    /** KEYBOARD **/
    //Variables

    //Methods
    @Override
    public void keyTyped(KeyEvent e) {notifyKeyTyped(e);}

    @Override
    public void keyPressed(KeyEvent e) {pressed[e.getKeyCode()] = true;}

    @Override
    public void keyReleased(KeyEvent e) {pressed[e.getKeyCode()] = false;}

    //Getters
    public boolean isPressed(int keyCode) {return pressed[keyCode];}
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

    public void notifyMouseClicked(MouseEvent mouseClicked){
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyMouseClicked(mouseClicked);
        }
    }

    private void notifyMousePressed(MouseEvent mousePressed) {
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyMousePressed(mousePressed);
        }
    }

    private void notifyMouseReleased(MouseEvent mouseEvent){
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyMouseReleased(mouseEvent);
        }
    }

    private void notifyMouseMoved(MouseEvent mouseEvent){
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyMouseMoved(mouseEvent);
        }
    }

    private void notifyMouseDragged(MouseEvent mouseEvent){
        for(InputObserver inputObserver: inputObservers){
            inputObserver.notifyMouseDragged(mouseEvent);
        }
    }

    public Position getMousePositionRelativeToScreen(){
        return mousePositionRelativeToScreen;
    }


    //Setters
    public void setAddingItem(boolean addingItem) {this.addingItem = addingItem;}
    public void setLooting(boolean looting) {this.looting = looting;}
    public void setCamera(Camera camera) {this.camera = camera;}
}

