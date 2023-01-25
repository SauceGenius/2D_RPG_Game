package input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputObserver {

    void notifyKeyPressed(KeyEvent keyPressed);
    void notifyMouseClicked(MouseEvent mouseClicked);
    void notifyMousePressed(MouseEvent mousePressed);
    void notifyMouseDragged(MouseEvent mouseDragged);
    void notifyMouseMoved(MouseEvent mouseEvent);
}
