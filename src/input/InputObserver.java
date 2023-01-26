package input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputObserver {

    void notifyMouseClicked(MouseEvent mouseEvent);
    void notifyMousePressed(MouseEvent mouseEvent);
    void notifyMouseReleased(MouseEvent mouseEvent);
    void notifyMouseMoved(MouseEvent mouseEvent);
    void notifyMouseDragged(MouseEvent mouseEvent);
    void notifyKeyPressed(KeyEvent keyPressed);

}
