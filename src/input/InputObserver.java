package input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputObserver {

    public void notifyKeyPressed(KeyEvent keyPressed);
    public void notifyMouseClicked(MouseEvent mouseClicked);
}
